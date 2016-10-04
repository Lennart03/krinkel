class RegisterParticipantController {

    constructor($log) {
        this.$log = $log;
    }

        addPerson(newPerson) {
        var person = {
            name: {
                first: newPerson.firstName,
                last: newPerson.lastName
            },
            email: newPerson.email
        };
        this.$log.debug(person);

        newPerson.firstName = '';
        newPerson.lastName = '';
        newPerson.email = '';
        newPerson.birthdate = '';
        newPerson.street = '';
        newPerson.building = '';
        newPerson.postalCode = '';
        newPerson.city = '';
        newPerson.phone = '';
        newPerson.email = '';
        newPerson.gender = '';
        newPerson = {};
        this.addForm.$setPristine();


    }


}


export var RegisterParticipantComponent= {
    template: require('./register-participant.html'),
    controller: RegisterParticipantController
}