package sbnz.mrsandman.neuralinkapp.model.templates;

import static org.hamcrest.CoreMatchers.is;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalTime;

import org.drools.template.DataProvider;
import org.drools.template.DataProviderCompiler;
import org.drools.template.objects.ArrayDataProvider;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.model.enums.Chronotype;

@SpringBootTest
public class OptimalSleepTimeTemplateTest extends BaseTemplateTest {

	@Test
	public void testSimpleTemplateWithArrays() throws FileNotFoundException {
		System.out.println("Hello");
		System.out.println("Working Directory = " + System.getProperty("user.dir"));

		String fileName = "optimal-sleep-time-template";

//        InputStream template = SleepPhaseTemplate.class.getResourceAsStream("../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/sleep-stage-clasification.drt");
//        InputStream template = SleepPhaseTemplateTest.class.getResourceAsStream("/templates/sleep-stage-clasification.drt");
		InputStream template = new FileInputStream(
				"../neuralink-kjar/src/main/resources/sbnz/mrsandman/templates/" + fileName + ".drt");

		DataProvider dataProvider = new ArrayDataProvider(new String[][] {
//        	lowerHourBound
//        	lowerMinuteBound
//        	upperHourBound
//        	upperMinuteBound
//        	isLightSleep
//        	chronotype
				new String[] { "21", "30", "LION" }, new String[] { "0", "0", "WOLF" },
				new String[] { "23", "0", "DOLPHIN" }, new String[] { "22", "0", "BEAR" }, });

		DataProviderCompiler converter = new DataProviderCompiler();
		String drl = converter.compile(dataProvider, template);

		System.out.println(drl);

		try {
			new File("../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/")
					.mkdirs();
			FileWriter myWriter = new FileWriter(
					"../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/"
							+ fileName + ".drl");
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

	private void doTest(KieSession ksession) {
		User user1 = new User();
		user1.setAge(16);
		user1.setIsLightSleep(false);
		user1.setGoingToBedTime(LocalTime.of(21, 0));
		user1.setChronotype(Chronotype.LION);

		ksession.insert(user1);
		ksession.fireAllRules();

		User user2 = new User();
		user2.setAge(16);
		user2.setIsLightSleep(false);
		user2.setGoingToBedTime(LocalTime.of(23, 0));
		user2.setChronotype(Chronotype.BEAR);

		ksession.insert(user2);
		ksession.fireAllRules();

		User user3 = new User();
		user3.setAge(16);
		user3.setIsLightSleep(false);
		user3.setGoingToBedTime(LocalTime.of(1, 0));
		user3.setChronotype(Chronotype.WOLF);

		ksession.insert(user3);
		ksession.fireAllRules();

		User user4 = new User();
		user4.setAge(16);
		user4.setIsLightSleep(true);
		user4.setGoingToBedTime(LocalTime.of(22, 0));
		user4.setChronotype(Chronotype.DOLPHIN);

		ksession.insert(user4);
		ksession.fireAllRules();

		assertNotNull(user1.getOptimalSleepTime());
		assertNotNull(user2.getOptimalSleepTime());
		assertNotNull(user3.getOptimalSleepTime());
		assertNotNull(user4.getOptimalSleepTime());

	}
}
