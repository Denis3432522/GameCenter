package gameCenter.games;

public interface RoundGame {
    default String stringifyRound(int round) {
        if(round < 1)
            throw new RuntimeException("Invalid numeral representation of the round");

        return switch(round) {
            case 1 -> "First";
            case 2 -> "Second";
            case 3 -> "Third";
            default -> round + "th";
        };
    }
}
