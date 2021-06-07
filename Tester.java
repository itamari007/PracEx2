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
        int p = 1009;
        Graph.Node[] nodes = new Graph.Node[p];
        for(int i = 1; i <= p; i++){
            nodes[i-1] = new Graph.Node(i, ThreadLocalRandom.current().nextInt(1,p));
        }
        Graph g = new Graph(nodes);
        Graph.DoublyLinkedList example;
        for (Graph.DoublyLinkedList d: g.myTable.hashedIds
             ) {
            if(d!=null){
                example = d;
            }
        }
        int x =5;
    }
}
