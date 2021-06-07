/*
You must NOT change the signatures of classes/methods in this skeleton file.
You are required to implement the methods of this skeleton file according to the requirements.
You are allowed to add classes, methods, and members as required.
 */

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

/**
 * This class represents a graph that efficiently maintains the heaviest neighborhood over edge addition and
 * vertex deletion.
 *
 */
public class Graph {
    private static int p = (int) Math.pow(10, 9) + 9;
    private int N;
    int n;
    int  m;
    MyHashtable myTable;
    /**
     * Initializes the graph on a given set of nodes. The created graph is empty, i.e. it has no edges.
     * You may assume that the ids of distinct nodes are distinct.
     *
     * @param nodes - an array of node objects
     */
    public Graph(Node [] nodes){
        this.N = nodes.length;
        this.n = N;
        this.m = 0;
        myTable = new MyHashtable(p,nodes);
    }



    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
        //TODO: implement this method.
        return null;
    }

    /**
     * given a node id of a node in the graph, this method returns the neighborhood weight of that node.
     *
     * @param node_id - an id of a node.
     * @return the neighborhood weight of the node of id 'node_id' if such a node exists in the graph.
     * Otherwise, the function returns -1.
     */
    public int getNeighborhoodWeight(int node_id){
        //TODO: implement this method.
        return 0;
    }

    /**
     * This function adds an edge between the two nodes whose ids are specified.
     * If one of these nodes is not in the graph, the function does nothing.
     * The two nodes must be distinct; otherwise, the function does nothing.
     * You may assume that if the two nodes are in the graph, there exists no edge between them prior to the call.
     *
     * @param node1_id - the id of the first node.
     * @param node2_id - the id of the second node.
     * @return returns 'true' if the function added an edge, otherwise returns 'false'.
     */
    public boolean addEdge(int node1_id, int node2_id){
        //TODO: implement this method.
        return false;
    }

    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        //TODO: implement this method.
        return false;
    }
	
	/**
	 * Returns the number of nodes currently in the graph.
	 * @return the number of nodes in the graph.
	 */
	public int getNumNodes(){
		//TODO: implement this method.
		return 0;
	}
	
	/**
	 * Returns the number of edges currently in the graph.
	 * @return the number of edges currently in the graph.
	 */
	public int getNumEdges(){
		//TODO: implement this method.
		return 0;
	}

    /**
     * This class represents a node in the graph.
     */
    public static class Node{
        private int id;
        private int weight;
        private int neighbourhoodWeight;
        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            this.neighbourhoodWeight = weight;
        }

        /**
         * Returns the id of the node.
         * @return the id of the node.
         */
        public int getId(){
            return id;
        }

        /**
         * Returns the weight of the node.
         * @return the weight of the node.
         */
        public int getWeight(){
            return weight;
        }
    }

    public static class DoublyLinkedList{
        private dllNode head;
        private dllNode tail;
        private int dllLength = 0;

        public int getDllLength(){
            return dllLength;
        }

        public void insert(Node newNode){
            dllNode newDllnode = new dllNode(newNode);
            if(head == null){
                head = newDllnode;
            }
            if(tail == null){
                tail = newDllnode;
            }
            else{
                newDllnode.setPrev(head);
                head.setNext(newDllnode);
                head = head.next;
            }
            dllLength++;
        }
        private boolean hasPrev(dllNode nodeInQuestion){
            return nodeInQuestion!=null && nodeInQuestion.prev!=null;
        }
        private boolean hasNext(dllNode nodeInQuestion){
            return nodeInQuestion!=null && nodeInQuestion.next!=null;
        }
        public void delete(dllNode condemned){
            boolean prevExist = hasPrev(condemned);
            boolean nextExist = hasNext(condemned);
            dllNode X = null;
            dllNode Z = null;
            if(prevExist){
                X = condemned.prev;
                condemned.prev = null;
            }
            if(nextExist){
                Z = condemned.next;
                condemned.next = null;
                if(condemned == head){
                    head = Z;
                }
            }
            /*
                    X-Y-Z -> X-Z
            * */
            if(prevExist && nextExist && X!=null && Z!=null){
                X.setNext(Z);
                Z.setPrev(X);
            }
            if(!prevExist && !nextExist){
                head = tail = null;
            }
            if(dllLength>0){
                dllLength--;
            }
        }


        private class dllNode{
            private Node value;
            private dllNode prev;
            private dllNode next;
            private dllNode connectedVertex;
            private dllNode(Node value){
                this.value = value;
            }
            private void setPrev(dllNode prev){
                this.prev = prev;
            }
            private void setNext(dllNode next){
                this.next = next;
            }
            private boolean isConnected(){
                return connectedVertex!=null;
            }
            private void connectToVertex(dllNode vertex){
                connectedVertex = vertex;
            }
            private dllNode getConnectedVertex(){
                return connectedVertex;
            }
            private Node getValue(){
                return value;
            }
        }
    }

    public static class maxHeap{}

    /**
     * for each node: uses a pseudo-random hash function to map the id to a DLl named hashedIds
     * Next, when the id have been mapped to a smaller array, hashedIds[index],
     * the smaller array will point to an object of type nodeMapper, which upon request will
     * provide either the graph implementation of node(a DLL of its neighbours in the graph),
     * or the maxHeap implementation(who is his parent and who are his children).
     */
    public static class MyHashtable{
        int p;
        int m;
        DoublyLinkedList[] hashedIds;
        private int a=-1;
        private int b=-1;
        public MyHashtable(int size, Node[] nodes){
            this.p=size;
            this.m = nodes.length;
            hashedIds = new DoublyLinkedList[p];
            setHashFunctionParameters(ThreadLocalRandom.current().nextInt(1,p),new Random().nextInt(p) );
            populateTable(nodes);
        }
        private void populateTable(Node[] nodes){
            for (Node node: nodes
                 ) {
                int identifier = node.id;
                int hashedId = hash(identifier);
                if(hashedIds[hashedId]==null){
                    hashedIds[hashedId] = new DoublyLinkedList();
                }
                hashedIds[hashedId].insert(node);
            }
        }
        private void setHashFunctionParameters(int a, int b){
            if(this.a==-1&& this.b == -1){
                this.a = a;
                this.b = b;
            }
        }
        private int hash(int id){
            return Math.floorMod(Math.floorMod((a*id + b),p),m);
        }
    }
}




