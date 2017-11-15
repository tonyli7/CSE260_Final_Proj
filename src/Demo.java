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
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import java.util.Random;

public class Demo extends Application {

    private boolean right_pressed;
    private boolean left_pressed;
    private boolean up_pressed;
    private boolean down_pressed;
    
    
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
		    right_pressed = true;
		    
		    
		}
		if (key == KeyCode.LEFT){
		    left_pressed = true;
		    //Movement.unitMoveX(player, Sprite.LEFT);
		    
		}
		if (key == KeyCode.UP){
		    up_pressed = true;
		    //Movement.unitMoveY(player, Sprite.UP);
		    
		}
		if (key == KeyCode.DOWN){
		    down_pressed = true;
		    //Movement.unitMoveY(player,Sprite.DOWN);
		    
		}

		
	    });

	scene.setOnKeyReleased(e -> {
		KeyCode key = e.getCode();
		if (key == KeyCode.RIGHT){
		    right_pressed = false;
		    //Movement.stop(player, 3);
		}
		if (key == KeyCode.LEFT){
		    left_pressed = false;
		    //Movement.move(player, 0, 0, Sprite.LEFT, 3);
		}
		if (key == KeyCode.UP){
		    up_pressed = false;
		    //Movement.move(player, 0, 0, Sprite.UP, 3);
		}
		if (key == KeyCode.DOWN){
		    down_pressed = false;
		    //Movement.move(player, 0, 0, Sprite.DOWN, 3);
		}


		
	    });


	AnimationTimer player_mvmt = new AnimationTimer(){
		@Override
		public void handle(long timestamp){
		    if (right_pressed){
			Movement.unitMoveX(player, Sprite.RIGHT);
			//System.out.println(player.getForm());
		    }
		    if (left_pressed){
			Movement.unitMoveX(player, Sprite.LEFT);
		    }
		    if (up_pressed){
			Movement.unitMoveY(player, Sprite.UP);
		    }
		    if (down_pressed){
			Movement.unitMoveY(player, Sprite.DOWN);
		    }
		    if (!right_pressed && !left_pressed && !up_pressed && !down_pressed){
			Movement.stop(player, 3);
		    }
		   
		}
	    };
	
	player_mvmt.start();
	
	primaryStage.setScene(scene); primaryStage.show();
	primaryStage.show();

    }

}
