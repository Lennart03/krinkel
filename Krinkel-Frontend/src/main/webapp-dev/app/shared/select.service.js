export class SelectService {
    constructor() {
        this.selectedFlag = false;
    }

    setColleague(colleague) {
        this.colleague = colleague;
    }

    getColleague() {
        console.log(this.colleague);
        return this.colleague;
    }

    setSelectedFlag(flag) {
        this.selectedFlag = flag;
    }

    getSelectedFlag() {
        return this.selectedFlag;
    }
}




