package vn.vimo.risk.configuration;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.config.TopicConfig;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.errors.LogAndContinueExceptionHandler;
import org.apache.kafka.streams.processor.WallclockTimestampExtractor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration;
import org.springframework.kafka.config.KafkaStreamsConfiguration;
import org.springframework.kafka.config.StreamsBuilderFactoryBean;
import org.springframework.kafka.support.serializer.JsonSerde;
import vn.vimo.risk.kafka.CustomInfrastructureCustomizer;

import java.util.HashMap;
import java.util.Map;

import static java.util.Map.entry;
import static org.apache.kafka.clients.consumer.ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG;
import static org.apache.kafka.streams.StreamsConfig.*;

@Configuration
@EnableKafka
public class ProcessorConfig {


    @Value("${kafka-topic.output-topic}")
    private String OUTPUT_TOPIC;
    @Value("${kafka-topic.input-topic}")
    private String INPUT_TOPIC ;

    private final static String bootstrapServers = "localhost:9092";
    private final static String applicationId = "kafka-producer-api-example-application";

    public ProcessorConfig() {

    }

    @Bean
    public KafkaStreamsConfiguration kafkaStreamsConfigConfiguration() {
        return new KafkaStreamsConfiguration(
                Map.ofEntries(
                        entry(APPLICATION_ID_CONFIG, applicationId),
                        entry(DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor.class.getName()),
                        entry(BOOTSTRAP_SERVERS_CONFIG, bootstrapServers),
                        entry(NUM_STREAM_THREADS_CONFIG, 1),
                        entry(consumerPrefix(SESSION_TIMEOUT_MS_CONFIG), 30000),
                        entry(consumerPrefix(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG), "earliest"),
                        // PROD CONFS
                        entry(DEFAULT_DESERIALIZATION_EXCEPTION_HANDLER_CLASS_CONFIG, LogAndContinueExceptionHandler.class),
                        entry(REPLICATION_FACTOR_CONFIG, 1),
                        entry(CACHE_MAX_BYTES_BUFFERING_CONFIG, 10 * 1024 * 1024L), // 10MB cache
                        entry(topicPrefix(TopicConfig.RETENTION_MS_CONFIG), Integer.MAX_VALUE),
                        entry(producerPrefix(ProducerConfig.ACKS_CONFIG), "all"),
                        entry(producerPrefix(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG), 2147483647),
                        entry(producerPrefix(ProducerConfig.MAX_BLOCK_MS_CONFIG), 9223372036854775807L)));
    }

    @Bean("riskStreamBuilderFactoryBean")
    @Primary
    public StreamsBuilderFactoryBean streamsBuilderFactoryBean(KafkaStreamsConfiguration kafkaStreamsConfiguration)throws  Exception{
        StreamsBuilderFactoryBean streamsBuilderFactoryBean = new StreamsBuilderFactoryBean(kafkaStreamsConfiguration);
        streamsBuilderFactoryBean.afterPropertiesSet();
        streamsBuilderFactoryBean.setInfrastructureCustomizer(new CustomInfrastructureCustomizer(INPUT_TOPIC,OUTPUT_TOPIC));
        streamsBuilderFactoryBean.setCloseTimeout(10);
        return streamsBuilderFactoryBean;
    }
}
