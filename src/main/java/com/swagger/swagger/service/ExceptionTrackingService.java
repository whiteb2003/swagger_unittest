package com.swagger.swagger.service;

import com.swagger.swagger.exception.AppException;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
@Service
public class ExceptionTrackingService {
    private final AtomicInteger exceptionCount = new AtomicInteger(0);
    private LocalDateTime lastResetTime = LocalDateTime.now();
    private final List<AppException> exceptions = new ArrayList<>();

    public void logException(AppException exception) {
        exceptions.add(exception);
        exceptionCount.incrementAndGet();
    }

    public int getExceptionCount() {
        return exceptionCount.get();
    }

    public List<AppException> getExceptions() {
        return new ArrayList<>(exceptions);
    }

    public LocalDateTime getLastResetTime() {
        return lastResetTime;
    }

    public void resetExceptionCount() {
        exceptionCount.set(0);
        exceptions.clear();
        lastResetTime = LocalDateTime.now();
    }
}
