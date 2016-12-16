/*@ngInject*/
class LoadingSpinnerController {
    constructor() {}
}

export var LoadingSpinnerComponent = {
    template: require('./loading-spinner.html'),
    controller: LoadingSpinnerController,
    bindings: {
        loading: "<"
    }
};
