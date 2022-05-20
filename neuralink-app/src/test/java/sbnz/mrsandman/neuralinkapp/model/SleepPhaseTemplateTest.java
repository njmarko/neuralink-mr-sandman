package sbnz.mrsandman.neuralinkapp.model;

import java.io.InputStream;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;


import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.ObjectDataCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.Test;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;



public class SleepPhaseTemplateTest {

    @Test
    public void testSimpleTemplateWithArrays(){
        System.out.println("Hello");
        InputStream template = SleepPhaseTemplate.class.getResourceAsStream("../src/main/resources/sbnz/mrsandman/templates/sleep-stage-clasification.drt");
        
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
            new String[]{"0", "15",  "AWAKE"},
            new String[]{"15", "25",  "PHASE1"},
            new String[]{"25", "37.5",  "PHASE2"},
            new String[]{"37.5", "50",  "PHASE3"},
            new String[]{"50", "65",  "PHASE4"},
            new String[]{"65", "90",  "REM"},
        });
        
        DataProviderCompiler converter = new DataProviderCompiler();
        String drl = converter.compile(dataProvider, template);
        
        System.out.println(drl);
        
        KieSession ksession = createKieSessionFromDRL(drl);
        
        doTest(ksession);
    }
    
	private void doTest(KieSession ksession){
        Sleep sleep1 = new Sleep(11, SleepPhase.REM);
        Sleep sleep2 = new Sleep(22, SleepPhase.AWAKE);
        Sleep sleep3 = new Sleep(33, SleepPhase.AWAKE);
        Sleep sleep4 = new Sleep(44, SleepPhase.AWAKE);
        Sleep sleep5 = new Sleep(55, SleepPhase.AWAKE);
        Sleep sleep6 = new Sleep(77, SleepPhase.AWAKE);
        
        
        ksession.insert(sleep1);
        ksession.insert(sleep2);
        ksession.insert(sleep3);
        ksession.insert(sleep4);
        ksession.insert(sleep5);
        ksession.insert(sleep6);
        
        ksession.fireAllRules();
        
        assertThat(sleep1.getSleepPhase(), is(SleepPhase.AWAKE));
        assertThat(sleep2.getSleepPhase(), is(SleepPhase.PHASE1));
        assertThat(sleep3.getSleepPhase(), is(SleepPhase.PHASE2));
        assertThat(sleep4.getSleepPhase(), is(SleepPhase.PHASE3));
        assertThat(sleep5.getSleepPhase(), is(SleepPhase.PHASE4));
        assertThat(sleep6.getSleepPhase(), is(SleepPhase.REM));
    }
    
	private KieSession createKieSessionFromDRL(String drl){
        KieHelper kieHelper = new KieHelper();
        kieHelper.addContent(drl, ResourceType.DRL);
        
        Results results = kieHelper.verify();
        
        if (results.hasMessages(Message.Level.WARNING, Message.Level.ERROR)){
            List<Message> messages = results.getMessages(Message.Level.WARNING, Message.Level.ERROR);
            for (Message message : messages) {
                System.out.println("Error: "+message.getText());
            }
            
            throw new IllegalStateException("Compilation errors were found. Check the logs.");
        }
        
        return kieHelper.build().newKieSession();
    }
	
}
