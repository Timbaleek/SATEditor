package entities;

import java.util.ArrayList;
import org.lwjgl.util.vector.Vector2f;

public class CollidingGameEntity extends Polygon{

	Vector2f vel = new Vector2f(0,0);
	
	public CollidingGameEntity(Vector2f pos, ArrayList<Vector2f> shape) {
		super(pos, shape);
	}

	public void update(){
		move(vel);
		
		if(vel.x != 0){
			vel.x /= 1.02f;
		}
		if(vel.y != 0){
			vel.y /= 1.02f;
		}
	}
}
