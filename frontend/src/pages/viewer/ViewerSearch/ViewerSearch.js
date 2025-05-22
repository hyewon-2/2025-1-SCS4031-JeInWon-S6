import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { useEffect } from 'react';
import { useLocation } from 'react-router-dom';

import './ViewerSearch.css';
import { FaSearch, FaHeart, FaRegHeart, FaTimes } from 'react-icons/fa';

const artistImages = Array.from({ length: 20 }, (_, i) => `/images/artists/artist_${String(i).padStart(3, '0')}.png`);

const intros = [
  "서울 홍대에서 활동 중인 어쿠스틱 밴드입니다.",
  "일상적인 감정을 노래하는 싱어송라이터입니다.",
  "밴드 결성 3년 차, 다양한 축제에서 공연 중입니다.",
  "몽환적인 분위기의 인디 팝 밴드입니다.",
  "재즈와 힙합을 섞은 독특한 음악을 합니다.",
  "기타와 보컬이 주축인 2인조 밴드입니다.",
  "서정적인 멜로디로 사람들의 마음을 어루만집니다.",
  "락과 EDM을 결합한 퓨전 밴드입니다.",
  "거리 공연으로 시작해 팬층을 쌓아가고 있어요.",
  "강렬한 퍼포먼스와 에너지 넘치는 무대를 선보입니다.",
  "다문화 배경을 가진 멤버들로 구성된 글로벌 밴드입니다.",
  "사운드 클라우드에서 인기를 얻고 있는 신예입니다.",
  "클래식 전공자들이 만든 앰비언트 밴드입니다.",
  "몽환적이고 실험적인 사운드를 시도합니다.",
  "리듬감 있는 드럼이 특징인 팀입니다.",
  "평범한 이야기를 특별하게 풀어내는 팀입니다.",
  "꿈과 희망을 노래하는 10대 밴드입니다.",
  "부산에서 올라온 감성 폭발 밴드입니다.",
  "SNS에서 핫한 스트릿 뮤지션 출신입니다.",
  "뮤직 페스티벌 단골팀으로 활약하고 있습니다."
];

const initialData = Array.from({ length: 20 }, (_, i) => ({
  id: (i + 1).toString(),
  name: `가수${i + 1}`,
  image: artistImages[i],
  intro: intros[i],
  region: '서울',
  size: 5,
}));

const ViewerSearch = () => {
  const navigate = useNavigate();
  const [selectedTab, setSelectedTab] = useState('전체');
  const [favorites, setFavorites] = useState(['2', '5', '10']);
  const [editMode, setEditMode] = useState(false);
  const [search, setSearch] = useState('');

  const toggleFavorite = (id) => {
    const updated = favorites.includes(id)
        ? favorites.filter(fav => fav !== id)
        : [...favorites, id];

      setFavorites(updated);
      localStorage.setItem('viewer-favorites', JSON.stringify(updated));
  };

  const filteredData = initialData.filter(item => {
    const matchSearch = item.name.includes(search);
    const isFavorite = favorites.includes(item.id);
    if (editMode) return matchSearch && isFavorite;
    return selectedTab === '전체' ? matchSearch : (matchSearch && isFavorite);
  });
    const location = useLocation();

    useEffect(() => {
      const stored = JSON.parse(localStorage.getItem('viewer-favorites') || '[]');
      setFavorites(stored);
    }, [location]);
  return (
    <div className="viewer-container">
      <div className="viewer-search-row">
        <FaSearch className="viewer-search-icon" />
        <input
          className="viewer-search-input"
          type="text"
          placeholder="공연자 이름"
          value={search}
          onChange={e => setSearch(e.target.value)}
        />
        <button className="viewer-search-button">검색</button>
      </div>

      <div className="viewer-tab-row">
        <button
          className={`viewer-tab-button ${selectedTab === '전체' && !editMode ? 'viewer-tab-selected' : ''}`}
          onClick={() => { setSelectedTab('전체'); setEditMode(false); }}
        >전체</button>
        <button
          className={`viewer-tab-button ${selectedTab === '찜' && !editMode ? 'viewer-tab-selected' : ''}`}
          onClick={() => { setSelectedTab('찜'); setEditMode(false); }}
        >관심 아티스트</button>
      </div>

      <div className="viewer-list-container">
        {filteredData.map(item => {
          const isFavorite = favorites.includes(item.id);
          return (
            <div
              key={item.id}
              className="viewer-item"
              onClick={() => navigate('/viewer/artist', { state: { artist: item } })}
              style={{ cursor: 'pointer' }}
            >
              {/* 이미지 + 하트 아이콘 겹쳐서 보여줄 부모 */}
              <div className="viewer-image-box">
                <div className="viewer-image-wrapper">
                  <img src={item.image} alt={item.name} className="viewer-profile-image" />
                </div>

                {!editMode && (
                  <button
                    className="viewer-favorite-icon"
                    onClick={(e) => {
                      e.stopPropagation();
                      toggleFavorite(item.id);
                    }}
                  >
                    {isFavorite ? <FaHeart color="red" size={14} /> : <FaRegHeart color="#fff" size={14} />}
                  </button>
                )}

                {editMode && isFavorite && (
                  <button
                    className="viewer-delete-button"
                    onClick={(e) => {
                      e.stopPropagation();
                      toggleFavorite(item.id);
                    }}
                  >
                    <FaTimes size={12} color="#fff" />
                  </button>
                )}
              </div>

              <p className="viewer-artist-name">{item.name}</p>
            </div>
          );
        })}
      </div>
    </div>
  );
};

export default ViewerSearch;
