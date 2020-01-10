package pl.polsl.edge_bundling.controller;

import javafx.scene.shape.Line;
import pl.polsl.edge_bundling.model.DividedEdge;
import pl.polsl.edge_bundling.model.Edge;
import pl.polsl.edge_bundling.model.Vertex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controller {

    public Line verticesToLine(Vertex vertex1, Vertex vertex2) {
        return new Line(vertex1.getX(), vertex1.getY(), vertex2.getX(), vertex2.getY());
    }

    public Line edgeToLine(Edge edge) {
        return verticesToLine(edge.getStartingVertex(), edge.getEndingVertex());
    }

    public Set<Line> dividedEdgeToLine(DividedEdge dividedEdge) {
        Set<Line> lines = new HashSet<>();
        List<Vertex> divisionPoints = dividedEdge.getDivisionPoints();
        for (int i = 0; i < divisionPoints.size() - 1; i++) {
            lines.add(verticesToLine(divisionPoints.get(i), divisionPoints.get(i + 1)));
        }

        return lines;
    }

    public Set<Edge> resolveShortEdges(Set<Edge> edges, double threshold){
        Set<Edge> shortEdges = new HashSet<>();
        for(Edge edge : edges){
            if(edge.getLength() < threshold){
                shortEdges.add(edge);
            }
        }
        edges.removeAll(shortEdges);
        return shortEdges;
    }
}
