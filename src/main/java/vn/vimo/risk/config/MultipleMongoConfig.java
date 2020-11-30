package vn.vimo.risk.config;

import com.mongodb.ConnectionString;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.convert.converter.Converter;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import vn.vimo.risk.enums.DateToZonedDateTimeConverter;
import vn.vimo.risk.enums.ZonedDateTimeToDateConverter;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableMongoAuditing(dateTimeProviderRef = "dateTimeProvider")
public class MultipleMongoConfig {

    @Bean
    @Primary
    public MongoTemplate mongoTemplate() {
        MappingMongoConverter converter = mappingMongoConverter(primaryMongodbFactory());
        return new MongoTemplate(primaryMongodbFactory(), converter);
    }

    @Bean
    @Primary
    public MongoDatabaseFactory primaryMongodbFactory() {
        String uri = System.getenv("SPRING_DATA_MONGODB_URI");
        return new SimpleMongoClientDatabaseFactory(new ConnectionString(uri));
    }

    private MappingMongoConverter mappingMongoConverter(final MongoDatabaseFactory mongoDbFactory) {
        MappingMongoConverter converter = new MappingMongoConverter(
                new DefaultDbRefResolver(mongoDbFactory), new MongoMappingContext());
        converter.setCustomConversions(customConversions());
        converter.afterPropertiesSet();

        return converter;
    }

    @Bean
    public MongoCustomConversions customConversions() {
        List<Converter<?, ?>> converters = new ArrayList<>();
        converters.add(DateToZonedDateTimeConverter.INSTANCE);
        converters.add(ZonedDateTimeToDateConverter.INSTANCE);
        return new MongoCustomConversions(converters);
    }


}
