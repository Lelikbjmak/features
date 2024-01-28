package com.innowise.queryparametrization.specification_context_revision.aspect;

import com.innowise.queryparametrization.specification_context_revision.SpecificationContext;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Slf4j
@Aspect
@Component
public class DynamicSpecificationInjectionAspect {

    @Autowired
    private SpecificationContext<?> specificationContext;

    @Pointcut("@annotation(com.innowise.queryparametrization.specification_context_revision.annotation.WithDynamicSpecification)")
    public void methodsWithDynamicSpecification() {
    }

    @Around("methodsWithDynamicSpecification()")
    public Object aroundServiceMethod(ProceedingJoinPoint joinPoint) throws Throwable {
        Specification<?> spec = specificationContext.getSpecification();
        if (spec != null) {
            Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
            Method withSpecMethod = findWithSpecificationMethod(joinPoint.getTarget().getClass(), method);
            Object[] mergedArgs = mergeArguments(spec, joinPoint.getArgs());
            return withSpecMethod.invoke(joinPoint.getTarget(), mergedArgs);
        }

        return joinPoint.proceed();
    }

    private Method findWithSpecificationMethod(Class<?> clazz, Method originalMethod) {
        Class<?>[] originalMethodParameterTypes = originalMethod.getParameterTypes();
        Class<?>[] specificationMethodParameterTypes = new Class<?>[originalMethodParameterTypes.length + 1];

        specificationMethodParameterTypes[0] = Specification.class;
        System.arraycopy(originalMethodParameterTypes, 0, specificationMethodParameterTypes, 1, originalMethodParameterTypes.length);
        try {
            return clazz.getMethod(originalMethod.getName(), specificationMethodParameterTypes);
        } catch (NoSuchMethodException e) {
            log.error("Method that can process Dynamic Specification is not found. Method expected: " + originalMethod.getName() + " with args: (Specification<?> spec, ...args), where args - all args from original method." +
                    "Applying original method without dynamic specification...");
            return originalMethod;
        }
    }

    private Object[] mergeArguments(Specification<?> spec, Object[] originalArgs) {
        Object[] mergedArgs = new Object[originalArgs.length + 1];
        mergedArgs[0] = spec;
        System.arraycopy(originalArgs, 0, mergedArgs, 1, originalArgs.length);
        return mergedArgs;
    }
}
