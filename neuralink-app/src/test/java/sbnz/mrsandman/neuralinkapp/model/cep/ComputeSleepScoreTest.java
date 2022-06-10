package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.hamcrest.Matchers;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;
import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.SleepStage;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.WakeUpEvent;
import sbnz.mrsandman.neuralinkapp.model.events.somnabulism.SomnabulismDetectedEvent;

@SpringBootTest
public class ComputeSleepScoreTest extends BaseCepTest {

	@Autowired
	public ComputeSleepScoreTest(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/compute-sleep-score.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		int ruleCount = 0;
		SessionPseudoClock clock = ksession.getSessionClock();
		
		// without somnabulism detection
        Sleep sleep = new Sleep();
        ksession.insert(sleep);
        ksession.insert(new SleepStage(sleep, SleepPhase.REM, 2));
        ksession.insert(new SleepStage(sleep, SleepPhase.AWAKE, 3));
        ksession.insert(new SleepStage(sleep, SleepPhase.REM, 20));
        ksession.insert(new SleepStage(sleep, SleepPhase.PHASE2, 50));
        ksession.insert(new WakeUpEvent());
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(2));
    	assertThat(sleep.getEfficient(), equalTo(true));
    	assertThat(sleep.getQuality(), Matchers.closeTo(2.933333, 1e-5));
    	
    	// with somnabulism detection
    	ruleCount = 0;
    	Sleep sleep2 = new Sleep();
        ksession.insert(sleep2);
        ksession.insert(new SleepStage(sleep2, SleepPhase.REM, 2));
        ksession.insert(new SleepStage(sleep2, SleepPhase.AWAKE, 3));
        ksession.insert(new SleepStage(sleep2, SleepPhase.REM, 2));
        ksession.insert(new SleepStage(sleep2, SleepPhase.PHASE2, 1));
        ksession.insert(new SomnabulismDetectedEvent());
        ksession.insert(new WakeUpEvent());
    	clock.advanceTime(1,  TimeUnit.SECONDS);
    	ruleCount = ksession.fireAllRules();
    	assertThat(ruleCount, equalTo(2));
    	assertThat(sleep2.getEfficient(), equalTo(false));
    	assertThat(sleep2.getQuality(), Matchers.closeTo(5.0, 1e-5));
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub
		
	}

}
