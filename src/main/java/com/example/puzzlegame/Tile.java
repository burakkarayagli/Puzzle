package com.example.puzzlegame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile extends ImageView{
    private int id; // tile's id number
    private String type; // tile's type
    private String property;// tile's property


    //No-Arg Constructor
    public Tile() {}
    //Constructor with Tile
    public Tile(Tile tile) {
        setTileId(tile.id);
        setType(tile.type);
        setProperty(tile.property);
        setImage(tile.getImage());
    }
    //Constructor with attributions
    public Tile(int id, String type, String property) {
        setTileId(id);
        setType(type);
        setProperty(property);
        try {
            findImage();
        }catch(Exception e){
            System.out.println(e);
        }
    }

    //Getter and Setters
    public int getTileId() {
        return id;
    }

    public void setTileId(int id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    //Finding and setting the image of tile
    private void findImage() {

        if (type.equalsIgnoreCase("Starter")) { // blue starter
            if (property.equalsIgnoreCase("Vertical")) {
                this.setImage(new Image(getClass().getResource("/StarterVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                this.setImage(new Image(getClass().getResource("/StarterHorizontal.jpg").toString()));

            }

        } else if (type.equalsIgnoreCase("End")) { // red end
            if (property.equalsIgnoreCase("Vertical")) {
                this.setImage(new Image(getClass().getResource("/EndVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                this.setImage(new Image(getClass().getResource("/EndHorizontal.jpg").toString()));

            }
        } else if (type.equalsIgnoreCase("Empty")) {
            if (property.equalsIgnoreCase("none")) { // grey pile
                this.setImage(new Image(getClass().getResource("/EmptyNone.jpg").toString()));
            } else if (property.equalsIgnoreCase("Free")) {
                this.setImage(new Image(getClass().getResource("/EmptyFree.jpg").toString()));

            }
        } else if (type.equalsIgnoreCase("PipeStatic")) { // fix blue pile
            if (property.equalsIgnoreCase("Vertical")) {
                this.setImage(new Image(getClass().getResource("/PipeStaticVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                this.setImage(new Image(getClass().getResource("/PipeStaticHorizontal.jpg").toString()));
            }

        } else if (type.equalsIgnoreCase("Pipe")) {
            if (property.equalsIgnoreCase("Vertical")) {
                this.setImage(new Image(getClass().getResource("/PipeVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                this.setImage(new Image(getClass().getResource("/PipeHorizontal.jpg").toString()));
            }else if (property.equalsIgnoreCase("00")) {
                this.setImage(new Image(getClass().getResource("/Pipe00.jpg").toString()));
            }else if (property.equalsIgnoreCase("01")) {
                this.setImage(new Image(getClass().getResource("/Pipe01.jpg").toString()));
            }else if (property.equalsIgnoreCase("10")) {
                this.setImage(new Image(getClass().getResource("/Pipe10.jpg").toString()));
            }else if (property.equalsIgnoreCase("11")) {
                this.setImage(new Image(getClass().getResource("/Pipe11.jpg").toString()));
            }
        }
    }

    public void copy(Tile tile) {
        setTileId(tile.getTileId());
        setType(tile.getType());
        setProperty(tile.getProperty());
        setImage(tile.getImage());
    }

    //To String Method
    @Override
    public String toString() {
        return "Id: " + id + " Property: " + property + " Type: " + type ;
    }
}
