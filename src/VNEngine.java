import javafx.application.Application;
import javafx.stage.Stage;

public class VNEngine extends Application {
    private SceneController controller;

    @Override
    public void start(Stage stage) {
        controller = new SceneController(stage);

        controller.registerScene("intro", () -> new IntroScene(800, 600));
        // register many more scenes...
        controller.registerScene(STYLESHEET_CASPIAN, null);

        controller.setScene("intro");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
