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

export default RegisterPage;
