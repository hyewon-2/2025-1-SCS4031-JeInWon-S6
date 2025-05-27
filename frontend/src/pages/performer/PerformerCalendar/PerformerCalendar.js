import React, { useEffect, useState } from 'react';
import './PerformerCalendar.css';
import { FaChevronLeft, FaChevronRight } from 'react-icons/fa';
import Swal from 'sweetalert2';

const PerformerCalendar = () => {
  const today = new Date();
  const [currentYear, setCurrentYear] = useState(today.getFullYear());
  const [currentMonth, setCurrentMonth] = useState(today.getMonth());
  const [selectedDate, setSelectedDate] = useState(today.toISOString().slice(0, 10));
  const [performerReservations, setPerformerReservations] = useState([]);

  const extractEventDates = (dateRange) => {
    const [startStr, endStr] = dateRange.split('~').map(str => str.trim());
    const start = new Date(startStr.replace(/\./g, '-'));
    const end = new Date(endStr.replace(/\./g, '-'));
    const dates = [];
    let cur = new Date(start);
    while (cur <= end) {
      dates.push(cur.toISOString().split('T')[0]);
      cur.setDate(cur.getDate() + 1);
    }
    return dates;
  };

  const loadReservations = () => {
    const venue = JSON.parse(localStorage.getItem('performerReservations') || '[]');
    const events = JSON.parse(localStorage.getItem('eventReservations') || '[]');

    const venueWithType = venue.map(v => ({
      ...v,
      type: 'venue'
    }));

    const expandedEvents = events.flatMap(ev =>
      extractEventDates(ev.date).map(date => ({
        id: ev.id,
        name: ev.name,
        region: ev.city + ' ' + ev.district,
        size: '축제',
        date: date,
        type: 'event'
      }))
    );

    setPerformerReservations([...venueWithType, ...expandedEvents]);
  };

  useEffect(() => {
    loadReservations();
  }, []);

  const handlePrevMonth = () => {
    if (currentMonth === 0) {
      setCurrentMonth(11);
      setCurrentYear(prev => prev - 1);
    } else {
      setCurrentMonth(prev => prev - 1);
    }
  };

  const handleNextMonth = () => {
    if (currentMonth === 11) {
      setCurrentMonth(0);
      setCurrentYear(prev => prev + 1);
    } else {
      setCurrentMonth(prev => prev + 1);
    }
  };

  const getDaysInMonth = (year, month) => {
    const firstDayOfMonth = new Date(year, month, 1);
    const startDay = firstDayOfMonth.getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    const days = [];

    for (let i = 0; i < startDay; i++) {
      days.push({ day: null, dateString: null, hasReservation: false });
    }

    for (let i = 1; i <= daysInMonth; i++) {
      const dateString = `${year}-${String(month + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;
      const hasReservation = performerReservations.some(ev => ev.date === dateString);
      days.push({ day: i, dateString, hasReservation });
    }

    return days;
  };

  const days = getDaysInMonth(currentYear, currentMonth);
  const filteredEvents = performerReservations.filter(e => e.date === selectedDate);

  const handleCancel = (id, type) => {
    Swal.fire({
      title: '예약을 취소하시겠습니까?',
      icon: 'warning',
      showCancelButton: true,
      confirmButtonColor: '#f85151',
      cancelButtonColor: '#666',
      confirmButtonText: '네, 취소합니다',
      cancelButtonText: '아니오',
      height: '100px',
      width: '290px',
      customClass: {
        title: 'swal2-custom-title'
      }
    }).then((result) => {
      if (result.isConfirmed) {
        if (type === 'venue') {
          const updated = JSON.parse(localStorage.getItem('performerReservations') || '[]')
            .filter(r => r.id !== id);
          localStorage.setItem('performerReservations', JSON.stringify(updated));
        } else if (type === 'event') {
          const updated = JSON.parse(localStorage.getItem('eventReservations') || '[]')
            .filter(r => r.id !== id);
          localStorage.setItem('eventReservations', JSON.stringify(updated));
        }

        loadReservations();

        Swal.fire({
          title: '취소 완료',
          text: '예약이 성공적으로 취소되었습니다.',
          icon: 'success',
          timer: 1500,
          customClass: {
            title: 'swal2-custom-title'
          },
          showConfirmButton: false
        });
      }
    });
  };

  return (
    <div className="calendar-container">
      <div className="calendar-header">
        <FaChevronLeft onClick={handlePrevMonth} style={{ cursor: 'pointer' }} />
        <span className="calendar-month">{currentYear}년 {currentMonth + 1}월</span>
        <FaChevronRight onClick={handleNextMonth} style={{ cursor: 'pointer' }} />
      </div>

      <div className="calendar-days-row">
        {['일', '월', '화', '수', '목', '금', '토'].map((d, i) => (
          <div key={i} className="calendar-day-label">
            {d}
          </div>
        ))}
      </div>

      <div className="calendar-grid">
        {days.map((day, i) => (
          <div
            key={i}
            className="calendar-day"
            onClick={() => day.dateString && setSelectedDate(day.dateString)}
          >
            {day.dateString ? (
              <div className="calendar-day-content">
                <div>{day.day}</div>
                {day.hasReservation && <div className="performer-highlight"></div>}
              </div>
            ) : (
              <div className="empty-day"></div>
            )}
          </div>
        ))}
      </div>

      <div className="calendar-info">
        <span>{selectedDate.replace(/-/g, '.')} ({new Date(selectedDate).toLocaleDateString('ko-KR', { weekday: 'short' })})</span>
        <span>{filteredEvents.length}개의 예약이 있습니다</span>
      </div>

      <div className="calendar-event-list">
        {filteredEvents.map(event => (
          <div key={event.id + event.date} className="performer-calendar-event-card">
            <p className="calendar-event-title">{event.name}</p>
            <p className="calendar-event-sub">지역: {event.region}</p>
            <p className="calendar-event-sub">
              {event.size === '축제' ? '종류: 축제' : `규모: ${event.size}명`}
            </p>
            <button
              className="cancel-button"
              onClick={() => handleCancel(event.id, event.type)}
            >
              예약 취소
            </button>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PerformerCalendar;
