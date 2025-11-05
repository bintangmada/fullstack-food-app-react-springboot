import { Navigate, useLocation } from "react-router-dom";
import ApiService from "../../../../../../../../Downloads/food-ordering-app/food-react/src/services/ApiService";

export const CustomerRoute = ({ element: Component }) => {
  const location = useLocation();
  return ApiService.isCustomer() ? (
    Component
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};

export const AdminRoute = ({ element: Component }) => {
  const location = useLocation();
  return ApiService.isAdmin() ? (
    Component
  ) : (
    <Navigate to="/login" replace state={{ from: location }} />
  );
};
