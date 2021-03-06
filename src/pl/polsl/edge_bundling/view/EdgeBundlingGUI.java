package pl.polsl.edge_bundling.view;

import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Set;

public class EdgeBundlingGUI {

    private Set<Line> edges = new HashSet<>();

    public void start(Stage stage) {
        stage.setTitle("Edge Bundling");


//        Group group = new Group();
//        group.getChildren().addAll(edges);
        Canvas canvas = new Canvas(2288, 1712);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        ScrollPane scrollPane = new ScrollPane(canvas);
        scrollPane.setPrefSize(300, 300);
        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);
        Scene scene = new Scene(scrollPane);
        File file = new File("data/animal.jpg");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            gc.drawImage(new Image(fileInputStream), 0, 0);
        } catch (FileNotFoundException e) {
            //todo
        }
        edges.forEach(edge -> gc.strokeLine(edge.getStartX(), edge.getStartY(), edge.getEndX(), edge.getEndY()));
        stage.setMinWidth(100);
        stage.setMinHeight(100);
        stage.setScene(scene);
        stage.show();
    }

    public Set<Line> getEdges() {
        return edges;
    }

    public void setEdges(Set<Line> edges) {
        this.edges = edges;
    }
}
