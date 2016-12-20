package com.realdolmen.chiro.converters;

import com.realdolmen.chiro.containers.ChiroContactContainer;
import com.realdolmen.chiro.dataholders.ChiroContactHolder;
import com.realdolmen.chiro.domain.Gender;
import com.realdolmen.chiro.domain.units.ChiroContact;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * Created by LVDBB73 on 15/12/2016.
 */
public class ChiroContactMapperTest {

    private final String adnr = "3658942";
    private final String firstName = "Yorick";
    private final String lastName = "Mori";
    private final String afdeling = "R";
    private final List<String> functies = Arrays.asList("fucntion1", "function2");
    private final String gender = "0";
    private final Gender fullGender = Gender.values()[0];
    private final String birthDate = "1936-09-15";
    private final String streetAddress = "Summoners rift 3";
    private final String postalCode = "6969";
    private final String city = "Test city";
    private final String country  = "BE";
    private final String phone = "6589-9874569";
    private final String email = "yorick.mori@feeders.com";
    private final String id = "1";

    private ChiroContactHolder fullChiroContactHolder;
    private ChiroContactHolder chiroContactHolderLackingGender;
    private ChiroContactHolder chiroContactHolderLackingBirthDay;

    private final String is_error = "1";
    private final String version = "2";
    private final String count = "3";

    private ChiroContactContainer chiroContactContainer;

    private ChiroContactMapper chiroContactMapper;

    @Before
    public void init() {
        fullChiroContactHolder = new ChiroContactHolder();
        fullChiroContactHolder.setAdnr(adnr);
        fullChiroContactHolder.setFirst_name(firstName);
        fullChiroContactHolder.setLast_name(lastName);
        fullChiroContactHolder.setAfdeling(afdeling);
        fullChiroContactHolder.setFuncties(functies);
        fullChiroContactHolder.setGender_id(gender);
        fullChiroContactHolder.setBirth_date(birthDate);
        fullChiroContactHolder.setStreet_address(streetAddress);
        fullChiroContactHolder.setPostal_code(postalCode);
        fullChiroContactHolder.setCity(city);
        fullChiroContactHolder.setCountry(country);
        fullChiroContactHolder.setPhone(phone);
        fullChiroContactHolder.setEmail(email);
        fullChiroContactHolder.setId(id);
        chiroContactHolderLackingGender = new ChiroContactHolder(fullChiroContactHolder);
        chiroContactHolderLackingGender.setGender_id(null);
        chiroContactHolderLackingBirthDay = new ChiroContactHolder(fullChiroContactHolder);
        chiroContactHolderLackingBirthDay.setBirth_date(null);
        chiroContactContainer = new ChiroContactContainer();
        chiroContactContainer.setId(id);
        chiroContactContainer.setCount(count);
        chiroContactContainer.setIs_error(is_error);
        chiroContactContainer.setCount(count);
        chiroContactContainer.setValues(Arrays.asList(fullChiroContactHolder, chiroContactHolderLackingBirthDay, chiroContactHolderLackingGender));
    }

    @Before
    public void initializeMapper() {
        chiroContactMapper = new ChiroContactMapper();
    }

    @Test
    public void getFullChiroContact() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(1936, Calendar.SEPTEMBER, 15);
        ChiroContact chiroContact = chiroContactMapper.createChiroContact(fullChiroContactHolder);
        Assert.assertEquals(adnr, chiroContact.getAdnr());
        Assert.assertEquals(firstName, chiroContact.getFirstName());
        Assert.assertEquals(lastName, chiroContact.getLastName());
        Assert.assertEquals(afdeling, chiroContact.getAfdeling());
        Assert.assertEquals(functies, chiroContact.getFuncties());
        Assert.assertEquals(fullGender, chiroContact.getGender());
        Calendar chiroCaledar = Calendar.getInstance();
        chiroCaledar.setTime(chiroContact.getBirthDate());
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), chiroCaledar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(calendar.get(Calendar.MONTH), chiroCaledar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.YEAR), chiroCaledar.get(Calendar.YEAR));
        Assert.assertEquals(streetAddress, chiroContact.getStreetAddress());
        Assert.assertEquals(postalCode, chiroContact.getPostalCode());
        Assert.assertEquals(city, chiroContact.getCity());
        Assert.assertEquals(country, chiroContact.getCountry());
        Assert.assertEquals(phone, chiroContact.getPhone());
        Assert.assertEquals(email, chiroContact.getEmail());
        Assert.assertEquals(id, chiroContact.getId());
    }

    @Test
    public void getChiroContactWithoutGender() {
        ChiroContact chiroContact = chiroContactMapper.createChiroContact(chiroContactHolderLackingGender);
        Assert.assertEquals(Gender.X, chiroContact.getGender());
    }

    @Test
    public void getChiroContactWithoutBirthDay() {
        ChiroContact chiroContact = chiroContactMapper.createChiroContact(chiroContactHolderLackingBirthDay);
        Assert.assertEquals(null, chiroContact.getBirthDate());
    }

    @Test
    public void shouldReturnFirstChiroContact() {
        ChiroContact chiroContact = chiroContactMapper.retrieveChiroContact(chiroContactContainer);
        Calendar calendar = Calendar.getInstance();
        calendar.set(1936, Calendar.SEPTEMBER, 15);
        Assert.assertEquals(adnr, chiroContact.getAdnr());
        Assert.assertEquals(firstName, chiroContact.getFirstName());
        Assert.assertEquals(lastName, chiroContact.getLastName());
        Assert.assertEquals(afdeling, chiroContact.getAfdeling());
        Assert.assertEquals(functies, chiroContact.getFuncties());
        Assert.assertEquals(fullGender, chiroContact.getGender());
        Calendar chiroCaledar = Calendar.getInstance();
        chiroCaledar.setTime(chiroContact.getBirthDate());
        Assert.assertEquals(calendar.get(Calendar.DAY_OF_MONTH), chiroCaledar.get(Calendar.DAY_OF_MONTH));
        Assert.assertEquals(calendar.get(Calendar.MONTH), chiroCaledar.get(Calendar.MONTH));
        Assert.assertEquals(calendar.get(Calendar.YEAR), chiroCaledar.get(Calendar.YEAR));
        Assert.assertEquals(streetAddress, chiroContact.getStreetAddress());
        Assert.assertEquals(postalCode, chiroContact.getPostalCode());
        Assert.assertEquals(city, chiroContact.getCity());
        Assert.assertEquals(country, chiroContact.getCountry());
        Assert.assertEquals(phone, chiroContact.getPhone());
        Assert.assertEquals(email, chiroContact.getEmail());
        Assert.assertEquals(id, chiroContact.getId());
    }

    @Test
    public void shouldReturnChiroContacts() {
        List<ChiroContact> chiroContacts = chiroContactMapper.retrieveCollegues(chiroContactContainer);
        Assert.assertEquals(chiroContactContainer.getValues().size(), chiroContacts.size());
    }

}
