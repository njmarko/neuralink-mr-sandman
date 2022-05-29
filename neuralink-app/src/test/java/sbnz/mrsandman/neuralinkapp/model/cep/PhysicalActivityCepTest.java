package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
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
import sbnz.mrsandman.neuralinkapp.model.events.heartrate.HeartBeatEvent;
import sbnz.mrsandman.neuralinkapp.model.events.physicalactivity.PhysicalActivityEvent;

@SpringBootTest
public class PhysicalActivityCepTest {

    @Test
    public void testCEPConfigThroughCode() throws FileNotFoundException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String fileName = "physical-activity";
    	
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        
        String filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + ".drl";
        
        File f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
        
        fileName = "sleep-cep";
        filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + ".drl";
        
        f = new File(filePath);
        kfs.write(ResourceFactory.newFileResource(f));
//        kfs.write(ResourceFactory.newClassPathResource("cep2/sleep-cep.drl"));
        KieBuilder kbuilder = ks.newKieBuilder(kfs);
        kbuilder.buildAll();
        if (kbuilder.getResults().hasMessages(Message.Level.ERROR)) {
            throw new IllegalArgumentException("Coudln't build knowledge module" + kbuilder.getResults());
        }
        KieContainer kContainer = ks.newKieContainer(kbuilder.getKieModule().getReleaseId());
        KieBaseConfiguration kbconf = ks.newKieBaseConfiguration();
        kbconf.setOption(EventProcessingOption.STREAM);
        KieBase kbase = kContainer.newKieBase(kbconf);
        
        KieSessionConfiguration ksconf1 = ks.newKieSessionConfiguration();
        ksconf1.setOption(ClockTypeOption.get(ClockType.REALTIME_CLOCK.getId()));
        KieSession ksession1 = kbase.newKieSession(ksconf1, null);
        
        KieSessionConfiguration ksconf2 = ks.newKieSessionConfiguration();
        ksconf2.setOption(ClockTypeOption.get(ClockType.PSEUDO_CLOCK.getId()));
        KieSession ksession2 = kbase.newKieSession(ksconf2, null);
        
//        runRealtimeClockExample(ksession1);
        runPseudoClockExample(ksession2);
    }
    
    private void runPseudoClockExample(KieSession ksession) {
    	int ruleCount = 0;
        SessionPseudoClock clock = ksession.getSessionClock();
        for (int index = 0; index < 5; index++) {
            BrainWaveEvent beep = new BrainWaveEvent();
            ksession.insert(beep);
            clock.advanceTime(1, TimeUnit.SECONDS);
            ruleCount = ksession.fireAllRules();
            //As long as there is a steady heart beat, no rule will fire
            assertThat(ruleCount, equalTo(0));
        }
//        clock.advanceTime(1, TimeUnit.MINUTES);
//        int ruleCount = ksession.fireAllRules();
//        assertThat(ruleCount, equalTo(1));
        for (int index = 0; index < 3; index++) {
        	HeartBeatEvent hearthBeet = new HeartBeatEvent();
        	ksession.insert(hearthBeet);
        	clock.advanceTime(1,  TimeUnit.SECONDS);
        	ksession.fireAllRules();
        }

        //We manually advance time 1 minute, without a heart beat
    	HeartBeatEvent hearthBeet = new HeartBeatEvent();
    	ksession.insert(hearthBeet);
        clock.advanceTime(1, TimeUnit.SECONDS);
        ruleCount = ksession.fireAllRules();
//        assertThat(ruleCount, equalTo(0));
        
        clock.advanceTime(1, TimeUnit.MINUTES);
        ruleCount = ksession.fireAllRules();
        assertThat(ruleCount, equalTo(2));
        
        Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(PhysicalActivityEvent.class));
        assertThat(newEvents.size(), equalTo(1));
    }
    
    private void runRealtimeClockExample(KieSession ksession) {
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
}