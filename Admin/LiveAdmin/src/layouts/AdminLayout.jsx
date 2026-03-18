// src/layouts/AdminLayout.jsx
import { Outlet, useNavigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import { SuperAdminSidebar } from "../components/sidebar/SuperAdminSidebar";
import { ModeratorSidebar } from "../components/sidebar/ModeratorSidebar";

/**
 * AdminLayout
 * ──────────────────────────────────────────
 * Wraps every protected page with:
 *   - Role-based sidebar (SuperAdmin or Moderator)
 *   - <Outlet /> for page content
 *
 * Role mapping (matches AdminRole.java enum):
 *   SUPER_ADMIN  →  SuperAdminSidebar
 *   MODERATOR    →  ModeratorSidebar
 */
export default function AdminLayout() {
    const { user, logout } = useAuth();
    const navigate = useNavigate();

    function handleNav(path) {
        navigate(`/${path}`);
    }

    async function handleLogout() {
        await logout();
        navigate("/login");
    }

    return (
        <div className="flex h-screen bg-gray-50 overflow-hidden">

            {/* Sidebar — switches by role */}
            {user?.role === "SUPER_ADMIN" ? (
                <SuperAdminSidebar
                    user={user}
                    onNav={handleNav}
                    onLogout={handleLogout}
                />
            ) : (
                <ModeratorSidebar
                    user={user}
                    onNav={handleNav}
                    onLogout={handleLogout}
                />
            )}

            {/* Page content */}
            <main className="flex-1 overflow-y-auto">
                <Outlet />
            </main>

        </div>
    );
}