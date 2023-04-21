package main;

import org.json.JSONObject;

public class ros_mon_data {
	
	public static double cleaned_nav_data(JSONObject distance){
		double rad = distance.getDouble("mast_angle");
        return rad;
	}
	
	public static double cleaned_velocity_data(JSONObject velocity){
		double vel = velocity.getDouble("velocity");
        return vel;
	}
	
	public static double cleaned_mast_data(JSONObject radians){
		double rad = radians.getDouble("mast_angle");
        return rad;
	}
	
	public static void tooClose(boolean fix){
		if(fix){
			
		}else{
			  
		}
	}

}
