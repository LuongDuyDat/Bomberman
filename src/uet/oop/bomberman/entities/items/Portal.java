package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;

public class Portal extends Item{
    public Portal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        alive = true;
    }

    @Override
    public void update() {

    }
}
