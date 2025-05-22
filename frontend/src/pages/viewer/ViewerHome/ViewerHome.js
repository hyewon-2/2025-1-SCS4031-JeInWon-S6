import React, { useState } from 'react';
import './ViewerHome.css';
import { useNavigate } from 'react-router-dom';
import { FaMusic, FaHome, FaStreetView, FaMedal } from 'react-icons/fa';
import { IoStar, IoStarOutline } from 'react-icons/io5';

const BestReviewSection = () => {
  const reviews = [
    { id: 1, user: 'fdnskajfhk12', title: '공연이름', content: '재밌어요bbbbbbbbbbbb', rating: 4 },
    { id: 2, user: 'fdnskajfhk12', title: '공연이름', content: '재밌어요bbbbbbbbbbbb', rating: 4 },
    { id: 3, user: 'fdnskajfhk12', title: '공연이름', content: '재밌어요bbbbbbbbbbbb', rating: 4 },
  ];

  const medalColors = ['#FFD700', '#C0C0C0', '#CD7F32'];

  return (
    <>
      <div className="viewer-section-header">
        <span className="viewer-section-title">베스트 리뷰</span>
        <button className="viewer-more-text">전체 보기 ➔</button>
      </div>

      <div className="viewer-best-review-section">
        {reviews.map((review, idx) => (
          <div key={review.id} className="viewer-best-review-card">
            <div className="viewer-best-review-top">
              <FaMedal size={18} color={medalColors[idx]} style={{ marginRight: 8 }} />
              <span className="viewer-user-id">{review.user}</span>
              <div className="viewer-star-row">
                {Array.from({ length: 5 }).map((_, i) =>
                  i < review.rating ? (
                    <IoStar key={i} size={14} color="#FFD700" />
                  ) : (
                    <IoStarOutline key={i} size={14} color="#FFD700" />
                  )
                )}
              </div>
            </div>
            <div className="viewer-review-title">{review.title}</div>
            <div className="viewer-review-content">{review.content}</div>
          </div>
        ))}
      </div>
    </>
  );
};

