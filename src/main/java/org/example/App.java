package org.example;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.Modality;
import javafx.scene.text.Text;
import javafx.util.Duration;

/**
 * JavaFX App
 */
public class App extends Application {

    private final static int SCENE_HEIGHT = 500;
    private final static int SCENE_WIDTH = 500;

    @Override
    public void start(Stage stage) {

        // Main window
        Pane pane = new Pane();
        Scene scene = new Scene(pane, SCENE_HEIGHT, SCENE_WIDTH);
        Circle circle = new Circle(20, Color.RED);
        circle.relocate(10, 10);
        pane.getChildren().add(circle);

        stage.setScene(scene);
        stage.show();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {
            double dx = 2;
            double dy = 5;

            @Override
            public void handle(ActionEvent actionEvent) {
                circle.setLayoutX(circle.getLayoutX() + dx);
                circle.setLayoutY(circle.getLayoutY() + dy);

                Bounds bounds = pane.getBoundsInLocal();

                if (circle.getLayoutX() <= (bounds.getMinX() + circle.getRadius()) ||
                        circle.getLayoutX() >= (bounds.getMaxX() - circle.getRadius())) {
                    System.out.println("Out of bounds X " + circle.getLayoutX());

                    dx = -dx;
                }

                if (circle.getLayoutY() <= (bounds.getMinY() + circle.getRadius()) ||
                        circle.getLayoutY() >= (bounds.getMaxY() - circle.getRadius())) {
                    dy = -dy;
                }
            }
        }));

        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        Button changeColorButton = new Button("Change Color");
        changeColorButton.relocate(10, SCENE_HEIGHT - 40);
        pane.getChildren().add(changeColorButton);

        changeColorButton.setOnAction(event -> {
            // Creates a new stage for the color input window
            Stage colorInputStage = new Stage();
            colorInputStage.initModality(Modality.APPLICATION_MODAL);
            colorInputStage.setTitle("Color Input");

            // Creates a layout for the color input window
            Pane colorInputPane = new Pane();
            TextField colorInputTextField = new TextField();
            colorInputTextField.relocate(50, 50);
            Button applyColorButton = new Button("Apply Color");
            applyColorButton.relocate(50, 80);

            applyColorButton.setOnAction(applyEvent -> {
                String colorInput = colorInputTextField.getText();
                try {
                    Color color = Color.web(colorInput);
                    circle.setFill(color);
                    colorInputStage.close();
                } catch (IllegalArgumentException e) {
                    System.out.println("Invalid color input: " + colorInput);
                }
            });

            colorInputPane.getChildren().addAll(colorInputTextField, applyColorButton);
            Scene colorInputScene = new Scene(colorInputPane, 300, 150);
            colorInputStage.setScene(colorInputScene);
            colorInputStage.show();
        });

        // Create a text window that starts minimized
        Stage textStage = new Stage();
        textStage.initModality(Modality.APPLICATION_MODAL);
        textStage.setTitle("ThisDisco Was Here");
        textStage.setIconified(true); // Start minimized

        Pane textPane = new Pane();
        Text text = new Text("ThisDisco Was Here");
        text.relocate(50, 50);
        textPane.getChildren().add(text);
        Scene textScene = new Scene(textPane, 300, 100);
        textStage.setScene(textScene);

        textStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
