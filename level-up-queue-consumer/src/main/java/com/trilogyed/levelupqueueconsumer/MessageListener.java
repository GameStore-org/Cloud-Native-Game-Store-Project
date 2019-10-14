package com.trilogyed.levelupqueueconsumer;

import com.trilogyed.levelupqueueconsumer.util.feign.LevelUpClient;
import com.trilogyed.levelupqueueconsumer.util.messages.LevelUp;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageListener {

    @Autowired
    private LevelUpClient client;

    @RabbitListener(queues = LevelUpQueueConsumerApplication.QUEUE_NAME)
    public void receiveMessage(LevelUp msg) {

        System.out.println(msg.toString());
        if(msg.getLevelUpId() != 0){
            client.updateLevelUp(msg.getLevelUpId(), msg);
            System.out.println("=== Update LevelUp ===");
        }else {
            client.addLevelUp(msg);
            System.out.println("=== Create new LevelUp ===");
        }
    }
}
