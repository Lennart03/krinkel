export class KrinkelService {
    constructor($http, BASEURL, $window,$filter) {
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;
        this.$filter = $filter;
        this.adNumber = "";
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

    putParticipantToCancelled(participantId) {
        return this.$http.post(`${this.BASEURL}/api/participantCancel?participantId=${participantId}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    updatePayment(participantId, paymentStatus) {
        return this.$http.post(`${this.BASEURL}/api/paymentStatusChange?participantId=${participantId}&paymentStatus=${paymentStatus}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getColleagues(adNumber) {
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

    getVerbondenList() {
        return this.$http.get(`${this.BASEURL}/api/overview?verbonden`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getGewestenList(verbondStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/gewesten/${verbondStamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getGroepenList(gewestStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/groepen/${gewestStamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getParticipantsList(groepStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/groep/${groepStamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    getVolunteersList(groepStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/groep/${groepStamNummer}/vrijwilligers`).then((resp) => {
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


    getGraphLoginInfo(start, end) {
        return this.$http.get(`${this.BASEURL}/api/graph/uniqueLoginsPerVerbond?startDate=`+this.formatDate(start)+`&endDate=`+this.formatDate(end)).then((resp) => {
            return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    formatDate (inDate)
    {
        var outDate = new Date();
        outDate= this.$filter('date')(inDate,"dd/MM/yyyy");
        return outDate;
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
                // var headers = resp.getHeaders();
                // headers.getResponseHeader();

                return resp.data;
            },
            () => {
                this.popup();
            }
        );
    }

    exportCompleteEntryList() {
        return this.$http.get(`${this.BASEURL}/downloadExcel`).then((resp) => {

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
                });

        return promise;
    }

    /**
     * Used to retrieve all the admins in the application
     * @returns {*}
     */
    getAdmins() {
        return this.$http.get(`${this.BASEURL}/api/admins`).then((resp) => {
            return resp.data;
        }, () => {
                this.popup();
            }
        );
    }

    /**
     * Used to retrieve all the super admins in the application
     * */
    getSuperAdmins() {
        return this.$http.get(`${this.BASEURL}/api/superadmins`).then((resp) => {
            return resp.data;
        }, () => {
            this.popup();
        });
    }

    isSuperAdmin(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/superadmins/${adNumber}`).then((resp) => {
            return resp.data;
        });
    }

    /**
     * Requests to give the person admin rights with the given adnumber
     * @param adNumber unique identiefies given by Chiro
     */
    postAdmin(adNumber) {
        return this.$http.post(`${this.BASEURL}/api/admins/${adNumber}`);
    }

    deleteAdmin(adNumber) {
        return this.$http.delete(`${this.BASEURL}/api/admins/${adNumber}`);
    }

    getBasket() {
        return this.$http.get(`${this.BASEURL}/api/basket`).then((resp) => {
            return resp.data;
        });
        //return [{last_name:'Fre', first_name:'De Riek'}];
    }

    addPersonToBasket(person){
        return this.$http.post(`${this.BASEURL}/api/basket`, person).then((resp)=>{
           return resp.data;
        });
    }

    setSubscriberEmailForBasket(emailStr) {
        let email = {email: emailStr};
        return this.$http.post(`${this.BASEURL}/api/basket/mail`, email).then((resp) => {
            return resp.data;
        });
    }

    doPayment() {
        return this.$http.get(`${this.BASEURL}/api/basket/pay`).then((resp) => {
            return resp;
        });
    }

    removePersonFromBasket(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/basket/delete/${adNumber}`).then((resp) => {
            return resp.data;
        });
    }

    generateGroups(groupSize) {
        console.log("generate groups");
        return this.$http.get(`${this.BASEURL}/tools/generate-groups/${groupSize}`).then((resp) => {
            return resp.data;
        });
    }

    popup() {
        Materialize.toast('Sessie verlopen, binnen 10 seconden herstart de applicatie', 10000, 'red rounded');
        setTimeout(() => {
            this.$window.location.reload();
        }, 10000);
    }


    popupForAdmin() {
        Materialize.toast('De deelnemer is ingeschreven', 10000, 'red rounded');
    }
}

KrinkelService.$inject = ['$http', 'BASEURL', '$window', '$filter'];
