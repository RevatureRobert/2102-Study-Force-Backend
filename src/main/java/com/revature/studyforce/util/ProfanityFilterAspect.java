package com.revature.studyforce.util;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.CodeSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;


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



    @Around("postAction() || putAction()")
    public void runFilterOnPostOrPut(ProceedingJoinPoint joinPoint) throws Throwable {
        String content = getPayload(joinPoint);
        if(filter.isBad(content)){
            HttpServletResponse response = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getResponse();
            assert response != null;
            response.sendError(HttpStatus.BAD_REQUEST.value(), "Profanity was detected.");
            // TODO: log the information of the request
            return;
        }
        joinPoint.proceed();

    }

    private String getPayload(ProceedingJoinPoint joinPoint){
        CodeSignature signature = (CodeSignature) joinPoint.getSignature();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < joinPoint.getArgs().length; i++) {
            String parameterName = signature.getParameterNames()[i];
            builder.append(parameterName);
            builder.append(": ");
            builder.append(joinPoint.getArgs()[i].toString());
            builder.append(", ");
        }
        return builder.toString();
    }

}
