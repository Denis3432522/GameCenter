package gameCenter.games;

public interface Game {

    String getName();

    void showRules();

    void start();

    String retrieveStats();
}
