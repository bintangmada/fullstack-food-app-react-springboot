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

  // register user
  static async registerUser(registrationData) {
    const response = await axios.post(
      `${this.BASE_URL}/auth/register`,
      registrationData
    );
    return response.data;
  }

  // login user
  static async loginUser(loginData) {
    const response = await axios(`${this.BASE_URL}/auth/login`, loginData);
    return response.data;
  }

  // USER

  // get profile for this user
  static async myProfile() {
    const response = await axios.get(`${this.BASE_URL}/users/account`, {
      headers: this.getHeader(),
    });
    return response.data;
  }

  static async updateProfile(formData) {
    const response = await axios.put(
      `${this.BASE_URL}/users/update`,
      formData,
      {
        headers: {
          ...this.getHeader(),
          "Content-Type": "multipart/form-data",
        },
      }
    );
    return response.data;
  }

  static async deacticeProfile() {
    const response = await axios.delete(`${this.BASE_URL}/users/deactive`, {
      headers: this.getHeader(),
    });

    return response.data;
  }
}
