package uet.oop.bomberman.gamemap;

public class Level {
    private final int[][] enemy = {{1, 1, 2, 1}, {3, 3, 0, 0}, {4, 4, 3, 0}, {3, 3, 3, 3}};
    private final int[] item = {2, 2, 2, 1};

    public int[][] getEnemy() {
        return enemy;
    }

    public int[] getItem() {
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

    public int[] generateItems() {
        return item;
    }
}
