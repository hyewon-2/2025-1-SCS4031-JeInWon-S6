import React from 'react';
import { useSearchParams, useNavigate } from 'react-router-dom';
import { FaArrowLeft } from 'react-icons/fa';
import Swal from 'sweetalert2';
import './PerformerEventList.css';

const eventImages = [
  '/images/events/event_000.png',
  '/images/events/event_001.png',
  '/images/events/event_002.png',
  '/images/events/event_003.png',
  '/images/events/event_004.png',
];

const eventList = [
  { id: 1, name: '강남 페스티벌', city: '서울', district: '강남구', category: '축제' },
  { id: 2, name: '강남 신한뮤직쇼', city: '서울', district: '강남구', category: '축제' },
  { id: 3, name: '강남 나이트페스티벌', city: '서울', district: '강남구', category: '축제' },
  { id: 4, name: '강남 거리문화축제', city: '서울', district: '강남구', category: '축제' },
  { id: 5, name: '서울재즈페스티벌', city: '서울', district: '송파구', category: '축제', date: '2025.05.28 ~ 2025.05.30' },
  { id: 6, name: '광안리불꽃쇼', city: '부산', district: '수영구', category: '축제', date: '2025.06.20 ~ 2025.06.21' },
  { id: 7, name: '인천뮤직위크', city: '인천', district: '연수구', category: '축제', date: '2025.07.03 ~ 2025.07.05' },
  { id: 8, name: '청춘버스킹페스티벌', city: '서울', district: '마포구', category: '축제', date: '2025.07.07 ~ 2025.07.08' },
  { id: 9, name: '제천국제음악영화제', city: '충북', district: '제천시', category: '축제', date: '2025.08.10 ~ 2025.08.15' },
  { id: 10, name: '홍대거리예술축제', city: '서울', district: '마포구', category: '축제', date: '2025.09.01 ~ 2025.09.02' },
  { id: 11, name: 'DMZ 평화축제', city: '경기', district: '파주시', category: '축제', date: '2025.09.15 ~ 2025.09.16' },
  { id: 12, name: '전주세계소리축제', city: '전북', district: '전주시', category: '축제', date: '2025.10.01 ~ 2025.10.05' },
  { id: 13, name: '부평청소년페스티벌', city: '인천', district: '부평구', category: '축제', date: '2025.10.10 ~ 2025.10.11' },
  { id: 14, name: '강릉재즈페스티벌', city: '강원', district: '강릉시', category: '축제', date: '2025.10.15 ~ 2025.10.16' },
  { id: 15, name: '제주인디페스티벌', city: '제주', district: '제주시', category: '축제', date: '2025.10.20 ~ 2025.10.21' },
  { id: 16, name: '성수힙합페스티벌', city: '서울', district: '성동구', category: '축제', date: '2025.11.01 ~ 2025.11.02' },
  { id: 17, name: '동대문락축제', city: '서울', district: '동대문구', category: '축제', date: '2025.11.05 ~ 2025.11.06' },
  { id: 18, name: '서초가을음악제', city: '서울', district: '서초구', category: '축제', date: '2025.11.10 ~ 2025.11.12' },
  { id: 19, name: '부산인디록데이', city: '부산', district: '해운대구', category: '축제', date: '2025.11.20 ~ 2025.11.22' },
  { id: 20, name: '노원청년페스타', city: '서울', district: '노원구', category: '축제', date: '2025.12.01 ~ 2025.12.02' },
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
