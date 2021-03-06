export class AuthService {
    constructor($window, KrinkelService, $timeout, $http, BASEURL) {
        this.$window = $window;
        this.KrinkelService = KrinkelService;
        this.$timeout = $timeout;
        this.$http = $http;
        this.BASEURL = BASEURL;

        this.getUserFromStorage();

        this.userDetailsCallMade = false;
        this.userDetailsCallReturned = false;
        this.userDetailsPromiseWhenCallHasntReturnedYet = undefined;


        this.getUserDetails().then(function (resp) {
            // Initialize userdetails because we need them instantly.
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

    getUserFromStorage() {
        var jwt = this.getToken('Authorization');
        if (jwt != undefined && jwt.indexOf('.') != -1) {
            var payload = jwt.split('.')[1];
            var decodedPayload = JSON.parse(window.atob(payload));
            this.user = decodedPayload;
        }
    }

    logoutUser() {
        this.$http.get(`${this.BASEURL}/api/logout`).then((resp) => {
            if (resp.status !== 200) {
                return;
            }
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
            // The service parameter is not recognized by the Chiro CAS.
            // This feature is probably deactivated.
            this.$window.location = 'https://login.chiro.be/cas/logout?service=https://www.krinkel.be';
        });
    }

    getToken(name) {
        var value = "; " + document.cookie;
        var parts = value.split("; " + name + "=");
        if (parts.length == 2) {
            return parts.pop()
                .split(";")
                .shift();
        }
    }

    getCurrentUserDetails(adNumber) {
        return this.KrinkelService.getCurrentUserDetails(adNumber).then(resp => {
            this.userDetails = resp;
            return resp;
        });
    }

    /**
     * This function returns the user details. The call to the server only happens ONCE, after it resolves it'll return cached data to reduce load.
     * @returns {*}
     */
    getUserDetails() {
        if (this.userDetailsCallMade) {
            if (this.userDetailsCallReturned) {
                return new Promise((resolve, reject) => {
                    resolve(this.userDetails);
                });
            } else {
                return this.userDetailsPromiseWhenCallHasntReturnedYet;
            }
        } else {
            this.userDetailsCallMade = true;
            this.userDetailsPromiseWhenCallHasntReturnedYet = this.getCurrentUserDetails(this.getLoggedinUser().adnummer).then((resp) => {
                this.userDetails = resp;
                return new Promise((resolve, reject) => {
                    this.userDetailsCallReturned = true;
                    resolve(resp);
                });
            });
            return this.userDetailsPromiseWhenCallHasntReturnedYet;
        }
    }
}

AuthService.$inject = ['$window', 'KrinkelService', '$timeout', '$http', 'BASEURL'];


