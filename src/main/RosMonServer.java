package main;

//import org.ros.node.NodeMain;
//import org.ros.node.AbstractNodeMain;
//import org.ros.node.ConnectedNode;
//import org.ros.node.Node;
//import org.ros.namespace.GraphName;
//import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import ros.Publisher;
import ros.RosBridge;
import ros.RosListenDelegate;
import ros.SubscriptionRequestMsg;
import ros.msgs.std_msgs.PrimitiveMsg;
import ros.tools.MessageUnpacker;

public class RosMonServer {

	public static void main(String[] args) {
			if(args.length != 1){
				System.out.println("Need the rosbridge websocket URI provided as argument. For example:\n\tws://localhost:9090");
				System.exit(0);
			}

			RosBridge bridge = new RosBridge();
			bridge.connect(args[0], true);

			bridge.subscribe(SubscriptionRequestMsg.generate("/larvaToRos")
						.setType("std_msgs/String")
						.setThrottleRate(1)
						.setQueueLength(1),
					new RosListenDelegate() {
						@Override
						public void receive(JsonNode data, String stringRep) {
							MessageUnpacker<PrimitiveMsg<String>> unpacker = new MessageUnpacker<PrimitiveMsg<String>>(PrimitiveMsg.class);
							PrimitiveMsg<String> msg = unpacker.unpackRosMessage(data);
							System.out.println(msg.data);
						}
					}
			);



			Publisher pub = new Publisher("/java_to_ros", "std_msgs/String", bridge);

			for(int i = 0; i < 100; i++) {
				pub.publish(new PrimitiveMsg<String>("hello from java " + i));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}

//    public static void main(String[] args) {
//
//        // Create a new ROS instance
//        Ros ros = Ros.getInstance();
//
//        // Connect to ROSbridge on the robot
//        RosBridge rosBridge = new RosBridge("ws://robot-ip:9090");
//
//        // Initialize the ROS system
//        ros.init("RosbridgeExample");
//
//        // Create a publisher for sending messages to the ROS node
//        rosBridge.advertise("/pose_topic", "geometry_msgs/Pose");
//
//        // Create a subscriber for receiving messages from the ROS node
//        rosBridge.subscribe("/string_topic", "std_msgs/String", new MessageListener() {
//            @Override
//            public void onNewMessage(Message message) {
//                // Handle the received message
//                String stringMsg = (String) message;
//                System.out.println("Received message: " + stringMsg.getData());
//            }
//        });
//
//        // Create a service client for calling a ROS service
//        ServiceClient<ros.pkg.std_srvs.srv.TriggerRequest, ros.pkg.std_srvs.srv.TriggerResponse> serviceClient = 
//                rosBridge.createServiceClient("/service_name", "std_srvs/Trigger");
//
//        // Call the service
//        serviceClient.callService(new ros.pkg.std_srvs.srv.TriggerRequest(), new ServiceResponseListener<ros.pkg.std_srvs.srv.TriggerResponse>() {
//            @Override
//            public void onSuccess(ros.pkg.std_srvs.srv.TriggerResponse response) {
//                System.out.println("Service call succeeded");
//            }
//
//            @Override
//            public void onFailure(Exception e) {
//                System.err.println("Service call failed: " + e.getMessage());
//            }
//        });
//
//        // Create a message to send to the ROS node
//        Pose poseMsg = new Pose();
//        poseMsg.getPosition().setX(1.0);
//        poseMsg.getPosition().setY(2.0);
//        poseMsg.getPosition().setZ(3.0);
//
//        // Publish the message to the ROS node
//        rosBridge.publish("/pose_topic", poseMsg);
//
//        // Wait for messages and services to complete
//        ros.spin();
//    }
}