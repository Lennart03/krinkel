package com.realdolmen.chiro.service;

import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.units.ChiroContact;
import com.realdolmen.chiro.domain.units.HttpChiroContact;
import com.realdolmen.chiro.spring_test.SpringIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.Calendar;

/**
 * Created by LVDBB73 on 15/12/2016.
 */
public class ChiroContactServiceTest extends SpringIntegrationTest {

    @Autowired
    private ChiroContactService chiroContactService;

    @Test
    public void shouldReturnChiroContact() throws IOException, URISyntaxException {
        HttpChiroContact chiroContact = (HttpChiroContact) chiroContactService.getChiroContact(397768);
        Assert.assertEquals("397768", chiroContact.getAdnr());
        Assert.assertEquals("thomas", chiroContact.getFirstName());
        Assert.assertEquals("simons", chiroContact.getLastName());
        Assert.assertEquals("A", chiroContact.getAfdeling());
        Assert.assertEquals(Arrays.asList("GP"), chiroContact.getFuncties());
        Assert.assertEquals(Gender.X, chiroContact.getGender());
        Calendar calendar = Calendar.getInstance();
        calendar.set(1946, Calendar.DECEMBER, 3);
        Calendar chiroContactCalendar = Calendar.getInstance();
        chiroContactCalendar.setTime(chiroContact.getBirthDate());
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), chiroContactCalendar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(calendar.get(Calendar.MONTH), chiroContactCalendar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.YEAR), chiroContactCalendar.get(Calendar.YEAR));
        Assert.assertEquals("teststraat 8", chiroContact.getStreetAddress());
        Assert.assertEquals("3660", chiroContact.getPostalCode());
        Assert.assertEquals("Bree", chiroContact.getCity());
        Assert.assertEquals("BE", chiroContact.getCountry());
        Assert.assertEquals(null, chiroContact.getPhone());
        Assert.assertEquals("thomas.simons@realdolmen.com", chiroContact.getEmail());
        Assert.assertEquals("377768", chiroContact.getId());
        Assert.assertEquals("200", chiroContact.getHttpStatus());
    }

}
