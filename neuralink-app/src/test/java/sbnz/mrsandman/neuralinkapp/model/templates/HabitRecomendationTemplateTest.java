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

public class HabitRecomendationTemplateTest extends BaseTemplateTest {

    @Test
    public void testSimpleTemplateWithArrays() throws FileNotFoundException{
        System.out.println("Hello");
        System.out.println("Working Directory = " + System.getProperty("user.dir"));
        
        String fileName = "habit-recomendations";

//        InputStream template = SleepPhaseTemplate.class.getResourceAsStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/sleep-stage-clasification.drt");
//        InputStream template = SleepPhaseTemplateTest.class.getResourceAsStream("/templates/sleep-stage-clasification.drt");
        InputStream template = new FileInputStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/" + fileName + ".drt");
        
        DataProvider dataProvider = new ArrayDataProvider(new String[][]{
//        	minValue
//        	habitType
//        	message
//        	salience_factor
            new String[]{"1.0", "ALCOHOL",  "You should stop drinking less than 4 hours before optimal sleep time!", "1.0"},
            new String[]{"1.0", "BRIGHT_LIGHT_EXPOSURE",  "You should dim the lights 2 hours before optimal sleep time!", "0.85"},
            new String[]{"1.0", "CAFFEINE",  "You should drink less caffeine less than 4 hours before optimal sleep time!", "0.95"},
            new String[]{"1.0", "INTENSE_PHYSICAL_ACTIVITY",  "You should stop doing intense physical exercises less than 4 hours before optimal sleep time!", "0.90"},
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
