package ioc.container.core;

import java.util.Arrays;

import ioc.container.core.context.AbstractAnnotationContext;
import ioc.container.core.context.AbstractContextFactory;
import ioc.container.core.context.impl.SimpleContextFactory;
import ioc.container.demo.CompanyService;
import ioc.container.demo.UserService;

public class App {
	public static void main(String[] args) {
		AbstractContextFactory contextFactory = new SimpleContextFactory();
		String[] packages = { "ioc.container.demo" };
		AbstractAnnotationContext context = contextFactory.createAnnotaionContext(Arrays.asList(packages));

		UserService userService = context.getBean(UserService.class);
		System.out.println(userService.getCurrentUser());

		CompanyService companyService = context.getBean(CompanyService.class);
		System.out.println(companyService.getCurrentUserCompany());
	}
}
