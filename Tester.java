public class Tester {
    public static void main(String[] args){
        Graph.DoublyLinkedList dll = new Graph.DoublyLinkedList();
        for(int i = 1; i <= 10; i++){
            dll.insert(new Graph.Node(i,i*i));
        }
        int x = 5;
    }
}
