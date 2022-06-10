package sbnz.mrsandman.neuralinkapp.dto;

import java.time.LocalTime;

import sbnz.mrsandman.neuralinkapp.model.enums.Gender;

public class RegisterUserRequest {
	private Integer age;
	private LocalTime goingToBedTime;
	private Boolean isLightSleep;
	private Gender gender;

	public RegisterUserRequest() {
		super();
	}

	public RegisterUserRequest(Integer age, LocalTime goingToBedTime, Boolean isLightSleep, Gender gender) {
		super();
		this.age = age;
		this.goingToBedTime = goingToBedTime;
		this.isLightSleep = isLightSleep;
		this.gender = gender;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public LocalTime getGoingToBedTime() {
		return goingToBedTime;
	}

	public void setGoingToBedTime(LocalTime goingToBedTime) {
		this.goingToBedTime = goingToBedTime;
	}

	public Boolean getIsLightSleep() {
		return isLightSleep;
	}

	public void setIsLightSleep(Boolean isLightSleep) {
		this.isLightSleep = isLightSleep;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

}
