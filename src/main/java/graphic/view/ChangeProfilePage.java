package graphic.view;

import graphic.controller.LoginController;
import graphic.controller.UserController;
import javafx.animation.FadeTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;

public class ChangeProfilePage {
    private static Stage gameStage;
    public Label messageLabel;
    public TextField username;
    public TextField password;
    public RadioButton first;
    public RadioButton second;
    private final ToggleGroup toggleGroup = new ToggleGroup();


    public void run(Stage stage) {
        gameStage = stage;
        URL url = (getClass().getResource("/graphic/view/change-profile-page.fxml"));
        Parent parent = null;
        try {
            parent = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        gameStage.setScene(scene);
    }

    public void initialize() {
        first.setToggleGroup(toggleGroup);
        second.setToggleGroup(toggleGroup);
    }

    public void change(MouseEvent mouseEvent) {
        String pass = password.getText();
        String user = username.getText();
        if (toggleGroup.getSelectedToggle() == null) {
            messageLabel.setText("please choose one");
            messageLabel.setTextFill(Color.RED);
            return;
        }
        String message;
        if (toggleGroup.getSelectedToggle() == first) {
            message = UserController.changePassOrUser(user, false);
        } else {
            message = UserController.changePassOrUser(pass, true);
        }
        messageLabel.setText(message);

        if (message.endsWith("successfully")) {
            messageLabel.setTextFill(Color.GREEN);
            password.setText("");
            username.setText("");
        } else {
            messageLabel.setTextFill(Color.RED);
        }

    }

    public void back(MouseEvent mouseEvent) {
        UserPage userPage = new UserPage();
        userPage.run(gameStage);
    }
}
