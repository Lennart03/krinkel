package com.realdolmen.chiro.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.units.ChiroUnit;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;

@ContextConfiguration(classes = { com.realdolmen.chiro.config.TestConfig.class })
public class ChiroUnitServiceIntegrationTest extends SpringIntegrationTest {
	private static final String ANTWERPEN_STAMNUMMER = "AG0000";
	private static final String BRUSSEL_STAMNUMMER = "BG0000";
	private static final String LACH_STAMNUMMER = "AG0200";
	private static final String PLEPLO_STAMNUMMER = "AG0103";
	
	@Autowired
	private ChiroUnitService chiroUnitService;
	
	@Before
	public void setup(){
		chiroUnitService.findAll().forEach(unit->{
			unit.setParticipantsCount(0);
			unit.setVolunteersCount(0);
		});
	}
	@Test
	public void shouldReturnAParticipantInPleploGroup(){
		List<RegistrationParticipant>participants=chiroUnitService.findParticipantsByChiroUnit(PLEPLO_STAMNUMMER);
		Assert.assertNotNull(participants);
		Assert.assertEquals(1, participants.size());
		ChiroUnit unit = chiroUnitService.find(PLEPLO_STAMNUMMER);
		Assert.assertEquals(participants.size(), unit.getParticipantsCount());
	}
	
	@Test
	public void shouldReturnTwoParticipantsInAntwerpVerbond(){
		List<RegistrationParticipant>participants=chiroUnitService.findParticipantsByChiroUnit(ANTWERPEN_STAMNUMMER);
		Assert.assertNotNull(participants);
		Assert.assertEquals(2, participants.size());
		ChiroUnit unit = chiroUnitService.find(ANTWERPEN_STAMNUMMER);
		Assert.assertEquals(participants.size(), unit.getParticipantsCount());
	}
	
	@Test
	public void shouldReturnOnlyOneParticipantInLachGewestInAntwerpenVerbond(){
		List<RegistrationParticipant>participants=chiroUnitService.findParticipantsByChiroUnit(LACH_STAMNUMMER);
		Assert.assertNotNull(participants);
		Assert.assertEquals(1, participants.size());
		ChiroUnit unit = chiroUnitService.find(LACH_STAMNUMMER);
		Assert.assertEquals(participants.size(), unit.getParticipantsCount());
	}
	
	@Test
	public void shouldNotReturnTheOnlyParticipantInBrusselBecauseHeHasntPaid(){
		List<RegistrationParticipant>participants=chiroUnitService.findParticipantsByChiroUnit(BRUSSEL_STAMNUMMER);
		Assert.assertNotNull(participants);
		Assert.assertEquals(0, participants.size());
		ChiroUnit unit = chiroUnitService.find(BRUSSEL_STAMNUMMER);
		Assert.assertEquals(participants.size(), unit.getParticipantsCount());
	}
	
	@Test
	public void shouldReturnAVolunteerInJostoGroup(){
		List<RegistrationVolunteer>volunteers=chiroUnitService.findVolunteersByChiroUnit(PLEPLO_STAMNUMMER);
		Assert.assertNotNull(volunteers);
		Assert.assertEquals(1, volunteers.size());
		ChiroUnit unit = chiroUnitService.find(PLEPLO_STAMNUMMER);
		Assert.assertEquals(volunteers.size(), unit.getVolunteersCount());
	}
	
	@Test
	public void shouldReturnChiroUnitAntwerpWithFilledCountFields(){
		ChiroUnit unit = chiroUnitService.find(ANTWERPEN_STAMNUMMER);
		Assert.assertEquals(2, unit.getParticipantsCount());
	}
	
	@Test
	public void shouldReturnChiroUnitPleploWithFilledCountFields(){
		ChiroUnit unit = chiroUnitService.find(PLEPLO_STAMNUMMER);
		Assert.assertEquals(1, unit.getVolunteersCount());
	}
	
	
	
	/*
	 * Prints details about chiroUnit hierarchy in a file
	 * */
	//@Test
	public void printUnits() {
		Assert.assertNotNull(chiroUnitService);
		List<ChiroUnit> verbonden = chiroUnitService.findVerbondUnits();
		printDetailsInFile("verbonden_details.txt", verbonden);
	}


	private void printDetailsInFile(String fileName, List<ChiroUnit> units) {
		try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(fileName)));) {
			
			units.forEach(s -> {
				s.getLower().forEach(l -> {
					l.getLower().forEach(g -> {
						if (g != null) {
							String info = String.format("%20s %10s %20s %10s %30s %10s", s.getName(), s.getStam(),
									l.getName(), l.getStam(), g.getName(), g.getStam());
							try {
								out.write(info);
								out.newLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}else{
							try {
								out.write("no lower level");
								out.newLine();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
				});
			});
		} catch (

		FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
