
public class FgImageHelper {
	
	public FgImageHelper() {
		
	}
	
	public String getPath(String name, String expression) {
	    String rawPath = "H:/git/CSAFinal/VisualNovel/src/fgimage/" + name + "/" + name + "_" + expression + ".png";
	    return "file:///" + rawPath.replace("\\", "/").replace(" ", "%20");
	}


	public static void main(String[] args) {
		FgImageHelper helper = new FgImageHelper();
		
		System.out.println(helper.getPath("Atri", "default"));
	}
}
