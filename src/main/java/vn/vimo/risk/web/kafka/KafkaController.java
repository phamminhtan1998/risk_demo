package vn.vimo.risk.web.kafka;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import vn.vimo.risk.service.StateStoreQueryService;

@RestController
public class KafkaController {
    @Autowired
    private StateStoreQueryService stateStoreQueryService ;
    @GetMapping(value = "/kafka")
    public String getKafka(){
       return  stateStoreQueryService.getValue();
    }
}
