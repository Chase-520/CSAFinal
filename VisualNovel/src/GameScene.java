import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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
    private MediaPlayer errorPlayer;
    private MediaPlayer clickPlayer;
    private Pane fgLayer;
    
    
    protected int currentIndex = 0;
    
    protected ScriptLoader loader;
    protected DialogueManager DM;
    protected SceneCompleteListener sceneCompleteListener;

    public GameScene(int width, int height, String bg) {
        initCommonUI(width, height);
        initMusic("H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM03.mp3");

        // Background setup first
        initBackground(bg);

        // Title label
        Text title = new Text("Game Title");
        title.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        title.setStyle("-fx-text-fill: white;");
        title.setOpacity(1);
        title.setStroke(Color.BLACK);          // Border/stroke color
        title.setStrokeWidth(2);  
        StackPane.setAlignment(title, Pos.TOP_CENTER);
        StackPane.setMargin(title, new Insets(50, 0, 0, 0));
        root.getChildren().add(title);

        // Interactive "Start" text with border
        Text startText = new Text("Start");
        startText.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        startText.setFill(Color.web("#BFFFF1"));   // Text fill color
        startText.setStroke(Color.BLACK);          // Border/stroke color
        startText.setStrokeWidth(2);               // Stroke thickness

        startText.setOnMouseEntered(e -> this.grow(startText, 200));
        startText.setOnMouseExited(e -> this.shrink(startText, 200));
        startText.setOnMouseClicked(e -> onSceneComplete());
        
        Text LoadText = new Text("Load");
        LoadText.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        LoadText.setFill(Color.web("#BFFFF1"));   // Text fill color
        LoadText.setStroke(Color.BLACK);          // Border/stroke color
        LoadText.setStrokeWidth(2);               // Stroke thickness

        LoadText.setOnMouseEntered(e -> this.grow(LoadText, 200));
        LoadText.setOnMouseExited(e -> this.shrink(LoadText, 200));
        LoadText.setOnMouseClicked(e -> this.onErrorClicked());
        
        Text continueText = new Text("Conitnue");
        continueText.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        continueText.setFill(Color.web("#BFFFF1"));   // Text fill color
        continueText.setStroke(Color.BLACK);          // Border/stroke color
        continueText.setStrokeWidth(2);               // Stroke thickness

        continueText.setOnMouseEntered(e -> this.grow(continueText, 200));
        continueText.setOnMouseExited(e -> this.shrink(continueText, 200));
        continueText.setOnMouseClicked(e -> this.onErrorClicked());

        
        StackPane.setAlignment(startText, Pos.CENTER_RIGHT);
        StackPane.setMargin(startText, new Insets(-200, 50, 0, 0));
        
        StackPane.setAlignment(LoadText, Pos.CENTER_RIGHT);
        StackPane.setMargin(LoadText, new Insets(0, 50, 0, 0));
        
        StackPane.setAlignment(continueText, Pos.CENTER_RIGHT);
        StackPane.setMargin(continueText, new Insets(200, 50, 0, 0));
        
        root.getChildren().add(startText);
        root.getChildren().add(LoadText);
        root.getChildren().add(continueText);
    }


    public GameScene(int width, int height, String bgPath, String textPath, String musicPath) {
        initCommonUI(width, height);
        scene.setOnMouseClicked(e -> nextDialogue());
        initBackground(bgPath);
        loader = new ScriptLoader(textPath);
        initCharacter();
        initDialogueBackground();
        initCharacterName();
        loadDialogue(textPath);
        initDialogueText();
        initSmallButtons();
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

    	// Create a horizontal Separator
    	Separator separator = new Separator();
    	separator.setOrientation(Orientation.HORIZONTAL);

    	// Set a fixed width for the line
    	separator.setPrefWidth(300);  // Set to your desired length
    	separator.setMaxWidth(300);   // Prevent stretching in VBox
    	separator.setStyle("-fx-background-color: white;"); // Optional: color
    	StackPane.setAlignment(separator, Pos.BOTTOM_LEFT);
    	StackPane.setMargin(separator, new Insets(0,0,110,100));
    	root.getChildren().add(separator);

    }
    
    private void initCharacterName() {
    	nameLabel = new Label();
    	nameLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
    	nameLabel.setWrapText(true);
    	nameLabel.setOpacity(1);
    	StackPane.setAlignment(nameLabel, Pos.BOTTOM_LEFT);
    	StackPane.setMargin(nameLabel, new Insets(0,0,110,100));
    	root.getChildren().add(nameLabel);
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
    	StackPane.setAlignment(dialogueLabel, Pos.BOTTOM_CENTER);
    	StackPane.setMargin(dialogueLabel, new Insets(0,0,60,0));
    	root.getChildren().add(dialogueLabel);
    }
    
    private void initMusic(String path) {
    	Media bgMusic = new Media(new File(path).toURI().toString());
        bgmPlayer = new MediaPlayer(bgMusic);
        bgmPlayer.setCycleCount(MediaPlayer.INDEFINITE); // loop forever
        //bgmPlayer.setVolume(0.3); // optional
        bgmPlayer.play();
        
        // load sfx
        Media sfx = new Media(new File("H:\\git\\CSAFinal\\VisualNovel\\src\\music\\error-8-206492.mp3").toURI().toString());
    	errorPlayer = new MediaPlayer(sfx);
    	
    	Media clicksfx = new Media(new File("H:\\git\\CSAFinal\\VisualNovel\\src\\music\\mouse-click-117076.mp3").toURI().toString());
    	clickPlayer = new MediaPlayer(clicksfx);
        
    }
    
    protected void initBackground(String bgPath) {
    	Image bgImage = new Image(bgPath);
    	bgImageView = new ImageView(bgImage);
    	bgImageView.setFitWidth(scene.getWidth());
    	bgImageView.setFitHeight(scene.getHeight());
    	bgImageView.setPreserveRatio(false);
    	root.getChildren().add(bgImageView);
    }

    protected void initSmallButtons() {
    	/*
    	 * it only create a text node and bind some stuff to it, it doesn't do anything
    	 * It's for the VISUAL!!!!!!!!!!!!
    	 */
    	
    	HBox box = new HBox(20);  // spacing of 20 between children
    	box.setPadding(new Insets(10));  // optional padding around the box

    	// Example button: "Save"
    	Text save = new Text("Save");
    	save.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
    	save.setFill(Color.web("#BFFFF1"));
    	save.setStroke(Color.WHITE);
    	save.setStrokeWidth(2);
    	save.setOnMouseEntered(e -> grow(save, 150));
    	save.setOnMouseExited(e -> shrink(save, 150));
    	save.setOnMouseClicked(e -> this.onErrorClicked());

    	// Example button: "Load"
    	Text load = new Text("Load");
    	load.setFont(Font.font("Georgia", FontWeight.BOLD, 24));
    	load.setFill(Color.web("#BFFFF1"));
    	load.setStroke(Color.WHITE);
    	load.setStrokeWidth(2);
    	load.setOnMouseEntered(e -> grow(load, 150));
    	load.setOnMouseExited(e -> shrink(load, 150));
    	load.setOnMouseClicked(e -> this.onErrorClicked());

    	// Add all buttons to the HBox
    	box.getChildren().addAll(save, load);
    	
    	StackPane.setAlignment(box, Pos.CENTER_RIGHT);
    	StackPane.setMargin(box, new Insets(520,0,0,1000));
    	root.getChildren().add(box);
        
        
        
    	
    }
    
    private void grow(Node nodein,int duration) {
		ScaleTransition grow = new ScaleTransition(Duration.millis(duration), nodein);
	 	grow.setToX(1.2);
	 	grow.setToY(1.2);
	 	grow.playFromStart();
    }
    
    private void shrink(Node nodein, int duration) {
    	ScaleTransition shrink = new ScaleTransition(Duration.millis(duration),nodein);
    	shrink.setToX(1.0);
    	shrink.setToY(1.0);
    	shrink.playFromStart();
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
    	this.onClicked();
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
    	this.onClicked();
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
    
    protected void onErrorClicked() {
    	errorPlayer.seek(Duration.ZERO);
    	errorPlayer.play();
    }
    
    protected void onClicked() {
    	clickPlayer.seek(Duration.ZERO);
    	clickPlayer.play();
    }

    protected List<DialogueLine> _loadDialogues(String textPath) {
    	return loader.getDialogueLines();
    }
}
