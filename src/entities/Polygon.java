package entities;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import main.Main;

public class Polygon {
	
	public boolean isActive;
	public Vector2f pos;
	public ArrayList<Vector2f> shape;
	
	public Polygon(Vector2f pos, ArrayList<Vector2f> shape){
		this.pos = pos;
		this.shape = shape;
	}
	
	public void render(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glColor4f(255, 0, 100, Main.transparency);
		GL11.glBegin(GL11.GL_POLYGON);
		for(Vector2f point:shape){
			GL11.glVertex2f(point.getX(),point.getY());
		}
		GL11.glEnd();
		if(isActive) GL11.glColor4f(0, 255, 0, Main.transparency);
		else GL11.glColor4f(0, 0, 255, Main.transparency);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(pos.x-5,pos.y-5);
	    GL11.glVertex2f(pos.x+5,pos.y-5);
	    GL11.glVertex2f(pos.x+5,pos.y+5);
	    GL11.glVertex2f(pos.x-5,pos.y+5);
	    GL11.glEnd();
	}

	public void move(Vector2f move){
		pos.x += move.x;
		pos.y += move.y;
		for(Vector2f p:shape){
			p.x += move.x;
			p.y += move.y;
		}
	}
	
	public void addVert(Vector2f pos){
		shape.add(pos);
	}
	
	public void setVert(int index, Vector2f pos){
		shape.set(index, pos);
	}
	
	public void delVert(int index){
		shape.remove(index);
	}
	
	public int getVertIndexAtPos(Vector2f pos, float range){
		for(int i = 0; i < shape.size(); i++){
			Vector2f p = shape.get(i); 
			if(pos.x >= p.x-range && pos.x <= p.x+range && pos.y >= p.y-range && pos.y <= p.y+range){
				return i;
			}
		}
		return -1;
	}
	
	//https://en.wikipedia.org/wiki/Centroid#Of_a_polygon
	public Vector2f getCenterPoint(){
		float f = 0, area = 0;
		Vector2f first = shape.get(0);
		Vector2f center = new Vector2f(0,0);
		for (int i = 0, j = shape.size() - 1; i < shape.size(); j = i++) {
			Vector2f p1 = shape.get(i), p2 = shape.get(j);
			f = (p1.x - first.x) * (p2.y - first.y) - (p2.x - first.x) * (p1.y - first.y);
	        area += f;
	        center.x += (p1.x + p2.x - 2 * first.x) * f;
	        center.y += (p1.y + p2.y - 2 * first.y) * f;
		}
		area *= 3;
		return new Vector2f(center.x / area + first.x,center.y / area + first.y);
	}
	
	public void savePolygon(String path, int polygonNumber){
		try {
			Writer wr = new FileWriter(path + polygonNumber +".txt");
			wr.write(pos.x+","+pos.y+"\n");
			for(Vector2f p:shape){
				wr.write((int)p.x+","+(int)p.y+"\n");
			}
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
