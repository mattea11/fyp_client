package aspects;

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
before () : (call(* *.goodLogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 14/*goodLogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 14/*goodLogin*/);
}
}
before () : (call(* *.badLogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 12/*badLogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 12/*badLogin*/);
}
}
before ( boolean locked) : (call(* *.setLocked(..)) && args(locked) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_rover_mon0.lock){

_cls_rover_mon0 _cls_inst = _cls_rover_mon0._get_cls_rover_mon0_inst();
_cls_inst.locked = locked;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 16/*unlockAccount*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 16/*unlockAccount*/);
}
}
}