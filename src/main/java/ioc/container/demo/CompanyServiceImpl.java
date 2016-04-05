package ioc.container.demo;

import ioc.container.core.annotation.Component;
import ioc.container.core.annotation.Inject;

@Component
public class CompanyServiceImpl implements CompanyService {

	@Inject
	private UserService userService;

	@Override
	public String getCurrentUserCompany() {
		return "company1" + " has user " + userService.getCurrentUser();
	}

	@Override
	public int getEmployeeCount() {
		return 10;
	}

}
