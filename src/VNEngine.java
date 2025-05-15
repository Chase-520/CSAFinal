import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.animation.FadeTransition;
import javafx.util.Duration;
import javafx.geometry.Pos;

public class VNEngine extends Application {
	private Character Atri = new Atri();
	
    @Override
    public void start(Stage primaryStage) {
        // Character Image
        ImageView characterImage = new ImageView(new Image("fgimage/tatr03_l_231.png"));
        characterImage.setFitHeight(300);
        characterImage.setPreserveRatio(true);

        // Dialogue Label
        Label dialogueLabel = new Label();
        dialogueLabel.setText("Alice: Hello, welcome to the story!");
        dialogueLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: white;");
        dialogueLabel.setWrapText(true);
        dialogueLabel.setOpacity(0); // Initially invisible

        // Layout
        VBox dialogueBox = new VBox(20, characterImage, dialogueLabel);
        dialogueBox.setAlignment(Pos.CENTER);
        dialogueBox.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 40px;");

        Scene scene = new Scene(new StackPane(dialogueBox), 800, 600);

        primaryStage.setScene(scene);
        primaryStage.setTitle("Simple Visual Novel");
        primaryStage.show();

        // Fade in the dialogue
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(1), dialogueLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
