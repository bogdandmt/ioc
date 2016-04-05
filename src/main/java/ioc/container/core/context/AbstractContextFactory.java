package ioc.container.core.context;

import java.util.Collection;

public interface AbstractContextFactory {

	AbstractAnnotationContext createAnnotaionContext(Collection<String> packages);

	AbstractXmlContext createXmlContext(String configLocation);
}
