// PerformerEventList.jsx
import React from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import Swal from 'sweetalert2';
import './PerformerEventList.css';

const eventImages = [
  '/images/events/event_000.png',
  '/images/events/event_001.png',
  '/images/events/event_002.png',
];

const eventList = [
  { id: 1, name: '강남페스티벌', city: '서울', district: '강남구', category: '축제', date: '2025.04.25 ~ 2025.04.26' },
  { id: 2, name: '영등포불꽃축제', city: '서울', district: '영등포구', category: '축제', date: '2025.05.10 ~ 2025.05.11' },
  { id: 3, name: '부산문화제', city: '부산', district: '중구', category: '축제', date: '2025.06.01 ~ 2025.06.02' },
];

const PerformerEventList = () => {
  const [params] = useSearchParams();
  const city = params.get('city');
  const district = params.get('district');
  const category = params.get('category');
  const navigate = useNavigate();

  const filtered = eventList.filter(evt =>
    (!city || evt.city === city) &&
    (!district || evt.district === district) &&
    (!category || evt.category === category)
  );

  const handleReserve = (evt) => {
    const newReservation = {
      id: evt.id,
      name: evt.name,
      city: evt.city,
      district: evt.district,
      category: evt.category,
      date: evt.date,
    };

    const existing = JSON.parse(localStorage.getItem('eventReservations') || '[]');
    const already = existing.some(r => r.id === evt.id);

    if (already) {
      Swal.fire({
        icon: 'warning',
        title: '이미 예약됨',
        text: '해당 축제는 이미 예약하셨습니다.',
        confirmButtonColor: '#f85151'
      });
      return;
    }

    localStorage.setItem('eventReservations', JSON.stringify([...existing, newReservation]));
    Swal.fire({
      icon: 'success',
      title: '예약 완료!',
      text: `${evt.name} 축제가 예약되었습니다.`,
      confirmButtonColor: '#495BFB'
    });
  };

  return (
    <div className="search-container">
      <button className="back-button" onClick={() => navigate(-1)}>
        <FaArrowLeft size={16} color="white" /> 뒤로가기
      </button>
      <h2 className="section-title">{city} {district}의 {category}</h2>
      <div className="venue-grid">
        {filtered.map((evt) => (
          <div key={evt.id} className="venue-card">
            <img
              src={eventImages[evt.id % eventImages.length]}
              alt={evt.name}
              className="venue-image"
              onClick={() => navigate(`/performer/events/${evt.id}`)}
            />
            <div className="venue-name">{evt.name}</div>
            <div className="venue-subtext">{evt.district} / {evt.category}</div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PerformerEventList;
