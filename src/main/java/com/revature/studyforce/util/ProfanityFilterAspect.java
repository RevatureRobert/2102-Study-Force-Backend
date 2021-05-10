package com.revature.studyforce.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.Objects;



/**
 * ProfanityFilterAspect intercepts any POST or PUT action in the application and
 * runs the payload through the {@link ProfanityFilter}
 * @author Brandon Pinkerton
 */
@Component
@Aspect
public class ProfanityFilterAspect {
    ProfanityFilter filter;

    @Autowired
    public ProfanityFilterAspect(){
        filter = new ProfanityFilter();
    }

    @Pointcut("@annotation(org.springframework.web.bind.annotation.PostMapping)")
    public void postAction(){ /* pointcut to grab all post actions to the controllers */ }
    @Pointcut("@annotation(org.springframework.web.bind.annotation.PutMapping)")
    public void putAction(){ /* pointcut to grab all post actions to the controllers */ }


    /**
     * Runs the profanity filter on either the postAction or the putAction pointcuts.
     * @param proceedingJoinPoint The proceeding join point to intercept and take action on.
     * @throws Throwable if there are issues with the response or the join point.
     */
    @Around("postAction() || putAction()")
    public void runFilterOnPostOrPut(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String content = getPayload(proceedingJoinPoint);
        if(filter.isBad(content)){
            HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            assert response != null;
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Profanity was detected.");
            // TODO: log the information of the requester
            return;
        }
        proceedingJoinPoint.proceed();

    }

    /**
     *
     * @param proceedingJoinPoint The proceeding join point to intercept and take action on passed from the runFilterOnPostOrPut method.
     * @return The entire payload of the request as a String.
     */
    private String getPayload(ProceedingJoinPoint proceedingJoinPoint){
        CodeSignature signature = (CodeSignature) proceedingJoinPoint.getSignature();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < proceedingJoinPoint.getArgs().length; i++) {
            String parameterName = signature.getParameterNames()[i];
            builder.append(parameterName);
            builder.append(": ");
            builder.append(proceedingJoinPoint.getArgs()[i].toString());
            builder.append(", ");
        }
        return builder.toString();
    }

}
