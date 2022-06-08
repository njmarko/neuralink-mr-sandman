package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClassObjectFilter;
import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;
import sbnz.mrsandman.neuralinkapp.model.events.SignalEvent;
import sbnz.mrsandman.neuralinkapp.model.events.movement.MovementDetectedEvent;

@SpringBootTest
public class MovementDetectionCepTest extends BaseCepTest {

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/movement-detection.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		int ruleCount = 0;
		SessionPseudoClock clock = ksession.getSessionClock();
		
		for (int i = 0; i < 30; i++) { 
			ksession.insert(new SignalEvent(new Random().doubles(1,  10).findFirst().getAsDouble(), SignalType.SPEED));
			clock.advanceTime(1, TimeUnit.SECONDS);
		}
		ruleCount = ksession.fireAllRules();
		assertEquals(1, ruleCount);
		Collection<?> movementEvents = ksession.getObjects(new ClassObjectFilter(MovementDetectedEvent.class));
		assertEquals(1, movementEvents.size());
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub
		
	}

}
