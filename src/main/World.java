package main;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.util.vector.Vector2f;

import entities.Polygon;
import graphics.GraphicRect;
import util.CollisionHandler;

public class World {
	public List<Polygon> polygons = new ArrayList<Polygon>();
	public List<GraphicRect> graphicRects = new ArrayList<GraphicRect>();
	
	public World(List<Polygon> polygons,List<GraphicRect> graphicRects){
		this.polygons = polygons;
		this.graphicRects = graphicRects;
	}
	
	public void render(){
		for (GraphicRect g:graphicRects){
			g.render();
		}
		for (Polygon p:polygons){
			p.render();
			CollisionHandler.renderNormals(p);
		}
	}
	
	public void changeActivePolygon(){
		polygons.get(Main.activeColliderIndex).isActive = false;
		Main.activeColliderIndex = (Main.activeColliderIndex + 1) % polygons.size();
		polygons.get(Main.activeColliderIndex).isActive = true;
	}
	
	public void changeActivePolygonToLast(){
		polygons.get(Main.activeColliderIndex).isActive = false;
		Main.activeColliderIndex = polygons.size() -1;
		polygons.get(Main.activeColliderIndex).isActive = true;
	}
	
	public void changeActiveRect(){
		graphicRects.get(Main.activeColliderIndex).isActive = false;
		Main.activeColliderIndex = (Main.activeColliderIndex + 1) % graphicRects.size();
		graphicRects.get(Main.activeColliderIndex).isActive = true;
	}
	
	public void changeActiveRectToLast(){
		graphicRects.get(Main.activeColliderIndex).isActive = false;
		Main.activeColliderIndex = graphicRects.size() -1;
		graphicRects.get(Main.activeColliderIndex).isActive = true;
	}
	
	public void saveWorld(String path, int worldNumber){
		for(int i = 0; i < polygons.size(); i++){
			polygons.get(i).savePolygon(path + "/worlds/world" + worldNumber + "/polygons/" , i);
		}
		for(int i = 0; i < graphicRects.size(); i++){
			graphicRects.get(i).saveGraphicRect(path + "/worlds/world" + worldNumber + "/rects/" , i);
		}
	}

	public void createNewPolygon(){
		Vector2f mousePos = Camera.single.getAbsoluteMouse();
		polygons.add(new Polygon(mousePos,new ArrayList<Vector2f>()));
	}
	
	public void createNewGraphicRect() {
		Vector2f mousePos = Camera.single.getAbsoluteMouse();
		graphicRects.add(new GraphicRect(mousePos,new Vector2f(50,50),"noTex"));
	}

}
