package sbnz.mrsandman.neuralinkapp.model;

import java.time.LocalTime;

import sbnz.mrsandman.neuralinkapp.model.enums.Chronotype;
import sbnz.mrsandman.neuralinkapp.model.enums.Gender;

public class User {

	private int age;
	private Gender gender;
	private LocalTime goingToBedTime;
	private Chronotype chronotype;
	private boolean isLightSleep;
	private LocalTime optimalSleepTime;
	private float speed;
	private boolean isStatic;

	public User() {
		super();
		this.isStatic = false;
	}

	public User(int age, Gender gender, LocalTime goingToBedTime, Chronotype chronotype, boolean isLightSleep,
			LocalTime optimalSleepTime, float speed) {
		super();
		this.age = age;
		this.gender = gender;
		this.goingToBedTime = goingToBedTime;
		this.chronotype = chronotype;
		this.isLightSleep = isLightSleep;
		this.optimalSleepTime = optimalSleepTime;
		this.speed = speed;
	}
	
	public void setIsStatic(boolean isStatic) {
		this.isStatic = isStatic;
	}
	
	public boolean getIsStatic() {
		return this.isStatic;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public void setOptimalSleepTime(LocalTime optimalSleepTime) {
		this.optimalSleepTime = optimalSleepTime;
	}

	public LocalTime getOptimalSleepTime() {
		return optimalSleepTime;
	}

	public boolean getIsLightSleep() {
		return isLightSleep;
	}

	public void setIsLightSleep(boolean isLightSleep) {
		this.isLightSleep = isLightSleep;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public LocalTime getGoingToBedTime() {
		return goingToBedTime;
	}

	public void setGoingToBedTime(LocalTime goingToBedTime) {
		this.goingToBedTime = goingToBedTime;
	}

	public Chronotype getChronotype() {
		return chronotype;
	}

	public void setChronotype(Chronotype chronotype) {
		this.chronotype = chronotype;
	}

	@Override
	public String toString() {
		return "User [age=" + age + ", gender=" + gender + ", goingToBedTime=" + goingToBedTime + ", chronotype="
				+ chronotype + ", isLightSleep=" + isLightSleep + ", optimalSleepTime=" + optimalSleepTime + "]";
	}

}
