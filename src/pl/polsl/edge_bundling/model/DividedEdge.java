package pl.polsl.edge_bundling.model;

import java.util.ArrayList;
import java.util.List;

public class DividedEdge extends Edge {

    private List<Vertex> divisionPoints = new ArrayList<>();

    private List<Integer> compatibleEdges = new ArrayList<>();

    private double localSpringConstant;

    void addCompatibleEdgeIndex(int index) {
        compatibleEdges.add(index);
    }

    List<Integer> getCompatibleEdges() {
        return compatibleEdges;
    }

    DividedEdge(DividedEdge template) {
        this.setStartingVertex(new Vertex(template.getStartingVertex()));
        this.setEndingVertex(new Vertex(template.getEndingVertex()));
        this.localSpringConstant = template.getLocalSpringConstant();
        this.compatibleEdges = template.getCompatibleEdges();
        for (Vertex vertex : template.getDivisionPoints()) {
            this.divisionPoints.add(new Vertex(vertex));
        }
    }

    public DividedEdge(Edge edge, int numberOfSegments, double globalSpringConstant) {
        super(edge.getStartingVertex(), edge.getEndingVertex());
        Vertex startingVertex = edge.getStartingVertex();
        Vertex endingVertex = edge.getEndingVertex();
        divisionPoints.add(startingVertex);

        double xLengthOfEdge = startingVertex.xDistanceTo(endingVertex);
        double yLengthOfEdge = startingVertex.yDistanceTo(endingVertex);
        double xSegmentLength = xLengthOfEdge / numberOfSegments;
        double ySegmentLength = yLengthOfEdge / numberOfSegments;

        for (int i = 0; i < numberOfSegments - 1; i++) {
            divisionPoints.add(new Vertex(startingVertex.getX() + (i + 1) * xSegmentLength,
                    startingVertex.getY() + (i + 1) * ySegmentLength));
        }
        divisionPoints.add(endingVertex);

        localSpringConstant = globalSpringConstant / (getLength() * numberOfSegments);
    }

    public void doubleDivisionPoints() {
        List<Vertex> newDivisionPoints = new ArrayList<>();
        newDivisionPoints.add(getStartingVertex());
        for (int i = 1; i < divisionPoints.size(); i++) {
            Vertex previous = divisionPoints.get(i - 1);
            Vertex current = divisionPoints.get(i);
            double y = (previous.getY() + current.getY()) / 2.0;
            double x = (previous.getX() + current.getX()) / 2.0;
            newDivisionPoints.add(new Vertex(x, y));
            newDivisionPoints.add(current);
        }
        this.divisionPoints = newDivisionPoints;
    }

    public List<Vertex> getDivisionPoints() {
        return divisionPoints;
    }

    double getLocalSpringConstant() {
        return localSpringConstant;
    }
}
