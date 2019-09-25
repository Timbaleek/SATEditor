package util;

import org.lwjgl.input.Mouse;
import org.lwjgl.util.vector.Vector2f;

import entities.Polygon;
import main.Camera;

public class PolygonEditor {

	static final float range = 30;
		
	private static boolean leftBeingPressed = false;
	private static boolean middleBeingPressed = false;
	static int index = -1;
	static Polygon startPolygon = null;

	public static void update(Polygon p){
		Vector2f mousePos = Camera.single.getAbsoluteMouse();
		
		if(Mouse.isButtonDown(0)){ //pressed
			if(!leftBeingPressed){
				leftBeingPressed = true;
				index = p.getVertIndexAtPos(mousePos, range);
				if(index < 0){ //no index found
		// add new point
					p.addVert(mousePos);
				}
		// move point
			} else {
				if(index>=0&&p!=null){
					p.setVert(index, mousePos);
				}
			}
			p.pos = p.getCenterPoint();
		} else if(!Mouse.isButtonDown(0)&&leftBeingPressed){ //released
			leftBeingPressed = false;
		}
		//delete point
		if(Mouse.isButtonDown(1)){
			index = p.getVertIndexAtPos(mousePos, range);
			if(index >= 0)	p.delVert(index);
			p.pos = p.getCenterPoint();
		} 
		// move whole polygon
		if(Mouse.isButtonDown(2)){
			if(!middleBeingPressed){
				middleBeingPressed = true;
				startPolygon = p;
			} else {
				Vector2f mouseMove = new Vector2f(Mouse.getDX(),Mouse.getDY());
				for(int i = 0; i < p.shape.size(); i++){
					p.setVert(i, new Vector2f(startPolygon.shape.get(i).x+mouseMove.x,
											  startPolygon.shape.get(i).y-mouseMove.y));
				}
				p.pos = new Vector2f(startPolygon.pos.x + mouseMove.x, startPolygon.pos.y - mouseMove.y);
			}
		} else if(!Mouse.isButtonDown(2)&&middleBeingPressed){middleBeingPressed = false;}
	}
}
