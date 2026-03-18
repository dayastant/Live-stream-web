import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_URL || "http://localhost:8080/api";

const api = axios.create({
  baseURL: BASE_URL,
  headers: { "Content-Type": "application/json" },
  withCredentials: true,
});

// ── Attach JWT to every request ──────────────────────────────────
api.interceptors.request.use((config) => {
  const token = localStorage.getItem("admin_token");
  if (token) config.headers.Authorization = `Bearer ${token}`;
  return config;
});

// ── Auto-logout on 401 ────────────────────────────────────────────
api.interceptors.response.use(
  (res) => res,
  (err) => {
    if (err.response?.status === 401) {
      localStorage.removeItem("admin_token");
      localStorage.removeItem("admin_info");
      window.dispatchEvent(new Event("auth:logout"));
    }
    return Promise.reject(err);
  }
);

// ────────────────────────────────────────────────────────────────
//  AUTH SERVICE
// ────────────────────────────────────────────────────────────────

const AdminAuthService = {
  /**
   * POST /api/auth/login
   * Body: { username, password }
   * Returns: { token, role, username }
   */
  async login(username, password) {
    const { data } = await api.post("/auth/login", { username, password });
    // Persist token + basic admin info
    localStorage.setItem("admin_token", data.token);
    localStorage.setItem(
      "admin_info",
      JSON.stringify({ username: data.username, role: data.role })
    );
    return data; // { token, role: "SUPER_ADMIN" | "MODERATOR", username }
  },

  /**
   * POST /api/auth/logout
   */
  async logout() {
    try {
      await api.post("/auth/logout");
    } finally {
      localStorage.removeItem("admin_token");
      localStorage.removeItem("admin_info");
    }
  },

  /**
   * GET /api/auth/me  — verify token & fetch current admin
   */
  async me() {
    const { data } = await api.get("/auth/me");
    return data; // Admin entity
  },

  /**
   * Synchronous helper — read cached role from localStorage
   */
  getCachedRole() {
    try {
      const info = JSON.parse(localStorage.getItem("admin_info") || "{}");
      return info.role || null;
    } catch {
      return null;
    }
  },

  getCachedUser() {
    try {
      return JSON.parse(localStorage.getItem("admin_info") || "null");
    } catch {
      return null;
    }
  },

  isAuthenticated() {
    return !!localStorage.getItem("admin_token");
  },
};

export default AdminAuthService;
export { api }; // export configured axios instance for other services