import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Arena {
    private final int width;
    private final int height;
    private final Hero hero;
    private final List<Wall> walls;
    private final List<Coin> coins;
    private final List<Monster> monsters;

    public Arena(int width, int height) {
        this.width = width;
        this.height = height;
        this.hero = new Hero(10,10);
        this.walls = this.createWalls();
        this.coins = this.createCoins();
        this.monsters = this.createMonsters();
    }

    private List<Monster> createMonsters() {
        Random random = new Random();
        ArrayList<Monster> monsters = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position position;
            do {
                position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            } while (position.equals(hero.getPosition()));
            monsters.add(new Monster(position.getX(), position.getY()));
        }

        return monsters;
    }

    private List<Coin> createCoins() {
        Random random = new Random();
        ArrayList<Coin> coins = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            Position position ;
            do {
                position = new Position(random.nextInt(width - 2) + 1, random.nextInt(height - 2) + 1);
            } while (position.equals(hero.getPosition()));
            coins.add(new Coin(position.getX(), position.getY()));
        }
        return coins;
    }

    private List<Wall> createWalls() {
        List<Wall> walls = new ArrayList<>();
        for (int c = 0; c < width; c++) {
            walls.add(new Wall(c, 0));
            walls.add(new Wall(c, height - 1));
        }
        for (int r = 1; r < height - 1; r++) {
            walls.add(new Wall(0, r));
            walls.add(new Wall(width - 1, r));
        }
        return walls;
    }

    public void draw(TextGraphics textGraphics) {
        textGraphics.setBackgroundColor(TextColor.Factory.fromString("#336699"));
        textGraphics.fillRectangle(new TerminalPosition(0, 0), new TerminalSize(width, height), ' ');
        hero.draw(textGraphics);

        for (Wall wall : walls) {
            wall.draw(textGraphics);
        }
        for (Coin coin : coins) {
            coin.draw(textGraphics);
        }
        for (Monster monster : monsters) {
            monster.draw(textGraphics);
        }
    }

    public void processKey(KeyStroke key) {
        System.out.println(key);
        switch (key.getKeyType()) {
            case ArrowUp: moveHero(hero.moveUp()); break;
            case ArrowDown: moveHero(hero.moveDown()); break;
            case ArrowLeft: moveHero(hero.moveLeft()); break;
            case ArrowRight: moveHero(hero.moveRight()); break;
        }
        retrieveCoins();
        moveMonsters();
    }

    private void moveHero(Position position) {
        if (canMove(position)) {
            hero.setPosition(position);
        }
    }

    private boolean canMove(Position position) {
        for (Wall w: walls) {
            if (w.getPosition().equals(position)) {
                return false;
            }
        }
        return true;
    }

    private void moveMonsters() {
        for (Monster m: monsters) {
            Position p;
            do {
                p = m.move();
            } while (!canMove(p));
            m.setPosition(p);
        }
    }

    public boolean verifyMonsterCollisions() {
        for (Monster m: monsters) {
            if (m.getPosition().equals(hero.getPosition())) {
                return true;
            }
        }
        return false;
    }

    public void retrieveCoins() {
        for (Coin c: coins) {
            if (c.getPosition().equals(hero.getPosition())) {
                coins.remove(c);
                break;
            }
        }

    }
}
