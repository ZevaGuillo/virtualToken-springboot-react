import { Navigate, Route, Routes } from "react-router-dom";
import { LoginPage } from "../auth/pages/LoginPage";
import { DashboardPage } from "../dashboard/pages/DashboardPage";
import { useAuthStore } from "@/store/useAuthStore";

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

            {/* <Route
              path="/post/destacados"
              element={<></>}
            /> */}

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
              path="/*"
              element={<Navigate to="/login" />}
            />
          </>
        )}
      </Routes>
    </>
  );
    };