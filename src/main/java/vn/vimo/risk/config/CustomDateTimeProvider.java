package vn.vimo.risk.config;

import org.springframework.data.auditing.DateTimeProvider;
import org.springframework.stereotype.Component;
import vn.vimo.risk.utils.DateTimeUtil;

import java.time.temporal.TemporalAccessor;
import java.util.Optional;

@Component("dateTimeProvider")
public class CustomDateTimeProvider implements DateTimeProvider {

    @Override
    public Optional<TemporalAccessor> getNow() {
        return Optional.of(DateTimeUtil.now());
    }
}
