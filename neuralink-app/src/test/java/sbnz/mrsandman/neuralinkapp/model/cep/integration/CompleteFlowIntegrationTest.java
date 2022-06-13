package sbnz.mrsandman.neuralinkapp.model.cep.integration;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalTime;
import java.util.Collection;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.drools.core.time.SessionPseudoClock;
import org.hamcrest.Matchers;
import org.kie.api.builder.KieFileSystem;
import org.kie.api.runtime.ClassObjectFilter;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import sbnz.mrsandman.neuralinkapp.bus.EventBus;
import sbnz.mrsandman.neuralinkapp.model.BadHabbit;
import sbnz.mrsandman.neuralinkapp.model.Sleep;
import sbnz.mrsandman.neuralinkapp.model.SleepStage;
import sbnz.mrsandman.neuralinkapp.model.User;
import sbnz.mrsandman.neuralinkapp.model.cep.BaseCepTest;
import sbnz.mrsandman.neuralinkapp.model.enums.SignalType;
import sbnz.mrsandman.neuralinkapp.model.enums.SleepPhase;
import sbnz.mrsandman.neuralinkapp.model.events.BrainWaveEvent;
import sbnz.mrsandman.neuralinkapp.model.events.HabitRecomendationEvent;
import sbnz.mrsandman.neuralinkapp.model.events.SignalEvent;
import sbnz.mrsandman.neuralinkapp.model.events.SleepMetricsCalculatedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.SleepPhaseEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.AlcoholBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.alcohol.RaisedAlcoholLevelEvent;
import sbnz.mrsandman.neuralinkapp.model.events.beginsleeping.BeginSleepingEvent;
import sbnz.mrsandman.neuralinkapp.model.events.caffeine.CaffeineBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.caffeine.RaisedCaffeineLevelEvent;
import sbnz.mrsandman.neuralinkapp.model.events.heartrate.HeartRateIncreasedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.light.BrightLightBeforeSleepEvent;
import sbnz.mrsandman.neuralinkapp.model.events.movement.MovementDetectedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.physicalactivity.PhysicalActivityEvent;
import sbnz.mrsandman.neuralinkapp.model.events.somnabulism.SomnabulismDetectedEvent;
import sbnz.mrsandman.neuralinkapp.model.events.temperature.RaisedTemperatureEvent;

@SpringBootTest
public class CompleteFlowIntegrationTest extends BaseCepTest{

	@Autowired
	public CompleteFlowIntegrationTest(EventBus eventBus) {
		super(eventBus);
	}

	@Override
	protected void writeResourcesToSession(KieFileSystem kfs) {
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/detect-wakeup.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/compute-sleep-score.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/begin-sleeping.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/somnabulism-detection.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/movement-detection.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/sleep-stage-clasification/sleep-stage-clasification.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/signal-clasification/signal-clasification.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/muscle-voltage-classification/muscle-voltage-classification.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/cep/bad-habbits-cep.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/chronotype-clasification/chronotype-clasification.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/optimal-sleep-time-template/optimal-sleep-time-template.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/bad-habit-score-template/bad-habit-score-template.drl");
		writeFile(kfs, "../neuralink-kjar/src/main/resources/sbnz/mrsandman/rules/template-rules/habit-recomendations/habit-recomendations.drl");
	}

