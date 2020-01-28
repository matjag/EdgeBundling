package pl.polsl.edge_bundling.view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import pl.polsl.edge_bundling.controller.BundlingParameters;

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;
import java.util.function.UnaryOperator;
import java.util.regex.Pattern;

public class EdgeBundlingGUI {

    private Set<Line> edges = new HashSet<>();

    private BundlingParameters parameters;

    private TextField compatibilityThresholdField;

    private TextField shortEdgeThresholdField;

    private TextField initialStepField;

    private TextField stepChangeRateField;

    private TextField numberOfIterationsField;

    private TextField iterationChangeRateField;

    private TextField numberOfCyclesField;

    private TextField springConstantField;

    private ComboBox datasetChoice;

    private Button button;

    public void start(Stage stage) {
        stage.setTitle("Edge Bundling");
        stage.setMinWidth(100);
        stage.setMinHeight(100);
        stage.setScene(prepareForm());
        stage.show();
    }

    public void setEdges(Set<Line> edges) {
        this.edges = edges;
    }

    public void saveStartingParameters(BundlingParameters parameters) {
        this.parameters = new BundlingParameters(parameters);
    }

    private TextFormatter<Double> getDoubleFormatter(double initialValue) {
        Pattern validEditingState = Pattern.compile("(([1-9][0-9]*)|0)?(\\.[0-9]*)?");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };
        StringConverter<Double> converter = new StringConverter<>() {
            @Override
            public Double fromString(String s) {
                if (s.isEmpty() || ".".equals(s)) {
                    return 0.0;
                } else {
                    return Double.valueOf(s);
                }
            }

            @Override
            public String toString(Double d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, initialValue, filter);
    }


    private TextFormatter<Integer> getIntegerFormatter(int initialValue) {
        Pattern validEditingState = Pattern.compile("\\d+");
        UnaryOperator<TextFormatter.Change> filter = c -> {
            String text = c.getControlNewText();
            if (validEditingState.matcher(text).matches()) {
                return c;
            } else {
                return null;
            }
        };
        StringConverter<Integer> converter = new StringConverter<>() {
            @Override
            public Integer fromString(String s) {
                if (s.isEmpty()) {
                    return 0;
                } else {
                    return Integer.valueOf(s);
                }
            }

            @Override
            public String toString(Integer d) {
                return d.toString();
            }
        };

        return new TextFormatter<>(converter, initialValue, filter);
    }


    private Scene prepareForm() {
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text title = new Text("Choose parameters");
        title.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(title, 0, 0, 2, 1);

        Label compatibilityThresholdLabel = new Label("Compatibility threshold:");
        grid.add(compatibilityThresholdLabel, 0, 1);
        compatibilityThresholdField = new TextField();
        compatibilityThresholdField.setTextFormatter(getDoubleFormatter(0.2));
        grid.add(compatibilityThresholdField, 1, 1);

        Label shortEdgeThresholdLabel = new Label("Short edge threshold:");
        grid.add(shortEdgeThresholdLabel, 0, 2);
        shortEdgeThresholdField = new TextField();
        shortEdgeThresholdField.setTextFormatter(getDoubleFormatter(15.0));
        grid.add(shortEdgeThresholdField, 1, 2);

        Label initialStepLabel = new Label("Initial step:");
        grid.add(initialStepLabel, 0, 3);
        initialStepField = new TextField();
        initialStepField.setTextFormatter(getDoubleFormatter(0.2));
        grid.add(initialStepField, 1, 3);

        Label stepChangeRateLabel = new Label("Step change rate:");
        grid.add(stepChangeRateLabel, 0, 4);
        stepChangeRateField = new TextField();
        stepChangeRateField.setTextFormatter(getDoubleFormatter(0.5));
        grid.add(stepChangeRateField, 1, 4);

        Label numberOfIterationsLabel = new Label("Number of iterations:");
        grid.add(numberOfIterationsLabel, 0, 5);
        numberOfIterationsField = new TextField();
        numberOfIterationsField.setTextFormatter(getIntegerFormatter(50));
        grid.add(numberOfIterationsField, 1, 5);

        Label iterationChangeRateLabel = new Label("Iteration change rate:");
        grid.add(iterationChangeRateLabel, 0, 6);
        iterationChangeRateField = new TextField();
        iterationChangeRateField.setTextFormatter(getDoubleFormatter(0.66));
        grid.add(iterationChangeRateField, 1, 6);

        Label numberOfCyclesLabel = new Label("Number of cycles:");
        grid.add(numberOfCyclesLabel, 0, 7);
        numberOfCyclesField = new TextField();
        numberOfCyclesField.setTextFormatter(getIntegerFormatter(5));
        grid.add(numberOfCyclesField, 1, 7);

        Label springConstantLabel = new Label("Spring constant:");
        grid.add(springConstantLabel, 0, 8);
        springConstantField = new TextField();
        springConstantField.setTextFormatter(getDoubleFormatter(0.1));
        grid.add(springConstantField, 1, 8);

        ObservableList<String> options =
                FXCollections.observableArrayList(
                        "animal-8",
                        "animal-11",
                        "animal-21"
                );
        datasetChoice = new ComboBox(options);
        datasetChoice.setValue("animal-11");

        Label dataSetLabel = new Label("Choose data set:");
        grid.add(dataSetLabel, 0, 9);
        grid.add(datasetChoice, 1, 9);

        button = new Button("Run");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(button);
        grid.add(hbBtn, 1, 10);

        return new Scene(grid, 400, 450);
    }

    public Button getButton() {
        return button;
    }

    public TextField getCompatibilityThresholdField() {
        return compatibilityThresholdField;
    }

    public TextField getShortEdgeThresholdField() {
        return shortEdgeThresholdField;
    }

    public TextField getInitialStepField() {
        return initialStepField;
    }

    public TextField getStepChangeRateField() {
        return stepChangeRateField;
    }

    public TextField getNumberOfIterationsField() {
        return numberOfIterationsField;
    }

    public TextField getIterationChangeRateField() {
        return iterationChangeRateField;
    }

    public TextField getNumberOfCyclesField() {
        return numberOfCyclesField;
    }

    public TextField getSpringConstantField() {
        return springConstantField;
    }

    public ComboBox getDatasetChoice() {
        return datasetChoice;
    }

    public Scene displayBundling() {
        String imageName = datasetChoice.getValue().toString();
        File file = new File("data/" + imageName + "/animal.jpg");
        try {
            FileInputStream fileInputStream = new FileInputStream(file);
            Image image = new Image(fileInputStream);
            Canvas canvas = new Canvas(image.getWidth(), image.getHeight());
            GraphicsContext gc = canvas.getGraphicsContext2D();
            gc.setStroke(Color.RED);
            gc.setFill(Color.YELLOW);
            ScrollPane scrollPane = new ScrollPane(canvas);
            scrollPane.setPrefSize(500, 500);
            scrollPane.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            Scene scene = new Scene(scrollPane);
            gc.drawImage(image, 0, 0);
            edges.forEach(edge -> {
                gc.strokeLine(edge.getStartX(), edge.getStartY(), edge.getEndX(), edge.getEndY());
            });


            File saveFile = new File("result/" + imageName + parameters.toString() + ".png");
            WritableImage wi = new WritableImage((int) image.getWidth(), (int) image.getHeight());
            try {
                ImageIO.write(SwingFXUtils.fromFXImage(canvas.snapshot(null, wi), null), "png", saveFile);
                return scene;
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}

