import org.ros.node.NodeMain;

public class Runner {

    public static void main(String[] args) {

    	
        // Create a new ROS instance
        Ros ros = Ros.getInstance();

        // Connect to ROSbridge on the robot
        RosBridge rosBridge = new RosBridge("ws://robot-ip:9090");

        // Initialize the ROS system
        ros.init("RosbridgeExample");

        // Create a publisher for sending messages to the ROS node
        rosBridge.advertise("/pose_topic", "geometry_msgs/Pose");

        // Create a subscriber for receiving messages from the ROS node
        rosBridge.subscribe("/string_topic", "std_msgs/String", new MessageListener() {
            @Override
            public void onNewMessage(Message message) {
                // Handle the received message
                String stringMsg = (String) message;
                System.out.println("Received message: " + stringMsg.getData());
            }
        });

        // Create a service client for calling a ROS service
        ServiceClient<ros.pkg.std_srvs.srv.TriggerRequest, ros.pkg.std_srvs.srv.TriggerResponse> serviceClient = 
                rosBridge.createServiceClient("/service_name", "std_srvs/Trigger");

        // Call the service
        serviceClient.callService(new ros.pkg.std_srvs.srv.TriggerRequest(), new ServiceResponseListener<ros.pkg.std_srvs.srv.TriggerResponse>() {
            @Override
            public void onSuccess(ros.pkg.std_srvs.srv.TriggerResponse response) {
                System.out.println("Service call succeeded");
            }

            public void onFailure(Exception e) {
                System.err.println("Service call failed: " + e.getMessage());
            }
        });

        // Create a message to send to the ROS node
        Pose poseMsg = new Pose();
        poseMsg.getPosition().setX(1.0);
        poseMsg.getPosition().setY(2.0);
        poseMsg.getPosition().setZ(3.0);

        // Publish the message to the ROS node
        rosBridge.publish("/pose_topic", poseMsg);

        // Wait for messages and services to complete
        ros.spin();
    }
}
