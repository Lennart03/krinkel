class GroepenController {
    constructor(KrinkelService, $route, AuthService, $location, $routeParams) {
        this.KrinkelService = KrinkelService;
        this.$route = $route;
        this.AuthService = AuthService;
        this.$location = $location;
        this.gewestNr = $routeParams.gewestNr;
        this.gewestNaam = $routeParams.gewestNaam;
        //console.log(this.lolo + 'groepen.component.js says hi!');
    }

    $onInit() {
        this.user = this.AuthService.getLoggedinUser();
        this.userRole = this.user.role;
        this.userRoles = this.user.roles;
        this.canSeeDeelnemersAantallen = this.checkCanSeeAantallen();
        console.log('===== GROEPEN ====')
        console.log('userRoles');
        console.log(this.userRoles);
        this.KrinkelService.getGroepenList(this.gewestNr).then((results) => {
            //console.log(results);
            this.groepen = results;
            console.log('Ongefilterde groepen');
            console.log(this.groepen);
            if(this.gewestNr !== 'OTHERS'){
                this.filterGroepen();
            } else{
                console.log('Show groepen of OTHERS');
            }
            console.log('Gefilterde groepen');
            console.log(this.groepen);
        });
    }

    /**
     * FILTERING for access control
     */
    checkCanSeeAantallen(){
        if(this.userRole === 'ADMIN'){
            return true;
        }
        for(var i = 0; i < this.userRoles.length; i++){
            // Verbond role kan enkel zijn verbond zien dus hiermee ook aantallen van zijn verbond laten zien!!!
            if(this.userRoles[i].adNumber.substring(0,3) === 'NAT' || this.userRoles[i].role === 'verbond' || this.userRoles[i].role === 'gewest'){
                return true;
            }
        }
        return false;
    }

    filterGroepen(){
        if(this.userRole === 'ADMIN'){
            return;
        }
        var temp = [];
        for(var i = 0; i < this.groepen.length; i++){
            if(this.canSee(this.groepen[i].stamnummer)) {
                temp.push(this.groepen[i]);
            }
        }
        this.groepen = [];
        for(var j = 0; j < temp.length; j++){
            this.groepen.push(temp[j]);
        }
    }

    canSee(groepNr){
        var thiz = this;
        console.log('######### GROEPEN ######')
        console.log('#### cansee('+groepNr+') ####');
        console.log('#########################')
        if( Object.prototype.toString.call( this.userRoles ) === '[object Array]' ){ // array check
            for(var i = 0; i < this.userRoles.length; i++){
                var roleAndAdNumber = this.userRoles[i];
                console.log('* ' + roleAndAdNumber.role + ' : ' + roleAndAdNumber.adNumber);
                if(this.checkForRoleAndAdNumber(roleAndAdNumber, groepNr)){
                    return true;
                }
            }
        }
        return false;
    }

    checkForRoleAndAdNumber(roleAndAdNumber, groepNr){
        var nr = roleAndAdNumber.adNumber;
        // Nr of letters to check => in case of leuven it is 3, otherwise 2
        //      ==> want to check the first letters by which the verbond can be identified
        var nrLettersVerbond = (groepNr.substring(0,3) === 'LEG') ? 3 : 2;
        // Nr of letters to check => in case of leuven it is 5, otherwise 4
        //      ==> want to check the first letters by which the gewest can be identified
        var nrLettersGewest = (groepNr.substring(0,3) === 'LEG') ? 5 : 4;
        switch (roleAndAdNumber.role){
            case 'verbond':
                if(nr.substring(0,nrLettersVerbond) === groepNr.substring(0,nrLettersVerbond)){
                    console.log('### TRUE verbond met nr: ' + nr);
                    return true;
                } else{
                    console.log('### FALSE verbond met nr: ' + nr);
                    return false;
                }
                // return (nr === groepNr) ? true : false;
                break;
            case 'gewest':
                if(nr.substring(0,nrLettersGewest) === groepNr.substring(0,nrLettersGewest)){
                    console.log('### TRUE gewest met nr: ' + nr);
                    return true;
                } else {
                    console.log('### FALSE gewest met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === groepNr.substring(0,nrLetters)) ? true : false;
                break;
            case 'groep':
                if(nr === groepNr){
                //if(nr.substring(0,nrLetters) === groepNr.substring(0,nrLetters) || this.checkForOthers(nr, groepNr)){
                    console.log('### TRUE groep met nr: ' + nr + ' vs ' + groepNr);
                    return true;
                } else {
                    console.log('### FALSE groep met nr: ' + nr);
                    return false;}
                // return (nr.substring(0,nrLetters) === groepNr.substring(0,nrLetters)) ? true : false;
                break;
            default:
                if(nr.substring(0,3) === 'NAT'){
                    console.log('### TRUE Nationaal met nr: ' + nr);
                    return true;
                }
                return false;
        }
        return false;
    }

    checkForOthers(nr, verbondNr){
        if(verbondNr === 'OTHERS') {
            var normalBeginnings = ['AG', 'AJ', 'AM',
                'BG', 'BJ', 'BM', 'INT', 'KG', 'KJ', 'KM', 'LEG', 'LEJ', 'LEM',
                'LG', 'LJ', 'LM', 'MG', 'MJ', 'MM', 'NAT', 'OG', 'OJ', 'OM', 'WG', 'WJ', 'WM'];
            if (!normalBeginnings.includes(nr.substr(0, 2))) {
                return true; // Groepnr is part of others
            }
        }
        return false;
    }

    /**
     * Redirecting
     * @param groepNr
     * @param groepNaam
     */
    redirectToGroep(groepNr, groepNaam) {
        console.log('Tried to redirect to groep with groep: ' + groepNr + ' ' + groepNaam);
        //Check of de persoon in de groep zit
        var canSeeGroep = this.canDirectTo(groepNr);
        if(canSeeGroep) {
            this.$location.path('/groep/' +
                groepNr + '/' + groepNaam);
        } else{
            this.KrinkelService.popupMessage('U heeft niet de machtiging om de lijst van inschrijvingen van deze groep te bekijken.',4000,'red')
            //popupMessage(message, millis, color)
        }
    }

    canDirectTo(groepNr){
        if(groepNr === 'OTHERS'){
            return true;
        }
        var canSeeGroep = this.userRole === 'ADMIN';
        for(var i = 0; i < this.userRoles.length; i++){
            if(this.userRoles[i].adNumber === groepNr){
                canSeeGroep = true;
            }
        }
        return canSeeGroep;
    }

    testblablla(){
        for(var i = 0; i<gewesten.length;i++){console.log("INSERT INTO groepen (groep_naam,groep_stam_nummer,gewest_stam_nummer,gewest_naam,verbond_stam_nummer,verbond_naam) VALUES ('Groep "+gewesten[i].gewest_naam + "','"+gewesten[i].gewest_stam_nummer+"','"+gewesten[i].gewest_stam_nummer + "','"+gewesten[i].gewest_naam+"','"+gewesten[i].verbond_stam_nummer + "','"+gewesten[i].verbond_naam+"');");}
    }
}

export var GroepenComponent = {
    template: require('./groepen.html'),
    controller: GroepenController
};

GroepenComponent.$inject = ['KrinkelService', '$route', 'AuthService', '$location', '$routeParams'];
