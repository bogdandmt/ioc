package ioc.container.demo;

import ioc.container.core.annotation.Component;

@Component
public interface CompanyService {

	String getCurrentUserCompany();

	int getEmployeeCount();
}
