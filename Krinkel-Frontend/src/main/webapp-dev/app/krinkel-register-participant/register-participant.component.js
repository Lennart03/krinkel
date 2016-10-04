class RegisterParticipantController {

    constructor() {
        /*var currentTime = new Date();
        this.currentTime = currentTime;
        this.month = ['January', 'February', 'March', 'April', 'May', 'June', 'July', 'August', 'September', 'October', 'November', 'December'];
        this.monthShort = ['Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'];
        this.weekdaysFull = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        this.weekdaysLetter = ['S', 'M', 'T', 'W', 'T', 'F', 'S'];
        this.disable = [false, 1, 7];
        this.today = 'Today';
        this.clear = 'Clear';
        this.close = 'Close';
        var days = 15;
        this.minDate = (new Date(this.currentTime.getTime() - (1000 * 60 * 60 * 24 * days))).toISOString();
        this.maxDate = (new Date(this.currentTime.getTime() + (1000 * 60 * 60 * 24 * days))).toISOString();
        this.onStart = function () {
            console.log('onStart');
        };
        this.onRender = function () {
            console.log('onRender');
        };
        this.onOpen = function () {
            console.log('onOpen');
        };
        this.onClose = function () {
            console.log('onClose');
        };
        this.onSet = function () {
            console.log('onSet');
        };
        this.onStop = function () {
            console.log('onStop');
        };
*/
        angular.element('.datepicker').pickadate({
            selectMonths: true, // Creates a dropdown to control month
            selectYears: 15 // Creates a dropdown of 15 years to control year
        });
    }
    $onInit() {
        angular.element('.modal-trigger').leanModal();

    }


}


export var RegisterParticipantComponent = {
    template: require('./register-participant.html'),
    controller: RegisterParticipantController
}