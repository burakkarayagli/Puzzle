package com.example.puzzlegame;

import java.util.ArrayList;

public class Road {

    private ArrayList<Tile> roadTiles = new ArrayList<>(); // tiles that made the road
    private ArrayList<Tile> tiles = new ArrayList<>(); // tiles on the gridPane
    private Tile end = new Tile(); // End point
    private Tile start = new Tile(); // Start point

    // default constructor
    public Road() {
    }

    // constructor
    public Road(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    // setter-getters
    public Tile getEnd() {
        return end;
    }

    public void setEnd(Tile end) {
        this.end = end;
    }

    public Tile getStart() {
        return start;
    }

    public void setStart(Tile start) {
        this.start = start;
    }

    public ArrayList<Tile> getRoadTiles() {
        return roadTiles;
    }

    public void setRoadTiles(ArrayList<Tile> roadTiles) {
        this.roadTiles = roadTiles;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    // this method finds a appropriate road for the ball from start point to end
    // point
    public boolean levelRoad() {
        // finding end and start point
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getType().equalsIgnoreCase("End")) {
                end = tiles.get(i);
            }
            if (tiles.get(i).getType().equalsIgnoreCase("Starter")) {
                start = tiles.get(i);
            }
        }

        //roadTiles.add(start); // road's first element is always start point
        System.out.println("starter eklendi");
        int i = start.getTileId() - 1; // keeps the start point index for tiles list

        // deciding ball's direction
        if (start.getProperty().equalsIgnoreCase("Vertical")) {
            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("00")
                    || getTiles().get(i).getProperty().equalsIgnoreCase("01")
                    || getTiles().get(i).equals(end))) {
                // road goes all vertical
                if (getTiles().get(i).getProperty().equalsIgnoreCase("Vertical")) {
                    roadTiles.add(getTiles().get(i));
                    i += 4;
                    if (i > 15)
                        return false;
                    continue;
                } else
                    return false;
            }
        } else if (start.getProperty().equalsIgnoreCase("Horizontal")) {

            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("11")
                    || getTiles().get(i).getProperty().equalsIgnoreCase("01") || getTiles().get(i).equals(end))) {
                // road goes all horizontal
                if (getTiles().get(i).getProperty().equalsIgnoreCase("Horizontal")) {

                    roadTiles.add(getTiles().get(i));
                    i -= 1;
                    if (i < 0)
                        return false;
                    continue;
                } else
                    return false;
            }

        }

        // finding a road to end point.
        while (!getTiles().get(i).equals(end) && i >= 0 && i <= 15) {
            if (i < 15 && i > 0)
                if (getTiles().get(i).getProperty().equalsIgnoreCase("01")) {// curved pipe number 01

                    if (i + 1 <= 15 && i - 4 >= 0) {
                        roadTiles.add(getTiles().get(i));
                        if (getTiles().get(i + 1).equals(end)) {
                            i += 1;
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i + 1))) { /* if the road came from right */
                            i -= 4;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("10")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("11")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all vertical
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Vertical")
                                        && !getTiles().get(i).equals(end)) {

                                    roadTiles.add(getTiles().get(i));
                                    i -= 4;
                                    if (i < 0)
                                        return false;
                                    continue;
                                } else
                                    return false;

                            }

                        } else if (getTiles().get(i - 4).equals(end)) {
                            i -= 4;
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i - 4))) {/* if the road came from up */
                            i++;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("00")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("10")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all horizontal
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Horizontal")
                                        && !getTiles().get(i).equals(end)) {

                                    roadTiles.add(getTiles().get(i));
                                    i++;
                                    if (i > 15)
                                        return false;
                                    continue;
                                } else
                                    return false;

                            }
                        } else
                            return false;

                    } else
                        return false;

                }

            if (i <= 15 && i > 0)

                if (getTiles().get(i).getProperty().equalsIgnoreCase("00")) {// curved pipe number 00
                    roadTiles.add(getTiles().get(i));
                    if (i - 4 >= 0) {
                        if (getTiles().get(i - 4).equals(end)) {
                            i -= 4;
                        } else if (getTiles().get(i - 1).equals(end)) {
                            i -= 1;
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i - 4))) {/* if the road came from up */
                            i -= 1;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("01")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("11")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all horizontal
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Horizontal")) {
                                    roadTiles.add(getTiles().get(i));
                                    i -= 1;
                                    if (i < 0)
                                        return false;

                                    continue;
                                } else
                                    return false;
                            }

                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i - 1))) { /* if the road came from left */
                            i -= 4;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("11")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("10")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all vertical
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Vertical")) {
                                    roadTiles.add(getTiles().get(i));
                                    i -= 4;
                                    if (i < 0)
                                        return false;
                                    continue;
                                } else
                                    return false;
                            }
                        } else
                            return false;
                    } else
                        return false;

                }
            if (i < 15 && i > 0)
                if (getTiles().get(i).getProperty().equalsIgnoreCase("10")) {// curved pipe number 10
                    if (i - 1 >= 0 && i + 4 <= 15) {
                        roadTiles.add(getTiles().get(i));
                        if (getTiles().get(i - 1).equals(end)) {
                            i -= 1;
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i - 1))) { /* if the road came from left */
                            i += 4;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("00")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("01")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all vertical
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Vertical")) {
                                    roadTiles.add(getTiles().get(i));
                                    i += 4;
                                    if (i > 15)
                                        return false;
                                    continue;
                                } else
                                    return false;
                            }
                        }

                        else if (getTiles().get(i + 4).equals(end)) {
                            i += 4;
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i + 4))) { /* if the road came from down */
                            i -= 1;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("11")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("01")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all horizontal
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Horizontal")) {
                                    roadTiles.add(getTiles().get(i));
                                    i -= 1;
                                    if (i < 0)
                                        return false;
                                    continue;
                                } else
                                    return false;
                            }
                        } else
                            return false;

                    } else
                        return false;

                }

            if (i < 15 && i >= 0)
                if (getTiles().get(i).getProperty().equalsIgnoreCase("11")) {// curved pipe number 11
                    roadTiles.add(getTiles().get(i));

                    if (i + 4 <= 15) {

                        if (getTiles().get(i + 1).equals(end)) {
                            i += 1;
                        } else if (getTiles().get(i + 4).equals(end)) {
                            i += 4;
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i + 1))) { /* if the road came from right */
                            i += 4;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("00")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("01")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all vertical
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Vertical")) {
                                    roadTiles.add(getTiles().get(i));
                                    // right
                                    i += 4;
                                    if (i > 15)
                                        return false;
                                    continue;
                                } else
                                    return false;
                            }
                        } else if (roadTiles.get(roadTiles.size() - 2)
                                .equals(getTiles().get(i + 4))) { /* if the road came from down */

                            i += 1;
                            while (!(getTiles().get(i).getProperty().equalsIgnoreCase("00")
                                    || getTiles().get(i).getProperty().equalsIgnoreCase("10")
                                    || getTiles().get(i).equals(end))) {
                                // road goes all horizontal
                                if (getTiles().get(i).getProperty().equalsIgnoreCase("Horizontal")) {
                                    roadTiles.add(getTiles().get(i));

                                    i += 1;
                                    if (i > 15)
                                        return false;
                                    continue;
                                } else
                                    return false;
                            }
                        } else
                            return false;
                    } else
                        return false;

                }

        }

        roadTiles.add(end);

        int size = roadTiles.size(); // size of the roadTiles list
        String finish = roadTiles.get(size - 2).getProperty(); // property of the last tile before end.
        int finishId = roadTiles.get(size - 2).getTileId(); // id of the last tile before end.

        // checking end point is right for the road with its property
        if (!(finish.equalsIgnoreCase("Vertical") && end.getProperty().equalsIgnoreCase("Horizontal")
                || finish.equalsIgnoreCase("Horizontal") && end.getProperty().equalsIgnoreCase("Vertical"))) {
            if (finishId + 1 == end.getTileId() || finishId - 4 == end.getTileId()) {
                return true;
            }
            System.out.println("Wrong Way!!!");
            return false;
        }
        return false;
    }

    // Information about road
    @Override
    public String toString() {
        String s = "";
        for (Tile roads : roadTiles)
            s += "Id: " + roads.getTileId() + " Property: " + roads.getProperty() + "Type: " + roads.getType() + "\n";

        return s;
    }
}
