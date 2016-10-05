export class AuthService {
  constructor() {
  }

  setLoggedinUser(user){
    this.user = user;

    sessionStorage.setItem('user', JSON.stringify(user));
  }

  getLoggedinUser(){
    this.getUserFromLocalStorage();
    return this.user;
  }

  getUserRole(){
    this.getUserFromLocalStorage();
    return this.user.role;
  }

  getRegistrationStatus(){
    this.getUserFromLocalStorage();
    return this.user.subscribed;
  }

  getUserFromLocalStorage(){

    this.user = JSON.parse(sessionStorage.getItem('user'));
  }

  logoutUser(){
    sessionStorage.clear();
  }
}




