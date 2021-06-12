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
    private static String ADD = "ADD";
    private static String REMOVE = "REMOVE";
    MyHashtable myTable;
    MaxHeap myMaxHeap;
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
        myMaxHeap = new MaxHeap(N,nodes);
    }



    /**
     * This method returns the node in the graph with the maximum neighborhood weight.
     * Note: nodes that have been removed from the graph using deleteNode are no longer in the graph.
     * @return a Node object representing the correct node. If there is no node in the graph, returns 'null'.
     */
    public Node maxNeighborhoodWeight(){
        if (myMaxHeap.heapArray[0]!=null){
            return myMaxHeap.heapArray[0];
        }
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
        Node n = myTable.accessNode(node_id);
        if(n!=null){
            return n.neighbourhoodWeight;
        }
        return -1;
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
        if(node1_id==node2_id){
            return false;
        }
        Node n1 = myTable.accessNode(node1_id);
        Node n2 = myTable.accessNode(node2_id);
        if(n1==null || n2==null){
            return false;
        }
        n1.addNeighbour(new Neighbour(n2));
        myMaxHeap.heapifyUp(n1);
        myMaxHeap.heapifyUp(n2);
        m++;
        return true;
    }



    /**
     * Given the id of a node in the graph, deletes the node of that id from the graph, if it exists.
     *
     * @param node_id - the id of the node to delete.
     * @return returns 'true' if the function deleted a node, otherwise returns 'false'
     */
    public boolean deleteNode(int node_id){
        Node condemned = myTable.accessNode(node_id);
        if(condemned == null){
            return false;
        }
        int edgesRemoved = condemned.disassociateFromNeighbours();
        myTable.hashedIds[myTable.hash(node_id)].delete(condemned.dllRef);
        n--;
        m-=edgesRemoved;
        myMaxHeap.delete(condemned);
        return true;
    }
	
	/**
	 * Returns the number of nodes currently in the graph.
	 * @return the number of nodes in the graph.
	 */
	public int getNumNodes(){
		return n;
	}
	
	/**
	 * @return the number of edges currently in the graph.
	 */
	public int getNumEdges(){
		return m;
	}

    /**
     * Neighbour signifies a node within another node's neighbourhood DoublyLinkedList.
     * Used for having a direct connection between 2 vertices u,v who are connected via an edge e,
     * i.e: e=(u,v) ∈ E
     */
	public static class Neighbour extends Node{
	    private Neighbour connectedVertex = null;
	    public Node origialNodeRef = null;
	    public Neighbour(Node node){
	        super(node.id, node.weight);
	        origialNodeRef = node;
        }
        public void connect(Node node){
	        Neighbour otherVertex = new Neighbour(node);
	        connectedVertex = otherVertex;
	        otherVertex.connectedVertex = this;
	        origialNodeRef.neighbourhood.insert(connectedVertex);
        }

    }

    /**
     * This class represents a node in the graph.
     */
    public static class Node{
        private int id;
        private int weight;
        private int neighbourhoodWeight;
        protected DoublyLinkedList neighbourhood;
        private int heapIndex;
        DoublyLinkedList.dllNode dllRef;
        /**
         * Creates a new node object, given its id and its weight.
         * @param id - the id of the node.
         * @param weight - the weight of the node.
         */
        public Node(int id, int weight){
            this.id = id;
            this.weight = weight;
            this.neighbourhoodWeight = weight;
            this.neighbourhood = new DoublyLinkedList();
        }
        protected void addNeighbour(Neighbour buddy){
            neighbourhood.insert(buddy);
            buddy.connect(this);
            updateNeighbourhoodWeight(ADD,buddy);
            buddy.origialNodeRef.updateNeighbourhoodWeight(ADD,this);
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

        /**
         * Called when an edge e = (u,v) is added. Updates both node's neighbourhood weight value.
         * @param op indicates if changed occurs due to removal of a node from the graph(REMOVE),
         *           or if a new edge was created(ADD).
         * @param nodeWeightToAddOrRemove - self explanatory
         */
        protected void updateNeighbourhoodWeight(String op,Node nodeWeightToAddOrRemove){
            if(op==REMOVE){
                this.neighbourhoodWeight -= nodeWeightToAddOrRemove.getWeight();
            }
            if(op==ADD){
                this.neighbourhoodWeight += nodeWeightToAddOrRemove.getWeight();
            }
        }
        public int disassociateFromNeighbours(){
            if(neighbourhood.tail == null){
                return 0;
            }
            DoublyLinkedList.dllNode doorToDoor = neighbourhood.tail;
            Neighbour doorVal = (Neighbour) doorToDoor.value;
            int counter = 0;
            while(doorToDoor != null ){
                doorVal = (Neighbour) doorToDoor.value;
                doorVal.origialNodeRef.updateNeighbourhoodWeight(REMOVE,this);
                doorVal.origialNodeRef.neighbourhood.delete(dllRef);
                doorToDoor = doorToDoor.next;
                counter++;
            }
            return counter;
        }

    }

    public static class DoublyLinkedList{
        private dllNode head;
        private dllNode tail;
        private int dllLength = 0;

        public int getDllLength(){
            return dllLength;
        }

        public Node retrieveNode(int id){
            dllNode cursor = tail;
            while(cursor!=null){
                if(cursor.value!=null && cursor.value.id==id){
                    return cursor.value;
                }
                cursor = cursor.next;
            }
            return null;
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
            /**
             * Will be used both for deleting a node from the graph, and to delete it from all of
             * its neighbour's adjacency lists.
             */

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


        public class dllNode{
            private Node value;
            private dllNode prev;
            private dllNode next;
            private dllNode connectedVertex;
            private dllNode(Node value){
                this.value = value;
                value.dllRef = this;//TODO: make sure is ok
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

    public static class MaxHeap{
        private int N;
        private Node[] heapArray;
        private int lastIndex;

        public MaxHeap(int N,Node[] nodes){
            this.N = N;
            heapArray = new Node[N];
            this.lastIndex = heapArray.length-1;
            initializeHeap(nodes);
        }
        //will be used only for first insertion
        //TODO
        private void initializeHeap(Node[] nodes){
            int i = 0;
            while (i<N){
                heapArray[i] = nodes[i];
                nodes[i].heapIndex = i;
                heapifyUp(heapArray[i]);
                i++;
            }
            setHeapIndexes();
        }

        private void findMaxAndSwap(){
            Node tempMaxNode = heapArray[0];
            int tempMax = tempMaxNode.neighbourhoodWeight;
            int tempMaxIndex = 0;
            for(int i =1; i< heapArray.length; i++){
                if(heapArray[i].neighbourhoodWeight > tempMax){
                    tempMax = heapArray[i].neighbourhoodWeight;
                    tempMaxIndex = i;
                }
            }
            Node actualMaxNode = heapArray[tempMaxIndex];
            heapArray[0].heapIndex = tempMaxIndex;
            heapArray[tempMaxIndex] = heapArray[0];
            actualMaxNode.heapIndex = 0;
            heapArray[0] = actualMaxNode;
        }

        private void setHeapIndexes(){
            for(int i =0; i< heapArray.length; i++){
                heapArray[i].heapIndex = i;
            }
        }

        public void delete(Node tBD){
            heapArray[lastIndex].heapIndex = tBD.heapIndex;
            heapArray[tBD.heapIndex] = heapArray[lastIndex];
            heapArray[lastIndex] = null;
            lastIndex--;
            heapifyDown(heapArray[tBD.heapIndex]);
        }


        public void heapifyUp(Node changedNode){
            int index = changedNode.heapIndex;
            Node parent = heapArray[(index-1)/2];
            while (parent.neighbourhoodWeight < changedNode.neighbourhoodWeight){
                int parentIndex = parent.heapIndex;
                index = changedNode.heapIndex;
                changedNode.heapIndex = parentIndex;
                parent.heapIndex = index;
                heapArray[index] = parent;
                heapArray[parentIndex] = changedNode;
                parentIndex = (parentIndex-1)/2;
                parent = heapArray[parentIndex];
            }
        }
        public void heapifyDown(Node changedNode){
            int index = changedNode.heapIndex;
            Node highestNW = changedNode;
            int rightSonIndex = (index*2) + 2;
            int leftSonIndex = (index*2) + 1;
            if( rightSonIndex < lastIndex){
                if(highestNW.neighbourhoodWeight < heapArray[rightSonIndex].neighbourhoodWeight){
                    highestNW = heapArray[rightSonIndex];
                    /**
                     * in this case, the right son exist and his neighborhoodWeight is larger then changedNode
                     */
                }
            }
            if(leftSonIndex < lastIndex){
                if(highestNW.neighbourhoodWeight < heapArray[leftSonIndex].neighbourhoodWeight){
                    highestNW = heapArray[leftSonIndex];
                    /**
                     * in this case, the left son exist and his neighborhoodWeight is larger then changedNode AND rightSon
                     */
                }
            }
            if(changedNode!=highestNW){
                int newPlaceForChangedNode = highestNW.heapIndex;
                int oldPlaceOfChangedNode = changedNode.heapIndex;
                changedNode.heapIndex = newPlaceForChangedNode;
                heapArray[newPlaceForChangedNode] = changedNode;
                highestNW.heapIndex = oldPlaceOfChangedNode;
                heapArray[oldPlaceOfChangedNode] = highestNW;
                heapifyDown(changedNode);
            }
        }
    }

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
            hashedIds = new DoublyLinkedList[m];
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

        /**
         * Given
         * @param id
         * @return the instance of Node with Node.id == id
         */
        public Node accessNode(int id){
            DoublyLinkedList relevantDll = hashedIds[hash(id)];
            if(relevantDll!=null){
                return relevantDll.retrieveNode(id);
            }
            return null;
        }
    }
}




