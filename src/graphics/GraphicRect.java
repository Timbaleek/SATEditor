package graphics;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Vector2f;
import org.newdawn.slick.opengl.Texture;

import main.Main;

public class GraphicRect {
	public Vector2f pos;
	public Vector2f size;
	private Texture tex;
	public String textureName;
	public boolean isActive;
	
	public GraphicRect(Vector2f pos, Vector2f size, String textureName) {
		this.pos = pos;
		this.size = size;
		this.textureName = textureName;
		tex = GraphicRectLoader.initTex(textureName);
	}
	
	public void render(){
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		if(isActive) GL11.glColor4f(0, 255, 0, Main.transparency);
		else GL11.glColor4f(0, 0, 255, Main.transparency);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2f(pos.x-5,pos.y-5);
		GL11.glVertex2f(pos.x+size.x+5,pos.y-5);
		GL11.glVertex2f(pos.x+size.x+5,pos.y+size.y+5);
		GL11.glVertex2f(pos.x-5,pos.y+size.y+5);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glColor3f(255, 255, 255);
		tex.bind();
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glTexCoord2f(0, 0);
		GL11.glVertex2f(pos.x,pos.y);
		GL11.glTexCoord2f(0, 1);
		GL11.glVertex2f(pos.x,pos.y+size.y);
		GL11.glTexCoord2f(1, 1);
		GL11.glVertex2f(pos.x+size.x,pos.y+size.y);
		GL11.glTexCoord2f(1, 0);
		GL11.glVertex2f(pos.x+size.x,pos.y);
		GL11.glEnd();
	}

	public void saveGraphicRect(String path, int rectNumber){
		try {
			Writer wr = new FileWriter(path + rectNumber +".txt");
			wr.write(pos.x+","+pos.y+",");
			wr.write(size.x+","+size.y+",");
			wr.write(textureName);
			wr.flush();
			wr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
