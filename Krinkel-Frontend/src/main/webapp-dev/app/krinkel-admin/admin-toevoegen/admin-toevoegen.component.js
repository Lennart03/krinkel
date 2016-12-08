/*@ngInject*/
class AdminToevoegenController {

    constructor(KrinkelService, $location) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.admins = [];
        this.init();
        this.loading = true;
    }

    init() {
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

    searchChiroMember(adnumber) {
        console.log("Hopelijk is dit het ingegeven adnummer: " + adnumber);
        var response = this.KrinkelService.getContactFromChiro(adnumber);
        console.log("Response from Chiro: " + response);
    }

    deleteAdmin(adnumber) {
        console.log("Delete button pressed");
        console.log("Given adnumber: " +adnumber);
        this.KrinkelService.deleteAdmin(adnumber).then(() =>{
            this.$location.path("/adminBeheer");
        });
    }

}

export var AdminToevoegenComponent = {
    template: require('./admin-toevoegen.html'),
    controller: AdminToevoegenController
};
