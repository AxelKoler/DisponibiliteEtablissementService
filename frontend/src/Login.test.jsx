import { render, screen, fireEvent } from "@testing-library/react";
import { describe, it, expect, vi } from "vitest";
import Login from "./Login.jsx";

describe("Login", () => {
  it("affiche le titre et le bouton de connexion", () => {
    render(<Login onSuccess={vi.fn()} />);

    expect(
      screen.getByRole("heading", { name: "Connexion" })
    ).toBeInTheDocument();
    expect(
      screen.getByRole("button", { name: "Se connecter" })
    ).toBeInTheDocument();
  });

  it("affiche les champs identifiant et mot de passe", () => {
    const { container } = render(<Login onSuccess={vi.fn()} />);

    expect(screen.getByPlaceholderText("votre identifiant")).toBeInTheDocument();
    expect(container.querySelector('input[type="password"]')).not.toBeNull();
  });

  it("permet de saisir un identifiant", () => {
    render(<Login onSuccess={vi.fn()} />);

    const champ = screen.getByPlaceholderText("votre identifiant");
    fireEvent.change(champ, { target: { value: "admin" } });

    expect(champ).toHaveValue("admin");
  });
});