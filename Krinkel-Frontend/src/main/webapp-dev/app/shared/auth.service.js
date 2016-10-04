export class AuthService {
  constructor($http, BASEURL) {
    this.$http = $http;
    this.BASEURL = BASEURL;

  }

  setLoggedinUser(user,password){

    return this.$http.get(`${this.BASEURL}/api/users?user=${user}&password=${password}`).then((resp) => {
      return resp.data;
    });
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




