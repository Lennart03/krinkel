package com.realdolmen.chiro.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Table(name="registration_communciation")
public class RegistrationCommunication {
	
	@Id
	@GeneratedValue
	private Long id;
	
	@Column(unique = true)
	@NotBlank
    private String adNumber;
	
	@NotNull
	private SendStatus status;
	
	@Min(0)
	private int communicationAttempt;

	public Long getId() {
		return id;
	}

	public String getAdNumber() {
		return adNumber;
	}

	public void setAdNumber(String adNumber) {
		this.adNumber = adNumber;
	}

	public SendStatus getStatus() {
		return status;
	}

	public void setStatus(SendStatus status) {
		this.status = status;
	}

	public int getCommunicationAttempt() {
		return communicationAttempt;
	}

	public void setCommunicationAttempt(int communicationAttempt) {
		this.communicationAttempt = communicationAttempt;
	}

}
