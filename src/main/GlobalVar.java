package main;

import org.javatuples.Pair;

public class GlobalVar {
	//global varaibles serving as buffers for the command data as it is processed and checked
	static Pair<String, Double> obj_dist = null;
	static Pair<String, Double> x_data = null;
	static Pair<String, Double> y_data = null;
	static Pair<String, Double> turn_z_data = null;
	static Pair<String, Double> turn_w_data = null;

	static Pair<String, Double> curr_data = null;
	static Pair<String, Double> change_data = null;

	//hardcoded value of the thresholds of the respective commands being monitored
	final static double min_dist = 5.0;
	final static double max_move = 3.0;
	final static double max_speed = 5.0;
	final static double max_vert_angle = 0.08;

}
