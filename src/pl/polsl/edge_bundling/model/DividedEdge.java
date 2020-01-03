package pl.polsl.edge_bundling.model;

import java.util.ArrayList;
import java.util.List;

public class DividedEdge extends Edge {

    private List<Vertex> divisionPoints = new ArrayList<>();

    public DividedEdge(Edge edge, int numberOfSegments) {
        Vertex startingVertex = edge.getStartingVertex();
        Vertex endingVertex = edge.getEndingVertex();
        double lengthOfEdge = startingVertex.distanceTo(endingVertex);
        int xLengthOfEdge = startingVertex.xDistanceTo(endingVertex);
        int yLengthOfEdge = startingVertex.yDistanceTo(endingVertex);
        int xSegmentLength = xLengthOfEdge/numberOfSegments; //todo Math.round?
        int ySegmentLength = yLengthOfEdge/numberOfSegments;

        for (int i = 0; i < numberOfSegments; i++) {
            divisionPoints.add(new Vertex(startingVertex.getX() + xSegmentLength, startingVertex.getY() + ySegmentLength));
        }
    }
}
