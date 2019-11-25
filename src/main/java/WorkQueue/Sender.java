package WorkQueue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author Sidian.Luo
 * @date 2019/11/25 13:10
 */
public class Sender {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        //set config
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("sidian123.geely.com");
        factory.setUsername("sidian");
        factory.setPassword("123456");
        //connect to server
        try (Connection connection = factory.newConnection();
             //acquire channel, like acquire socket
             Channel channel = connection.createChannel()) {
             //get queue for send
            channel.queueDeclare(QUEUE_NAME, false, false, false, null);
            //command line args as message
            String message = String.join(" ", argv);
            //send
            channel.basicPublish("", QUEUE_NAME, null, message.getBytes(StandardCharsets.UTF_8));
            System.out.println(" [x] Sent '" + message + "'");
        }
    }
}
