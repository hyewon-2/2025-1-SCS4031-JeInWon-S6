import React, { useState, useEffect } from 'react';
import './PerformerHallDetail.css';
import { useNavigate, useParams, useLocation } from 'react-router-dom';
import { FaArrowLeft, FaChevronLeft, FaChevronRight } from 'react-icons/fa';
import Swal from 'sweetalert2';
import fetchVenues from '../../api/venues';

const PerformerHallDetail = () => {
  const today = new Date();
  const [currentYear, setCurrentYear] = useState(today.getFullYear());
  const [currentMonth, setCurrentMonth] = useState(today.getMonth());
  const [selectedDate, setSelectedDate] = useState(null);
  const [selectedTime, setSelectedTime] = useState(null);
  const [isEditing, setIsEditing] = useState(false);
  const [venue, setVenue] = useState(null);
  const [loading, setLoading] = useState(true);

  const navigate = useNavigate();
  const { id } = useParams();
  const location = useLocation();
  const readonly = location.state?.readonly ?? false;
  const from = location.state?.from || (readonly ? '/business/home' : '/performer/home');

  useEffect(() => {
    const loadVenue = async () => {
      const venueList = await fetchVenues();
      const found = venueList.find(v => String(v.id) === id);
      if (found) {
        setVenue(found);
      }
      setLoading(false);
    };
    loadVenue();
  }, [id]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setVenue(prev => ({ ...prev, [name]: value }));
  };

  const handleImageUpload = (e) => {
    const file = e.target.files[0];
    if (!file) return;
    const reader = new FileReader();
    reader.onload = () => {
      const imageDataUrl = reader.result;
      setVenue(prev => ({ ...prev, image: imageDataUrl }));
    };
    reader.readAsDataURL(file);
  };

  const handleSave = () => {
    Swal.fire({ icon: 'success', title: '수정 완료', text: '공연장 정보가 수정되었습니다!' });
    setIsEditing(false);
    // TODO: 추후 백엔드 연동 시 PATCH 요청 추가
  };

  const handlePrevMonth = () => {
    if (currentMonth === 0) {
      setCurrentMonth(11);
      setCurrentYear(prev => prev - 1);
    } else {
      setCurrentMonth(prev => prev - 1);
    }
    setSelectedDate(null);
    setSelectedTime(null);
  };

  const handleNextMonth = () => {
    if (currentMonth === 11) {
      setCurrentMonth(0);
      setCurrentYear(prev => prev + 1);
    } else {
      setCurrentMonth(prev => prev + 1);
    }
    setSelectedDate(null);
    setSelectedTime(null);
  };

  const getDaysInMonth = (year, month) => {
    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();
    const days = [];
    for (let i = 0; i < firstDay; i++) days.push(null);
    for (let i = 1; i <= daysInMonth; i++) {
      const dateStr = `${year}-${String(month + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;
      days.push({ day: i, dateStr });
    }
    return days;
  };

  const availableSlots = {
    '2025-05-24': ['10:00', '14:00', '18:00'],
    '2025-05-28': ['11:00', '15:00'],
    '2025-06-02': ['09:00', '13:00', '17:00'],
    '2025-06-10': ['10:00', '12:00', '16:00'],
  };

  const handleReserve = () => {
    if (!selectedDate || !selectedTime) return;

    const allReservations = JSON.parse(localStorage.getItem('reservations') || '[]');
    const user = JSON.parse(localStorage.getItem('user') || '{}');
    const performerId = user?.id || 'guest';

    const alreadyExists = allReservations.some(
      r =>
        r.performerId === performerId &&
        r.venueName === venue.name &&
        r.date === selectedDate &&
        r.time === selectedTime &&
        r.status !== '취소됨'
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

    Swal.fire({
      title: '지원하시겠습니까?',
      text: `${venue.name} 공연장 - ${selectedDate} ${selectedTime}`,
      icon: 'question',
      showCancelButton: true,
      confirmButtonColor: '#495BFB',
      cancelButtonColor: '#666',
      confirmButtonText: '지원하기',
      cancelButtonText: '취소',
      width: '320px',
      customClass: { title: 'swal2-custom-title' }
    }).then((result) => {
      if (result.isConfirmed) {
        const newReservation = {
          id: Date.now(),
          performerId,
          venueId: String(venue.id),
          venueName: venue.name,
          region: venue.region,
          size: venue.size,
          equipment: venue.equipment,
          date: selectedDate,
          time: selectedTime,
          status: '대기중',
          type: 'venue'
        };
        const updatedReservations = [...allReservations, newReservation];
        localStorage.setItem('reservations', JSON.stringify(updatedReservations));
        localStorage.setItem('reservationRequested', 'true');

        Swal.fire({
          title: '지원 완료',
          html: '해당 공연장에 지원되었습니다.<br/>상태: 대기중',
          icon: 'success',
          timer: 1500,
          showConfirmButton: false,
          customClass: { title: 'swal2-custom-title' }
        }).then(() => {
          navigate('/performer/calendar');
        });
      }
    });
  };

  const days = getDaysInMonth(currentYear, currentMonth);

  if (loading) return <div>로딩 중...</div>;
  if (!venue) return <div>공연장 정보를 찾을 수 없습니다.</div>;

  return (
    <div className="venue-detail-container">
      <button className="back-button" onClick={() => navigate(from)}>
        <FaArrowLeft /> 뒤로가기
      </button>

      <h2 className="venue-title">{venue.name}</h2>
      <div className="venue-image-box responsive-box">
        {isEditing ? (
          <>
            <label htmlFor="venue-image-upload">
              <img src={venue.image} className="venue-image clickable" alt="이미지" />
            </label>
            <input
              id="venue-image-upload"
              type="file"
              accept="image/*"
              onChange={handleImageUpload}
              style={{ display: 'none' }}
            />
          </>
        ) : (
          <img src={venue.image} className="venue-image" alt={venue.name} />
        )}
      </div>

      <div className="venue-info-box responsive-box">
        {isEditing ? (
          <>
            <p>이름: <input name="name" value={venue.name} onChange={handleChange} /></p>
            <p>지역: <input name="region" value={venue.region} onChange={handleChange} /></p>
            <p>규모: <input name="size" value={venue.size} onChange={handleChange} /></p>
            <p>장비: <input name="equipment" value={venue.equipment} onChange={handleChange} /></p>
            <p>연락처: <input name="contact" value={venue.contact} onChange={handleChange} /></p>
            <button className="save-button" onClick={handleSave}>저장</button>
          </>
        ) : (
          <>
            <p>이름: {venue.name}</p>
            <p>지역: {venue.region}</p>
            <p>규모: {venue.size}명</p>
            <p>장비: {venue.equipment}</p>
            <p>연락처: {venue.contact}</p>
            {(() => {
              const links = JSON.parse(localStorage.getItem('businessVenueLinks') || '{}');
              const venueLink = links[venue.id];
              return venueLink ? (
                <p>
                  <a
                    href={venueLink}
                    target="_blank"
                    rel="noopener noreferrer"
                    className="venue-reserve-link"
                  >
                    공식 홈페이지 바로가기
                  </a>
                </p>
              ) : null;
            })()}
            {readonly && <button className="edit-button" onClick={() => setIsEditing(true)}>수정</button>}
          </>
        )}
      </div>

      {!readonly && (
        <>
          <div className="calendar-header">
            <FaChevronLeft onClick={handlePrevMonth} style={{ cursor: 'pointer' }} />
            <span className="calendar-month">{currentYear}년 {currentMonth + 1}월</span>
            <FaChevronRight onClick={handleNextMonth} style={{ cursor: 'pointer' }} />
          </div>

          <div className="calendar-days-row">
            {['일', '월', '화', '수', '목', '금', '토'].map((d, i) => (
              <div key={i} className="calendar-day-label">{d}</div>
            ))}
          </div>

          <div className="calendar-grid">
            {days.map((item, i) => (
              <div
                key={i}
                className={
                  item
                    ? `calendar-day
                        ${availableSlots[item.dateStr] ? 'available' : 'unavailable'}
                        ${selectedDate === item.dateStr ? 'selected' : ''}`
                    : 'calendar-day empty'
                }
                onClick={() => {
                  if (item && availableSlots[item.dateStr]) {
                    setSelectedDate(item.dateStr);
                    setSelectedTime(null);
                  }
                }}
              >
                {item ? item.day : ''}
              </div>
            ))}
          </div>

          {selectedDate && availableSlots[selectedDate] && (
            <>
              <div className="calendar-info">
                <span>
                  {selectedDate.replace(/-/g, '.')} ({new Date(selectedDate).toLocaleDateString('ko-KR', { weekday: 'short' })})
                </span>
              </div>

              <div className="time-grid">
                {availableSlots[selectedDate].map(time => (
                  <button
                    key={time}
                    className={`time-button ${selectedTime === time ? 'selected' : ''}`}
                    onClick={() => setSelectedTime(time)}
                  >
                    {time}
                  </button>
                ))}
              </div>

              <button
                className="reserve-button"
                disabled={!selectedTime}
                onClick={handleReserve}
              >
                지원하기
              </button>
            </>
          )}
        </>
      )}
    </div>
  );
};

export default PerformerHallDetail;