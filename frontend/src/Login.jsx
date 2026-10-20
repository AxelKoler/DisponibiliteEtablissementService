import { useState } from "react";

export default function Login({ onSuccess }) {
  const [form, setForm] = useState({ username: "", password: "" });
  const [error, setError] = useState(""); const [ok, setOk] = useState(""); const [loading, setLoading] = useState(false);
  const change = (e) => setForm({ ...form, [e.target.name]: e.target.value });

  async function submit(e) {
    e.preventDefault(); setError(""); setOk(""); setLoading(true);
    try {
      // Adaptez les noms de champs à votre LoginDtoRequest si besoin
      const res = await fetch("/api/auth/login", { method: "POST", headers: { "Content-Type": "application/json" }, body: JSON.stringify(form) });
      if (!res.ok) throw new Error(res.status === 401 || res.status === 403 ? "Email ou mot de passe incorrect" : "Erreur serveur (" + res.status + ")");
      const data = await res.json();
      const token = data.token || data.accessToken || data.jwt;
      if (token) localStorage.setItem("token", token);
      localStorage.setItem("user", JSON.stringify(data));
      setOk("Connexion réussie !"); onSuccess && onSuccess();
    } catch (err) { setError(err.message === "Failed to fetch" ? "Impossible de joindre le serveur" : err.message); }
    finally { setLoading(false); }
  }

  return (
    <main className="page">
      <section className="side">
        <h1>Trouvez, vérifiez,<br/>réservez.</h1>
        <p>Les établissements près de chez vous et la disponibilité de leurs services, en temps réel.</p>
      </section>
      <form className="card" onSubmit={submit}>
        <h2>Connexion</h2>
        <p className="sub">Heureux de vous revoir</p>
        <label>Nom d'utilisateur<input name="username" type="text" autoComplete="username" required value={form.username} onChange={change} placeholder="votre identifiant"/></label>
        <label>Mot de passe<input name="password" type="password" required value={form.password} onChange={change} placeholder="••••••••"/></label>
        {error && <div className="msg err">{error}</div>}
        {ok && <div className="msg ok">{ok}</div>}
        <button disabled={loading}>{loading ? "Connexion..." : "Se connecter"}</button>
      </form>
    </main>
  );
}
