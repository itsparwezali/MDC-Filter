package com.example.aop;


import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Aspect
public class Transaction {

//    @Pointcut("@annotation(com.example.annotation.CustomAnnoation)")


    @Pointcut(value = "execution(* com.example.*.*.*(..) )")
    public void p1(){}


    @Around("p1()")
    public Object anoundTest(ProceedingJoinPoint joinPoint){

        Object proceed = null;
        long l1 = System.currentTimeMillis();

        log.info("work before transaction :: {}",l1);

        try {
             proceed = joinPoint.proceed();
            log.info("method work successfully :: ");
            return proceed;
        }catch (Throwable e){

            log.info("exection time any exception occur :: ");

            return proceed;
        }finally {

            long l2 = System.currentTimeMillis();
            log.info("method execution time taken :: {} ", l2-l1);
            log.info("class:: {} ,method:: {}: parameters : {} : response : {} executed in {} ms,",MethodSignature.class.cast(joinPoint.getSignature()).getMethod().getDeclaringClass().getSimpleName(),MethodSignature.class.cast(joinPoint.getSignature()).getMethod()
                    .getName(), joinPoint.getArgs(), proceed, l2-l1);

        }
    }



//    @Around("p1()")
//    public Object applicationLogger(ProceedingJoinPoint pjp) throws Throwable{
//
//        ObjectMapper mapper = new ObjectMapper();
//        String methodName = pjp.getSignature().getName();
//        String className = pjp.getTarget().getClass().getName();
//        Object[] array = pjp.getArgs();
//        log.info("class :: {} , method :: {} arg :: {} ",className,methodName,mapper.writeValueAsString(array));
//
//        Object object = pjp.proceed();
////        log.info("class :: {} , method :: {} response :: {} ",className,methodName,mapper.writeValueAsString(object));
//
//        return object;
//
//    }

}
