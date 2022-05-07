/*package com.example.puzzlegame;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Game {

    public void test(ArrayList<Tile> tiles) {
        System.out.println(tiles);
    }
    public ArrayList<Tile> LevelReader(String fileName) throws Exception {
        //String filePath = getClass().getResource("/Levels/"+fileName).toString();

        //File file = new File(filePath);
        // input = new Scanner(file);
        //ArrayList<Tile> tiles = new ArrayList<>(); // keeps tiles
        try {
            while (input.hasNext()) {
                String line = input.nextLine();
                if (!line.isEmpty()) {
                    String[] Splitter = line.split(",");

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
}*/
