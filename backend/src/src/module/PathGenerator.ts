import { Districts } from "@models/Districts";
import { timeStamp } from "console";
import { scatter_util, randomUniform } from "@tensorflow/tfjs-node";
import { add } from "winston";

class PathGenerator {
  readonly MAX_MIN = 30;

  public pathList: Array<any> = [];
  constructor() {}

  public generateMap() {
    this.pathList = [];
    const graph: Graph = new Graph(12);
    graph.initGraph();
    this.dfs(1, graph, []);
    this.dfs(8,graph,[]);
    const index = Math.floor(randomUniform([1]).dataSync()[0] * this.pathList.length);
    return this.pathList[index];
  }

  public getPath(){
    const list = this.generateMap();
    const path = [];
    for(let item of list){
      const stayTime = Math.floor(Math.random() * this.MAX_MIN) + 1;
      path.push({place : item, stayTime : stayTime});
    }
    return path;
  }
  

  public dfs(start: number, graph: Graph, list: Array<number>) {
    if (list.length !== 0 && start == Districts.ENTRANCE) {
      this.pathList.push(list);
      return;
    }

    if (list.length >= 12) return;
    list.push(start);

    for (let next of graph.adjacent[start]) {
      if (list.indexOf(next) != -1) {
        // console.log(start,next,graph.adjacent[start].indexOf(next));
        continue;
      }
      const newList = Array.from(list);
      this.dfs(next, graph, newList);
    }
  }
}

class Graph {
  public vertex!: number;
  public adjacent!: Array<Array<number>>;

  constructor(vertex: number) {
    this.vertex = vertex;
    this.adjacent = new Array<Array<number>>(vertex + 1);
    for (let i = 0; i <= vertex; i++) {
      this.adjacent[i] = [];
    }
  }

  public addEdge(v: number, w: number) {
    if (this.adjacent[v].indexOf(w) !== -1) return;
    this.adjacent[v].push(w);
    this.adjacent[w].push(v);
  }

  public initGraph() {
    //ANCIENT:1, MEDIEVAL:2, MODERN:3, DONATION:4,
    //PAINTING:5, WORLD:6, CRAFT:7, SCIENCE:8, SPACE:9,
    //HUMAN:10, NATURAL:11, FUTURE:12
    this.addEdge(Districts.ENTRANCE, Districts.ANCIENT);
    this.addEdge(Districts.ENTRANCE, Districts.SCIENCE);

    this.addEdge(Districts.ANCIENT, Districts.MEDIEVAL);
    this.addEdge(Districts.ANCIENT, Districts.DONATION);

    this.addEdge(Districts.MEDIEVAL, Districts.MODERN);

    this.addEdge(Districts.MODERN, Districts.DONATION);

    this.addEdge(Districts.SCIENCE, Districts.SPACE);
    this.addEdge(Districts.SCIENCE, Districts.HUMAN);
    this.addEdge(Districts.SCIENCE, Districts.NATURAL);

    this.addEdge(Districts.SPACE, Districts.HUMAN);
    this.addEdge(Districts.SPACE, Districts.FUTURE);

    this.addEdge(Districts.HUMAN, Districts.NATURAL);
    this.addEdge(Districts.HUMAN, Districts.CRAFT);

    this.addEdge(Districts.NATURAL, Districts.FUTURE);
    this.addEdge(Districts.NATURAL, Districts.CRAFT);

    this.addEdge(Districts.FUTURE, Districts.HUMAN);

    this.addEdge(Districts.DONATION, Districts.PAINTING);
    this.addEdge(Districts.DONATION, Districts.CRAFT);

    this.addEdge(Districts.PAINTING, Districts.WORLD);
    this.addEdge(Districts.WORLD, Districts.CRAFT);
  }
}

export default PathGenerator;
