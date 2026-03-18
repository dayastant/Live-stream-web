import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  LayoutDashboard, Radio, Video, BookOpen, BarChart2,
  Eye, Settings, LogOut, ChevronRight, Menu, Shield,
  Users, Database, Key, Server, Globe, Zap, Activity,
  AlertTriangle, Lock, FileText, Layers, Bell, RefreshCw
} from "lucide-react";

// ── Nav sections ─────────────────────────────────────────────────
const BASE_NAV = [
  { id: "dashboard",   label: "Dashboard",    icon: LayoutDashboard },
  { id: "livestreams", label: "Livestreams",  icon: Radio },
  { id: "videos",      label: "Videos",       icon: Video },
  { id: "bookings",    label: "Bookings",     icon: BookOpen },
  { id: "analytics",   label: "Analytics",    icon: BarChart2 },
  { id: "viewers",     label: "Viewers",      icon: Eye },
];

const SUPER_NAV = [
  { id: "admins",       label: "Admin Users",     icon: Users },
  { id: "streamconfig", label: "Stream Config",   icon: Zap },
  { id: "streamevents", label: "Stream Events",   icon: Activity },
  { id: "migrations",   label: "DB Migrations",   icon: Database },
  { id: "accesskeys",   label: "Access Keys",     icon: Key },
  { id: "systemlogs",   label: "System Logs",     icon: Server },
  { id: "globalconfig", label: "Global Config",   icon: Globe },
  { id: "security",     label: "Security",        icon: Lock },
];

const SYSTEM_NAV = [
  { id: "settings",  label: "Settings",  icon: Settings },
  { id: "reports",   label: "Reports",   icon: FileText },
];

// ── Nav button ────────────────────────────────────────────────────
function NavButton({ item, active, onClick, collapsed, variant = "base" }) {
  const isActive = active === item.id;

  const activeStyles = {
    base:  "bg-indigo-50 text-indigo-700",
    super: "bg-rose-50 text-rose-700",
  };
  const activeBarStyles = {
    base:  "bg-indigo-500",
    super: "bg-rose-500",
  };
  const activeIconStyles = {
    base:  "text-indigo-600",
    super: "text-rose-600",
  };
  const activeTextStyles = {
    base:  "text-indigo-700",
    super: "text-rose-700",
  };

  return (
    <motion.button
      onClick={() => onClick(item.id)}
      whileTap={{ scale: 0.97 }}
      className={`
        relative w-full flex items-center gap-3 px-3 py-2.5 rounded-xl text-left
        transition-all duration-150 group
        ${isActive ? activeStyles[variant] : "text-gray-500 hover:bg-gray-50 hover:text-gray-800"}
      `}
    >
      {isActive && (
        <motion.div
          layoutId={`${variant}-active-bar`}
          className={`absolute left-0 top-2 bottom-2 w-0.5 rounded-full ${activeBarStyles[variant]}`}
        />
      )}

      <item.icon
        size={16}
        strokeWidth={isActive ? 2.2 : 1.8}
        className={`flex-shrink-0 transition-colors ${isActive ? activeIconStyles[variant] : "text-gray-400 group-hover:text-gray-600"}`}
      />

      <AnimatePresence>
        {!collapsed && (
          <motion.span
            initial={{ opacity: 0, width: 0 }}
            animate={{ opacity: 1, width: "auto" }}
            exit={{ opacity: 0, width: 0 }}
            transition={{ duration: 0.15 }}
            className={`text-sm font-medium whitespace-nowrap overflow-hidden ${isActive ? activeTextStyles[variant] : ""}`}
          >
            {item.label}
          </motion.span>
        )}
      </AnimatePresence>

      {collapsed && (
        <div className="absolute left-full ml-3 px-2.5 py-1.5 bg-gray-900 text-white text-xs rounded-lg
          opacity-0 group-hover:opacity-100 pointer-events-none whitespace-nowrap z-50 shadow-lg">
          {item.label}
          <div className="absolute right-full top-1/2 -translate-y-1/2 border-4 border-transparent border-r-gray-900" />
        </div>
      )}
    </motion.button>
  );
}

