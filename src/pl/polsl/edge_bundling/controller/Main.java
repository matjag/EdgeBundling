package pl.polsl.edge_bundling.controller;


import javafx.application.Application;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pl.polsl.edge_bundling.model.DataLoader;
import pl.polsl.edge_bundling.model.DividedEdge;
import pl.polsl.edge_bundling.model.Edge;
import pl.polsl.edge_bundling.model.EdgeBundlingAlgorithm;
import pl.polsl.edge_bundling.view.EdgeBundlingGUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Main extends Application {
    private final EdgeBundlingGUI edgeBundlingGUI = new EdgeBundlingGUI();
    private final Controller controller = new Controller();
    private final EdgeBundlingAlgorithm algorithm = new EdgeBundlingAlgorithm(0.2, 5, 30, 0.1);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        DataLoader dataLoader = new DataLoader();
        Set<Edge> edges = new HashSet<>();
        List<DividedEdge> dividedEdges = new ArrayList<>();
        Set<Line> lines = new HashSet<>();

        for (int i = 10; i < 66; i++) {
            edges.addAll(dataLoader.loadFromCsv("data/animal-11/A" + i + ".csv"));
        }
////
//        edges.add(new Edge(new Vertex(100, 200), new Vertex(1000, 200)));
//        edges.add(new Edge(new Vertex(200, 220), new Vertex(1100, 220)));
//        edges.add(new Edge(new Vertex(100, 100), new Vertex(1000, 500)));
//
//        edges.add(new Edge(new Vertex(100, 200), new Vertex(1000, 200)));
//        edges.add(new Edge(new Vertex(200, 250), new Vertex(1100, 250)));
//        edges.add(new Edge(new Vertex(100, 300), new Vertex(1000, 300)));
//        edges.add(new Edge(new Vertex(200, 350), new Vertex(1100, 150)));
//        edges.add(new Edge(new Vertex(100, 400), new Vertex(1000, 100)));
//        edges.add(new Edge(new Vertex(200, 550), new Vertex(1100, 150)));

//        edges.addAll(dataLoader.loadFromCsv("data/processed.csv"));


        System.out.println("Total number of edges:\t" + edges.size());

        Set<Edge> shortEdges = controller.resolveShortEdges(edges, algorithm.getNumberOfSegments());

        System.out.println("Resolving short edges:\t" + shortEdges.size());
        System.out.println("Edges left:\t" + edges.size());
        System.out.println("Bundling...");
        long startTime = System.nanoTime();

        for (Edge edge : edges) {
            dividedEdges.add(new DividedEdge(edge, 2, algorithm.getSpringConstant()));
        }


        int I = 50;
        int C = 6;

        algorithm.fillCompatibilities(dividedEdges, 0.2);

        for (int cycle = 0; cycle < C; cycle++) {
            for (int i = 0; i < I; i++) {
                dividedEdges = algorithm.iterate(dividedEdges);
            }
            dividedEdges.forEach(DividedEdge::doubleDivisionPoints);
            I *= 0.66;
            algorithm.setInitialStep(algorithm.getInitialStep() * 0.5);
        }


        dividedEdges.forEach(edge -> lines.addAll(controller.dividedEdgeToLine(edge)));
        shortEdges.forEach(edge -> lines.add(controller.edgeToLine(edge)));


        edgeBundlingGUI.setEdges(lines);
        edgeBundlingGUI.start(stage);

        long endTime = System.nanoTime();
        System.out.println("Time elapsed:\t" + (endTime - startTime) / 1000000000.0);

    }
}
