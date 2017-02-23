package com.realdolmen.chiro.controller;

import com.realdolmen.chiro.domain.RegistrationParticipant;
import com.realdolmen.chiro.service.GenerateGroupsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by MHSBB71 on 15/12/2016.
 */
@Controller
public class GenerateGroupsController {
    @Autowired
    GenerateGroupsService generateGroupsService;

    @RequestMapping(value = "/tools/generate-groups/{groupSize}/{option}", produces = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.GET)
    @ResponseBody
    public List<List<RegistrationParticipant>> generateGroups(@PathVariable String groupSize, @PathVariable String option) {
        return generateGroupsService.generateRandomGroups(Integer.valueOf(groupSize), Integer.valueOf(option));
    }

    public static void generateTestData () {
        String repl_email = "@example.com";
        String repl_firstName = "firstName";
        String repl_lastName = "lastName";
        String repl_gender;

        Integer repl_stamNumber;
        Integer repl_UnionNumber;

        for(int i = 0; i < 100; i++) {
            String repl_adNumber = " " + i + i + i + i + i + i + i;
            repl_stamNumber = (i % 12 + 1);
            if((int)(Math.random() * 2) == 0) {
                repl_gender = "MAN";
            } else {
                repl_gender = "WOMAN";
            }

            int age = 2000 - (int)(Math.random() * 50);
            String repl_birthDate = age + "-" + "01-01";

            System.out.println("INSERT INTO registration_participant (dtype,id,ad_number,city, house_number, postal_code, street, birthdate,social_promotion, eatinghabbit, email, email_subscriber, first_name, gender, last_name, remarks_food, medical_remarks, remarks,event_role, buddy, stamnumber, status, phone_number, camp_ground, other, preset, registered_by) VALUES('RegistrationParticipant', '" + 500+i + "', '" + repl_adNumber + "', 'Brussel', '123', '1000', 'Veldstraat', '" + repl_birthDate + "', false, 'FISHANDMEAT'," +
                            "'" + i + repl_email + "', '" + repl_email + "', '" + repl_firstName + i + "', '" + repl_gender + "', '" + repl_lastName + i + "', 'food remarks', 'medical remarks', 'remarks', 'ASPI', false, '" + repl_stamNumber + "', 'TO_BE_PAID', 1, '', '', '', '" + repl_adNumber + "');"

            );
        }

        for(int i = 1; i <= 12; i++) {
            repl_stamNumber = i;
            repl_UnionNumber = 100+i;
            System.out.println("INSERT INTO groepen VALUES ('Webbekom','" + repl_stamNumber + "','LEG/0400','Hageland','" + repl_UnionNumber + "','Leuven');");
        }
    }

    public static void main(String [] args) {
        generateTestData();
    }
}