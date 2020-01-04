package pl.polsl.edge_bundling.controller;


import javafx.application.Application;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pl.polsl.edge_bundling.model.DataLoader;
import pl.polsl.edge_bundling.model.DividedEdge;
import pl.polsl.edge_bundling.model.Edge;
import pl.polsl.edge_bundling.model.EdgeBundlingAlgorithm;
import pl.polsl.edge_bundling.view.EdgeBundlingGUI;

import java.util.HashSet;
import java.util.Set;

public class Main extends Application {
    private EdgeBundlingGUI edgeBundlingGUI = new EdgeBundlingGUI();
    private Controller controller = new Controller();
    private final EdgeBundlingAlgorithm edgeBundlingAlgorithm = new EdgeBundlingAlgorithm();

    public static void main(String[] args) {
        launch(args);
    }


    @Override
    public void start(Stage stage) {
        DataLoader dataLoader = new DataLoader();
//        List<Vertex> list = dataLoader.loadFromCsv("data/A10.csv"); todo
        Set<Edge> edges = new HashSet<>();
        Set<DividedEdge> dividedEdges = new HashSet<>();
        Set<Line> lines = new HashSet<>();
//        for (int i = 10; i < 67; i++) {
//            edges.addAll(dataLoader.loadFromCsv("data/A" + i + ".csv"));
//        }
        edges.addAll(dataLoader.loadFromCsv("data/test.csv"));

        edges.forEach(edge -> dividedEdges.add(new DividedEdge(edge, 5, 1)));

        dividedEdges.forEach(edge -> lines.addAll(controller.dividedEdgeToLine(edge)));

        edgeBundlingGUI.setEdges(lines);
        edgeBundlingGUI.start(stage);
    }
}
//    @Override
//    public void start(Stage primaryStage) {
//        primaryStage.setTitle("Drawing Operations Test");
//        Group root = new Group();
//        Canvas canvas = new Canvas(2288, 171);
//        GraphicsContext gc = canvas.getGraphicsContext2D();
//        drawShapes(gc);
////        root.getChildren().add(canvas);
//        ScrollPane scrollPane = new ScrollPane();
//        scrollPane.setContent(canvas);
//        root.getChildren().add(scrollPane);
//        primaryStage.setScene(new Scene(root, 400, 300));
//        primaryStage.show();
//    }
//
//    private void drawShapes(GraphicsContext gc) {
//        gc.setFill(Color.GREEN);
//        gc.setStroke(Color.BLUE);
//        gc.setLineWidth(1);
//        gc.strokeLine(40, 10, 10, 40);
//        gc.fillOval(10, 60, 30, 30);
//        gc.strokeOval(60, 60, 30, 30);
//        gc.fillRoundRect(110, 60, 30, 30, 10, 10);
//        gc.strokeRoundRect(160, 60, 30, 30, 10, 10);
//        gc.fillArc(10, 110, 30, 30, 45, 240, ArcType.OPEN);
//        gc.fillArc(60, 110, 30, 30, 45, 240, ArcType.CHORD);
//        gc.fillArc(110, 110, 30, 30, 45, 240, ArcType.ROUND);
//        gc.strokeArc(10, 160, 30, 30, 45, 240, ArcType.OPEN);
//        gc.strokeArc(60, 160, 30, 30, 45, 240, ArcType.CHORD);
//        gc.strokeArc(110, 160, 30, 30, 45, 240, ArcType.ROUND);
//        gc.fillPolygon(new double[]{10, 40, 10, 40},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolygon(new double[]{60, 90, 60, 90},
//                new double[]{210, 210, 240, 240}, 4);
//        gc.strokePolyline(new double[]{110, 140, 110, 140},
//                new double[]{210, 210, 240, 240}, 4);
//    }
//}

//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;

//public class Main extends Application {

//    @Override
//    public void start(Stage primaryStage) throws Exception{
//        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
//        primaryStage.setTitle("Hello World");
//        primaryStage.setScene(new Scene(root, 400, 300));
//        primaryStage.show();
//    }
//
//
//    public static void main(String[] args) {
//        launch(args);
//    }
//}
