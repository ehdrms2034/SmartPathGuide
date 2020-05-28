package kr.pnu.ga2019.data

import com.orhanobut.logger.Logger
import kr.pnu.ga2019.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/*
 * Created by Lee Oh Hyoung on 2020/05/27 .. 
 */
object RetrofitManager {

    private const val TAG: String = "RetrofitManager"

    private const val BASE_URL = "http://52.87.231.150:3000"
    private const val CONNECT_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L

    inline fun <reified T> create(service: Class<T>): T {
        return getRetrofit().create(service)
    }

    fun getRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(getOkHttpClient())
            .build()

    private fun getOkHttpClient(): OkHttpClient =
        OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            .addInterceptor(getLoggerInterceptor())
            .build()

    private fun getLoggerInterceptor(): Interceptor = object: Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {
            val response: Response = chain.proceed(chain.request().newBuilder().build())
            val body: String = response.body?.string() ?: ""
            try {
                JSONObject(body)
                Logger.t(TAG).json(body)
            } catch (e: JSONException) {
                Logger.t(TAG).d(body)
            } finally {
                return response.newBuilder()
                    .body(ResponseBody.create(
                        response.body?.contentType(),
                        body)
                    )
                    .build()
            }
        }
    }
}
