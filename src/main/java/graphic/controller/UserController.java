package graphic.controller;

import graphic.model.User;
import javafx.scene.control.cell.CheckBoxListCell;

public class UserController {
    private static User loggedInUser;

    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }


    public static String welcomeMessage() {
        return "welcome " + loggedInUser.getUsername() + " !!!";
    }

    public static String changePassOrUser(String newStr, boolean isPassword) {
        if (isPassword) {
            if (newStr.length() <= 4)
                return "password needs at least 5 letter";
            if (newStr.equals(UserController.loggedInUser.getPassword()))
                return "you should use new password";
            UserController.loggedInUser.setPassword(newStr);
            return "password changed successfully";
        } else {

            if (newStr.length() <= 2)
                return "username needs at least 3 letter";
            if (newStr.equals(UserController.loggedInUser.getUsername()))
                return "you should use new username";
            UserController.loggedInUser.setUsername(newStr);
            return "username changed successfully";
        }
    }

    public static String userScoreMessage() {
        return "SCORE : " + loggedInUser.getThisGameScore();
    }

    public static void addScore(int i) {
        loggedInUser.setThisGameScore(loggedInUser.getThisGameScore() + i);
    }


    public static void calculateScore(boolean win, int health) {
        int addindScore = 0;
        if (!win)
            addindScore = -100;
        else {
            addindScore += health * 150;
        }
        loggedInUser.setTotalScore(loggedInUser.getThisGameScore() + loggedInUser.getThisGameScore() + addindScore);
        loggedInUser.setThisGameScore(loggedInUser.getThisGameScore() + addindScore);
    }

    public static void resetGameScore() {
        loggedInUser.setThisGameScore(0);
    }
}
