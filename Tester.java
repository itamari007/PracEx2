import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Tester {
    public static void main(String[] args){
        /**Graph.DoublyLinkedList dll = new Graph.DoublyLinkedList();
        for(int i = 1; i <= 10; i++){
            dll.insert(new Graph.Node(i,i*i));
        }
        int x = 5;
         */
        int p = 1000000;
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
        int x  = 5;
    }
}
