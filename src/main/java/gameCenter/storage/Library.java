package gameCenter.storage;

import gameCenter.games.*;
import java.util.*;
import java.util.function.*;

public class Library {

    public static final String BASE_PATH = "gameCenter/storage/";

    private static final Map<String, Function<Difficulties, Game>> games = Map.of(
            "GuessNumber", GuessNumber::new,
            "GuessWord", GuessWord::new
    );

    public enum Difficulties { Easy, Medium, Hard }

    private static final Map<Library.Difficulties, String> wordsFilePath = Map.of(
            Library.Difficulties.Easy, BASE_PATH + "easy_words.txt",
            Library.Difficulties.Medium, BASE_PATH + "medium_words.txt",
            Library.Difficulties.Hard, BASE_PATH + "hard_words.txt"
    );

    public static String getWordsFilePath(Difficulties difficulty) {
        return wordsFilePath.get(difficulty);
    }

    public static Map<String, Function<Difficulties, Game>> getGames() {
        return games;
    }

    public static void outputGameNameList() {
        System.out.println(listToString(games.keySet()));
    }

    public static void outputDifficultyList () {
        System.out.println(listToString(Arrays.stream(Difficulties.values()).map(Enum::toString).toList()));
    }

    private static String listToString(Iterable<String> collection) {
        var sB = new StringBuilder();
        int position = 1;
        for(String s : collection) {
            sB.append(String.format("%s. %s,\n", position++, s));
        }
        return sB.deleteCharAt(sB.length()-2).toString();
    }

}
