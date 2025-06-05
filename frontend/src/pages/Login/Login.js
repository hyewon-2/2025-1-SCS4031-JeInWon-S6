import React, { useState } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import './Login.css';

const Login = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const location = useLocation();
  const navigate = useNavigate();

  const type = location.state?.type || '';

  const handleLogin = () => {
    if (!id || !password) {
      alert('아이디와 비밀번호를 모두 입력해주세요.');
      return;
    }

    console.log('로그인 유저 타입:', type);

    const userListKey =
      type === 'performer' ? 'performerUsers'
      : type === 'viewer' ? 'viewerUsers'
      : 'businessUsers';

    const users = JSON.parse(localStorage.getItem(userListKey) || '[]');
    const found = users.find(user => user.id === id && user.password === password);

    if (!found) {
      alert('아이디 또는 비밀번호가 일치하지 않습니다.');
      return;
    }

    // 로그인 성공 → 유저 정보 저장
    const userData = { id: found.id, type };

    if (type === 'business') {
      userData.companyName = found.companyName;
      userData.venueName = found.venueName;
    }

    localStorage.setItem('user', JSON.stringify(userData));

    if (type === 'performer') {
      navigate('/performer/home');
    } else if (type === 'viewer') {
      navigate('/viewer/home');
    } else if (type === 'business') {
      navigate('/business/home');
    } else {
      alert('잘못된 접근입니다.');
    }
  };

  const handleSignupRedirect = () => {
    if (type === 'performer') navigate('/performer/signup');
    else if (type === 'viewer') navigate('/viewer/signup');
    else if (type === 'business') navigate('/business/signup');
  };

  return (
    <div className="login-container">
      <div className="login-wrapper">
        <input
          type="text"
          placeholder="아이디"
          value={id}
          onChange={(e) => setId(e.target.value)}
        />
        <input
          type="password"
          placeholder="비밀번호"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />
        <button className="login-button" onClick={handleLogin}>
          로그인
        </button>
        <p className="signup-link" onClick={handleSignupRedirect}>
          회원이 아니신가요? 회원가입
        </p>
        <p className="forgot-password">
          비밀번호 찾기
        </p>
      </div>
    </div>
  );
};

export default Login;
