package com.davinchicoder.spring.kafka.stream.infrastructure.event.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Slf4j
public class AspectLogger {


    @Pointcut("execution(* com.davinchicoder..StreamBridgeWrapper.send(*))")
    public void send() {
        // need to capture by AOP
    }

    @Pointcut("execution(* com.davinchicoder..*Consumer.accept(*))")
    public void accept() {
        // need to capture by AOP
    }


    @Before("(send())")
    public void logEventSent(JoinPoint joinPoint) {
        Object message = joinPoint.getArgs()[0];

        log.info(
                "NotifyEventSendAspect: pointcut: {}.{} - message: {}",
                joinPoint.getSignature().getDeclaringType(),
                joinPoint.getSignature().getName(),
                message
        );
    }

    @Before("(accept())")
    public void logEventAccepted(JoinPoint joinPoint) {

        Object message = joinPoint.getArgs()[0];

        log.info(
                "NotifyEventAcceptedAspect: pointcut: {}.{} - message: {}",
                joinPoint.getSignature().getDeclaringType().getSimpleName(),
                joinPoint.getSignature().getName(),
                message
        );
    }

}

