package com.zjry.pms.rocketmq;


import com.zjry.framework.mq.RocketMQProducer;
import com.zjry.pms.basic.constant.Constants;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendResult;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * mq生产者
 *
 * @author lf
 * @date 2021年07月06日 11:04
 */
@Component
@Slf4j
public class BasicdataProducer {

//    @Autowired
//    private RocketMQTemplate rocketMqTemplate;

    public int sendMessageOfBasicData(String message, int delayLevel, String destination, Long id) {
        int msg = 200;
        try{
//            使用框架提供的生产者类----20211202----byLicheng
//            SendResult sendResult = rocketMqTemplate.sendSync(destination, MessageBuilder.withPayload(message).setHeader(MessageConst.PROPERTY_KEYS,id).build(), 2000, delayLevel);
            SendResult sendResult = RocketMQProducer.sendSync(destination, message,id.toString(), 2000, delayLevel);
            log.info("sendTime is {}", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()));
            log.info("sendResult is {}", sendResult);
        }catch (Exception e){
            log.error("宕机：" + e.getMessage());
            msg = Constants.DOWNTIME;
        }
        return msg;
    }

}
