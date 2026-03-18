import { useState, useEffect } from "react";
import { motion, AnimatePresence } from "framer-motion";
import {
  Shield, Radio, LogIn, Eye, EyeOff, AlertCircle,
  LayoutDashboard, Video, BookOpen, BarChart2,
  Users, Database, Key, Server, Globe, Zap, Activity,
  Settings, FileText, Lock, Flag, MessageSquare,
  HelpCircle, ChevronRight, Menu, LogOut, Bell, AlertTriangle
} from "lucide-react";

// ─────────────────────────────────────────────────────────────────
//  MOCK AUTH  (mirrors AdminAuthService.js — swap for real axios)
// ─────────────────────────────────────────────────────────────────
const MOCK_DB = {
  moderator:   { password: "mod12345",   role: "MODERATOR",   username: "moderator" },
  super_admin: { password: "super123",   role: "SUPER_ADMIN",  username: "super_admin" },
};

function mockLogin(username, password) {
  return new Promise((res, rej) => {
    setTimeout(() => {
      const user = MOCK_DB[username];
      if (user && user.password === password) res({ ...user, token: "demo-jwt" });
      else rej(new Error("Invalid credentials"));
    }, 700);
  });
}

// ─────────────────────────────────────────────────────────────────
//  NAV DATA
// ─────────────────────────────────────────────────────────────────
const BASE_NAV_GROUPS = [
  { group: "Overview", items: [
    { id: "dashboard",   label: "Dashboard",   icon: LayoutDashboard },
    { id: "livestreams", label: "Livestreams",  icon: Radio },
    { id: "videos",      label: "Videos",       icon: Video },
  ]},
  { group: "Management", items: [
    { id: "bookings",    label: "Bookings",     icon: BookOpen },
    { id: "viewers",     label: "Viewers",      icon: Eye },
    { id: "comments",    label: "Comments",     icon: MessageSquare },
    { id: "reports",     label: "Reports",      icon: Flag },
  ]},
  { group: "Insights", items: [
    { id: "analytics",   label: "Analytics",    icon: BarChart2 },
  ]},
  { group: "System", items: [
    { id: "settings",    label: "Settings",     icon: Settings },
    { id: "help",        label: "Help",         icon: HelpCircle },
  ]},
];

const SUPER_ONLY = [
  { id: "admins",       label: "Admin Users",   icon: Users },
  { id: "streamconfig", label: "Stream Config", icon: Zap },
  { id: "streamevents", label: "Stream Events", icon: Activity },
  { id: "migrations",   label: "DB Migrations", icon: Database },
  { id: "accesskeys",   label: "Access Keys",   icon: Key },
  { id: "systemlogs",   label: "System Logs",   icon: Server },
  { id: "globalconfig", label: "Global Config", icon: Globe },
  { id: "security",     label: "Security",      icon: Lock },
];

const SYSTEM_NAV = [
  { id: "settings2", label: "Settings",  icon: Settings },
  { id: "reports2",  label: "Reports",   icon: FileText },
];

