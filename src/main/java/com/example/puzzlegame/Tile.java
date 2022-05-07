package com.example.puzzlegame;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Tile{
    private int id; // tile's id number
    private String type; // tile's type
    private String property;// tile's property
    private ImageView image;// the image of the tile


    //No-Arg Constructor
    public Tile() {}
    //Constructor with Tile
    public Tile(Tile tile) {
        setTileId(tile.id);
        setType(tile.type);
        setProperty(tile.property);
        setImage(tile.image);
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

    public void setImage(ImageView image) {
        this.image = image;
    }

    public ImageView getImage() {
        return image;
    }

    //Finding and setting the image of tile
    private void findImage() {

        if (type.equalsIgnoreCase("Starter")) { // blue starter
            if (property.equalsIgnoreCase("Vertical")) {
                image = new ImageView(new Image(getClass().getResource("/StarterVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                image = new ImageView(new Image(getClass().getResource("/StarterHorizontal.jpg").toString()));

            }

        } else if (type.equalsIgnoreCase("End")) { // red end
            if (property.equalsIgnoreCase("Vertical")) {
                image = new ImageView(new Image(getClass().getResource("/EndVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                image = new ImageView(new Image(getClass().getResource("/EndHorizontal.jpg").toString()));

            }
        } else if (type.equalsIgnoreCase("Empty")) {
            if (property.equalsIgnoreCase("none")) { // grey pile
                image = new ImageView(new Image(getClass().getResource("/EmptyNone.jpg").toString()));
            } else if (property.equalsIgnoreCase("Free")) {
                image = new ImageView(new Image(getClass().getResource("/EmptyFree.jpg").toString()));

            }
        } else if (type.equalsIgnoreCase("PipeStatic")) { // fix blue pile
            if (property.equalsIgnoreCase("Vertical")) {
                image = new ImageView(new Image(getClass().getResource("/PipeStaticVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                image = new ImageView(new Image(getClass().getResource("/PipeStaticHorizontal.jpg").toString()));
            }

        } else if (type.equalsIgnoreCase("Pipe")) {
            if (property.equalsIgnoreCase("Vertical")) {
                image = new ImageView(new Image(getClass().getResource("/PipeVertical.jpg").toString()));

            } else if (property.equalsIgnoreCase("Horizontal")) {
                image = new ImageView(new Image(getClass().getResource("/PipeHorizontal.jpg").toString()));
            }else if (property.equalsIgnoreCase("00")) {
                image = new ImageView(new Image(getClass().getResource("/Pipe00.jpg").toString()));
            }else if (property.equalsIgnoreCase("01")) {
                image = new ImageView(new Image(getClass().getResource("/Pipe01.jpg").toString()));
            }else if (property.equalsIgnoreCase("10")) {
                image = new ImageView(new Image(getClass().getResource("/Pipe10.jpg").toString()));
            }else if (property.equalsIgnoreCase("11")) {
                image = new ImageView(new Image(getClass().getResource("/Pipe11.jpg").toString()));
            }
        }
    }

    //To String Method
    @Override
    public String toString() {
        return "Id: " + id + " Property: " + property + "Type: " + type ;
    }
}
