package vn.vimo.risk.enums;

import org.springframework.core.convert.converter.Converter;

import java.time.ZonedDateTime;
import java.util.Date;


/**
 * @author anhnq
 */
public enum ZonedDateTimeToDateConverter implements Converter<ZonedDateTime, Date> {
    INSTANCE;

    @Override
    public Date convert(ZonedDateTime source) {
        return Date.from(source.toInstant());
    }
}