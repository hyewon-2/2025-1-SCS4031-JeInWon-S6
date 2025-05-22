// src/pages/Splash/Splash.js
import React, { useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Splash = () => {
  const navigate = useNavigate();

  useEffect(() => {
    const checkUser = () => {
      try {
        // 개발 중일 경우 사용자 정보 초기화 (선택)
        // localStorage.removeItem('user'); // 주석 해제 시 초기화

        const userData = localStorage.getItem('user');
        if (userData) {
          const { type } = JSON.parse(userData);
          if (type === 'performer') {
            navigate('/performer/home');
          } else if (type === 'viewer') {
            navigate('/viewer/home');
          } else {
            navigate('/select-user');
          }
        } else {
          navigate('/select-user');
        }
      } catch (e) {
        console.error('자동 로그인 오류:', e);
        navigate('/select-user');
      }
    };

    checkUser();
  }, [navigate]);

  return (
    <div style={{ height: '100vh', display: 'flex', justifyContent: 'center', alignItems: 'center' }}>
      <div className="loader" />
    </div>
  );
};

export default Splash;
