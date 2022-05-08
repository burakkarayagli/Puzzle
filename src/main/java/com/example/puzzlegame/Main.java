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
import javafx.scene.layout.HBox;
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

        HBox control = new HBox();
        Button buttonPrev = new Button("Prev Level");
        Button buttonNext = new Button("Next Level");
        Button button1 = new Button("Next Level");
        Button button2 = new Button("Next Level");
        Button button3 = new Button("Next Level");

        control.setAlignment(Pos.CENTER);
        control.setSpacing(50);

        control.getChildren().addAll(buttonPrev,buttonNext,button1,button2,button3);

        Game game = new Game(Game.LevelReader("level1.txt"));
        Game game2 = new Game(Game.LevelReader("level2.txt"));

        Tile starter = new Tile(5, "Starter", "Vertical");


        BorderPane borderPane = new BorderPane();
        GridPane grid = game.getGrid();
        borderPane.setCenter(grid);
        borderPane.setBottom(control);

        control.setPrefHeight(100);

        Scene scene = new Scene(borderPane,600,700);
        stage.setResizable(true);


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e)
            {
                if (borderPane.getCenter().equals(game.getGrid())) {
                    borderPane.setCenter(game2.getGrid());
                }
                else borderPane.setCenter(game.getGrid());
            }
        };

        buttonPrev.setOnAction(event);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.setResizable(false);

        // Set background color
        borderPane.setStyle("-fx-background-color:#4f4f4f");

        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}