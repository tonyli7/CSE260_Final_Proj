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
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.Random;

public class Demo extends Application {

    
    
    @Override
    public void start(Stage primaryStage) {
	Random rand = new Random();
	
	Pane pane = new Pane();
	
	Player player = new Player("Link", "Link", 400, 300);
	Monster tentacles = new Monster("Tentacles", 2, "Tentacles", 200, 200); 

	EventHandler<ActionEvent> eH = e -> {
	    if (rand.nextInt(2) == 0){
		Movement.move(tentacles, rand.nextFloat()*4 - 2, 0, Sprite.RIGHT, tentacles.getForm());
	    }else{
		Movement.move(tentacles, 0, rand.nextFloat()*4 - 2, Sprite.RIGHT, tentacles.getForm());
	    }
		
	};

	Timeline animation = new Timeline(new KeyFrame(Duration.millis(250), eH));
	animation.setCycleCount(Timeline.INDEFINITE);
	
	pane.getChildren().addAll(player.getImageView(), tentacles.getImageView());

	animation.play();
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
