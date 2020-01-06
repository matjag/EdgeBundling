package pl.polsl.edge_bundling.model;

import java.util.List;
import java.util.Set;

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

    public EdgeBundlingAlgorithm() {
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

    public Force calculateElectrostaticForce(Set<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        if (vertexIndex == 0 || vertexIndex == dividedEdge.getDivisionPoints().size()) {
            throw new IllegalArgumentException();//todo
        }
        Force force = new Force(0, 0);
        Vertex currentVertex = dividedEdge.getDivisionPoints().get(vertexIndex);
        dividedEdges.forEach(edge -> {
            if (edge != dividedEdge) {//todo potential bottleneck
                int xDistance = currentVertex.getX() - edge.getDivisionPoints().get(vertexIndex).getX();
                int yDistance = currentVertex.getY() - edge.getDivisionPoints().get(vertexIndex).getY();
                if(xDistance != 0 && yDistance != 0) {
                    force.setX(force.getX() + 1.0 / (xDistance));
                    force.setY(force.getY() + 1.0 / (yDistance));
                }
            }
        });

        return force;
    }

    public Force calculateForces(Set<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        Force force = new Force(0, 0);
        force.combine(calculateSpringForce(dividedEdge, vertexIndex));
        force.combine(calculateElectrostaticForce(dividedEdges, dividedEdge, vertexIndex));
        return force;
    }

    public void applyForces(Set<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        Force force = calculateForces(dividedEdges, dividedEdge, vertexIndex);
        force.scale(initialStep);
        dividedEdge.getDivisionPoints().get(vertexIndex).applyForce(force);
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
