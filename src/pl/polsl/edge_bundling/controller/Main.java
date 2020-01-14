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

public class Main extends Application {
    private final EdgeBundlingGUI edgeBundlingGUI = new EdgeBundlingGUI();
    private final Controller controller = new Controller();
    private final EdgeBundlingAlgorithm algorithm = new EdgeBundlingAlgorithm(0.1, 5, 30, 0.1);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DataLoader dataLoader = new DataLoader();
        Set<Edge> edges = new HashSet<>();
        List<DividedEdge> dividedEdges = new ArrayList<>();
        Set<Line> lines = new HashSet<>();

        for (int i = 10; i < 40; i++) {
            edges.addAll(dataLoader.loadFromCsv("data/animal-11/A" + i + ".csv"));
        }
////
//        edges.add(new Edge(new Vertex(100, 1600), new Vertex(1000, 1600)));
//        edges.add(new Edge(new Vertex(200, 1650), new Vertex(1100, 1650)));
//        edges.add(new Edge(new Vertex(100, 100), new Vertex(1000, 500)));
//
//        edges.add(new Edge(new Vertex(100, 200), new Vertex(1000, 200)));
//        edges.add(new Edge(new Vertex(200, 250), new Vertex(1100, 250)));
//        edges.add(new Edge(new Vertex(100, 300), new Vertex(1000, 300)));
//        edges.add(new Edge(new Vertex(200, 350), new Vertex(1100, 150)));
//        edges.add(new Edge(new Vertex(100, 400), new Vertex(1000, 100)));
//        edges.add(new Edge(new Vertex(200, 550), new Vertex(1100, 150)));

//        edges.addAll(dataLoader.loadFromCsv("data/test.csv"));
//        Set<Vertex> vertices = new HashSet<>();
//
//        edges.forEach(edge -> {
//            vertices.add(edge.getStartingVertex());
//            vertices.add(edge.getEndingVertex());
//        });

//        vertices.forEach(vertex ->{
//            vertices.forEach(vertex1 -> {
//                if(vertex.distanceTo(vertex1) < 4){
//                    double y =( vertex.getY() + vertex1.getY()) /2.0;
//                    double x =( vertex.getX() + vertex1.getX()) /2.0;
//                    vertex.setX(x);
//                    vertex.setY(y);
//                    vertex1.setX(x);
//                    vertex1.setY(y);
//                }
//            });
//        });

        System.out.println("Total number of edges:\t" + edges.size());

        Set<Edge> shortEdges = controller.resolveShortEdges(edges, algorithm.getNumberOfSegments());

        System.out.println("Resolving short edges:\t" + shortEdges.size());
        System.out.println("Edges left:\t" + edges.size());
        System.out.println("Bundling...");
        long startTime = System.nanoTime();

        for (Edge edge : edges) {
            dividedEdges.add(new DividedEdge(edge, algorithm.getNumberOfSegments(), algorithm.getSpringConstant()));
        }


        int I = 50;
        int C = 6;

        algorithm.fillCompatibilities(dividedEdges, 0.4);

        for (int mateusz = 0; mateusz < C; mateusz++) {
            for (int i = 0; i < I; i++) {
                dividedEdges = algorithm.iterate(dividedEdges);
            }
            dividedEdges.forEach(DividedEdge::doubleDivisionPoints);
            I *= 0.66;
            algorithm.setInitialStep(algorithm.getInitialStep()*0.5);
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
        shortEdges.forEach(edge -> lines.add(controller.edgeToLine(edge)));


        edgeBundlingGUI.setEdges(lines);
        edgeBundlingGUI.start(stage);

        long endTime = System.nanoTime();
        System.out.println("Time elapsed:\t" + (endTime - startTime)/1000000000.0);

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
