package uet.oop.bomberman.entities;

import javafx.scene.image.Image;

public class Grass extends stillEntity {

    public Grass(int x, int y, Image img) {
        super(x, y, img);
        setLayer(0);
    }

    @Override
    public void update() {

    }
}
