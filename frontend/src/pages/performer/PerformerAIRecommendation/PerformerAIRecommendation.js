import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './PerformerAIRecommendation.css';

const allRecommendations = [
  {
    id: '1',
    venueName: 'OO공연장',
    tags: ['락', '~150명 규모', '마포구'],
  },
  {
    id: '2',
    venueName: '□ □공연장',
    tags: ['재즈', '~50명 규모', '서대문구'],
  },
  {
    id: '3',
    venueName: '무브홀',
    tags: ['인디', '~200명 규모', '홍대'],
  },
  {
    id: '4',
    venueName: '브이홀',
    tags: ['발라드', '~100명 규모', '강남구'],
  },
  {
    id: '5',
    venueName: '스트레인지프룻',
    tags: ['힙합', '~80명 규모', '이태원'],
  },
  {
    id: '6',
    venueName: '예스24라이브홀',
    tags: ['EDM', '~500명 규모', '광진구'],
  },
  {
    id: '7',
    venueName: '플랫폼창동61',
    tags: ['재즈', '~120명 규모', '도봉구'],
  },
  {
    id: '8',
    venueName: '홍대프리즘홀',
    tags: ['락', '~300명 규모', '마포구'],
  },
  {
    id: '9',
    venueName: '서교동스테이지',
    tags: ['클래식', '~70명 규모', '서대문구'],
  },
  {
    id: '10',
    venueName: '가산디지털홀',
    tags: ['어쿠스틱', '~100명 규모', '금천구'],
  },
];

const getRandomSubset = (array, count) => {
  const shuffled = [...array].sort(() => 0.5 - Math.random());
  return shuffled.slice(0, count);
};

const PerformerAIRecommendation = () => {
  const userName = '홍길동';
  const navigate = useNavigate();
  const [recommendations, setRecommendations] = useState([]);

  useEffect(() => {
    const selected = getRandomSubset(allRecommendations, 3);
    setRecommendations(selected);
  }, []);

  const handleCardClick = (venue) => {
    navigate(`/performer/venues/${venue.id}`);
  };

  return (
    <div className="ai-container">
      <h2 className="ai-title">AI가 {userName}님의 공연을 추천했어요</h2>

      <div className="ai-card-list">
        {recommendations.map((rec, index) => (
          <div
            key={index}
            className="ai-card"
            onClick={() => handleCardClick(rec)}
          >
            <div className="ai-card-header">{rec.venueName}</div>
            <div className="ai-card-body">
              <div className="ai-tag-title">추천 태그 :</div>
              <div className="ai-tag-row">
                {rec.tags.map((tag, i) => (
                  <span key={i} className="ai-tag">{tag}</span>
                ))}
              </div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

export default PerformerAIRecommendation;
