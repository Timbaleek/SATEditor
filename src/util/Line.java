package util;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import main.Main;

public class Line {
	Vector2f v1, v2;
	
	public Line(Vector2f v1, Vector2f v2){
		this.v1 = v1;
		this.v2 = v2;
	}
	
	public void render(){
		GL11.glColor4f(0, 255, 0, Main.transparency);
		GL11.glBegin(GL11.GL_LINE_STRIP);
		GL11.glVertex2f(v1.x,v1.y);
		GL11.glVertex2f(v2.x,v2.y);
		GL11.glEnd();
	}
}
