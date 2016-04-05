package ioc.container.demo;

import ioc.container.core.annotation.Component;

@Component
public interface UserService {

	String getCurrentUser();

}
