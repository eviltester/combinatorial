package uk.co.compendiumdev.allpairs.domain.graph;

import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.ArrayList;
import java.util.List;

public class Graph {

    List<Node> nodes;
    List<Edge> edges;

    public Graph(){
        nodes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Node createOrGetNode(String name, String value) {
        for(Node aNode : nodes){
            if(aNode.matches(name, value)){
                return aNode;
            }
        }

        Node aNewNode = new Node(name, value);
        nodes.add(aNewNode);
        return aNewNode;
    }

    public Edge createOrGetEdge(Node lnode, Node rnode) {
        for(Edge anEdge : edges){
            if(anEdge.isBetween(lnode, rnode)){
                return anEdge;
            }
        }
        Edge anEdge = new Edge(lnode, rnode);
        edges.add(anEdge);
        return anEdge;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
