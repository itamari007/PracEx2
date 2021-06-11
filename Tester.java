import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tester {
    public static void main(String[] args){
        int p = 10;
        Graph.Node[] nodes = new Graph.Node[p];
        for(int i = 1; i <= p; i++){
            nodes[i-1] = new Graph.Node(i, ThreadLocalRandom.current().nextInt(1,p));
        }
        Graph g = new Graph(nodes);
        /*System.out.println("Histogram is: ");
        double sum = 0;
        double amount = 0;
        for (Graph.DoublyLinkedList d: g.myTable.hashedIds
             ) {
            if(d!=null){
                sum+=d.getDllLength();
                System.out.println("number of nodes mapped here: "+d.getDllLength());
                System.out.println("Total sum: "+sum);
                amount++;
            }
        }
        Double avg = sum/amount;
        System.out.println("avg alpha = "+avg);
        int x =5;*/
        g.addEdge(2,3);
        g.addEdge(4,5);
        g.addEdge(6,7);
        g.addEdge(2,5);
        Graph.Node n = g.maxNeighborhoodWeight();
        System.out.println("Maximum nWeight = "+n);
        int x  = g.getNeighborhoodWeight(5);
        System.out.println("NeighborW for id: 5  is "+ x);
        Graph.Node v2 = g.myTable.accessNode(2);
        Graph.Node v3 = g.myTable.accessNode(3);
        Graph.Node v5 = g.myTable.accessNode(5);
        g.deleteNode(2);
        System.out.println("Managed to delete node with id:" + 1000000 + 99 +"? "+g.deleteNode(1000000 + 99));
  }
}
