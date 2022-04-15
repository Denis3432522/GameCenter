package gameCenter.storage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SingleWordLists {
    private static final Map<Library.Difficulties, List<String>> dictionaries = new HashMap<>();

    public static List<String> get(Library.Difficulties difficulty) {
        if(!dictionaries.containsKey(difficulty))
            setWordList(difficulty);
        return dictionaries.get(difficulty);
    }

    private static void setWordList(Library.Difficulties difficulty) {
        try(BufferedReader reader = new BufferedReader(new FileReader(Library.getWordsFilePath(difficulty)))) {
            List<String> dict = new ArrayList<>();
            String line;
            while((line = reader.readLine()) != null) {
                dict.add(line);
            }
            dictionaries.put(difficulty, dict);
        } catch(IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}