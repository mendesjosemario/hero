import com.googlecode.lanterna.TerminalPosition;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;

import java.util.Random;

public class Monster extends Element{

    public Monster(int x, int y) {
        super(new Position(x,y));
    }

    @Override
    public void draw(TextGraphics textGraphics) {
        textGraphics.setForegroundColor(TextColor.Factory.fromString("#000033"));
        textGraphics.putString(new TerminalPosition(getPosition().getX(), getPosition().getY()), "M");
    }

    public Position move() {
        Random random = new Random();
        //Randomly move the X or the Y
        int moveX = random.nextInt(2);

        //Randomly move + or -
        int movePlus = random.nextInt(2);

        return new Position((moveX == 1 ? (movePlus == 1 ? getPosition().getX() + 1:  getPosition().getX() - 1): getPosition().getX()) ,
                            (moveX == 0 ? (movePlus == 1 ? getPosition().getY() + 1:  getPosition().getY() - 1): getPosition().getY()));
    }
}
