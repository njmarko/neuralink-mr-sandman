package sbnz.mrsandman.neuralinkapp.model.templates;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.Test;
import org.kie.api.builder.Message;
import org.kie.api.builder.Results;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.utils.KieHelper;

import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.BrainWaveFrequencyChangedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.SignalEvent;

public class SignalTemplateTest extends BaseTemplateTest{

    @Test
    public void testSimpleTemplateWithArrays() throws FileNotFoundException{
        System.out.println("Hello");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
        String fileName = "signal-clasification";

//        InputStream template = SleepPhaseTemplate.class.getResourceAsStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/sleep-stage-clasification.drt");
//        InputStream template = SleepPhaseTemplateTest.class.getResourceAsStream("/templates/sleep-stage-clasification.drt");
        InputStream template = new FileInputStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/" + fileName + ".drt");
        
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
//        	minValue
//        	maxValue
//        	signalType
//        	timePeriod
//        	timeUnit
//        	amountAppeared
//        	newEvent
            new String[]{"0.2", "100.0",  "ALCOHOL_LEVEL", "10", "s", "3", "RaisedAlcoholLevelEvent"},
            new String[]{"0", "100",  "HEART_BEAT", "60", "s", "100", "HeartRateIncreasedEvent"},
            new String[]{"20", "100",  "CAFFEINE_LEVEL", "10", "s", "3", "RaiseCaffeineLevelEvent"},
            
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
        
//        KieSession ksession = createKieSessionFromDRL(drl);
//        
//        doTest(ksession);
    }
    
	private void doTest(KieSession ksession){

    }

	
}
