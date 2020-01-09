package pl.polsl.edge_bundling.controller;


import javafx.application.Application;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pl.polsl.edge_bundling.model.*;
import pl.polsl.edge_bundling.view.EdgeBundlingGUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class Main extends Application {
    private final EdgeBundlingGUI edgeBundlingGUI = new EdgeBundlingGUI();
    private final Controller controller = new Controller();
    private final EdgeBundlingAlgorithm algorithm = new EdgeBundlingAlgorithm(10, 50, 4, 10);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DataLoader dataLoader = new DataLoader();
//        List<Vertex> list = dataLoader.loadFromCsv("data/A10.csv"); todo
        Set<Edge> edges = new HashSet<>();
        List<DividedEdge> dividedEdges = new ArrayList<>();
        Set<Line> lines = new HashSet<>();

//        for (int i = 10; i < 25; i++) {
//            edges.addAll(dataLoader.loadFromCsv("data/A" + i + ".csv"));
//        }
//
        edges.add(new Edge(new Vertex(100, 100), new Vertex(1000, 100)));
        edges.add(new Edge(new Vertex(200, 150), new Vertex(1100, 150)));

        edges.removeIf(edge -> edge.getLength() < 7.5);//todo

        for(Edge edge : edges){
            dividedEdges.add(new DividedEdge(edge, algorithm.getNumberOfSegments(), algorithm.getSpringConstant()));

        }

        for (int i = 0; i < algorithm.getNumberOfIterations(); i++) {
            dividedEdges = algorithm.iterate(dividedEdges);
        }

//        for (int i = 0; i < algorithm.getNumberOfIterations(); i++) {
//            List<DividedEdge> current = dividedEdges.stream().collect(toList());
//
//            for (int edgeIndex = 0; edgeIndex<dividedEdges.size(); edgeIndex++) {
//                List<Vertex> tmp = new ArrayList<>();
//                tmp.add(current.get(edgeIndex).getStartingVertex());
//
//                for (int vertexIndex = 1; vertexIndex < algorithm.getNumberOfSegments(); vertexIndex++) {
//                    tmp.add(algorithm.applyForces(current, current.get(edgeIndex), vertexIndex));
//                }
//                tmp.add(current.get(edgeIndex).getEndingVertex());
//                dividedEdges.get(edgeIndex).setDivisionPoints(tmp);
//            }
//        }


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
