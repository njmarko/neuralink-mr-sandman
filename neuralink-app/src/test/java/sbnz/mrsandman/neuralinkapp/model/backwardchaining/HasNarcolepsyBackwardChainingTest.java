package sbnz.mrsandman.neuralinkapp.model.backwardchaining;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;
import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.cep.BaseCepTest;


@SpringBootTest
public class HasNarcolepsyBackwardChainingTest extends BaseCepTest {

	@Autowired
	public HasNarcolepsyBackwardChainingTest(EventBus eventBus) {
		super(eventBus);
		//TODO Auto-generated constructor stub
	}

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/backward-chaining/has-narcolepsy.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		int ruleCount = 0;
        SessionPseudoClock clock = ksession.getSessionClock();
        
        Sleep s1 = new Sleep(); s1.setDay(LocalDate.now().minusDays(2)); s1.setHadSleepParalysis(true);
        Sleep s2 = new Sleep(); s2.setDay(LocalDate.now().minusDays(4)); s2.setHadSleepParalysis(true);
        Sleep s3 = new Sleep(); s3.setDay(LocalDate.now().minusDays(5)); s3.setHadSleepParalysis(true);
         
        ksession.insert(s1);
        ksession.insert(s2); 
        ksession.insert(s3);
        
        ksession.insert("Has narcolepsy");
        ruleCount = ksession.fireAllRules();
        
		assertEquals(1, ruleCount);
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub
		
	}
}