// ─────────────────────────────────────────────────────────────────
//  NAV BUTTON — shared by both sidebars
// ─────────────────────────────────────────────────────────────────
function NavBtn({ item, active, onClick, collapsed, accent = "indigo" }) {
  const isActive = active === item.id;
  const colors = {
    indigo: { bg: "bg-indigo-50", text: "text-indigo-700", bar: "bg-indigo-500", icon: "text-indigo-600" },
    rose:   { bg: "bg-rose-50",   text: "text-rose-700",   bar: "bg-rose-500",   icon: "text-rose-600"   },
  };
  const c = colors[accent];

  return (
    <motion.button
      onClick={() => onClick(item.id)}
      whileTap={{ scale: 0.97 }}
      className={`relative w-full flex items-center gap-3 px-3 py-2.5 rounded-xl text-left transition-all duration-150 group
        ${isActive ? `${c.bg} ${c.text}` : "text-gray-500 hover:bg-gray-50 hover:text-gray-800"}`}
    >
      {isActive && (
        <motion.div layoutId={`${accent}-bar`}
          className={`absolute left-0 top-2 bottom-2 w-0.5 rounded-full ${c.bar}`} />
      )}
      <item.icon size={16} strokeWidth={isActive ? 2.2 : 1.8}
        className={`flex-shrink-0 transition-colors ${isActive ? c.icon : "text-gray-400 group-hover:text-gray-600"}`} />
      <AnimatePresence>
        {!collapsed && (
          <motion.span initial={{ opacity: 0, width: 0 }} animate={{ opacity: 1, width: "auto" }}
            exit={{ opacity: 0, width: 0 }} transition={{ duration: 0.15 }}
            className={`text-sm font-medium whitespace-nowrap overflow-hidden ${isActive ? c.text : ""}`}>
            {item.label}
          </motion.span>
        )}
      </AnimatePresence>
      {collapsed && (
        <div className="absolute left-full ml-3 px-2.5 py-1.5 bg-gray-900 text-white text-xs rounded-lg
          opacity-0 group-hover:opacity-100 pointer-events-none whitespace-nowrap z-50 shadow-lg">
          {item.label}
        </div>
      )}
    </motion.button>
  );
}

// ─────────────────────────────────────────────────────────────────
//  MODERATOR SIDEBAR
// ─────────────────────────────────────────────────────────────────
function ModeratorSidebar({ user, active, onNav, onLogout }) {
  const [collapsed, setCollapsed] = useState(false);
  return (
    <motion.aside animate={{ width: collapsed ? 72 : 248 }} transition={{ type: "spring", stiffness: 300, damping: 30 }}
      className="h-screen flex flex-col bg-white border-r border-gray-200 overflow-hidden flex-shrink-0"
      style={{ minWidth: collapsed ? 72 : 248 }}>

      {/* Header */}
      <div className={`flex items-center border-b border-gray-100 px-4 py-4 ${collapsed ? "justify-center" : "gap-3"}`}>
        {!collapsed && (
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="flex items-center gap-2.5 flex-1">
            <div className="w-8 h-8 rounded-lg bg-indigo-600 flex items-center justify-center flex-shrink-0">
              <Radio size={14} className="text-white" />
            </div>
            <div>
              <p className="text-gray-900 font-bold text-sm leading-none">StreamCMS</p>
              <p className="text-indigo-500 text-[10px] font-semibold uppercase tracking-wider mt-0.5">Moderator</p>
            </div>
          </motion.div>
        )}
        <button onClick={() => setCollapsed(p => !p)}
          className="w-8 h-8 rounded-lg flex items-center justify-center text-gray-400 hover:bg-gray-100 transition-all">
          {collapsed ? <ChevronRight size={16} /> : <Menu size={16} />}
        </button>
      </div>

      {/* Nav */}
      <nav className="flex-1 overflow-y-auto py-4 px-3 space-y-4">
        {BASE_NAV_GROUPS.map(group => (
          <div key={group.group}>
            <AnimatePresence>
              {!collapsed && (
                <motion.p initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
                  className="text-[10px] font-semibold text-gray-400 uppercase tracking-widest px-3 mb-1.5">
                  {group.group}
                </motion.p>
              )}
            </AnimatePresence>
            {collapsed && <div className="my-1 mx-2 h-px bg-gray-100" />}
            <div className="space-y-0.5">
              {group.items.map(item => (
                <NavBtn key={item.id} item={item} active={active} onClick={onNav} collapsed={collapsed} accent="indigo" />
              ))}
            </div>
          </div>
        ))}
      </nav>

      {/* Footer */}
      <div className="border-t border-gray-100 p-3">
        <div className={`flex items-center gap-3 px-2 py-2 rounded-xl bg-gray-50 ${collapsed ? "justify-center" : ""}`}>
          <div className="w-8 h-8 rounded-lg bg-indigo-100 flex items-center justify-center flex-shrink-0">
            <span className="text-indigo-700 text-xs font-bold">{user?.username?.slice(0,2).toUpperCase()}</span>
          </div>
          <AnimatePresence>
            {!collapsed && (
              <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} className="flex-1 min-w-0">
                <p className="text-gray-800 text-xs font-semibold truncate">{user?.username}</p>
                <p className="text-gray-400 text-[10px]">Moderator</p>
              </motion.div>
            )}
          </AnimatePresence>
          {!collapsed && (
            <button onClick={onLogout} className="text-gray-400 hover:text-rose-500 transition-colors">
              <LogOut size={15} />
            </button>
          )}
        </div>
      </div>
    </motion.aside>
  );
}

