package pl.polsl.edge_bundling.controller;


import javafx.application.Application;
import javafx.scene.control.ButtonBar;
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
    private final EdgeBundlingAlgorithm algorithm = new EdgeBundlingAlgorithm();
    private final BundlingParameters parameters = new BundlingParameters(
            0.2,
            0.2,
            0.5,
            50,
            0.66,
            6,
            0.1,
            30
            );
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

        Set<Edge> shortEdges = controller.resolveShortEdges(edges, parameters.getShortEdgeThreshold())
                ;

        System.out.println("Resolving short edges:\t" + shortEdges.size());
        System.out.println("Edges left:\t" + edges.size());
        System.out.println("Bundling...");
        long startTime = System.nanoTime();

        for (Edge edge : edges) {
            dividedEdges.add(new DividedEdge(edge, 2, parameters.getGlobalSpringConstant()));
        }


//        int I = 50;
//        int C = 6;

        algorithm.fillCompatibilities(dividedEdges, parameters.getCompatibilityThreshold());

        for (int cycle = 0; cycle < parameters.getNumberOfCycles(); cycle++) {
            for (int i = 0; i < parameters.getNumberOfIterations(); i++) {
                dividedEdges = algorithm.iterate(dividedEdges, parameters.getStep());
            }
            dividedEdges.forEach(DividedEdge::doubleDivisionPoints);
            parameters.updateNumberOfIterations();
            parameters.updateStep();
        }


        dividedEdges.forEach(edge -> lines.addAll(controller.dividedEdgeToLine(edge)));
        shortEdges.forEach(edge -> lines.add(controller.edgeToLine(edge)));


        edgeBundlingGUI.setEdges(lines);
        edgeBundlingGUI.start(stage);

        long endTime = System.nanoTime();
        System.out.println("Time elapsed:\t" + (endTime - startTime) / 1000000000.0);

    }
}
