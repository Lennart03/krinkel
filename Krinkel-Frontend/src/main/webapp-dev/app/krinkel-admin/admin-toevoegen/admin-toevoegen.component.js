/*@ngInject*/
class AdminToevoegenController {

    constructor(KrinkelService, $location) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
    }

}

export var AdminToevoegenComponent = {
    template: require('./admin-toevoegen.html'),
    controller: AdminToevoegenController
};
