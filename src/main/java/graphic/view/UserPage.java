package graphic.view;

import graphic.controller.GameController;
import graphic.controller.LoginController;
import graphic.controller.UserController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class UserPage {
    static Stage gameStage;
    public Label messageLabel;

    public void run(Stage stage) {
        gameStage = stage;
        URL url = (getClass().getResource("/graphic/view/user-page.fxml"));
        Parent parent = null;
        try {
            parent = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        gameStage.setScene(scene);
    }

    @FXML
    public void initialize() {
        messageLabel.setText(UserController.welcomeMessage());
    }


    public void logout(MouseEvent mouseEvent) {
        LoginController.getInstance().logout();
        FirstPage firstPage = new FirstPage();
        firstPage.run(gameStage);
    }

    public void showScoreBoard(MouseEvent mouseEvent) {
        ScoreBoardPage scoreBoardPage = new ScoreBoardPage();
        scoreBoardPage.run(gameStage);
    }

    public void profileMenu(MouseEvent mouseEvent) {
        ChangeProfilePage changeProfilePage = new ChangeProfilePage();
        changeProfilePage.run(gameStage);
    }

    public void startGame(MouseEvent mouseEvent) {
        GameController.startGame();
        GamePage gamePage = new GamePage();
        gamePage.run(gameStage);
    }
}
