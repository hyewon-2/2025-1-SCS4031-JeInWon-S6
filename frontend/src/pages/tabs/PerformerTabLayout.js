import React from 'react';
import { Outlet, Link, useLocation } from 'react-router-dom';
import './BottomTabLayout.css';

const PerformerTabLayout = () => {
  const location = useLocation();

  return (
    <div className="page-container">
      <div className="main-content">
        <Outlet />
      </div>
      <nav className="bottom-tab">
        <Link to="/performer/home" className={location.pathname.includes('/performer/home') ? 'active' : ''}>
          <img src="/images/bottomtab/home.png" alt="홈" />
          <span>홈</span>
        </Link>
        <Link to="/performer/search" className={location.pathname.includes('/performer/search') ? 'active' : ''}>
          <img src="/images/bottomtab/search.png" alt="정보모음" />
          <span>정보모음</span>
        </Link>
        <Link to="/performer/ai" className={location.pathname.includes('/performer/ai') ? 'active' : ''}>
          <img src="/images/bottomtab/ai.png" alt="AI추천" />
          <span>AI추천</span>
        </Link>
        <Link to="/performer/calendar" className={location.pathname.includes('/performer/calendar') ? 'active' : ''}>
          <img src="/images/bottomtab/calendar.png" alt="캘린더" />
          <span>캘린더</span>
        </Link>
        <Link to="/performer/profile" className={location.pathname.includes('/performer/profile') ? 'active' : ''}>
          <img src="/images/bottomtab/union.png" alt="마이페이지" />
          <span>마이페이지</span>
        </Link>
      </nav>
    </div>
  );
};

export default PerformerTabLayout;
