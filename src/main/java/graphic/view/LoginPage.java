package graphic.view;

import graphic.controller.LoginController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class LoginPage {
    public Label messageLabel;
    public TextField username;
    public TextField password;
    static Stage gameStage;

    public void run(Stage stage) {
        gameStage = stage;
        URL url = (getClass().getResource("/graphic/view/login-page.fxml"));
        Parent parent = null;
        try {
            parent = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        gameStage.setScene(scene);
    }


    public void backToFirstPage(MouseEvent mouseEvent) {
        FirstPage firstPage = new FirstPage();
        firstPage.run(gameStage);
    }

    public void login(MouseEvent mouseEvent) {
        String pass = password.getText();
        String user = username.getText();
        String message = LoginController.getInstance().login(pass, user);
        messageLabel.setText(message);
        if (message.equals("done")) {
            messageLabel.setTextFill(Color.GREEN);
            password.setText("");
            username.setText("");
            FadeTransition fadeTransition = new FadeTransition(Duration.millis(2000));
            fadeTransition.setNode(messageLabel);
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.setCycleCount(1);
            fadeTransition.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    UserPage userPage = new UserPage();
                    userPage.run(gameStage);
                }
            });
            fadeTransition.play();
        } else {
            messageLabel.setTextFill(Color.RED);
        }

    }
}
