package com.innowise.dynamicspecification.bpp;

import com.innowise.dynamicspecification.specification.DynamicSpecificationContext;
import com.innowise.dynamicspecification.annotation.WithDynamicSpecification;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.SneakyThrows;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.data.jpa.domain.Specification;

//@Component
// TODO: possible???
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DynamicSpecificationBeanPostProcessor implements BeanPostProcessor {

    final static String SPECIFICATION_METHOD_POSTFIX = "WithSpecification";

//    @Autowired
    ApplicationContext applicationContext;

    final Map<String, Class<?>> beanClasses = new HashMap<>();

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        var isAnnotationPresented = Arrays.stream(bean.getClass().getMethods())
                .anyMatch(it -> it.isAnnotationPresent(WithDynamicSpecification.class));
        if (isAnnotationPresented) {
            beanClasses.put(beanName, bean.getClass());
        }

        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        var originalBeanClass = beanClasses.get(beanName);
        if (Objects.nonNull(originalBeanClass)) {
            return Proxy.newProxyInstance(
                    bean.getClass().getClassLoader(),
                    bean.getClass().getInterfaces(),
                    new DynamicSpecificationInvocationHandler(bean, applicationContext, originalBeanClass)
            );
        }
        return bean;
    }


    private static class DynamicSpecificationInvocationHandler implements InvocationHandler {
        private final Object target;
        private final ApplicationContext context;
        private final Class<?> originalBeanClass;

        DynamicSpecificationInvocationHandler(Object target, ApplicationContext context, Class<?> originalBeanClass) {
            this.target = target;
            this.context = context;
            this.originalBeanClass = originalBeanClass;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (method.isAnnotationPresent(WithDynamicSpecification.class)) {
                DynamicSpecificationContext<?> specHolder = context.getBean(
                    DynamicSpecificationContext.class);
                Specification<?> spec = specHolder.getSpecification();

                if (Objects.nonNull(spec)) {
                    Method withSpecMethod = findWithSpecificationMethod(originalBeanClass, method);
                    return withSpecMethod.invoke(target, mergeArguments(spec, args));
                }
            }
            return method.invoke(target, args);
        }

        @SneakyThrows
        private Method findWithSpecificationMethod(Class<?> clazz, Method originalMethod) {
            String withSpecMethodName = originalMethod.getName() + SPECIFICATION_METHOD_POSTFIX;
            Class<?>[] originalMethodParameterTypes = originalMethod.getParameterTypes();
            Class<?>[] specificationMethodParameterTypes = new Class<?>[originalMethodParameterTypes.length + 1];

            specificationMethodParameterTypes[0] = Specification.class;
            System.arraycopy(originalMethodParameterTypes, 0, specificationMethodParameterTypes, 1, originalMethodParameterTypes.length);

            return clazz.getMethod(withSpecMethodName, specificationMethodParameterTypes);
        }

        private Object[] mergeArguments(Specification<?> spec, Object[] originalArgs) {
            Object[] mergedArgs = new Object[originalArgs.length + 1];
            mergedArgs[0] = spec;
            System.arraycopy(originalArgs, 0, mergedArgs, 1, originalArgs.length);
            return mergedArgs;
        }
    }

    // TODO: If we have a BPP - how to determine what ethod to call instead of:
    //  i.e. - we retrieve Specification - if notNull - it was generated - hence use method with Specification instead  - call origin
}
