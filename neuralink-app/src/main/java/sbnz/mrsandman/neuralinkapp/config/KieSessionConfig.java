package sbnz.mrsandman.neuralinkapp.config;

import org.drools.core.ClockType;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;

@Configuration
public class KieSessionConfig {

	@Bean
	public KieSession kieSession(EventBus eventBus) {
		KieServices ks = KieServices.Factory.get();
		KieContainer kContainer = ks
				.newKieContainer(ks.newReleaseId("sbnz.mrsandman", "neuralink-kjar", "0.0.1-SNAPSHOT"));
		KieBaseConfiguration kieBaseConfiguration = ks.newKieBaseConfiguration();
		kieBaseConfiguration.setOption(EventProcessingOption.STREAM);
		KieBase kieBase = kContainer.newKieBase(kieBaseConfiguration);

		KieSessionConfiguration kieSessionConfiguration = ks.newKieSessionConfiguration();
		kieSessionConfiguration.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
		KieSession kieSession = kieBase.newKieSession(kieSessionConfiguration, null);
		
		kieSession.setGlobal("eventBus", eventBus);
		
		return kieSession;
	}
}
