import javafx.animation.AnimationTimer;
import javafx.scene.image.Image;
import java.util.LinkedList;
import java.util.Random;


public class MonsterComs extends AnimationTimer{

    private LinkedList<Monster> mons;
    private Player player;
    public MonsterComs(LinkedList<Monster> mons, Player player){
	this.mons = mons;
	this.player = player;
	
    }

    @Override
    public void handle(long timestamp){

	//System.out.println(mons.get(0).getSteps());
	for (int i = 0; i < mons.size(); i++){
	    
	    Monster m = mons.get(i);
	    
	    Movement.unitMove(m, player, m.getDir(), 20);
	}
	
    }

    
    
    
    
}
