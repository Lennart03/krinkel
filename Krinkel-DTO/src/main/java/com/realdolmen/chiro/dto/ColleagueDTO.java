package com.realdolmen.chiro.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.realdolmen.chiro.conversion.GenderDeserializer;
import com.realdolmen.chiro.conversion.GenderSerializer;
import com.realdolmen.chiro.domain.Address;
import com.realdolmen.chiro.domain.Gender;

import java.util.Date;
import java.util.List;

public class ColleagueDTO {

    private String adNumber;

    private String firstName;
    private String lastName;

    //afdeling
    @JsonView
    private String division;

    private List<String> functions;

    @JsonSerialize(using = GenderSerializer.class)
    @JsonDeserialize(using = GenderDeserializer.class)
    private Gender gender;

    private Date birthDate;

    private Address address;

    private String phone;

    private String email;



}
