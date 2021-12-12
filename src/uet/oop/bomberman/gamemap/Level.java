package uet.oop.bomberman.gamemap;

public class Level {
    private final int[][] enemy = {{8, 0, 0, 0}, {5, 5, 0, 0}, {4, 4, 3, 0}, {3, 3, 3, 3}};
    private final int[][] item = {{}};

    public int[][] getEnemy() {
        return enemy;
    }

    public int[][] getItem() {
        return item;
    }

    public int[] generateRandomEnemy() {
        int[] newArray = new int[4];
        for (int i = 0; i < 4; i++) {
            newArray[i] = (int) (Math.random() * 7);
        }
        return newArray;
    }

    public int[] getEnemyByLevel(int level) {
        if (level < 4) {
            return enemy[level];
        }
        return generateRandomEnemy();
    }
}
