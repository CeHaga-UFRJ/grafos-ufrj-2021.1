public class AlgGrafos {
    public static void main(String[] args) {
        String fileName = "myfiles/grafo01.txt";
        Graph g = new Graph(fileName);
        g.isCactusVerbose();
    }
}
