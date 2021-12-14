package uet.oop.bomberman.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

public class Brick extends Entity{

    public Brick(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setLayer(3);
    }

    public void exploded() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3800),
                (ActionEvent event) -> {
                    img = Sprite.brick_exploded.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(4300),
                (ActionEvent event) -> {
                    img = Sprite.brick_exploded1.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(4800),
                (ActionEvent event) -> {
                    img = Sprite.brick_exploded2.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(5000),
                (ActionEvent event) -> {
                    BombermanGame.stillObjects.remove(this);
                    Grass grass = new Grass(x/ Sprite.SCALED_SIZE, y/Sprite.SCALED_SIZE, Sprite.grass.getFxImage());
                    BombermanGame.stillObjects.add(grass);
                    BombermanGame.ObjectMap[y/Sprite.SCALED_SIZE][x/Sprite.SCALED_SIZE] = grass;
                }
        ));
        t.play();
    }


    @Override
    public void update() {

    }
}
