package larva;


import main.GlobalVar;
import main.JSON_cleaner_creator;
import main.LarvaServer;
import main.RosBridgeClient;
import main.WebSocketClientHandler;
import main.checkCommand;
import org.java_websocket.client.WebSocketClient;

import java.util.LinkedHashMap;
import java.io.PrintWriter;

public class _cls_rover_mon0 implements _callable{

public static PrintWriter pw; 
public static _cls_rover_mon0 root;

public static LinkedHashMap<_cls_rover_mon0,_cls_rover_mon0> _cls_rover_mon0_instances = new LinkedHashMap<_cls_rover_mon0,_cls_rover_mon0>();
static{
try{
RunningClock.start();
pw = new PrintWriter("C:\\Users\\matte\\eclipse_workspace\\Oracle/src/output_rover_mon.txt");

root = new _cls_rover_mon0();
_cls_rover_mon0_instances.put(root, root);
  root.initialisation();
}catch(Exception ex)
{ex.printStackTrace();}
}

_cls_rover_mon0 parent; //to remain null - this class does not have a parent!
public static double change_speed;
public static double distance;
public static double w;
public static double x;
public static double y;
public static double change_ang;
public static String message;
int no_automata = 3;

public static void initialize(){}
//inheritance could not be used because of the automatic call to super()
//when the constructor is called...we need to keep the SAME parent if this exists!

public _cls_rover_mon0() {
}

public void initialisation() {
}

public static _cls_rover_mon0 _get_cls_rover_mon0_inst() { synchronized(_cls_rover_mon0_instances){
 return root;
}
}

public boolean equals(Object o) {
 if ((o instanceof _cls_rover_mon0))
{return true;}
else
{return false;}
}

public int hashCode() {
return 0;
}

public void _call(String _info, int... _event){
synchronized(_cls_rover_mon0_instances){
_performLogic_Navigation(_info, _event);
_performLogic_Speed(_info, _event);
_performLogic_Mast_Angle(_info, _event);
}
}

public void _call_all_filtered(String _info, int... _event){
}

public static void _call_all(String _info, int... _event){

_cls_rover_mon0[] a = new _cls_rover_mon0[1];
synchronized(_cls_rover_mon0_instances){
a = _cls_rover_mon0_instances.keySet().toArray(a);}
for (_cls_rover_mon0 _inst : a)

if (_inst != null) _inst._call(_info, _event);
}

public void _killThis(){
try{
if (--no_automata == 0){
synchronized(_cls_rover_mon0_instances){
_cls_rover_mon0_instances.remove(this);}
}
else if (no_automata < 0)
{throw new Exception("no_automata < 0!!");}
}catch(Exception ex){ex.printStackTrace();}
}

int _state_id_Navigation = 41;

public void _performLogic_Navigation(String _info, int... _event) {

_cls_rover_mon0.pw.println("[Navigation]AUTOMATON::> Navigation("+") STATE::>"+ _string_Navigation(_state_id_Navigation, 0));
_cls_rover_mon0.pw.flush();

if (0==1){}
else if (_state_id_Navigation==37){
		if (1==0){}
		else if ((_occurredEvent(_event,40/*send_command*/))){
		_cls_rover_mon0.pw .println ("Sent navigation command to ROS");

		_state_id_Navigation = 39;//moving to state sendExecToROS
		_goto_Navigation(_info);
		}
}
else if (_state_id_Navigation==41){
		if (1==0){}
		else if ((_occurredEvent(_event,42/*check_nav*/))){
		_cls_rover_mon0.pw .println ("Checking the new navigation command data and obsticle distance!");

		_state_id_Navigation = 36;//moving to state checkValid
		_goto_Navigation(_info);
		}
}
else if (_state_id_Navigation==36){
		if (1==0){}
		else if ((_occurredEvent(_event,34/*valid_command*/))){
		_cls_rover_mon0.pw .println ("Valid, send navigation command to the ROS System");

		_state_id_Navigation = 37;//moving to state commandValid
		_goto_Navigation(_info);
		}
		else if ((_occurredEvent(_event,36/*invalid_command*/))){
		_cls_rover_mon0.pw .println ("Invalid, send move on to the Control System");

		_state_id_Navigation = 38;//moving to state commandInvalid
		_goto_Navigation(_info);
		}
}
else if (_state_id_Navigation==39){
		if (1==0){}
		else if ((_occurredEvent(_event,38/*send_move_on*/))){
		_cls_rover_mon0.pw .println ("Sent send move on to the Control System");

		_state_id_Navigation = 40;//moving to state sendMoveToNextCommand
		_goto_Navigation(_info);
		}
}
}

public void _goto_Navigation(String _info){
_cls_rover_mon0.pw.println("[Navigation]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_Navigation(_state_id_Navigation, 1));
_cls_rover_mon0.pw.flush();
}

public String _string_Navigation(int _state_id, int _mode){
switch(_state_id){
case 40: if (_mode == 0) return "sendMoveToNextCommand"; else return "sendMoveToNextCommand";
case 37: if (_mode == 0) return "commandValid"; else return "commandValid";
case 36: if (_mode == 0) return "checkValid"; else return "checkValid";
case 41: if (_mode == 0) return "getDistAndCommand"; else return "getDistAndCommand";
case 39: if (_mode == 0) return "sendExecToROS"; else return "sendExecToROS";
case 38: if (_mode == 0) return "commandInvalid"; else return "commandInvalid";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_Speed = 47;

public void _performLogic_Speed(String _info, int... _event) {

_cls_rover_mon0.pw.println("[Speed]AUTOMATON::> Speed("+") STATE::>"+ _string_Speed(_state_id_Speed, 0));
_cls_rover_mon0.pw.flush();

if (0==1){}
else if (_state_id_Speed==43){
		if (1==0){}
		else if ((_occurredEvent(_event,40/*send_command*/))){
		_cls_rover_mon0.pw .println ("Sent speed command to ROS");

		_state_id_Speed = 45;//moving to state sendExecToROS
		_goto_Speed(_info);
		}
}
else if (_state_id_Speed==47){
		if (1==0){}
		else if ((_occurredEvent(_event,44/*check_speed*/))){
		_cls_rover_mon0.pw .println ("Checking the new speed command data!");

		_state_id_Speed = 42;//moving to state checkValid
		_goto_Speed(_info);
		}
}
else if (_state_id_Speed==42){
		if (1==0){}
		else if ((_occurredEvent(_event,34/*valid_command*/))){
		_cls_rover_mon0.pw .println ("Valid, send speed command to the ROS System");

		_state_id_Speed = 43;//moving to state commandValid
		_goto_Speed(_info);
		}
		else if ((_occurredEvent(_event,36/*invalid_command*/))){
		_cls_rover_mon0.pw .println ("Invalid, send move on to the Control System");

		_state_id_Speed = 44;//moving to state commandInvalid
		_goto_Speed(_info);
		}
}
else if (_state_id_Speed==45){
		if (1==0){}
		else if ((_occurredEvent(_event,38/*send_move_on*/))){
		_cls_rover_mon0.pw .println ("Sent send move on to the Control System");

		_state_id_Speed = 46;//moving to state sendMoveToNextCommand
		_goto_Speed(_info);
		}
}
}

public void _goto_Speed(String _info){
_cls_rover_mon0.pw.println("[Speed]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_Speed(_state_id_Speed, 1));
_cls_rover_mon0.pw.flush();
}

public String _string_Speed(int _state_id, int _mode){
switch(_state_id){
case 46: if (_mode == 0) return "sendMoveToNextCommand"; else return "sendMoveToNextCommand";
case 43: if (_mode == 0) return "commandValid"; else return "commandValid";
case 47: if (_mode == 0) return "getCommand"; else return "getCommand";
case 42: if (_mode == 0) return "checkValid"; else return "checkValid";
case 45: if (_mode == 0) return "sendExecToROS"; else return "sendExecToROS";
case 44: if (_mode == 0) return "commandInvalid"; else return "commandInvalid";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}
int _state_id_Mast_Angle = 53;

public void _performLogic_Mast_Angle(String _info, int... _event) {

_cls_rover_mon0.pw.println("[Mast_Angle]AUTOMATON::> Mast_Angle("+") STATE::>"+ _string_Mast_Angle(_state_id_Mast_Angle, 0));
_cls_rover_mon0.pw.flush();

if (0==1){}
else if (_state_id_Mast_Angle==49){
		if (1==0){}
		else if ((_occurredEvent(_event,40/*send_command*/))){
		_cls_rover_mon0.pw .println ("Sent Mast command to ROS");

		_state_id_Mast_Angle = 51;//moving to state sendExecToROS
		_goto_Mast_Angle(_info);
		}
}
else if (_state_id_Mast_Angle==53){
		if (1==0){}
		else if ((_occurredEvent(_event,46/*check_vert_ang*/))){
		_cls_rover_mon0.pw .println ("Checking the new Mast command data!");

		_state_id_Mast_Angle = 48;//moving to state checkValid
		_goto_Mast_Angle(_info);
		}
}
else if (_state_id_Mast_Angle==48){
		if (1==0){}
		else if ((_occurredEvent(_event,34/*valid_command*/))){
		_cls_rover_mon0.pw .println ("Valid, send Mast command to the ROS System");

		_state_id_Mast_Angle = 49;//moving to state commandValid
		_goto_Mast_Angle(_info);
		}
		else if ((_occurredEvent(_event,36/*invalid_command*/))){
		_cls_rover_mon0.pw .println ("Invalid, send move on to the Control System");

		_state_id_Mast_Angle = 50;//moving to state commandInvalid
		_goto_Mast_Angle(_info);
		}
}
else if (_state_id_Mast_Angle==51){
		if (1==0){}
		else if ((_occurredEvent(_event,38/*send_move_on*/))){
		_cls_rover_mon0.pw .println ("Sent send move on to the Control System");

		_state_id_Mast_Angle = 52;//moving to state sendMoveToNextCommand
		_goto_Mast_Angle(_info);
		}
}
}

public void _goto_Mast_Angle(String _info){
_cls_rover_mon0.pw.println("[Mast_Angle]MOVED ON METHODCALL: "+ _info +" TO STATE::> " + _string_Mast_Angle(_state_id_Mast_Angle, 1));
_cls_rover_mon0.pw.flush();
}

public String _string_Mast_Angle(int _state_id, int _mode){
switch(_state_id){
case 52: if (_mode == 0) return "sendMoveToNextCommand"; else return "sendMoveToNextCommand";
case 49: if (_mode == 0) return "commandValid"; else return "commandValid";
case 53: if (_mode == 0) return "getCommand"; else return "getCommand";
case 48: if (_mode == 0) return "checkValid"; else return "checkValid";
case 51: if (_mode == 0) return "sendExecToROS"; else return "sendExecToROS";
case 50: if (_mode == 0) return "commandInvalid"; else return "commandInvalid";
default: return "!!!SYSTEM REACHED AN UNKNOWN STATE!!!";
}
}

public boolean _occurredEvent(int[] _events, int event){
for (int i:_events) if (i == event) return true;
return false;
}
}