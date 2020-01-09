package pl.polsl.edge_bundling.model;

import java.util.ArrayList;
import java.util.List;

public class EdgeBundlingAlgorithm {

    private double initialStep;

    private int numberOfIterations;

    private int numberOfSegments;

    private double springConstant;

    public EdgeBundlingAlgorithm(double initialStep, int numberOfIterations, int numberOfSegments, double springConstant) {
        this.initialStep = initialStep;
        this.numberOfIterations = numberOfIterations;
        this.numberOfSegments = numberOfSegments;
        this.springConstant = springConstant;
    }

    public Force calculateSpringForce(DividedEdge dividedEdge, int vertexIndex) {
        List<Vertex> vertices = dividedEdge.getDivisionPoints();
        Vertex previousVertex = vertices.get(vertexIndex - 1);
        Vertex currentVertex = vertices.get(vertexIndex);
        Vertex nextVertex = vertices.get(vertexIndex + 1);
        double x = (previousVertex.getX() - currentVertex.getX()) + (nextVertex.getX() - currentVertex.getX());
        double y = (previousVertex.getY() - currentVertex.getY()) + (nextVertex.getY() - currentVertex.getY());
        x *= dividedEdge.getLocalSpringConstant();
        y *= dividedEdge.getLocalSpringConstant();
        return new Force(x, y);
    }

    public Force calculateElectrostaticForce(List<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        if (vertexIndex == 0 || vertexIndex == dividedEdge.getDivisionPoints().size()) {
            throw new IllegalArgumentException();//todo
        }
        Force force = new Force(0, 0);
        Vertex currentVertex = dividedEdge.getDivisionPoints().get(vertexIndex);
        dividedEdges.forEach(edge -> {
            if (edge != dividedEdge) {//todo potential bottleneck
                double xDistance = currentVertex.xDistanceTo(edge.getDivisionPoints().get(vertexIndex));
                double yDistance = currentVertex.yDistanceTo(edge.getDivisionPoints().get(vertexIndex));
                if (xDistance != 0) {
                    force.setX(force.getX() + (1.0 / xDistance));
                }
                if (yDistance != 0) {
                    force.setY(force.getY() + (1.0 / yDistance));
                }
            }
        });

        return force;
    }

    public Force calculateForces(List<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        Force force = new Force(0, 0);
//        force.combine(calculateSpringForce(dividedEdge, vertexIndex));
        force.combine(calculateElectrostaticForce(dividedEdges, dividedEdge, vertexIndex));
        return force;
    }

    public List<DividedEdge> iterate(List<DividedEdge> dividedEdges) {
        List<DividedEdge> nextIteration = new ArrayList<>();
        int numberOfSegments = dividedEdges.get(0).getDivisionPoints().size() - 1;
        for (DividedEdge edge : dividedEdges) {
            nextIteration.add(new DividedEdge(edge));
        }

        for (int edgeIndex = 0; edgeIndex < dividedEdges.size(); edgeIndex++) {
            List<Vertex> tmp = new ArrayList<>();
            tmp.add(dividedEdges.get(edgeIndex).getStartingVertex());

            for (int vertexIndex = 1; vertexIndex < numberOfSegments; vertexIndex++) {
                tmp.add(applyForces(dividedEdges, dividedEdges.get(edgeIndex), vertexIndex));
            }
            tmp.add(dividedEdges.get(edgeIndex).getEndingVertex());
            nextIteration.get(edgeIndex).setDivisionPoints(tmp);
        }

        return nextIteration;
    }


    public Vertex applyForces(List<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        Force force = calculateForces(dividedEdges, dividedEdge, vertexIndex);
        force.scale(initialStep);
        return dividedEdge.getDivisionPoints().get(vertexIndex).applyForce(force);
    }


    public double getInitialStep() {
        return initialStep;
    }

    public void setInitialStep(double initialStep) {
        this.initialStep = initialStep;
    }

    public int getNumberOfIterations() {
        return numberOfIterations;
    }

    public void setNumberOfIterations(int numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    public int getNumberOfSegments() {
        return numberOfSegments;
    }

    public void setNumberOfSegments(int numberOfSegments) {
        this.numberOfSegments = numberOfSegments;
    }

    public double getSpringConstant() {
        return springConstant;
    }

    public void setSpringConstant(double springConstant) {
        this.springConstant = springConstant;
    }
}
