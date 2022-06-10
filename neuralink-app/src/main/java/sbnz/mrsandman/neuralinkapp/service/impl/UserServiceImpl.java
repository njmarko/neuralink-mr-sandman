package sbnz.mrsandman.neuralinkapp.service.impl;

import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.mrsandman.neuralinkapp.dto.RegisterUserRequest;
import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {
	private final KieSession kieSession;
	
	@Autowired
	public UserServiceImpl(KieSession kieSession) {
		this.kieSession = kieSession;
	}



	@Override
	public User register(RegisterUserRequest request) {
		// TODO: Insert the user into session and fire rules for computing chronotype and optimal sleep type
		User user = new User();
		user.setAge(request.getAge());
		user.setIsLightSleep(request.getIsLightSleep());
		user.setGoingToBedTime(request.getGoingToBedTime());
		user.setGender(request.getGender());
		kieSession.insert(user);
		kieSession.fireAllRules();
		return user;
	}

}