	@Override
	protected void runPseudoClockExample(KieSession ksession) {
		int ruleCount = 0;
		SessionPseudoClock clock = ksession.getSessionClock();
		System.out.println(clock.getCurrentTime());
		
		// we make user static
		User user = new User();
		user.setSpeed(0f);
		user.setAge(16);
		user.setIsLightSleep(false);
		user.setGoingToBedTime(LocalTime.of(23, 0));
		ksession.insert(user);
		ruleCount = ksession.fireAllRules();
		
		// 1 - Determine chronotype
		// 2 - Determine optimal sleep time
		
		assertEquals(2, ruleCount);
		
		
		System.out.println(user.getOptimalSleepTime());
		
		// Advance time to evening 20h (starts at +1)
		clock.advanceTime(19, TimeUnit.HOURS);
		System.out.println(clock.getCurrentTime());
		

		SleepPhaseEvent initialAwakeEvent = new SleepPhaseEvent(SleepPhase.AWAKE);
		ksession.insert(initialAwakeEvent);
		

		// Add base level signals
		SignalEvent level;
		for (int i = 0; i < 300; i++) {

			level = new SignalEvent(21, SignalType.CAFFEINE_LEVEL);
			ksession.insert(level);

			level = new SignalEvent(39, SignalType.TEMPERATURE);
			ksession.insert(level);

			level = new SignalEvent(0.22, SignalType.ALCOHOL_LEVEL);
			ksession.insert(level);
			
			level = new SignalEvent(1100, SignalType.LIGHT_LEVEL);
			ksession.insert(level);
			
			BrainWaveEvent bw = new BrainWaveEvent(10);
			ksession.insert(bw);
			
			clock.advanceTime(1, TimeUnit.SECONDS);
			ruleCount = ksession.fireAllRules();
			
		}
		
		for (int i = 0; i < 2400; i++) {
			level = new SignalEvent(80, SignalType.HEART_BEAT);
			ksession.insert(level);
			clock.advanceTime(5, TimeUnit.MILLISECONDS);
			ruleCount = ksession.fireAllRules();
		}
		
		// Assert base level signals raised events
		
		Collection<?> newEvents = ksession.getObjects(new ClassObjectFilter(RaisedAlcoholLevelEvent.class));
		assertThat(newEvents.size(), equalTo(6));
		
		newEvents = ksession.getObjects(new ClassObjectFilter(RaisedCaffeineLevelEvent.class));
		assertThat(newEvents.size(), equalTo(6));
		
		newEvents = ksession.getObjects(new ClassObjectFilter(RaisedTemperatureEvent.class));
		assertThat(newEvents.size(), equalTo(6));
		
		newEvents = ksession.getObjects(new ClassObjectFilter(HeartRateIncreasedEvent.class));
		assertThat(newEvents.size(), equalTo(1));
		
		
		// Assert Bad Habit events before sleep
		
		newEvents = ksession.getObjects(new ClassObjectFilter(AlcoholBeforeSleepEvent.class));
		assertThat(newEvents.size(), equalTo(1));
		
		newEvents = ksession.getObjects(new ClassObjectFilter(CaffeineBeforeSleepEvent.class));
		assertThat(newEvents.size(), equalTo(1));
		
		newEvents = ksession.getObjects(new ClassObjectFilter(BrightLightBeforeSleepEvent.class));
		assertThat(newEvents.size(), equalTo(1));
		
		newEvents = ksession.getObjects(new ClassObjectFilter(PhysicalActivityEvent.class));
		assertThat(newEvents.size(), equalTo(0));
		
		clock.advanceTime(30, TimeUnit.MINUTES);
		
		
		// we make user fall asleep
		for (int i = 0; i < 240; i++) {
			ksession.insert(new SignalEvent(35, SignalType.TEMPERATURE));
			ksession.insert(new SignalEvent(40, SignalType.HEART_BEAT));
			clock.advanceTime(100, TimeUnit.MILLISECONDS);
		}
		ruleCount = ksession.fireAllRules();
		// 1 - Rule for detecting heart rate lowered event
		// 2 - Rule for detecting temperature lowered event
		// 3 - Rule for detecting start of sleeping
		// 4 - Bad habit score calculations
		assertEquals(8, ruleCount);
		Collection<?> beginSleepingEvents = ksession.getObjects(new ClassObjectFilter(BeginSleepingEvent.class));
		assertEquals(1, beginSleepingEvents.size());
		Sleep sleep = (Sleep) ksession.getObjects(new ClassObjectFilter(Sleep.class)).toArray()[0];
		assertNotNull(sleep);
		
		newEvents = ksession.getObjects(new ClassObjectFilter(BadHabbit.class));
		assertThat(newEvents.size(), equalTo(3));
		
		// we make user iterate over sleep stages
		
		// phase 1
		insertBrainWaveWithFrequencyInRange(ksession, clock, 15, 25);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE1);
	
