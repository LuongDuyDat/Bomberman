package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;
import uet.oop.bomberman.entities.*;
import uet.oop.bomberman.gamemap.Map;
import uet.oop.bomberman.graphics.Sprite;

import java.util.ArrayList;
import java.util.List;

public class BombermanGame extends Application {
    
    public static final int WIDTH = 31;
    public static final int HEIGHT = 13;

    private GraphicsContext gc;
    private Canvas canvas;
    private Map map = new Map();
    private int _level;
    private List<Entity> allEntities = new ArrayList<>();
    private List<Entity> allStillObjects = new ArrayList<>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static Entity [][] ObjectMap = new Entity[HEIGHT][WIDTH];
    public static int [][] BombMap = new int[HEIGHT][WIDTH];
    public static Bomber bomberman;
    public static int n_flame = 2;

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
                    Bomb bomb = new Bomb(x, y, Sprite.bomb_2.getFxImage());
                    stillObjects.add(bomb);
                    bomb.TimeStart = System.currentTimeMillis();
                    bomb.explosion();
                    for (int i = 0;i < n_flame - 1; i++) {
                        if (!(ObjectMap[y][x-1-i] instanceof Wall)) {
                            Flame flame_left = new Flame(x - 1 - i, y, null);
                            stillObjects.add(flame_left);
                            flame_left.left();
                            testBrick(y, x-1-i);
                        }
                        if (!(ObjectMap[y][x+1+i] instanceof Wall)) {
                            Flame flame_right = new Flame(x + 1 + i, y, null);
                            stillObjects.add(flame_right);
                            flame_right.right();
                            testBrick(y, x+1+i);
                        }
                        if (!(ObjectMap[y-1-i][x] instanceof Wall)) {
                            Flame flame_up = new Flame(x, y - 1 - i, null);
                            stillObjects.add(flame_up);
                            flame_up.up();
                            testBrick(y-1-i, x);
                        }
                        if (!(ObjectMap[y+1+i][x] instanceof Wall)) {
                            Flame flame_down = new Flame(x, y + 1 + i, null);
                            stillObjects.add(flame_down);
                            flame_down.down();
                            testBrick(y+1+i, x);
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
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object;
                char x = m.get(i).charAt(j);
                if (x == '#') {
                    object = new Wall(j, i, Sprite.wall.getFxImage());
                } else if (x == ' ' || x == 'p') {
                    object = new Grass(j, i, Sprite.grass.getFxImage());
                } else {
                    object = new Brick(j, i, Sprite.brick.getFxImage());
                }
                ObjectMap[i][j] = object;
                stillObjects.add(object);
            }
        }
    }

    public void update() {
        entities.forEach(Entity::update);
    }

    public void render() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        stillObjects.forEach(g -> g.render(gc));
        entities.forEach(g -> g.render(gc));
    }

    public void testBrick(int y, int x) {
        if (ObjectMap[y][x] instanceof Brick) {
            Brick b = (Brick) ObjectMap[y][x];
            b.exploded();
        }
    }
}
