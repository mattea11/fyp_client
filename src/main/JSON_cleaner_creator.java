package main;

import org.javatuples.Pair;
import org.json.JSONObject;

public class JSON_cleaner_creator {

	public Pair<String, Double> get_obj_dist(JSONObject data) {
		Pair<String, Double> obj_dist = null;
		if (data.has("curr_distances")) {
			obj_dist = Pair.with("curr_distances", data.getDouble("curr_distances"));
		} else {
			obj_dist = null;
		}
		return obj_dist;
	}

	public Pair<String, Double> get_nav_command(JSONObject data, String key) {
		Pair<String, Double> nav_val = null;
		if (data.has("new_nav")) {
			JSONObject new_nav = data.getJSONObject("new_nav");
			if (new_nav.has(key)) {
				nav_val = Pair.with(key, new_nav.getDouble(key));
			}
		} else {
			nav_val = null;
		}
		return nav_val;
	}

	public Pair<String, Double> get_curr_data(JSONObject data) {
		Pair<String, Double> curr_data = null;
		if (data.has("curr_speed")) {
			curr_data = Pair.with("curr_speed", data.getDouble("curr_speed"));
		} else if (data.has("curr_vert_ang")) {
			curr_data = Pair.with("curr_vert_ang", data.getDouble("curr_vert_ang"));
		} else if (data.has("end_program")) {
			curr_data = Pair.with("end_program", data.getDouble("end_program"));
		} else {
			curr_data = null;
		}
		return curr_data;
	}

	public Pair<String, Double> get_change_data(JSONObject data) {
		Pair<String, Double> change_data = null;
		if (data.has("change_speed")) {
			change_data = Pair.with("change_speed", data.getDouble("change_speed"));
		} else if (data.has("change_vert_ang")) {
			change_data = Pair.with("change_vert_ang", data.getDouble("change_vert_ang"));
		} else if (data.has("end_program")) {
			change_data = Pair.with("end_program", data.getDouble("end_program"));
		} else {
			change_data = null;
		}
		return change_data;
	}
}
