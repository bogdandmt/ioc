package ioc.container.core.context;

public interface AbstractAnnotationContext {

	<T> T getBean(Class<T> type);
}
