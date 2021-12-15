package uet.oop.bomberman.entities.enemy;

import javafx.scene.image.Image;
import uet.oop.bomberman.BombermanGame;
import uet.oop.bomberman.entities.Brick;
import uet.oop.bomberman.entities.Entity;
import uet.oop.bomberman.entities.Wall;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.LinkedList;
import java.util.Queue;

public class Oneal extends Enemy{

    protected final int _width = 31, _height = 13;
    private int direct;
    private int staight = 32;

    public Oneal(int xUnit, int yUnit, Image img) {
        super(xUnit, yUnit, img);
        alive = true;
        setLayer(1);
        setSpeed(1);
    }

    @Override
    public void update() {
        if (!alive) {
            if (animated < 40) {
                notMove();
                animated++;
                img = Sprite.oneal_dead.getFxImage();
            } else {
                BombermanGame.enemies.remove(this);
                BombermanGame.point += 70;
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

    @Override
    public void notMove() {
        x = (x / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
        y = (y / Sprite.SCALED_SIZE) * Sprite.SCALED_SIZE;
        super.notMove();
        direct = generateDirect();
        staight = 0;
    }

    public void turnLeft() {
        right = 0;
        up = 0;
        down = 0;
        super.turnLeft();
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, left++, 18).getFxImage();
    }

    public void turnRight() {
        left = 0;
        up = 0;
        down = 0;
        super.turnRight();
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, right++, 28).getFxImage();
    }

    public void goUp() {
        down = 0;
        left = 0;
        right = 0;
        super.goUp();
        img = Sprite.movingSprite(Sprite.oneal_left1, Sprite.oneal_left2, Sprite.oneal_left3, up++, 18).getFxImage();
    }

    public void goDown() {
        up = 0;
        left = 0;
        right = 0;
        super.goDown();
        img = Sprite.movingSprite(Sprite.oneal_right1, Sprite.oneal_right2, Sprite.oneal_right3, down++, 18).getFxImage();
    }

    public int generateDirect() {
        if (!BombermanGame.bomberman.isAlive()) {
            return (int)(Math.random() * 4);
        }
        int[][] trace = new int[_height][_width];
        int[][] d = new int[_height][_width];
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                trace[i][j] = 0;
                d[i][j] = 500;
            }
        }
        Queue<Integer> q = new LinkedList<>();
        int x1 = x / Sprite.SCALED_SIZE;
        int y1 = y / Sprite.SCALED_SIZE;
        q.add(y1 * _width + x1);
        d[y1][x1] = 0;
        trace[y1][x1] = -1;
        Entity [][] map = BombermanGame.ObjectMap;
        while (q.peek() != null) {
            int peek = q.remove();
            int i = peek / _width;
            int j = peek % _width;
            if (i == BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE && j == BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE) {
                break;
            }
            if (i > 0 && d[i - 1][j] > d[i][j] + 1 && !(map[i - 1][j] instanceof Wall) && !(map[i - 1][j] instanceof Brick)) {
                q.add((i - 1) * _width + j);
                d[i - 1][j] = d[i][j] + 1;
                trace[i - 1][j] = i * _width + j;
            }
            if (i + 1 < _height && d[i + 1][j] > d[i][j] + 1 && !(map[i + 1][j] instanceof Wall) && !(map[i + 1][j] instanceof Brick)) {
                q.add((i + 1) * _width + j);
                d[i + 1][j] = d[i][j] + 1;
                trace[i + 1][j] = i * _width + j;
            }
            if (j > 0 && d[i][j - 1] > d[i][j] + 1 && !(map[i][j - 1] instanceof Wall) && !(map[i][j - 1] instanceof Brick)) {
                q.add(i * _width + j - 1);
                d[i][j - 1] = d[i][j] + 1;
                trace[i][j - 1] = i * _width + j;
            }
            if (j + 1 < _width && d[i][j + 1] > d[i][j] + 1 && !(map[i][j + 1] instanceof Wall) && !(map[i][j + 1] instanceof Brick)) {
                q.add(i * _width + j + 1);
                d[i][j + 1] = d[i][j] + 1;
                trace[i][j + 1] = i * _width + j;
            }
        }
        int i = BombermanGame.bomberman.getY() / Sprite.SCALED_SIZE;
        int j = BombermanGame.bomberman.getX() / Sprite.SCALED_SIZE;
        /*System.out.println("New Potition");
        System.out.print(y1);
        System.out.print(' ');
        System.out.println(x1);
        System.out.print(desY);
        System.out.print(' ');
        System.out.println(desX);
        System.out.print(i);
        System.out.print(' ');
        System.out.println(j);
        System.out.println(d[i][j]);*/
        if (d[i][j] != 500 && d[i][j] != 0) {
            while (trace[i][j] != y1 * _width + x1) {
                int temp = trace[i][j];
                i = temp / _width;
                j = temp % _width;
            }
            if (i > y1) {
                return 3;
            }
            if (i < y1) {
                return 2;
            }
            if (j < x1) {
                return 0;
            }
            return 1;
        }
        return (int)(Math.random() * 4);
    }

    public void goStraight() {
        if (staight == 32) {
            direct = generateDirect();
            staight = 1;
        } else {
            staight++;
        }
    }
}
