package main;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;

import entities.CollidingGameEntity;
import entities.Player;
import util.PolygonEditor;
import util.RectEditor;
import util.WorldLoader;

public class Main {

	final static int screenWidth = 1920;
	final static int screenHeight = 1080;

	public static void main(String[] args) {
		
		try {
			Display.setDisplayMode(new DisplayMode(screenWidth, screenHeight));
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		Display.setTitle("Editor");

    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glMatrixMode(GL11.GL_PROJECTION);
		GL11.glLoadIdentity();
		GL11.glOrtho(0, screenWidth, screenHeight, 0, 1, -1);
		GL11.glMatrixMode(GL11.GL_MODELVIEW);
		GL11.glLineWidth(5f);
		init();
		
		while (!Display.isCloseRequested()) {
			 
		    GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);	
		    render();
		    update();
	 
		    //Display.sync(120);
		    Display.update();
		}
		
			currentWorld.saveWorld("res", currentWorldNumber);
		
		Display.destroy();
	}

	public static float transparency = 0.5f;
	public static CollidingGameEntity g;
	public static Player p;
	static final int numberOfWorlds = 1;
	public static World currentWorld;
	public static int currentWorldNumber = 0;
	
	static Camera camera;
	private static void init() {
		camera = new Camera(new Vector2f(0,0), 1);
		currentWorld = WorldLoader.loadWorld(currentWorldNumber);
		
		currentWorld.polygons.get(activeColliderIndex).isActive = true;
	}
	
	public static int activeColliderIndex = 0;
	public static boolean editMode = true; //true=polygon, false=rect
	private static void update() {
		getInput();
		
		if(editMode){
			if(activeColliderIndex>=0&&currentWorld.polygons.size()>0){
				PolygonEditor.update(currentWorld.polygons.get(activeColliderIndex));
			}
		} else {
			if(activeColliderIndex>=0&&currentWorld.graphicRects.size()>0){
				RectEditor.update(currentWorld.graphicRects.get(activeColliderIndex));
			}
		}
		
		//CollisionHandler.resolveSATCollision(p,g);
	}
	
	private static void changeWorld(int worldNumber){
		currentWorldNumber = worldNumber;
		currentWorld = WorldLoader.loadWorld(currentWorldNumber);
	}
	
	private static void render() {
		GL11.glPushMatrix();
		GL11.glTranslatef(-camera.pos.x, -camera.pos.y, 0);
		
		currentWorld.render();
		
		GL11.glPopMatrix();
		//CollisionHandler.currentLine.render();
		//CollisionHandler.currentNormal.render();
	}
	
	private static void getInput(){
		while (Keyboard.next()) {
			if(Keyboard.getEventKey() == Keyboard.KEY_SPACE){
				if(Keyboard.getEventKeyState()){//pressed
					if(editMode){
						currentWorld.polygons.get(activeColliderIndex).isActive = false;
						currentWorld.graphicRects.get(activeColliderIndex).isActive = true;
					} else {
						currentWorld.polygons.get(activeColliderIndex).isActive = true;
						currentWorld.graphicRects.get(activeColliderIndex).isActive = false;
					}
					editMode = !editMode;
				}
			} else if(Keyboard.getEventKey() == Keyboard.KEY_1){
				if(Keyboard.getEventKeyState()){//pressed
					Main.changeWorld(currentWorldNumber + 1);
				}
			}
			if(editMode){
				if(Keyboard.getEventKey() == Keyboard.KEY_N){
					if(Keyboard.getEventKeyState()){
						currentWorld.createNewPolygon();
						currentWorld.changeActivePolygonToLast();
					}
				}else if(Keyboard.getEventKey() == Keyboard.KEY_TAB){
					if(Keyboard.getEventKeyState()){
						currentWorld.changeActivePolygon();
					}
				}
			} else {
				if(Keyboard.getEventKey() == Keyboard.KEY_N){
					if(Keyboard.getEventKeyState()){
						currentWorld.createNewGraphicRect();
						currentWorld.changeActiveRectToLast();
					}
				}else if(Keyboard.getEventKey() == Keyboard.KEY_TAB){
					if(Keyboard.getEventKeyState()){
						currentWorld.changeActiveRect();
					}
				}
			}
		}
		if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
			if(Mouse.isButtonDown(1)){
				camera.pos.x -= Mouse.getDX();
				camera.pos.y += Mouse.getDY();
			}
		}
	}
}

