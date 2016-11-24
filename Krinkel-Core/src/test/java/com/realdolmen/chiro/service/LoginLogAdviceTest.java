package com.realdolmen.chiro.service;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.realdolmen.chiro.domain.LoginLog;
import com.realdolmen.chiro.domain.User;
import com.realdolmen.chiro.integration.IntegrationTest;
import com.realdolmen.chiro.repository.LoginLoggerRepository;


@ContextConfiguration(classes={com.realdolmen.chiro.config.TestConfig.class})
@RunWith(SpringJUnit4ClassRunner.class)
public class LoginLogAdviceTest extends IntegrationTest {
	
	@Autowired
	private CASService casService;
	
	@Autowired
    private LoginLoggerRepository loginLoggerRepository;
	
	private User user;
	private static final String AD_NUMBER = "12131212";
	private static final String STAM_NUMBER = "AG/2013";
	
	@Before
	public void setup(){
		user = new User(AD_NUMBER, "user@email.com", "userFirstName", "userLastName", STAM_NUMBER);
	}

	@Test
	public void whenCasServiceCreatesTokenLoginLogsShouldContainUserAdNumber(){		
		Assert.assertNotNull(casService);
		Assert.assertNotNull(loginLoggerRepository);		
		casService.createToken(user);
		List<LoginLog> loginLogs = loginLoggerRepository.findByAdNumber(AD_NUMBER);
		assertEquals(STAM_NUMBER,loginLogs.get(0).getStamNumber());		
	}
	

}
