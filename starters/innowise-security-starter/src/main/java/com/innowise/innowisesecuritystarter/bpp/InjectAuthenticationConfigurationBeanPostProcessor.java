package com.innowise.innowisesecuritystarter.bpp;


import static lombok.AccessLevel.PRIVATE;

import com.innowise.innowisesecuritystarter.annotation.AuthenticationConfiguration;
import com.innowise.innowisesecuritystarter.annotation.Endpoint;
import com.innowise.innowisesecuritystarter.annotation.InjectAuthenticationConfiguration;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.framework.AopProxyUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

@Slf4j
@Component
@RequiredArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class InjectAuthenticationConfigurationBeanPostProcessor implements BeanPostProcessor {

  ApplicationContext applicationContext;

  @Override
  public Object postProcessBeforeInitialization(
      Object bean, String beanName) throws BeansException {
    // TODO: refactor - AopUtils.isCglibProxy(bean) - returns `false` - somewhy...
    Class<?> beanClass = AopProxyUtils.ultimateTargetClass(bean)
        .getSuperclass(); // hardcoded getSuperClass
    Field[] fields = beanClass.getDeclaredFields();
    for (Field field : fields) {
      if (field.isAnnotationPresent(InjectAuthenticationConfiguration.class)) {
        var notAuthenticatedEndpoints = getNotAuthenticatedEndpointsMatchers();
        field.setAccessible(true);
        ReflectionUtils.setField(field, bean, notAuthenticatedEndpoints);
      }
    }
    return bean;
  }

  private List<RequestMatcher> getNotAuthenticatedEndpointsMatchers() {
    var beans = applicationContext.getBeansWithAnnotation(AuthenticationConfiguration.class);
    var matchers = new ArrayList<RequestMatcher>();

    for (Object bean : beans.values()) {
      var beanClass = bean.getClass();
      if (!beanClass.isAnnotationPresent(AuthenticationConfiguration.class)) {
        beanClass = beanClass.getSuperclass();
      }
      if (beanClass.isAnnotationPresent(AuthenticationConfiguration.class)) {
        AuthenticationConfiguration annotation = beanClass.getAnnotation(
            AuthenticationConfiguration.class);
        for (Endpoint endpoint : annotation.notAuthenticatedEndpoints()) {
          RequestMatcher matcher;
          if (endpoint.method().name().isBlank()) {
            matcher = new AntPathRequestMatcher(endpoint.path());
          } else {
            matcher = new AntPathRequestMatcher(endpoint.path(), endpoint.method().getName());
          }
          log.info("Not secured route: " + endpoint.method() + ": " + endpoint.path());
          matchers.add(matcher);
        }
      }
    }

    return matchers;
  }
}
