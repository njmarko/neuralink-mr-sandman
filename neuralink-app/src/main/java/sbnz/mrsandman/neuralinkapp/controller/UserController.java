package sbnz.mrsandman.neuralinkapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import sbnz.mrsandman.neuralinkapp.dto.RegisterUserRequest;
import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.service.UserService;

@CrossOrigin("*")
@RequestMapping("api/users")
@RestController
public class UserController {
	private final UserService userService;
	
	@Autowired
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping
	public User register(@RequestBody RegisterUserRequest request) {
		return this.userService.register(request);
	}
	
}
