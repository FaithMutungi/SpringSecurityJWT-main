import React from 'react';
import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';
import Authenticate from './Authenticate';
import Register from './Register';

const App = () => {
    return (
        <Router>
            <div className="App">
                <Routes>
                    <Route path="/authenticate" element={<Authenticate />} />
                    <Route path="/register" element={<Register />} />
                    <Route path="/" element={<Authenticate />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
