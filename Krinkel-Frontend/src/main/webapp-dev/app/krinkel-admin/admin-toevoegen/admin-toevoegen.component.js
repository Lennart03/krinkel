/*@ngInject*/
class AdminToevoegenController {

    constructor(KrinkelService, AuthService) {
        this.KrinkelService = KrinkelService;
        this.AuthService = AuthService;
        this.admins = [];
        this.superAdmins = [];
    }

    $onInit() {
        this.isLoading = true;
        this.KrinkelService.getAdmins().then((resp) => {
            resp.forEach((admin) => {
                var admin = {
                    firstname: admin.firstname,
                    lastname: admin.lastname,
                    adNumber: admin.adNumber,
                    email: admin.email
                };
                this.admins.push(admin);
            });
        });
        this.KrinkelService.getSuperAdmins().then((resp) => {
            resp.forEach((adNumber)  => {
                this.superAdmins.push(adNumber);
            });
            this.isLoading = false;
        });
    }

    /**
     * Searches a Chiro member in the API of Chiro and makes the member an administator for the Krinkelsite.
     * The call should return an Array with one Object in it, the searched person.
     * The Object in the array is then converted into an admin and saved in the frontend and backend.
     * @param adNumber Unique identifier given by Chiro.
     */
    searchAndSaveAsAdmin(adNumber) {
        this.isLoading = true;
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
                    this.isLoading = false;
                } else {
                    this.isLoading = false;
                    this.popup(adNumber);
                }
            });
        } else {
            this.isLoading = false;
            this.popupAlreadyAdmin();
        }

    }

    popup(adNumber) {
        Materialize.toast('Geen gebruiker gevonden voor ingegeven AD nummer '+ adNumber, 5000, 'red rounded');
    }

    popupAlreadyAdmin() {
        Materialize.toast('Deze persoon heeft al admin rechten.', 5000, 'red rounded');
    }

    isSuperAdmin(adNumber) {
        let isSuperAdmin = false;
        this.superAdmins.forEach((admin) => {
            if(admin ==  adNumber){
                isSuperAdmin = true;
            }
        });
        return isSuperAdmin;
    }

    isAdmin(adNumber) {
        this.admins.forEach(function(admin) {
            if (admin.adNumber == adNumber) {
                    return true;
            }
        });
        return false;
    }

    /**
     * Deletes an administator by his/her adnumber.
     * @param adNumber Unique identifier given by Chiro.
     */
    deleteAdmin(adNumber) {
        this.isLoading = true;
        this.KrinkelService.deleteAdmin(adNumber);
        let newAdmins = [];
        this.admins.forEach(admin => {
            if (admin.adNumber !== adNumber) {
                newAdmins.push(admin);
            }
        });
        this.admins = newAdmins;
        this.isLoading = false;
    }

}

export var AdminToevoegenComponent = {
    template: require('./admin-toevoegen.html'),
    controller: AdminToevoegenController
};

// AdminToevoegenController.$inject = ['KrinkelService', '$location'];
