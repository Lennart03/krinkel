export class KrinkelService {
    constructor($http, BASEURL, $window,$filter) {
        this.$http = $http;
        this.BASEURL = BASEURL;
        this.$window = $window;
        this.$filter = $filter;
        this.adNumber = "";
        this.helpEmail = 'inschrijvingen@krinkel.be';

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
        console.log('Starting to post volunteer at camground: ' + user.campGround);
        console.log('user');
        console.log(user);
        if(user.campGround === 'WEST-VLAANDEREN'){
            user.campGround = 'WESTVLAANDEREN';
            console.log('New campground = ' + user.campGround);
        }

        return this.$http.post(`${this.BASEURL}/api/volunteers`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popupMessage('Fout KR01: Er is iets misgelopen binnen de applicatie terwijl u zich probeerde in te ' +
                    'schrijven als vrijwilliger. Gelieve contact op te nemen via '+this.helpEmail+' en te vermelden ' +
                    'bij welke kampgrond u zich probeerde in te schrijven. (Deze pop-up verdwijnt na 30 seconden)', 30000, 'red');
            }
        );
    }

    postParticipant(user) {
        return this.$http.post(`${this.BASEURL}/api/participants`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popupMessage('Fout KR02: Er is Er is iets misgelopen binnen de applicatie terwijl u zich probeerde ' +
                    'in te schrijven als deelnemer. Gelieve contact op te nemen via '+this.helpEmail+' en te ' +
                    'vermelden welke gegevens u heeft ingevuld in het inschrijvingsformulier. (Deze pop-up verdwijnt ' +
                    'na 30 seconden)', 30000, 'red');
            }
        );
    }

    postVolunteerByAdmin(user) {
        console.log('Starting to post volunteer at camground: ' + user.campGround);
        console.log('user');
        console.log(user);
        if(user.campGround === 'WEST-VLAANDEREN'){
            user.campGround = 'WESTVLAANDEREN';
            console.log('New campground = ' + user.campGround);
        }
        return this.$http.post(`${this.BASEURL}/api/volunteers/admin`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popupMessage('Fout KR03: Er is iets misgelopen binnen de applicatie terwijl we probeerden de persoon' +
                    ' in te schrijven als vrijwilliger. Gelieve contact op te nemen met Tom en te vermelden bij welke ' +
                    'kampgrond u de persoon probeerde in te schrijven. (Deze pop-up verdwijnt na 30 seconden)', 30000, 'red');
            }
        );
    }

    postParticipantByAdmin(user) {
        return this.$http.post(`${this.BASEURL}/api/participants/admin`, user).then((resp) => {
                return resp;
            },
            () => {
                this.popupMessage('Fout KR04: Er is iets misgelopen binnen de applicatie terwijl we probeerden de persoon ' +
                    'in te schrijven als deelnemer. Gelieve contact op te nemen met Tom. (Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getConfirmation(adNumber, token) {
        return this.$http.get(`${this.BASEURL}/api/confirmation?ad=${adNumber}&token=${token}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR05: Er is iets misgelopen bij het ophalen van uw bevestigingslink. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR05.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    putParticipantToCancelled(participantId) {
        return this.$http.post(`${this.BASEURL}/api/participantCancel?participantId=${participantId}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR06: Er is iets misgelopen bij het annuleren van de inschrijving. Gelieve ' +
                    'contact op te nemen met Tom met deze foutcode KR06.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    updatePayment(participantId, paymentStatus) {
        return this.$http.post(`${this.BASEURL}/api/paymentStatusChange?participantId=${participantId}&paymentStatus=${paymentStatus}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR07: Er is iets misgelopen bij het updaten van de betaalstatus. Gelieve ' +
                    'contact op te nemen met Tom met deze foutcode KR07.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    resendConfirmationEmails(participants) {
        return this.$http.post(`${this.BASEURL}/api/participantsResendConfirmationEmails?participants=${participants}`).then((resp) => {
                return resp.data;
            }, () => {
                console.log('Fout bij het zenden van mails met bevestiginslinks naar de gebruikers: ');
                console.log(participants);
                this.popupMessage('Fout KR08: bij het zenden van mails met bevestiginslinks.',10000,'red');
            }
        );
    }

    getParticipantsListAll(){
        return this.$http.get(`${this.BASEURL}/api/participants/all`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR09: Er is iets misgelopen bij het ophalen van alle ingeschreven deelnemers. getParticipantsListAll', 10000, 'red');
            }
        );
    }

    getColleagues(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/colleagues`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR10: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR10 en het AD nummer '+adNumber+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        )
    }

    getCurrentUserDetails(adNumber) {
        return this.$http.get(this.BASEURL + "/api/user/").then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR11: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR11 en het AD nummer '+adNumber+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getVerbonden() {
        return this.$http.get(`${this.BASEURL}/api/units?verbond`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR12: Er is iets misgelopen bij het ophalen van de verbonden. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR12.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getVerbondenList() {
        return this.$http.get(`${this.BASEURL}/api/overview?verbonden`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR13: Er is iets misgelopen bij het ophalen van de verbonden. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR13.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getGewestenList(verbondStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/gewesten/${verbondStamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR14: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR14 en het verbond stamnummer '+verbondStamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getGroepenList(gewestStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/groepen/${gewestStamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR15: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR15 en het gewest stamnummer '+verbondStamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getParticipantsList(groepStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/groep/${groepStamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR16: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR16 en het groep stamnummer '+groepStamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getVolunteersList(groepStamNummer) {
        return this.$http.get(`${this.BASEURL}/api/overview/groep/${groepStamNummer}/vrijwilligers`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR17: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR17 en het groep stamnummer '+verbondStamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getVolunteersListByCampground(campground) {
        return this.$http.get(`${this.BASEURL}/api/overview/campground/${campground}/vrijwilligers`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR18: Er is iets misgelopen bij het inladen van vrijwilligers van kamgrond ' + campground + '' +
                    'Gelieve contact op te nemen via '+this.helpEmail+' met deze foutcode KR18 en het groep stamnummer '+
                    verbondStamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 seconden)', 30000, 'red');
            }
        );
    }

    getParticipantsForUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${stamNummer}`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR19: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR19 en het stamnummer '+stamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getParticipantsOfUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${stamNummer}/participants`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR20: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR20 en het stamnummer '+stamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        )
    }

    getVolunteersOfUnit(stamNummer) {
        return this.$http.get(`${this.BASEURL}/api/units/${stamNummer}/volunteers`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR21: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR21 en het stamnummer '+stamNummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        )
    }

    getGraphSunInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/sun`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR22: Er is iets misgelopen bij het ophalen van gegevens voor de "sun-grafiek". Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR22.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getGraphStatusInfo() {
        return this.$http.get(`${this.BASEURL}/api/graph/status`).then((resp) => {
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR23: Er is iets misgelopen bij het ophalen van gegevens voor de grafieken. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR23.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }


    getGraphLoginInfo(start, end) {
        return this.$http.get(`${this.BASEURL}/api/graph/uniqueLoginsPerVerbond?startDate=`+this.formatDate(start)+`&endDate=`+this.formatDate(end)).then((resp) => {
            return resp.data;
            },
            () => {
                this.popupMessage('Fout KR24: Er is iets misgelopen bij het ophalen van gegevens voor de grafiek met logins. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR24.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
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
                this.popupMessage('Fout KR25: Er is iets misgelopen bij het ophalen van een contact van de chiro. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR25.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
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
                this.popupMessage('Fout KR26: Er is iets misgelopen bij het ophalen van gegevens van de ploegen van een persoon. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR26 en het AD nummer '+adNumber+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getChiroGroepGewestVerbondByGroepStamNummer(groepstamnummer, index){
        // console.log('getChiroGroepGewestVerbondByGroepStamNummer inside krinkel service');
        return this.$http.get(`${this.BASEURL}/api/overview/groepstamnummer/${groepstamnummer}`).then((resp) => {
                // console.log('RESP.DATA getRawChiroUnitByGroepStamNummer');
                // console.log(resp.data);
                resp.data.index = index;
                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR27: Er is iets misgelopen bij ophalen van verbond en gewest op basis van groep stamnummer: '
                    + groepstamnummer+' Gelieve contact op te nemen via '+this.helpEmail+' met deze foutcode KR27 en het groep stamnummer '+groepstamnummer+' te vermelden.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    exportCompleteEntryList() {
        return this.$http.get(`${this.BASEURL}/downloadExcel`).then((resp) => {

                return resp.data;
            },
            () => {
                this.popupMessage('Fout KR28: Er is iets misgelopen bij het downloaden van de gevraagde excel. Gelieve ' +
                    'contact op te nemen via '+this.helpEmail+' met deze foutcode KR28.(Deze pop-up verdwijnt na 30 ' +
                    'seconden)', 30000, 'red');
            }
        );
    }

    getContact(adNumber) {
        var promise = this.$http.get(`${this.BASEURL}/api/contact/${adNumber}`).success(function (data, status, headers, config) {
                return data;
            })
                .error(function (data, status, headers, config) {
                    this.popupMessage('Fout KR29: Er is iets misgelopen bij het ophalen van een contact. Gelieve ' +
                        'contact op te nemen via '+this.helpEmail+' met deze foutcode KR29 en het AD nummer '+adNumber+' te vermelden' +
                        '.(Deze pop-up verdwijnt na 30 seconden)', 30000, 'red');
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
            this.popupMessage('Fout KR30: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR30.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
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
            this.popupMessage('Fout KR31: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR31.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
        });
    }

    isSuperAdmin(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/superadmins/${adNumber}`).then((resp) => {
            return resp.data;
        }, () => {
            this.popupMessage('Fout KR32: Er is iets misgelopen bij het ophalen van gegevens. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR32.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
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
        }, () => {
            this.popupMessage('Fout KR33: Er is iets misgelopen bij het doorsturen van gegevens. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR33.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
        });
    }

    setSubscriberEmailForBasket(emailStr) {
        let email = {email: emailStr};
        return this.$http.post(`${this.BASEURL}/api/basket/mail`, email).then((resp) => {
            return resp.data;
        }, () => {
            this.popupMessage('Fout KR34: Er is iets misgelopen bij het doorsturen van gegevens. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR34.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
        });
    }

    doPayment() {
        return this.$http.get(`${this.BASEURL}/api/basket/pay`).then((resp) => {
            return resp;
        }, () => {
            this.popupMessage('Fout KR35: Er is iets misgelopen bij het opstarten van de betaling. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR35.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
        });
    }

    removePersonFromBasket(adNumber) {
        return this.$http.get(`${this.BASEURL}/api/basket/delete/${adNumber}`).then((resp) => {
            return resp.data;
        }, () =>{
            this.popupMessage('Fout KR36: Er is iets misgelopen bij het verwijderen van een persoon uit het winkelmandje. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR36.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
        });
    }

    generateGroups(groupSize, option) {
        console.log("generate groups");
        return this.$http.get(`${this.BASEURL}/tools/generate-groups/${groupSize}/${option}`).then((resp) => {
            return resp.data;
        }, () => {
            this.popupMessage('Fout KR37: Er is iets misgelopen bij het genereren van de groepen. Gelieve ' +
                'contact op te nemen via '+this.helpEmail+' met deze foutcode KR37.(Deze pop-up verdwijnt na 30 ' +
                'seconden)', 30000, 'red');
        });
    }

    popup() {
        Materialize.toast('Sessie verlopen, binnen 10 seconden herstart de applicatie', 10000, 'red rounded');
        setTimeout(() => {
            this.$window.location.reload();
        }, 10000);
    }

    popupMessage(message, millis, color) {
            Materialize.toast(message, millis, '' + color + ' rounded');
    }


    popupForAdmin() {
        Materialize.toast('De deelnemer is ingeschreven', 10000, 'green rounded');
    }
}

KrinkelService.$inject = ['$http', 'BASEURL', '$window', '$filter'];
