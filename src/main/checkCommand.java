package main;

public class checkCommand {
	
	public double get_change_data(){
		double data = GlobalVar.change_data.getValue1();
		return data;
	}

	public double get_total_data(){
		double data = GlobalVar.curr_data.getValue1() + GlobalVar.change_data.getValue1();
		return data;
	}
	
	public boolean check_speed(double speed){
		boolean ret = false;
		if(speed < GlobalVar.max_speed){
			ret =  true;
		}else if(speed >= GlobalVar.max_speed){
			ret =  false;
		}
		return ret;
	}
	
	public boolean check_vert_ang(double angle){
		boolean ret = false;
		if(angle < GlobalVar.max_vert_angle){
			ret =  true;
		}else if(angle >= GlobalVar.max_vert_angle){
			ret =  false;
		}
		return ret;
	}
	
	
	public double get_total_ang(){
		double angle = GlobalVar.curr_data.getValue1() + GlobalVar.change_data.getValue1();
		return angle;
	}
	
}
