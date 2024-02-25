import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class App extends Application {

    private PalList palList;

    @Override
    public void start(Stage primaryStage) {
        // Initialize list and populate from file
        palList = new PalList();
        palList.populateFromTextFile("C:\\Java\\final update 4\\ListOfPals.txt");

        // Set stage
        primaryStage.setTitle("Pal App");

        // Create labels
        Label completedLabel = new Label("Completed Pals (10 captures)");
        completedLabel.setTextFill(Color.WHITE);
        VBox completedPalsBox = new VBox();
        Label incompleteLabel = new Label("Incomplete Pals");
        incompleteLabel.setTextFill(Color.WHITE);
        VBox incompletePalsBox = new VBox();

        // Iterate though pals to create info labels and sliders
        for (Pal pal : palList.getPals()) {
            String palInfo = "ID: " + pal.getPalNumber() + ", " + pal.getName() + ", "
                    + pal.getElementType() + ", Captures: " + pal.getCapturedCount();
            Label palLabel = new Label(palInfo);
            palLabel.setTextFill(Color.WHITE);

            // Create slider for capture count
            Slider slider = new Slider(1, 10, pal.getCapturedCount());
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(1);
            slider.setSnapToTicks(true);
            slider.setBlockIncrement(1); // Allow only integer values

            // Listener to change capture count with slider
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                        Number newValue) {
                    pal.setCapturedCount(newValue.intValue());
                }
            });

            // Create vbox for pal label and slider
            VBox palBoxContent = new VBox();
            palBoxContent.getChildren().addAll(palLabel, slider);

            // Create hbox so slider will apear under pals info
            HBox palBox = new HBox();
            palBox.getChildren().addAll(palBoxContent);

            // add pal to apropriate list
            if (pal.getCapturedCount() == 10) {
                completedPalsBox.getChildren().add(palBox);
            } else {
                incompletePalsBox.getChildren().add(palBox);
            }
        }

        // Create sort options box
        VBox sortingOptionsBox = new VBox();
        sortingOptionsBox.setSpacing(5);
        Label sortingOptionsLabel = new Label("Sorting Options");
        sortingOptionsLabel.setTextFill(Color.WHITE);
        sortingOptionsBox.getChildren().add(sortingOptionsLabel);

        // Create choice box for sort options
        ChoiceBox<String> sortingOptions = new ChoiceBox<>();
        sortingOptions.getItems().addAll("ID", "Name", "Element", "CapturedCount");
        sortingOptions.setValue("ID");
        sortingOptions.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            // handle sort options
            switch (newVal) {
                case "ID":
                    palList.sortPalsByPalId();
                    break;
                case "Name":
                    palList.sortPalsByPalName();
                    break;
                case "Element":
                    palList.sortPalsByPalElement();
                    break;
                case "CapturedCount":
                    palList.sortPalsByCapturedCount();
                    break;
                default:
                    break;
            }
            // update view after sorting
            updatePalsView(completedPalsBox, incompletePalsBox);
        });

        // button to save changes
        Button saveChangesButton = new Button("Update Changes");
        saveChangesButton.setOnAction(e -> {
            saveChanges();
            updatePalsView(completedPalsBox, incompletePalsBox);
        });

        // Create layout for scene
        VBox layout = new VBox(10);
        layout.setStyle("-fx-background-color: rgb(39, 5, 64);");
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(sortingOptionsBox, sortingOptions, completedLabel, completedPalsBox,
                incompleteLabel,
                incompletePalsBox,
                saveChangesButton);

        // Set stage
        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    // updates list
    private void updatePalsView(VBox completedPalsBox, VBox incompletePalsBox) {
        // Clear the existing content
        completedPalsBox.getChildren().clear();
        incompletePalsBox.getChildren().clear();

        // Iterate each pal to create info and sliders
        for (Pal pal : palList.getPals()) {
            // Create a label to display pal information
            String palInfo = "ID: " + pal.getPalNumber() + ", " + pal.getName() + ", "
                    + pal.getElementType() + ", Captures: " + pal.getCapturedCount();
            Label palLabel = new Label(palInfo);
            palLabel.setTextFill(Color.WHITE);

            // Create slider for capture count
            Slider slider = new Slider(1, 10, pal.getCapturedCount());
            slider.setShowTickMarks(true);
            slider.setShowTickLabels(true);
            slider.setMajorTickUnit(1);
            slider.setSnapToTicks(true);
            slider.setBlockIncrement(1); // Allow only integer values

            // Listener to change capture count with slider
            slider.valueProperty().addListener(new ChangeListener<Number>() {
                public void changed(ObservableValue<? extends Number> observable, Number oldValue,
                        Number newValue) {
                    pal.setCapturedCount(newValue.intValue());
                }
            });

            // Create vbox for pal label and slider
            VBox palBox = new VBox();
            palBox.getChildren().addAll(palLabel, slider);

            // add pal to apropriate list
            if (pal.getCapturedCount() == 10) {
                completedPalsBox.getChildren().add(palBox);
            } else {
                incompletePalsBox.getChildren().add(palBox);
            }
        }
    }

    private void saveChanges() {
        // updates changes
    }

    // Main, launch app
    public static void main(String[] args) {
        launch(args);
    }

    // populate pals from a text file
    public void populatePalsFromTextFile(String filename) {
        palList.populateFromTextFile(filename);
    }
}
