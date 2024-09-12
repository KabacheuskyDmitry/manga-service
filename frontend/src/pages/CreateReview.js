import React, { useState } from 'react';
import ReviewService from '../services/ReviewService';
import { useNavigate, useParams } from 'react-router-dom';
import '../styles/CreateReview.css';

const CreateReview = () => {
    const { name } = useParams();
    const navigate = useNavigate();
    const [content, setContent] = useState('');


    const handleSubmit = async (e) => {
        e.preventDefault();
        try {
            await ReviewService.createReview(name, content);
            alert('Отзыв успешно создан!');
            navigate(`/reviews/${encodeURIComponent(name)}`);
        } catch (error) {
            console.error('Ошибка при создании отзыва:', error);
        }
    };

    return (
        <div>
            <h1>Создать отзыв для книги: {decodeURIComponent(name)}</h1>
            <form onSubmit={handleSubmit}>
                <div>
                    <label htmlFor="comment">Комментарий:</label>
                    <textarea
                        id="comment"
                        className="review-textarea"
                        value={content}
                        onChange={(e) => setContent(e.target.value)}
                        required
                    />
                </div>
                <button type="submit">Создать отзыв</button>
            </form>
        </div>
    );
};

export default CreateReview;