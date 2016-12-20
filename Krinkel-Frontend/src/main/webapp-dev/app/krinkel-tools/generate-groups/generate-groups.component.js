/**
 * Created by MHSBB71 on 15/12/2016.
 */
class GenerateGroupsController {
    constructor(KrinkelService, $location, $log) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.$log = $log;
        this.groups = [];
    }

    $onInit() {}

    generateGroups(groupSize) {
        this.groups = [];
        this.KrinkelService.generateGroups(groupSize).then((resp) => {
            resp.forEach((list) => {
                var group = [];
                list.forEach(person => {
                    var person = {
                        adNumber: person.adNumber,
                        firstName: person.firstName,
                        lastName: person.lastName,
                        gender: person.gender,
                        stamnumber: person.stamnumber
                    };
                    group.push(person);
                });
                this.groups.push(group);
            })
        });

        //this.groups = [[{"adNumber": "0"} , {"adNumber": "1"}], [{"adNumber": "2"}, {"adNumber": "3"}, {"adNumber": "4"}], [{"adNumber": "5"}]];
        this.$log.debug(this.groups);


        /*for(var i=0; i < this.groups.size(); i++) {
            this.$log.debug("test3");
            this.$log.debug(this.groups.get(i));
            this.$log.debug("test4");
        }*/
    }
}

export var GenerateGroupsComponent = {
    template: require('./generate-groups.html'),
    controller: GenerateGroupsController
};

GenerateGroupsComponent.$inject = ['KrinkelService','$location', '$log'];
