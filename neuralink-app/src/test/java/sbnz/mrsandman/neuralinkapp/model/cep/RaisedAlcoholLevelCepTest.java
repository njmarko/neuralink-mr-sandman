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

import sbnz.mrsandman.neuralinkapp.model.events.alcohol.AlcoholLevelEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.RaisedAlcoholLevelEvent;

public class RaisedAlcoholLevelCepTest extends BaseCepTest {

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		String fileName = "alcohol-cep";
		String filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/" + fileName + ".drl";
		File f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));

	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		SessionPseudoClock clock = ksession.getSessionClock();
		for (int index = 0; index < 3; index++) {
			AlcoholLevelEvent level = new AlcoholLevelEvent(0.2);
			ksession.insert(level);
			clock.advanceTime(1, TimeUnit.SECONDS);
			int ruleCount = ksession.fireAllRules();
			assertThat(ruleCount, equalTo(0));
		}
		AlcoholLevelEvent level = new AlcoholLevelEvent(0.2);
		ksession.insert(level);
		clock.advanceTime(1, TimeUnit.SECONDS);
		int ruleCount = ksession.fireAllRules();
		assertThat(ruleCount, equalTo(1));
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(RaisedAlcoholLevelEvent.class));
		assertThat(newEvents.size(), equalTo(1));

	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub

	}

}
