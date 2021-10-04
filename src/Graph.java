import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Stack;

public class Graph {
    private HashMap<Integer, Vertex> vertices;
    private Stack<Vertex> comp;
    protected int time;
    private boolean biComp2Deg;
    private boolean isConnected;

    public Graph(){
        vertices = new HashMap<>();
        biComp2Deg = true;
        isConnected = true;
    }

    public Graph(String fileName){
        String thisLine = null;
        vertices = new HashMap<>();
        biComp2Deg = true;
        isConnected = true;
        String pieces[];
        try {
            FileReader fileReader = new FileReader(fileName);
            BufferedReader br = new BufferedReader(fileReader);
            while((thisLine = br.readLine()) != null) {
                thisLine = thisLine.replaceAll("\\s+", " ");
                pieces = thisLine.split(" ");
                int v1 = Integer.parseInt(pieces[0]);
                this.addVertex(v1);
                for(int i = 2; i < pieces.length; i++) {
                    int v2 = Integer.parseInt(pieces[i]);
                    this.addVertex(v2);
                    this.addEdge(v1, v2);
                }
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    public void print() {
        for( Vertex v : vertices.values())
            v.print();
    }

    public void addVertex(int id){
        if(id < 1 || vertices.containsKey(id)){
            return;
        }
        Vertex v = new Vertex(id);
        vertices.put(id, v);
        reset();
    }

    public void addEdge(int id1, int id2){
        addVertex(id1);
        addVertex(id2);
        Vertex u = vertices.get(id1);
        Vertex v = vertices.get(id2);
        u.addNeighbor(v);
        v.addNeighbor(u);
        reset();
    }

    public void findBiComp(){
        comp = new Stack<>();
        int connected = 1;
        reset();
        for(Vertex v : vertices.values()){
            if(v.d == null) {
                if(connected-- == 0) isConnected = false;
                biCompVisit(v);
            }
        }
    }

    private void biCompVisit(Vertex v){
        v.d = ++time;
        v.low = v.d;
        for(Vertex nb : v.nbhood.values()){
            if(nb.d == null){
                comp.push(v);
                comp.push(nb);
                nb.parent = v;
                biCompVisit(nb);
                if(nb.low >= v.d){
                    checkBiCompGraph(v, nb);
                }
                if(nb.low < v.low){
                    v.low = nb.low;
                }
            }else if(nb != v.parent){
                if(nb.d < v.d){
                    comp.push(v);
                    comp.push(nb);
                    if(nb.d < v.low){
                        v.low = nb.d;
                    }
                }
            }
        }
    }

    private void checkBiCompGraph(Vertex cut, Vertex aux){
        if(comp.empty()) return;
        Graph g = new Graph();
        Vertex v2 = comp.pop();
        Vertex v1 = comp.pop();
        while(v1 != cut || v2 != aux) {
            if(comp.empty())
                return;
            g.addEdge(v1.id, v2.id);
            v2 = comp.pop();
            v1 = comp.pop();
        }
        g.addEdge(v1.id, v2.id);
        if(!isDeg2(g)){
            biComp2Deg = false;
        }
    }

    private boolean isDeg2(Graph g){
        for(Vertex v : g.vertices.values()){
            if(v.getDegree() > 2) return false;
        }
        return true;
    }

    public void reset(){
        time = 0;
        for(Vertex v : vertices.values()){
            v.reset();
        }
    }

    public boolean isCactus(){
        findBiComp();
        if(!isConnected) return false;
        if(!biComp2Deg) return false;
        return true;
    }

    public void isCactusVerbose(){
        String cactus = "";
        if(!isCactus()) cactus = "não ";
        System.out.println("O grafo "+cactus+"é cacto");
    }
}
