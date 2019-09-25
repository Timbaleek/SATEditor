package entities;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector2f;

public class Player extends CollidingGameEntity{
	
	public Player(Vector2f pos,ArrayList<Vector2f> shape) {
		super(pos, shape);
	}

	public void updateInput(){
			if ((Keyboard.isKeyDown(Keyboard.KEY_A))){
				vel.x -= 0.01;
				//move(new Vector2f(-1,0));
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_D))){
				vel.x += 0.01;
				//move(new Vector2f(1,0));
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_W))){
				vel.y -= 0.01;
				//move(new Vector2f(0,-1));
			}
			if ((Keyboard.isKeyDown(Keyboard.KEY_S))){
				vel.y += 0.01;
				//move(new Vector2f(0,1));
			}
	}
}
