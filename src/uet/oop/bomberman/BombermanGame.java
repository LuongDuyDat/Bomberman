package uet.oop.bomberman;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
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
    public int _level = 1;
    public List<Entity> allEntities = new ArrayList<>();
    public List<Entity> allStillObjects = new ArrayList<>();
    public static List<Entity> entities = new ArrayList<>();
    public static List<Entity> stillObjects = new ArrayList<>();
    public static List<Enemy> enemies = new ArrayList<>();
    public static List<Entity> grasses = new ArrayList<>();
    public static List<Flame> flames = new ArrayList<>();
    public static List<Item> items = new ArrayList<>();
    public static Entity [][] ObjectMap = new Entity[HEIGHT + 2][WIDTH];
    public static int [][] BombMap = new int[HEIGHT + 2][WIDTH];
    public static Bomber bomberman;
    public static int n_flame = 2;
    public static int point = 0;
    public static int n_bomb = 1;
    private boolean run = false;
    private Label start;
    private Label exit;
    private Label playAgain;
    private int time = 0;
    private boolean newMap = true;
    public static int lives = 3;
    public int gameTime = 300;
    public static final List<Entity> life = new ArrayList<>();
    public static final List<Entity> bomb = new ArrayList<>();
    public boolean reload = true;

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

        stage.setScene(startGame());
        start.setOnMouseClicked(event -> {
            run = true;
            loadMap();
        });
        // Tao Canvas
        /*canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * HEIGHT);
        gc = canvas.getGraphicsContext2D();

        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);

        // Tao scene
        Scene scene = new Scene(root);

        // Them scene vao stage
        stage.setScene(startGame());
        stage.show();*/

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if (run) {
                    if (newMap) {
                        if (time < 120) {
                            stage.setScene(loadMessage());
                            time++;
                        } else {
                            stage.setScene(loadScene());
                            newMap = false;
                            System.out.println("Load Scene");
                            System.out.println(newMap);
                            System.out.println(run);
                        }
                    } else {
                        render();
                        update();
                        if (checkNextMap()) {
                            nextMap();
                            time = 0;
                            if (time < 120) {
                                stage.setScene(loadMessage());
                                time++;
                            } else {
                                stage.setScene(loadScene());
                                newMap = false;
                            }
                        }

                        if (lives <= 0 || gameTime == 0) {
                            stage.setScene(gameOver("GAME OVER"));
                        }
                    }
                }
            }
        };
        timer.start();
        stage.show();
        /*createMap();

        */

    }

    public void loadMap() {
        createMap();
        createUpperPane_life();
        createUpperPane_bomb();
    }


    private void nextMap() {
        _level++;
        newMap = true;


        stillObjects.clear();
        items.clear();
        enemies.clear();
        grasses.clear();
        entities.clear();
        loadMap();
    }

    public static void createUpperPane_life() {
        life.clear();
        for (int i = 25; i < 25 + lives; i++) {
            int j = 1;
            life.add(new Brick(i, j, Sprite.life.getFxImage()));
        }
    }

    public static void createUpperPane_bomb() {
        bomb.clear();
        for (int i = 25; i < 25 + n_bomb; i++) {
            int j = 0;
            bomb.add(new Brick(i, j, Sprite.bomb.getFxImage()));
        }
    }

    private boolean checkNextMap() {
        if (enemies.isEmpty()) {
            Rectangle r1 = bomberman.getBounds();
            for (int i = 0; i < items.size(); i++) {
                if (items.get(i) instanceof Portal) {
                    Rectangle r2 = items.get(i).getBounds();
                    if (r1.intersects(r2))
                        return true;
                }
            }
        }
        return false;
    }

    public Scene loadScene() {
        canvas = new Canvas(Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 2));
        gc = canvas.getGraphicsContext2D();
        entities.clear();
        // Tao root container
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root);
        bomberman = new Bomber(1, 3, Sprite.player_right.getFxImage());
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
        return scene;
    }

    public void createMap() {
        gameTime = 200 * 100;
        if (_level > 1) {
            point = 0;
            n_bomb = 1;
            lives = 3;
        }
        ArrayList<String> m = map.generateMapByLevel(_level, 40, 60);
        for (int i = 0; i < m.size(); i++) {
            System.out.println(m.get(i));
        }
        for (int i = 2; i < HEIGHT + 2; i++) {
            for (int j = 0; j < WIDTH; j++) {
                Entity object = null;
                char x = m.get(i - 2).charAt(j);
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
                BombMap[i][j] = 0;
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
        if (bomberman.isAlive()) handleBomberCollisions();
        handleEnemiesCollisions();
        handleFlamesCollisions();
    }

    public void handleFlamesCollisions() {
        for (Flame f: flames) {
            Rectangle r = f.getBounds();

            if (r.intersects(BombermanGame.bomberman.getBounds()) && bomberman.isAlive()) {
                bomberman.setAlive(false);
                bomberman.die();
                lives--;
                createUpperPane_life();

                System.out.println(lives);
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
                lives--;
                createUpperPane_life();
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
                        createUpperPane_bomb();
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

        gc.setFill(Color.AQUA);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.AZURE);
        for (int i = 0; i < canvas.getWidth(); ) {
            gc.fillRect(i, 0, 32 * 1.3, 32 * 2);
            i += 32 * 2.5;
        }

        gc.setFill(Color.AQUA);
        gc.fillRect(0, 32 * 2, canvas.getWidth(), canvas.getHeight());
        grasses.forEach(g -> g.render(gc));
        items.forEach(g->g.render(gc));
        life.forEach(g -> g.render(gc));
        bomb.forEach(g -> g.render(gc));
        stillObjects.forEach(g -> g.render(gc));
        flames.forEach(g->g.render(gc));
        entities.forEach(g -> g.render(gc));
        enemies.forEach(g-> g.render(gc));

        gc.setFill(Color.BLACK);
        if (gameTime > 0) {
            gameTime--;
            if (gameTime < 30 * 100) {
                gc.setFill(Color.RED);
            }
            gc.setFont(new Font("Default Font", 30));
            gc.fillText("Time : " + gameTime / 100, 14, 50);
        }

        gc.setFill(Color.BLACK);
        gc.setFont(new Font("Default Font", 50));
        gc.fillText("" + point, 448, 50);
    }

    private Scene startGame() {
        Image Start = new Image("images/start.png");
        BackgroundImage backgroundImage = new BackgroundImage(
                Start,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                BackgroundSize.DEFAULT
        );
        Background background = new Background(backgroundImage);
        start = new Label("START");
        start.setTextFill(Color.BLACK);
        start.setPrefSize(150,  65);
        start.setFont(new Font(30));
        start.setLayoutX(465);
        start.setLayoutY(360);
        start.setOnMouseEntered(mouseEvent -> {
            start.setTextFill(Color.GRAY);
        });
        start.setOnMouseExited(mouseEvent -> {
            start.setTextFill(Color.BLACK);
        });
        AnchorPane game = new AnchorPane(start);
        game.setBackground(background);
        return new Scene(game, Sprite.SCALED_SIZE * WIDTH, Sprite.SCALED_SIZE * (HEIGHT + 2));
    }
    private Label label(String str, double x, double y, double size) {
        Label lb = new Label(str);
        lb.setLayoutX(x);
        lb.setLayoutY(y);
        lb.setTextFill(Color.WHITE);
        lb.setAlignment(Pos.CENTER);
        lb.setFont(new Font(size));
        return lb;
    }
    private Scene loadMessage() {
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);
        Label lb = label("LEVEL " + _level, 390, 160, 60);
        lb.setTextFill(Color.BLUE);
        AnchorPane anchorPane = new AnchorPane(lb);
        anchorPane.setBackground(background);
        return new Scene(anchorPane, WIDTH * Sprite.SCALED_SIZE, (HEIGHT + 2) * Sprite.SCALED_SIZE);
    }
    private Scene gameOver(String str) {
        BackgroundFill backgroundFill = new BackgroundFill(Color.BLACK, CornerRadii.EMPTY, Insets.EMPTY);
        Background background = new Background(backgroundFill);

        Label yourScore = label("Your Score : " + point, 370, 90, 30);
        yourScore.setTextFill(Color.WHITE);

        Label lb = label(str, 310, 150, 60);
        if (str == "GAME OVER") {
            lb.setTextFill(Color.RED);
        } else {
            lb = label(str, 360, 150, 60);
            lb.setTextFill(Color.GOLD);
        }
        exit = label("EXIT", WIDTH * Sprite.SCALED_SIZE / 2 + 60, (HEIGHT + 2) * Sprite.SCALED_SIZE / 2 + 50, 25);
        playAgain = label("PLAY AGAIN", WIDTH * Sprite.SCALED_SIZE / 2 - 150, (HEIGHT + 2) * Sprite.SCALED_SIZE / 2 + 50, 25);
        AnchorPane close = new AnchorPane(lb, yourScore);
        close.setBackground(background);
        return new Scene(close, WIDTH * Sprite.SCALED_SIZE, (HEIGHT + 2) * Sprite.SCALED_SIZE);
    }

    public void testBrick(int y, int x) {
        if (ObjectMap[y][x] instanceof Brick) {
            Brick b = (Brick) ObjectMap[y][x];
            b.exploded();
        }
    }
}
