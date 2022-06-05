package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.model.events.beginsleeping.BeginSleepingEvent;
import sbnz.mrsandman.neuralinkapp.model.events.beginsleeping.HeartRateLoweredEvent;
import sbnz.mrsandman.neuralinkapp.model.events.temperature.LoweredTemperatureEvent;

@SpringBootTest
public class BeginSleepingCepTest extends BaseCepTest {

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/begin-sleeping.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
    	int ruleCount = 0;
        SessionPseudoClock clock = ksession.getSessionClock();
        User user = new User();
        user.setIsStatic(true);
        ksession.insert(user);
        HeartRateLoweredEvent loweredHeartRate = new HeartRateLoweredEvent();
        ksession.insert(loweredHeartRate);
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(0));
        LoweredTemperatureEvent changedTemp = new LoweredTemperatureEvent();
        ksession.insert(changedTemp);
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(1));
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(BeginSleepingEvent.class));
        assertThat(newEvents.size(), equalTo(1));
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub
		
	}

}
