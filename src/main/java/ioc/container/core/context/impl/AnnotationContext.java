package ioc.container.core.context.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.reflections.Reflections;

import ioc.container.core.annotation.Component;
import ioc.container.core.annotation.Inject;
import ioc.container.core.context.AbstractAnnotationContext;
import javassist.util.proxy.MethodHandler;
import javassist.util.proxy.ProxyFactory;
import javassist.util.proxy.ProxyObject;

public class AnnotationContext implements AbstractAnnotationContext {

	private Set<Class<?>> componentClasses;
	private Map<Class<?>, Object> proxies;
	private Map<Class<?>, Object> instances;

	public AnnotationContext(Collection<String> packages) {
		Reflections reflections = new Reflections(packages);
		componentClasses = reflections.getTypesAnnotatedWith(Component.class);

		try {
			createProxies();
			setDependencies();
			redirectProxies();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private void createProxies() throws InstantiationException, IllegalAccessException {
		proxies = new HashMap<>();

		for (Class<?> cls : componentClasses) {
			if (cls.isInterface()) {
				ProxyFactory factory = new ProxyFactory();
				Class<?>[] interfaces = { cls };
				factory.setInterfaces(interfaces);

				Class<?> proxyClass = factory.createClass();
				Object proxyInstance = proxyClass.newInstance();
				proxies.put(cls, proxyInstance);
			}
		}
	}

	private void setDependencies() throws IllegalArgumentException, IllegalAccessException, InstantiationException {
		instances = new HashMap<>();

		for (Class<?> cls : componentClasses) {
			if (!cls.isInterface()) {
				Object newInstance = cls.newInstance();
				for (Class<?> ifs : componentClasses) {
					if (ifs.isAssignableFrom(cls) && ifs.isInterface()) {
						instances.put(ifs, newInstance);
					}
				}

				for (Field field : cls.getDeclaredFields()) {
					if (field.isAnnotationPresent(Inject.class)) {
						boolean accessible = field.isAccessible();
						field.setAccessible(true);
						field.set(newInstance, proxies.get(field.getType()));
						field.setAccessible(accessible);
					}
				}
			}
		}
	}

	private void redirectProxies() {
		for (Entry<Class<?>, Object> entry : proxies.entrySet()) {
			ProxyObject proxyObject = (ProxyObject) entry.getValue();
			proxyObject.setHandler(new MethodHandler() {

				@Override
				public Object invoke(Object self, Method thisMethod, Method proceed, Object[] args) throws Throwable {

					Object object = instances.get(entry.getKey());
					Method method = object.getClass().getMethod(thisMethod.getName(), thisMethod.getParameterTypes());
					return method.invoke(object, args);
				}
			});
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getBean(Class<T> type) {
		return (T) proxies.get(type);
	}

}
