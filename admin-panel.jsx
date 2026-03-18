import { useState } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  LayoutDashboard, Video, Users, BookOpen, BarChart2,
  Settings, Shield, Database, Key, Activity, LogOut,
  Radio, Eye, Calendar, CreditCard, Zap, ChevronRight,
  Menu, X, Bell, Search, Sun, Moon, ChevronDown,
  Globe, Lock, AlertTriangle, Server, FileText, Layers
} from "lucide-react";

// ── Mock auth state ──────────────────────────────────────────────
const USERS = {
  superadmin: { name: "Alex Root", email: "root@stream.io", role: "superadmin", avatar: "AR" },
  admin:      { name: "Dana Wei",  email: "dana@stream.io",  role: "admin",      avatar: "DW" },
};

// ── Nav config ───────────────────────────────────────────────────
const adminNav = [
  { label: "Dashboard",    icon: LayoutDashboard, id: "dashboard" },
  { label: "Livestreams",  icon: Radio,           id: "livestreams" },
  { label: "Videos",       icon: Video,           id: "videos" },
  { label: "Bookings",     icon: BookOpen,        id: "bookings" },
  { label: "Analytics",    icon: BarChart2,       id: "analytics" },
  { label: "Viewers",      icon: Eye,             id: "viewers" },
  { label: "Settings",     icon: Settings,        id: "settings" },
];

const superAdminExtra = [
  { label: "Super Panel",   icon: Shield,    id: "superpanel",   group: true },
  { label: "Admins",        icon: Users,     id: "admins" },
  { label: "Stream Config", icon: Zap,       id: "streamconfig" },
  { label: "Stream Events", icon: Activity,  id: "streamevents" },
  { label: "DB Migrations", icon: Database,  id: "migrations" },
  { label: "Access Keys",   icon: Key,       id: "accesskeys" },
  { label: "System Logs",   icon: Server,    id: "systemlogs" },
  { label: "Global Config", icon: Globe,     id: "globalconfig" },
];

// ── Mock content ─────────────────────────────────────────────────
const STATS = {
  dashboard: [
    { label: "Live Streams",    value: "12",      delta: "+3",   color: "#6ee7b7" },
    { label: "Total Viewers",   value: "48,230",  delta: "+12%", color: "#93c5fd" },
    { label: "Bookings Today",  value: "284",     delta: "+8%",  color: "#fcd34d" },
    { label: "Revenue (LKR)",   value: "1.2M",    delta: "+22%", color: "#f9a8d4" },
  ],
  admins: [
    { label: "Total Admins",    value: "8",      delta: "active",color: "#6ee7b7" },
    { label: "Super Admins",    value: "2",      delta: "root",  color: "#f9a8d4" },
    { label: "Sessions Today",  value: "34",     delta: "logins",color: "#93c5fd" },
    { label: "Failed Logins",   value: "3",      delta: "alert", color: "#fcd34d" },
  ],
  migrations: [
    { label: "Applied",         value: "42",     delta: "ok",    color: "#6ee7b7" },
    { label: "Pending",         value: "1",      delta: "warn",  color: "#fcd34d" },
    { label: "Rolled Back",     value: "2",      delta: "info",  color: "#93c5fd" },
    { label: "DB Size",         value: "2.4 GB", delta: "total", color: "#f9a8d4" },
  ],
};

function getStats(id) {
  return STATS[id] || STATS.dashboard;
}

