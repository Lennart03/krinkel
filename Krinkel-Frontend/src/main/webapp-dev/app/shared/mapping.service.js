export class MapperService {
    constructor() {

    }

    mapVolunteer(data) {
        var volunteer = {
            adNumber: 123, //TODO
            firstName: data.firstName,
            lastName: data.lastName,
            address: {
                street: data.street,
                houseNumber: data.building,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate,
            stamnumber: 123, //TODO
            gender: data.gender.toUpperCase(),
            buddy: data.buddy,
            languages: data.languages,
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phoneNumber,
            campGround: data.campGround.toUpperCase(),

        };

        if (data.rank === 'Leider') {
            volunteer.role = 'LEADER'
        } else if (data.rank === 'Begeleider') {
            //TODO
        } else {
            //TODO
        }

        volunteer.function = {
            preset: this.mapJob(data.job)
        };
        volunteer.preCampList = [];

        //TODO CATCH NULL
        data.preCamp.forEach(d => volunteer.preCampList.push(new Date(d)));
        volunteer.postCampList = [];
        data.postCamp.forEach(d => volunteer.postCampList.push(new Date(d)));

        map



        return volunteer;
    }

    mapJob(job) {
        var upperCasedJob = job.toUpperCase();

        switch (upperCasedJob) {
            case 'AANBOD NATIONALE KAMPGROND': return 'NATIONAL_CAMPGROUND';
                break;
            case 'KAMPGRONDTREKKER': return 'CAMPGROUND';
                break;
            case 'KLINKERREDACTIE': return 'KLINKER_EDITORIAL';
                break;
            case 'KOOKPLOEG': return 'COOCKING';
                break;
            case 'LOGISTIEK (KAMPGROND)': return 'LOGISTICS_CAMPGROUND';
                break;
            case 'LOGISTIEK (NATIONAAL)': return 'LOGISTICS_NATIONAL';
                break;
            case 'LEEFGROEPBEGELEIDING': return 'LIVING_GROUP_GUIDANCE';
                break;
            case 'OTHER': return 'CUSTOM';
                break; // TODO OTHER
        }

        /**
         * NATIONAL_CAMPGROUND, // Aanbod nationale kampgrond
         CAMPGROUND, // Kampgrondtrekker
         KLINKER_EDITORIAL, // Klinkerredactie
         COOCKING, // Kookploeg

         LOGISTICS_CAMPGROUND, // Logistiek (kampgrond)
         LOGISTICS_NATIONAL, // Logistiek (nationaal)

         LIVING_GROUP_GUIDANCE, // Leefgroepbegeleiding
         CUSTOM // I didn't select something from this list but instead defined my own function in the 'other' field.
         */
            }

    mapParticipant(data) {
        var participant = {
            adNumber: 123, //TODO
            email: data.email,
            firstName: data.firstName,
            lastName: data.lastName,
            adress: {
                street: data.street,
                houseNumber: data.building,
                postalCode: data.postalCode,
                city: data.city
            },
            birthdate: data.birthDate, //TODO
            stamnumber: 123, //TODO
            buddy: data.buddy,
            languages: data.languages, //TODO lege array
            eatinghabbit: data.dietary,
            remarksFood: data.dietaryText,
            socialPromotion: data.socialPromotion,
            medicalRemarks: data.medicalText,
            remarks: data.otherText,
            phoneNumber: data.phoneNumber,
        };
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
        } else if (data.rank === 'ASPI') {
            participant.role = 'ASPI';
        }


        return participant;
    }
}

// MapperService.$inject = ['$window'];


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
//     "preset": "KLINKER_EDITORIAL",
//         "other": null
// },
//     "preCampList": [ ],
//     "postCampList": [ ]
// }
