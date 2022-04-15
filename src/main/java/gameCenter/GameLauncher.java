package gameCenter;

import gameCenter.games.*;
import gameCenter.storage.Library;

import java.util.Optional;
import java.util.Scanner;
import java.util.function.*;

public class GameLauncher {

    private static final Scanner scanner = new Scanner(System.in);
    public enum GAME_STATE {INITIALIZED, STARTED, FINISHED};

    private final Game game;
    private final Library.Difficulties difficulty;
    private GAME_STATE gameState;


    GameLauncher(Function<Library.Difficulties, Game> gameInitializer,
                 Library.Difficulties difficulty) {
        this.difficulty = difficulty;
        this.game = gameInitializer.apply(difficulty);
        this.gameState = GAME_STATE.INITIALIZED;
    }

    public void launch() {
        game.showRules();

        if(!continueGame())
            return;

        outputStartLine();
        this.gameState = GAME_STATE.STARTED;
        game.start();
        this.gameState = GAME_STATE.FINISHED;
    }

    private boolean continueGame() {
        System.out.println("Do you really want to start the game?");
        while (true) {
            System.out.println("Enter yes or no");
            String response = scanner.next();
            if(response.equals("yes"))
                return true;
            if(response.equals("no"))
                return false;
            System.out.println("Incorrect option. Try again\n");
        }
    }

    public GAME_STATE getGameState() {
        return gameState;
    }

    public void collectData() {
        if(gameState != GAME_STATE.FINISHED)
            throw new IllegalCallerException();

        game.retrieveStats();
    }

    private void outputStartLine() {
        System.out.printf("\nThe game %s have started with %s difficulty\n\n", game.getName(), difficulty);
    }

    public static Optional<Function<Library.Difficulties, Game>> promptGameInitializer() {
        System.out.println("List of available games:");
        Library.outputGameNameList();
        System.out.println("Enter the game name you wish to play");

        String gameName = scanner.next();
        if (!Library.getGames().containsKey(gameName)) {
            System.out.println("There is no such game\n");
            return Optional.empty();
        }

        return Optional.of(Library.getGames().get(gameName));
    }

    public static Optional<Library.Difficulties> promptDifficulty() {
        System.out.println("List of available difficulties");
        Library.outputDifficultyList();
        System.out.println("Enter the game difficulty you want to play on");

        try {
            String inputDifficulty = scanner.next();
            return Optional.of(Library.Difficulties.valueOf(inputDifficulty));
        } catch(IllegalArgumentException e) {
            System.out.println("There is no such game difficulty\n");
            return Optional.empty();
        }
    }
}
