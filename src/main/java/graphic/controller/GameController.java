package graphic.controller;

import graphic.model.Boss;
import graphic.model.Plane;
import graphic.view.*;
import javafx.animation.Timeline;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.AudioClip;


public class GameController {
    private static boolean gameIsFinished;

    public static void startGame() {
        gameIsFinished = false;
        UserController.resetGameScore();
    }

    public static String planeHitBoss() {
        AudioClip audioClip = new AudioClip(GameController.class.getResource("/music/explosion1.mp3").toExternalForm());
        if (!FirstPage.getMute())
            audioClip.play();
        if (gameIsFinished)
            return "";
        Plane.getInstance().setHealth(Plane.getInstance().getHealth() - 1);
        if (Plane.getInstance().getHealth() <= 0) {

            return "dead";
        }
        return "done";
    }

    public static void updateHealth(Label healthLabel) {
        if (gameIsFinished)
            return;
        healthLabel.setText("HEALTH : " + Plane.getInstance().getHealth());
    }


    public static String bossHitBullet() {
        if (gameIsFinished)
            return "";
        Boss.getInstance().setHealth(Boss.getInstance().getHealth() - 1);
        if (Boss.getInstance().getHealth() == 40)
            return "halfDone";
        UserController.addScore(20);
        if (Boss.getInstance().getHealth() <= 0)
            return "dead";
        return "done";
    }

    public static void updateBossHealth(ProgressBar progressBar, Label progressLabel) {
        if (gameIsFinished)
            return;
        double percent = ((double) Boss.getInstance().getHealth()) / Boss.getInstance().getTotalHealth();
        progressBar.setProgress(percent);
        progressLabel.setText((int) (percent * 100) + "%");
    }

    public static void miniBossHitBullet(MiniBoss miniBoss) {
        if (gameIsFinished)
            return;
        UserController.addScore(5);
        miniBoss.setHealth(miniBoss.getHealth() - 1);
        if (miniBoss.getHealth() <= 0) {
            miniBoss.remove();
            UserController.addScore(5);
        }
    }

    public static void endGame(boolean win) {
        gameIsFinished = true;

        UserController.calculateScore(win, Plane.getInstance().getHealth());
        for (Timeline timeline : GamePage.getTimelines()) {
            timeline.stop();
        }
        while (!GamePage.getTimelines().isEmpty()) {
            GamePage.getTimelines().remove(0);
        }
        String time = ((Label) ((AnchorPane) graphic.view.Boss.getInstance().getParent()).getChildren().get(5)).getText();
        EndGamePage endGamePage = new EndGamePage();
        endGamePage.run((AnchorPane) graphic.view.Boss.getInstance().getParent(), win, GamePage.getStage(), time);
        Boss.reset();
        graphic.view.Boss.reset();
        Plane.reset();
        graphic.view.Plane.reset();
        MiniBoss.reset();
        Bullet.reset();
        BossBomb.reset();
    }
}
