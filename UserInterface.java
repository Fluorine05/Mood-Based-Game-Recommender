import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class UserInterface extends Application {
    private RecommendationEngine engine = RecommendationEngine.getInstance();
    private ObservableList<String> previousRecommendations = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        // Add more moods and default games
        String[] moods = {"Bored", "Sad", "Excited", "Relaxed", "Stressed", "Adventurous"};
        for (String mood : moods) {
            engine.addMood(mood); // Ensures mood exists
        }

        // Add a bunch of extra games for variety
        engine.addGameToMood("Excited", new Game("God of War: Ragnarok", "PS5"));
        engine.addGameToMood("Excited", new Game("Forza Horizon 5", "Xbox"));
        engine.addGameToMood("Excited", new Game("Smash Bros. Ultimate", "Switch"));
        engine.addGameToMood("Relaxed", new Game("Animal Crossing", "Switch"));
        engine.addGameToMood("Relaxed", new Game("Flower", "PS5"));
        engine.addGameToMood("Stressed", new Game("Tetris Effect", "PS5"));
        engine.addGameToMood("Stressed", new Game("Stardew Valley", "Switch"));
        engine.addGameToMood("Adventurous", new Game("Zelda: Tears of the Kingdom", "Switch"));
        engine.addGameToMood("Adventurous", new Game("Horizon Forbidden West", "PS5"));
        engine.addGameToMood("Adventurous", new Game("Sea of Thieves", "Xbox"));

        Label titleLabel = new Label("🎮 Mood-Based Game Recommender");
        titleLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        Label moodLabel = new Label("Select your mood:");
        ComboBox<String> moodBox = new ComboBox<>();
        moodBox.getItems().addAll(moods);
        moodBox.setValue("Bored");

        Button recommendButton = new Button("🎲 Get Game");
        Label resultLabel = new Label();

        ListView<String> recommendationLog = new ListView<>(previousRecommendations);
        recommendationLog.setPrefHeight(120);

        recommendButton.setOnAction(e -> {
            String selectedMood = moodBox.getValue();
            Game game = engine.recommend(selectedMood);
            if (game != null) {
                String recommendation = "🎮 " + game.toString();
                resultLabel.setText(recommendation);
                previousRecommendations.add(recommendation);
            } else {
                resultLabel.setText("No game found for that mood.");
            }
        });

        // Add custom game
        Label addGameLabel = new Label("Add a new game to this mood:");
        TextField gameTitleField = new TextField();
        gameTitleField.setPromptText("Game Title");
        TextField gameConsoleField = new TextField();
        gameConsoleField.setPromptText("Console (e.g., PS5)");

        Button addButton = new Button("➕ Add Game to Mood");
        addButton.setOnAction(e -> {
            String mood = moodBox.getValue();
            String gameTitleInput = gameTitleField.getText().trim();
            String console = gameConsoleField.getText().trim();
            if (!gameTitleInput.isEmpty() && !console.isEmpty()) {
                engine.addGameToMood(mood, new Game(gameTitleInput, console));
                resultLabel.setText("✅ Game added to " + mood + " mood.");
                gameTitleField.clear();
                gameConsoleField.clear();
            } else {
                resultLabel.setText("⚠️ Please enter both title and console.");
            }
        });

        VBox layout = new VBox(12, titleLabel, moodLabel, moodBox, recommendButton, resultLabel,
                addGameLabel, gameTitleField, gameConsoleField, addButton,
                new Label("📜 Previous Recommendations:"), recommendationLog);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #f4f4f4;");

        Scene scene = new Scene(layout, 520, 600);
        primaryStage.setTitle("Mood-Based Game Recommender");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}