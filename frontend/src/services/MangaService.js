const API_BASE_URL = 'http://localhost:8080';
class MangaService {
    async getAllMangas() {
        const response = await fetch(`${API_BASE_URL}/manga/GetAll`);
        if (!response.ok) {
            throw new Error('Ошибка при получении списка манги');
        }
        return await response.json();
    }

    async getMangaById(id) {
        const response = await fetch(`${API_BASE_URL}/manga/${id}`);
        if (!response.ok) {
            throw new Error('манга не найдена');
        }
        return await response.json();
    }
    async getMangaByTitle(name) {
        const response = await fetch(`${API_BASE_URL}/manga/${encodeURIComponent(name)}`);
        if (!response.ok) {
            throw new Error('Манга не найдена');
        }
        return await response.json();
    }
    async saveManga(mangaData) {
        const response = await fetch(`${API_BASE_URL}/saveManga`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(mangaData),
        });
        if (!response.ok) {
            throw new Error('Ошибка при сохранении Манги');
        }
        return await response.json();
    }

    async updateManga(id, mangaData) {
        const params = new URLSearchParams();

        // Append each property if it exists
        if (mangaData.name) params.append('name', mangaData.name);
        if (mangaData.rating) params.append('rating', mangaData.rating);
        if (mangaData.author) params.append('author', mangaData.author);

        const response = await fetch(`${API_BASE_URL}/updateManga/${id}?${params.toString()}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
        });

        if (!response.ok) {
            throw new Error('Ошибка при обновлении манги');
        }
        return await response.json();
    }

    async deleteManga(id) {
        const response = await fetch(`${API_BASE_URL}/deleteManga/${id}`, {
            method: 'DELETE',
        });
        if (!response.ok) {
            throw new Error('Ошибка при удалении манги');
        }
    }

    async getMangaWithHighRating(minRating) {
        const response = await fetch(`${API_BASE_URL}/manga/high-rating?minRating=${minRating}`);
        if (!response.ok) {
            throw new Error('Ошибка при получении манги с высоким рейтингом');
        }
        return await response.json();
    }

    // Методы для работы с счетчиком
    async getCounterData() {
        const response = await fetch(`${API_BASE_URL}/counter/data`);
        if (!response.ok) {
            throw new Error('Ошибка при получении данных счетчика');
        }
        return await response.json();
    }
}
export default new MangaService();