package com.funkydonkies.w4v3.obstacles;

import com.jme3.scene.Geometry;

public class ObstacleFactory {
	public Obstacle makeObstacle(String type){
	      if(type == null){
	          return null;
	       }		
	       if(type.equalsIgnoreCase("SPIKE")){
	          return new Spike(10, 10);
	          
	       } else if(type.equalsIgnoreCase("CLOSINGBOX")){
	          return new ClosingBox(2,4,1);
	          
	       } else {   
	    	   return null;
	       }
	}
}
