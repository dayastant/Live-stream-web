// src/routes/AppRouter.jsx
import { BrowserRouter, Routes, Route, Navigate } from "react-router-dom";
import { useAuth } from "../context/AuthContext";
import AdminLayout from "../layouts/AdminLayout";
import LoginPage from "../pages/auth/LoginPage";

// ── Lazy page imports (code-split per route) ──────────────────────
import { lazy, Suspense } from "react";

// const DashboardPage    = lazy(() => import("../pages/dashboard/DashboardPage"));
// const LivestreamsPage  = lazy(() => import("../pages/livestreams/LivestreamsPage"));
// const VideosPage       = lazy(() => import("../pages/videos/VideosPage"));
// const BookingsPage     = lazy(() => import("../pages/bookings/BookingsPage"));
// const AnalyticsPage    = lazy(() => import("../pages/analytics/AnalyticsPage"));
// const ViewersPage      = lazy(() => import("../pages/viewers/ViewersPage"));

// // Super Admin only
// const AdminUsersPage   = lazy(() => import("../pages/superadmin/AdminUsersPage"));
// const StreamConfigPage = lazy(() => import("../pages/superadmin/StreamConfigPage"));
// const StreamEventsPage = lazy(() => import("../pages/superadmin/StreamEventsPage"));
// const MigrationsPage   = lazy(() => import("../pages/superadmin/MigrationsPage"));
// const AccessKeysPage   = lazy(() => import("../pages/superadmin/AccessKeysPage"));
// const SystemLogsPage   = lazy(() => import("../pages/superadmin/SystemLogsPage"));
// const GlobalConfigPage = lazy(() => import("../pages/superadmin/GlobalConfigPage"));
// const SecurityPage     = lazy(() => import("../pages/superadmin/SecurityPage"));

// ─────────────────────────────────────────────────────────────────
//  GUARDS
// ─────────────────────────────────────────────────────────────────

/** Redirects unauthenticated users to /login */
function ProtectedRoute({ children }) {
  const { user, loading } = useAuth();
  if (loading) return <PageLoader />;
  if (!user) return <Navigate to="/login" replace />;
  return children;
}

/** Blocks access to pages that require a specific role */
function RoleGuard({ role, children }) {
  const { user } = useAuth();
  if (user?.role !== role) return <Navigate to="/dashboard" replace />;
  return children;
}

function PageLoader() {
  return (
    <div className="flex-1 flex items-center justify-center bg-gray-50">
      <div className="w-6 h-6 border-2 border-indigo-300 border-t-indigo-600 rounded-full animate-spin" />
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────
//  ROUTER
// ─────────────────────────────────────────────────────────────────

export default function AppRouter() {
  return (
    <BrowserRouter>
      <Suspense fallback={<PageLoader />}>
        <Routes>

          {/* Public */}
          {/* <Route path="/login" element={<LoginPage />} /> */}

          {/* Protected — all routes share AdminLayout (sidebar + topbar) */}
          <Route
            path="/"
            element={
              // <ProtectedRoute>
                <AdminLayout />
              // </ProtectedRoute>
            }
          >
            {/* <Route index element={<Navigate to="/dashboard" replace />} />
            <Route path="dashboard"   element={<DashboardPage />} />
            <Route path="livestreams" element={<LivestreamsPage />} />
            <Route path="videos"      element={<VideosPage />} />
            <Route path="bookings"    element={<BookingsPage />} />
            <Route path="analytics"   element={<AnalyticsPage />} />
            <Route path="viewers"     element={<ViewersPage />} /> */}

            {/* SUPER_ADMIN only routes */}
            {/* <Route
              path="super/admins"    element={<RoleGuard role="SUPER_ADMIN"><AdminUsersPage /></RoleGuard>}
            />
            <Route
              path="super/config"    element={<RoleGuard role="SUPER_ADMIN"><StreamConfigPage /></RoleGuard>}
            />
            <Route
              path="super/events"    element={<RoleGuard role="SUPER_ADMIN"><StreamEventsPage /></RoleGuard>}
            />
            <Route
              path="super/db"        element={<RoleGuard role="SUPER_ADMIN"><MigrationsPage /></RoleGuard>}
            />
            <Route
              path="super/keys"      element={<RoleGuard role="SUPER_ADMIN"><AccessKeysPage /></RoleGuard>}
            />
            <Route
              path="super/logs"      element={<RoleGuard role="SUPER_ADMIN"><SystemLogsPage /></RoleGuard>}
            />
            <Route
              path="super/global"    element={<RoleGuard role="SUPER_ADMIN"><GlobalConfigPage /></RoleGuard>}
            />
            <Route
              path="super/security"  element={<RoleGuard role="SUPER_ADMIN"><SecurityPage /></RoleGuard>}
            />
            */}
          </Route>

          {/* 404 fallback */}
          {/* <Route path="*" element={<Navigate to="/dashboard" replace />} /> */}

        </Routes>
      </Suspense>
    </BrowserRouter>
  );
}