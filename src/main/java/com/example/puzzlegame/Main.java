package com.example.puzzlegame;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {


    public static int currentLevelInt;
    public static Text MoveCount = new Text("Move Count: " + 0);
    private ArrayList<ArrayList<Tile>> levelsTile = new ArrayList<>();
    private ArrayList<Game> gameBoardsList = new ArrayList<>();
    @Override
    public void start(Stage stage) throws Exception {

        MoveCount.setFont(Font.font("Times New Roman", FontWeight.BOLD, 18));

        File levelFolder = new File("src/main/resources/Levels");
        File[] listOfFiles = levelFolder.listFiles();

        //Reading all levels and storing them as Tile ArrayList
        for (File file: listOfFiles) {
            String fileName = file.getName();
            levelsTile.add(Game.LevelReader(fileName));
        }

        //Creating gameboards with levelsTile list
        for (ArrayList<Tile> level : levelsTile) {
            gameBoardsList.add(new Game(level));
        }






        HBox control = new HBox();
        Button buttonPrev = new Button("Prev Level");
        Button buttonNext = new Button("Next Level");
        control.setAlignment(Pos.CENTER);
        control.setSpacing(50);
        control.getChildren().addAll(buttonPrev,buttonNext,MoveCount);
        control.setBackground(Background.fill(Color.AQUA));


        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(control);

        control.setPrefHeight(50);

        Scene scene = new Scene(borderPane,600,650);
        stage.setResizable(false);



        //Setting first level
        Game game = gameBoardsList.get(0);
        currentLevelInt = 0;
        borderPane.setCenter(game.getGrid());




        EventHandler<ActionEvent> Next = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                //If the level isn't the last level
                if (currentLevelInt != gameBoardsList.size()-1 && gameBoardsList.get(currentLevelInt).isPassed()) {
                    borderPane.setCenter(gameBoardsList.get(++currentLevelInt).getGrid());
                    int levelnumber = currentLevelInt+1;
                    stage.setTitle("Level " + levelnumber);
                    Main.MoveCount.setText("Move Count: " + gameBoardsList.get(currentLevelInt).getMoveCounter());
                }
            }
        };

        EventHandler<ActionEvent> Prev = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                //If the level isn't the first level
                if (currentLevelInt != 0) {
                    borderPane.setCenter(gameBoardsList.get(--currentLevelInt).getGrid());
                    int levelnumber = currentLevelInt+1;
                    stage.setTitle("Level " + levelnumber);
                    Main.MoveCount.setText("Move Count: " + gameBoardsList.get(currentLevelInt).getMoveCounter());
                }
            }
        };

        buttonNext.setOnAction(Next);
        buttonPrev.setOnAction(Prev);





        stage.setTitle("Level " + 1);

        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
    }

