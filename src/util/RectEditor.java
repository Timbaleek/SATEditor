package util;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import graphics.GraphicRect;
import main.Camera;

public class RectEditor {
	
	static final float range = 30;
	
	private static boolean leftBeingPressed = false;
	static int corner = -1;
	public static void update(GraphicRect r){
		Vector2f mousePos = Camera.single.getAbsoluteMouse();
		if(Mouse.isButtonDown(0)){ //pressed
			if(!leftBeingPressed){
				leftBeingPressed = true;
				if(mousePos.x>r.pos.x-range&&mousePos.x<r.pos.x+range&&mousePos.y>r.pos.y-range&&mousePos.y<r.pos.y+range){
					corner = 0;
				} else if(mousePos.x>r.pos.x+r.size.x-range&&mousePos.x<r.pos.x+r.size.x+range&&mousePos.y>r.pos.y-range&&mousePos.y<r.pos.y+range){
					corner = 1;
				} else if(mousePos.x>r.pos.x+r.size.x-range&&mousePos.x<r.pos.x+r.size.x+range&&mousePos.y>r.pos.y+r.size.y-range&&mousePos.y<r.pos.y+r.size.y+range){
					corner = 2;
				} else if(mousePos.x>r.pos.x-range&&mousePos.x<r.pos.x+range&&mousePos.y>r.pos.y+r.size.y-range&&mousePos.y<r.pos.y+r.size.y+range){
					corner = 3;
				}
			}else{
				Vector2f mouseMove = new Vector2f(Mouse.getDX(),Mouse.getDY());
				if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
					float sizeRelation = r.size.y / r.size.x;
					if(corner==0){
						r.size.x-=mouseMove.x; r.size.y+=mouseMove.x*-sizeRelation;
						r.pos.x+=mouseMove.x; r.pos.y-=mouseMove.x*-sizeRelation;
					}else if(corner==1){
						r.size.x+=mouseMove.x; r.size.y+=mouseMove.x*sizeRelation;
						r.pos.y-=mouseMove.x*sizeRelation;
					}else if(corner==2){
						r.size.x+=mouseMove.x; r.size.y-=mouseMove.x*-sizeRelation;
					}else if(corner==3){
						r.size.x-=mouseMove.x; r.size.y-=mouseMove.x*sizeRelation;
						r.pos.x+=mouseMove.x;
					}
				} else {
					if(corner==0){
						r.size.x-=mouseMove.x; r.size.y+=mouseMove.y;
						r.pos.x+=mouseMove.x; r.pos.y-=mouseMove.y;
					}else if(corner==1){
						r.size.x+=mouseMove.x; r.size.y+=mouseMove.y;
						r.pos.y-=mouseMove.y;
					}else if(corner==2){
						r.size.x+=mouseMove.x; r.size.y-=mouseMove.y;
					}else if(corner==3){
						r.size.x-=mouseMove.x; r.size.y-=mouseMove.y;
						r.pos.x+=mouseMove.x;
					}
				}
			}
		} else {
			corner = -1;
			leftBeingPressed = false;
		}
		if (Mouse.isButtonDown(2)){
			if(mousePos.x >= r.pos.x&&mousePos.x <= r.pos.x+r.size.x&&mousePos.y >= r.pos.y&&mousePos.y <= r.pos.y+r.size.y){
				r.pos.x += Mouse.getDX();
				r.pos.y -= Mouse.getDY();
			}
		}
	}
}
