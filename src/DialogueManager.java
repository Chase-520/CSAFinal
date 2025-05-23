import java.util.List;

public class DialogueManager {
    private List<DialogueLine> lines;
    private int currentIndex = 0;

    public DialogueManager(List<DialogueLine> lines) {
        this.lines = lines;
    }

    public DialogueLine getCurrentLine() {
        if (currentIndex < lines.size()) {
            return lines.get(currentIndex);
        }
        return null; // end of script
    }

    public boolean nextLine() {
    	System.out.println("Mouse clicked");
    	System.out.println("line size: " + lines.size());
    	System.out.println("Current index: " + currentIndex);
        if (currentIndex < lines.size() - 1) {
            currentIndex++;
            return true; // advanced to next line
        }
        return false; // no more lines
    }
    
    public boolean setLineIndex(int index) {
    	if (index < lines.size()) {
    		currentIndex = index;
            return true;
        }else {
        	return false;
        }
    }
    
    public boolean hasNext() {
    	return currentIndex<lines.size()-1;
    }
    public void reset() {
        currentIndex = 0;
    }
    
    public boolean isEmpty() {
    	return lines.size() > 0;
    }
    
    public int size() {
    	return lines.size();
    }
}
