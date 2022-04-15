package gameCenter.games;

import gameCenter.storage.Library;

import java.util.Random;
import java.util.Scanner;

public class GuessNumber implements Game, RoundGame{

    private final String name = "GuessNumber";
    private final Scanner scanner = new Scanner(System.in);

    private final int from;
    private final int to;
    private final int attempts;
    private final int answer;
    private int round = 1;

    public GuessNumber(Library.Difficulties difficulty) {
        from = 1;

        switch (difficulty) {
            case Easy -> {
                to = 10;
                attempts = 5;
            }
            case Medium -> {
                to = 20;
                attempts = 4;
            }
            case Hard -> {
                to = 30;
                attempts = 3;
            }
            default -> throw new RuntimeException("Incorrect difficulty");
        }
        answer = generateAnswer(from, to);
    }
    
    public String getName() {
        return this.name;
    }

    private int generateAnswer(int from, int to) throws NumberFormatException {
        Random random = new Random();
        return random.nextInt(to+1) + from;
    }

    public void showRules() {
        System.out.println("The GuessNumber game rules");
        System.out.printf("You need to guess a number that is random in a range of %s and %s.\n", from, to);
        System.out.println("If you enter an wrong number, you will be hinted whether the correct number is lower or higher");
        System.out.printf("You have %s attempts\n\n", attempts);
    }

    public void start() {
        while(round <= attempts) {
            if(nextRound()) {
                System.out.println("You won!");
                return;
            }
            round++;
        }
        System.out.println("You have used all of the available attempts.");
        System.out.println("Unfortunately, you loss.");
    }

    private boolean nextRound() {
        System.out.println(stringifyRound(round) + " round\n");
        System.out.println("Enter the number");

        while(true) {
            String str = scanner.next();
            try {
                int number = Integer.parseInt(str);
                return checkAnswer(number);

            } catch(NumberFormatException e) {
                System.out.println("Invalid number. Try again.");
            }
        }
    }

    private boolean checkAnswer(int number) {
        if(number < from || number > to)
            throw new NumberFormatException();

        if(number == answer)
            return true;

        if(number > answer)
            System.out.println("You entered the number that is greater than the answer\n");
        else {
            System.out.println("You entered the number that is lower than the answer\n");
        }
        return false;
    }

    public String retrieveStats() {
        return null;
    }
}
