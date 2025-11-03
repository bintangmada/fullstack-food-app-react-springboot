import axios from "axios";

export default class ApiService {
  static BASE_URL = "http://localhost:8090/api";

  static saveToken(token) {
    localStorage.setItem("token", token);
  }

  static getToken() {
    return localStorage.getItem("token");
  }

  // save role
  static saveRole(roles) {
    localStorage.setItem("roles", JSON.stringify(roles));
  }

  // get roles from local storage
  static getRoles() {
    const roles = localStorage.getItem("roles");
    return roles ? JSON.parse(roles) : null;
  }

  static hadRole(role) {
    const roles = this.getRoles();
    return roles ? roles.includes(role) : false;
  }
}
