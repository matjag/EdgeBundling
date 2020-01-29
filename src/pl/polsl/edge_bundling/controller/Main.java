package pl.polsl.edge_bundling.controller;


import javafx.application.Application;
import javafx.stage.Stage;
import pl.polsl.edge_bundling.model.EdgeBundlingAlgorithm;
import pl.polsl.edge_bundling.view.EdgeBundlingGUI;

public class Main extends Application {
    private final EdgeBundlingGUI edgeBundlingGUI = new EdgeBundlingGUI();
    private final EdgeBundlingAlgorithm algorithm = new EdgeBundlingAlgorithm();
    private final Controller controller = new Controller(algorithm, edgeBundlingGUI);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller.run(stage);
    }
}
