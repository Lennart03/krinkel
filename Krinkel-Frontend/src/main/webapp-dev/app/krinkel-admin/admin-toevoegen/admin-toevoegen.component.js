/*@ngInject*/
class AdminToevoegenController {

    constructor(KrinkelService, AuthService) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
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
                    adNumber: admin.adNumber,
                    email: admin.email
                };
                this.admins.push(admin);
            });
            this.loading = false;
        });

    }

    /**
     * Searches a Chiro member in the API of Chiro and makes the member an administator for the Krinkelsite.
     * The call should return an Array with one Object in it, the searched person.
     * The Object in the array is then converted into an admin and saved in the frontend and backend.
     * @param adNumber Unique identifier given by Chiro.
     */
    searchAndSaveAsAdmin(adNumber) {
        this.loading = true;
        if (!this.isAdmin(adNumber)) {
            let response = this.KrinkelService.getContactFromChiro(adNumber).then((resp) => {
                if((resp[0] !== undefined)){
                    let newAdmin = {
                        firstname: resp[0].first_name,
                        lastname: resp[0].last_name,
                        email: resp[0].email,
                        adNumber: resp[0].adnr
                    };
                    this.admins.push(newAdmin);
                    this.KrinkelService.postAdmin(adNumber);
                    this.loading = false;
                } else {
                    this.loading = false;
                    this.popup(adNumber);
                }
            });
        } else {
            this.loading = false;
            this.popupAlreadyAdmin();
        }

    }

    popup(adNumber) {
        Materialize.toast('Geen gebruiker gevonden voor ingegeven AD nummer '+ adNumber, 10000, 'red rounded');
    }

    popupAlreadyAdmin() {
        Materialize.toast('Deze persoon heeft al admin rechten.', 10000, 'red rounded');
    }

    isCurrentUser(adNumber) {
        let user = this.AuthService.getLoggedinUser();
        return (user.adnummer == adNumber);
    }

    isAdmin(adNumber) {
        let isAdmin = false;
        this.admins.forEach(admin => {
            if (admin.adNumber == adNumber) {
                isAdmin = true;
            }
        });
        return isAdmin;
    }

    /**
     * Deletes an administator by his/her adnumber.
     * @param adNumber Unique identifier given by Chiro.
     */
    deleteAdmin(adNumber) {
        this.loading = true;
        this.KrinkelService.deleteAdmin(adNumber);
        let newAdmins = [];
        this.admins.forEach(admin => {
            if (admin.adNumber !== adNumber) {
                newAdmins.push(admin);
            }
        });
        this.admins = newAdmins;
        this.loading = false;
    }

}

export var AdminToevoegenComponent = {
    template: require('./admin-toevoegen.html'),
    controller: AdminToevoegenController
};

// AdminToevoegenController.$inject = ['KrinkelService', '$location'];
