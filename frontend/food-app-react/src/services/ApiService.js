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

  static isAdmin() {
    return this.hadRole("ADMIN");
  }

  static isCustomer() {
    return this.hadRole("CUSTOMER");
  }

  static isDeliveryPerson() {
    return this.hadRole("DELIVERY");
  }

  static logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("roles");
  }

  static isAuthenticated() {
    const token = this.getToken();
    return !!token;
  }

  static getHeader() {
    const token = this.getToken();
    return {
      Authorization: `Bearer ${token}`,
      "Content-Type": "application/json",
    };
  }
}
