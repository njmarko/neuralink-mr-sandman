package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;
import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.SleepStage;
import sbnz.mrsandman.neuralinkapp.model.enums.MuscleTone;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.MuscleToneChangedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.movement.MovementDetectedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.somnabulism.SomnabulismDetectedEvent;

@SpringBootTest
public class SomnabulismDetecetionCepTest extends BaseCepTest {

	@Autowired
	public SomnabulismDetecetionCepTest(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/somnabulism-detection.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		int ruleCount = 0;
        SessionPseudoClock clock = ksession.getSessionClock();
        ksession.insert(new MovementDetectedEvent());
        Sleep sleep = new Sleep();
        ksession.insert(sleep);
        ksession.insert(new SleepStage(sleep, SleepPhase.REM));
        MuscleToneChangedEvent muscleToneChanged = new MuscleToneChangedEvent(MuscleTone.TENSE);
        ksession.insert(muscleToneChanged);
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(1));
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(SomnabulismDetectedEvent.class));
        assertThat(newEvents.size(), equalTo(1));
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub
		
	}

}
