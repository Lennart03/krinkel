package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.exception.NoContactFoundException;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.runners.MockitoJUnitRunner;

import java.net.URISyntaxException;

/**
 * Created by MHSBB71 on 11/12/2016.
 */
@RunWith(MockitoJUnitRunner.class)
@Ignore
public class ChiroContactServiceTest {

    @InjectMocks
    private ChiroContactService chiroContactService;

    @Test
    public void getChiroContactTest() throws URISyntaxException {
        String contact = chiroContactService.getContact(221826);

        Assert.assertNotNull(contact);

        String count = contact.substring(contact.indexOf("count") + 2 + 5 );
        count = count.substring(0, count.indexOf(","));
        Assert.assertNotEquals(Integer.valueOf(count), (Integer)0);
    }

    @Test
    public void parsteContactJSONTest() throws URISyntaxException, NoContactFoundException {
        RegistrationParticipant registrationParticipant = chiroContactService.getRegistrationParticipant(221826);

        Assert.assertNotNull(registrationParticipant);
        Assert.assertEquals(registrationParticipant.getAdNumber(), 221826);
        Assert.assertNotNull(registrationParticipant.getFirstName());
        Assert.assertNotNull(registrationParticipant.getLastName());
        Assert.assertNotNull((registrationParticipant.getEmail()));
        Assert.assertNotNull((registrationParticipant.getBirthdate()));
        Assert.assertNotNull((registrationParticipant.getAddress()));
        Address address = registrationParticipant.getAddress();
        Assert.assertNotNull((address.getPostalCode()));
        Assert.assertNotNull((address.getStreet()));
        Assert.assertNotNull((address.getCity()));
        Assert.assertNotNull((address.getHouseNumber()));
        Assert.assertNotNull((registrationParticipant.getGender()));
        Assert.assertNotNull((registrationParticipant.getPhoneNumber()));
    }
}