const ViewerHome = () => {
  const navigate = useNavigate();
  const [selectedCity, setSelectedCity] = useState('서울');
  const [selectedDistrict, setSelectedDistrict] = useState('');
  const [selectedCategory, setSelectedCategory] = useState('');
  const [selectedGenre, setSelectedGenre] = useState('락');

  const categories = ['축제', '대관', '무료'];
  const cities = ['서울', '부산', '대구', '인천', '광주', '대전', '울산', '세종', '경기', '강원', '충북', '충남', '전북', '전남', '경북', '경남', '제주'];
  const districtsByCity = {
    서울: [
      '강남구', '강동구', '강북구', '강서구', '관악구', '광진구', '구로구', '금천구', '노원구',
      '도봉구', '동대문구', '동작구', '마포구', '서대문구', '서초구', '성동구', '성북구',
      '송파구', '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구',
    ],
  };

  const handleLogout = () => {
    localStorage.removeItem('user');
    navigate('/select-user');
  };

  const handleCategoryPress = (category) => {
    setSelectedCategory(category);
    if (selectedCity && selectedDistrict) {
      const pageParams = { city: selectedCity, district: selectedDistrict };
      if (category === '축제') navigate('/viewer/festival', { state: pageParams });
      else if (category === '대관') navigate('/viewer/rental', { state: pageParams });
      else if (category === '무료') navigate('/viewer/busking', { state: pageParams });
    } else {
      alert('지역(시/구)을 모두 선택해주세요.');
    }
  };

  return (
    <div className="viewer-home-container">
      <div className="viewer-header">
        <button className="viewer-logout-btn" onClick={handleLogout}>
          <img src="/images/icons/logout.png" alt="로그아웃" className="viewer-logout-icon" />
        </button>
      </div>

      <div className="viewer-banner-card">
        <img className="viewer-banner-image" src="/images/homeviewer/visual_00.png" alt="배너" />
        <div className="viewer-banner-overlay">
          <div className="viewer-banner-main-title">OVGD 7주년 특별공연</div>
          <div className="viewer-banner-sub-title">크리스탈 티 · ddbb · 밴드기린 단 하루의 라이브!</div>
          <div className="viewer-banner-button">
            <span className="viewer-banner-button-text">자세히 보기</span>
          </div>
        </div>
      </div>

      <section className="viewer-category-section">
        <h3 className="viewer-section-title category-title">🎯 맞춤 공연장 찾기</h3>
        <div className="viewer-category-grid">
          {categories.map((cat) => {
            const isSelected = selectedCategory === cat;
            const icon = cat === '축제' ? <FaMusic /> : cat === '대관' ? <FaHome /> : <FaStreetView />;
            return (
              <button
                key={cat}
                className={`viewer-category-card ${isSelected ? 'active' : ''}`}
                onClick={() => handleCategoryPress(cat)}
              >
                <div className="viewer-category-icon">{icon}</div>
                <div className="viewer-category-label">{cat}</div>
              </button>
            );
          })}
        </div>
      </section>

      <section className="viewer-location-section">
        <div className="viewer-city-scroll">
          {cities.map(city => (
            <button
              key={city}
              className={`viewer-city-btn ${selectedCity === city ? 'selected' : ''}`}
              onClick={() => {
                setSelectedCity(city);
                setSelectedDistrict('');
              }}>
              {city}
            </button>
          ))}
        </div>
        <div className="viewer-district-scroll-wrapper">
          {Array.from({ length: Math.ceil((districtsByCity[selectedCity] || []).length / 8) }, (_, i) => {
            const pageItems = districtsByCity[selectedCity].slice(i * 8, (i + 1) * 8);
            const rows = [pageItems.slice(0, 4), pageItems.slice(4, 8)];

            return (
              <div className="viewer-district-page" key={i}>
                {rows.map((row, rowIndex) => (
                  <div className="viewer-district-row" key={rowIndex}>
                    {row.map(district => (
                      <button
                        key={district}
                        className={`viewer-district-btn ${selectedDistrict === district ? 'selected' : ''}`}
                        onClick={() => setSelectedDistrict(district)}
                      >
                        {district}
                      </button>
                    ))}
                  </div>
                ))}
              </div>
            );
          })}
        </div>
      </section>

      <section className="viewer-ranking-section">
        <div className="viewer-section-header">
          <span className="viewer-section-title">이달의 랭킹</span>
          <button className="viewer-more-text">전체 보기 ➔</button>
        </div>
        <div className="viewer-genre-filter">
          {['락', '인디', '재즈'].map((genre) => (
            <button
              key={genre}
              className={`viewer-genre-btn ${selectedGenre === genre ? 'active' : ''}`}
              onClick={() => setSelectedGenre(genre)}
            >
              {genre}
            </button>
          ))}
        </div>
        <div className="viewer-ranking-cards">
          {[1, 2, 3].map((item, idx) => (
            <div key={idx} className="viewer-ranking-card">
              <div className="viewer-ranking-img">{item}</div>
              <div className="viewer-ranking-pick">찜하기</div>
            </div>
          ))}
        </div>
      </section>

      <section className="viewer-popular-section">
        <div className="viewer-section-header">
          <span className="viewer-section-title">지금 인기있는 공연 리스트</span>
          <button className="viewer-more-text">전체 보기 ➔</button>
        </div>
        <div className="viewer-popular-cards">
          {[1, 2, 3].map((item, idx) => (
            <div key={idx} className="viewer-popular-card">
              <div className="viewer-popular-img-placeholder">{item}</div>
              <div className="viewer-popular-pick">찜하기</div>
            </div>
          ))}
        </div>
      </section>

      <section className="viewer-recommend-section">
        <h3 className="viewer-section-title recommend-main-title">추천 공연장</h3>
        <p className="viewer-recommend-sub">○○님 취향 저격! 공연장</p>
        <div className="viewer-venue-scroll">
          {[{
            img: '/images/home/recom_00.png', title: 'musinsa garage'
          }, {
            img: '/images/home/recom_01.png', title: '제비다방'
          }, {
            img: '/images/home/recom_02.png', title: 'A.O.R'
          }].map((venue, idx) => (
            <div key={idx} className="viewer-venue-card">
              <img src={venue.img} alt={venue.title} className="viewer-venue-image" />
              <p className="viewer-venue-name">{venue.title}</p>
            </div>
          ))}
        </div>
      </section>

      <BestReviewSection />
    </div>
  );
};

export default ViewerHome;
