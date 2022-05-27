module graphic.graphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.media;


    opens graphic.view to javafx.fxml;
    opens graphic to javafx.fxml;
    opens graphic.model to javafx.base,com.google.gson;


    exports graphic;
}