// src/pages/Reviews.js

import React, { useEffect, useState } from 'react';
import { useLocation } from 'react-router-dom';
import ReviewService from '../services/ReviewService';

const Reviews = () => {
    const [reviews, setReviews] = useState([]);
    const location = useLocation();

    useEffect(() => {
        const queryParams = new URLSearchParams(location.search);
        const mangaName = queryParams.get('name');
        if (mangaName) {
            fetchReviews(mangaName);
        }
    }, [location]);

    const fetchReviews = async (name) => {
        try {
            const fetchedReviews = await ReviewService.getReviews(name);
            setReviews(fetchedReviews);
        } catch (error) {
            console.error('Ошибка при получении отзывов:', error);
        }
    };

    const handleDelete = async (id) => {
        const isDeleted = await ReviewService.deleteReview(id);
        if (isDeleted) {
            setReviews(reviews.filter(review => review.id !== id));
            alert('Отзыв успешно удален!');
        } else {
            alert('Ошибка при удалении отзыва.');
        }
    };

    return (
        <div>
            <h1>Отзывы</h1>
            <ul>
                {reviews.map(review => (
                    <li key={review.id}>
                        <p>{review.content}</p>
                        <button onClick={() => handleDelete(review.id)}>Удалить</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default Reviews;