export class KrinkelService {
    constructor($http, BASEURL, $window) {
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;
        this.adNumber="";
    }

    getCasUrl() {
        return this.$http.get(`${this.BASEURL}/api/casurl`).then((resp) => {
            return resp.data;
        });
    }

    logIn(user, password) {
        return this.$http.get(`${this.BASEURL}/api/users?user=${user}&password=${password}`).then((resp) => {
            return resp.data;
        });
    }

    postVolunteer(user) {
    return this.$http.post(`${this.BASEURL}/api/volunteers`, user).then((resp) => {
            return resp;
        },
        () => {
            this.popup();
        }
    );
}

    postParticipant(user) {
        return this.$http.post(`${this.BASEURL}/api/participants`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popup();
            }
        );
    }

    postVolunteerByAdmin(user) {
        return this.$http.post(`${this.BASEURL}/api/volunteers/admin`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popup();
            }
        );
    }

    postParticipantByAdmin(user) {
        return this.$http.post(`${this.BASEURL}/api/participants/admin`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popup();
            }
        );
    }

    getConfirmation(adNumber, token) {
        return this.$http.get(`${this.BASEURL}/api/confirmation?ad=${adNumber}&token=${token}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getColleagues() {
        return this.$http.get(`${this.BASEURL}/api/colleagues`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        )
    }

    getCurrentUserDetails(adNumber) {
        return this.$http.get(this.BASEURL + "/api/user/").then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getVerbonden() {
        return this.$http.get(`${this.BASEURL}/api/units?verbond`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getGewestenForVerbond(verbondNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${verbondNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getParticipantsForUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${stamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getParticipantsOfUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${stamNummer}/participants`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        )
    }

    getVolunteersOfUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${stamNummer}/volunteers`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        )
    }

    getGraphSunInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/sun`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getGraphStatusInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/status`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getGraphLoginInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/uniqueLoginsPerVerbond`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getGraphLoginCurrent() {
        return this.$http.get(`${this.BASEURL}/api/graph/uniqueLoginsPerVerbondLastTwoWeeks`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getContactFromChiro(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/contact/` + adNumber).then((resp) => {
                return resp.data.values;
            },
            () => {
                this.popup();
            }
        );
    }

    getPloegen(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/ploegen/${adNumber}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getContact(adNumber) {
        var promise = this.$http.get(`${this.BASEURL}/api/contact/${adNumber}`).success(function (data, status, headers, config) {
            return data;
        })
            .error(function (data, status, headers, config) {
                    return {"status": false};
                })
        ;
        console.log("output of promise " +promise);
        return promise;
    }



    popup() {
        Materialize.toast('Sessie verlopen, binnen 10 seconden herstart de applicatie', 10000, 'red rounded');
        setTimeout(() => {
            this.$window.location.reload();
        }, 10000);
    }
}

KrinkelService.$inject = ['$http', 'BASEURL', '$window'];
