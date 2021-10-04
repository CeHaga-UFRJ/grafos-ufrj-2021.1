import java.util.HashMap;

public class Vertex {
    protected int id;
    protected HashMap<Integer, Vertex> nbhood;
    protected Integer d;
    protected Integer low;
    protected Vertex parent;

    public Vertex(int id){
        this.id = id;
        nbhood = new HashMap<>();
        d = null;
        low = null;
        parent = null;
    }

    public void addNeighbor(Vertex v){
        nbhood.put(v.id, v);
    }

    public int getDegree(){
        return nbhood.size();
    }

    public void reset(){
        d = null;
        parent = null;
    }

    public void print() {
        System.out.print("\nId do vértice " + id + ", Vizinhança: " );
        for( Vertex v : nbhood.values())
            System.out.print(" " + v.id );
    }
}
