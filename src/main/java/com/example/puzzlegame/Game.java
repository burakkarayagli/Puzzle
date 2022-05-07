package com.example.puzzlegame;

import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {
    GridPane grid;
    int rowNum = 4,colNum = 4;

    public Game(String level) throws Exception {
        grid = createGame(level);
    }


    public GridPane getGrid() {
        return grid;
    }

    public GridPane createGame(String fileName) throws Exception {
        ArrayList<Tile> level = LevelReader(fileName);
        grid = new GridPane();
        int tile = 0;
        for (int row = 0; row < rowNum; row++) {
            for (int column = 0; column < colNum; column++,tile++) {
                ImageView img = level.get(tile).getImage();
                img.setFitHeight(125);
                img.setFitWidth(125);

                grid.add(img, row, column);
            }
        }
        return grid;
    }

    public ArrayList<Tile> LevelReader(String fileName) throws Exception {
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
}
