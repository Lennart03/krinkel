export class StorageService {
    constructor($window) {
        this.$window = $window;
    }

    saveUser(user) {
        localStorage.setItem('savedData', JSON.stringify(user));
    }

    getUser() {
        if (localStorage.getItem('savedData') == null || localStorage.getItem('savedData') == undefined) {
            return;
        } else {
            // TODO: FIX BUG: set preCamp & postCamp to be empty when loaded because the Materialize select can't load it (to avoid confusion)
            return JSON.parse(localStorage.getItem('savedData'));
        }
    }
}

StorageService.$inject = ['$window'];
