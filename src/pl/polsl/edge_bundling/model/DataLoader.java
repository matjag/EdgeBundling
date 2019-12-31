package pl.polsl.edge_bundling.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DataLoader {

    public List<Vertex> loadFromCsv(String filename) {
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
        Collections.sort(vertices);

        return vertices;
    }
}
