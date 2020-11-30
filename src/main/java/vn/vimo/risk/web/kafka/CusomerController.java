package vn.vimo.risk.web.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class CusomerController {

    @KafkaListener(topics = "test_output" ,groupId = "group-id")
    public String cosume(String message){
        System.out.println(message+"==================message from output topic ==============");
        System.out.println("runing");
        return  message ;
    }

}
