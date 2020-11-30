package vn.vimo.risk.web.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import vn.vimo.risk.entity.Transaction;

import java.util.Date;

@RestController
public class TestProducerController {

    @Value("${kafka-topic.input-topic}")
    private String inputTopic ;

    @Autowired
    KafkaTemplate kafkaTemplate ;
    @GetMapping(value = "producer/{title}")
    public String createTransaction(
            @PathVariable(name = "title") String title) throws JsonProcessingException {
        Transaction transaction = new Transaction();
        transaction.setId(1);
        transaction.setAuthentication(true);
        transaction.setTime(new Date());
        transaction.setTitle(title);
        transaction.setType("insert time here ");
        transaction.setUsername("holly");
        ObjectMapper objectMapper = new ObjectMapper();
        System.out.println(objectMapper.writeValueAsString(transaction));
        String data =  objectMapper.writeValueAsString(transaction);
        kafkaTemplate.send(inputTopic,"user-id",data);
        return data.toString();
    }
}
