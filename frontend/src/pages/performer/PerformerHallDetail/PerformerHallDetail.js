import React, { useState } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import './PerformerHallDetail.css';
import { FaArrowLeft } from 'react-icons/fa';
import Swal from 'sweetalert2';

const availableDates = [
  '2025-05-24',
  '2025-05-28',
  '2025-06-02',
  '2025-06-10'
];

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
  const [selectedDate, setSelectedDate] = useState(null);
  const navigate = useNavigate();
  const { id } = useParams();
  const location = useLocation();

  const venue = location.state?.venue || venueList.find(v => String(v.id) === id);

  const handleReserve = () => {
    const newReservation = {
      id: Date.now(),
      date: selectedDate,
      name: venue.name,
      region: venue.region,
      size: venue.size,
    };

    const existing = JSON.parse(localStorage.getItem('performerReservations') || '[]');

    const alreadyExists = existing.some(
      r => r.date === selectedDate && r.name === venue.name
    );

    if (alreadyExists) {
      Swal.fire({
        icon: 'error',
        title: '이미 예약됨',
        html: '해당 날짜에 이 공연장은<br/>이미 예약되어 있습니다.',
        confirmButtonColor: '#f85151'
      });
      return;
    }

    // ✅ SweetAlert2 확인창
    Swal.fire({
      title: '예약을 확정하시겠습니까?',
      text: `${venue.name} 공연장 - ${selectedDate}`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#F6859A',
      cancelButtonColor: '#666',
      confirmButtonText: '네, 예약합니다',
      cancelButtonText: '아니오',
      width: '320px',
      customClass: {
        title: 'swal2-custom-title'
      }
    }).then((result) => {
      if (result.isConfirmed) {
        const updated = [...existing, newReservation];
        localStorage.setItem('performerReservations', JSON.stringify(updated));

        Swal.fire({
          title: '예약 완료',
          text: '정상적으로 예약이 처리되었습니다.',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false,
          customClass: {
            title: 'swal2-custom-title'
          }
        });
      }
    });
  };


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

      <div className="hall-detail-container">
        <h3>대관 가능 날짜</h3>
        <div className="date-grid">
          {availableDates.map(date => (
            <button
              key={date}
              className={`date-button ${selectedDate === date ? 'selected' : ''}`}
              onClick={() => setSelectedDate(date)}
            >
              {new Date(date).toLocaleDateString('ko-KR', {
                month: 'long',
                day: 'numeric',
                weekday: 'short',
              })}
            </button>
          ))}
        </div>

        <button
          className="reserve-button"
          disabled={!selectedDate}
          onClick={handleReserve}
        >
          예약하기
        </button>
      </div>
    </div>
  );
};

export default PerformerHallDetail;
