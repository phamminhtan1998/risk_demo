package vn.vimo.risk.kafka;

import org.apache.kafka.common.serialization.*;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.state.KeyValueStore;
import org.apache.kafka.streams.state.StoreBuilder;
import org.apache.kafka.streams.state.Stores;
import org.springframework.kafka.config.KafkaStreamsInfrastructureCustomizer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class CustomInfrastructureCustomizer implements KafkaStreamsInfrastructureCustomizer {
    private static final Serde<String> KEY_SERDE = Serdes.String();
    private static final Serde<String>  VALUE_SERDE = Serdes.String();
    private static final String RISK_k_TABLE_NAME = "risk-k-table";
    final Deserializer<String> KEY_STRING_DE = new StringDeserializer();
    final Deserializer<String> VALUE_STRING_DE= new StringDeserializer();
    final Serializer<String> KEY_STRING_SE = new StringSerializer();
    final Serializer<String> VALUE_STRING_SE= new StringSerializer();

    private String inputTopic ;
    private String outputTopic ;

    public CustomInfrastructureCustomizer(String input_topic, String output_topic) {
        this.inputTopic=input_topic;
        this.outputTopic= output_topic ;
    }
/*
*
*           PROVIDE INSTANCE OF THE STREAM BUILDER FACTORY
*
* */

    @Override
    public void configureBuilder(StreamsBuilder builder) {
//        TODO: remove


        StoreBuilder<KeyValueStore<String,String>> stateStoreBuilder =
                Stores.keyValueStoreBuilder(Stores.persistentKeyValueStore(RISK_k_TABLE_NAME),KEY_SERDE,VALUE_SERDE);
        Topology topology = builder.build();
        topology.addSource("Source", KEY_STRING_DE, VALUE_STRING_DE, inputTopic)
                .addProcessor("Process", () -> new CustomeProcessor(RISK_k_TABLE_NAME), "Source")
                .addStateStore(stateStoreBuilder, "Process")
                .addSink("SINK", "test_output",KEY_STRING_SE,VALUE_STRING_SE ,"Process");
    }
}
