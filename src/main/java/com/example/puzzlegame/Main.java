package com.example.puzzlegame;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {


        BorderPane borderPane = new BorderPane();

        new Image();
        File currentDir = new File()
        System.out.println(currentDir.toPath());

//        File file = new File("level1.txt");
//        Scanner scanner = new Scanner(file);
//        System.out.println(scanner.nextLine());

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