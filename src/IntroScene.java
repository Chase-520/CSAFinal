import java.util.List;

public class IntroScene extends GameScene {

    public IntroScene(int width, int height) {
        super(width, height);
        this.id = "intro";
    }



    @Override
    protected void onSceneComplete() {
        System.out.println("Intro Scene finished. You can switch scenes here.");
        // Notify SceneController to switch scene if you implement it
    }
}
