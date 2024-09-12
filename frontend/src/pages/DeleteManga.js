import React from 'react';
import { useParams, useNavigate } from 'react-router-dom';
import MangaService from '../services/MangaService';

const DeleteManga = () => {
    const { id } = useParams();
    const navigate = useNavigate();

    const handleDelete = async () => {
        try {
            await MangaService.deleteManga(id);
            alert('манга успешно удалена!');
            navigate('/manga'); // Используйте navigate для перенаправления
        } catch (error) {
            console.error('Ошибка при удалении манги:', error);
        }
    };

    return (
        <div>
            <h1>Удалить мангу</h1>
            <p>Вы уверены, что хотите удалить мангу с ID: {id}?</p>
            <button onClick={handleDelete}>Удалить</button>
        </div>
    );
};

export default DeleteManga;