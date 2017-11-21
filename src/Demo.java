import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;
import javafx.animation.PathTransition;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.scene.input.KeyCode;
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

	/*
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
	*/
	pane.getChildren().addAll(player.getImageView());

	//animation.play();
	Scene scene = new Scene(pane, 800, 600);
	setKeyPressed(scene);
	setKeyReleased(scene);
	

	KeyComs key_coms = new KeyComs(player);
	key_coms.start();
	
	primaryStage.setScene(scene);
	primaryStage.show();

    }


    public void setKeyPressed(Scene scene){
	
	scene.setOnKeyPressed(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    KeyComs.right_pressed = true;
		    
		    
		}
		if (key == KeyCode.LEFT){
		    KeyComs.left_pressed = true;
		    
		}
		if (key == KeyCode.UP){
		    KeyComs.up_pressed = true;

		    
		}
		if (key == KeyCode.DOWN){
		    KeyComs.down_pressed = true;
		    
		}

	    });
    }

    public void setKeyReleased(Scene scene){
	scene.setOnKeyReleased(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    KeyComs.right_pressed = false;

		}
		if (key == KeyCode.LEFT){
		    KeyComs.left_pressed = false;

		}
		if (key == KeyCode.UP){
		    KeyComs.up_pressed = false;
		}
		if (key == KeyCode.DOWN){
		    KeyComs.down_pressed = false;
		}
		
	    });
    }
}
