import java.io.IOException;

public class Application {

    public static void main(String[] args) {
        try {
            Game game = new Game(40,20);
            game.run();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
