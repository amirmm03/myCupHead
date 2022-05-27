package graphic.view;

import graphic.controller.LoginController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.media.*;

import java.io.IOException;
import java.net.URL;


public class FirstPage {
    public static AudioClip menuAudioclip;
    private static boolean mute = false;
    static Stage gameStage;
    public TextField password;
    public TextField username;
    public Label messageLabel;

    public void run(Stage stage) {
        if (menuAudioclip == null) {
            menuAudioclip = new AudioClip(getClass().getResource("/music/13. Concerto in G Minor- I Allegro.mp3").toExternalForm());
            menuAudioclip.play();
        }
        gameStage = stage;
        stage.setTitle("cup head");
        URL url = (getClass().getResource("/graphic/view/first-page.fxml"));
        Parent parent = null;
        try {
            parent = FXMLLoader.load(url);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    public void exit(MouseEvent mouseEvent) {
        System.exit(0);
    }


    public void createUserMenu(MouseEvent mouseEvent) {
        URL url = (getClass().getResource("/graphic/view/create-user-page.fxml"));
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
        run(gameStage);
    }

    public void createUser(MouseEvent mouseEvent) {
        String pass = password.getText();
        String user = username.getText();
        String message = LoginController.getInstance().createUser(pass, user);
        messageLabel.setText(message);
        if (message.endsWith("successfully")) {
            messageLabel.setTextFill(Color.GREEN);
            password.setText("");
            username.setText("");

        } else {
            messageLabel.setTextFill(Color.RED);
        }

    }

    public void startLogin(MouseEvent mouseEvent) {
        LoginPage loginPage = new LoginPage();
        loginPage.run(gameStage);
    }

    public void startGuest(MouseEvent mouseEvent) {
        if (LoginController.getInstance().findUser("Guest") == null)
            LoginController.getInstance().createUser("Guest", "Guest");
        LoginController.getInstance().login("Guest", "Guest");
        UserPage userPage = new UserPage();
        userPage.run(gameStage);
    }

    public void mute(MouseEvent mouseEvent) {
        mute = !mute;
        if (mute) {
            menuAudioclip.stop();
        } else {
            menuAudioclip.play();
        }
    }
    public static boolean getMute(){
        return mute;
    }
}
