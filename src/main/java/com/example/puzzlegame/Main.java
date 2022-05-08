package com.example.puzzlegame;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

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

        // create Ball
        Circle ball = new Circle();

        ball.setRadius(13.5);
        ball.setCenterX(75);
        ball.setCenterY(75);
        ball.setFill(Color.YELLOW);
        ball.setStrokeWidth(0.2);
        ball.setStroke(Color.BLACK);


        game.getGrid().getChildren().add(ball);

        Path path = new Path();

        // for line path

        // MoveTo baslangic pozisyonu starter tile'a gore ayarlariz
        // LineTo'yu ise vertical ve horizantal tile lar icin
        path.getElements().add(new MoveTo(75,75));
        path.getElements().add(new LineTo(75,450));


        // for Curved path
        QuadCurveTo path2 = new QuadCurveTo();
        double fromX = 75;
        double fromY = 450;
        double toX = 150;
        double toY = 525;
        path2.setX(toX);
        path2.setY(toY);
        path2.setControlX(fromX);
        path2.setControlY(toY);
        path.getElements().add(path2);


        path.getElements().add(new LineTo(525,525));


        PathTransition pathTransition = new PathTransition();

        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.play();









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



        // Set background color
        borderPane.setStyle("-fx-background-color:#4f4f4f");



        stage.setTitle("Hello!");

        stage.setScene(scene);
        stage.setResizable(false);


        stage.show();
    }


    public static void main(String[] args) {

        launch();
    }
}