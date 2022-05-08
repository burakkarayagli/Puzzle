package com.example.puzzlegame;

import javafx.animation.PathTransition;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.PointLight;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public int moveCounter = 0;
    private ArrayList<Tile> tiles;
    GridPane grid;
    private final int rowNum = 4;
    private final int colNum = 4;

    private Circle ball;

    private final double tileSize = 150;
    private Tile[][] tile2d = new Tile[rowNum][colNum];

    public Game(ArrayList<Tile> level) throws Exception {
        tiles = level;
        grid = createGame(level);
        addBall();

    }
    private double startX, startY;
    private void makeDraggable(Tile tile) {
        //If tile is not (Starter, End, Pipestatic, Free)
        if (true || !(tile.getType().equalsIgnoreCase("Starter") ||
                tile.getType().equalsIgnoreCase("End") ||
                tile.getType().equalsIgnoreCase("PipeStatic") ||
                tile.getProperty().equalsIgnoreCase("Free"))) {

            tile.setOnMousePressed(e -> {
                //System.out.println(tile + "bastin");
                startX = e.getSceneX() - tile.getTranslateX();
                startY = e.getSceneY() - tile.getTranslateY();
                tile.setEffect(new Glow());
                tile.toFront();
            });

            tile.setOnMouseEntered(e -> {
                //System.out.println("Row: " + GridPane.getRowIndex(tile));
                //System.out.println("Column: " + GridPane.getColumnIndex(tile));
            });

            tile.setOnMouseDragged(e -> {
                //TODO Sinirlardan disari cikarmayi engelle bir de suruklemeye baslayinca soluk bir tile ciksin
                tile.setTranslateX(e.getSceneX()-startX);
                tile.setTranslateY(e.getSceneY()-startY);
            });

            tile.setOnMouseReleased(e -> {
                double targetX = e.getSceneX();
                double targetY = e.getSceneY();

                int currentRow = GridPane.getRowIndex(tile);
                int currentCol = GridPane.getColumnIndex(tile);
                int targetRow = gridIndexFinder(targetX,targetY)[0];
                int targetCol = gridIndexFinder(targetX,targetY)[1];

                Tile targetTile = tile2d[targetRow][targetCol];




                if(true || targetTile.getProperty().equalsIgnoreCase("Free") &&
                        ((targetCol == currentCol && (targetRow == currentRow+1 || targetRow == currentRow-1)) ||
                         (targetRow == currentRow && (targetCol == currentCol+1 || targetCol == currentCol-1)))) {
                    moveTiles(tile, targetTile);
                }

                else {
                    tile.setTranslateX(0);
                    tile.setTranslateY(0);
                }


                //System.out.println("Satir" + gridIndexFinder(targetX,targetY)[0]);
                //System.out.println("Sutun" + gridIndexFinder(targetX,targetY)[1]);



                tile.setEffect(null);
                //System.out.println(tile + "biraktin");
            });

        }
    }

    public GridPane getGrid() {
        return grid;
    }

    public static int[] gridIndexFinder(double targetX, double targetY) {
        int row = (int)(targetY/150);
        int column = (int)(targetX/150);
        return new int[]{row,column};
    }

    public int[] tileIndexFinder(Tile searchingTile) {
        for (int row = 0; row < rowNum; row++) {
            for (int column = 0; column < colNum; column++) {
                if (searchingTile.equals(tile2d[row][column])) {
                    return new int[]{row,column};
                }
            }
        }
        return null;
    }

    public GridPane createGame(ArrayList<Tile> level) throws Exception {
        grid = new GridPane();
        int tile = 0;
        for (int row = 0; row < colNum; row++) {
            for (int column = 0; column < rowNum; column++,tile++) {
                Tile img = level.get(tile);
                img.setFitHeight(150);
                img.setFitWidth(150);

                tile2d[row][column] = img;
                grid.add(img, column, row);
                makeDraggable(img);

            }
        }
        grid.setStyle("-fx-background-color:#4f4f4f");
        return grid;
    }

    public static ArrayList<Tile> LevelReader(String fileName) throws Exception {
        String filePath = "src/main/resources/Levels/" + fileName;
        File file = new File(filePath);
        Scanner input = new Scanner(file);
        ArrayList<Tile> tiles = new ArrayList<>(); //Tiles list
        try {
            while (input.hasNext()) {
                String line = input.nextLine();
                if (!line.isEmpty()) {
                    String[] Splitter = line.split(",");//Splitting line

                    int id = Integer.parseInt(Splitter[0]);
                    String type = Splitter[1];
                    String property = Splitter[2];
                    Tile tile = new Tile(id, type, property);//Creating tile and adding to tiles arraylist
                    tiles.add(tile);
                }
            }
        } catch (Exception e) {
            System.out.println("Error at file reading" + e);
        }
        input.close();
        return tiles;
    }

    public int getRowNum() {
        return rowNum;
    }

    public int getColNum() {
        return colNum;
    }

    public int getMoveCounter() {
        return moveCounter;
    }

    private void moveTiles(Tile current, Tile target){
        //Getting indexes of current tile
        int currentRow = GridPane.getRowIndex(current);
        int currentCol = GridPane.getColumnIndex(current);
        int targetRow = GridPane.getRowIndex(target);
        int targetCol = GridPane.getColumnIndex(target);

        Tile tempCur = current;
        int indexCur = currentRow*4+currentCol+1;
        int indexTar = targetRow*4+targetCol+1;
        tiles.set(indexCur-1, target);
        tiles.set(indexTar-1, tempCur);

        grid.getChildren().removeAll(current, target);
        current.setTranslateX(startX);
        current.setTranslateY(startY);
        grid.add(current, targetCol, targetRow);
        grid.add(target, currentCol, currentRow);
        current.setTranslateX(0);
        current.setTranslateY(0);
        makeDraggable(current);
        tile2d[targetRow][targetCol] = current;
        tile2d[currentRow][currentCol] = target;




        //System.out.println("Curr" + currentRow + "|" + currentCol + "index: " + indexCur);
        //System.out.println("Target" + targetRow + "|" + targetCol + "index: " + indexTar);
        moveCounter++;

        Main.MoveCount.setText("Move Count: " + getMoveCounter());
        //createPath(tile2d);


    }

    public void addBall() {
        //Creating ball
        ball = new Circle();

        ball.setRadius(13.5);
        ball.setCenterX(tileSize);
        ball.setCenterY(tileSize/2);
        ball.setFill(Color.YELLOW);
        ball.setStrokeWidth(0.2);
        ball.setStroke(Color.BLACK);
        getGrid().getChildren().add(ball);

    }

    public boolean createPath(Tile[][] tile2d) {


        Tile starterTile = null, endTile = null;

        //Finding the starter and end tile
        for (int row = 0; row < rowNum; row++) {
            for (int col = 0; col < colNum; col++) {
                if (tile2d[row][col].getType().equalsIgnoreCase("Starter")) {
                    starterTile = tile2d[row][col];
                }
                if (tile2d[row][col].getType().equalsIgnoreCase("End")) {
                    endTile = tile2d[row][col];
                }
            }
        }

        int rowStarter = tileIndexFinder(starterTile)[0];
        int colStarter = tileIndexFinder(starterTile)[1];

        Path path = new Path();

        path.getElements().add(new MoveTo(rowStarter*tileSize+tileSize/2,
                colStarter*tileSize+tileSize/2));

        Tile current = starterTile;
        boolean a = true;
        while (a) {

            int curRow = tileIndexFinder(current)[0];
            int curCol = tileIndexFinder(current)[1];
//            Tile upTile = tile2d[curRow-1][curCol];
//            Tile downTile = tile2d[curRow+1][curCol];
//            Tile rightTile = tile2d[curRow][curCol+1];
//            Tile leftTile = tile2d[curRow][curCol-1];

            //Starter Vertical
            if (current.getType().equalsIgnoreCase("Starter") &&
                    current.getProperty().equalsIgnoreCase("Vertical")) {
                    Tile downTile = tile2d[curRow+1][curCol];
                    path.getElements().add(new LineTo(rowStarter*tileSize+tileSize/2, colStarter*tileSize+tileSize));

                if (downTile.getProperty().equalsIgnoreCase("Vertical")) {
                    double downX = curRow+1 * tileSize + tileSize/2;
                    double downY = curRow+1 * tileSize + tileSize;
                    path.getElements().add(new LineTo(downX, downY));

                    current = downTile;

                }

                else if (downTile.getProperty().equalsIgnoreCase("00")) {

                }

                else if (downTile.getProperty().equalsIgnoreCase("01")) {

                }

            }

            else if (current.getProperty().equalsIgnoreCase("Vertical")) {
                Tile downTile = tile2d[curRow+1][curCol];

                if (downTile.getProperty().equalsIgnoreCase("Vertical")) {
                    double downX = curRow+1 * tileSize + tileSize/2;
                    double downY = curRow+1 * tileSize + tileSize;
                    path.getElements().add(new LineTo(downX, downY));
                    a = false;

                }

            }

        }

        PathTransition pathTransition = new PathTransition();

        pathTransition.setPath(path);
        pathTransition.setNode(ball);
        pathTransition.setDuration(Duration.seconds(3));
        pathTransition.play();






        return false;
    }
}
