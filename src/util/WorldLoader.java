package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import entities.Polygon;
import graphics.GraphicRect;
import graphics.GraphicRectLoader;
import main.World;

public class WorldLoader {
	
	static public World loadWorld(String path, int worldNumber){
		List<Polygon> polygons = new ArrayList<Polygon>();
		List<GraphicRect> graphicRects = new ArrayList<GraphicRect>();
		
		String dirPath = path + worldNumber + "/polygons/";
		String[] pFiles = new File(dirPath).list();
		if(pFiles != null){
			for(String file:pFiles){
				polygons.add(PolygonLoader.load(dirPath+file));
			}
		}
		dirPath = path + worldNumber + "/rects/";
		String[] rFiles = new File(dirPath).list();
		if(rFiles != null){
			for(String file:rFiles){
				graphicRects.add(GraphicRectLoader.load(dirPath+file));
			}
		}
		return new World(polygons, graphicRects);
	}
	
}
