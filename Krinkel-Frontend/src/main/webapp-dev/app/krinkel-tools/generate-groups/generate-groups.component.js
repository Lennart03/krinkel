/**
 * Created by MHSBB71 on 15/12/2016.
 */
class GenerateGroupsController {
    constructor(KrinkelService, $location, $log) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.$log = $log;
    }

    $onInit() {}

    test() {
        this.$log.debug("test");
        this.groups = this.KrinkelService.generateGroups();
        this.$log.debug("test2");
        this.$log.debug(this.groups);
        for(i=0; i < this.groups.size(); i++) {
            this.$log.debug("test3");
            this.$log.debug(this.groups.get(i));
            this.$log.debug("test4");
        }
    }
}

export var GenerateGroupsComponent = {
    template: require('./generate-groups.html'),
    controller: GenerateGroupsController
};

GenerateGroupsComponent.$inject = ['KrinkelService','$location', '$log'];
