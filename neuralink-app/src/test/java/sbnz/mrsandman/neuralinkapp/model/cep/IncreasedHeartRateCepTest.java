package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;

import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;
import sbnz.mrsandman.neuralinkapp.model.events.SignalEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.RaisedAlcoholLevelEvent;
import sbnz.mrsandman.neuralinkapp.model.events.heartrate.HeartRateIncreasedEvent;

public class IncreasedHeartRateCepTest extends BaseCepTest{
	
	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		String fileName = "signal-clasification";
		String filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/" + fileName + ".drl";
		File f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));

	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		SessionPseudoClock clock = ksession.getSessionClock();
		for (int index = 0; index < 100; index++) {
			SignalEvent level = new SignalEvent(SignalType.HEART_BEAT);
			ksession.insert(level);
			clock.advanceTime(400, TimeUnit.MILLISECONDS);
			int ruleCount = ksession.fireAllRules();
//			assertThat(ruleCount, equalTo(0));
		}
		SignalEvent level = new SignalEvent(SignalType.HEART_BEAT);
		ksession.insert(level);
		clock.advanceTime(400, TimeUnit.MILLISECONDS);
		int ruleCount = ksession.fireAllRules();
		assertThat(ruleCount, equalTo(1));
		
		// Should not trigger the rule again because HeartRateIncreasedEvent was generated in recently
		level = new SignalEvent(SignalType.HEART_BEAT);
		ksession.insert(level);
		clock.advanceTime(400, TimeUnit.MILLISECONDS);
		ruleCount = ksession.fireAllRules();
		assertThat(ruleCount, equalTo(0));
		
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(HeartRateIncreasedEvent.class));
		assertThat(newEvents.size(), equalTo(1));

	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub

	}

}
