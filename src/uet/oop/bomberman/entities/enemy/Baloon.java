package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.Random;

public class Baloon extends Enemy {

    private int direct;
    private Rectangle e = this.getBounds();
    private Rectangle eArea = this.getBoundsArea(generateDirect());

    public Baloon(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(1);
        setLayer(1);
        alive = true;
    }

    @Override
    public void update() {
        if (!alive) {
            if (animated < 40) {
                notMove();
                animated++;
                img = Sprite.balloom_dead.getFxImage();
            } else {
                BombermanGame.enemies.remove(this);
                BombermanGame.point += 50;
            }
        } else {
            goStraight();
            if (direct == 0) {
                turnLeft();
            } else if (direct == 1) {
                turnRight();
            } else if (direct == 2) {
                goUp();
            } else if (direct == 3) {
                goDown();
            }
        }
    }

    public void turnLeft() {
        right = 0;
        up = 0;
        down = 0;
        e = this.getBounds();
        super.turnLeft();
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, left++, 18).getFxImage();
    }

    public void turnRight() {
        left = 0;
        up = 0;
        down = 0;
        e = this.getBounds();
        super.turnRight();
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, right++, 18).getFxImage();
    }

    public void goUp() {
        down = 0;
        left = 0;
        right = 0;
        e = this.getBounds();
        super.goUp();
        img = Sprite.movingSprite(Sprite.balloom_left1, Sprite.balloom_left2, Sprite.balloom_left3, up++, 18).getFxImage();
    }

    public void goDown() {
        up = 0;
        left = 0;
        right = 0;
        e = this.getBounds();
        super.goDown();
        img = Sprite.movingSprite(Sprite.balloom_right1, Sprite.balloom_right2, Sprite.balloom_right3, down++, 18).getFxImage();
    }

    @Override
    public void notMove() {
        super.notMove();
        direct = generateDirect();
    }

    public int generateDirect() {
        return (int)(Math.random() * 4);
    }
    public void goStraight() {
        if (!eArea.contains(e)) {
            direct = generateDirect();
            eArea = this.getBoundsArea(generateDirect());
        }
    }


}
