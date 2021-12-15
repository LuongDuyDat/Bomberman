package uet.oop.bomberman.entities.items;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;

public class BombItem extends Item{
    public BombItem(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        alive = true;
    }

    @Override
    public void update() {
        if (!alive) {
            BombermanGame.items.remove(this);
        }
    }
}
