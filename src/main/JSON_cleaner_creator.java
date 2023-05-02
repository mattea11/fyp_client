package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.javatuples.Pair;
import org.json.JSONObject;

public class JSON_cleaner_creator {
	
	public Pair<String, Double> get_curr_data(JSONObject data){
		Pair<String, Double> curr_data = null;
		if(data.has("curr_speed")){
		curr_data = Pair.with("curr_speed", data.getDouble("curr_speed"));
		}
		else if(data.has("curr_vert_ang")){
			curr_data = Pair.with("curr_vert_ang", data.getDouble("curr_vert_ang"));
		}
		else if(data.has("curr_horiz_ang")){
			curr_data = Pair.with("curr_horiz_ang", data.getDouble("curr_horiz_ang"));
		}
		else if(data.has("dist")){
			curr_data = Pair.with("dist", data.getDouble("dist"));
		}
		else if(data.has("end program")){
			curr_data = Pair.with("end program", data.getDouble("end program"));
		}
		else{
			curr_data = null;
		}
		return curr_data;
	}
	
	public Pair<String, Double> get_change_data(JSONObject data){
		Pair<String, Double> change_data = null;
		if(data.has("change_speed")){
			change_data = Pair.with("change_speed", data.getDouble("change_speed"));
		}
		else if(data.has("change_vert_ang")){
			change_data = Pair.with("change_vert_ang", data.getDouble("change_vert_ang"));
		}
		else if(data.has("change_horiz_ang")){
			change_data = Pair.with("change_horiz_ang", data.getDouble("change_horiz_ang"));
		}
		else if(data.has("dist")){
			change_data = Pair.with("dist", data.getDouble("dist"));
		}
		else if(data.has("end program")){
			change_data = Pair.with("end program", data.getDouble("end program"));
		}
		else{
			change_data = null;
		}
		return change_data;
	}

	
	public static void tooClose(boolean fix){
		if(fix){
			
		}else{
			  
		}
	}
	
//	public static JSONObject create_mast_obj(Double mast){
//		JSONObject reply = "";
//        return reply;
//	}
	
	//read teh speed passed in and cehck validity in larva
	public double validateSpeed(String filePath) {
		double speed = 0.0;
		  try {
		    FileReader fileReader = new FileReader(filePath);
		    BufferedReader bufferedReader = new BufferedReader(fileReader);

		    // Read and print each line from the file continuously
		    while (true) {
		      String line = bufferedReader.readLine();
		      if (line != null) {
		        System.out.println("Read from file: " + line);
		      } else {
		        // Sleep for a short duration before checking for new lines
		        Thread.sleep(1000); // Adjust the sleep duration as needed
		      }
		    }

		  } catch (IOException e) {
		    System.out.println("Error occurred while reading from file: " + e.getMessage());
		  } catch (InterruptedException e) {
		    System.out.println("Interrupted while sleeping: " + e.getMessage());
		  }
		  return speed;
		}

}
