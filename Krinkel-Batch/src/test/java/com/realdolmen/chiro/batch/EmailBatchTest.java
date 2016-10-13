package com.realdolmen.chiro.batch;

import static org.junit.Assert.assertEquals;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realdolmen.chiro.config.BatchTestConfig;
import com.realdolmen.chiro.configuration.ApplicationConfiguration;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.domain.RegistrationVolunteer;
import com.realdolmen.chiro.repository.RegistrationParticipantRepository;


@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest
@ContextConfiguration(classes={BatchTestConfig.class,ApplicationConfiguration.class})
public class EmailBatchTest {

	@Test
	public void test(){
		
	}
}
