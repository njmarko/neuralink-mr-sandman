package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.ClockType;
import org.drools.core.time.SessionPseudoClock;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.model.events.BrainWaveEvent;
import sbnz.mrsandman.neuralinkapp.model.events.SleepPhaseEvent;

@SpringBootTest
public class SleepStageCepTest extends BaseCepTest {
	
    protected void runPseudoClockExample(KieSession ksession) {
        SessionPseudoClock clock = ksession.getSessionClock();
        for (int index = 0; index < 100; index++) {
            BrainWaveEvent beep = new BrainWaveEvent();
            ksession.insert(beep);
            clock.advanceTime(1, TimeUnit.SECONDS);
            int ruleCount = ksession.fireAllRules();
            //As long as there is a steady heart beat, no rule will fire
            assertThat(ruleCount, equalTo(0));
        }
        //We manually advance time 1 minute, without a heart beat
        clock.advanceTime(1, TimeUnit.MINUTES);
        int ruleCount = ksession.fireAllRules();
        assertThat(ruleCount, equalTo(1));
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(SleepPhaseEvent.class));
        assertThat(newEvents.size(), equalTo(1));
    }
    
    protected void runRealtimeClockExample(KieSession ksession) {
        Thread t = new Thread() {
            @Override
            public void run() {
                for (int index = 0; index < 4; index++) {
                	BrainWaveEvent beep = new BrainWaveEvent();
                    ksession.insert(beep);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //do nothing
                    }
                }
            }
        };
        t.setDaemon(true);
        t.start();
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            //do nothing
        }
        ksession.fireUntilHalt();
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(SleepPhaseEvent.class));
        assertThat(newEvents.size(), equalTo(1));
    }

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
        String fileName = "sleep-cep";
        String filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + ".drl";
        File f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
	}
    
}
