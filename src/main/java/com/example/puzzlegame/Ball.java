package com.example.puzzlegame;

import javafx.animation.PathTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcTo;
import javafx.scene.shape.Circle;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Ball extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage Stage) {
        Stage.setTitle("BALL");



        Circle ball = new Circle();

        ball.setRadius(13);
        ball.setCenterX(500);
        ball.setCenterY(500);
        ball.setFill(Color.YELLOW);
        ball.setStrokeWidth(0.2);
        ball.setStroke(Color.BLACK);


        // for shape path
        //Rectangle PathRectangle = new Rectangle(100,100);
        //Circle pathCircle = new Circle(100);



        Path path = new Path();


        // for line path
        MoveTo moveTo = new MoveTo();
        moveTo.setX(0);
        moveTo.setY(0);
        path.getElements().add(new MoveTo(0,0));
        path.getElements().add(new LineTo(200,100));

        // for circler path
        ArcTo arcto = new ArcTo();
        arcto.setX(100);
        arcto.setY(100);
        arcto.setRadiusX(50);
        arcto.setRadiusY(25);

        path.getElements().add(new ArcTo());

        // Basladigi yere geri donduruyor
        //path.getElements().add(new ClosePath());

        PathTransition pathTransition = new PathTransition();

        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.play();



        BorderPane pane = new BorderPane(ball);
        pane.setLayoutY(300);
        pane.setLayoutX(300);




        Scene scene = new Scene(pane);

        Stage.setScene(scene);
        Stage.show();
    }
}
