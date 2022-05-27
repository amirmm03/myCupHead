package graphic.model;


import java.util.ArrayList;

public class User {
    private static ArrayList<User> allUsers = new ArrayList<>();
    private String username;
    private String password;
    private int totalScore;
    private int thisGameScore = 0;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public int getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(int totalScore) {
        this.totalScore = totalScore;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getThisGameScore() {
        return thisGameScore;
    }

    public void setThisGameScore(int thisGameScore) {
        this.thisGameScore = thisGameScore;
    }

    public static ArrayList<User> getAllUsers() {
        return allUsers;
    }

    public static void setAllUsers(ArrayList<User> newAll) {
        allUsers = newAll;
    }
}