// ── Table mock data ───────────────────────────────────────────────
const TABLE_DATA = {
  livestreams: {
    cols: ["Title", "Status", "Viewers", "Scheduled At", "Created By"],
    rows: [
      ["Sunday Worship Live", "LIVE", "1,240", "2026-03-18 09:00", "admin@stream.io"],
      ["Evening Prayer", "SCHEDULED", "—", "2026-03-18 18:00", "dana@stream.io"],
      ["Youth Conference", "ENDED", "3,891", "2026-03-15 14:00", "root@stream.io"],
      ["Healing Service", "LIVE", "872", "2026-03-18 11:30", "admin@stream.io"],
    ],
    badge: { LIVE: "bg-emerald-500/20 text-emerald-400", SCHEDULED: "bg-amber-500/20 text-amber-400", ENDED: "bg-slate-500/20 text-slate-400" },
    badgeCol: 1,
  },
  bookings: {
    cols: ["Name", "Event", "Date", "Price", "Status"],
    rows: [
      ["Amara Silva", "Sunday Worship Live", "2026-03-18", "LKR 500", "confirmed"],
      ["Rohan Perera", "Evening Prayer", "2026-03-18", "LKR 300", "pending"],
      ["Nilufar Rashid", "Youth Conference", "2026-03-15", "LKR 750", "confirmed"],
      ["Kasun Jayawardena", "Healing Service", "2026-03-18", "LKR 500", "cancelled"],
    ],
    badge: { confirmed: "bg-emerald-500/20 text-emerald-400", pending: "bg-amber-500/20 text-amber-400", cancelled: "bg-rose-500/20 text-rose-400" },
    badgeCol: 4,
  },
  admins: {
    cols: ["Username", "Role", "Active", "Last Login"],
    rows: [
      ["root@stream.io", "superadmin", "Yes", "2026-03-18 08:12"],
      ["dana@stream.io", "admin", "Yes", "2026-03-18 09:45"],
      ["mark@stream.io", "admin", "No", "2026-03-10 14:00"],
      ["priya@stream.io", "admin", "Yes", "2026-03-17 22:31"],
    ],
    badge: { superadmin: "bg-rose-500/20 text-rose-400", admin: "bg-blue-500/20 text-blue-400" },
    badgeCol: 1,
  },
  migrations: {
    cols: ["Migration Name", "Checksum", "Applied At", "Rolled Back"],
    rows: [
      ["20240101_init_schema", "abc123", "2024-01-01 10:00", "No"],
      ["20240215_add_booking", "def456", "2024-02-15 11:00", "No"],
      ["20240310_stream_event", "ghi789", "2024-03-10 09:30", "Yes"],
      ["20240401_analytics",   "jkl012", "pending", "—"],
    ],
    badge: { Yes: "bg-rose-500/20 text-rose-400", No: "bg-emerald-500/20 text-emerald-400", pending: "bg-amber-500/20 text-amber-400" },
    badgeCol: 3,
  },
};

function getTable(id) {
  return TABLE_DATA[id] || TABLE_DATA.livestreams;
}

// ── Sidebar Item ─────────────────────────────────────────────────
function NavItem({ item, active, onClick, collapsed }) {
  return (
    <motion.button
      onClick={() => onClick(item.id)}
      whileHover={{ x: collapsed ? 0 : 4 }}
      whileTap={{ scale: 0.97 }}
      className={`
        w-full flex items-center gap-3 px-3 py-2.5 rounded-xl transition-all duration-200 group relative
        ${active
          ? "bg-gradient-to-r from-indigo-600/40 to-violet-600/20 text-white shadow-lg shadow-indigo-900/30"
          : "text-slate-400 hover:text-slate-200 hover:bg-white/5"
        }
      `}
    >
      <item.icon size={18} className={active ? "text-indigo-300" : "text-slate-500 group-hover:text-slate-300"} />
      <AnimatePresence>
        {!collapsed && (
          <motion.span
            initial={{ opacity: 0, width: 0 }}
            animate={{ opacity: 1, width: "auto" }}
            exit={{ opacity: 0, width: 0 }}
            className="text-sm font-medium whitespace-nowrap overflow-hidden"
          >
            {item.label}
          </motion.span>
        )}
      </AnimatePresence>
      {active && !collapsed && (
        <motion.div layoutId="activeIndicator" className="ml-auto w-1.5 h-1.5 rounded-full bg-indigo-400" />
      )}
      {collapsed && (
        <div className="absolute left-full ml-3 px-2 py-1 bg-slate-800 text-white text-xs rounded-lg opacity-0 group-hover:opacity-100 pointer-events-none whitespace-nowrap z-50 border border-slate-700">
          {item.label}
        </div>
      )}
    </motion.button>
  );
}

