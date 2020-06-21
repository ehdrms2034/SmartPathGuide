#!/usr/bin/env python
# coding: utf-8
from flask import Flask,jsonify

import pandas as pd
import numpy as np
import tmdbsimple as tmdb
from sklearn.pipeline import make_pipeline
from sklearn import pipeline, feature_selection, decomposition
from sklearn.base import BaseEstimator, TransformerMixin
import joblib
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.pipeline import Pipeline, FeatureUnion
from sklearn.cluster import DBSCAN, AgglomerativeClustering, Birch
from sklearn.decomposition import PCA, NMF
from sklearn.metrics import silhouette_score
from sklearn.metrics.pairwise import cosine_similarity
import pprint
from sklearn.utils.extmath import randomized_svd
from scipy.sparse.linalg import svds

app = Flask(__name__)

def recommend_path():
    userinfos = pd.read_csv('userinfo.csv')
    userinfos = userinfos.drop(userinfos.columns[-1], axis="columns")
    userinfos = userinfos.drop(userinfos.columns[-1], axis="columns")

    userinfos.head()
    userinfos.loc[len(userinfos)] = [20001, 22, 21, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
    userinfos.tail()

    places = pd.read_csv('places.csv')
    places = places.drop(places.columns[-1], axis="columns")
    places = places.drop(places.columns[-1], axis="columns")

    places.head()

    paths = pd.read_csv('path.csv')
    paths.head()
    paths = paths.drop(paths.columns[-1], axis="columns")
    paths = paths.drop(paths.columns[-1], axis="columns")

    paths.head()
    paths.tail()

    paths.loc[len(paths)] = [len(paths+1), 20001, 0, 1, 22]
    paths.loc[len(paths)] = [len(paths+1), 20001, 1, 2, 27]

    paths.tail()

    wideMatrix = pd.pivot_table(paths, values="stayTime",
                                index=['userSeq', 'placeId'],
                                aggfunc=np.mean).unstack()
    
    wideMatrix.iloc[0:5, 0:30]
    wideMatrix2 = wideMatrix.fillna(0)
    wideMatrix2.tail()

    R = wideMatrix2.values

    user_datas = np.mean(R, axis=1)

    R_demeaned = R - user_datas.reshape(-1, 1)

    U, sigma, Vt = svds(R_demeaned, k=11)

    sigma = np.diag(sigma)

    preidicted = np.dot(np.dot(U, sigma), Vt) + user_datas.reshape(-1, 1)

    preds_df = pd.DataFrame(preidicted, columns=wideMatrix2.columns)
    
    already_rated, predictions = recommend_place(preds_df, 1, places, paths, 5)
    return already_rated, predictions

def recommend_place(predictions_df, userSeq, places_df, original_paths_df, num_recommendations=2):
    user_row_number = userSeq - 1
    sorted_user_predictions = predictions_df.iloc[user_row_number].sort_values(
        ascending=False)

    user_data = original_paths_df[original_paths_df.userSeq == (userSeq)]
    user_full = (user_data.merge(places_df, how='left', left_on="placeId", right_on="id").
                 sort_values(['stayTime'], ascending=False))
    print('user {0} has alerady rated {1} places.'.format(
        userSeq, user_full.shape[0]))

    recommendations = (places_df[~places_df['id'].isin(user_full['placeId'])].
                       merge(pd.DataFrame(sorted_user_predictions).reset_index(), how='left',
                             left_on="id",
                             right_on="placeId").
                       rename(columns={user_row_number: 'Predictions'}).
                       sort_values('Predictions', ascending=False).iloc[:num_recommendations, :-1])
    return user_full, recommendations


@app.route('/')
def index():
    user_full, recommendation = recommend_path()
    print(recommendation)
    data = recommendation.to_dict()
    return jsonify(data)

if __name__ == '__main__':
    # recommend_path()
    app.run(host="localhost",port=5000,debug=True)

