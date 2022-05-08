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
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
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


        BorderPane borderPane = new BorderPane();
        borderPane.setBottom(control);

        control.setPrefHeight(100);

        Scene scene = new Scene(borderPane,600,700);
        stage.setResizable(true);



        //Setting first level
        Game game = gameBoardsList.get(0);
        currentLevelInt = 0;
        borderPane.setCenter(game.getGrid());

        System.out.println(currentLevelInt);
        System.out.println(gameBoardsList.size());

        EventHandler<ActionEvent> Next = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                //If the level isn't the last level
                if (currentLevelInt != gameBoardsList.size()-1) {
                    borderPane.setCenter(gameBoardsList.get(++currentLevelInt).getGrid());
                }

                if (currentLevelInt == gameBoardsList.size()-2){
                    //control.getChildren().remove(buttonNext);
                }

                else if (currentLevelInt == 1) {
                    //control.getChildren().add(buttonPrev);
                }
            }
        };

        EventHandler<ActionEvent> Prev = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {

                //If the level isn't the first level
                if (currentLevelInt != 0) {
                    borderPane.setCenter(gameBoardsList.get(--currentLevelInt).getGrid());
                }

                if(currentLevelInt == 1) {
                    //control.getChildren().remove(buttonPrev);
                }
                else if (currentLevelInt == gameBoardsList.size()-1) {
                    //control.getChildren().add(buttonNext);
                }


            }
        };

        buttonNext.setOnAction(Next);
        buttonPrev.setOnAction(Prev);

        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }


    public static void main(String[] args) {
        launch();
    }
}