// ── Sidebar ───────────────────────────────────────────────────────
function Sidebar({ user, active, onNav, collapsed, onToggle }) {
  const isSA = user.role === "superadmin";

  return (
    <motion.aside
      animate={{ width: collapsed ? 68 : 240 }}
      transition={{ type: "spring", stiffness: 300, damping: 30 }}
      className="h-screen flex flex-col bg-slate-900 border-r border-slate-800/80 relative z-20 overflow-hidden"
      style={{ minWidth: collapsed ? 68 : 240 }}
    >
      {/* Logo */}
      <div className="flex items-center gap-3 px-4 py-5 border-b border-slate-800/60">
        <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-indigo-500 to-violet-600 flex items-center justify-center flex-shrink-0">
          <Radio size={14} className="text-white" />
        </div>
        <AnimatePresence>
          {!collapsed && (
            <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}>
              <p className="text-white font-bold text-sm tracking-tight leading-none">StreamCMS</p>
              <p className="text-slate-500 text-xs mt-0.5">Live Platform</p>
            </motion.div>
          )}
        </AnimatePresence>
        <button
          onClick={onToggle}
          className="ml-auto text-slate-500 hover:text-slate-300 transition-colors flex-shrink-0"
        >
          {collapsed ? <ChevronRight size={16} /> : <Menu size={16} />}
        </button>
      </div>

      {/* Nav */}
      <nav className="flex-1 overflow-y-auto px-3 py-4 space-y-1 scrollbar-none">
        {adminNav.map(item => (
          <NavItem key={item.id} item={item} active={active === item.id} onClick={onNav} collapsed={collapsed} />
        ))}

        {/* Super Admin Section */}
        {isSA && (
          <>
            <AnimatePresence>
              {!collapsed && (
                <motion.div
                  initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
                  className="pt-4 pb-1 px-3"
                >
                  <div className="flex items-center gap-2 text-xs font-semibold text-rose-400/80 uppercase tracking-widest">
                    <Shield size={10} />
                    <span>Super Admin</span>
                  </div>
                </motion.div>
              )}
            </AnimatePresence>
            {collapsed && <div className="my-2 mx-3 h-px bg-rose-500/20" />}
            {superAdminExtra.filter(i => !i.group).map(item => (
              <NavItem key={item.id} item={item} active={active === item.id} onClick={onNav} collapsed={collapsed} />
            ))}
          </>
        )}
      </nav>

      {/* User */}
      <div className="border-t border-slate-800/60 p-3">
        <div className={`flex items-center gap-3 px-2 py-2 rounded-xl bg-white/5 ${collapsed ? "justify-center" : ""}`}>
          <div className={`w-7 h-7 rounded-lg flex items-center justify-center text-xs font-bold flex-shrink-0 ${user.role === "superadmin" ? "bg-gradient-to-br from-rose-500 to-orange-500" : "bg-gradient-to-br from-indigo-500 to-blue-500"}`}>
            {user.avatar}
          </div>
          <AnimatePresence>
            {!collapsed && (
              <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} className="flex-1 min-w-0">
                <p className="text-white text-xs font-medium truncate">{user.name}</p>
                <p className="text-slate-500 text-xs truncate capitalize">{user.role}</p>
              </motion.div>
            )}
          </AnimatePresence>
          {!collapsed && <LogOut size={14} className="text-slate-600 hover:text-slate-400 cursor-pointer transition-colors flex-shrink-0" />}
        </div>
      </div>
    </motion.aside>
  );
}

// ── Stat Card ─────────────────────────────────────────────────────
function StatCard({ s, i }) {
  return (
    <motion.div
      initial={{ opacity: 0, y: 20 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ delay: i * 0.07 }}
      className="bg-slate-800/50 border border-slate-700/50 rounded-2xl p-5 relative overflow-hidden"
    >
      <div className="absolute top-0 right-0 w-24 h-24 rounded-full opacity-10 blur-2xl" style={{ background: s.color }} />
      <p className="text-slate-400 text-xs font-medium uppercase tracking-wider">{s.label}</p>
      <p className="text-white text-3xl font-bold mt-2 tracking-tight">{s.value}</p>
      <span className="inline-block mt-2 text-xs px-2 py-0.5 rounded-full bg-white/5 text-slate-400">{s.delta}</span>
    </motion.div>
  );
}

