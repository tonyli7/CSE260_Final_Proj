import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;

public class Demo extends Application {

    
    
    @Override
    public void start(Stage primaryStage) {
	Pane pane = new Pane();

	Sprite player = new Player("Link", "Link", 400, 300);
	
	
	pane.getChildren().add(player.getImageView());

	Scene scene = new Scene(pane, 800, 600);

	scene.setOnKeyPressed(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    Movement.move(player, 1, 0, Sprite.RIGHT, player.getForm());
		    
		}
		if (key == KeyCode.LEFT){
		    Movement.move(player, -1, 0, Sprite.LEFT, player.getForm());
		    
		}
		if (key == KeyCode.UP){
		    Movement.move(player, 0, -1, Sprite.UP, player.getForm());
		    
		}
		if (key == KeyCode.DOWN){
		    Movement.move(player, 0, 1, Sprite.DOWN, player.getForm());
		    
		}

		
	    });

	scene.setOnKeyReleased(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    Movement.move(player, 0, 0, Sprite.RIGHT, 3);
		}
		if (key == KeyCode.LEFT){
		    Movement.move(player, 0, 0, Sprite.LEFT, 3);
		}
		if (key == KeyCode.UP){
		    Movement.move(player, 0, 0, Sprite.UP, 3);
		}
		if (key == KeyCode.DOWN){
		    Movement.move(player, 0, 0, Sprite.DOWN, 3);
		}


		
	    });
	primaryStage.setScene(scene); primaryStage.show();
	primaryStage.show();

    }

}
