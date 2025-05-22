import React, { useState, useEffect } from 'react';
import { useNavigate, useLocation } from 'react-router-dom';
import { AiOutlineArrowLeft, AiFillHeart, AiOutlineHeart } from 'react-icons/ai';
import './ViewerArtistDetail.css';

const mockConcerts = [
  {
    id: '1',
    image: '/images/concerts/concert_000.png',
    location: '홍대 인근',
    date: '2025.04.01',
    price: '25,000원',
  },
  {
    id: '2',
    image: '/images/concerts/concert_001.png',
    location: '합정 클럽',
    date: '2025.04.10',
    price: '30,000원',
  },
];

const ViewerArtistDetail = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { artist } = location.state || {};

  const [liked, setLiked] = useState(false);

  useEffect(() => {
    const storedFavorites = JSON.parse(localStorage.getItem('viewer-favorites') || '[]');
    if (artist && storedFavorites.includes(artist.id)) {
      setLiked(true);
    }
  }, [artist]);

  const handleToggleFavorite = () => {
    const current = JSON.parse(localStorage.getItem('viewer-favorites') || '[]');
    let updated;
    if (liked) {
      updated = current.filter(id => id !== artist.id);
    } else {
      updated = [...current, artist.id];
    }
    localStorage.setItem('viewer-favorites', JSON.stringify(updated));
    setLiked(!liked);
  };

  const renderConcertCard = (concert) => (
    <div key={concert.id} className="viewer-card">
      <img src={concert.image} alt="concert" className="viewer-card-image" />
      <p className="viewer-card-text">장소: {concert.location}</p>
      <p className="viewer-card-text">일시: {concert.date}</p>
      <p className="viewer-card-text">가격: {concert.price}</p>
      <div className="viewer-zzim-btn">
        <p className="viewer-zzim-text">찜하기</p>
      </div>
    </div>
  );

  if (!artist) {
    return <div className="viewer-container">아티스트 정보가 없습니다.</div>;
  }

  return (
    <div className="viewer-container">
      {/* 뒤로가기 버튼 */}
      <div className="viewer-back-button" onClick={() => navigate(-1)}>
        <AiOutlineArrowLeft size={24} color="#fff" />
      </div>

      {/* 아티스트 정보 섹션 */}
      <div className="viewer-profile-section">
        <img
          className="viewer-profile-image"
          src={artist.image || '/images/artists/artist_000.png'}
          alt="artist"
        />
        <div className="viewer-profile-text">
          <p className="viewer-intro-label">소개</p>
          <p className="viewer-intro-text">{artist.intro}</p>
        </div>
      </div>

      {/* 하트 버튼 (찜) */}
      <div onClick={handleToggleFavorite} className="viewer-like-button">
        {liked ? <AiFillHeart size={24} color="red" /> : <AiOutlineHeart size={24} color="red" />}
      </div>

      <h2 className="viewer-artist-name">{artist.name}</h2>
      <p className="viewer-section-title">{artist.name}의 공연</p>

      <div className="viewer-card-container">
        {mockConcerts.map(renderConcertCard)}
      </div>
    </div>
  );
};

export default ViewerArtistDetail;
