import java.util.HashMap;

public class RecommendationEngine {
    private static RecommendationEngine instance = null;
    private HashMap<String, Mood> moods;

    private RecommendationEngine() {
        moods = new HashMap<>();
        loadDefaultMoods();
    }

    public static RecommendationEngine getInstance() {
        if (instance == null) {
            instance = new RecommendationEngine();
        }
        return instance;
    }

    private void loadDefaultMoods() {
        Mood bored = new Mood("Bored");
        bored.addGame(new Game("Spider-Man 2", "PS5"));
        bored.addGame(new Game("Halo Infinite", "Xbox"));
        bored.addGame(new Game("Bayonetta 3", "Switch"));

        Mood sad = new Mood("Sad");
        sad.addGame(new Game("Life is Strange", "PS5"));
        sad.addGame(new Game("Ori and the Blind Forest", "Xbox"));
        sad.addGame(new Game("Celeste", "Switch"));

        moods.put("Bored", bored);
        moods.put("Sad", sad);
    }

    public Game recommend(String moodName) {
        Mood mood = moods.get(moodName);
        return (mood != null) ? mood.getRandomGame() : null;
    }

    public void addGameToMood(String moodName, Game game) {
        moods.computeIfAbsent(moodName, Mood::new).addGame(game);
    }

    public void addMood(String moodName) {
        moods.putIfAbsent(moodName, new Mood(moodName));
    }
    
}