package pl.polsl.edge_bundling.controller;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import pl.polsl.edge_bundling.model.*;
import pl.polsl.edge_bundling.view.EdgeBundlingGUI;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Controller {

    private EdgeBundlingAlgorithm algorithm;

    private EdgeBundlingGUI edgeBundlingGUI;

    private BundlingParameters parameters;

    private String datasetName;

    public void run(Stage stage) {

        edgeBundlingGUI.start(stage);

        edgeBundlingGUI.getButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                parameters = new BundlingParameters(
                        Double.parseDouble(edgeBundlingGUI.getCompatibilityThresholdField().getText()),
                        Double.parseDouble(edgeBundlingGUI.getShortEdgeThresholdField().getText()),
                        Double.parseDouble(edgeBundlingGUI.getInitialStepField().getText()),
                        Double.parseDouble(edgeBundlingGUI.getStepChangeRateField().getText()),
                        Integer.parseInt(edgeBundlingGUI.getNumberOfIterationsField().getText()),
                        Double.parseDouble(edgeBundlingGUI.getIterationChangeRateField().getText()),
                        Integer.parseInt(edgeBundlingGUI.getNumberOfCyclesField().getText()),
                        Double.parseDouble(edgeBundlingGUI.getSpringConstantField().getText())
                );

                datasetName = edgeBundlingGUI.getDatasetChoice().getValue().toString();
                edgeBundlingGUI.saveStartingParameters(parameters);


                DataLoader dataLoader = new DataLoader();
                Set<Edge> edges = new HashSet<>();
                List<DividedEdge> dividedEdges = new ArrayList<>();
                Set<Line> lines = new HashSet<>();

                for (int i = 10; i < 66; i++) {
                    edges.addAll(dataLoader.loadFromCsv("data/" + datasetName + "/A" + i + ".csv", 2288, 1712));
                }

                System.out.println("Total number of edges:\t" + edges.size());

                Set<Edge> shortEdges = resolveShortEdges(edges, parameters.getShortEdgeThreshold());

                System.out.println("Resolving short edges:\t" + shortEdges.size());
                System.out.println("Edges left:\t" + edges.size());
                System.out.println("Bundling...");
                long startTime = System.nanoTime();

                for (Edge edge : edges) {
                    dividedEdges.add(new DividedEdge(edge, 2, parameters.getGlobalSpringConstant()));
                }


                algorithm.fillCompatibilities(dividedEdges, parameters.getCompatibilityThreshold());

                for (int cycle = 0; cycle < parameters.getNumberOfCycles(); cycle++) {
                    for (int i = 0; i < parameters.getNumberOfIterations(); i++) {
                        dividedEdges = algorithm.iterate(dividedEdges, parameters.getStep());
                    }
                    dividedEdges.forEach(DividedEdge::doubleDivisionPoints);
                    parameters.updateNumberOfIterations();
                    parameters.updateStep();
                }


                dividedEdges.forEach(edge -> lines.addAll(dividedEdgeToLine(edge)));
                shortEdges.forEach(edge -> lines.add(edgeToLine(edge)));


                edgeBundlingGUI.setEdges(lines);
//                edgeBundlingGUI.start(stage, datasetName);

                long endTime = System.nanoTime();
                System.out.println("Time elapsed:\t" + (endTime - startTime) / 1000000000.0);


                Stage stage = new Stage();
                stage.setScene(edgeBundlingGUI.displayBundling());
                stage.show();
            }
        });
    }

    public Line verticesToLine(Vertex vertex1, Vertex vertex2) {
        return new Line(vertex1.getX(), vertex1.getY(), vertex2.getX(), vertex2.getY());
    }

    public Line edgeToLine(Edge edge) {
        return verticesToLine(edge.getStartingVertex(), edge.getEndingVertex());
    }

    public Set<Line> dividedEdgeToLine(DividedEdge dividedEdge) {
        Set<Line> lines = new HashSet<>();
        List<Vertex> divisionPoints = dividedEdge.getDivisionPoints();
        for (int i = 0; i < divisionPoints.size() - 1; i++) {
            lines.add(verticesToLine(divisionPoints.get(i), divisionPoints.get(i + 1)));
        }

        return lines;
    }

    public Set<Edge> resolveShortEdges(Set<Edge> edges, double threshold) {
        Set<Edge> shortEdges = new HashSet<>();
        for (Edge edge : edges) {
            if (edge.getLength() < threshold) {
                shortEdges.add(edge);
            }
        }
        edges.removeAll(shortEdges);
        return shortEdges;
    }

    public Controller(EdgeBundlingAlgorithm algorithm, EdgeBundlingGUI edgeBundlingGUI) {
        this.algorithm = algorithm;
        this.edgeBundlingGUI = edgeBundlingGUI;
    }
}
