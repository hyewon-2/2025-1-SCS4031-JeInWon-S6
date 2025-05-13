import React, { useEffect } from 'react';
import { BrowserRouter as Router, Routes, Route, useNavigate, Navigate } from 'react-router-dom';

// 공통
import Splash from './pages/Splash/Splash';
import UserTypeSelect from './pages/UserTypeSelect/UserTypeSelect';
import Login from './pages/Login/Login';

// 공연자앱
import PerformerSignup from './pages/performer/PerformerSignup/PerformerSignup';
import PerformerHome from './pages/performer/PerformerHome/PerformerHome';
import PerformerTabLayout from './pages/tabs/PerformerTabLayout';

// 관람자앱
import ViewerSignup from './pages/viewer/ViewerSignup/ViewerSignup';

function InitialRedirect() {
  const navigate = useNavigate();

  useEffect(() => {
    const userType = localStorage.getItem('userType');
    if (userType === 'performer') {
      navigate('/performer/signup');
    } else if (userType === 'viewer') {
      navigate('/viewer/signup');
    } else {
      navigate('/select-user');
    }
  }, [navigate]);

  return null; // 아무것도 렌더링하지 않음
}

function App() {
  return (
    <Router>
      <Routes>
        {/* 처음 진입 시 어떤 경로든 여기로 */}
        <Route path="/" element={<InitialRedirect />} />

        {/* 공통 */}
        <Route path="/select-user" element={<UserTypeSelect />} />
        <Route path="/login" element={<Login />} />
        <Route path="/splash" element={<Splash />} />

        {/* 공연자 */}

        <Route path="/performer/signup" element={<PerformerSignup />} />
        <Route path="/performer/main" element={<PerformerHome />} />
        <Route path="home" element={<PerformerHome />} />

        {/* 관람자 */}
        <Route path="/viewer/signup" element={<ViewerSignup />} />

        <Route
          path="*"
          element={
            window.location.pathname.startsWith('/images/')
              ? null
              : <Navigate to="/select-user" replace />
          }
        />
      </Routes>
    </Router>
  );
}

export default App;
