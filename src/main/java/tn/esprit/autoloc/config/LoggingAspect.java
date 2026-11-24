package tn.esprit.autoloc.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LoggingAspect {

    // Avant chaque méthode de la couche service
    @Before("execution(* tn.esprit.autoloc.service.*.*(..))")
    public void logBefore(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        Object[] parameters = joinPoint.getArgs();
        log.info("Before - Méthode appelée : {} | Paramètres : {}",
                methodName, Arrays.toString(parameters));
    }

    // Après chaque méthode de la couche service
    @After("execution(* tn.esprit.autoloc.service.*.*(..))")
    public void logAfter(JoinPoint joinPoint) {
        String methodName = joinPoint.getSignature().getName();
        log.info("After - Fin d'exécution de la méthode : {}", methodName);
    }
}