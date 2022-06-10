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

public class BadHabitScoreTemplate extends BaseTemplateTest {

    @Test
    public void testSimpleTemplateWithArrays() throws FileNotFoundException{
        System.out.println("Hello");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
        String fileName = "bad-habit-score-template";

//        InputStream template = SleepPhaseTemplate.class.getResourceAsStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/sleep-stage-clasification.drt");
//        InputStream template = SleepPhaseTemplateTest.class.getResourceAsStream("/templates/sleep-stage-clasification.drt");
        InputStream template = new FileInputStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/" + fileName + ".drt");
        
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
//        	badHabitType
//        	causeEventName
//        	timePeriod
//        	timeUnit
//        	timeUnitValue
//        	expectedValue
            new String[]{ "ALCOHOL", "AlcoholBeforeSleepEvent", "6", "h", "60", "2.0"},
            new String[]{ "INTENSE_PHYSICAL_ACTIVITY", "PhysicalActivityEvent", "2", "h", "60", "37.0"},
            new String[]{ "BRIGHT_LIGHT_EXPOSURE", "BrightLightBeforeSleepEvent", "2", "h", "60", "1000.0"},
            new String[]{ "CAFFEINE", "CaffeineBeforeSleepEvent", "6", "h", "60", "25.0"},

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
