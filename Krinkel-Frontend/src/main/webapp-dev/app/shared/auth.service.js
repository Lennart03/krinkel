export class AuthService {
  constructor() {
  }

  setLoggedinUser(user){
    this.user = user;
    localStorage.setItem('user', JSON.stringify(user));
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
    this.user = JSON.parse(localStorage.getItem('user'));
  }

  logoutUser(){
    localStorage.clear();
  }
}




