package com.realdolmen.chiro.controller;

import java.util.Calendar;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.CampGround;
import com.realdolmen.chiro.domain.Eatinghabbit;
import com.realdolmen.chiro.domain.EventRole;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.domain.VolunteerFunction;
import com.realdolmen.chiro.repository.RegistrationVolunteerRepository;
import com.realdolmen.chiro.spring_test.MockMvcTest;

//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = { com.realdolmen.chiro.config.TestConfig.class })
public class UnitControllerMockMvcTest extends MockMvcTest{
	private static final String ANTWERPEN_STAMNUMMER= "AG0103";
	private RegistrationVolunteer volunteer;
    private RegistrationParticipant participant;
    private int nVolunteers = 0;

//	@Autowired 
//	private WebApplicationContext ctx;
	
	@Autowired
    private RegistrationVolunteerRepository repo;
	
	private MockMvc mockMvc;

	 @Before
	    public void setUp(){
	        // Participant
	        Calendar c = Calendar.getInstance();
	        c.set(1995, Calendar.AUGUST, 5);

	        participant = new RegistrationParticipant(
	                "386283", "astrid@mail.any", "Maia", "Van Op Beeck", c.getTime(),
	                ANTWERPEN_STAMNUMMER, Gender.WOMAN, EventRole.ASPI, Eatinghabbit.FISHANDMEAT
	        );
	        participant.setAddress(new Address("My Street", "2", 1252, "My City"));

	        c.set(1995, Calendar.AUGUST, 5);

	        volunteer = new RegistrationVolunteer(
	                "386283", "aster.deckers@example.org", "Aster", "Deckers", c.getTime(),
	                ANTWERPEN_STAMNUMMER, Gender.MAN, EventRole.LEADER, Eatinghabbit.VEGI,
	                CampGround.ANTWERPEN,
	                new VolunteerFunction(VolunteerFunction.Preset.KRINKEL_EDITORIAL)
	        );
	        volunteer.setAddress(new Address("-", "-", 1500, "-"));

	        this.nVolunteers = repo.findAll().size();
	    }
	@Ignore
	@Test
	public void resultShouldContainFilledInCountFields() throws Exception{
		mockMvc().perform(MockMvcRequestBuilders.get("api/" + ANTWERPEN_STAMNUMMER))
				.andExpect(MockMvcResultMatchers.content().string(Matchers.containsString("\"aantal_ingeschreven_vrijwilligers\":\"2\",")));
	}
}
