
/**
 * Created by TSOAV51 on 8/12/2016.
 */
class FindByAdController {
    constructor(KrinkelService) {
        this.KrinkelService= KrinkelService;
    }

    findByAdNumber(adNumber) {
        var part;
        this.KrinkelService.getContact(adNumber).then(function(response) {
            part = response.data;
                this.participant = {
                    firstName: part.values[0].firstName
                };
        });
        console.log(part);
    }


    functionCallAfterDOMRender() {
        try {
            Materialize.updateTextFields();
        } catch (exception) {

        }
    }
}

export var FindByAdComponent = {
    template: require('./krinkel-find-member-by-ad.html'),
    controller: FindByAdController
};

FindByAdController.$inject = ['KrinkelService'];

