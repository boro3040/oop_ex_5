/*
Barak Davidovitch
211604350
OOP BIU 2024
 */

/**
 * class that runs the Game.
 */
public class Ass3Game {

    /**
     * the maim method that creating the game, initialize it, and running it.
     * @param args command line arguments (not in use).
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
    }
}
