package pl.polsl.edge_bundling.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class DataLoader {

    public Set<Edge> loadFromCsv(String filename) {
        List<Vertex> vertices = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String entry;
            while ((entry = br.readLine()) != null) {
                vertices.add(new Vertex(entry.split(",")));
            }
        } catch (IOException e) {
            //Some error logging todo
        }

        vertices.removeIf(vertex -> vertex.getY() < 0 || vertex.getX() < 0);
        Collections.sort(vertices);//todo needed?

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
