package sbnz.mrsandman.neuralinkapp.model.templates;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;

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
            new String[]{"0.2", "100.0",  "ALCOHOL_LEVEL", "60", "s", "3", ">=", "RaisedAlcoholLevelEvent"},
            new String[]{"50", "100",  "HEART_BEAT", "60", "s", "100", ">=", "HeartRateIncreasedEvent"},
            new String[]{"1", "50",  "HEART_BEAT", "60", "s", "100", ">=", "HeartRateLoweredEvent"},
            new String[]{"20", "100",  "CAFFEINE_LEVEL", "10", "s", "3", ">=", "RaisedCaffeineLevelEvent"},
            new String[]{"38", "100",  "TEMPERATURE", "60", "s", "3", ">=", "RaisedTemperatureEvent"},
            new String[]{"0", "36", "TEMPERATURE", "60", "s", "100", ">=", "LoweredTemperatureEvent"},
            new String[]{"1000", "100",  "LIGHT_LEVEL", "60", "s", "3", ">=", "BrightLightEvent"},
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
