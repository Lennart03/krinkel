export class MapperService {
    constructor( AuthService, SelectService) {
        this.AuthService = AuthService;
        this.SelectService = SelectService;
    }

    mapVolunteer(data) {
        var volunteer = {
            // adNumber: Math.floor(Math.random() * 100000),
            adNumber: this.AuthService.getLoggedinUser().adnummer,
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: 0,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate,
            stamnumber: data.group,
            buddy: data.buddy,
            language: [], // data.languages
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phone,
            campGround: data.campGround.toUpperCase(),
            email: data.email,
            emailSubscriber: data.emailSubscriber,
            eventRole: 'VOLUNTEER'
        };

        var genderTemp = data.gender.toLowerCase();
        if (genderTemp == '2') {
            volunteer.gender = 'MAN';
        } else if (genderTemp == '1') {
            volunteer.gender = 'WOMAN';
        } else if (genderTemp == '0') {
            volunteer.gender = 'X';
        }

        var mappedJob = this.mapJob(data.job);
        console.log('mappedJob in volunteerMapper');
        console.log(mappedJob);
        if(mappedJob === 'ERROR'){
            console.log('ERROR at mapping service js => job is malformed');
            volunteer.function={
                preset: 'CUSTOM',
                other: 'Iets is misgelopen in de mapping.service.js: de aangeduide functie is incorrect geparst!'
            };
        }
        else if (mappedJob === 'CUSTOM') {
            console.log('MappedJob = custom');
            volunteer.function = {
                preset: 'CUSTOM',
                other: data.jobOther
            };
        } else {
            console.log('MappedJob != custom');
            volunteer.function = {
                preset: mappedJob
            };
        }

        volunteer.preCampList = this.mapPreCampToObject(data.preCamp);
        volunteer.postCampList = this.mapPostCampToObject(data.postCamp);

        // map
        return volunteer;
    }

    mapJob(job) {
        var upperCasedJob = job.toUpperCase();

        switch (upperCasedJob) {
            case 'AANBOD NATIONALE KAMPGROND':
                return 'NATIONAL_CAMPGROUND';
                break;
            case 'KAMPGRONDTREKKER':
                return 'CAMPGROUND';
                break;
            case 'KLINKERREDACTIE':
                return 'KRINKEL_EDITORIAL';
                break;
            case 'KOOKPLOEG':
                return 'COOKING';
                break;
            case 'LOGISTIEK (KAMPGROND)':
                return 'LOGISTICS_CAMPGROUND';
                break;
            case 'LOGISTIEK (NATIONAAL)':
                return 'LOGISTICS_NATIONAL';
                break;
            case 'LEEFGROEPBEGELEIDING':
                return 'LIVING_GROUP_GUIDANCE';
                break;
            case 'OTHER':
                return 'CUSTOM';
                break;
            default :
                return 'ERROR';
                break;
        }

        /**
         * NATIONAL_CAMPGROUND, // Aanbod nationale kampgrond
         CAMPGROUND, // Kampgrondtrekker
         KRINKEL_EDITORIAL, // Krinkelredactie
         COOCKING, // Kookploeg

         LOGISTICS_CAMPGROUND, // Logistiek (kampgrond)
         LOGISTICS_NATIONAL, // Logistiek (nationaal)

         LIVING_GROUP_GUIDANCE, // Leefgroepbegeleiding
         CUSTOM // I didn't select something from this list but instead defined my own function in the 'other' field.
         */
    }

    mapParticipant(data) {
        var participant = {
            // adNumber: Math.floor(Math.random() * 100000), // FOR TESTING
            email: data.email,
            emailSubscriber: data.emailSubscriber,
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: 0,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate,
            stamnumber: data.group,
            buddy: data.buddy,
            // language: data.languages,
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phone
        };


        if (this.SelectService.getColleague() == undefined) {
            participant.adNumber = this.AuthService.getLoggedinUser().adnummer;
        } else {
            participant.adNumber = this.SelectService.getColleague().adnr;
        }
        // if (data.email != this.AuthService.getLoggedinUser().email) {
        //     participant.adNumber = this.SelectService.getColleague().adnr;
        // } else {
        //     participant.adNumber = this.AuthService.getLoggedinUser().adnummer;
        // }

        if (data.buddy) {
            participant.language = data.languages;
        } else {
            participant.language = [];
        }
        var genderTemp = data.gender.toLowerCase();
        if (genderTemp == '2') {
            participant.gender = 'MAN';
        } else if (genderTemp == '1') {
            participant.gender = 'WOMAN';
        } else if (genderTemp == '0') {
            participant.gender = 'X'
        }

        if (data.rank === 'L') {
            participant.eventRole = 'LEADER';
        } else if (data.rank === 'VB') {
            participant.eventRole = 'MENTOR';
        } else if (data.rank === 'A') {
            participant.eventRole = 'ASPI';
        }
        return participant;
    }

    mapPreCampToObject(listOfPreCamp) {
        if (listOfPreCamp !== undefined) {
            var preCamp = {
                '2017-08-19': {
                    id: 2,
                    date: '2017-08-19'
                },
                '2017-08-20': {
                    id: 5,
                    date: '2017-08-19'
                },
                '2017-08-21': {
                    id: 10,
                    date: '2017-08-21'
                },
                '2017-08-22': {
                    id: 20,
                    date: '2017-08-22'
                },
                '2017-08-23': {
                    id: 30,
                    date: '2017-08-23'
                },
                '2017-08-24': {
                    id: 40,
                    date: '2017-08-24'
                }
            };

            var mappedList = [];

            listOfPreCamp.forEach(d => {
                mappedList.push(preCamp[d]);
            });

            return mappedList;
        } else {
            return [];
        }
    }

    mapPostCampToObject(listOfPostCamp) {
        if (listOfPostCamp !== undefined) {
            var postCamp = {
                '2017-08-31': {
                    id: 60,
                    date: '2017-08-31'
                },
                '2017-09-01': {
                    id: 70,
                    date: '2017-09-01'
                },
                '2017-09-02': {
                    id: 80,
                    date: '2017-09-02'
                }
            };

            var mappedList = [];

            listOfPostCamp.forEach(d => {
                mappedList.push(postCamp[d]);
            });
            return mappedList;
        } else {
            return [];
        }

    }

    mapVolunteerFunction(volunteerFunction) {
        switch (volunteerFunction) {
            case 'NATIONAL_CAMPGROUND':
                return 'Nationale kampgrond';
                break;
            case 'CAMPGROUND':
                return 'Kampgrondtrekker';
                break;
            case 'KLINKER_EDITORIAL':
                return 'Krinkelredactie';
                break;
            case 'COOKING':
                return 'Kookploeg';
                break;
            case 'LOGISTICS_CAMPGROUND':
                return 'Logistiek (kampgrond)';
                break;
            case 'LOGISTICS_NATIONAL':
                return 'Logistiek (nationaal)';
                break;
            case 'LIVING_GROUP_GUIDANCE':
                return 'Leefgroepbegeleiding';
                break;
            case 'CUSTOM':
                return 'CUSTOM';
                break;
        }
    }

    mapEatingHabbit(eatingHabbit) {
        switch (eatingHabbit) {
            case 'VEGI':
                return 'Vegetarisch';
                break;
            case 'HALAL':
                return 'Halal';
                break;
            case 'FISHANDMEAT':
                return 'Vlees en vis';
                break;
        }
    }

    mapCampground(campGround) {
        switch (campGround) {
            case 'ANTWERPEN':
                return 'Antwerpen';
                break;
            case 'KEMPEN':
                return 'Kempen';
                break;
            case 'MECHELEN':
                return 'Mechelen';
                break;
            case 'LIMBURG':
                return 'Limburg';
                break;
            case 'LEUVEN':
                return 'Leuven';
                break;
            case 'BRUSSEL':
                return 'Brussel';
                break;
            case 'WEST_VLAANDEREN':
                return 'West-Vlaanderen';
                break;
            case 'HEUVELLAND':
                return 'Heuvelland';
                break;
            case 'ROELAND':
                return 'Roeland';
                break;
            case 'REINAERT':
                return 'Reinaert';
                break;
            case 'NATIONAAL':
                return 'Nationaal';
                break;
            case 'INTERNATIONAAL':
                return 'Internationaal';
                break;
        }
    }

    mapVolunteerByAdmin(data) {
        var volunteer = {
            adNumber: data.adNumber,
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: 0,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate,
            stamnumber: data.group,
            buddy: data.buddy,
            language: [], // data.languages
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phone,
            campGround: data.campGround.toUpperCase(),
            email: data.email,
            emailSubscriber: data.emailSubscriber,
            eventRole: 'VOLUNTEER'
        };

        var genderTemp = data.gender.toLowerCase();
        if (genderTemp == '2') {
            volunteer.gender = 'MAN';
        } else if (genderTemp == '1') {
            volunteer.gender = 'WOMAN';
        } else if (genderTemp == '0') {
            volunteer.gender = 'X';
        }

        var mappedJob = this.mapJob(data.job);

        if (mappedJob === 'CUSTOM') {
            volunteer.function = {
                preset: 'CUSTOM',
                other: data.jobOther
            };
        } else {
            volunteer.function = {
                preset: mappedJob


            };
        }

        volunteer.preCampList = this.mapPreCampToObject(data.preCamp);
        volunteer.postCampList = this.mapPostCampToObject(data.postCamp);

        // map
        return volunteer;
    }

    mapParticipantByAdmin(data) {
        var participant = {
            adNumber: data.adNumber,
            email: data.email,
            emailSubscriber: data.emailSubscriber,
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: 0,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate,
            stamnumber: data.group,
            buddy: data.buddy,
            // language: data.languages,
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phone
        };

        if (data.buddy) {
            participant.language = data.languages;
        } else {
            participant.language = [];
        }
        var genderTemp = data.gender.toLowerCase();
        if (genderTemp == '2') {
            participant.gender = 'MAN';
        } else if (genderTemp == '1') {
            participant.gender = 'WOMAN';
        } else if (genderTemp == '0') {
            participant.gender = 'X'
        }

        if (data.rank === 'L') {
            participant.eventRole = 'LEADER';
        } else if (data.rank === 'VB') {
            participant.eventRole = 'MENTOR';
        } else if (data.rank === 'A') {
            participant.eventRole = 'ASPI';
        }
        return participant;
    }
}

MapperService.$inject = ['AuthService', 'SelectService'];


/**
 * JSON Model
 */
// {
//     "adNumber": "123456789",
//     "firstName": "Aster",
//     "lastName": "Deckers",
//     "adress": {
//     "street": null,
//         "houseNumber": 0,
//         "postalCode": 0,
//         "city": null
// },
//     "birthdate": "1995-05-01",
//     "stamnumber": "AG0001",
//     "gender": "MAN",
//     "eventRole": "LEADER",
//     "buddy": false,
//     "language": [ ],
//     "eatinghabbit": "VEGI",
//     "remarksFood": null,
//     "socialPromotion": false,
//     "medicalRemarks": null,
//     "remarks": null,
//     "phoneNumber": null,
//     "campGround": "ANTWERPEN",
//     "function": {
//     "preset": "KRINKEL_EDITORIAL",
//         "other": null
// },
//     "preCampList": [ ],
//     "postCampList": [ ]
// }
