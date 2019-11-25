package HelloWorld;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.nio.charset.StandardCharsets;

/**
 * @author Sidian.Luo
 * @date 2019/11/25 13:29
 */
public class Receiver {
    private final static String QUEUE_NAME = "hello";

    public static void main(String[] argv) throws Exception {
        //set config
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("sidian123.geely.com");
        factory.setUsername("sidian");
        factory.setPassword("123456");
        //connect to server, but without try-width-resource to close ,因为消费者异步监听后, 将存在子线程, 主线程的main()方法结束了, 主线程也不会结束, 而是等待子线程退出. 因此不应该在main()方法中释放资源
        Connection connection = factory.newConnection();
        //acquire channel, like socket
        Channel channel = connection.createChannel();
        //get queue to receive
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
        //start to receive
        channel.basicConsume(QUEUE_NAME, true, (consumerTag, delivery) -> {//callback of received
            String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
            System.out.println(" [x] Received '" + message + "'");
        }, consumerTag -> { });
    }

}
