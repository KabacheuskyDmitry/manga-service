// src/routes.js

import React from 'react';
import { Route, Routes } from 'react-router-dom';
import Home from './pages/Home';
import MangaList from './pages/MangasList';
import AddManga from './pages/AddManga';
import UpdateManga from './pages/UpdateManga';
import DeleteManga from './pages/DeleteManga';
import FindManga from './pages/FindManga';
import Reviews from './pages/Reviews';
import CreateReview from './pages/CreateReview';

const AppRoutes = () => (
    <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/mangas" element={<MangaList />} />
        <Route path="/add" element={<AddManga />} />
        <Route path="/find" element={<FindManga />} />
        <Route path="/update/:id" element={<UpdateManga />} />
        <Route path="/delete/:id" element={<DeleteManga />} />
        <Route path="/reviews/:name" element={<Reviews />} />
        <Route path="/createReview/:name" element={<CreateReview />} />
    </Routes>
);

export default AppRoutes;