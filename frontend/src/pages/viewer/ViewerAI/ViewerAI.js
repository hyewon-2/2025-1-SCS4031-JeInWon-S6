import React from 'react';
import { useNavigate } from 'react-router-dom';
import './ViewerAI.css';

const recommendations = [
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
];

const ViewerAI = () => {
  const userName = '홍길동';
  const navigate = useNavigate();

  const handleCardClick = (venue) => {
    navigate(`/viewer/venues/${venue.id}`);
    // 예: navigate(`/venues/${venue.id}`) 등으로 연결 가능
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

export default ViewerAI;
