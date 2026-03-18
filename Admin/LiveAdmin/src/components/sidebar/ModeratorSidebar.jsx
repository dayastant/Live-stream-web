import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  LayoutDashboard, Radio, Video, BookOpen, BarChart2,
  Eye, Settings, LogOut, ChevronRight, Menu, Bell,
  Users, MessageSquare, Flag, HelpCircle
} from "lucide-react";

// ── Nav items available to MODERATOR role ────────────────────────
const NAV_ITEMS = [
  {
    group: "Overview",
    items: [
      { id: "dashboard",   label: "Dashboard",    icon: LayoutDashboard },
      { id: "livestreams", label: "Livestreams",  icon: Radio },
      { id: "videos",      label: "Videos",       icon: Video },
    ],
  },
  {
    group: "Management",
    items: [
      { id: "bookings",    label: "Bookings",     icon: BookOpen },
      { id: "viewers",     label: "Viewers",      icon: Eye },
      { id: "comments",    label: "Comments",     icon: MessageSquare },
      { id: "reports",     label: "Reports",      icon: Flag },
    ],
  },
  {
    group: "Insights",
    items: [
      { id: "analytics",   label: "Analytics",    icon: BarChart2 },
    ],
  },
  {
    group: "System",
    items: [
      { id: "settings",    label: "Settings",     icon: Settings },
      { id: "help",        label: "Help",         icon: HelpCircle },
    ],
  },
];

// ── Single nav button ─────────────────────────────────────────────
function NavButton({ item, active, onClick, collapsed }) {
  const isActive = active === item.id;

  return (
    <motion.button
      onClick={() => onClick(item.id)}
      whileTap={{ scale: 0.97 }}
      className={`
        relative w-full flex items-center gap-3 px-3 py-2.5 rounded-xl text-left
        transition-all duration-150 group
        ${isActive
          ? "bg-indigo-50 text-indigo-700"
          : "text-gray-500 hover:bg-gray-100 hover:text-gray-800"
        }
      `}
    >
      {/* Active left bar */}
      {isActive && (
        <motion.div
          layoutId="mod-active-bar"
          className="absolute left-0 top-2 bottom-2 w-0.5 rounded-full bg-indigo-500"
        />
      )}

      <item.icon
        size={17}
        strokeWidth={isActive ? 2.2 : 1.8}
        className={isActive ? "text-indigo-600 flex-shrink-0" : "flex-shrink-0 text-gray-400 group-hover:text-gray-600 transition-colors"}
      />

      <AnimatePresence>
        {!collapsed && (
          <motion.span
            initial={{ opacity: 0, width: 0 }}
            animate={{ opacity: 1, width: "auto" }}
            exit={{ opacity: 0, width: 0 }}
            transition={{ duration: 0.18 }}
            className={`text-sm font-medium whitespace-nowrap overflow-hidden ${isActive ? "text-indigo-700" : ""}`}
          >
            {item.label}
          </motion.span>
        )}
      </AnimatePresence>

      {/* Tooltip when collapsed */}
      {collapsed && (
        <div className="absolute left-full ml-3 px-2.5 py-1.5 bg-gray-900 text-white text-xs rounded-lg
          opacity-0 group-hover:opacity-100 pointer-events-none whitespace-nowrap z-50
          shadow-lg transition-opacity duration-150">
          {item.label}
          <div className="absolute right-full top-1/2 -translate-y-1/2 border-4 border-transparent border-r-gray-900" />
        </div>
      )}
    </motion.button>
  );
}

// ── ModeratorSidebar ──────────────────────────────────────────────
export function ModeratorSidebar({ user, activeNav, onNavChange, onLogout }) {
  const [collapsed, setCollapsed] = useState(false);
  const [notifCount] = useState(3);

  return (
    <motion.aside
      animate={{ width: collapsed ? 72 : 248 }}
      transition={{ type: "spring", stiffness: 320, damping: 32 }}
      className="h-screen flex flex-col bg-white border-r border-gray-200 overflow-hidden flex-shrink-0"
      style={{ minWidth: collapsed ? 72 : 248 }}
    >
      {/* ── Header ── */}
      <div className={`flex items-center border-b border-gray-100 px-4 py-4 ${collapsed ? "justify-center" : "gap-3"}`}>
        {!collapsed && (
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="flex items-center gap-2.5 flex-1">
            <div className="w-8 h-8 rounded-lg bg-indigo-600 flex items-center justify-center flex-shrink-0">
              <Radio size={14} className="text-white" />
            </div>
            <div>
              <p className="text-gray-900 font-bold text-sm leading-none tracking-tight">StreamCMS</p>
              <p className="text-gray-400 text-xs mt-0.5">Moderator</p>
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

      {/* ── Nav groups ── */}
      <nav className="flex-1 overflow-y-auto py-4 px-3 space-y-5">
        {NAV_ITEMS.map((group) => (
          <div key={group.group}>
            <AnimatePresence>
              {!collapsed && (
                <motion.p
                  initial={{ opacity: 0 }}
                  animate={{ opacity: 1 }}
                  exit={{ opacity: 0 }}
                  className="text-[10px] font-semibold text-gray-400 uppercase tracking-widest px-3 mb-1.5"
                >
                  {group.group}
                </motion.p>
              )}
            </AnimatePresence>
            {collapsed && <div className="my-1 mx-2 h-px bg-gray-100" />}
            <div className="space-y-0.5">
              {group.items.map(item => (
                <NavButton
                  key={item.id}
                  item={item}
                  active={activeNav}
                  onClick={onNavChange}
                  collapsed={collapsed}
                />
              ))}
            </div>
          </div>
        ))}
      </nav>

      {/* ── Footer: user + logout ── */}
      <div className="border-t border-gray-100 p-3 space-y-2">
        {/* Notification pill */}
        {!collapsed && notifCount > 0 && (
          <motion.div
            initial={{ opacity: 0, y: 6 }}
            animate={{ opacity: 1, y: 0 }}
            className="flex items-center gap-2.5 bg-amber-50 border border-amber-200 rounded-xl px-3 py-2"
          >
            <Bell size={14} className="text-amber-500 flex-shrink-0" />
            <span className="text-xs text-amber-700 font-medium flex-1">{notifCount} new notifications</span>
            <span className="w-5 h-5 bg-amber-500 text-white text-[10px] font-bold rounded-full flex items-center justify-center">
              {notifCount}
            </span>
          </motion.div>
        )}

        {/* User chip */}
        <div className={`flex items-center gap-3 px-2 py-2 rounded-xl bg-gray-50 ${collapsed ? "justify-center" : ""}`}>
          <div className="w-8 h-8 rounded-lg bg-indigo-100 flex items-center justify-center flex-shrink-0">
            <span className="text-indigo-700 text-xs font-bold">
              {user?.username?.slice(0, 2).toUpperCase() || "MO"}
            </span>
          </div>
          <AnimatePresence>
            {!collapsed && (
              <motion.div
                initial={{ opacity: 0 }}
                animate={{ opacity: 1 }}
                exit={{ opacity: 0 }}
                className="flex-1 min-w-0"
              >
                <p className="text-gray-800 text-xs font-semibold truncate">{user?.username || "Moderator"}</p>
                <p className="text-gray-400 text-[10px] truncate">Moderator</p>
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

export default ModeratorSidebar;