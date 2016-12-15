package com.realdolmen.chiro.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.realdolmen.chiro.converters.ChiroContactMapper;
import com.realdolmen.chiro.containers.ChiroContactContainer;
import com.realdolmen.chiro.domain.*;
import com.realdolmen.chiro.domain.units.ChiroContact;
import com.realdolmen.chiro.domain.units.HttpChiroContact;
import com.realdolmen.chiro.exception.NoContactFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Date;


/**
 *
 * Contacts the Chiro API to get user info
 *
 */
@Service
public class ChiroContactService {
    @Value("${chiro_url}")
    private String chiroUrl;

    @Value("${chiro_api_key}")
    private String apiKey;

    @Value("${chiro_key}")
    private String chiroKey;


    /**
     * GET JSON from Chiro, no need to map this to an object because it's only used in the frontend.
     * adNmber for test = 308986
     * @return JSON from Chiro
     */
    public String getContact(Integer adNumber) throws URISyntaxException {
        /**
         * Example URL
         * "https://cividev.chiro.be/sites/all/modules/civicrm/extern/rest.php?key=2340f8603072358ffc23f5459ef92f88&api_key=vooneih8oo1XepeiduGh&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";
         */

        String url = chiroUrl + "?key=" + chiroKey + "&api_key=" + apiKey + "&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";
        //String url = chiroUrl + "?key=" + chiroKey + "&api_key=" + apiKey + "&entity=Light&action=getcontact&json=%7B%22adnr%22:" + adNumber + "%7D";


        // Throws exception when the URL isn't valid, no further checks necessary because of this.
        URI uri = new URI(url);

        RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForEntity(uri, String.class)
                           .getBody();
    }

