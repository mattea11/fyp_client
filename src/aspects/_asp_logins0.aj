package aspects;

import larva.*;
public aspect _asp_logins0 {

public static Object lock = new Object();

boolean initialized = false;

after():(staticinitialization(*)){
if (!initialized){
	initialized = true;
	_cls_logins0.initialize();
}
}
before () : (call(* *.goodLogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_logins0.lock){

_cls_logins0 _cls_inst = _cls_logins0._get_cls_logins0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 20/*goodLogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 20/*goodLogin*/);
}
}
before () : (call(* *.badLogin(..)) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_logins0.lock){

_cls_logins0 _cls_inst = _cls_logins0._get_cls_logins0_inst();
_cls_inst._call(thisJoinPoint.getSignature().toString(), 18/*badLogin*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 18/*badLogin*/);
}
}
before ( boolean locked) : (call(* *.setLocked(..)) && args(locked) && !cflow(adviceexecution()) && !cflow(within(larva.*))  && !(within(larva.*))) {

synchronized(_asp_logins0.lock){

_cls_logins0 _cls_inst = _cls_logins0._get_cls_logins0_inst();
_cls_inst.locked = locked;
_cls_inst._call(thisJoinPoint.getSignature().toString(), 22/*unlockAccount*/);
_cls_inst._call_all_filtered(thisJoinPoint.getSignature().toString(), 22/*unlockAccount*/);
}
}
}