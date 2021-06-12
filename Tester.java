import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tester {
    public static void main(String[] args){
        double p = 0.5;
        for(int i = 6; i<21; i++){
            int N = 2^i;
            Graph.Node[] nodes = new Graph.Node[N];
            for(int j =1;j<=N;j++){
                int randomW = ThreadLocalRandom.current().nextInt(1,2^i);
                nodes[j-1] = new Graph.Node(j,randomW);
            }
            Graph g = new Graph(nodes);
            for(int j =1;j<=i;j++){
                for(int k = j+1;k<i;k++){
                    double res = ThreadLocalRandom.current().nextDouble(0.0,1.0);
                    if( res >= p){
                        g.addEdge(j,k);
                    }
                }
            }
            Graph.Node maxNode = g.maxNeighborhoodWeight();
            System.out.println("For graph with i = "+i);
            System.out.println("The maxNeighbourhoodWeight is:"+maxNode.getNeighbourhoodWeight());
            System.out.println("the graph has "+g.getNumNodes()+" nodes , and "+g.getNumEdges()+" edges");
            System.out.println("The node in questionn is node with id: "+maxNode.getId());
            System.out.println("it's DARGA(num of neighbours) is "+maxNode.neighbourhood.getDllLength());
            System.out.println("maxNBw = "+maxNode.getNeighbourhoodWeight() + ",darga =" +maxNode.neighbourhood.getDllLength());
            System.out.println();
        }
  }
}
