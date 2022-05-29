package sbnz.mrsandman.neuralinkapp.model.cep;

import java.io.File;
import java.io.FileNotFoundException;

import org.drools.core.ClockType;
import org.junit.jupiter.api.Test;
import org.kie.api.KieBase;
import org.kie.api.KieBaseConfiguration;
import org.kie.api.KieServices;
import org.kie.api.builder.KieBuilder;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.builder.Message;
import org.kie.api.conf.EventProcessingOption;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.api.runtime.KieSessionConfiguration;
import org.kie.api.runtime.conf.ClockTypeOption;
import org.kie.internal.io.ResourceFactory;

public abstract class BaseCepTest {

    @Test
    public void testCEPConfigThroughCode() throws FileNotFoundException {
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
    	
        KieServices ks = KieServices.Factory.get();
        KieFileSystem kfs = ks.newKieFileSystem();
        
        writeResourcesToSession(kfs);
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
        
        runRealtimeClockExample(ksession1);
        runPseudoClockExample(ksession2);
    }
    
    protected abstract void writeResourcesToSession(KieFileSystem kfs);
    
    protected abstract void runPseudoClockExample(KieSession ksession);
    
    protected abstract void runRealtimeClockExample(KieSession ksession);
    
    protected void writeFile(KieFileSystem kfs, String filePath) {
        kfs.write(ResourceFactory.newFileResource(new File(filePath)));
    }
	
}
