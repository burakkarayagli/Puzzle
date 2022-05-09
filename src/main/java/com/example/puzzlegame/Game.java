package com.example.puzzlegame;

import javafx.animation.KeyFrame;
import javafx.animation.PathTransition;
import javafx.animation.Timeline;
import javafx.scene.effect.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
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

    private final double tileSize = 150;
    private Tile[][] tile2d = new Tile[rowNum][colNum];
    private double ycoordinate; // tile's y coordinate on the pane
    private double xcoordinate; // tile's x coordinate on the pane
    private boolean passed = false;

    private Circle ball;

    public Game(ArrayList<Tile> level) throws Exception {
        tiles = level;
        grid = createGame(level);

        Tile startTile = null;
        for (Tile tile:
             level) {
            if (tile.getType().equalsIgnoreCase("Starter")) {
                startTile = tile;
            }
        }

        setCircleGame(startTile);
    }
    private double startX, startY;
    private void makeDraggable(Tile tile) {
        //If tile is not (Starter, End, Pipestatic, Free)
        if (!(tile.getType().equalsIgnoreCase("Starter") ||
                tile.getType().equalsIgnoreCase("End") ||
                tile.getType().equalsIgnoreCase("PipeStatic") ||
                tile.getProperty().equalsIgnoreCase("Free"))) {

            tile.setOnMousePressed(e -> {
                startX = e.getSceneX() - tile.getTranslateX();
                startY = e.getSceneY() - tile.getTranslateY();
                tile.setEffect(new Glow());
                tile.toFront();
            });

            tile.setOnMouseEntered(e -> {
                //System.out.println(tile.toString());
            });

            tile.setOnMouseDragged(e -> {
                if(e.getSceneX()<=tileSize*colNum
                        && e.getSceneX()>=0) tile.setTranslateX(e.getSceneX()-startX);
                if(e.getSceneY()<=tileSize*rowNum
                        && e.getSceneY()>=0) tile.setTranslateY(e.getSceneY()-startY);
            });

            tile.setOnMouseReleased(e -> {
                double targetX = e.getSceneX();
                double targetY = e.getSceneY();

                int currentRow = GridPane.getRowIndex(tile);
                int currentCol = GridPane.getColumnIndex(tile);
                int targetRow = gridIndexFinder(targetX,targetY)[0];
                int targetCol = gridIndexFinder(targetX,targetY)[1];

                Tile targetTile = null;

                try {
                    targetTile = tile2d[targetRow][targetCol];
                }
                catch (Exception e2) {
                    System.out.println("Mouse isn't on tile");
                }

                if(targetTile != null && targetTile.getProperty().equalsIgnoreCase("Free") &&
                        ((targetCol == currentCol && (targetRow == currentRow+1 || targetRow == currentRow-1)) || (targetRow == currentRow && (targetCol == currentCol+1 || targetCol == currentCol-1)))) {
                    moveTiles(tile, targetTile);
                }

                else {
                    tile.setTranslateX(0);
                    tile.setTranslateY(0);
                }

                tile.setEffect(null);
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
        grid.setStyle("-fx-background-color:#2596be");
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


        moveCounter++;

        Main.MoveCount.setText("Move Count: " + getMoveCounter());
        //createPath(tile2d);

        double xcoordinate = 0;
        double ycoordinate = 0;
        Track road = new Track(tiles);
        // Check whether there is an appropriate path
        if (road.levelRoad()) {
            ArrayList<LineTo> lines = new ArrayList<LineTo>();

            double x = grid.getWidth() / 8;
            double y = grid.getHeight() / 8;

            xcoordinate = 0;
            ycoordinate = 0;



            // Create a path using the tiles in roadTiles ArrayList
            for (Tile tile1 : road.getRoadTiles()) {

                if (!tile1.getType().equalsIgnoreCase("Empty")) {

                    int id = tile1.getTileId();

                    int tile1row = tileIndexFinder(tile1)[0];
                    int tile1col = tileIndexFinder(tile1)[1];

                    xcoordinate = getXdistance(tile1row,tile1col);
                    ycoordinate = getYdistance(tile1row,tile1col);

                    // Create a lineTo using a and y coordinates
                    LineTo line = new LineTo(xcoordinate, ycoordinate);
                    // Keep the lines in lines ArrayList
                    lines.add(line);

                }

            }
            // Get the starter tile's id and set a starting point
            double startx = getXCoordinates(road.getStart().getTileId(), x);
            double starty = getYCoordinates(road.getStart().getTileId(), y);

            Path path = new Path();
            MoveTo moveTo = new MoveTo(startx, starty);

            path.getElements().add(moveTo);

            for (LineTo line1 : lines) {

                // Add each element of lines ArrayList to path
                path.getElements().add(line1);
            }

            if (!isPassed()) {
                // Create a circle (ball) for the animation
                Circle circle = new Circle(startx, starty, 20, Color.GOLD);
                PathTransition pt = new PathTransition();
                // Set the duration of the animation
                pt.setDuration(Duration.millis(3000));
                pt.setNode(circle);
                // Set the path which is created using lineTo
                pt.setPath(path);
                pt.setOrientation(PathTransition.OrientationType.ORTHOGONAL_TO_TANGENT);
                grid.getChildren().remove(ball);
                circle.setManaged(false);
                // Add the circle(ball) to pane
                grid.getChildren().add(circle);
                pt.play();
            }

            passed = true;
        }
    }
    private double getXCoordinates(int id, double x) {

        int order = 1;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getTileId() == id) order = i+1;
        }


        if (order % 4 == 1)
            xcoordinate = x;
        else if (order % 4 == 2)
            xcoordinate = 3 * x;
        else if (order % 4 == 3)
            xcoordinate = 5 * x;
        else if (order % 4 == 0)
            xcoordinate = 7 * x;

        return xcoordinate;
    }

    private double getYCoordinates(int id, double y) {

        int order = 1;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getTileId() == id) order = i+1;
        }

        if (order <= 4)
            ycoordinate = y;
        else if (order > 4 && id <= 8)
            ycoordinate = 3 * y;
        else if (order > 8 && id <= 12)
            ycoordinate = 5 * y;
        else if (order > 12 && id <= 16)
            ycoordinate = 7 * y;
        return ycoordinate;
    }

    private void setCircleGame(Tile start) {

        grid.layout();
        double x = grid.getBoundsInLocal().getWidth() / 8; // x width of a tile
        double y = grid.getBoundsInLocal().getHeight() / 8; // y height of a tile
        double startx = getXCoordinates(start.getTileId(), x); // start x coordinate
        double endx = getYCoordinates(start.getTileId(), y); // start y coordinates
        ball = new Circle(startx, endx, 20, Color.GOLD);//setting the circle object
        ball.setManaged(false);
        grid.getChildren().add(ball);
    }

    private double getXdistance(int row, int column) {
        return column*tileSize+tileSize/2;
    }

    private  double getYdistance(int row, int column) {
        return row*tileSize+tileSize/2;
    }

    public boolean isPassed() {
        return passed;
    }
}
