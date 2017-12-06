import javafx.scene.layout.Pane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import javafx.scene.image.Image;
public class MyScreen{

    private MyButton[] buttons;
    private int screen;
    private int hovered;
    private Pane pane;
    
    public MyScreen(MyButton[] buttons, int screen){

	Rectangle rect = new Rectangle(200, 150 , 370, buttons.length * 100);
	rect.setFill(Color.BLACK);
	rect.setOpacity(.4);

	
	this.buttons = buttons;
	this.screen = screen;
	
	hovered = 0;
	buttons[hovered].select();
	
	pane = new Pane();
	pane.setBackground(new Background(new BackgroundImage(new Image("img/Background/bg.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
	pane.getChildren().add(rect);
	for (MyButton b: buttons){
	    pane.getChildren().add(b.getText());
	}

	
    }

    public int getScreen(){
	return screen;
    }

    public int hovered(){
	return hovered;
    }

    public void setHovered(int i){
	buttons[hovered].deselect();
	hovered = i;
	buttons[hovered].select();
    }
    
    public Pane getPane(){
	return pane;
    }

    public MyButton[] getButtons(){
	return buttons;
    }
}