    /**
     * parse the JSON of the contact to a registrationParticipant
     *
     */

//    public RegistrationParticipant getRegistrationParticipant(Integer adNumber) throws URISyntaxException, NoContactFoundException {
//        String json = getContact(adNumber);
//       // String json = "{\"is_error\":0,\"version\":3,\"count\":1,\"id\":0,\"values\":[{\"adnr\":\"308986\",\"first_name\":\"Elise\",\"last_name\":\"Schollaert\",\"afdeling\":\"L\",\"functies\":[\"GG1\",\"GG2\"],\"gender_id\":\"1\",\"birth_date\":\"1978-07-20\",\"street_address\":\"Desselgemstraat 17\",\"postal_code\":\"2240\",\"city\":\"Massenhoven\",\"country\":\"BE\",\"phone\":\"02-0233980\",\"email\":\"120845@example.com\",\"id\":\"301334\"}]}";
//        System.out.println(json + " json object");
//        String count = json.substring(json.indexOf("count") +2 +5);
//        count = count.substring(0,count.indexOf(","));
//        System.out.println(count + " count");
//        RegistrationParticipant participant = new RegistrationParticipant();
//        if (Integer.parseInt(count) < 1)
//        {
//            System.out.println("throwing error NoContactFoundException");
//            participant=getDummyParticipant();
//            participant.setHttpStatus("404");
//            // throw new NoContactFoundException("Geen persoon gevonden voor het ingegeven adNummer");
//        }
//        else
//        {
//        String adnr = json.substring(json.indexOf("adnr") + 3 + 4 );
//        adnr = adnr.substring(0, adnr.indexOf("\""));
//        if(adnr.length() < 1) { adnr = null; }
//
//        String email = json.substring(json.indexOf("email") + 3 + 5 );
//        email = email.substring(0, email.indexOf("\""));
//        if(email.length() < 1) { email = null; }
//
//        String firstName = json.substring(json.indexOf("first_name") + 3 + 10 );
//        firstName = firstName.substring(0, firstName.indexOf("\""));
//        if(firstName.length() < 1) { firstName = null; }
//
//        String lastName = json.substring(json.indexOf("last_name") + 3 + 9 );
//        lastName = lastName.substring(0, lastName.indexOf("\""));
//        if(lastName.length() < 1) { lastName = null; }
//
//
//        //TODO: check if the conversion from string to date happens correctly
//        Date birthdate = null;
//        String bd = json.substring(json.indexOf("birth_date") + 3 + 10 );
//        bd = bd.substring(0, bd.indexOf("\""));
//        if(bd.length() > 0) {
//            String[] pts = bd.split("-");
//            if (pts.length >= 3 && pts[0].length() > 0 && pts[1].length() > 0 && pts[2].length() > 0) {
//                Integer year = Integer.valueOf(pts[0]);
//                Integer month = Integer.valueOf(pts[1]);
//                Integer day = Integer.valueOf(pts[2]);
//
//                birthdate = new Date(year, month, day);
//            }
//        }
//        //make address
//        String street = json.substring(json.indexOf("street_address") + 3 + 14 );
//        street = street.substring(0, street.indexOf("\""));
//        if(street.length() < 1) { street = null; }
//        String[] parts = street.split(" ");
//        String streetName = parts[0];
//        String houseNumber = parts[1];
//
//        String postalCode = json.substring(json.indexOf("postal_code") + 3 + 11 );
//        postalCode = postalCode.substring(0, postalCode.indexOf("\""));
//        if(postalCode.length() < 1) { postalCode = null; }
//
//        String city = json.substring(json.indexOf("city") + 3 + 4 );
//        city = city.substring(0, city.indexOf("\""));
//        if(city.length() < 1) { city = null; }
//
//        Address address = new Address(streetName, houseNumber, Integer.valueOf(postalCode), city);
//
//        String genderId = json.substring(json.indexOf("gender_id") + 3 + 9 );
//        Gender gender = null;
//        genderId = genderId.substring(0, genderId.indexOf("\""));
//        if(genderId.length() < 1) { genderId = null; }
//        Integer id = Integer.valueOf(genderId);
//        if(id == Gender.MAN.ordinal()) {
//            gender = Gender.MAN;
//        } else if(id == Gender.WOMAN.ordinal()) {
//            gender = Gender.WOMAN;
//        } else if(id == Gender.X.ordinal()) {
//            gender = Gender.X;
//        }
//
//        String phoneNumber = json.substring(json.indexOf("phone") + 3 + 5 );
//        phoneNumber = phoneNumber.substring(0, phoneNumber.indexOf("\""));
//        if(phoneNumber.length() < 1) { phoneNumber = null; }
//
//        //RegistrationParticipant(String adNumber, String email, String firstName, String lastName, java.util.Date birthdate, String stamnumber, Gender gender, EventRole eventRole, Eatinghabbit eatinghabbit, String emailSubscriber) {
//        participant = new RegistrationParticipant(adnr, email, firstName, lastName, birthdate, null, gender, null, null, null);
//            participant.setHttpStatus("200");
//        participant.setAddress(address);
//
//
//        participant.setPhoneNumber(phoneNumber);
//        }
//
//        return participant;
//    }

    private ChiroContact getDummyParticipant()
    {
        ChiroContact chiroContact = new ChiroContact();
        chiroContact.setAdnr("1");
        chiroContact.setEmail("fake@nomail.com");
        chiroContact.setFirstName("not found");
        chiroContact.setLastName("not found");
        chiroContact.setPostalCode(null);
        chiroContact.setBirthDate("");
        chiroContact.setAfdeling(null);
        chiroContact.setFuncties(null);
        chiroContact.setCity(null);
        chiroContact.setCountry(null);
        chiroContact.setGender(Gender.MAN);
        chiroContact.setStreetAddress(null);
        chiroContact.setPhone(null);
        chiroContact.setId(null);
        return chiroContact;
    }

    public ChiroContact getChiroContact(Integer adNummer) throws URISyntaxException, IOException {
        String json = getContact(adNummer);
        ObjectMapper objectMapper = new ObjectMapper();
        ChiroContactMapper chiroContactMapper = new ChiroContactMapper();
        ChiroContactContainer chiroContactsContainer = objectMapper.readValue(json, ChiroContactContainer.class);
        if(Integer.parseInt(chiroContactsContainer.getCount()) < 1) {
            return new HttpChiroContact(getDummyParticipant(), "404");
        } else {
            return new HttpChiroContact(chiroContactMapper.retrieveChiroContact(chiroContactsContainer), "200");
        }
    }
}
