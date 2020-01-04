package pl.polsl.edge_bundling.model;

import java.util.Set;

public class EdgeBundlingAlgorithm {

    private Set<DividedEdge> edges;

    private int initialStep;

    private int numberOfIterations;

    private int numberOfSegments;

    private double springConstant;

    public EdgeBundlingAlgorithm(Set<DividedEdge> edges, int initialStep, int numberOfIterations, int numberOfSegments, double springConstant) {
        this.edges = edges;
        this.initialStep = initialStep;
        this.numberOfIterations = numberOfIterations;
        this.numberOfSegments = numberOfSegments;
        this.springConstant = springConstant;
    }

    public EdgeBundlingAlgorithm() {
    }
//    public Force calculateSpringForce(DividedEdge dividedEdge, int vertexIndex) {
//        List<Vertex> vertices = dividedEdge.getDivisionPoints();
//        Vertex previousVertex = vertices.get(vertexIndex - 1);
//        Vertex currentVertex = vertices.get(vertexIndex);
//        Vertex nextVertex = vertices.get(vertexIndex + 1);
//    }
}
