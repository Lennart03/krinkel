/**
 * Created by MHSBB71 on 15/12/2016.
 */
class GenerateGroupsController {
    constructor(KrinkelService, $location, $log) {
        this.KrinkelService = KrinkelService;
        this.$location = $location;
        this.$log = $log;
        this.groups = [];
        this.groupsSize = 0;
    }

    $onInit() {}

    generateGroups(groupSize) {
        this.groupsSize = groupSize;
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

    PrintGroups(elem) {
        this.$log.debug("testing printing")
        var mywindow = window.open('', 'PRINT', 'height=400,width=600');

        mywindow.document.write('<html><head><title>' + "Groepen van " + this.groupsSize  + '</title>');

        mywindow.document.write('</head><body>');
        mywindow.document.write('<h1>' + "Groepen van " + this.groupsSize  + '</h1>');
        mywindow.document.write(document.getElementById(elem).innerHTML);
        this.$log.debug("inner html");
        this.$log.debug(document.getElementById(elem).innerHTML);
        mywindow.document.write('</body></html>');

        mywindow.document.close(); // necessary for IE >= 10
        mywindow.focus(); // necessary for IE >= 10*/

        mywindow.print();
        mywindow.close();

        return true;
    }
}

export var GenerateGroupsComponent = {
    template: require('./generate-groups.html'),
    controller: GenerateGroupsController
};

GenerateGroupsComponent.$inject = ['KrinkelService','$location', '$log'];
