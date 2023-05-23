package main;

public class checkCommand {

	public double get_change_data() {
		double data = GlobalVar.change_data.getValue1();
		return data;
	}

	public static boolean check_nav(double distance, double x, double y, double w) {
		boolean valid = false;
		if (distance <= GlobalVar.min_dist || x >= GlobalVar.max_move || y >= GlobalVar.max_move || w == 0) {
			valid = false;
		} else {
			valid = true;
		}
		return valid;
	}

	public boolean check_speed(double speed) {
		boolean ret = false;
		if (speed < GlobalVar.max_speed) {
			ret = true;
		} else if (speed >= GlobalVar.max_speed) {
			ret = false;
		}
		return ret;
	}

	public boolean check_vert_ang(double angle) {
		boolean ret = false;
		if (angle < GlobalVar.max_vert_angle) {
			ret = true;
		} else if (angle >= GlobalVar.max_vert_angle) {
			ret = false;
		}
		return ret;
	}

	public double get_total_ang() {
		double angle = GlobalVar.curr_data.getValue1() + GlobalVar.change_data.getValue1();
		return angle;
	}

}
