package util;

import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import entities.Polygon;

//Idea from:
//https://stackoverflow.com/questions/563198/how-do-you-detect-where-two-line-segments-intersect
//https://www.youtube.com/watch?v=7Ik2vowGcU0

public class CollisionHandler {

	public static Line currentLine = new Line(new Vector2f(0,0),new Vector2f(0,0));
	public static Line currentNormal = new Line(new Vector2f(0,0),new Vector2f(0,0));
	public static Vector2f doSATCollision(CollidingGameEntity movingE, CollidingGameEntity staticE) {

		float overlap = Float.MAX_VALUE;
					
			for (int i = 0; i < staticE.shape.size(); i++) {
				Vector2f a = staticE.shape.get(i);
				Vector2f b = staticE.shape.get((i + 1) % staticE.shape.size());
				Vector2f edge = new Vector2f(b.x-a.x, b.y-a.y);
				
				Vector2f normal;
				normal = new Vector2f((edge.y),-(edge.x));
				//normal = new Vector2f(-(edge.y),(edge.x));
				
				normal.normalise();
				
				currentNormal = new Line(new Vector2f(normal.x+b.x, normal.y+b.y),
										 new Vector2f(normal.x*100+b.x, normal.y*100+b.y));
				new Line(a, b).render();
				
				float min1 = Float.MAX_VALUE, max1 = Float.MIN_VALUE;
				for(Vector2f p:staticE.shape){ // Farthest points
					float q = Vector2f.dot(normal,p);
					min1 = Math.min(min1, q);
					max1 = Math.max(max1, q);
				}
				
				float min2 = Float.MAX_VALUE, max2 = Float.MIN_VALUE;
				for(Vector2f p:movingE.shape){ // Farthest points
					float q = Vector2f.dot(normal,p);
					min2 = Math.min(min2, q);
					max2 = Math.max(max2, q);
				}
				
				overlap = Math.min(Math.min(max1,max2) - Math.max(min1, min2), overlap);
				
				if (!(max2 >= min1 && max1 >= min2)) {//axis not overlapping
					return null;
				} 
				
			}

			Vector2f d = new Vector2f(staticE.pos.x - movingE.pos.x, staticE.pos.y - movingE.pos.y); //minimalTranslationVector
			float s = d.length();
			
			Vector2f move = new Vector2f(-overlap * d.x / s, -overlap * d.y / s); //block
			
			return move;
		
	}
	
	public static boolean detectSATCollision(CollidingGameEntity movingE, CollidingGameEntity staticE) {
		if(doSATCollision(movingE, staticE) != null){
			return true;
		}
		return false;
	}
	
	public static void resolveSATCollision(CollidingGameEntity movingE, CollidingGameEntity staticE) {
		Vector2f move = doSATCollision(movingE, staticE);
		Vector2f move1 = doSATCollision(staticE, movingE);
		if(move != null){
			if(move1.length()<move.length()){
				movingE.move(move1);
			} else {
				movingE.move(move);	
			}
		}
	}
	
	public static void renderNormals(Polygon e){
		for (int i = 0; i < e.shape.size(); i++) {
			Vector2f a = e.shape.get(i);
			Vector2f b = e.shape.get((i + 1) % e.shape.size());
			Vector2f edge = new Vector2f(b.x-a.x, b.y-a.y);
			
			Vector2f normal;
			normal = new Vector2f(-(edge.y),(edge.x));
			
			if(!normal.equals(new Vector2f(0,0))){
				normal.normalise();
			}
			
			currentNormal = new Line(new Vector2f(normal.x+b.x, normal.y+b.y),
									 new Vector2f(normal.x*100+b.x, normal.y*100+b.y));
			currentNormal.render();
		}
	}
}
