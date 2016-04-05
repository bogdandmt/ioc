package ioc.container.demo;

import ioc.container.core.annotation.Component;
import ioc.container.core.annotation.Inject;

@Component
public class UserServiceImpl implements UserService {

	@Inject
	private CompanyService companyService;

	@Override
	public String getCurrentUser() {
		return "current_user" + " works with " + companyService.getEmployeeCount() + " employees";
	}

}
