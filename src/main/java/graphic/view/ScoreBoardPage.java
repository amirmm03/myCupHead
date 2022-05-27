package graphic.view;

import graphic.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.net.URL;

public class ScoreBoardPage {
    private static Stage gameStage;
    public TableView<User> tableView;

    public void run(Stage stage) {
        gameStage = stage;
        URL url = (getClass().getResource("/graphic/view/score-board-page.fxml"));
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
        ObservableList<User> data = FXCollections.observableArrayList();
        TableColumn<User, String> usernames = new TableColumn<User, String>("username");
        TableColumn<User, Integer> scores = new TableColumn<>("score");
        usernames.setSortable(false);

        data.addAll(User.getAllUsers());
        usernames.setCellValueFactory(
                new PropertyValueFactory<User, String>("username")
        );
        scores.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("totalScore")
        );
        tableView.setItems(data);
        tableView.getColumns().addAll(usernames, scores);
        scores.setSortType(TableColumn.SortType.DESCENDING);
        tableView.getSortOrder().addAll(scores);
        final int[] number = {0};
        int numberOfUsers = User.getAllUsers().size();
        if (numberOfUsers>=3)
            numberOfUsers =2;
        int finalNumberOfUsers = numberOfUsers;
        tableView.setRowFactory(tableView -> new TableRow<User>() {
            @Override
            public void updateItem(User item, boolean empty) {
                super.updateItem(item, empty);
                number[0]++;
                if (number[0] == 1+4* finalNumberOfUsers)
                    setStyle("-fx-background-color: #eef601");
                if (number[0] == 2+4* finalNumberOfUsers)
                    setStyle("-fx-background-color: #acacac");
                if (number[0] == 3+4* finalNumberOfUsers)
                    setStyle("-fx-background-color: #b67c30");
            }
        });
    }

    public void back(MouseEvent mouseEvent) {
        UserPage userPage = new UserPage();
        userPage.run(gameStage);
    }
}
