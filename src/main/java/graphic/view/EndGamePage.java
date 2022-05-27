package graphic.view;

import graphic.controller.UserController;
import graphic.model.User;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class EndGamePage {
    private static Stage gameStage;

    public void run(AnchorPane parent, boolean win, Stage stage, String time) {
        gameStage = stage;
        URL url = (getClass().getResource("/graphic/view/end-game-page.fxml"));
        VBox menu = null;
        try {
            menu = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (win) {
            ((Label) menu.getChildren().get(0)).setText("YOU WON!!!!");
            ((Label) menu.getChildren().get(0)).setTextFill(Color.GREEN);
        } else {
            ((Label) menu.getChildren().get(0)).setText("YOU LOST :(");
            ((Label) menu.getChildren().get(0)).setTextFill(Color.RED);
        }
        ((Label) menu.getChildren().get(1)).setText(UserController.userScoreMessage());
        ((Label) menu.getChildren().get(2)).setText("TIME:" + time);

        parent.getChildren().add(menu);

    }

    public void restart(MouseEvent mouseEvent) {
        UserPage userPage = new UserPage();
        userPage.startGame(null);
    }

    public void exit(MouseEvent mouseEvent) {
        GamePage.gameAudio.stop();
        if (!FirstPage.getMute())
            FirstPage.menuAudioclip.play();
        UserPage userPage = new UserPage();
        userPage.run(gameStage);
    }
}
