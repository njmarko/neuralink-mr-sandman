package sbnz.mrsandman.neuralinkapp.model.cep;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.kie.internal.io.ResourceFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;
import sbnz.mrsandman.neuralinkapp.model.BadHabbit;
import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.model.enums.BadHabbitType;
import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.SignalEvent;
import sbnz.mrsandman.neuralinkapp.model.events.SleepPhaseEvent;
import sbnz.mrsandman.neuralinkapp.model.events.WakeUpEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.AlcoholBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.RaisedAlcoholLevelEvent;
import sbnz.mrsandman.neuralinkapp.model.events.beginsleeping.BeginSleepingEvent;
import sbnz.mrsandman.neuralinkapp.model.events.caffeine.CaffeineBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.light.BrightLightBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.physicalactivity.PhysicalActivityEvent;

@SpringBootTest
public class HabitRecommendationCepTest extends BaseCepTest {

	@Autowired
	public HabitRecommendationCepTest(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		String fileName = "habit-recomendations";
		String filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/"
				+ fileName + ".drl";
		File f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));
		
		fileName = "chronotype-clasification";
		filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/"
				+ fileName + ".drl";
		f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));

		fileName = "optimal-sleep-time-template";
		filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/"
				+ fileName + ".drl";
		f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));

		fileName = "bad-habbits-cep";
		filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/" + fileName + ".drl";
		f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));
		
		fileName = "bad-habit-score-template";
		filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/"
				+ fileName + ".drl";
		f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));
		
		fileName = "begin-sleeping";
		filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/" + fileName + ".drl";
		f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));
		
		fileName = "signal-clasification";
		filePath = "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/" + fileName + "/"
				+ fileName + ".drl";
		f = new File(filePath);
		kfs.write(ResourceFactory.newFileResource(f));
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		User user1 = new User();
		user1.setAge(16);
		user1.setIsLightSleep(false);
		user1.setGoingToBedTime(LocalTime.of(21, 0));
		user1.setIsStatic(true);

		ksession.insert(user1);
		ksession.fireAllRules();

		int ruleCount = 0;
		SessionPseudoClock clock = ksession.getSessionClock();

		for (int i = 0; i < 2; i++) {
			AlcoholBeforeSleepEvent alch = new AlcoholBeforeSleepEvent(1.69 + i,
					LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0 + 2 * i)));
			ksession.insert(alch);
			clock.advanceTime(1, TimeUnit.MINUTES);
			ruleCount = ksession.fireAllRules();

			PhysicalActivityEvent phys = new PhysicalActivityEvent(38 + i,
					LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0 + 2 * i)));
			ksession.insert(phys);
			clock.advanceTime(1, TimeUnit.MINUTES);
			ruleCount = ksession.fireAllRules();

			BrightLightBeforeSleepEvent light = new BrightLightBeforeSleepEvent(8000 + i * 100,
					LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0 + 2 * i)));
			ksession.insert(light);
			clock.advanceTime(1, TimeUnit.MINUTES);
			ruleCount = ksession.fireAllRules();

			CaffeineBeforeSleepEvent caffeine = new CaffeineBeforeSleepEvent(25 + i * 5,
					LocalDateTime.of(LocalDate.now(), LocalTime.of(20, 0 + 2 * i)));
			ksession.insert(caffeine);
			clock.advanceTime(1, TimeUnit.MINUTES);
			ruleCount = ksession.fireAllRules();
		}

		SleepPhaseEvent phase = new SleepPhaseEvent(SleepPhase.AWAKE);
		ksession.insert(phase);
		clock.advanceTime(1, TimeUnit.MINUTES);
		ruleCount = ksession.fireAllRules();

		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(AlcoholBeforeSleepEvent.class));
		assertThat(newEvents.size(), equalTo(2));

		newEvents = ksession.getObjects(new ClassObjectFilter(PhysicalActivityEvent.class));
		assertThat(newEvents.size(), equalTo(2));

		newEvents = ksession.getObjects(new ClassObjectFilter(BrightLightBeforeSleepEvent.class));
		assertThat(newEvents.size(), equalTo(2));

		newEvents = ksession.getObjects(new ClassObjectFilter(CaffeineBeforeSleepEvent.class));
		assertThat(newEvents.size(), equalTo(2));

//		phase = new SleepPhaseEvent(SleepPhase.PHASE1);
//		BeginSleepingEvent beginSleep = new BeginSleepingEvent();
//		ksession.insert(beginSleep);
//		clock.advanceTime(1, TimeUnit.MINUTES);
//		ruleCount = ksession.fireAllRules();
		
		// we make user fall asleep
		for (int i = 0; i < 240; i++) {
			ksession.insert(new SignalEvent(35, SignalType.TEMPERATURE));
			ksession.insert(new SignalEvent(40, SignalType.HEART_BEAT));
			clock.advanceTime(100, TimeUnit.MILLISECONDS);
		}
		ruleCount = ksession.fireAllRules();

		newEvents = ksession.getObjects(new ClassObjectFilter(BadHabbit.class));
		assertThat(newEvents.size(), equalTo(4));
		
		clock.advanceTime(8, TimeUnit.HOURS);
		WakeUpEvent level = new WakeUpEvent();
		ksession.insert(level);
		clock.advanceTime(1, TimeUnit.MINUTES);
		ruleCount = ksession.fireAllRules();
		assertThat(ruleCount, equalTo(4));
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		// TODO Auto-generated method stub

	}

}
