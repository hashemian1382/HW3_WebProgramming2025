import React, { createContext, useState, useContext, ReactNode } from 'react';
import type { PaintingState, PaintingContextType, Shape } from '../types';

const PaintingContext = createContext<PaintingContextType | undefined>(undefined);

export const PaintingProvider = ({ children }: { children: ReactNode }) => {
  const [paintingState, setPaintingState] = useState<PaintingState>({
    title: 'My Painting',
    shapes: [],
  });

  const setPaintingTitle = (title: string) => {
    setPaintingState((prevState) => ({ ...prevState, title }));
  };

  const addShape = (shape: Omit<Shape, 'id'>) => {
    const newShape: Shape = { ...shape, id: crypto.randomUUID() };
    setPaintingState((prevState) => ({
      ...prevState,
      shapes: [...prevState.shapes, newShape],
    }));
  };

  const removeShape = (id: string) => {
    setPaintingState((prevState) => ({
      ...prevState,
      shapes: prevState.shapes.filter((shape) => shape.id !== id),
    }));
  };

  const updateShapePosition = (id: string, x: number, y: number) => {
    setPaintingState((prevState) => ({
      ...prevState,
      shapes: prevState.shapes.map((shape) =>
        shape.id === id ? { ...shape, x, y } : shape
      ),
    }));
  };

  const loadPainting = (newState: PaintingState) => {
    setPaintingState(newState);
  };

  const savePainting = async (username: string) => {
    if (!username) {
      alert('Please select a user first.');
      return;
    }
    try {
      const response = await fetch(`http://localhost:8080/api/painting/${username}`, {
        method: 'PUT',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(paintingState),
      });

      if (!response.ok) {
        throw new Error('Failed to save painting');
      }
      alert('Painting saved successfully!');
    } catch (error) {
      console.error('Error saving painting:', error);
      alert('Could not save painting.');
    }
  };

  const loadPaintingFromServer = async (username: string) => {
    if (!username) return;
    try {
      const response = await fetch(`http://localhost:8080/api/painting/${username}`);
      if (!response.ok) {
        throw new Error('Failed to load painting');
      }
      const data: PaintingState = await response.json();
      loadPainting(data);
    } catch (error) {
      console.error('Error loading painting:', error);
      alert('Could not load painting from server.');
    }
  };


  const value = {
    paintingState,
    setPaintingTitle,
    addShape,
    removeShape,
    updateShapePosition,
    loadPainting,
    savePainting,
    loadPaintingFromServer,
  };

  return (
    <PaintingContext.Provider value={value}>
      {children}
    </PaintingContext.Provider>
  );
};

export const usePainting = () => {
  const context = useContext(PaintingContext);
  if (context === undefined) {
    throw new Error('usePainting must be used within a PaintingProvider');
  }
  return context;
};