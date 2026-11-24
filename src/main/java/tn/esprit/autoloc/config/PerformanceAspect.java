package tn.esprit.autoloc.config;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class PerformanceAspect {

    @Around("execution(* tn.esprit.autoloc.service.*.*(..))")
    public Object mesurerTemps(ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();

        long debut = System.currentTimeMillis();
        Object resultat = joinPoint.proceed();   // exécution de la méthode
        long duree = System.currentTimeMillis() - debut;

        log.info("Performance - Méthode {} exécutée en {} ms", methodName, duree);

        if (duree > 1000) {
            log.warn("ATTENTION - La méthode {} est lente : {} ms (> 1000 ms)",
                    methodName, duree);
        }

        return resultat;
    }
}