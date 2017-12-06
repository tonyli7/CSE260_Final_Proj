import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.paint.Color;

public class MyButton{

    private String label;
    private int link;
    private double x_pos;
    private double y_pos;
    private int font;
    private Text text;
  

    
    public MyButton(String label, int link, double x_pos, double y_pos, int font){
	this.label = label;
	this.link = link;
	this.x_pos = x_pos;
	this.y_pos = y_pos;
	this.font = font;

	text = new Text(x_pos, y_pos, label);
	text.setFont(new Font("Courier New", font));
	text.setFill(Color.WHITE);
	
    }

    public MyButton(String label, int link, double x_pos, double y_pos){
	this(label, link, x_pos, y_pos, 24);
    }
    
    public MyButton(String label, int link){
	this(label, link, 350, 279, 24);
    }

    public Text getText(){
	return text;
    }

    public int getLink(){
	return link;
    }

    public void deselect(){
	text.setFill(Color.WHITE);
    }

    public void select(){
	text.setFill(Color.ORANGE);
    }
}
