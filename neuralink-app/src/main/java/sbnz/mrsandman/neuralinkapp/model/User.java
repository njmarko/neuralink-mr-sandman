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

	public User() {
		super();
	}


	public User(int age, Gender gender, LocalTime goingToBedTime, Chronotype chronotype, boolean isLightSleep) {
		super();
		this.age = age;
		this.gender = gender;
		this.goingToBedTime = goingToBedTime;
		this.chronotype = chronotype;
		this.isLightSleep = isLightSleep;
	}


	public boolean isLightSleep() {
		return isLightSleep;
	}


	public void setLightSleep(boolean isLightSleep) {
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
				+ chronotype + "]";
	}

}
