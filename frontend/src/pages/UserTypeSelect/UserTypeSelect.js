import React from 'react';
import { useNavigate } from 'react-router-dom';
import './UserTypeSelect.css';

const UserTypeSelect = () => {
  const navigate = useNavigate();

  const goToUserType = (type) => {
    // 로그인 화면으로 type 정보를 state로 전달합니다.
    navigate('/login', { state: { type } });
  };

  return (
    <div className="user-type-select-container">
      <h1 className="user-type-select-title">유형을 선택해주세요</h1>
      <button
        className="user-type-select-button"
        onClick={() => goToUserType('performer')}
      >
        🎤 공연자
      </button>
      <button
        className="user-type-select-button"
        onClick={() => goToUserType('viewer')}
      >
        👀 관람자
      </button>
    </div>
  );
};

export default UserTypeSelect;