// ─────────────────────────────────────────────────────────────────
//  SUPER ADMIN SIDEBAR
// ─────────────────────────────────────────────────────────────────
function SuperAdminSidebar({ user, active, onNav, onLogout }) {
  const [collapsed, setCollapsed] = useState(false);
  const [superOpen, setSuperOpen] = useState(true);

  return (
    <motion.aside animate={{ width: collapsed ? 72 : 260 }} transition={{ type: "spring", stiffness: 300, damping: 30 }}
      className="h-screen flex flex-col bg-white border-r border-gray-200 overflow-hidden flex-shrink-0 relative"
      style={{ minWidth: collapsed ? 72 : 260 }}>

      {/* Rose accent top bar */}
      <div className="absolute top-0 left-0 right-0 h-0.5 bg-gradient-to-r from-rose-500 via-orange-400 to-rose-500" />

      {/* Header */}
      <div className={`flex items-center border-b border-gray-100 px-4 pt-5 pb-4 ${collapsed ? "justify-center" : "gap-3"}`}>
        {!collapsed && (
          <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} className="flex items-center gap-2.5 flex-1">
            <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-rose-500 to-orange-500 flex items-center justify-center flex-shrink-0 shadow-md shadow-rose-200">
              <Shield size={14} className="text-white" />
            </div>
            <div>
              <p className="text-gray-900 font-bold text-sm leading-none">StreamCMS</p>
              <div className="flex items-center gap-1 mt-0.5">
                <span className="w-1.5 h-1.5 bg-rose-500 rounded-full animate-pulse" />
                <p className="text-rose-500 text-[10px] font-semibold uppercase tracking-wider">Super Admin</p>
              </div>
            </div>
          </motion.div>
        )}
        <button onClick={() => setCollapsed(p => !p)}
          className="w-8 h-8 rounded-lg flex items-center justify-center text-gray-400 hover:bg-gray-100 transition-all">
          {collapsed ? <ChevronRight size={16} /> : <Menu size={16} />}
        </button>
      </div>

      {/* Alert badge */}
      <AnimatePresence>
        {!collapsed && (
          <motion.div initial={{ opacity: 0, height: 0 }} animate={{ opacity: 1, height: "auto" }} exit={{ opacity: 0, height: 0 }} className="mx-3 mt-3">
            <div className="flex items-center gap-2 bg-rose-50 border border-rose-100 rounded-xl px-3 py-2">
              <AlertTriangle size={12} className="text-rose-500 flex-shrink-0" />
              <p className="text-rose-600 text-[11px] font-semibold">Full system access active</p>
            </div>
          </motion.div>
        )}
      </AnimatePresence>

      {/* Nav */}
      <nav className="flex-1 overflow-y-auto py-4 px-3">
        {/* Platform */}
        <AnimatePresence>
          {!collapsed && (
            <motion.p initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }}
              className="text-[10px] font-semibold text-gray-400 uppercase tracking-widest px-3 mb-1.5">
              Platform
            </motion.p>
          )}
        </AnimatePresence>
        <div className="space-y-0.5 mb-3">
          {BASE_NAV_GROUPS.flatMap(g => g.items).slice(0, 5).map(item => (
            <NavBtn key={item.id} item={item} active={active} onClick={onNav} collapsed={collapsed} accent="indigo" />
          ))}
        </div>

        {/* Divider */}
        <div className={`${collapsed ? "mx-2" : "mx-1"} h-px bg-rose-100 my-2`} />

        {/* Super section */}
        {!collapsed ? (
          <button onClick={() => setSuperOpen(p => !p)} className="w-full flex items-center gap-2 px-3 mb-1.5">
            <Shield size={10} className="text-rose-400" />
            <span className="text-[10px] font-semibold text-rose-400 uppercase tracking-widest flex-1 text-left">Super Admin</span>
            <ChevronRight size={11} className={`text-rose-300 transition-transform ${superOpen ? "rotate-90" : ""}`} />
          </button>
        ) : (
          <div className="flex justify-center mb-1"><Shield size={13} className="text-rose-400" /></div>
        )}

        <AnimatePresence>
          {(superOpen || collapsed) && (
            <motion.div initial={{ opacity: 0, height: 0 }} animate={{ opacity: 1, height: "auto" }} exit={{ opacity: 0, height: 0 }} className="space-y-0.5 mb-3">
              {SUPER_ONLY.map(item => (
                <NavBtn key={item.id} item={item} active={active} onClick={onNav} collapsed={collapsed} accent="rose" />
              ))}
            </motion.div>
          )}
        </AnimatePresence>

        <div className={`${collapsed ? "mx-2" : "mx-1"} h-px bg-gray-100 my-2`} />
        <div className="space-y-0.5">
          {SYSTEM_NAV.map(item => (
            <NavBtn key={item.id} item={item} active={active} onClick={onNav} collapsed={collapsed} accent="indigo" />
          ))}
        </div>
      </nav>

      {/* Footer */}
      <div className="border-t border-gray-100 p-3">
        <div className={`flex items-center gap-3 px-2 py-2 rounded-xl bg-rose-50 border border-rose-100 ${collapsed ? "justify-center" : ""}`}>
          <div className="w-8 h-8 rounded-lg bg-gradient-to-br from-rose-500 to-orange-500 flex items-center justify-center flex-shrink-0 shadow-sm">
            <span className="text-white text-xs font-bold">{user?.username?.slice(0,2).toUpperCase()}</span>
          </div>
          <AnimatePresence>
            {!collapsed && (
              <motion.div initial={{ opacity: 0 }} animate={{ opacity: 1 }} exit={{ opacity: 0 }} className="flex-1 min-w-0">
                <p className="text-gray-800 text-xs font-semibold truncate">{user?.username}</p>
                <p className="text-rose-500 text-[10px] font-semibold uppercase tracking-wide">SUPER_ADMIN</p>
              </motion.div>
            )}
          </AnimatePresence>
          {!collapsed && (
            <button onClick={onLogout} className="text-gray-400 hover:text-rose-500 transition-colors">
              <LogOut size={15} />
            </button>
          )}
        </div>
      </div>
    </motion.aside>
  );
}