// ── SuperAdminSidebar ─────────────────────────────────────────────
export function SuperAdminSidebar({ user, activeNav, onNavChange, onLogout }) {
  const [collapsed, setCollapsed] = useState(false);
  const [superOpen, setSuperOpen] = useState(true);

  return (
    <motion.aside
      animate={{ width: collapsed ? 72 : 260 }}
      transition={{ type: "spring", stiffness: 320, damping: 32 }}
      className="h-screen flex flex-col bg-white border-r border-gray-200 overflow-hidden flex-shrink-0 relative"
      style={{ minWidth: collapsed ? 72 : 260 }}
    >
      {/* Subtle top accent stripe */}
      <div className="absolute top-0 left-0 right-0 h-0.5 bg-gradient-to-r from-rose-500 via-orange-400 to-rose-500" />

      {/* ── Header ── */}
      <div className={`flex items-center border-b border-gray-100 px-4 pt-5 pb-4 ${collapsed ? "justify-center" : "gap-3"}`}>
        {!collapsed && (
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="flex items-center gap-2.5 flex-1">
            <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-rose-500 to-orange-500 flex items-center justify-center flex-shrink-0 shadow-md shadow-rose-200">
              <Shield size={14} className="text-white" />
            </div>
            <div>
              <p className="text-gray-900 font-bold text-sm leading-none tracking-tight">StreamCMS</p>
              <div className="flex items-center gap-1 mt-0.5">
                <span className="w-1.5 h-1.5 bg-rose-500 rounded-full" />
                <p className="text-rose-500 text-[10px] font-semibold uppercase tracking-wider">Super Admin</p>
              </div>
            </div>
          </motion.div>
        )}
        <button
          onClick={() => setCollapsed(p => !p)}
          className="w-8 h-8 rounded-lg flex items-center justify-center text-gray-400 hover:text-gray-700 hover:bg-gray-100 transition-all"
        >
          {collapsed ? <ChevronRight size={16} /> : <Menu size={16} />}
        </button>
      </div>

      {/* ── Super Admin badge (expanded only) ── */}
      <AnimatePresence>
        {!collapsed && (
          <motion.div
            initial={{ opacity: 0, height: 0 }}
            animate={{ opacity: 1, height: "auto" }}
            exit={{ opacity: 0, height: 0 }}
            className="mx-3 mt-3"
          >
            <div className="flex items-center gap-2 bg-rose-50 border border-rose-100 rounded-xl px-3 py-2">
              <AlertTriangle size={13} className="text-rose-500 flex-shrink-0" />
              <p className="text-rose-600 text-[11px] font-semibold">Full system access active</p>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      {/* ── Nav ── */}
      <nav className="flex-1 overflow-y-auto py-4 px-3 space-y-1">
        {/* Base section */}
        <AnimatePresence>
          {!collapsed && (
            <motion.p initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
              className="text-[10px] font-semibold text-gray-400 uppercase tracking-widest px-3 mb-1.5">
              Platform
            </motion.p>
          )}
        </AnimatePresence>
        <div className="space-y-0.5 mb-4">
          {BASE_NAV.map(item => (
            <NavButton key={item.id} item={item} active={activeNav} onClick={onNavChange} collapsed={collapsed} variant="base" />
          ))}
        </div>

        {/* Divider */}
        <div className={`${collapsed ? "mx-2" : "mx-1"} h-px bg-rose-100 my-2`} />

        {/* Super Admin section */}
        <div>
          {!collapsed ? (
            <button
              onClick={() => setSuperOpen(p => !p)}
              className="w-full flex items-center gap-2 px-3 mb-1.5 group"
            >
              <Shield size={10} className="text-rose-400" />
              <span className="text-[10px] font-semibold text-rose-400 uppercase tracking-widest flex-1 text-left">
                Super Admin
              </span>
              <ChevronRight
                size={11}
                className={`text-rose-300 transition-transform ${superOpen ? "rotate-90" : ""}`}
              />
            </button>
          ) : (
            <div className="flex justify-center mb-1">
              <Shield size={13} className="text-rose-400" />
            </div>
          )}

          <AnimatePresence>
            {(superOpen || collapsed) && (
              <motion.div
                initial={{ opacity: 0, height: 0 }}
                animate={{ opacity: 1, height: "auto" }}
                exit={{ opacity: 0, height: 0 }}
                className="space-y-0.5"
              >
                {SUPER_NAV.map(item => (
                  <NavButton key={item.id} item={item} active={activeNav} onClick={onNavChange} collapsed={collapsed} variant="super" />
                ))}
              </motion.div>
            )}
          </AnimatePresence>
        </div>

        {/* System section */}
        <div className={`${collapsed ? "mx-2" : "mx-1"} h-px bg-gray-100 my-2`} />
        <AnimatePresence>
          {!collapsed && (
            <motion.p initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
              className="text-[10px] font-semibold text-gray-400 uppercase tracking-widest px-3 mb-1.5">
              System
            </motion.p>
          )}
        </AnimatePresence>
        <div className="space-y-0.5">
          {SYSTEM_NAV.map(item => (
            <NavButton key={item.id} item={item} active={activeNav} onClick={onNavChange} collapsed={collapsed} variant="base" />
          ))}
        </div>
      </nav>

      {/* ── Footer ── */}
      <div className="border-t border-gray-100 p-3 space-y-2">
        <div className={`flex items-center gap-3 px-2 py-2 rounded-xl bg-rose-50 border border-rose-100 ${collapsed ? "justify-center" : ""}`}>
          <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-rose-500 to-orange-500 flex items-center justify-center flex-shrink-0 shadow-sm shadow-rose-300">
            <span className="text-white text-xs font-bold">
              {user?.username?.slice(0, 2).toUpperCase() || "SA"}
            </span>
          </div>
          <AnimatePresence>
            {!collapsed && (
              <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} className="flex-1 min-w-0">
                <p className="text-gray-800 text-xs font-semibold truncate">{user?.username || "super_admin"}</p>
                <p className="text-rose-500 text-[10px] font-semibold uppercase tracking-wide">SUPER_ADMIN</p>
              </motion.div>
            )}
          </AnimatePresence>
          {!collapsed && (
            <button
              onClick={onLogout}
              className="text-gray-400 hover:text-rose-500 transition-colors"
              title="Logout"
            >
              <LogOut size={15} />
            </button>
          )}
        </div>
      </div>
    </motion.aside>
  );
}

export default SuperAdminSidebar;
