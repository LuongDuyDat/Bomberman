package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;

public abstract class Enemy extends Entity {
    protected int desX = x;
    protected int desY = y;
    protected int speed;
    protected int left = 0;
    protected int right = 0;
    protected int up = 0;
    protected int down = 0;

    public Enemy(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
    }

    public int getDesX() {
        return desX;
    }

    public void setDesX(int desX) {
        this.desX = desX;
    }

    public int getDesY() {
        return desY;
    }

    public void setDesY(int desY) {
        this.desY = desY;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getLeft() {
        return left;
    }

    public void setLeft(int left) {
        this.left = left;
    }

    public int getRight() {
        return right;
    }

    public void setRight(int right) {
        this.right = right;
    }

    public int getUp() {
        return up;
    }

    public void setUp(int up) {
        this.up = up;
    }

    public int getDown() {
        return down;
    }

    public void setDown(int down) {
        this.down = down;
    }

    public void move() {
        x = desX;
        y = desY;
    }

    public void notMove() {
        desX = x;
        desY = y;
    }

    public void turnLeft() {
        desX = x - speed;
    }

    public void turnRight() {
        desX = x + speed;
    }

    public void goUp() {
        desY = y - speed;
    }

    public void goDown() {
        desY = y + speed;
    }

    public Rectangle getBounds() {
        return new Rectangle(desX, desY, Sprite.SCALED_SIZE, Sprite.SCALED_SIZE);
    }

    public Rectangle getBoundsArea(int n) {
        return new Rectangle(desX - Sprite.SCALED_SIZE * n, desY - Sprite.SCALED_SIZE * n, Sprite.SCALED_SIZE * (1 + 2 * n), Sprite.SCALED_SIZE * (1 + 2 * n));
    }
}