// ─────────────────────────────────────────────────────────────────
//  LOGIN PAGE
// ─────────────────────────────────────────────────────────────────
function LoginPage({ onLogin }) {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [showPw, setShowPw] = useState(false);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState("");

  async function handleLogin() {
    setError(""); setLoading(true);
    try {
      const user = await mockLogin(username, password);
      onLogin(user);
    } catch (e) {
      setError(e.message);
    } finally {
      setLoading(false);
    }
  }

  return (
    <div className="min-h-screen bg-gray-50 flex items-center justify-center p-4">
      {/* Subtle grid bg */}
      <div className="absolute inset-0 bg-[linear-gradient(to_right,#f0f0f0_1px,transparent_1px),linear-gradient(to_bottom,#f0f0f0_1px,transparent_1px)] bg-[size:40px_40px] opacity-60" />

      <motion.div initial={{ opacity: 0, y: 24 }} animate={{ opacity: 1, y: 0 }} transition={{ type: "spring", stiffness: 200, damping: 22 }}
        className="relative w-full max-w-sm">

        <div className="bg-white rounded-3xl border border-gray-200 shadow-xl shadow-gray-200/80 p-8">
          {/* Logo */}
          <div className="flex items-center gap-3 mb-8">
            <div className="w-10 h-10 rounded-xl bg-gradient-to-br from-indigo-600 to-indigo-700 flex items-center justify-center shadow-lg shadow-indigo-200">
              <Radio size={18} className="text-white" />
            </div>
            <div>
              <p className="text-gray-900 font-bold text-lg leading-none">StreamCMS</p>
              <p className="text-gray-400 text-xs mt-0.5">Admin Portal</p>
            </div>
          </div>

          <h2 className="text-gray-800 font-bold text-xl mb-1">Welcome back</h2>
          <p className="text-gray-400 text-sm mb-6">Sign in to your admin account</p>

          <div className="space-y-4">
            <div>
              <label className="text-gray-600 text-xs font-semibold uppercase tracking-wider block mb-1.5">Username</label>
              <input value={username} onChange={e => setUsername(e.target.value)}
                placeholder="Enter username"
                className="w-full border border-gray-200 rounded-xl px-4 py-3 text-gray-800 text-sm outline-none
                  focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100 transition-all bg-gray-50 placeholder-gray-300" />
            </div>
            <div>
              <label className="text-gray-600 text-xs font-semibold uppercase tracking-wider block mb-1.5">Password</label>
              <div className="relative">
                <input type={showPw ? "text" : "password"} value={password}
                  onChange={e => setPassword(e.target.value)}
                  onKeyDown={e => e.key === "Enter" && handleLogin()}
                  placeholder="••••••••"
                  className="w-full border border-gray-200 rounded-xl px-4 py-3 text-gray-800 text-sm outline-none
                    focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100 transition-all bg-gray-50 placeholder-gray-300 pr-10" />
                <button onClick={() => setShowPw(p => !p)} type="button"
                  className="absolute right-3 top-1/2 -translate-y-1/2 text-gray-400 hover:text-gray-600">
                  {showPw ? <EyeOff size={16} /> : <Eye size={16} />}
                </button>
              </div>
            </div>

            <AnimatePresence>
              {error && (
                <motion.div initial={{ opacity: 0, height: 0 }} animate={{ opacity: 1, height: "auto" }} exit={{ opacity: 0, height: 0 }}
                  className="flex items-center gap-2.5 bg-rose-50 border border-rose-200 rounded-xl px-3 py-2.5">
                  <AlertCircle size={14} className="text-rose-500 flex-shrink-0" />
                  <p className="text-rose-600 text-xs font-medium">{error}</p>
                </motion.div>
              )}
            </AnimatePresence>

            <motion.button onClick={handleLogin} disabled={loading}
              whileHover={{ scale: 1.01 }} whileTap={{ scale: 0.98 }}
              className="w-full bg-indigo-600 text-white font-semibold rounded-xl py-3 text-sm
                hover:bg-indigo-700 transition-all shadow-md shadow-indigo-200 flex items-center justify-center gap-2 disabled:opacity-70">
              {loading ? (
                <motion.div animate={{ rotate: 360 }} transition={{ repeat: Infinity, duration: 0.8, ease: "linear" }}
                  className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full" />
              ) : (
                <><LogIn size={15} /><span>Sign In</ span></>
              )}
            </motion.button>
          </div>

          {/* Demo hints */}
          <div className="mt-6 pt-5 border-t border-gray-100">
            <p className="text-gray-400 text-xs font-semibold mb-2 uppercase tracking-wider">Demo Accounts</p>
            <div className="space-y-1.5">
              {[
                { u: "moderator",   p: "mod12345",  role: "MODERATOR",   color: "indigo" },
                { u: "super_admin", p: "super123",  role: "SUPER_ADMIN", color: "rose" },
              ].map(d => (
                <button key={d.u} onClick={() => { setUsername(d.u); setPassword(d.p); }}
                  className={`w-full flex items-center justify-between bg-${d.color}-50 border border-${d.color}-100
                    rounded-xl px-3 py-2 hover:bg-${d.color}-100 transition-colors text-left`}>
                  <span className="font-mono text-xs text-gray-700">{d.u} / {d.p}</span>
                  <span className={`text-[10px] font-bold text-${d.color}-600 uppercase tracking-wide`}>{d.role}</span>
                </button>
              ))}
            </div>
          </div>
        </div>
      </motion.div>
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────
//  MAIN CONTENT placeholder
// ─────────────────────────────────────────────────────────────────
const PAGE_TITLES = {
  dashboard: "Dashboard", livestreams: "Livestreams", videos: "Videos",
  bookings: "Bookings", viewers: "Viewers", analytics: "Analytics",
  comments: "Comments", reports: "Reports", help: "Help", settings: "Settings",
  admins: "Admin Users", streamconfig: "Stream Config", streamevents: "Stream Events",
  migrations: "DB Migrations", accesskeys: "Access Keys", systemlogs: "System Logs",
  globalconfig: "Global Config", security: "Security", settings2: "Settings", reports2: "Reports",
};

function MainArea({ active, user }) {
  const isSA = user.role === "SUPER_ADMIN";
  return (
    <div className="flex-1 bg-gray-50 flex flex-col overflow-hidden">
      {/* Topbar */}
      <div className="bg-white border-b border-gray-200 px-8 py-4 flex items-center gap-4">
        <div>
          <h1 className="text-gray-900 font-bold text-base leading-none">{PAGE_TITLES[active] || active}</h1>
          <p className="text-gray-400 text-xs mt-0.5">{user.username} · {user.role}</p>
        </div>
        <div className="ml-auto flex items-center gap-2">
          <button className="w-9 h-9 rounded-xl border border-gray-200 flex items-center justify-center text-gray-500 hover:bg-gray-50 transition-colors relative">
            <Bell size={16} />
            <span className="absolute top-2 right-2 w-1.5 h-1.5 bg-rose-500 rounded-full" />
          </button>
          <div className={`flex items-center gap-2 px-3 py-1.5 rounded-xl border ${isSA ? "bg-rose-50 border-rose-200" : "bg-indigo-50 border-indigo-200"}`}>
            <Shield size={13} className={isSA ? "text-rose-500" : "text-indigo-500"} />
            <span className={`text-xs font-bold uppercase tracking-wide ${isSA ? "text-rose-600" : "text-indigo-600"}`}>{user.role}</span>
          </div>
        </div>
      </div>

      {/* Body */}
      <AnimatePresence mode="wait">
        <motion.div key={active} initial={{ opacity: 0, y: 10 }} animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: -10 }} transition={{ duration: 0.15 }}
          className="flex-1 flex items-center justify-center p-12">
          <div className="text-center">
            <div className={`w-16 h-16 rounded-2xl flex items-center justify-center mx-auto mb-4 shadow-lg
              ${isSA ? "bg-gradient-to-br from-rose-500 to-orange-500 shadow-rose-200" : "bg-gradient-to-br from-indigo-500 to-indigo-700 shadow-indigo-200"}`}>
              <Shield size={28} className="text-white" />
            </div>
            <h2 className="text-gray-800 font-bold text-xl">{PAGE_TITLES[active] || active}</h2>
            <p className="text-gray-400 text-sm mt-1">Page content renders here</p>
            {isSA && active === "migrations" && (
              <div className="mt-4 inline-flex items-center gap-2 bg-rose-50 border border-rose-200 rounded-xl px-4 py-2">
                <AlertTriangle size={14} className="text-rose-500" />
                <span className="text-rose-600 text-xs font-semibold">Super Admin only — proceed with caution</span>
              </div>
            )}
          </div>
        </motion.div>
      </AnimatePresence>
    </div>
  );
}

// ─────────────────────────────────────────────────────────────────
//  APP ROOT
// ─────────────────────────────────────────────────────────────────
export default function App() {
  const [user, setUser] = useState(null);
  const [active, setActive] = useState("dashboard");

  function handleLogin(u) { setUser(u); setActive("dashboard"); }
  function handleLogout() { setUser(null); }

  if (!user) return <LoginPage onLogin={handleLogin} />;

  const isSA = user.role === "SUPER_ADMIN";

  return (
    <div className="flex h-screen bg-gray-50 overflow-hidden">
      {isSA
        ? <SuperAdminSidebar user={user} active={active} onNav={setActive} onLogout={handleLogout} />
        : <ModeratorSidebar  user={user} active={active} onNav={setActive} onLogout={handleLogout} />
      }
      <MainArea active={active} user={user} />
    </div>
  );
}
