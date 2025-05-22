import React from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './PerformerHallDetail.css';
import { FaArrowLeft } from 'react-icons/fa';

const venueList = [
  {
    id: '1',
    name: 'OO공연장',
    image: '/images/performancehall/hall_000.png',
    region: '마포구',
    size: 150,
    equipment: '기본 음향/조명',
    contact: '010-1234-5678',
  },
  {
    id: '2',
    name: '□ □공연장',
    image: '/images/performancehall/hall_001.png',
    region: '서대문구',
    size: 50,
    equipment: '기본 음향/조명',
    contact: '010-5678-1234',
  },
];

const PerformerHallDetail = () => {
  const navigate = useNavigate();
  const { id } = useParams(); // ✅ 컴포넌트 내부에서 호출

  const venue = venueList.find(v => String(v.id) === id);

  if (!venue) {
    return <div className="venue-detail-container">공연장 정보가 없습니다.</div>;
  }

  return (
    <div className="venue-detail-container">
      <button className="back-button" onClick={() => navigate(-1)}>
        <FaArrowLeft color="#fff" size={16} /> 뒤로가기
      </button>

      <h2 className="venue-title">{venue.name}</h2>

      <div className="venue-image-box responsive-box">
        <img src={venue.image} alt={venue.name} className="venue-image" />
      </div>

      <div className="venue-info-box responsive-box">
        <p>이름: {venue.name}</p>
        <p>지역: {venue.region}</p>
        <p>규모: {venue.size}명</p>
        <p>장비: {venue.equipment}</p>
        <p>연락처: {venue.contact}</p>
      </div>
    </div>
  );
};

export default PerformerHallDetail;
