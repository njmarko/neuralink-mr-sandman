package sbnz.mrsandman.neuralinkapp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.model.enums.Chronotype;
import sbnz.mrsandman.neuralinkapp.model.enums.Gender;


@RestController
@RequestMapping("/api")
public class NeuralinkAppController {
	private static Logger log = LoggerFactory.getLogger(NeuralinkAppController.class);
	private final NeuralinkAppService appService;

	@Autowired
	public NeuralinkAppController(NeuralinkAppService appService) {
		this.appService = appService;
	}

	@RequestMapping(value = "/is-static", method = RequestMethod.GET, produces = "application/json")
	public User getQuestions(@RequestParam(required = true) float speed) {

		User user = new User();
		user.setSpeed(speed);

		log.debug("Item request received for: " + user.toString());

		User updated = appService.detectIsStatic(user);

		return updated;
	}
	
	@RequestMapping(value = "/determine-optimal-sleep-time", method = RequestMethod.GET, produces = "application/json")
	public User determineOptimalSleepTime(@RequestParam(required = true) int age,
			@RequestParam(required = true) Gender gender, @RequestParam(required = true) Chronotype chronotype) {

		User user = new User();
		user.setAge(age);
		user.setGender(gender);
		user.setChronotype(chronotype);


		log.debug("Item request received for: " + user.toString());

		User updated = appService.detectIsStatic(user);

		return updated;
	}
	
	
	
}
