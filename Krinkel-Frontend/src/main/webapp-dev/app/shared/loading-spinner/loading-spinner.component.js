/*@ngInject*/
class LoadingSpinnerController {
    constructor() {}
}

export var LoadingSpinnerComponent = {
    template: require('./loading-spinner.html'),
    controller: LoadingSpinnerController,
    bindings: {
        // Should return: false when ready and true otherwise.
        loading: "<"
    }
};
