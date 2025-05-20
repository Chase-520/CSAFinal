import java.util.List;


public class IntroScene extends GameScene {

    public IntroScene(int width, int height, String bg) {
        super(width, height, bg);
        this.id = "intro";
    }



//    protected void onSceneComplete() {
//        System.out.println("Intro Scene finished. You can switch scenes here.");
//        // Notify SceneController to switch scene if you implement it
//    }



	@Override
	protected List<DialogueLine> _loadDialogues(String textPath) {
		// TODO Auto-generated method stub
		return null;
	}
}
