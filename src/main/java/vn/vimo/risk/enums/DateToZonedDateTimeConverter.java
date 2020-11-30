package vn.vimo.risk.enums;

import org.springframework.core.convert.converter.Converter;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.Date;


/**
 * @author quangtm at 22/05/2020
 */
public enum DateToZonedDateTimeConverter implements Converter<Date, ZonedDateTime> {
    INSTANCE;

    @Override
    public ZonedDateTime convert(Date source) {
        return source.toInstant().atZone(ZoneOffset.UTC);
    }
}