// ── Data Table ────────────────────────────────────────────────────
function DataTable({ id }) {
  const { cols, rows, badge, badgeCol } = getTable(id);
  return (
    <motion.div
      initial={{ opacity: 0, y: 16 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{ delay: 0.25 }}
      className="bg-slate-800/40 border border-slate-700/50 rounded-2xl overflow-hidden"
    >
      <div className="overflow-x-auto">
        <table className="w-full text-sm">
          <thead>
            <tr className="border-b border-slate-700/50">
              {cols.map(c => (
                <th key={c} className="text-left px-5 py-3.5 text-slate-400 text-xs font-semibold uppercase tracking-wider">{c}</th>
              ))}
            </tr>
          </thead>
          <tbody>
            {rows.map((row, ri) => (
              <motion.tr
                key={ri}
                initial={{ opacity: 0, x: -10 }}
                animate={{ opacity: 1, x: 0 }}
                transition={{ delay: 0.3 + ri * 0.05 }}
                className="border-b border-slate-700/30 hover:bg-white/3 transition-colors"
              >
                {row.map((cell, ci) => (
                  <td key={ci} className="px-5 py-3.5 text-slate-300">
                    {ci === badgeCol && badge?.[cell] ? (
                      <span className={`px-2 py-0.5 rounded-full text-xs font-medium ${badge[cell]}`}>{cell}</span>
                    ) : (
                      <span className={ci === 0 ? "text-white font-medium" : ""}>{cell}</span>
                    )}
                  </td>
                ))}
              </motion.tr>
            ))}
          </tbody>
        </table>
      </div>
    </motion.div>
  );
}

// ── Main Content ──────────────────────────────────────────────────
const PAGE_TITLES = {
  dashboard: "Dashboard", livestreams: "Livestreams", videos: "Videos",
  bookings: "Bookings", analytics: "Analytics", viewers: "Viewers", settings: "Settings",
  admins: "Admin Management", streamconfig: "Stream Configuration", streamevents: "Stream Events",
  migrations: "DB Migrations", accesskeys: "Access Keys", systemlogs: "System Logs", globalconfig: "Global Config",
};

function MainContent({ active, user }) {
  const stats = getStats(active);
  const hasTable = !!TABLE_DATA[active];

  return (
    <div className="flex-1 overflow-y-auto bg-slate-950 min-h-screen">
      {/* Topbar */}
      <div className="sticky top-0 z-10 bg-slate-950/80 backdrop-blur-md border-b border-slate-800/60 px-8 py-4 flex items-center gap-4">
        <div>
          <h1 className="text-white font-bold text-lg leading-none">{PAGE_TITLES[active] || active}</h1>
          <p className="text-slate-500 text-xs mt-0.5 capitalize">{user.role} · {user.email}</p>
        </div>
        <div className="ml-auto flex items-center gap-3">
          <div className="flex items-center gap-2 bg-slate-800/60 border border-slate-700/50 rounded-xl px-3 py-2">
            <Search size={14} className="text-slate-500" />
            <input placeholder="Search…" className="bg-transparent text-slate-300 text-sm outline-none placeholder-slate-600 w-36" />
          </div>
          <button className="relative w-9 h-9 rounded-xl bg-slate-800/60 border border-slate-700/50 flex items-center justify-center text-slate-400 hover:text-white transition-colors">
            <Bell size={16} />
            <span className="absolute top-2 right-2 w-1.5 h-1.5 bg-rose-500 rounded-full" />
          </button>
        </div>
      </div>

      <div className="p-8 space-y-6">
        {/* Stats grid */}
        <div className="grid grid-cols-2 xl:grid-cols-4 gap-4">
          {stats.map((s, i) => <StatCard key={i} s={s} i={i} />)}
        </div>

        {/* Super admin badge */}
        {user.role === "superadmin" && (
          <motion.div
            initial={{ opacity: 0, scale: 0.97 }}
            animate={{ opacity: 1, scale: 1 }}
            transition={{ delay: 0.2 }}
            className="flex items-center gap-3 bg-gradient-to-r from-rose-900/30 to-orange-900/20 border border-rose-800/40 rounded-2xl px-5 py-3"
          >
            <Shield size={16} className="text-rose-400" />
            <p className="text-rose-300 text-sm font-medium">Super Admin session active — full system access granted</p>
            <AlertTriangle size={14} className="text-rose-400/60 ml-auto" />
          </motion.div>
        )}

        {/* Table */}
        {hasTable ? (
          <>
            <div className="flex items-center justify-between">
              <h2 className="text-slate-300 font-semibold text-sm">{PAGE_TITLES[active]} Records</h2>
              <button className="text-xs text-indigo-400 hover:text-indigo-300 transition-colors flex items-center gap-1">
                View all <ChevronRight size={12} />
              </button>
            </div>
            <DataTable id={active} />
          </>
        ) : (
          <motion.div
            initial={{ opacity: 0 }}
            animate={{ opacity: 1 }}
            transition={{ delay: 0.3 }}
            className="bg-slate-800/30 border border-slate-700/40 rounded-2xl p-16 text-center"
          >
            <Layers size={32} className="text-slate-600 mx-auto mb-3" />
            <p className="text-slate-500 text-sm">Select a section with data to display its records.</p>
          </motion.div>
        )}
      </div>
    </div>
  );
}

// ── Login ─────────────────────────────────────────────────────────
function Login({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [error, setError] = useState("");

  const handle = () => {
    if (username === "superadmin" && password === "super123") return onLogin("superadmin");
    if (username === "admin" && password === "admin123") return onLogin("admin");
    setError("Invalid credentials. Try admin/admin123 or superadmin/super123");
  };

  return (
    <div className="min-h-screen bg-slate-950 flex items-center justify-center p-4">
      {/* Ambient */}
      <div className="absolute inset-0 overflow-hidden pointer-events-none">
        <div className="absolute top-1/3 left-1/2 -translate-x-1/2 -translate-y-1/2 w-96 h-96 bg-indigo-600/10 rounded-full blur-3xl" />
        <div className="absolute bottom-1/4 right-1/4 w-64 h-64 bg-violet-600/10 rounded-full blur-3xl" />
      </div>

      <motion.div
        initial={{ opacity: 0, y: 30 }}
        animate={{ opacity: 1, y: 0 }}
        transition={{ type: "spring", stiffness: 200, damping: 20 }}
        className="relative w-full max-w-sm"
      >
        <div className="bg-slate-900/80 backdrop-blur-xl border border-slate-700/60 rounded-3xl p-8 shadow-2xl shadow-black/50">
          <div className="flex items-center gap-3 mb-8">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-indigo-500 to-violet-600 flex items-center justify-center">
              <Radio size={18} className="text-white" />
            </div>
            <div>
              <p className="text-white font-bold text-base">StreamCMS</p>
              <p className="text-slate-500 text-xs">Admin Portal</p>
            </div>
          </div>

          <div className="space-y-4">
            <div>
              <label className="text-slate-400 text-xs font-medium uppercase tracking-wider block mb-1.5">Username</label>
              <input
                value={username}
                onChange={e => setUsername(e.target.value)}
                placeholder="admin or superadmin"
                className="w-full bg-slate-800/60 border border-slate-700/60 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500/60 transition-colors placeholder-slate-600"
              />
            </div>
            <div>
              <label className="text-slate-400 text-xs font-medium uppercase tracking-wider block mb-1.5">Password</label>
              <input
                type="password"
                value={password}
                onChange={e => setPassword(e.target.value)}
                onKeyDown={e => e.key === "Enter" && handle()}
                placeholder="••••••••"
                className="w-full bg-slate-800/60 border border-slate-700/60 rounded-xl px-4 py-3 text-white text-sm outline-none focus:border-indigo-500/60 transition-colors placeholder-slate-600"
              />
            </div>

            {error && (
              <motion.p initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="text-rose-400 text-xs bg-rose-500/10 border border-rose-500/20 rounded-xl px-3 py-2">
                {error}
              </motion.p>
            )}

            <motion.button
              whileHover={{ scale: 1.02 }}
              whileTap={{ scale: 0.98 }}
              onClick={handle}
              className="w-full bg-gradient-to-r from-indigo-600 to-violet-600 text-white font-semibold rounded-xl py-3 text-sm hover:from-indigo-500 hover:to-violet-500 transition-all shadow-lg shadow-indigo-900/40"
            >
              Sign In
            </motion.button>
          </div>

          <div className="mt-6 pt-5 border-t border-slate-800 space-y-1">
            <p className="text-slate-600 text-xs font-medium mb-2">Demo credentials:</p>
            <p className="text-slate-500 text-xs font-mono bg-slate-800/40 rounded-lg px-3 py-1.5">admin / admin123</p>
            <p className="text-slate-500 text-xs font-mono bg-slate-800/40 rounded-lg px-3 py-1.5">superadmin / super123</p>
          </div>
        </div>
      </motion.div>
    </div>
  );
}

// ── App ───────────────────────────────────────────────────────────
export default function App() {
  const [loggedIn, setLoggedIn] = useState(false);
  const [userKey, setUserKey] = useState(null);
  const [active, setActive] = useState("dashboard");
  const [collapsed, setCollapsed] = useState(false);

  const handleLogin = (key) => {
    setUserKey(key);
    setLoggedIn(true);
    setActive("dashboard");
  };

  if (!loggedIn) return <Login onLogin={handleLogin} />;

  const user = USERS[userKey];

  return (
    <div className="flex h-screen bg-slate-950 overflow-hidden font-sans">
      <Sidebar
        user={user}
        active={active}
        onNav={setActive}
        collapsed={collapsed}
        onToggle={() => setCollapsed(p => !p)}
      />
      <AnimatePresence mode="wait">
        <motion.div
          key={active}
          initial={{ opacity: 0, x: 16 }}
          animate={{ opacity: 1, x: 0 }}
          exit={{ opacity: 0, x: -16 }}
          transition={{ duration: 0.18 }}
          className="flex-1 overflow-hidden"
        >
          <MainContent active={active} user={user} />
        </motion.div>
      </AnimatePresence>
    </div>
  );
}
