package props;

public class Scroll extends Prop {
    private String text = "...";
    public Scroll(String name){
        super(name);
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
