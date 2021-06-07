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
        int p = 1000000009;
        Graph2.Node[] nodes = new Graph2.Node[p];
        for(int i = 1; i <= p; i++){
            nodes[i-1] = new Graph2.Node(i, ThreadLocalRandom.current().nextInt(1,p));
        }
        Graph2 g2 = new Graph2(nodes);
        int x =100;
    }
}
