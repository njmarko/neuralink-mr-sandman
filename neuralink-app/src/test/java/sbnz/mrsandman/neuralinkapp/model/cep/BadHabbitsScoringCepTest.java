package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.SleepPhaseEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.AlcoholBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.RaisedAlcoholLevelEvent;
import sbnz.mrsandman.neuralinkapp.model.events.heartrate.HeartRateIncreasedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.physicalactivity.PhysicalActivityEvent;
import sbnz.mrsandman.neuralinkapp.model.events.temperature.RaisedTemperatureEvent;

public class BadHabbitsScoringCepTest extends BaseCepTest{

    protected void runPseudoClockExample(KieSession ksession) {
		User user1 = new User();
		user1.setAge(16);
		user1.setIsLightSleep(false);
		user1.setGoingToBedTime(LocalTime.of(21, 0));

		ksession.insert(user1);
		ksession.fireAllRules();
    	
    	int ruleCount = 0;
        SessionPseudoClock clock = ksession.getSessionClock();
        for (int index = 0; index < 5; index++) {
        	HeartRateIncreasedEvent raisedHr = new HeartRateIncreasedEvent();
            ksession.insert(raisedHr);
            clock.advanceTime(1, TimeUnit.SECONDS);
            ruleCount = ksession.fireAllRules();
        	RaisedTemperatureEvent raisedTemp = new RaisedTemperatureEvent();
            ksession.insert(raisedTemp);
            clock.advanceTime(1, TimeUnit.SECONDS);
            ruleCount = ksession.fireAllRules();
        	RaisedAlcoholLevelEvent alch = new RaisedAlcoholLevelEvent();
            ksession.insert(alch);
            clock.advanceTime(1, TimeUnit.SECONDS);
            ruleCount = ksession.fireAllRules();
        }
    	
        SleepPhaseEvent phase = new SleepPhaseEvent(SleepPhase.AWAKE);
        ksession.insert(phase);
        clock.advanceTime(1, TimeUnit.SECONDS);
        ruleCount = ksession.fireAllRules();

        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(AlcoholBeforeSleepEvent.class));
        assertThat(newEvents.size(), equalTo(1));
        
        phase = new SleepPhaseEvent(SleepPhase.PHASE1);
        ksession.insert(phase);
        clock.advanceTime(1, TimeUnit.SECONDS);
        ruleCount = ksession.fireAllRules();
    }
    
    protected void runRealtimeClockExample(KieSession ksession) {

    }

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
        String fileName = "chronotype-clasification";
        String filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/" + fileName + ".drl";
        File f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
        
        fileName = "optimal-sleep-time-template";
        filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/" + fileName + ".drl";
        f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
        
        fileName = "bad-habbits-cep";
        filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/" + fileName + ".drl";
        f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
        
        fileName = "bad-habbit-score-cep";
        filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/" + fileName + ".drl";
        f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
	}
}
