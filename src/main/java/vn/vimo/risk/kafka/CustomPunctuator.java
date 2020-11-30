package vn.vimo.risk.kafka;

import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.processor.ProcessorContext;
import org.apache.kafka.streams.processor.Punctuator;
import org.apache.kafka.streams.state.KeyValueIterator;
import org.apache.kafka.streams.state.KeyValueStore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class CustomPunctuator implements Punctuator {
    private static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final Integer DELETE_X_DAYS_BEFORE = 5; // Delete from state-store
    private final ProcessorContext context ;
    private final KeyValueStore<String,String> stateStore;

    public CustomPunctuator(ProcessorContext context, KeyValueStore<String, String> stateStore) {
        this.context = context;
        this.stateStore = stateStore;
    }

    @Override
    public void punctuate(long timestamp) {
        System.out.println("Punctator started");
        KeyValueIterator<String,String> iter = stateStore.all();
//        Date delete_before_date = Date.from(LocalDateTime.now()
//        .minusDays(DELETE_X_DAYS_BEFORE)
//        .atZone(ZoneId.of("Asia/Ha_Noi")).toInstant());
        context.commit();
    }
}
