package sbnz.mrsandman.neuralinkapp.service;

import sbnz.mrsandman.neuralinkapp.dto.RegisterUserRequest;
import sbnz.mrsandman.neuralinkapp.model.User;

public interface UserService {
	User register(RegisterUserRequest request);
}
