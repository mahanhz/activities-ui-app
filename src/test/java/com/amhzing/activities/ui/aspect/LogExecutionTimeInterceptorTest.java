package com.amhzing.activities.ui.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.boot.actuate.metrics.GaugeService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
public class LogExecutionTimeInterceptorTest {

    @Mock
    private GaugeService gaugeService;

    @Mock
    private ProceedingJoinPoint joinPoint;

    @Mock
    private Signature signature;

    private LogExecutionTimeInterceptor interceptor;

    @Before
    public void setUp() {
        interceptor = new LogExecutionTimeInterceptor(gaugeService);
    }

    @Test
    public void should_fail_handling_event() throws Throwable {
        given(joinPoint.getTarget()).willReturn("Target");
        given(joinPoint.getArgs()).willReturn(new Object[0]);
        given(joinPoint.getSignature()).willReturn(signature);
        given(joinPoint.proceed()).willReturn(returnValue());
        given(signature.getName()).willReturn("Signature-Name");

        final Object retVal = interceptor.logExecutionTaken(joinPoint);

        assertThat(retVal).isEqualTo(returnValue());
    }

    private String returnValue() {
        return "Return-Value";
    }
}