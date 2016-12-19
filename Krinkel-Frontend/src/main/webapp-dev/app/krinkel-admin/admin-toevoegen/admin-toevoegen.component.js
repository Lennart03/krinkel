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
     * @param adNumber Unique identifier given by Chiro.
     */
    searchAndSaveAsAdmin(adNummer) {
        if (!this.isAdmin(adNummer)) {
            let response = this.KrinkelService.getContactFromChiro(adNummer).then((resp) => {
                if((resp[0] !== undefined)){
                    let newAdmin = {
                        firstname: resp[0].first_name,
                        lastname: resp[0].last_name,
                        email: resp[0].email,
                        adNummer: resp[0].adnr
                    };
                    this.admins.push(newAdmin);
                    this.KrinkelService.postAdmin(adNummer);
                } else {
                    this.popup(adNummer);
                }
            });
        } else {
            this.popupAlreadyAdmin();
        }
    }

    popup(adNummer) {
        Materialize.toast('Geen gebruiker gevonden voor ingegeven AD nummer '+ adNummer, 10000, 'red rounded');
    }

    popupAlreadyAdmin() {
        Materialize.toast('Deze persoon heeft al admin rechten.', 10000, 'red rounded');
    }

    isAdmin(adNummer) {
        let isAdmin = false;
        this.admins.forEach(admin => {
            console.log(admin.firstname + " " + admin.adNummer);
            if (admin.adNummer == adNummer) {
                console.log("found the same person");
                isAdmin = true;
            }
        });
        console.log(isAdmin);
        return isAdmin;
    }

    /**
     * Deletes an administator by his/her adnumber.
     * @param adNummer Unique identifier given by Chiro.
     */
    deleteAdmin(adNummer) {
        this.KrinkelService.deleteAdmin(adNummer);
        let newAdmins = [];
        this.admins.forEach(admin => {
            if (admin.adNummer !== adNummer) {
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
