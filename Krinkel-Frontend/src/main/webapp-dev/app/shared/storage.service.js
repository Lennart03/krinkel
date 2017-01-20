export class StorageService {
    constructor($window) {
        this.$window = $window;
    }

    saveUser(user) {
        localStorage.setItem('savedData', JSON.stringify(user));
    }

    getUser() {
        if (localStorage.getItem('savedData') === 'undefined'|| localStorage.getItem('savedData') == null ) {
            return;
        } else {
            return JSON.parse(localStorage.getItem('savedData'));
        }
    }

    removeUser(){
        localStorage.removeItem('savedData');
    }
}

StorageService.$inject = ['$window'];
