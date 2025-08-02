import React, { useState, useEffect } from 'react';
import { usePainting } from '../context/PaintingContext';

export const Header = () => {
  const { paintingState, setPaintingTitle, savePainting, loadPaintingFromServer } = usePainting();
  const [currentUser, setCurrentUser] = useState('user1');

  useEffect(() => {
    if (currentUser) {
        loadPaintingFromServer(currentUser);
    }
  }, []);

  const handleUserChange = (e: React.ChangeEvent<HTMLSelectElement>) => {
    const newUser = e.target.value;
    setCurrentUser(newUser);
    loadPaintingFromServer(newUser);
  };

  const handleSave = () => {
    savePainting(currentUser);
  };

  return (
    <header className="header">
      <input
        type="text"
        value={paintingState.title}
        onChange={(e) => setPaintingTitle(e.target.value)}
        className="painting-title-input"
        placeholder="Painting Title"
      />
      <div className="header-controls" style={{ display: 'flex', alignItems: 'center', gap: '1rem' }}>
        <select value={currentUser} onChange={handleUserChange}>
          <option value="user1">User 1</option>
          <option value="user2">User 2</option>
        </select>
        <button onClick={handleSave}>Save</button>
      </div>
    </header>
  );
};