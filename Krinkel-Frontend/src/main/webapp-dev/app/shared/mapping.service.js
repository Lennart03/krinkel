export class MapperService {
    constructor() {

    }

    mapVolunteer(data) {
        var volunteer = {
            adNumber: 123, //TODO
            firstName: data.firstName,
            lastName: data.lastName,
            adress: {
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
            preset: data.job.toUpperCase().replace(' ', '_')
        };
        volunteer.preCampList = [];
        data.preCamp.forEach(d => volunteer.preCampList.push(new Date(d)));
        volunteer.postCampList = [];
        data.postCamp.forEach(d => volunteer.postCampList.push(new Date(d)));


        return volunteer;
    }
    mapParticipant(data) {
        var participant = {

        }
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
