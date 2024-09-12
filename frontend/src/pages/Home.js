import React from 'react';
import { Link } from 'react-router-dom';

const Home = () => {
    return (
        <div className="home">
            <h1>Добро пожаловать в библиотеку манги!</h1>
            <p>Выберите действие, которое хотите выполнить:</p>
            <nav className="tabs">
                <ul>
                    <li>
                        <Link to="/find">Найти мангу</Link>
                    </li>
                    <li>
                        <Link to="/add">Добавить мангу</Link>
                    </li>
                    <li>
                        <Link to="/books">Получить список манги</Link>
                    </li>
                </ul>
            </nav>
        </div>
    );
};

export default Home;