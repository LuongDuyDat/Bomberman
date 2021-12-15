package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.entities.enemy.*;
import uet.oop.bomberman.entities.items.*;
import uet.oop.bomberman.gamemap.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    public static Map map = new Map();
    private int _level;
    public List<Entity> allEntities = new ArrayList<>();
    public List<Entity> allStillObjects = new ArrayList<>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Entity> grasses = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static Entity [][] ObjectMap = new Entity[HEIGHT][WIDTH];
    public static int [][] BombMap = new int[HEIGHT][WIDTH];
    public static Bomber bomberman;
    public static int n_flame = 2;
    public static int point = 0;
    public static int n_bomb = 1;

    public void addAllEntities(Entity e) {
        allEntities.add(e);
    }

    public void addAllStillObjects(Entity e) {
        allEntities.add(e);
    }

    public int get_level() {
        return _level;
    }

    public void set_level(int _level) {
        this._level = _level;
    }

    public static void main(String[] args) {
        Application.launch(BombermanGame.class);
    }

    @Override
    public void start(Stage stage) {
        // Tao Canvas
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(scene);
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                render();
                update();
            }
        };
        timer.start();

        createMap();

        bomberman = new Bomber(1, 1, Sprite.player_right.getFxImage());
        entities.add(bomberman);

        scene.setOnKeyPressed(event -> {
            int x = bomberman.getX() / Sprite.SCALED_SIZE;
            int x1 = bomberman.getX();
            int y = bomberman.getY() / Sprite.SCALED_SIZE;
            int y1 = bomberman.getY();
            switch (event.getCode()) {
                case RIGHT: {
                    if(ObjectMap[y][x+1] instanceof Wall||ObjectMap[y][x+1] instanceof Brick || BombMap[y][x+1]==1) {
                        bomberman.moveRight1();
                    } else if (ObjectMap[y][x+1] instanceof Grass && x1 % 32 == 0 && y1 % 32 == 0 && BombMap[y][x+1]!=1) {
                        bomberman.moveRight();
                    }
                }
                break;
                case LEFT: {
                    if(ObjectMap[y][x-1] instanceof Wall||ObjectMap[y][x-1] instanceof Brick||BombMap[y][x-1]==1) {
                        bomberman.moveLeft1();
                    } else if (ObjectMap[y][x-1] instanceof Grass && x1 % 32 == 0 && y1 % 32 == 0 && BombMap[y][x-1]!=1) {
                        bomberman.moveLeft();
                    }
                }
                break;
                case DOWN: {
                    if(ObjectMap[y+1][x] instanceof Wall || ObjectMap[y+1][x] instanceof Bomb || BombMap[y+1][x]==1) {
                        bomberman.moveDown1();
                    } else if ((ObjectMap[y+1][x] instanceof Grass) && y1 % 32 == 0&& x1 % 32 == 0 && BombMap[y+1][x]!=1) {
                        bomberman.moveDown();
                    }
                }
                break;
                case UP: {
                    if(ObjectMap[y-1][x] instanceof Wall||ObjectMap[y-1][x] instanceof Brick || BombMap[y-1][x]==1) {
                        bomberman.moveUp1();
                    } else if (ObjectMap[y-1][x] instanceof Grass && x1 % 32 == 0 && y1 % 32 == 0 && BombMap[y-1][x]!=1) {
                        bomberman.moveUp();
                    }
                }
                break;
                case SPACE: {
                    if (n_bomb > 0) {
                        n_bomb--;
                        Bomb bomb = new Bomb(x, y, Sprite.bomb_2.getFxImage());
                        stillObjects.add(bomb);
                        BombMap[y][x] = 1;
                        bomb.TimeStart = System.currentTimeMillis();
                        bomb.explosion();
                        boolean left = true;
                        boolean right = true;
                        boolean up = true;
                        boolean down = true;
                        for (int i = 0;i < n_flame - 1; i++) {
                            if (!(ObjectMap[y][x-1-i] instanceof Wall) && left) {
                                Flame flame_left = new Flame(x - 1 - i, y, null);
                                flame_left.left();
                                testBrick(y, x-1-i);
                            } else {
                                left = false;
                            }
                            if (!(ObjectMap[y][x+1+i] instanceof Wall) && right) {
                                Flame flame_right = new Flame(x + 1 + i, y, null);
                                flame_right.right();
                                testBrick(y, x+1+i);
                            } else {
                                right = false;
                            }
                            if (!(ObjectMap[y-1-i][x] instanceof Wall) && up) {
                                Flame flame_up = new Flame(x, y - 1 - i, null);
                                flame_up.up();
                                testBrick(y-1-i, x);
                            } else {
                                up = false;
                            }
                            if (!(ObjectMap[y+1+i][x] instanceof Wall) && down) {
                                Flame flame_down = new Flame(x, y + 1 + i, null);
                                flame_down.down();
                                testBrick(y+1+i, x);
                            } else {
                                down = false;
                            }
                        }
                    }

                }
                default:
                    break;
            }
        });

    }

    public void createMap() {
        ArrayList<String> m = map.generateMapByLevel(1, 40, 60);
        for (int i = 0; i < m.size(); i++) {
            System.out.println(m.get(i));
        }
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                char x = m.get(i).charAt(j);
                if (x == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                    stillObjects.add(object);
                } else {
                    grasses.add(new Grass(j, i, Sprite.grass.getFxImage()));
                    if (x == ' ' || x == 'p') {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                    } else if (x == '*') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                    } else if (x == '1') {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        enemies.add(new Baloon(j, i, Sprite.balloom_left1.getFxImage()));
                    } else if (x == '2') {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        enemies.add(new Oneal(j, i, Sprite.oneal_left1.getFxImage()));
                    } else if (x == '3') {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        enemies.add(new Kondoria(j, i, Sprite.kondoria_left1.getFxImage()));
                    } else if (x == '4') {
                        object = new Grass(j, i, Sprite.grass.getFxImage());
                        enemies.add(new Doll(j, i, Sprite.doll_left1.getFxImage()));
                    } else if (x == 's') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        items.add(new SpeedItem(j, i, Sprite.powerup_speed.getFxImage()));
                    } else if (x == 'b') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        items.add(new BombItem(j, i, Sprite.powerup_bombs.getFxImage()));
                    } else if (x == 'f') {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        items.add(new FramItem(j, i, Sprite.powerup_flames.getFxImage()));
                    } else {
                        object = new Brick(j, i, Sprite.brick.getFxImage());
                        stillObjects.add(object);
                        items.add(new Portal(j, i, Sprite.portal.getFxImage()));
                    }
                }
                ObjectMap[i][j] = object;
            }
        }
    }

    public void update() {
        for (int i = 0; i < entities.size(); i++) {
            entities.get(i).update();
        }
        for (int i = 0; i < enemies.size(); i++) {
            enemies.get(i).update();
        }
        for (int i = 0; i < items.size(); i++) {
            items.get(i).update();
        }
        handleBomberCollisions();
        handleEnemiesCollisions();
        handleFlamesCollisions();
    }

    public void handleFlamesCollisions() {
        for (Flame f: flames) {
            Rectangle r = f.getBounds();

            if (r.intersects(BombermanGame.bomberman.getBounds())) {
                bomberman.setAlive(false);
                bomberman.die();
            }

            for (Item i: items) {
                Rectangle item = i.getBounds();
                int x = i.getX() / Sprite.SCALED_SIZE;
                int y = i.getY() / Sprite.SCALED_SIZE;
                if (r.intersects(item) && !(ObjectMap[y][x] instanceof Brick)) {
                    i.setAlive(false);
                }
            }

            for (Enemy e: enemies) {
                Rectangle ene = e.getBounds();
                if (r.intersects(ene)) {
                    e.setAlive(false);
                }
            }
        }
    }

    public void handleBomberCollisions() {
        Rectangle r = bomberman.getBounds();

        for (Enemy e: enemies) {
            Rectangle ene = e.getBounds();
            if (r.intersects(ene)) {
                bomberman.setAlive(false);
                bomberman.die();
            }
        }

        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            Rectangle r1 = item.getBounds();
            int x = item.getX() / Sprite.SCALED_SIZE;
            int y = item.getY() / Sprite.SCALED_SIZE;
            if (r.intersects(r1) && !(ObjectMap[y][x] instanceof Brick)) {
                if (!(item instanceof Portal)) {
                    item.setAlive(false);
                    if (item instanceof BombItem) {
                        n_bomb++;
                    }
                    if (item instanceof FramItem) {
                        n_flame++;
                    }
                    if (item instanceof SpeedItem) {
                        if (bomberman.getV() > 20) {
                            bomberman.v -= 10;
                        }
                    }
                }
            }
        }
    }

    public void handleEnemiesCollisions() {
        /**
         * Enemy with stillobject
         */
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            Rectangle r = e.getBounds();
            for (int j = 0; j < stillObjects.size(); j++) {
                Entity s = stillObjects.get(j);
                Rectangle r1 = s.getBounds();
                if (r.intersects(r1) && e.getLayer() < s.getLayer()) {
                    e.notMove();
                }
            }
            e.move();
        }
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        items.forEach(g->g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        flames.forEach(g->g.render(gc));
        entities.forEach(g -> g.render(gc));
        enemies.forEach(g-> g.render(gc));
    }

    public void testBrick(int y, int x) {
        if (ObjectMap[y][x] instanceof Brick) {
            Brick b = (Brick) ObjectMap[y][x];
            b.exploded();
        }
    }
}
