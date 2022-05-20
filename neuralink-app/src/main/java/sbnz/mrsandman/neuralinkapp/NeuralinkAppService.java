package sbnz.mrsandman.neuralinkapp;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sbnz.mrsandman.neuralinkapp.model.User;



@Service
public class NeuralinkAppService {
	private static Logger log = LoggerFactory.getLogger(NeuralinkAppService.class);
	private final KieContainer kieContainer;

	@Autowired
	public NeuralinkAppService(KieContainer kieContainer) {
		log.info("Initialising a new example session.");
		this.kieContainer = kieContainer;
	}

	public User detectIsStatic(User u) {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(u);
		kieSession.fireAllRules();
		kieSession.dispose();
		return u;
	}
	
	public User determineOptimalSleepTime(User u) {
		KieSession kieSession = kieContainer.newKieSession();
		kieSession.insert(u);
		kieSession.fireAllRules();
		kieSession.dispose();
		return u;
	}
}
