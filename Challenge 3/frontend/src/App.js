import React from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./Components/Login";
import Secure from "./Components/Secure";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/secure" element={<Secure />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;
