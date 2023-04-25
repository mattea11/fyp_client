package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.javatuples.Pair;
import org.json.JSONObject;

public class JSON_cleaner_creator {
	
	public Pair<String, Double> clean_data(JSONObject data){
		Pair<String, Double> data_to_send = null;
		if(data.has("lin_vel")){
		data_to_send = Pair.with("lin_vel", data.getDouble("lin_vel"));
		}
		if(data.has("mast_angle")){
			data_to_send = Pair.with("mast_angle", data.getDouble("mast_angle"));
		}
		if(data.has("dist")){
			data_to_send = Pair.with("dist", data.getDouble("dist"));
		}
		return data_to_send;
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
