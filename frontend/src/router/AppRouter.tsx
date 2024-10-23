import { Navigate, Route, Routes } from "react-router-dom";
import { LoginPage } from "../auth/pages/LoginPage";
import { DashboardPage } from "../dashboard/pages/DashboardPage";
import { useAuthStore } from "@/store/useAuthStore";
import { RegisterPage } from "@/auth/pages/RegisterPage";

export const AppRouter = () => {
  // const logged = isLogin();
  const logged = useAuthStore((state) => state.status);

return (
    <>
    {/* {logged && <Navbar/>} */}
      <Routes>
        {logged === 'authenticated' ? (
          <>
            <Route
              path="/"
              element={<DashboardPage/>}
            />

            <Route
              path="/*"
              element={<Navigate to="/" />}
            />
          </>
        ) : (
          <>
            <Route
              path="/login"
              element={<LoginPage/>}
            />
            <Route
              path="/register"
              element={<RegisterPage/>}
            />
            <Route
              path="/*"
              element={<Navigate to="/login" />}
            />
          </>
        )}
      </Routes>
    </>
  );
    };