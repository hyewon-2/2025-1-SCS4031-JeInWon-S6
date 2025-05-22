import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import './BottomTabLayout.css';

const ViewerTabLayout = () => {
  const location = useLocation();

  return (
    <div className="page-container">
      <div className="main-content">
        <Outlet />
      </div>
      <nav className="bottom-tab">
        <Link to="/viewer/home" className={location.pathname.includes('/viewer/home') ? 'active' : ''}>
          <img src="/images/bottomtab/home.png" alt="홈" />
          <span>홈</span>
        </Link>
        <Link to="/viewer/search" className={location.pathname.includes('/viewer/search') ? 'active' : ''}>
          <img src="/images/bottomtab/search.png" alt="정보모음" />
          <span>정보모음</span>
        </Link>
        <Link to="/viewer/ai" className={location.pathname.includes('/viewer/ai') ? 'active' : ''}>
          <img src="/images/bottomtab/ai.png" alt="AI추천" />
          <span>AI추천</span>
        </Link>
        <Link to="/viewer/calendar" className={location.pathname.includes('/viewer/calendar') ? 'active' : ''}>
          <img src="/images/bottomtab/calendar.png" alt="캘린더" />
          <span>캘린더</span>
        </Link>
        <Link to="/viewer/mypage" className={location.pathname.includes('/viewer/mypage') ? 'active' : ''}>
          <img src="/images/bottomtab/union.png" alt="마이페이지" />
          <span>마이페이지</span>
        </Link>
      </nav>
    </div>
  );
};

export default ViewerTabLayout;