		// phase 2
		insertBrainWaveWithFrequencyInRange(ksession, clock, 25, 37);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE2);
	
		// phase 3
		insertBrainWaveWithFrequencyInRange(ksession, clock, 38, 50);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE3);
	
		// phase 4
		insertBrainWaveWithFrequencyInRange(ksession, clock, 50, 65);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE4);
	
		// rem
		insertBrainWaveWithFrequencyInRange(ksession, clock, 65, 90);
		assertedSleepChange(ksession, sleep, SleepPhase.REM);
		
		// we detect somnabulism
		for (int i = 0; i < 30; i++) { 
			ksession.insert(new SignalEvent(new Random().doubles(1,  10).findFirst().getAsDouble(), SignalType.SPEED));
			ksession.insert(new SignalEvent(new Random().doubles(75,  100).findFirst().getAsDouble(), SignalType.MUSCLE_VOLTAGE));
			clock.advanceTime(1, TimeUnit.SECONDS);
		}
		ruleCount = ksession.fireAllRules();
		
		// 1 - Movement detected event
		// 2 - Muscle tone changed to tense event
		// 3 - Somnabulism detected event
		assertEquals(3, ruleCount);
		Collection<?> movementDetectedEvent = ksession.getObjects(new ClassObjectFilter(MovementDetectedEvent.class));
		assertEquals(1, movementDetectedEvent.size());
		Collection<?> somnabulismDetectedEvent = ksession.getObjects(new ClassObjectFilter(SomnabulismDetectedEvent.class));
		assertEquals(1, somnabulismDetectedEvent.size());
		
		// we make user iterate over sleep stages some more
		
		// phase 3
		insertBrainWaveWithFrequencyInRange(ksession, clock, 38, 50);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE3);
	
		// phase 4
		insertBrainWaveWithFrequencyInRange(ksession, clock, 50, 65);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE4);
	
		// rem
		insertBrainWaveWithFrequencyInRange(ksession, clock, 65, 90);
		assertedSleepChange(ksession, sleep, SleepPhase.REM);
	
		// phase 4
		insertBrainWaveWithFrequencyInRange(ksession, clock, 50, 65);
		assertedSleepChange(ksession, sleep, SleepPhase.PHASE4);
	
		// rem
		insertBrainWaveWithFrequencyInRange(ksession, clock, 65, 90);
		assertedSleepChange(ksession, sleep, SleepPhase.REM);
		
		// we make user wake up & compute user's sleep quality & efficiency
		insertBrainWaveWithFrequencyInRange(ksession, clock, 0, 15);
		ruleCount = ksession.fireAllRules();
		// 1 - Detect AWAKE sleep phase
		// 2 - Detect wake up event
		// 3 - Compute sleep score
		// 4 - Compute sleep efficiency
		// 5,6,7 - Show habbit recomendations
		assertEquals(7, ruleCount);
		assertFalse(sleep.getEfficient());
		assertThat(sleep.getQuality(), Matchers.closeTo(10.0 * 37.0 / 121.0, 1e-4));
		Collection<?> metricsEvent = ksession.getObjects(new ClassObjectFilter(SleepMetricsCalculatedEvent.class));
		assertEquals(1, metricsEvent.size());
		
		// Check that 3 recommendations were shown
		metricsEvent = ksession.getObjects(new ClassObjectFilter(HabitRecomendationEvent.class));
		assertEquals(3, metricsEvent.size());
	
		
	}

	@SuppressWarnings("unchecked")
	private void assertedSleepChange(KieSession ksession, Sleep sleep, SleepPhase sleepPhase) {
		int ruleCount = ksession.fireAllRules();
//		assertEquals(1, ruleCount);
		Collection<SleepPhaseEvent> newEvents = (Collection<SleepPhaseEvent>) ksession.getObjects(new ClassObjectFilter(SleepPhaseEvent.class));
		Collection<SleepStage> sleepStages = (Collection<SleepStage>) ksession.getObjects(new ClassObjectFilter(SleepStage.class));
		assertTrue(newEvents.size() > 0);
		assertTrue(sleepStages.size() > 0);
		assertTrue(newEvents.stream().anyMatch(e -> e.getSleepPhase().equals(sleepPhase)));
		assertTrue(sleepStages.stream().anyMatch(s -> s.getSleepPhase().equals(sleepPhase)));
		sleepStages.forEach(stage -> {
			assertEquals(sleep, stage.getSleep());
		});
	}

	private void insertBrainWaveWithFrequencyInRange(KieSession ksession, SessionPseudoClock clock, int low, int high) {
		Random random = new Random();
		for (int i = 0; i < 150; i++) {
			int brainWaveFrequency = random.ints(low, high).findFirst().getAsInt();
			ksession.insert(new BrainWaveEvent(brainWaveFrequency));
			clock.advanceTime(5, TimeUnit.SECONDS);
		}
	}

	@Override
	protected void runRealtimeClockExample(KieSession ksession) {
		
	}
	
}
