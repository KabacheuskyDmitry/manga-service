import React, { useState } from 'react';
import MangaService from '../services/MangaService';

const AddManga = () => {
    const [formData, setFormData] = useState({ name: '', rating: '', author: '' });

    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await MangaService.saveManga(formData);
            alert('Манга успешно добавлена!');
            setFormData({ name: '', rating: '', author: '' }); // Очистка формы
        } catch (error) {
            console.error('Ошибка при добавлении манги:', error);
        }
    };

    return (
        <form onSubmit={handleSubmit}>
            <h1>Добавить мангу</h1>
            <input
                type="text"
                placeholder="Название"
                value={formData.name}
                onChange={(e) => setFormData({ ...formData, name: e.target.value })}
                required
            />
            <input
                type="number"
                placeholder="рейтинг"
                value={formData.rating}
                onChange={(e) => setFormData({ ...formData, genre: e.target.value })}
                required
            />
            <input
                type="text"
                placeholder="Автор"
                value={formData.author}
                onChange={(e) => setFormData({ ...formData, author: e.target.value })}
                required
            />
            <button type="submit">Добавить мангу</button>
        </form>
    );
};

export default AddManga;