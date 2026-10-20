import { useEffect, useMemo, useState } from "react";

// Données d'exemple affichées si le backend ne répond pas encore
const DEMO = [
  { id: 1, nom: "Le Comptoir des Saveurs", categorie: "Restaurant", ville: "Lyon", note: 4.8, avis: 320, distance: 1.2, disponible: true, image: "https://images.unsplash.com/photo-1517248135467-4c7edcad34c4?w=600" },
  { id: 2, nom: "L'Atelier Beauté", categorie: "Salon de beauté", ville: "Lyon", note: 4.6, avis: 118, distance: 2.1, disponible: true, image: "https://images.unsplash.com/photo-1560066984-138dadb4c035?w=600" },
  { id: 3, nom: "Hôtel Bellecour", categorie: "Hôtel", ville: "Lyon", note: 4.5, avis: 412, distance: 0.8, disponible: false, image: "https://images.unsplash.com/photo-1566073771259-6a8506099945?w=600" },
  { id: 4, nom: "Rooftop 71", categorie: "Restaurant", ville: "Lyon", note: 4.4, avis: 275, distance: 3.4, disponible: true, image: "https://images.unsplash.com/photo-1414235077428-338989a2e8c0?w=600" },
  { id: 5, nom: "Pure Harmonie", categorie: "Bien-être", ville: "Lyon", note: 4.7, avis: 96, distance: 2.8, disponible: true, image: "https://images.unsplash.com/photo-1544161515-4ab6ce6db874?w=600" },
  { id: 6, nom: "Le Bar Auguste", categorie: "Bar", ville: "Lyon", note: 4.3, avis: 189, distance: 1.6, disponible: false, image: "https://images.unsplash.com/photo-1514933651103-005eec06c04b?w=600" },
];
const CATS = ["Tous", "Restaurant", "Salon de beauté", "Hôtel", "Bar", "Bien-être"];

export default function Accueil({ onLogout }) {
  const [items, setItems] = useState([]); const [demo, setDemo] = useState(false);
  const [q, setQ] = useState(""); const [cat, setCat] = useState("Tous"); const [tri, setTri] = useState("note");

  useEffect(() => {
    const token = localStorage.getItem("token");
    // Adaptez l'URL à votre contrôleur Spring
    fetch("/api/etablissements", { headers: token ? { Authorization: "Bearer " + token } : {} })
      .then((r) => { if (!r.ok) throw new Error(); return r.json(); })
      .then((d) => setItems(Array.isArray(d) ? d : d.content || []))
      .catch(() => { setItems(DEMO); setDemo(true); });
  }, []);

  const list = useMemo(() => {
    const s = q.toLowerCase();
    return items
      .filter((e) => cat === "Tous" || e.categorie === cat)
      .filter((e) => !s || (e.nom + " " + (e.ville || "")).toLowerCase().includes(s))
      .sort((a, b) => tri === "note" ? (b.note || 0) - (a.note || 0) : (a.distance || 0) - (b.distance || 0));
  }, [items, q, cat, tri]);

  return (
    <div className="home">
      <header className="top">
        <strong className="logo">ProxiPlace</strong>
        <nav><a>Découvrir</a><a>Mes réservations</a></nav>
        <button className="ghost" onClick={onLogout}>Déconnexion</button>
      </header>
      <main className="wrap">
        <h1>Les meilleures adresses<br/>près de chez vous</h1>
        <p className="lead">Restaurants, salons, hôtels… et leurs disponibilités en temps réel.</p>
        <div className="search">
          <input placeholder="Nom ou ville…" value={q} onChange={(e) => setQ(e.target.value)} />
          <select value={tri} onChange={(e) => setTri(e.target.value)}>
            <option value="note">Mieux notés</option><option value="distance">Plus proches</option>
          </select>
        </div>
        <div className="chips">
          {CATS.map((c) => <button key={c} className={c === cat ? "chip on" : "chip"} onClick={() => setCat(c)}>{c}</button>)}
        </div>
        <p className="count">{list.length} établissement{list.length > 1 ? "s" : ""}{demo && " · données d'exemple"}</p>
        <div className="grid">
          {list.map((e) => (
            <article key={e.id} className="etab">
              <div className="img" style={{ backgroundImage: `url(${e.image || ""})` }}>
                <span className={e.disponible ? "badge open" : "badge full"}>{e.disponible ? "Ouvert" : "Complet"}</span>
              </div>
              <div className="body">
                <h3>{e.nom}</h3>
                <p className="muted">{e.categorie}{e.ville && " · " + e.ville}</p>
                <p className="meta">★ {e.note ?? "–"} <span className="muted">({e.avis ?? 0})</span>{e.distance != null && <span> · {e.distance} km</span>}</p>
              </div>
            </article>
          ))}
        </div>
        {list.length === 0 && <p className="muted">Aucun établissement ne correspond.</p>}
      </main>
    </div>
  );
}
