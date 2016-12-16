/*@ngInject*/
class AdminToevoegenController {

    constructor(KrinkelService) {
        this.KrinkelService = KrinkelService;
        this.admins = [];
        this.init();

    }

    init() {
        this.loading = true;
        this.KrinkelService.getAdmins().then((resp) => {
            resp.forEach(admin => {
                var admin = {
                    firstname: admin.firstname,
                    lastname: admin.lastname,
                    adNummer: admin.adNummer,
                    email: admin.email
                };
                this.admins.push(admin);
            });
        });
        this.loading = false;
    }

    /**
     * Searches a Chiro member in the API of Chiro and makes the member an administator for the Krinkelsite.
     * The call should return an Array with one Object in it, the searched person.
     * The Object in the array is then converted into an admin and saved in the frontend and backend.
     * @param adnumber Unique identifier given by Chiro.
     */
    searchAndSaveAsAdmin(adnumber) {
        var response = this.KrinkelService.getContactFromChiro(adnumber).then((resp) => {
            var newAdmin = {
                firstname: resp[0].first_name,
                lastname: resp[0].last_name,
                email: resp[0].email,
                adNummer: resp[0].adnr
            };
            this.admins.push(newAdmin);
            this.KrinkelService.postAdmin(adnumber);
        });

    }

    /**
     * Deletes an administator by his/her adnumber.
     * @param adNummer Unique identifier given by Chiro.
     */
    deleteAdmin(adNummer) {
        this.KrinkelService.deleteAdmin(adNummer);
        var newAdmins = [];
        this.admins.forEach(admin => {
            if(admin.adNummer !== adNummer){
                newAdmins.push(admin);
            }
        });
        this.admins = newAdmins;
    }

}

export var AdminToevoegenComponent = {
    template: require('./admin-toevoegen.html'),
    controller: AdminToevoegenController
};

// AdminToevoegenController.$inject = ['KrinkelService', '$location'];
