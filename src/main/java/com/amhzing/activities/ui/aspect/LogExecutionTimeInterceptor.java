package com.amhzing.activities.ui.aspect;

import com.amhzing.activities.ui.web.MeasurableMetric;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.GaugeService;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.lang3.StringUtils.lowerCase;

@Component
@Aspect
public class LogExecutionTimeInterceptor {

    private static final String METRIC_PREFIX = "activities.ui";

    private final GaugeService gaugeService;

    @Autowired
    public LogExecutionTimeInterceptor(final GaugeService gaugeService) {
        this.gaugeService = gaugeService;
    }

    @Pointcut("execution(public * *(..))")
    private void anyPublicOperation() { }

    @Around("anyPublicOperation() && @annotation(com.amhzing.activities.ui.annotation.LogExecutionTime)")
    public Object logExecutionTaken(final ProceedingJoinPoint joinPoint) throws Throwable
    {
        final String nameOfClass = joinPoint.getTarget().toString();
        final Logger logger = LoggerFactory.getLogger(nameOfClass);

        final String shortDescr = joinPoint.toShortString();
        final String nameOfMethod = joinPoint.getSignature().getName();

        final StopWatch sw = new StopWatch();

        // Start the stopwatch
        sw.start(nameOfMethod);

        // Invoke method
        final Object retVal = joinPoint.proceed();

        // Stop the stopwatch
        sw.stop();

        final Long totalTimeMillis = Long.valueOf(sw.getTotalTimeMillis());
        gaugeMetrics(joinPoint, nameOfMethod, totalTimeMillis);

        logger.info("{} took {}ms", new Object[] {shortDescr, totalTimeMillis});

        return retVal;
    }

    private void gaugeMetrics(final ProceedingJoinPoint joinPoint, final String nameOfMethod, final Long totalTimeMillis) {
        gaugeQuantityMetric(joinPoint, nameOfMethod);
        gaugeResponseMetric(nameOfMethod, totalTimeMillis);
    }

    private void gaugeQuantityMetric(final ProceedingJoinPoint joinPoint, final String nameOfMethod) {
        final List<MeasurableMetric> measurableMetrics = Stream.of(joinPoint.getArgs())
                                                               .filter(MeasurableMetric.class::isInstance)
                                                               .map(MeasurableMetric.class::cast)
                                                               .collect(Collectors.toList());

        measurableMetrics.stream()
                         .forEach(metric -> {
                             final String metricName = METRIC_PREFIX + lowerCase(nameOfMethod) + "." + lowerCase(metric.id()) + ".quantity";
                             gaugeService.submit(metricName, metric.quantity());
                         });
    }

    private void gaugeResponseMetric(final String nameOfMethod, final Long totalTimeMillis) {
        final String metricName = METRIC_PREFIX + lowerCase(nameOfMethod) + ".response.time";
        gaugeService.submit(metricName, totalTimeMillis.doubleValue());
    }
}
