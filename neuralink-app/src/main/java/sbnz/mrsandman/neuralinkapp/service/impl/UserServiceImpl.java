package sbnz.mrsandman.neuralinkapp.service.impl;

import org.springframework.stereotype.Service;

import sbnz.mrsandman.neuralinkapp.dto.RegisterUserRequest;
import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.service.UserService;

@Service
public class UserServiceImpl implements UserService {

	@Override
	public User register(RegisterUserRequest request) {
		// TODO: Insert the user into session and fire rules for computing chronotype and optimal sleep type
		User user = new User();
		user.setAge(request.getAge());
		user.setIsLightSleep(request.getIsLightSleep());
		user.setGoingToBedTime(request.getGoingToBedTime());
		user.setGender(request.getGender());
		return user;
	}

}
