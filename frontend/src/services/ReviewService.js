// src/services/ReviewService.js

import axios from 'axios';

const API_URL = 'http://localhost:8080'; // Замените на ваш URL

const ReviewService = {
    getReviews: async (name) => {
        const response = await axios.get(`${API_URL}/mangaReviews`, { params: { name } });
        return response.data;
    },

    createReview: async (title, content, rating) => {
        const response = await axios.post(`${API_URL}/postReview`, null, {
            params: { title, content, rating }
        });
        return response.status === 201;
    },

    deleteReview: async (id) => {
        const response = await axios.delete(`${API_URL}/${id}`);
        return response.status === 200;
    }
};

export default ReviewService;