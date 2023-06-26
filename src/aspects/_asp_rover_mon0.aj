package aspects;

import main.GlobalVar;
import main.JSON_cleaner_creator;
import main.LarvaServer;
import main.RosBridgeClient;
import main.WebSocketClientHandler;
import main.checkCommand;
import org.java_websocket.client.WebSocketClient;

import larva.*;
public aspect _asp_rover_mon0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_rover_mon0.initialize();
}
}
before () : (call(* *.larva_check_valid_action2(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 4/*invalid_command*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 4/*invalid_command*/);
}
}
before () : (call(* *.get_change_data(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 0/*get_change_data*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 0/*get_change_data*/);
}
}
before ( String message) : (call(* *.send(..)) && args(message) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst.message = message;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 8/*send_command*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 8/*send_command*/);
}
}
before ( double change_speed) : (call(* *.check_speed(..)) && args(change_speed) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst.change_speed = change_speed;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*check_speed*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*check_speed*/);
}
}
before () : (call(* *.larva_check_valid_action1(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 2/*valid_command*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 2/*valid_command*/);
}
}
before ( double distance,double w,double x,double y) : (call(* *.check_vert_ang(..)) && args(distance,x,y,w) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst.distance = distance;
_cls_inst.w = w;
_cls_inst.x = x;
_cls_inst.y = y;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 10/*check_nav*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 10/*check_nav*/);
}
}
before ( double change_ang) : (call(* *.check_vert_ang(..)) && args(change_ang) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst.change_ang = change_ang;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*check_vert_ang*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*check_vert_ang*/);
}
}
before ( String message) : (call(* *.broadcast(..)) && args(message) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst.message = message;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 6/*send_move_on*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 6/*send_move_on*/);
}
}
}