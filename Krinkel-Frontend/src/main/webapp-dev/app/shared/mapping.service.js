export class MapperService {
    constructor(AuthService, SelectService) {
        this.AuthService = AuthService;
        this.SelectService = SelectService;
    }

    mapVolunteer(data) {
        var volunteer = {
            // adNumber: Math.floor(Math.random() * 100000),
            adNumber: this.AuthService.getLoggedinUser().adnummer, //TODO NON-RANDOM
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: data.building,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate,
            stamnumber: Math.floor(Math.random() * 100000), //TODO WHEN CHIRO STOPS BORKING
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
            role: 'VOLUNTEER',

        };


        var genderTemp = data.gender.toLowerCase();
        if (genderTemp === 'man') {
            volunteer.gender = data.gender.toUpperCase();
        } else if (genderTemp === 'vrouw') {
            volunteer.gender = 'WOMAN';
        } else if (genderTemp === 'x') {
            volunteer.gender = data.gender.toUpperCase();
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
                return 'KLINKER_EDITORIAL';
                break;
            case 'KOOKPLOEG':
                return 'COOCKING';
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
                break; // TODO OTHER
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
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: data.building,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate, //TODO
            stamnumber: Math.floor(Math.random() * 100000), //TODO NON-RANDOM
            buddy: data.buddy,
            // language: data.languages,
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phone,
        };

        if (this.SelectService.getSelectedFlag()) {
            participant.adNumber = this.SelectService.getColleague().adnr;
        } else {
            participant.adNumber = this.AuthService.getLoggedinUser().adnummer;
        }


        if (data.buddy) {
            participant.language = data.languages;
        } else {
            participant.language = [];
        }
        console.log("lagnuages:");
        console.log(data.languages);
        var genderTemp = data.gender.toLowerCase();
        if (genderTemp === 'man') {
            participant.gender = data.gender.toUpperCase();
        } else if (genderTemp === 'vrouw') {
            participant.gender = 'WOMAN';
        } else if (genderTemp === 'x') {
            participant.gender = data.gender.toUpperCase();
        }

        if (data.rank === 'Leider') {
            participant.role = 'LEADER';
        } else if (data.rank === 'Begeleider') {
            participant.role = 'MENTOR';
        } else if (data.rank === 'Aspi') {
            participant.role = 'ASPI';
        }

        return participant;
    }


    mapPreCampToObject(listOfPreCamp) {
        if (listOfPreCamp !== undefined) {
            var preCamp = {
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
            case 'COOCKING':
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
                break; // TODO OTHER
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
//     "role": "LEADER",
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
