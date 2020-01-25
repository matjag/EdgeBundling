package pl.polsl.edge_bundling.model;

import java.util.ArrayList;
import java.util.List;

public class EdgeBundlingAlgorithm {

    private Force calculateSpringForce(DividedEdge dividedEdge, int vertexIndex) {
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

    private Force calculateElectrostaticForce(List<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        if (vertexIndex == 0 || vertexIndex == dividedEdge.getDivisionPoints().size()) {
            throw new IllegalArgumentException();//todo
        }
        Force force = new Force(0, 0);
        Vertex currentVertex = dividedEdge.getDivisionPoints().get(vertexIndex);

        dividedEdge.getCompatibleEdges().forEach(index -> {
            double xDistance = Math.round(currentVertex.xDistanceTo(dividedEdges.get(index).getDivisionPoints().get(vertexIndex)));
            double yDistance = Math.round(currentVertex.yDistanceTo(dividedEdges.get(index).getDivisionPoints().get(vertexIndex)));
            if (xDistance != 0) {
                force.setX(force.getX() + (1.0 / xDistance));
            }
            if (yDistance != 0) {
                force.setY(force.getY() + (1.0 / yDistance));
            }
        });

        return force;
    }

    private Force calculateForces(List<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex) {
        Force force = new Force(0, 0);
        force.combine(calculateSpringForce(dividedEdge, vertexIndex));
        force.combine(calculateElectrostaticForce(dividedEdges, dividedEdge, vertexIndex));
        return force;
    }

    public List<DividedEdge> iterate(List<DividedEdge> dividedEdges, double step) {
        List<DividedEdge> nextIteration = new ArrayList<>();
        int numberOfSegments = dividedEdges.get(0).getDivisionPoints().size() - 1;
        for (DividedEdge edge : dividedEdges) {
            nextIteration.add(new DividedEdge(edge));
        }

        for (int edgeIndex = 0; edgeIndex < dividedEdges.size(); edgeIndex++) {
//            List<Vertex> tmp = new ArrayList<>();
//            tmp.add(dividedEdges.get(edgeIndex).getStartingVertex());

            for (int vertexIndex = 1; vertexIndex < numberOfSegments; vertexIndex++) {
                applyForces(dividedEdges, dividedEdges.get(edgeIndex), vertexIndex, step);
//                tmp.add(applyForces(dividedEdges, dividedEdges.get(edgeIndex), vertexIndex));
            }
//            tmp.add(dividedEdges.get(edgeIndex).getEndingVertex());
//            nextIteration.get(edgeIndex).setDivisionPoints(tmp);
        }

        return dividedEdges;
    }


    private void applyForces(List<DividedEdge> dividedEdges, DividedEdge dividedEdge, int vertexIndex, double step) {
        Force force = calculateForces(dividedEdges, dividedEdge, vertexIndex);
        force.scale(step);
        dividedEdge.getDivisionPoints().get(vertexIndex).applyForce(force);
//        return dividedEdge.getDivisionPoints().get(vertexIndex).applyForce(force);
    }

    private double calcCompatibility(Edge edge1, Edge edge2) {
        return calcAngleCompatibility(edge1, edge2) *
                calcPositionCompatibility(edge1, edge2) *
                calcScaleCompatibility(edge1, edge2) *
                calcVisibilityCompatibility(edge1, edge2);
    }

    private double calcAngleCompatibility(Edge edge1, Edge edge2) {
        return Math.abs(scalarProduct(edge1, edge2) / (edge1.getLength() * edge2.getLength()));
    }

    private double calcScaleCompatibility(Edge edge1, Edge edge2) {
        double length1 = edge1.getLength();
        double length2 = edge2.getLength();
        double average = (length1 + length2) / 2.0;
        return 2.0 / (average / Math.min(length1, length2) + Math.max(length1, length2) / average);
    }

    private double calcPositionCompatibility(Edge edge1, Edge edge2) {
        double length1 = edge1.getLength();
        double length2 = edge2.getLength();
        double average = (length1 + length2) / 2.0;

        return average / (average + edge1.getMidpoint().distanceTo(edge2.getMidpoint()));
    }

    public void fillCompatibilities(List<DividedEdge> dividedEdges, double threshold) {
        for (DividedEdge dividedEdge : dividedEdges) {

            for (int edgeIndex = 0; edgeIndex < dividedEdges.size(); edgeIndex++) {
                if (calcCompatibility(dividedEdge, dividedEdges.get(edgeIndex)) >= threshold &&
                        dividedEdges.get(edgeIndex) != dividedEdge) {
                    dividedEdge.addCompatibleEdgeIndex(edgeIndex);
                }
            }
        }
    }

    private double calcVisibilityCompatibility(Edge edge1, Edge edge2) {
        return 1.0;//todo
    }


    private double scalarProduct(Edge edge1, Edge edge2) {
        return edge1.getXLength() * edge2.getXLength() + edge1.getYLength() * edge2.getYLength();
    }
}
