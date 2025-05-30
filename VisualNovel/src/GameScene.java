import javafx.animation.FadeTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

// Optional
import javafx.scene.text.FontPosture;


public class GameScene {
    protected String id;
    protected Scene scene;
    protected StackPane root;
    
    protected Label dialogueLabel;
    protected Label nameLabel;
    protected ImageView bgImageView;
    protected ImageView dialogueBgView;
    protected ImageView fgImageView;
    private MediaPlayer bgmPlayer;
    private Pane fgLayer;

    
    protected int currentIndex = 0;
    
    protected ScriptLoader loader;
    protected DialogueManager DM;
    protected SceneCompleteListener sceneCompleteListener;

    public GameScene(int width, int height, String bg) {
        initCommonUI(width, height);
        initMusic("H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM03.mp3");
        
        Button startButton = new Button("Start");
        startButton.setStyle("-fx-pref-width: 120; -fx-pref-height: 90;");
        startButton.setOnAction(e -> onSceneComplete());
        StackPane.setAlignment(startButton, Pos.CENTER_RIGHT);
        StackPane.setMargin(startButton, new Insets(0, 50, 0, 0));
        
        Label title = new Label();
        title.setStyle("-fx-font-size: 48px; -fx-text-fill: white;");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        title.setWrapText(true);
        title.setOpacity(1);
        title.setText("Game Title");
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(50,0,0,0));
        
