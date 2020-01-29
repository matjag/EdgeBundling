package pl.polsl.edge_bundling.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataLoader {

    public Set<Edge> loadFromCsv(String filename, int xMax, int yMax) {
        List<Vertex> vertices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String entry;
            while ((entry = br.readLine()) != null) {
                vertices.add(new Vertex(entry.split(",")));
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        vertices.removeIf(vertex -> vertex.getY() <= 0 || vertex.getX() <= 0 || vertex.getX() > xMax || vertex.getY() > yMax);
        Collections.sort(vertices);

        return verticesToEdges(vertices);
    }

    private Set<Edge> verticesToEdges(List<Vertex> vertices) {
        Set<Edge> edges = new HashSet<>();
        for (int i = 0; i < vertices.size() - 1; i++) {
            edges.add(new Edge(vertices.get(i), vertices.get(i + 1)));
        }
        return edges;
    }
}
