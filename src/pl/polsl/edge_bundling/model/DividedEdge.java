package pl.polsl.edge_bundling.model;

import java.util.ArrayList;
import java.util.List;

public class DividedEdge extends Edge {

    private List<Vertex> divisionPoints = new ArrayList<>();

    private double localSpringConstant;

    public DividedEdge(Edge edge, int numberOfSegments, double globalSpringConstant) { //todo edge as argument?
        super(edge.getStartingVertex(), edge.getEndingVertex());
        Vertex startingVertex = edge.getStartingVertex();
        Vertex endingVertex = edge.getEndingVertex();
        divisionPoints.add(startingVertex);

        int xLengthOfEdge = startingVertex.xDistanceTo(endingVertex);
        int yLengthOfEdge = startingVertex.yDistanceTo(endingVertex);
        int xSegmentLength = xLengthOfEdge / numberOfSegments; //todo Math.round?
        int ySegmentLength = yLengthOfEdge / numberOfSegments;

        for (int i = 0; i < numberOfSegments - 1; i++) {
            divisionPoints.add(new Vertex(startingVertex.getX() + (i + 1) * xSegmentLength,
                    startingVertex.getY() + (i + 1) * ySegmentLength));
        }
        divisionPoints.add(endingVertex);

        localSpringConstant = globalSpringConstant / (getLength() * numberOfSegments);
    }

    public List<Vertex> getDivisionPoints() {
        return divisionPoints;
    }

    public void setDivisionPoints(List<Vertex> divisionPoints) {
        this.divisionPoints = divisionPoints;
    }

    public double getLocalSpringConstant() {
        return localSpringConstant;
    }

    public void setLocalSpringConstant(double localSpringConstant) {
        this.localSpringConstant = localSpringConstant;
    }
}
