public class DialogueLine {
    private Character character;
    private String expression; // e.g., "happy", "sad"
    private String text;

    public DialogueLine(Character character, String expression, String text) {
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
}
