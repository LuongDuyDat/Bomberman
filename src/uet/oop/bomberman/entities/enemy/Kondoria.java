package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public class Kondoria extends Enemy{

    private int direct;
    private Rectangle e = this.getBounds();
    private Rectangle eArea = this.getBoundsArea(generateDirect());

    public Kondoria(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        setSpeed(1);
        setLayer(4);
        alive = true;
    }

    @Override
    public void update() {
        if (!alive) {
            if (animated < 40) {
                notMove();
                animated++;
                img = Sprite.kondoria_dead.getFxImage();
            } else {
                BombermanGame.enemies.remove(this);
                BombermanGame.point += 100;
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
        img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, left++, 18).getFxImage();
    }

    public void turnRight() {
        left = 0;
        up = 0;
        down = 0;
        e = this.getBounds();
        super.turnRight();
        img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, right++, 18).getFxImage();
    }

    public void goUp() {
        down = 0;
        left = 0;
        right = 0;
        e = this.getBounds();
        super.goUp();
        img = Sprite.movingSprite(Sprite.kondoria_left1, Sprite.kondoria_left2, Sprite.kondoria_left3, up++, 18).getFxImage();
    }

    public void goDown() {
        up = 0;
        left = 0;
        right = 0;
        e = this.getBounds();
        super.goDown();
        img = Sprite.movingSprite(Sprite.kondoria_right1, Sprite.kondoria_right2, Sprite.kondoria_right3, down++, 18).getFxImage();
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
