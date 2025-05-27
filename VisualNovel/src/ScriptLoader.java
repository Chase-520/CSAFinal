//"H:\\\\git\\\\CSAFinal\\\\VisualNovel\\\\src\\\\scripts\\\\test.json"
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ScriptLoader {
	private String content;
	private JSONArray dialogues; 
	private List<DialogueLine> chapter = new ArrayList<DialogueLine>();
	private FgImageHelper pathHelper = new FgImageHelper();
	
	public ScriptLoader(String path) {
		try {
			content = new String(Files.readAllBytes(Paths.get(path)));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogues = new JSONArray(content);
	}
	
	public List<DialogueLine> getDialogueLines(){
		for (int i = 0; i < dialogues.length(); i++) {
            JSONObject line = dialogues.getJSONObject(i);
            String character = line.getString("character");
            String expression = line.getString("expression");
            String text = line.getString("text");

            chapter.add(new DialogueLine(new Character(character, pathHelper.getPath(character, expression)), text, expression));
        }
		return chapter;
		
	}
    public static void main(String[] args) {
        String filePath = "H:\\\\git\\\\CSAFinal\\\\VisualNovel\\\\src\\\\scripts\\\\test.json";

        try {
            String content = new String(Files.readAllBytes(Paths.get(filePath)));

            // Parse as JSON array
            JSONArray dialogues = new JSONArray(content);

            // Iterate through dialogue entries
            for (int i = 0; i < dialogues.length(); i++) {
                JSONObject line = dialogues.getJSONObject(i);
                String character = line.getString("character");
                String expression = line.getString("expression");
                String text = line.getString("text");

                System.out.println(character + " (" + expression + "): " + text);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

