import React, { useEffect } from 'react';
import {
  BrowserRouter as Router,
  Routes,
  Route,
  Navigate,
  useNavigate,
} from 'react-router-dom';

// 공통 페이지
import Splash from './pages/Splash/Splash';
import UserTypeSelect from './pages/UserTypeSelect/UserTypeSelect';
import Login from './pages/Login/Login';

// 공연자 페이지
import PerformerSignup from './pages/performer/PerformerSignup/PerformerSignup';
import PerformerHome from './pages/performer/PerformerHome/PerformerHome';
import PerformerSearch from './pages/performer/PerformerSearch/PerformerSearch';
import PerformerHallDetail from './pages/performer/PerformerHallDetail/PerformerHallDetail';
import PerformerAIRecommendation from './pages/performer/PerformerAIRecommendation/PerformerAIRecommendation';
import PerformerCalendar from './pages/performer/PerformerCalendar/PerformerCalendar';
import PerformerMypage from './pages/performer/PerformerMypage/PerformerMypage';

// 관람자 페이지
import ViewerSignup from './pages/viewer/ViewerSignup/ViewerSignup';
import ViewerHome from './pages/viewer/ViewerHome/ViewerHome';
import ViewerSearch from './pages/viewer/ViewerSearch/ViewerSearch';
import ViewerAI from './pages/viewer/ViewerAI/ViewerAI';
import ViewerCalendar from './pages/viewer/ViewerCalendar/ViewerCalendar';
import ViewerMypage from './pages/viewer/ViewerMypage/ViewerMypage';
import ViewerArtistDetail from './pages/viewer/ViewerArtistDetail/ViewerArtistDetail';
// 탭 레이아웃
import PerformerTabLayout from './pages/tabs/PerformerTabLayout';
import ViewerTabLayout from './pages/tabs/ViewerTabLayout';

function InitialRedirect() {
  const navigate = useNavigate();

  useEffect(() => {
    const userType = localStorage.getItem('userType');
    if (userType === 'performer') {
      navigate('/performer/home');
    } else if (userType === 'viewer') {
      navigate('/viewer/home');
    } else {
      navigate('/select-user');
    }
  }, [navigate]);

  return null;
}

function App() {
  return (
    <Router>
      <Routes>
        {/* 초기 진입 */}
        <Route path="/" element={<InitialRedirect />} />
        <Route path="/select-user" element={<UserTypeSelect />} />
        <Route path="/login" element={<Login />} />
        <Route path="/splash" element={<Splash />} />

        {/* 공연자 회원가입 (탭 없이 단독) */}
        <Route path="/performer/signup" element={<PerformerSignup />} />

        {/* 공연자 탭 포함 라우팅 */}
        <Route path="/performer" element={<PerformerTabLayout />}>
          <Route path="home" element={<PerformerHome />} />
          <Route path="search" element={<PerformerSearch />} />
          <Route path="ai" element={<PerformerAIRecommendation />} />
          <Route path="calendar" element={<PerformerCalendar />} />
          <Route path="profile" element={<PerformerMypage />} />
          <Route path="venues/:id" element={<PerformerHallDetail />} />
        </Route>

        {/* 관람자 회원가입 (탭 없이 단독) */}
        <Route path="/viewer/signup" element={<ViewerSignup />} />

        {/* 관람자 탭 포함 라우팅 */}
        <Route path="/viewer" element={<ViewerTabLayout />}>
          <Route path="home" element={<ViewerHome />} />
          <Route path="search" element={<ViewerSearch />} />
          <Route path="ai" element={<ViewerAI />} />
          <Route path="calendar" element={<ViewerCalendar />} />
          <Route path="mypage" element={<ViewerMypage />} />
          <Route path="artist" element={<ViewerArtistDetail />} />
        </Route>

        {/* 잘못된 경로 처리 */}
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
