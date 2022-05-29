package sbnz.mrsandman.neuralinkapp.model.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.kie.api.KieServices;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.BrainWaveFrequencyChangedEvent;

@SpringBootTest
public class SleepPhaseTemplateTest extends BaseTemplateTest {

    @Test
    public void testSimpleTemplateWithArrays() throws FileNotFoundException{
        System.out.println("Hello");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
        String fileName = "sleep-stage-clasification";

//        InputStream template = SleepPhaseTemplate.class.getResourceAsStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/sleep-stage-clasification.drt");
//        InputStream template = SleepPhaseTemplateTest.class.getResourceAsStream("/templates/sleep-stage-clasification.drt");
        InputStream template = new FileInputStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/" + fileName + ".drt");
        
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
        
        
        
        try {
        	new File("../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/").mkdirs();
            FileWriter myWriter = new FileWriter("../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/" + fileName + ".drl");
            myWriter.write(drl);
            myWriter.close();
            System.out.println("Successfully wrote to the file: " + fileName);
          } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
          }
        
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
        
        
        ksession.insert(new BrainWaveFrequencyChangedEvent());
        ksession.insert(sleep1);
        ksession.fireAllRules();
        
        ksession.insert(new BrainWaveFrequencyChangedEvent());
        ksession.insert(sleep2);
        ksession.fireAllRules();
        
        ksession.insert(new BrainWaveFrequencyChangedEvent());
        ksession.insert(sleep3);
        ksession.fireAllRules();
        
        ksession.insert(new BrainWaveFrequencyChangedEvent());
        ksession.insert(sleep4);
        ksession.fireAllRules();
        
        ksession.insert(new BrainWaveFrequencyChangedEvent());
        ksession.insert(sleep5);
        ksession.fireAllRules();
        
        ksession.insert(new BrainWaveFrequencyChangedEvent());
        ksession.insert(sleep6);
        ksession.fireAllRules();
        
        assertThat(sleep1.getSleepPhase(), is(SleepPhase.AWAKE));
        assertThat(sleep2.getSleepPhase(), is(SleepPhase.PHASE1));
        assertThat(sleep3.getSleepPhase(), is(SleepPhase.PHASE2));
        assertThat(sleep4.getSleepPhase(), is(SleepPhase.PHASE3));
        assertThat(sleep5.getSleepPhase(), is(SleepPhase.PHASE4));
        assertThat(sleep6.getSleepPhase(), is(SleepPhase.REM));
    }
    

	
}
