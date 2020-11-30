package vn.vimo.risk.service;

import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicReference;

@Service
public class StateStoreQueryService {
    private final static String stateStoreName = "risk-k-table";
    private final StreamsBuilderFactoryBean streamsBuilderFactoryBean;
    public StateStoreQueryService(StreamsBuilderFactoryBean streamsBuilderFactoryBean){
        this.streamsBuilderFactoryBean=streamsBuilderFactoryBean ;
    }
    public String getValue(){
        AtomicReference<String> value= new AtomicReference<>(" ");
        System.out.println(getReadOnlyStore().all().hasNext());
        getReadOnlyStore().all().forEachRemaining(stringStringKeyValue -> value.updateAndGet(v -> v + stringStringKeyValue));
        return value.get();
    }

    private ReadOnlyKeyValueStore<String,String> getReadOnlyStore(){
        return streamsBuilderFactoryBean.getKafkaStreams().store(StoreQueryParameters.fromNameAndType(stateStoreName, QueryableStoreTypes.keyValueStore()));
    }
}
