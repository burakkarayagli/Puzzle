module com.example.puzzlegame {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.puzzlegame to javafx.fxml;
    exports com.example.puzzlegame;
}