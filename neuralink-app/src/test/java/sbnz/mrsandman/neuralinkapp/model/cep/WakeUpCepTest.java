package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.SleepStage;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.SleepPhaseEvent;
import sbnz.mrsandman.neuralinkapp.model.events.WakeUpEvent;

@SpringBootTest
public class WakeUpCepTest extends BaseCepTest {

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/detect-wakeup.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		int ruleCount = 0;
        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.insert(new SleepPhaseEvent(SleepPhase.AWAKE));
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(0));
    	Sleep sleep = new Sleep();
    	SleepStage awakeStage = new SleepStage(sleep, SleepPhase.AWAKE);
        ksession.insert(sleep);
        ksession.insert(awakeStage);
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(1));
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(WakeUpEvent.class));
        assertThat(newEvents.size(), equalTo(1));
        assertThat(sleep.getEndTime(), notNullValue());
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub
		
	}

}
