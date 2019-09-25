package main;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

public class Camera {
	
	public static Camera single;
	
	Vector2f pos;
	float zoom;
	public Camera(Vector2f pos, float zoom){
		this.pos = pos;
		this.zoom = zoom;
		Camera.single = this;
	}
	
	public Vector2f getAbsoluteMouse(){
		return new Vector2f((Mouse.getX() + Camera.single.pos.x)/zoom,
				((Main.screenHeight-Mouse.getY()) + Camera.single.pos.y)/zoom);
	}
}
