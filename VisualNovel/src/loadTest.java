import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class loadTest {
	
    public static List<DialogueLine> loadDialoguesFromCSV(String path) {
        List<DialogueLine> dialogues = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            // Skip header line
            br.readLine();

            while ((line = br.readLine()) != null) {
                // Split line by comma (naive split)
                // For production, consider CSV libraries if your text has commas!
                String[] parts = line.split(",", 3);

                if (parts.length == 3) {
                    String speaker = parts[0].trim();
                    String text = parts[1].trim();
                    String emotion = parts[2].trim();

                    // You can adjust the Character constructor as needed for your project
                    dialogues.add(new DialogueLine(new Character(speaker, ""), text, emotion));
                    System.out.println(speaker + ": " + text + "(" + emotion + ")");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return dialogues;
    }
    
    public static void main(String[] args) {
    	loadDialoguesFromCSV("H:\\git\\CSAFinal\\VisualNovel\\src\\scripts\\something.csv");
    }
}
