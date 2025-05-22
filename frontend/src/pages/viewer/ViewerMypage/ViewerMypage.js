import React from 'react';
import './ViewerMypage.css';

const mockData = {
  recentHalls: [
    { id: 1, title: '제비다방', image: '/images/performancehall/hall_000.png' },
    { id: 2, title: '무브홀', image: '/images/performancehall/hall_001.png' },
    { id: 3, title: '브이홀', image: '/images/performancehall/hall_002.png' }
  ],
  recentEvents: [
    { id: 4, title: '홍대 축제', image: '/images/performancehall/hall_003.png' },
    { id: 5, title: '야외 버스킹', image: '/images/performancehall/hall_004.png' },
    { id: 6, title: '인디페스트', image: '/images/performancehall/hall_005.png' }
  ],
  likedArtists: [
    { id: 7, title: '시클', image: '/images/performancehall/hall_006.png' },
    { id: 8, title: '비제로', image: '/images/performancehall/hall_007.png' },
    { id: 9, title: '라브레드', image: '/images/performancehall/hall_008.png' }
  ],
  likedConcerts: [
    { id: 10, title: '봄의 소리', image: '/images/performancehall/hall_009.png' },
    { id: 11, title: '여름의 멜로디', image: '/images/performancehall/hall_010.png' },
    { id: 12, title: '겨울의 끝자락', image: '/images/performancehall/hall_011.png' }
  ]
};

const ViewerMypage = () => {
  return (
    <div className="viewer-mypage-container">
      <header className="viewer-header">
        <div className="viewer-profile-circle" />
        <span className="viewer-header-title">계정 정보</span>
        <button className="viewer-edit-btn">수정</button>
      </header>

      <section className="viewer-section">
        <h2 className="viewer-section-title">최근 본 공연장</h2>
        <div className="viewer-card-list">
          {mockData.recentHalls.map(item => (
            <div className="viewer-card" key={item.id}>
              <img src={item.image} alt={item.title} />
              <span>{item.title}</span>
            </div>
          ))}
        </div>
      </section>

      <section className="viewer-section">
        <h2 className="viewer-section-title">최근 본 행사</h2>
        <div className="viewer-card-list">
          {mockData.recentEvents.map(item => (
            <div className="viewer-card" key={item.id}>
              <img src={item.image} alt={item.title} />
              <span>{item.title}</span>
            </div>
          ))}
        </div>
      </section>

      <section className="viewer-section">
        <h2 className="viewer-section-title">찜한 아티스트</h2>
        <div className="viewer-card-list">
          {mockData.likedArtists.map(item => (
            <div className="viewer-card" key={item.id}>
              <img src={item.image} alt={item.title} />
              <span>{item.title}</span>
            </div>
          ))}
        </div>
      </section>

      <section className="viewer-section">
        <h2 className="viewer-section-title">찜한 공연</h2>
        <div className="viewer-card-list">
          {mockData.likedConcerts.map(item => (
            <div className="viewer-card" key={item.id}>
              <img src={item.image} alt={item.title} />
              <span>{item.title}</span>
            </div>
          ))}
        </div>
      </section>

      <section className="viewer-recommendation">
        <p className="viewer-recommend-title">A가 추천하는 공연이에요</p>
        <strong>Sunny Breeze in Busan - 시클 / 비제로</strong>
        <p className="viewer-recommendation-info">
          장소: ~~ <br />
          일시: 2025.04.01 <br />
          가격: 25,000원
        </p>
      </section>
    </div>
  );
};

export default ViewerMypage;
