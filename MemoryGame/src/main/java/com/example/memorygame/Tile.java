package com.example.memorygame;

import javafx.scene.image.Image;

public class Tile {

    private Integer id;
    private boolean isRevealed;
    private boolean isFound;
    private Image defaultImage;

    private Image image;

    public Tile(Integer id) {
        this.id = id;
        this.defaultImage = new Image("file:images/0.png");
        this.isFound = false;
        this.isRevealed = false;
    }



    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isRevealed() {
        return isRevealed;
    }

    public void setRevealed(boolean revealed) {
        isRevealed = revealed;
    }

    public boolean isFound() {
        return isFound;
    }

    public void setFound(boolean found) {
        isFound = found;
    }

    public Image getDefaultImage() {
        return defaultImage;
    }

    public void setDefaultImage(Image defaultImage) {
        this.defaultImage = defaultImage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }
}
