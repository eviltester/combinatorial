package uk.co.compendiumdev.allpairs.domain.graph;

import uk.co.compendiumdev.allpairs.domain.sparse.PairCombination;

import java.util.ArrayList;
import java.util.Comparator;
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

    public Node getLeastUsedNode() {
        return getLeastUsedNode(null);
    }

    public Node getLeastUsedNode(String ofName) {
        Node leastUsedNode = null;
        for(Node aNode : nodes){
            if(leastUsedNode == null || aNode.getWeighting() < leastUsedNode.getWeighting()){
                if(ofName != null && aNode.getName().equals(ofName)) {
                    leastUsedNode = aNode;
                }else{
                    if(ofName == null) {
                        leastUsedNode = aNode;
                    }
                }
            }

        }
        return leastUsedNode;
    }

    public List<Edge> getLeastUsedPathsFrom(Node aNode) {
        // get paths sorted in weighting order
        List<Edge> edgesToSort = new ArrayList<>();
        for(Edge anEdge : edges){
            if(anEdge.getLeft().matches(aNode.getName(), aNode.getValue())){
                edgesToSort.add(anEdge);
            }else{
                if(anEdge.getRight().matches(aNode.getName(), aNode.getValue())){
                    edgesToSort.add(anEdge);
                }
            }
        }

        // this should be a full path weighting or strategy
        Comparator<Edge> compareByUsage =
                Comparator.comparing((Edge p) -> p.getWeighting() - aNode.getWeighting());


        edgesToSort.sort(compareByUsage);
        return edgesToSort;
    }

    public void updateUsageForEdges(List<PairCombination> pairCombinations) {
        for(PairCombination pair : pairCombinations){
            Edge anEdge = getEdgeFor(pair);
            if(anEdge!=null){
                anEdge.incrementUsage();
            }
        }
    }

    public void updateUsageForNodes(List<PairCombination> pairCombinations) {
        for(PairCombination pair : pairCombinations){
            for(Node aNode : nodes){
                if(aNode.matches(pair.getRightName(), pair.getRightValue())){
                    aNode.incrementWeighting();
                }
                if(aNode.matches(pair.getLeftName(), pair.getLeftValue())){
                    aNode.incrementWeighting();
                }
            }
        }
    }

    public Edge getEdgeFor(PairCombination pair) {
        for(Edge anEdge : edges){
            if(anEdge.matches(pair)){
                return anEdge;
            }
        }
        return null;
    }
}
