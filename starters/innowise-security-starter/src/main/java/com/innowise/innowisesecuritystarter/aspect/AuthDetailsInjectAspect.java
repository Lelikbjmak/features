package com.innowise.innowisesecuritystarter.aspect;


import com.innowise.innowisesecuritystarter.annotation.AuthDetails;
import com.innowise.innowisesecuritystarter.utils.AuthenticationUtils;
import java.lang.reflect.Field;
import java.util.Objects;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;


@Slf4j
@Aspect
@Component
public class AuthDetailsInjectAspect {

  @SneakyThrows
  @Around("execution(* *(.., @com.innowise.innowisesecuritystarter.annotation.InjectAuthDetails (*), ..))")
  public Object injectAuthDetail(ProceedingJoinPoint joinPoint) {
    var args = joinPoint.getArgs();

    for (Object arg : args) {
      if (Objects.nonNull(arg)) {
        var targetClass = arg.getClass();
        for (Field field : targetClass.getDeclaredFields()) {
          if (field.isAnnotationPresent(AuthDetails.class)) {
            var authDetailsAnnotation = field.getAnnotation(AuthDetails.class);
            var authDetailValue = AuthenticationUtils.claim(authDetailsAnnotation.value());
            if (Objects.nonNull(authDetailValue) && field.getType()
                .isAssignableFrom(authDetailValue.getClass())) {
              field.setAccessible(true);
              field.set(arg, authDetailValue);
            }
          }
        }
      }
    }

    return joinPoint.proceed(args);
  }

  @SneakyThrows
  @Around("execution(* *(.., @com.innowise.innowisesecuritystarter.annotation.AuthDetails (*), ..))")
  public Object handleParameterCustomValue(ProceedingJoinPoint joinPoint) {
    var args = joinPoint.getArgs();
    var methodSignature = (MethodSignature) joinPoint.getSignature();
    var parameters = methodSignature.getMethod().getParameters();

    for (int i = 0; i < parameters.length; i++) {
      var annotation = parameters[i].getAnnotation(AuthDetails.class);
      if (Objects.nonNull(annotation)) {
        args[i] = AuthenticationUtils.claim(annotation.value());
      }
    }

    return joinPoint.proceed(args);
  }
}
