import React, { useState } from 'react';
import './Signup.css'; // 스타일 분리된 CSS import

const genreOptions = ['락', '재즈', '힙합', '발라드', '기타'];

const regionOptions = {
  서울: ['강남구', '강동구', '강북구', '강서구', '관악구', '광진구', '구로구', '금천구', '노원구', '도봉구', '동대문구', '동작구', '마포구', '서대문구', '서초구', '성동구', '성북구', '송파구', '양천구', '영등포구', '용산구', '은평구', '종로구', '중구', '중랑구'],
  부산: [], 대구: [], 인천: [], 광주: [], 대전: [], 울산: [], 세종: [],
  경기: [], 강원도: [], 충청북도: [], 충청남도: [],
  전라북도: [], 전라남도: [], 경상북도: [], 경상남도: [], 제주도: [],
};

const Signup = () => {
  const [id, setId] = useState('');
  const [password, setPassword] = useState('');
  const [confirmPassword, setConfirmPassword] = useState('');
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [customGenre, setCustomGenre] = useState('');
  const [selectedRegion, setSelectedRegion] = useState('서울');
  const [selectedDistricts, setSelectedDistricts] = useState([]);
  const [bandMembers, setBandMembers] = useState('');

  const toggleGenre = (genre) => {
    setSelectedGenres((prev) =>
      prev.includes(genre) ? prev.filter((g) => g !== genre) : [...prev, genre]
    );
  };

  const toggleDistrict = (district) => {
    setSelectedDistricts((prev) =>
      prev.includes(district) ? prev.filter((d) => d !== district) : [...prev, district]
    );
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (
      !id ||
      !password ||
      password !== confirmPassword ||
      selectedGenres.length === 0 ||
      selectedDistricts.length === 0 ||
      !bandMembers
    ) {
      alert('모든 항목을 정확히 입력해주세요.');
      return;
    }

    const userData = {
      id,
      password,
      genres: selectedGenres.includes('기타')
        ? [...selectedGenres.filter((g) => g !== '기타'), customGenre]
        : selectedGenres,
      region: {
        city: selectedRegion,
        districts: selectedDistricts,
      },
      bandMembers,
    };

    localStorage.setItem('user', JSON.stringify(userData));
    console.log('유저 정보 저장 완료:', userData);
    alert('회원가입 성공!');
  };

  return (
    <div className="signup-container">
      <h2>회원가입</h2>
      <form onSubmit={handleSubmit}>
        <input className="signup-input" placeholder="아이디" value={id} onChange={(e) => setId(e.target.value)} />
        <input className="signup-input" type="password" placeholder="비밀번호" value={password} onChange={(e) => setPassword(e.target.value)} />
        <input className="signup-input" type="password" placeholder="비밀번호 확인" value={confirmPassword} onChange={(e) => setConfirmPassword(e.target.value)} />

        <label className="signup-label">관심 장르</label>
        <div className="signup-checkbox-container">
          {genreOptions.map((genre) => (
            <label key={genre} className="signup-checkbox-item">
              <input type="checkbox" checked={selectedGenres.includes(genre)} onChange={() => toggleGenre(genre)} />
              {genre}
            </label>
          ))}
        </div>
        {selectedGenres.includes('기타') && (
          <input className="signup-input" placeholder="기타 장르 입력" value={customGenre} onChange={(e) => setCustomGenre(e.target.value)} />
        )}

        <label className="signup-label">활동 지역</label>
        <div className="signup-checkbox-container">
          {Object.keys(regionOptions).map((region) => (
            <label key={region} className="signup-checkbox-item">
              <input type="radio" name="region" checked={selectedRegion === region} onChange={() => setSelectedRegion(region)} />
              {region}
            </label>
          ))}
        </div>

        <div className="signup-checkbox-container">
          {regionOptions[selectedRegion].map((district) => (
            <label key={district} className="signup-checkbox-item">
              <input type="checkbox" checked={selectedDistricts.includes(district)} onChange={() => toggleDistrict(district)} />
              {district}
            </label>
          ))}
        </div>

        <input className="signup-input" placeholder="밴드 인원" value={bandMembers} onChange={(e) => setBandMembers(e.target.value)} type="number" />
        <button type="submit" className="signup-button">회원가입</button>
      </form>
    </div>
  );
};

export default Signup;
