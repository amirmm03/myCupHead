package graphic;

import graphic.view.FirstPage;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        FirstPage firstPage = new FirstPage();
        firstPage.run(stage);
    }

    public static void main(String[] args) {
        launch();
    }


}