        initBackground(bg);
        root.getChildren().add(startButton);
        root.getChildren().add(title);
        
    }

    public GameScene(int width, int height, String bgPath, String textPath, String musicPath) {
        initCommonUI(width, height);
        scene.setOnMouseClicked(e -> nextDialogue());
        initBackground(bgPath);
        initDialogueText();
        initCharacterName();
        initCharacter();
        loader = new ScriptLoader(textPath);
        loadDialogue(textPath);
        initDialogueBackground();
        initMusic(musicPath);
        
    }
    
    private void initCommonUI(int width, int height) {
        root = new StackPane();
        root.setStyle("-fx-padding: 20px;");
        scene = new Scene(root, width, height);
    }
    

    private void initDialogueBackground() {
    	// 1. Create gradient background panel first
    	StackPane gradientBox = new StackPane();
    	gradientBox.setMinWidth(1280);
    	gradientBox.setMaxWidth(1280);
    	gradientBox.setPrefHeight(200);
    	gradientBox.setMinHeight(200);
    	gradientBox.setMaxHeight(200);
    	gradientBox.setStyle(
    	    "-fx-background-color: linear-gradient(from 0% 100% to 0% 0%, " +
    	    "rgba(98, 176, 209, 1), " +        // bottom color (opaque)
    	    "rgba(129, 196, 225, 0.3));" +       // top color (transparent)
    	    "-fx-background-radius: 20;" +
    	    "-fx-border-radius: 20;" +
    	    "-fx-padding: 0;"
    	);
    	StackPane.setAlignment(gradientBox, Pos.BOTTOM_CENTER);
    	StackPane.setMargin(gradientBox, new Insets(0, 0, -50, 0));
    	root.getChildren().add(gradientBox);

    	// 2. Create a container for your labels inside the root (not inside gradientBox)
    	VBox dialogueContainer = new VBox(5);
    	// Create a horizontal Separator
    	Separator separator = new Separator();
    	separator.setOrientation(Orientation.HORIZONTAL);

    	// Set a fixed width for the line
    	separator.setPrefWidth(300);  // Set to your desired length
    	separator.setMaxWidth(300);   // Prevent stretching in VBox
    	separator.setStyle("-fx-background-color: white;"); // Optional: color

    	// Optionally center or align it
    	VBox.setMargin(separator, new Insets(5, 0, 20, 0));  // 20px bottom margin on nameLabel


    	// Add the elements in order
    	dialogueContainer.setPrefWidth(1280);
    	dialogueContainer.setPadding(new Insets(10, 30, 20, 30));  // example padding
    	
    	dialogueContainer.setAlignment(Pos.BOTTOM_LEFT);
    	
    	// Add your labels to this container
    	dialogueContainer.getChildren().addAll(nameLabel, separator, dialogueLabel);
    	StackPane.setAlignment(dialogueContainer, Pos.BOTTOM_CENTER);
    	StackPane.setMargin(dialogueContainer, new Insets(0,0,30,0));
    	root.getChildren().add(dialogueContainer);

    }
    
    private void initCharacterName() {
    	nameLabel = new Label();
    	nameLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    	nameLabel.setWrapText(true);
    	nameLabel.setOpacity(1);
//    	StackPane.setAlignment(nameLabel, Pos.BOTTOM_LEFT);
//    	StackPane.setMargin(nameLabel, new Insets(0,0,100,100));
//    	root.getChildren().add(nameLabel);
    }

    private void initCharacter() {
        fgImageView = new ImageView();

        // Create a Pane wrapper so we can control positioning
        fgLayer = new Pane();
        fgLayer.getChildren().add(fgImageView);

        // Add the pane to the StackPane root
        root.getChildren().add(fgLayer);
    }
    
    private void initDialogueText() {
    	dialogueLabel = new Label();
    	dialogueLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    	dialogueLabel.setWrapText(true);
    	dialogueLabel.setOpacity(0.8);
//    	StackPane.setAlignment(dialogueLabel, Pos.BOTTOM_CENTER);
//    	// insets top right bottom left
//    	StackPane.setMargin(dialogueLabel, new Insets(0,0,100,0));
//    	root.getChildren().add(dialogueLabel);
    }
    
    
    private void initMusic(String path) {
    	Media bgMusic = new Media(new File(path).toURI().toString());
        bgmPlayer = new MediaPlayer(bgMusic);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
        //bgmPlayer.setVolume(0.3); // optional
        bgmPlayer.play();
    }
    
    protected void initBackground(String bgPath) {
    	Image bgImage = new Image(bgPath);
    	bgImageView = new ImageView(bgImage);
    	bgImageView.setFitWidth(scene.getWidth());
    	bgImageView.setFitHeight(scene.getHeight());
    	bgImageView.setPreserveRatio(false);
    	root.getChildren().add(bgImageView);
    }
    
    public void setMusic(String path) {
    	
    }
    
    public Scene getScene() {
    	return scene;
    }

    
    protected void loadDialogue(String textPath) {
        DM = new DialogueManager(_loadDialogues(textPath));
        if (DM != null && !DM.isEmpty()) {
            DialogueLine line = DM.getCurrentLine();
            showDialogue(line);
            DM.nextLine();
        }
    }
    
    
    protected void showDialogue(DialogueLine line) {
    	Image fgImage = new Image(line.getCharacter().getPath());
    	fgImageView.setImage(fgImage);
    	// Set the desired width and height (scales image)
    	double scale = line.getCharacter().getScale();
    	fgImageView.setScaleX(scale);
    	fgImageView.setScaleY(scale);
//    	fgImageView.setFitWidth(960*scale); // for example
//    	fgImageView.setFitHeight(720*scale);
    	fgImageView.setPreserveRatio(true);

    	fgImageView.setLayoutX(line.getCharacter().getX());
    	fgImageView.setLayoutY(line.getCharacter().getY());
    	// Optional: preserve aspect ratio
        dialogueLabel.setText(line.getText());
        nameLabel.setText(line.getCharacter().getName());
        FadeTransition fadeIn = new FadeTransition(Duration.seconds(0.5), dialogueLabel);
        fadeIn.setFromValue(0);
        fadeIn.setToValue(1);
        fadeIn.play();
    }

    protected void nextDialogue() {
    	DialogueLine line = DM.getCurrentLine();
        if (line != null) {
        	showDialogue(line);
            DM.nextLine();
        } else {
        	System.out.println("mouse click triggered complete");
            onSceneComplete(); // When no more dialogue
        }
    }
    
    public void setSceneCompleteListener(SceneCompleteListener listener) {
        this.sceneCompleteListener = listener;
    }


    protected void onSceneComplete() {
        if (sceneCompleteListener != null) {
        	FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), root);
        	fadeOut.setFromValue(100);
        	fadeOut.setToValue(0);
            fadeOut.setOnFinished(event -> sceneCompleteListener.onSceneComplete(this));
        	fadeOut.play();
            //sceneCompleteListener.onSceneComplete(this);
        	this.bgmPlayer.stop();
        }
    }

    protected List<DialogueLine> _loadDialogues(String textPath) {
    	return loader.getDialogueLines();
    }
}
