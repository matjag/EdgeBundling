package pl.polsl.edge_bundling.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class EdgeBundlingGUI {

    private Set<Line> edges = new HashSet<>();

    public void start(Stage stage) {
        stage.setTitle("Edge Bundling");


//        Group group = new Group();
//        group.getChildren().addAll(edges);
//        Canvas canvas = new Canvas(2288, 1712);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        gc.setStroke(Color.RED);
//        gc.setFill(Color.YELLOW);
//        ScrollPane scrollPane = new ScrollPane(canvas);
//        scrollPane.setPrefSize(300, 300);
//        scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//        scrollPane.setFitToWidth(true);
//        scrollPane.setFitToHeight(true);
//        Scene scene = new Scene(scrollPane);
        File file = new File("data/animal-11/animal.jpg");

        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
//            Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
            Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.RED);
            gc.setFill(Color.YELLOW);
//            ScrollPane scrollPane = new ScrollPane(canvas);
//            scrollPane.setPrefSize(300, 300);
//            scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
//            scrollPane.setFitToWidth(true);
//            scrollPane.setFitToHeight(true);
            Pane pane = new Pane(canvas);
            pane.setPrefSize(500, 500);
//            ScrollPane pane = new ScrollPane(canvas);
            Scene scene = new Scene(pane);
            gc.drawImage(image, 0, 0);
            edges.forEach(edge -> {
//            gc.fillOval(edge.getStartX() - 2, edge.getStartY() - 2, 4, 4);
                gc.strokeLine(edge.getStartX(), edge.getStartY(), edge.getEndX(), edge.getEndY());
//            gc.fillOval(edge.getEndX() - 2, edge.getEndY() - 2, 4, 4);
            });
            stage.setMinWidth(100);
            stage.setMinHeight(100);
            this.zoom(pane);
//            stage.setScene(scene);
//            stage.show();

            SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
            Date date = new Date();
            File saveFile = new File("result/result" + formatter.format(date) + ".png");
                WritableImage wi = new WritableImage((int) image.getWidth(), (int) image.getHeight());
                try {
                    ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, wi), null), "png", saveFile);
                } catch (IOException e) {
                    e.printStackTrace();
                }
        } catch (FileNotFoundException e) {
            //todo
        }
    }

    public Set<Line> getEdges() {
        return edges;
    }

    public void setEdges(Set<Line> edges) {
        this.edges = edges;
    }


    private void zoom(Pane pane) {
        pane.setOnScroll(
                new EventHandler<ScrollEvent>() {
                    @Override
                    public void handle(ScrollEvent event) {
                        double zoomFactor = 1.05;
                        double deltaY = event.getDeltaY();

                        if (deltaY < 0) {
                            zoomFactor = 0.95;
                        }
                        pane.setScaleX(pane.getScaleX() * zoomFactor);
                        pane.setScaleY(pane.getScaleY() * zoomFactor);
                        event.consume();
                    }
                });

    }
}

