import { Navigate, Route, Routes } from "react-router-dom";
import { LoginPage } from "../auth/pages/LoginPage";
import { DashboardPage } from "../dashboard/pages/DashboardPage";

export const AppRouter = () => {
//   const logged = isLogin();

return (
    <>
    {/* {logged && <Navbar/>} */}
      <Routes>
        {false ? (
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