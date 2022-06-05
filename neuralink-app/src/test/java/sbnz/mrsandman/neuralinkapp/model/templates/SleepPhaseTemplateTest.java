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
import org.springframework.boot.test.context.SpringBootTest;

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

    }
    

	
}
