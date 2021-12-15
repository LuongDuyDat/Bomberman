package uet.oop.bomberman.gamemap;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Map {
    protected final int _width = 31, _height = 13;

    Level _level = new Level();

    public ArrayList<String> generateMapByLevel(int _l, int _brick, int _wall) {
        int[] mapStats = _level.getEnemyByLevel(_l - 1);
        int[] mapItems = _level.generateItems();
        int _ballon = mapStats[0];
        int _oneal = mapStats[1];
        int _kondo = mapStats[2];
        int _doll = mapStats[3];
        int _speedItem = mapItems[0];
        int _bombItem = mapItems[1];
        int _frameItem = mapItems[2];
        int _liveItem = mapItems[3];
        int _portal = mapItems[4];
        System.out.println(_kondo);
        int total = (_width - 2) * (_height - 2) - 3;
        int _grass = total - _brick - _wall - _ballon - _oneal - _kondo - _doll - _speedItem - _bombItem - _frameItem - _liveItem - _portal;
        ArrayList<String> map = new ArrayList<>();
        while (true) {
            int brick = _brick;
            int wall = _wall;
            int grass = _grass;
            int _total = total;
            int ballon = _ballon;
            int oneal = _oneal;
            int kondo = _kondo;
            int doll = _doll;
            int speedItem = _speedItem;
            int bombItem = _bombItem;
            int frameItem = _frameItem;
            int liveItem = _liveItem;
            int portal = _portal;
            for (int i = 0; i < _height; i++) {
                StringBuilder sb = new StringBuilder();
                for (int j = 0; j < _width; j++) {
                    if (i == 0 || j == 0 || i == _height - 1 || j == _width - 1) {
                        sb.append("#");
                    } else if (i + j == 2) {
                        sb.append("p");
                    } else if (i + j == 3) {
                        sb.append(" ");
                    } else {
                        int r = (int)(Math.random() * total);
                        if (r < _grass) {
                            sb.append(" ");
                            _grass--;
                            total--;
                        } else if (r < _grass + _brick) {
                            sb.append("*");
                            _brick--;
                            total--;
                        } else if (r < _grass + _brick + _wall) {
                            sb.append("#");
                            _wall--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon) {
                            sb.append("1");
                            _ballon--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal) {
                            sb.append("2");
                            _oneal--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal + _kondo) {
                            sb.append("3");
                            _kondo--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal + _kondo + _doll)  {
                            sb.append("4");
                            _doll--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal + _kondo + _doll + _speedItem) {
                            sb.append("s");
                            _speedItem--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal + _kondo + _doll
                                + _speedItem + _bombItem) {
                            sb.append("b");
                            _bombItem--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal + _kondo + _doll
                                + _speedItem + _bombItem + _frameItem) {
                            sb.append("f");
                            _frameItem--;
                            total--;
                        } else if (r < _grass + _brick + _wall + _ballon + _oneal + _kondo + _doll
                                + _speedItem + _bombItem + _frameItem + _liveItem) {
                            sb.append("l");
                            _liveItem--;
                            total--;
                        } else {
                            sb.append("x");
                            _portal--;
                            total--;
                        }
                    }
                }
                map.add(sb.toString());
            }
            if(checkMap(map)) {
                break;
            }
            map.clear();
            _brick = brick;
            _grass = grass;
            _wall = wall;
            total = _total;
            _ballon = ballon;
            _oneal = oneal;
            _kondo = kondo;
            _doll = doll;
            _bombItem = bombItem;
            _speedItem = speedItem;
            _frameItem = frameItem;
            _liveItem = liveItem;
            _portal = portal;
        }
        return map;
    }

    public boolean checkMap(ArrayList<String> map) {
        boolean[][] flag = new boolean[_height][_width];
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                flag[i][j] = false;
            }
        }

        Queue<Integer> q = new LinkedList<>();
        q.add(_width + 1);
        flag[1][1] = true;
        while (q.peek() != null) {
            int peek = q.remove();
            int x = peek / _width;
            int y = peek % _width;
            /*System.out.print(x);
            System.out.print(' ');
            System.out.println(y);
            System.out.println(map.get(x).charAt(y + 1) != '#');*/
            if (x > 0 && !flag[x - 1][y] && map.get(x - 1).charAt(y) != '#') {
                q.add((x - 1) * _width + y);
                flag[x - 1][y] = true;
            }
            if (x + 1 < _height && !flag[x + 1][y] && map.get(x + 1).charAt(y) != '#') {
                q.add((x + 1) * _width + y);
                flag[x + 1][y] = true;
            }
            if (y > 0 && !flag[x][y - 1] && map.get(x).charAt(y - 1) != '#') {
                q.add(x * _width + (y - 1));
                flag[x][y - 1] = true;
            }
            if (y + 1 < _width && !flag[x][y + 1] && map.get(x).charAt(y + 1) != '#') {
                q.add(x * _width + (y + 1));
                flag[x][y + 1] = true;
            }
        }
        for (int i = 0; i < _height; i++) {
            for (int j = 0; j < _width; j++) {
                if (map.get(i).charAt(j) != '#' && !flag[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}
