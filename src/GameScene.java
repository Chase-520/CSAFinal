import javafx.animation.FadeTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

public abstract class GameScene {
    protected String id;
    protected Scene scene;
    protected StackPane root;
    protected Label dialogueLabel;
    protected DialogueManager DM;
    protected ImageView bgImageView;
    protected int currentIndex = 0;

    public GameScene(int width, int height) {
        initCommonUI(width, height);
        Button startButton = new Button("Start");
        startButton.setOnAction(e -> onSceneComplete());
        root.getChildren().add(startButton);
    }

    public GameScene(int width, int height, String bgPath, String textPath) {
        initCommonUI(width, height);
        setBackground(bgPath);
        loadDialogue(textPath);
    }

    private void initCommonUI(int width, int height) {
        dialogueLabel = new Label();
        dialogueLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        dialogueLabel.setWrapText(true);
        dialogueLabel.setOpacity(0.8);

        root = new StackPane();
        root.setStyle("-fx-padding: 20px;");
        scene = new Scene(root, width, height);

        scene.setOnMouseClicked(e -> nextDialogue());
    }

    protected void setBackground(String bgPath) {
        Image bgImage = new Image(bgPath);
        bgImageView = new ImageView(bgImage);
        bgImageView.setFitWidth(scene.getWidth());
        bgImageView.setFitHeight(scene.getHeight());
        bgImageView.setPreserveRatio(false);

        root.getChildren().addAll(bgImageView, dialogueLabel);
    }

    protected void loadDialogue(String textPath) {
        DM = new DialogueManager(loadDialogues(textPath));
        if (DM != null && !DM.isEmpty()) {
            DialogueLine line = DM.getCurrentLine();
            showDialogue(line.getText());
            DM.nextLine();
        }
    }

    protected void showDialogue(String text) {
        dialogueLabel.setText(text);
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), dialogueLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    protected void nextDialogue() {
        if (DM != null && DM.hasNext()) {
            DialogueLine line = DM.getCurrentLine();
            showDialogue(line.getText());
            DM.nextLine();
        } else {
            onSceneComplete(); // When no more dialogue
        }
    }

    protected abstract void onSceneComplete();

    protected abstract java.util.List<DialogueLine> loadDialogues(String textPath);
}
