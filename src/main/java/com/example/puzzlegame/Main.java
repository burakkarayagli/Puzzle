package com.example.puzzlegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        BorderPane borderPane = new BorderPane();



        ImageView img = new ImageView(new Image(getClass().getResource("/EmptyFree.jpg").toString()));
        borderPane.setCenter(img);

        //Pane grid = makeGrid();

        Scene scene = new Scene(borderPane,500,500);


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    /*public Pane makeGrid() {
        for (int i = 0; i < size; i += squareSize) {
            for (int j = 0; j < size; j += squareSize) {
                Rectangle r = new Rectangle(i, j, squareSize, squareSize);
                grid[i / squareSize][j / squareSize] = r;
                r.setFill(Color.WHITE);
                r.setStroke(Color.BLACK);
                pane.getChildren().add(r);
            }
        }
    }*/

    public static void main(String[] args) {
        launch();
    }
}