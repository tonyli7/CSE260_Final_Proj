import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.util.LinkedList;
import java.util.Random;


public class MonsterComs extends AnimationTimer{

    //private Location curr_location;
    private Player player;
    public MonsterComs(Player player){
	this.player = player;
	
    }

    @Override
    public void handle(long timestamp){

	if (Demo.GAME_STATE == Demo.GAME){
	    LinkedList<Monster> mons = Demo.curr_location.getMonsters();
	
	    for (int i = 0; i < mons.size(); i++){
	    
		Monster m = mons.get(i);
	    
	    
		if (!m.isSlashed()){
	       
		    Movement.unitMove(m, player, m.getDir(), 20);
		    
		}else if (m.death() < 0){
		    Movement.follow(m, player);
		}

		if (m.death() < 7 * 3 && m.death() > -1){
		    Image[] death_imgs = m.getDeathImgs();
		    ImageView img_v = m.getImageView();
		    m.getDeathImageView().setImage(death_imgs[m.death()/3]);
		    m.getDeathImageView().setX(img_v.getX() + img_v.getFitWidth()/2 - death_imgs[m.death()/3].getWidth()/2);
		    m.getDeathImageView().setY(img_v.getY() + img_v.getFitHeight()/2 - death_imgs[m.death()/3].getHeight()/2);
		    m.incDeath();
		}
	    
		if (m.death() == 7 * 3){

		    Demo.pane.getChildren().remove(m.getDeathImageView());
		    Demo.pane.getChildren().remove(m.getImageView());
		    mons.remove(m);
		
		}
	}
	}
	
    }

    
    
    
    
}
