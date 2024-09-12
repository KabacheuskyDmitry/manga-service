import React, { useEffect, useState } from 'react';
import MangaService from '../services/MangaService';
import { Link } from 'react-router-dom';

const BooksList = () => {
    const [mangas, setBooks] = useState([]);

    useEffect(() => {
        const fetchManga = async () => {
            try {
                const allManga = await MangaService.getAllMangas();
                setBooks(allManga);
            } catch (error) {
                console.error('Ошибка при получении списка манги:', error);
            }
        };
        fetchManga();
    }, []);

    return (
        <div>
            <h1>Список манги</h1>
            <ul>
                {mangas.map(manga => (
                    <li key={manga.id}>
                        <strong>Название:</strong> {manga.name} <br />
                        <strong>Автор:</strong> {manga.author} <br />
                        <strong>Рейтинг:</strong> {manga.rating} <br />
                        <Link to={`/update/${manga.id}`}>Обновить</Link> |
                        <Link to={`/delete/${manga.id}`}>Удалить</Link> |
                        <Link to={`/reviews/${encodeURIComponent(manga.name)}`}>Комментарии</Link> |
                        <Link to={`/createReview/${encodeURIComponent(manga.name)}`}>Оставить комментарий</Link>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default BooksList;