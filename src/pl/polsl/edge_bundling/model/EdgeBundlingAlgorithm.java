package pl.polsl.edge_bundling.model;

import java.util.Set;

public class EdgeBundlingAlgorithm {

    private Set<DividedEdge> edges;

    private int initialStep;

    private int numberOfIterations;

    private int numberOfSegments;

    public EdgeBundlingAlgorithm(Set<DividedEdge> edges, int initialStep, int numberOfIterations, int numberOfSegments) {
        this.edges = edges;
        this.initialStep = initialStep;
        this.numberOfIterations = numberOfIterations;
        this.numberOfSegments = numberOfSegments;
    }


}
