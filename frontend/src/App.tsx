import { BrowserRouter, Route, Routes } from "react-router-dom";
import KaKaoLoginPage from "./page/KakaoLoginPage";
import LoginHandler from "./components/login/LoginHandler";
import "./App.css";

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<KaKaoLoginPage />} />
          <Route
            path="/login/oauth/redirect/kakao"
            element={<LoginHandler />}
          />
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
