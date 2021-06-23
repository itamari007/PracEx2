import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tester {
    public static void main(String[] args){
        for(double i = 6; i<21; i++){
            int N = (int)Math.pow(2,i);
            Graph.Node[] nodes = new Graph.Node[N];
            for(int j =1;j<=N;j++){
                nodes[j-1] = new Graph.Node(j,1);
            }
            Graph g = new Graph(nodes);
            int counter = 0;
            while(counter < N){
                Random rnd = new Random();
                int u = rnd.nextInt(N-1)+1;
                int v = rnd.nextInt(N-1)+1;
                if(g.addEdge(u,v)==true){
                    counter++;
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
