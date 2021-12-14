package uet.oop.bomberman.entities;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Transition;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.sql.Time;

public class Bomber extends Entity {
    public Bomber(int x, int y, Image img) {
        super(x, y, img);
    }

    public int v = 50;

    public void moveRight() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right.getFxImage();
                    x = x + Sprite.SCALED_SIZE / 4;
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right_1.getFxImage();
                    x = x + Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right_2.getFxImage();
                    x = x + Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right.getFxImage();
                    x = x + Sprite.SCALED_SIZE / 4;
                }
        ));
        t.play();
    }

    public void moveRight1() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right_2.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_right.getFxImage();
                }
        ));
        t.play();
    }

    public void moveLeft() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left.getFxImage();
                    x = x - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left_1.getFxImage();
                    x = x - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left_2.getFxImage();
                    x = x - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left.getFxImage();
                    x = x - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.play();
    }

    public void moveLeft1() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left_2.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_left.getFxImage();
                }
        ));
        t.play();
    }

    public void moveUp() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0),
                (ActionEvent event) -> {
                    img = Sprite.player_up.getFxImage();
                    y = y - Sprite.SCALED_SIZE / 4;
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_up_1.getFxImage();
                    y = y - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_up_2.getFxImage();
                    y = y - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_up.getFxImage();
                    y = y - Sprite.SCALED_SIZE / 4;
                }
        ));
        t.play();
    }

    public void moveUp1() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0),
                (ActionEvent event) -> {
                    img = Sprite.player_up.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_up_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_up_2.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_up.getFxImage();
                }
        ));
        t.play();
    }

    public void moveDown() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0),
                (ActionEvent event) -> {
                    img = Sprite.player_down.getFxImage();
                    y = y + Sprite.SCALED_SIZE / 4;
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_down_1.getFxImage();
                    y = y + Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_down_2.getFxImage();
                    y = y + Sprite.SCALED_SIZE / 4;
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_down.getFxImage();
                    y = y + Sprite.SCALED_SIZE / 4;
                }
        ));
        t.play();
    }

    public void moveDown1() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(0),
                (ActionEvent event) -> {
                    img = Sprite.player_down.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_down_1.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(2 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_down_2.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(3 * v),
                (ActionEvent event) -> {
                    img = Sprite.player_down.getFxImage();
                }
        ));
        t.play();
    }

    public void die() {
        Timeline t = new Timeline();
        t.setCycleCount(1);

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(400),
                (ActionEvent event) -> {
                    img = Sprite.player_dead1.getFxImage();

                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1000),
                (ActionEvent event) -> {
                    img = Sprite.player_dead2.getFxImage();
                }
        ));

        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1500),
                (ActionEvent event) -> {
                    img = Sprite.player_dead3.getFxImage();
                }
        ));
        t.getKeyFrames().add(new KeyFrame(
                Duration.millis(1800),
                (ActionEvent event) -> {
                    //BombermanGame.finish = true;
                    //BombermanGame.entities.remove(this);
                    //System.exit(0);
                }
        ));
        t.play();

    }

    @Override
    public void update() {

    }
}
