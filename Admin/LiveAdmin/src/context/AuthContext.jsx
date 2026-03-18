// src/context/AuthContext.jsx
import { createContext, useContext, useEffect, useState } from "react";
import AdminAuthService from "../api/Adminauthservice";

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
    const [user, setUser] = useState(null);
    const [loading, setLoading] = useState(true);

    // Rehydrate session on app mount
    useEffect(() => {
        if (AdminAuthService.isAuthenticated()) {
            AdminAuthService.me()
                .then(setUser)
                .catch(() => {
                    setUser(null);
                    localStorage.removeItem("admin_token");
                    localStorage.removeItem("admin_info");
                })
                .finally(() => setLoading(false));
        } else {
            setLoading(false);
        }

        // Listen for 401 auto-logout
        const handleLogout = () => setUser(null);
        window.addEventListener("auth:logout", handleLogout);
        return () => window.removeEventListener("auth:logout", handleLogout);
    }, []);

    async function login(username, password) {
        const data = await AdminAuthService.login(username, password);
        setUser({ username: data.username, role: data.role });
        return data;
    }

    async function logout() {
        await AdminAuthService.logout();
        setUser(null);
    }

    return (
        <AuthContext.Provider value={{ user, login, logout, loading }}>
            {children}
        </AuthContext.Provider>
    );
}

export function useAuth() {
    const ctx = useContext(AuthContext);
    if (!ctx) throw new Error("useAuth must be used inside <AuthProvider>");
    return ctx;
}