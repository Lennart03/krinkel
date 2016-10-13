export class AuthService {
    constructor($window,KrinkelService) {
        this.$window = $window;
        this.KrinkelService = KrinkelService;

        this.getUserFromStorage();
        console.log("logged in user:");
        console.log(this.getLoggedinUser());

        this.getCurrentUserDetails(this.getLoggedinUser().adnummer).then((resp) => {
            this.userDetails = resp;
        });


    }

    getLoggedinUser() {
        this.getUserFromStorage();
        return this.user;
    }

    getUserRole() {
        this.getUserFromStorage();
        return this.user.role;
    }

    //TODO: make this into a real thing(with rest call)
    getRegistrationStatus() {
        this.getUserFromStorage();
        //return this.user.subscribed;
        return false;
    }

    getUserFromStorage() {
        var jwt = this.getToken('Authorization');
        if (jwt!= undefined && jwt.indexOf('.') !=-1) {
            var payload = jwt.split('.')[1];
            var decodedPayload = JSON.parse(window.atob(payload));
            this.user = decodedPayload;
        }

    }

    logoutUser() {
        //clear sessionstorage and localstorage cuz mathias loves to put junk here
        sessionStorage.clear();
        localStorage.clear();
        //clear the cookies

        var cookies = document.cookie.split(";");

        for (var i = 0; i < cookies.length; i++) {
            var cookie = cookies[i];
            var eqPos = cookie.indexOf("=");
            var name = eqPos > -1 ? cookie.substr(0, eqPos) : cookie;
            document.cookie = name + "=;expires=Thu, 01 Jan 1970 00:00:00 GMT";
        }
        this.$window.location = 'https://login.chiro.be/cas/logout';
    }


    getToken(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) return parts.pop().split(";").shift();
    }

    getCurrentUserDetails(adNumber){
        return this.KrinkelService.getCurrentUserDetails(adNumber).then(resp => {
            this.userDetails = resp;
            return resp;
        })
    }
    getUserDetails() {
        return this.userDetails;
    }
}
AuthService.$inject = ['$window','KrinkelService'];


