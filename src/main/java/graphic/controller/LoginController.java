package graphic.controller;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import graphic.model.User;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LoginController {

    private static LoginController loginController = null;


    private LoginController() {
        loadUsers();
    }

    public static LoginController getInstance() {
        if (loginController == null) {
            loginController = new LoginController();
        }
        return loginController;
    }

    public String createUser(String password, String username) {
        if (password.length() < 5)
            return "password must be at least 5 characters";
        if (username.length() < 3)
            return "username must be at least 3 characters";
        if (findUser(username) != null)
            return "username is taken";
        User.getAllUsers().add(new User(username, password));
        saveUsers();
        return "user created successfully";
    }

    private void saveUsers() {
        try {
            FileWriter fileWriter = new FileWriter("./src/main/resources/UserDatabase.json");
            fileWriter.write(new Gson().toJson(User.getAllUsers()));
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadUsers() {
        try {
            String json = new String(Files.readAllBytes(Paths.get("./src/main/resources/UserDatabase.json")));
            ArrayList<User> createdUsers;
            createdUsers = new Gson().fromJson(json, new TypeToken<List<User>>() {
            }.getType());
            if (createdUsers != null) User.setAllUsers(createdUsers);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User findUser(String username) {
        for (User user : User.getAllUsers()) {
            if (user.getUsername().equals(username))
                return user;
        }
        return null;
    }


    public String login(String password, String username) {
        User user = findUser(username);
        if (user == null)
            return "no user with this name";
        if (!user.getPassword().equals(password))
            return "incorrect password";

        UserController.setLoggedInUser(user);
        return "done";
    }

    public void logout() {
        UserController.setLoggedInUser(null);
        saveUsers();
    }
}
