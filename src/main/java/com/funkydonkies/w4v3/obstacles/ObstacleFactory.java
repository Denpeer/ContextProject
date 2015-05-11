package com.funkydonkies.w4v3.obstacles;

public class ObstacleFactory {
	public Obstacle makeObstacle(String type){
	      if(type == null){
	          return null;
	       }		
	       if(type.equalsIgnoreCase("SPIKE")){
	          return new Spike();
	          
	       } else if(type.equalsIgnoreCase("CLOSINGBOX")){
	          return new ClosingBox();
	          
	       } else {   
	    	   return null;
	       }
	}
}
