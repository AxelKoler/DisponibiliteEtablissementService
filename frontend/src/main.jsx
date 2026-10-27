import React, { useState } from "react"; import { createRoot } from "react-dom/client";
import Login from "./Login.jsx"; import Accueil from "./Accueil.jsx"; import "./styles.css";
function App() {
  const [logged, setLogged] = useState(!!localStorage.getItem("token"));
  if (!logged) return <Login onSuccess={() => setLogged(true)} />;
  return <Accueil onLogout={() => { localStorage.clear(); setLogged(false); }} />;
}
createRoot(document.getElementById("root")).render(<App />);
