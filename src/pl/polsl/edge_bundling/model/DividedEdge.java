package pl.polsl.edge_bundling.model;

import java.util.ArrayList;
import java.util.List;

public class DividedEdge extends Edge {

    private List<Vertex> divisionPoints = new ArrayList<>();

    private double localSpringConstant;

    public DividedEdge(List<Vertex> divisionPoints, double localSpringConstant) {
        this.divisionPoints = divisionPoints;
        this.localSpringConstant = localSpringConstant;
    }

    public DividedEdge(DividedEdge template) {
        this.setStartingVertex(new Vertex(template.getStartingVertex()));
        this.setEndingVertex(new Vertex(template.getEndingVertex()));
        this.localSpringConstant = template.getLocalSpringConstant();
        for (Vertex vertex : template.getDivisionPoints()) {
            this.divisionPoints.add(new Vertex(vertex));
        }
    }

    public DividedEdge(Vertex startingVertex, Vertex endingVertex, List<Vertex> divisionPoints, double localSpringConstant) {
        super(startingVertex, endingVertex);
        this.divisionPoints = divisionPoints;
        this.localSpringConstant = localSpringConstant;
    }

    public DividedEdge(Edge edge, int numberOfSegments, double globalSpringConstant) { //todo edge as argument?
        super(edge.getStartingVertex(), edge.getEndingVertex());
        Vertex startingVertex = edge.getStartingVertex();
        Vertex endingVertex = edge.getEndingVertex();
        divisionPoints.add(startingVertex);

        double xLengthOfEdge = startingVertex.xDistanceTo(endingVertex);
        double yLengthOfEdge = startingVertex.yDistanceTo(endingVertex);
        double xSegmentLength = xLengthOfEdge / numberOfSegments; //todo Math.round?
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
            Vertex previous = divisionPoints.get(i-1);
            Vertex current = divisionPoints.get(i);
            double y = (previous.getY() + current.getY())/2.0;
            double x = (previous.getX() + current.getX())/2.0;
            newDivisionPoints.add(new Vertex(x,y));
            newDivisionPoints.add(current);
        }
        this.divisionPoints = newDivisionPoints;
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
