package com.example.puzzlegame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {

        Button button = new Button("scene size");


        Game game = new Game(Game.LevelReader("level3.txt"));
        Game game2 = new Game(Game.LevelReader("level2.txt"));

        Tile starter = new Tile(5, "Starter", "Vertical");


        BorderPane borderPane = new BorderPane();
        GridPane grid = game.getGrid();
        borderPane.setCenter(grid);

        button.setPrefHeight(30);

        Scene scene = new Scene(borderPane,600,600);
        stage.setResizable(true);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                System.out.println(borderPane.getCenter().getScene());
                System.out.println(borderPane.getCenter().getScaleY());

                if (borderPane.getCenter().equals(game.getGrid())) {
                    borderPane.setCenter(game2.getGrid());
            }
                else borderPane.setCenter(game.getGrid());
            }
        };

        button.setOnAction(event);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}

