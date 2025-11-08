import { useNavigate } from "react-router-dom";
import ApiService from "../../services/ApiService";

const Navbar = () => {
  const isAuthenticated = ApiService.isAuthenticated();
  const isAdmin = ApiService.isAdmin();
  const isCustomer = ApiService.isCustomer();
  const isDeliveryPerson = ApiService.isDeliveryPerson();
  const navigate = useNavigate();

  const handleLogout = () => {
    const isLogout = window.confirm("Are you sure want to logout ? ");
    if (isLogout) {
      ApiService.logout();
      navigate("/login");
    }
  };

  return (
    <nav>
      <div className="logo">
        <Link to="/" className="logo-link">
          Food App
        </Link>
      </div>

      <div className="desktop-nav">
        <Link to="/home">Home</Link>
      </div>
    </nav>
  );
};

export default Navbar;
