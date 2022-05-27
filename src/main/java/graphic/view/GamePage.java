package graphic.view;

import graphic.controller.UserController;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Random;

public class GamePage {
    private static Stage gameStage;
    private static ArrayList<Timeline> timelines = new ArrayList<>();
    public static AudioClip gameAudio = null;

    public static Stage getStage() {
        return gameStage;
    }

    public static ArrayList<Timeline> getTimelines() {
        return timelines;
    }

    public void run(Stage stage) {
        FirstPage.menuAudioclip.stop();
        if (gameAudio == null) {
            gameAudio = new AudioClip(getClass().getResource("/music/winter1.mp3").toExternalForm());
        }
        if (!FirstPage.getMute())
            gameAudio.play();
        gameStage = stage;

        AnchorPane anchorPane = new AnchorPane();
        loadBackground(anchorPane);
        addStartNodes(anchorPane);
        anchorPane.getChildren().get(1).requestFocus();
        anchorPane.setPrefWidth(1400);
        anchorPane.setPrefHeight(700);
        Scene scene = new Scene(anchorPane);
        scene.getRoot().getChildrenUnmodifiable().get(3).requestFocus();
        stage.setScene(scene);
    }

    private void addStartNodes(AnchorPane anchorPane) {
        ProgressBar progressBar = createProgressBar();
        anchorPane.getChildren().add(progressBar);
        Label progressLabel = creteProgressLabel();
        Boss boss = Boss.getInstance(progressBar, progressLabel);
        Label healthLabel = creteHealthLabel();
        anchorPane.getChildren().add(boss);
        anchorPane.getChildren().add(progressLabel);
        Plane plane = Plane.getInstance(healthLabel);
        anchorPane.getChildren().add(plane);
        anchorPane.getChildren().add(healthLabel);
        Label timeLabel = new Label();
        addTimelines(anchorPane, timeLabel);
        anchorPane.getChildren().add(timeLabel);
        anchorPane.getChildren().add(createScoreLabel());

    }

    private Label createScoreLabel() {
        Label label = new Label();
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(100), new EventHandler<ActionEvent>() {

                    @Override
                    public void handle(ActionEvent actionEvent) {
                        label.setText(UserController.userScoreMessage());
                    }
                }
                )
        );
        timelines.add(timeline);
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        label.setLayoutX(1250);
        label.setLayoutY(20);
        label.setTextFill(Color.AQUAMARINE);
        label.setBackground(new Background(new BackgroundFill(Color.DARKBLUE, null, null)));
        return label;
    }

    private Label creteHealthLabel() {
        Label label = new Label();
        label.setText("HEALTH : 4");
        label.setLayoutX(30);
        label.setLayoutY(20);
        label.setFont(new Font("Arial", 20));
        label.setTextFill(Color.GREEN);
        return label;
    }

    private Label creteProgressLabel() {
        Label label = new Label();
        label.setText("100%");
        label.setLayoutX(685);
        label.setLayoutY(20);
        label.setTextFill(Paint.valueOf("blue"));
        return label;
    }

    private ProgressBar createProgressBar() {
        ProgressBar progressBar = new ProgressBar(1);

        progressBar.setLayoutY(20);
        progressBar.setLayoutX(600);
        progressBar.setPrefWidth(200);
        progressBar.setStyle("-fx-accent: red");
        return progressBar;
    }

    private void addTimelines(AnchorPane anchorPane, Label timeLabel) {
        Timeline CreateMiniBoss = new Timeline(
                new KeyFrame(Duration.seconds(6),
                        new EventHandler<ActionEvent>() {
                            Random random = new Random();

                            @Override
                            public void handle(ActionEvent event) {
                                anchorPane.getChildren().add(new MiniBoss(random.nextDouble(10, 580)));
                            }
                        }));
        CreateMiniBoss.setCycleCount(Timeline.INDEFINITE);
        CreateMiniBoss.play();


        Timeline CreateBossBomb = new Timeline(
                new KeyFrame(Duration.seconds(6),
                        new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent event) {
                                anchorPane.getChildren().add(new BossBomb());
                            }
                        }));
        CreateBossBomb.setCycleCount(Timeline.INDEFINITE);
        CreateBossBomb.play();


        long startTime = System.currentTimeMillis();
        DateFormat timeFormat = new SimpleDateFormat("m:ss");
        final Timeline timeline = new Timeline(
                new KeyFrame(
                        Duration.millis(500), new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent actionEvent) {
                        UserController.addScore(1);
                        long diff = System.currentTimeMillis() - startTime;
                        timeLabel.setText(timeFormat.format(diff - 1000 * 30 * 60));
                    }
                }
                )
        );
        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();
        timeLabel.setLayoutX(685);
        timeLabel.setLayoutY(5);
        timeLabel.setTextFill(Color.PURPLE);
        timelines.add(timeline);
        timelines.add(CreateBossBomb);
        timelines.add(CreateMiniBoss);
    }

    private void loadBackground(AnchorPane anchorPane) {
        anchorPane.getStylesheets().add(getClass().getResource("/graphic/game.css").toExternalForm());
        DoubleProperty xPosition = new SimpleDoubleProperty(0);
        xPosition.addListener((observable, oldValue, newValue) -> setBackgroundPositions(anchorPane, xPosition.get()));
        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(xPosition, 0)),
                new KeyFrame(Duration.seconds(200), new KeyValue(xPosition, +15000))
        );
        timeline.play();
    }

    void setBackgroundPositions(Region region, double xPosition) {
        String style = "-fx-background-position: " +
                "left " + xPosition / 6 + "px bottom," +
                "left " + xPosition / 5 + "px bottom," +
                "left " + xPosition / 4 + "px bottom," +
                "left " + xPosition / 3 + "px bottom," +
                "left " + xPosition / 2 + "px bottom," +
                "left " + xPosition + "px bottom;";
        region.setStyle(style);
    }

}
