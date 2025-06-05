import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.effect.DropShadow;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
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
    
    private String textPath;
    
    protected Label dialogueLabel;
    protected Label nameLabel;
    protected ImageView bgImageView;
    protected ImageView dialogueBgView;
    protected ImageView fgImageView;
    private MediaPlayer bgmPlayer;
    private MediaPlayer errorPlayer;
    private MediaPlayer clickPlayer;
    private Pane fgLayer;
    private Group chapterFlag;
    private Text flagText;
    private boolean justEntered = true;
    
    
    protected int currentIndex = 0;
    
    protected ScriptLoader loader;
    protected DialogueManager DM;
    protected SceneCompleteListener sceneCompleteListener;

    public GameScene(int width, int height, String bg) {
        initCommonUI(width, height);
        initMusic("H:\\git\\CSAFinal\\VisualNovel\\src\\music\\BGM03.mp3");

        // Background setup first
        initBackground(bg);

        // Stroke-only title (behind)
        Text titleStroke = new Text("Sleepwalking");
        titleStroke.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 75));
        titleStroke.setFill(Color.TRANSPARENT);
        titleStroke.setStroke(Color.web("#2C3E50")); // or any dark blue you prefer
        titleStroke.setStrokeWidth(6); // Adjust as needed

        // Fill-only title (in front)
        Text titleFill = new Text("Sleepwalking");
        titleFill.setFont(Font.font("Lucida Calligraphy", FontWeight.BOLD, 75));
        titleFill.setFill(Color.web("#F1F1F1"));
        titleFill.setStrokeWidth(0);

        // Layer the title text
        Group titleGroup = new Group(titleStroke, titleFill);

        // Position it
        StackPane.setAlignment(titleGroup, Pos.TOP_CENTER);
        StackPane.setMargin(titleGroup, new Insets(50, 0, 0, 0));

        root.getChildren().add(titleGroup);

        // Stroke-only text (behind)
        Text startStroke = new Text("Start");
        startStroke.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        startStroke.setFill(Color.TRANSPARENT); // No fill
        startStroke.setStroke(Color.web("#2C3E50"));
        startStroke.setStrokeWidth(10);

        // Fill-only text (in front)
        Text startFill = new Text("Start");
        startFill.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        startFill.setFill(Color.WHITE); // Fill color
        startFill.setStrokeWidth(0);

        // Stack them together
        Group startTextStack = new Group(startStroke, startFill);


        startTextStack.setOnMouseEntered(e -> this.grow(startTextStack, 200));
        startTextStack.setOnMouseExited(e -> this.shrink(startTextStack, 200));
        startTextStack.setOnMouseClicked(e -> onSceneComplete());
        
     // ==== Load Button ====
        Text loadStroke = new Text("Load");
        loadStroke.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        loadStroke.setFill(Color.TRANSPARENT);
        loadStroke.setStroke(Color.web("#2C3E50"));
        loadStroke.setStrokeWidth(10);

        Text loadFill = new Text("Load");
        loadFill.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        loadFill.setFill(Color.WHITE);
        loadFill.setStrokeWidth(0);

        Group loadTextStack = new Group(loadStroke, loadFill);
        loadTextStack.setOnMouseEntered(e -> this.grow(loadTextStack, 200));
        loadTextStack.setOnMouseExited(e -> this.shrink(loadTextStack, 200));
        loadTextStack.setOnMouseClicked(e -> this.onErrorClicked());


        // ==== Continue Button ====
        Text continueStroke = new Text("Continue");
        continueStroke.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        continueStroke.setFill(Color.TRANSPARENT);
        continueStroke.setStroke(Color.web("#2C3E50"));
        continueStroke.setStrokeWidth(10);

        Text continueFill = new Text("Continue");
        continueFill.setFont(Font.font("Georgia", FontWeight.BOLD, 48));
        continueFill.setFill(Color.WHITE);
        continueFill.setStrokeWidth(0);

        Group continueTextStack = new Group(continueStroke, continueFill);
        continueTextStack.setOnMouseEntered(e -> this.grow(continueTextStack, 200));
        continueTextStack.setOnMouseExited(e -> this.shrink(continueTextStack, 200));
        continueTextStack.setOnMouseClicked(e -> this.onErrorClicked());


        StackPane.setAlignment(startTextStack, Pos.CENTER_RIGHT);
        StackPane.setMargin(startTextStack, new Insets(-200, 50, 0, 0));
        
        StackPane.setAlignment(loadTextStack, Pos.CENTER_RIGHT);
        StackPane.setMargin(loadTextStack, new Insets(0, 50, 0, 0));
        
        StackPane.setAlignment(continueTextStack, Pos.CENTER_RIGHT);
        StackPane.setMargin(continueTextStack, new Insets(200, 50, 0, 0));
        
        root.getChildren().add(startTextStack);
        root.getChildren().add(loadTextStack);
        root.getChildren().add(continueTextStack);
    }


    public GameScene(int width, int height, String bgPath, String textPath, String musicPath) {
    	this.textPath = textPath;
        initCommonUI(width, height);
        scene.setOnMouseClicked(e -> nextDialogue());
        initBackground(bgPath);
        initChapterFlag();
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
    	gradientBox.setPrefHeight(220);
    	gradientBox.setMinHeight(220);
    	gradientBox.setMaxHeight(220);
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
    	DropShadow dropShadow = new DropShadow();
    	dropShadow.setOffsetX(2.0);
    	dropShadow.setOffsetY(2.0);
    	dropShadow.setColor(Color.BLACK); // Or any shadow color

    	// Apply to your Label
    	nameLabel.setEffect(dropShadow);
    	StackPane.setAlignment(nameLabel, Pos.BOTTOM_LEFT);
    	StackPane.setMargin(nameLabel, new Insets(0,0,120,100));
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
        AnchorPane dialoguePane = new AnchorPane();

        dialogueLabel = new Label("...");
        dialogueLabel.setWrapText(true);
        dialogueLabel.setMaxWidth(800);
        // ❌ Don't set minHeight — let the label expand based on content
        // ✅ Align text inside the label (optional, but good practice)
        dialogueLabel.setAlignment(Pos.CENTER_LEFT);
        dialogueLabel.setStyle("-fx-font-size: 24px; -fx-text-fill: white;");
        dialogueLabel.setOpacity(0.8);
        
        DropShadow dropShadow = new DropShadow();
        dropShadow.setOffsetX(2.0);
        dropShadow.setOffsetY(2.0);
        dropShadow.setColor(Color.BLACK); // Or any shadow color

        // Apply to your Label
        dialogueLabel.setEffect(dropShadow);
        
        // Anchor to fixed top-left position
        AnchorPane.setTopAnchor(dialogueLabel, 580.0);
        AnchorPane.setLeftAnchor(dialogueLabel, 100.0);

        dialoguePane.getChildren().add(dialogueLabel);
        root.getChildren().add(dialoguePane);
    }
    
    private void initChapterFlag() {
        // Create the rectangular part of the flag
    	Color flagColor = Color.web("#f88c00ff");
        Rectangle rect = new Rectangle(200, 50); // Width, Height
        rect.setFill(flagColor);
        rect.setArcWidth(10); // Optional: rounded corners
        rect.setArcHeight(10);

        // Create the triangular "arrow" part
        Polygon triangle = new Polygon();
        triangle.getPoints().addAll(
            198.0, 0.0,   // Top right of rectangle
            230.0, 25.0,  // Tip of triangle
            198.0, 50.0   // Bottom right of rectangle
        );
        triangle.setFill(flagColor);

        // Create the text inside the rectangle
        flagText = new Text(this.textPath.substring(this.textPath.length()-10,this.textPath.length()-5));
        flagText.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        flagText.setFill(Color.WHITE);

        // StackPane to center the text over the rectangle
        StackPane labelStack = new StackPane();
        labelStack.setPrefSize(200, 50);
        labelStack.getChildren().addAll(rect, flagText);

        // Group everything into one node
        chapterFlag = new Group(labelStack, triangle);


        StackPane.setAlignment(chapterFlag, Pos.TOP_LEFT);
        StackPane.setMargin(chapterFlag, new Insets(10,0,0,-20));
        // Add it to your root or another pane
        root.getChildren().add(chapterFlag);
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
    	if (this.justEntered) {
    		// Create slide-left animation
    		TranslateTransition slideLeft = new TranslateTransition(Duration.seconds(1), chapterFlag);
    		slideLeft.setByX(-150); // Move 150px left
    		
    		// Create fade-out animation
    		FadeTransition fadeOut = new FadeTransition(Duration.seconds(1), chapterFlag);
    		fadeOut.setToValue(0); // Fully transparent
    		
    		// Run both animations together
    		ParallelTransition parallel = new ParallelTransition(slideLeft, fadeOut);
    		parallel.play();
    		
    		// Optional: remove from parent after fade
    		parallel.setOnFinished(e -> ((Pane) chapterFlag.getParent()).getChildren().remove(chapterFlag));
    		
    		this.justEntered =false;
    	}
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
