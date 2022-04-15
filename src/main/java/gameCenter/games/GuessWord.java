package gameCenter.games;

import gameCenter.storage.Library;
import gameCenter.storage.SingleWordLists;

import java.io.*;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Pattern;

public class GuessWord implements Game, RoundGame {

    private final String name = "GuessWord";
    private final Scanner scanner = new Scanner(System.in);
    private final Library.Difficulties difficulty;

    private int letterAttempts;
    private int wordAttempts;
    private final int wordLen;
    private final String answer;
    private StringBuilder currWord;

    private int round = 1;

    public GuessWord(Library.Difficulties difficulty) {
        this.difficulty = difficulty;

        switch (this.difficulty) {
            case Easy -> {
                letterAttempts = 4;
                wordAttempts = 4;
                wordLen = 4;
            }
            case Medium -> {
                letterAttempts = 4;
                wordAttempts = 4;
                wordLen = 6;
            }
            case Hard -> {
                letterAttempts = 4;
                wordAttempts = 4;
                wordLen = 8;
            }
            default -> throw new RuntimeException("Incorrect difficulty");
        }
        answer = generateAnswer();
        currWord = getCurrWord();
    }

    private String generateAnswer() {
        List<String> list = SingleWordLists.get(difficulty);
        return list.get(new Random().nextInt(list.size()));
    }

    public String getName() {
        return this.name;
    }

    public void showRules() {
        System.out.println("The \"Guess Word\" game rules");
        System.out.println("You need to guess the word");
        System.out.printf("You have %s attempts to guess letters and %s attempts to guess the word\n", letterAttempts, wordAttempts);
        System.out.println("Enter only lowercase alphabetical characters");
    }

    public String retrieveStats() {
        return null;
    }

    public void start() {
        while(wordAttempts >= 1) {
            if(letterAttempts == 0) {
                System.out.println("\n!!! You've used all your letter attempts !!!\n");
                letterAttempts = -1;
            }

            nextRound();
            if(checkAnswer()) {
                System.out.println("You won!");
                return;
            }
            round++;
        }
        System.out.println("Unfortunately, you lost the game.");
    }

    private void nextRound() {
        System.out.println(stringifyRound(round) + " round");
        System.out.println("Current word is " + currWord);
        System.out.printf("You have: %s word attempts, %s letter attempts\n", wordAttempts, letterAttempts == -1 ? 0 : letterAttempts);

        while(true) {
            try {
                if(letterAttempts == -1)
                    handleWord();
                else
                    handleLetterAndWord();
                return;
            } catch(IOException e) {
                System.out.println(e.getMessage());
                System.out.println("Please try again\n");
            }
        }
    }

    private void handleLetterAndWord() throws IOException {
        System.out.println("Enter a letter or the whole word");
        String input = scanner.next();

        if(input.length() == 1) {
            char ch = input.charAt(0);
            if(!isLetterValid(ch))
                throw new IOException("Letter must be lowercase alphabetic character (a-z)");
            checkLetter(ch);
            letterAttempts--;
        } else {
            if(!isWordValid(input))
                throw new IOException("Word must have both " + wordLen + " chars length and only lowercase alphabetic chars (a-z)");
            checkWord(input);
            wordAttempts--;
        }
    }

    private void handleWord() throws IOException {
        System.out.println("Enter the word");
        String input = scanner.next();

        if(!isWordValid(input))
            throw new IOException("Word must have both " + wordLen + " chars length and only lowercase alphabetic chars (a-z)");

        checkWord(input);
        wordAttempts--;
    }

    private void checkWord(String word) {
        if(word.equals(answer)) {
            currWord = new StringBuilder(word);
        }
    }

    private void checkLetter(char ch) {
        int i = 0;
        while((i = answer.indexOf(ch, i)) != -1) {
            currWord.replace(i, i+1, "" + ch);
            i++;
        }
    }

    private boolean checkAnswer() {
        return !currWord.toString().contains("*");
    }

    private boolean isWordValid(String word) {
        return word.matches("[a-z]{"+wordLen+"}");
    }

    private boolean isLetterValid(char ch) {
        return ch >= 97 && ch <= 122;
    }

    private StringBuilder getCurrWord() {
        return new StringBuilder("*".repeat(answer.length()));
    }

}


