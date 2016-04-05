package ioc.container.core.context.impl;

import java.util.Collection;

import ioc.container.core.context.AbstractAnnotationContext;
import ioc.container.core.context.AbstractContextFactory;
import ioc.container.core.context.AbstractXmlContext;

public class SimpleContextFactory implements AbstractContextFactory {

	@Override
	public AbstractAnnotationContext createAnnotaionContext(Collection<String> packages) {
		return new AnnotationContext(packages);
	}

	@Override
	public AbstractXmlContext createXmlContext(String configLocation) {
		// TODO Auto-generated method stub
		return null;
	}

}
