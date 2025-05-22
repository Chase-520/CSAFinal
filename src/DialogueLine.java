public class DialogueLine {
    private Character character;
    private String expression; // e.g., "happy", "sad"
    private String text;

    public DialogueLine() {
    	
    }
    public DialogueLine(Character character, String text, String expression) {
        this.character = character;
        this.expression = expression;
        this.text = text;
    }

    public Character getCharacter() { return character; }
    public String getExpression() { return expression; }
    public String getText() { 
    	if (this.text !=null ){
    		return text; 
    	}else {
    		return "";
    	}
    }
    public void setCharacter(Character ch) {
    	this.character = ch;
    }
    public void setExpression(String ex) {
    	this.expression = ex;
    }
    public void setText(String tx) {
    	this.text = tx;
    }
}
