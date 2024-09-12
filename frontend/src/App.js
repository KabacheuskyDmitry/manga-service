// src/App.js

import React from 'react';
import { BrowserRouter as Router } from 'react-router-dom';
import Routes from './routes';

const App = () => {
    return (
        <Router>
            <div className="app">
                <main>
                    <Routes />
                </main>
            </div>
        </Router>
    );
};

export default App;