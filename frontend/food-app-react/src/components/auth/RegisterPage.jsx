import { useNavigate } from "react-router-dom";
import { useError } from "../common/ErrorDisplay";
import { useState } from "react";
const RegisterPage = () => {
  const { ErrorDisplay, showError } = useError();
  const navigate = useNavigate();

  const [formData, setFormData] = useState({
    name: "",
    email: "",
    password: "",
    phoneNumber: "",
    address: "",
    confirmPassword: "",
  });
};

const handleChange = (e) => {
  setFormData({ ...formData, [e.target.name]: e.target.value });
};

const handleSubmit = async (e) => {
  e.preventDefault();

  if (
    !formData.name ||
    !formData.email ||
    !formData.password ||
    !formData.phoneNumber ||
    !formData.confirmPassword ||
    !formData.address
  ) {
    showError("All fields are required");
    return;
  }

  if (formData.password !== formData.confirmPassword) {
    showError("Confirmed password does not match");
    return;
  }
};

export default RegisterPage;
