package com.example.puzzlegame;

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
import javafx.scene.shape.Box;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    private ArrayList<Tile> tiles;

    GridPane grid;
    private final int rowNum = 4;
    private final int colNum = 4;
    private Tile[][] tile2d = new Tile[rowNum][colNum];

    public Game(ArrayList<Tile> level) throws Exception {
        tiles = level;
        grid = createGame(level);
        makeDraggable(tiles);
    }



    private double startX, startY;
    private void makeDraggable(ArrayList<Tile> tiles) {

        for (Tile tile:
             tiles) {
            //If tile is not (Starter, End, Pipestatic, Free)
            if (true) {
                
                tile.setOnMousePressed(e -> {
                    //System.out.println(tile + "bastin");
                    startX = e.getSceneX() - tile.getTranslateX();
                    startY = e.getSceneY() - tile.getTranslateY();
                    //tile.setEffect(new Glow());
                    tile.toFront();
                });

                tile.setOnMouseEntered(e -> {
                    System.out.println("Row: " + GridPane.getRowIndex(tile));
                    System.out.println("Column: " + GridPane.getColumnIndex(tile));
                });

                tile.setOnMouseDragged(e -> {
                    //TODO Sinirlardan disari cikarmayi engelle
                    tile.setTranslateX(e.getSceneX()-startX);
                    tile.setTranslateY(e.getSceneY()-startY);
                });

                tile.setOnMouseReleased(e -> {
                    double targetX = e.getSceneX();
                    double targetY = e.getSceneY();

                    int targetRow = gridIndexFinder(targetX,targetY)[0];
                    int targetCol = gridIndexFinder(targetX,targetY)[1];

                    Tile targetTile = tile2d[targetRow][targetCol];

                    moveTiles(tile, targetTile);
                    tile.setTranslateX(startX);
                    tile.setTranslateY(startY);


                    System.out.println("Satir" + gridIndexFinder(targetX,targetY)[0]);
                    System.out.println("Sutun" + gridIndexFinder(targetX,targetY)[1]);



                    tile.setEffect(null);
                    //System.out.println(tile + "biraktin");
                });

            }


        }


    }

    /*private void move() {

        for (Node node:
             getGrid().getChildren()) {
            node.setOnMousePressed(e-> {
                System.out.println(node + "tiklandi");
                startX = e.getSceneX() - node.getTranslateX();
                startY = e.getSceneY() - node.getTranslateY();
            });

            node.setOnMouseDragged(e-> {
                node.toFront();
                node.setTranslateX(e.getSceneX()-startX);
                node.setTranslateY(e.getSceneY()-startY);
            });
        }
        getGrid().setOnMousePressed(e-> {
            System.out.println("tiklandi grid");
            for (Tile tile:
                 tiles) {
                if (tile.equals(e.getSource())) System.out.println(tile.toString());;
            }
        });
    }*/


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
        for (int row = 0; row < rowNum; row++) {
            for (int column = 0; column < colNum; column++,tile++) {
                Tile img = level.get(tile);
                img.setFitHeight(150);
                img.setFitWidth(150);

                tile2d[row][column] = img;
                grid.add(img, column, row);

            }
        }
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

    private void moveTiles(Tile current, Tile target) {
    //Getting indexes of current tile
    int currentRow = GridPane.getRowIndex(current);
    int currentCol = GridPane.getColumnIndex(current);
    int targetRow = GridPane.getRowIndex(target);
    int targetCol = GridPane.getColumnIndex(target);
    System.out.println("Curr" + currentRow + "|" + currentCol);
    System.out.println("Target" + targetRow + "|" + targetCol);

    grid.getChildren().removeAll(current, target);
    grid.add(target,currentCol,currentRow);
    grid.add(current,targetCol,targetRow);

    }
}
