import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import MangaService from '../services/MangaService';

const FindManga = () => {
    const [name, setName] = useState('');
    const [mangaInfo, setMangaInfo] = useState(null);
    const [error, setError] = useState('');
    const navigate = useNavigate(); // Использование хука для навигации

    const handleSearch = async () => {
        setError('');
        setMangaInfo(null);
        try {
            const manga = await MangaService.getMangaByTitle(name);
            setMangaInfo(manga);
        } catch (err) {
            setError(err.message);
        }
    };

    const handleUpdate = () => {
        if (!mangaInfo) {
            setError('Сначала найдите мангу для обновления.');
            return;
        }
        // Переход на страницу обновления с ID книги
        navigate(`/update/${mangaInfo.id}`);
    };

    const handleDelete = () => {
        if (!mangaInfo) {
            setError('Сначала найдите мангу для удаления.');
            return;
        }
        // Переход на страницу удаления с ID книги
        navigate(`/delete/${mangaInfo.id}`);
    };

    return (
        <div style={{ padding: '20px' }}>
            <h1>Найти книгу</h1>
            <input
                type="text"
                value={name}
                onChange={(e) => setName(e.target.value)}
                placeholder="Введите название манги"
                style={{ marginRight: '10px' }}
            />
            <button onClick={handleSearch}>Поиск</button>

            {error && <p style={{ color: 'red' }}>{error}</p>}
            {mangaInfo && (
                <div style={{ marginTop: '20px' }}>
                    <h2>Информация о манге:</h2>
                    <p><strong>ID:</strong> {mangaInfo.id}</p>
                    <p><strong>Название:</strong> {mangaInfo.name}</p>
                    <p><strong>Рейтинг:</strong> {mangaInfo.rating}</p>
                    <p><strong>Автор:</strong> {mangaInfo.author}</p>
                    <button onClick={handleUpdate} style={{ marginRight: '10px' }}>Обновить</button>
                    <button onClick={handleDelete}>Удалить</button>
                </div>
            )}
        </div>
    );
};

export default FindManga;