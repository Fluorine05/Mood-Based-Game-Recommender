import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Mood {
    private String name;
    private List<Game> games;

    public Mood(String name) {
        this.name = name;
        this.games = new ArrayList<>();
    }

    public void addGame(Game game) {
        games.add(game);
    }

    public Game getRandomGame() {
        if (games.isEmpty()) return null;
        return games.get(new Random().nextInt(games.size()));
    }

    public String getName() {
        return name;
    }
}