package pl.polsl.edge_bundling.controller;

import javafx.scene.shape.Line;
import pl.polsl.edge_bundling.model.Vertex;

public class Controller {

    public Line verticesToLine(Vertex vertex1, Vertex vertex2){
        return new Line(vertex1.getX(), vertex1.getY(), vertex2.getX(), vertex2.getY());
    }
}
