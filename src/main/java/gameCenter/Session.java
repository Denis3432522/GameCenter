package gameCenter;

import gameCenter.games.*;
import gameCenter.storage.Library;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

public class Session {
    private final Scanner scanner = new Scanner(System.in);
    private final String nickname;

    public Session() {
        Pattern pattern = Pattern.compile("[\\W]");
        while(true) {
            System.out.println("Enter your nickname");
            String input = scanner.next();
            if(pattern.matcher(input).find()) {
                System.out.println("Nickname can contain only letters and digits. (For example, \"Ineffable228\")");
            } else if (input.length() < 3 || input.length() > 16) {
                System.out.println("Nickname length must be at least 3 characters and at most 16 character\n");
            } else {
                nickname = input;
                System.out.println("You logged in!\n");
                break;
            }
        }
    }

    public void startSession() {

        while(true) {
            System.out.println("""
                    Enter play if you want to play.\s
                    Enter stats if you want to view statistics.\s
                    Enter exit if you want to exit.""");
            String action = scanner.next();

            switch (action) {
                case "play"  -> gameHandler();
                case "stats" -> statisticHandler();
                case "exit"  -> {
                    System.out.println("Exiting");
                    return;
                }
                default -> System.out.println("You've entered an wrong option\n");
            }
        }
    }

    private void gameHandler() {
        Function<Library.Difficulties, Game> gameInitializer;
        Library.Difficulties difficulty;

        while(true) {
            Optional<Function<Library.Difficulties, Game>> o = GameLauncher.promptGameInitializer();
            if(o.isPresent()) {
                gameInitializer = o.get();
                break;
            }
        }
        while(true) {
            Optional<Library.Difficulties> o = GameLauncher.promptDifficulty();
            if(o.isPresent()) {
                difficulty = o.get();
                break;
            }
        }

        GameLauncher launcher = new GameLauncher(gameInitializer, difficulty);
        launcher.launch();

        if(launcher.getGameState() == GameLauncher.GAME_STATE.FINISHED)
            launcher.collectData();
    }

    private void statisticHandler() {

    }



}
