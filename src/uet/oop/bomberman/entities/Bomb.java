package uet.oop.bomberman.entities;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.scene.image.Image;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Bomb extends Entity {
    public long TimeStart;
    public int getX[] = {-1, 0, 1, 0};
    public int getY[] = {0, -1, 0, 1};

    public Bomb(int x, int y, Image img) {
        super(x, y, img);
    }

    public void explosion() {
        Timeline t = new Timeline();
        t.setCycleCount(1);
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(250),
                (ActionEvent event) -> {
                    img = Sprite.bomb_2.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(500),
                (ActionEvent event) -> {
                    img = Sprite.bomb_1.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(750),
                (ActionEvent event) -> {
                    img = Sprite.bomb_2.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1000),
                (ActionEvent event) -> {
                    img = Sprite.bomb_1.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1250),
                (ActionEvent event) -> {
                    img = Sprite.bomb_2.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1500),
                (ActionEvent event) -> {
                    img = Sprite.bomb_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1750),
                (ActionEvent event) -> {
                    img = Sprite.bomb.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2000),
                (ActionEvent event) -> {
                    img = Sprite.bomb_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2250),
                (ActionEvent event) -> {
                    img = Sprite.bomb.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2500),
                (ActionEvent event) -> {
                    img = Sprite.bomb_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3000),
                (ActionEvent event) -> {
                    img = Sprite.bomb.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3300),
                (ActionEvent event) -> {
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3800),
                (ActionEvent event) -> {
                    img = Sprite.bomb_exploded.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(4300),
                (ActionEvent event) -> {
                    img = Sprite.bomb_exploded1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(4800),
                (ActionEvent event) -> {
                    img = Sprite.bomb_exploded2.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(5000),
                (ActionEvent event) -> {
                    BombermanGame.BombMap[y / 32][x / 32] = 0;
                    BombermanGame.stillObjects.remove(this);
                }
        ));
        t.play();
    }

    public void checkEntities() {
        if (System.currentTimeMillis() - TimeStart > 3800 && BombermanGame.bomberman != null) {

            List<Entity> l = new ArrayList<Entity>();
            for (int k = 0; k < 4; k++) {
                for (int i = 0; i < BombermanGame.n_flame; i++) {
                    for (Entity e : BombermanGame.entities) {
                        if ((e.getX() == x && e.getY() == y) || ((e.getX() == x + (i + 1) * getX[k] * Sprite.SCALED_SIZE && e.getY() == y + (i + 1) * getY[k] * Sprite.SCALED_SIZE) && (BombermanGame.ObjectMap[y / 32 + getY[k]][x / 32 + getX[k]] instanceof Grass))) {
                            l.add(e);
                        }
                    }
                }
            }
        }
    }

    @Override
    public void update() {
        checkEntities();
    }
}
