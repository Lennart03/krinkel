export class AuthService {
  constructor() {
  }

  setLoggedinUser(user){
    this.user = user;
  }

  getLoggedinUser(){
    return this.user;
  }

  getUserRole(){
    //return this.user.role;
    return 'admin';
  }

  getRegistrationStatus(){
    // return this.user.isRegistrated;
    return false;
  }
}




