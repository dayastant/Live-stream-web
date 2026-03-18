// src/pages/auth/LoginPage.jsx
import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { motion, AnimatePresence } from "framer-motion";
import {
    Eye, EyeOff, Radio, AlertCircle,
    ArrowRight, Shield, Wifi, Lock,
} from "lucide-react";
import { useAuth } from "../../context/AuthContext";

// ─────────────────────────────────────────────────────────────────
//  ANIMATION VARIANTS
// ─────────────────────────────────────────────────────────────────

const fadeUp = {
    hidden: { opacity: 0, y: 24 },
    visible: (i = 0) => ({
        opacity: 1,
        y: 0,
        transition: { delay: i * 0.08, duration: 0.5, ease: [0.22, 1, 0.36, 1] },
    }),
};

const fadeIn = {
    hidden: { opacity: 0 },
    visible: { opacity: 1, transition: { duration: 0.4 } },
};

// ─────────────────────────────────────────────────────────────────
//  FLOATING BACKGROUND ORBS
// ─────────────────────────────────────────────────────────────────

function BackgroundOrbs() {
    return (
        <div className="absolute inset-0 overflow-hidden pointer-events-none select-none">
            {/* Large soft orb — top left */}
            <motion.div
                animate={{ x: [0, 18, 0], y: [0, -12, 0] }}
                transition={{ duration: 12, repeat: Infinity, ease: "easeInOut" }}
                className="absolute -top-32 -left-32 w-[520px] h-[520px] rounded-full"
                style={{
                    background:
                        "radial-gradient(circle, rgba(99,102,241,0.12) 0%, transparent 70%)",
                }}
            />
            {/* Medium orb — bottom right */}
            <motion.div
                animate={{ x: [0, -14, 0], y: [0, 16, 0] }}
                transition={{ duration: 10, repeat: Infinity, ease: "easeInOut", delay: 2 }}
                className="absolute -bottom-24 -right-24 w-[400px] h-[400px] rounded-full"
                style={{
                    background:
                        "radial-gradient(circle, rgba(244,114,182,0.09) 0%, transparent 70%)",
                }}
            />
            {/* Small accent — top right */}
            <motion.div
                animate={{ scale: [1, 1.15, 1] }}
                transition={{ duration: 8, repeat: Infinity, ease: "easeInOut", delay: 1 }}
                className="absolute top-24 right-16 w-48 h-48 rounded-full"
                style={{
                    background:
                        "radial-gradient(circle, rgba(16,185,129,0.07) 0%, transparent 70%)",
                }}
            />

            {/* Subtle dot-grid texture */}
            <div
                className="absolute inset-0 opacity-[0.025]"
                style={{
                    backgroundImage:
                        "radial-gradient(circle, #64748b 1px, transparent 1px)",
                    backgroundSize: "28px 28px",
                }}
            />
        </div>
    );
}

// ─────────────────────────────────────────────────────────────────
//  DECORATIVE LEFT PANEL (desktop only)
// ─────────────────────────────────────────────────────────────────

function LeftPanel() {
    const features = [
        { icon: Radio, label: "Live Streaming", desc: "Manage real-time broadcasts" },
        { icon: Shield, label: "Role-Based Access", desc: "SUPER_ADMIN & MODERATOR roles" },
        { icon: Wifi, label: "Real-Time Analytics", desc: "Viewer & engagement insights" },
        { icon: Lock, label: "Secure by Default", desc: "JWT + Spring Security backend" },
    ];

    return (
        <div className="hidden lg:flex flex-col justify-between p-12 relative overflow-hidden">
            {/* Gradient card bg */}
            <div className="absolute inset-0 bg-gradient-to-br from-indigo-600 via-indigo-700 to-violet-800" />
            <div
                className="absolute inset-0 opacity-10"
                style={{
                    backgroundImage:
                        "radial-gradient(circle at 2px 2px, white 1px, transparent 0)",
                    backgroundSize: "32px 32px",
                }}
            />

            {/* Floating glows */}
            <div className="absolute top-0 right-0 w-72 h-72 bg-violet-500/20 rounded-full blur-3xl -translate-y-1/3 translate-x-1/3" />
            <div className="absolute bottom-0 left-0 w-56 h-56 bg-indigo-400/20 rounded-full blur-3xl translate-y-1/3 -translate-x-1/3" />

            <div className="relative z-10">
                {/* Brand */}
                <motion.div
                    variants={fadeUp} initial="hidden" animate="visible" custom={0}
                    className="flex items-center gap-3 mb-16"
                >
                    <div className="w-10 h-10 rounded-xl bg-white/20 backdrop-blur-sm flex items-center justify-center border border-white/20">
                        <Radio size={18} className="text-white" />
                    </div>
                    <div>
                        <p className="text-white font-bold text-lg leading-none tracking-tight">StreamCMS</p>
                        <p className="text-indigo-200 text-xs mt-0.5">Live Platform</p>
                    </div>
                </motion.div>

                {/* Headline */}
                <motion.div variants={fadeUp} initial="hidden" animate="visible" custom={1} className="mb-12">
                    <h1 className="text-white font-black text-4xl leading-tight tracking-tight">
                        Manage your<br />
                        <span className="text-indigo-200">live streams</span><br />
                        with confidence.
                    </h1>
                    <p className="text-indigo-200/80 text-sm mt-4 leading-relaxed max-w-xs">
                        A unified admin platform for broadcasts, bookings, analytics, and team management.
                    </p>
                </motion.div>

                {/* Feature list */}
                <div className="space-y-4">
                    {features.map((f, i) => (
                        <motion.div
                            key={f.label}
                            variants={fadeUp} initial="hidden" animate="visible" custom={2 + i}
                            className="flex items-center gap-3"
                        >
                            <div className="w-8 h-8 rounded-lg bg-white/10 border border-white/15 flex items-center justify-center flex-shrink-0">
                                <f.icon size={14} className="text-indigo-200" />
                            </div>
                            <div>
                                <p className="text-white text-sm font-semibold leading-none">{f.label}</p>
                                <p className="text-indigo-200/70 text-xs mt-0.5">{f.desc}</p>
                            </div>
                        </motion.div>
                    ))}
                </div>
            </div>

            {/* Footer */}
            <motion.p
                variants={fadeIn} initial="hidden" animate="visible"
                className="relative z-10 text-indigo-300/50 text-xs"
            >
                © {new Date().getFullYear()} StreamCMS. All rights reserved.
            </motion.p>
        </div>
    );
}

// ─────────────────────────────────────────────────────────────────
//  INPUT FIELD
// ─────────────────────────────────────────────────────────────────

function InputField({
    label, id, type = "text", value, onChange,
    placeholder, autoComplete, suffix, error,
}) {
    return (
        <div>
            <label
                htmlFor={id}
                className="block text-xs font-semibold text-gray-500 uppercase tracking-wider mb-1.5"
            >
                {label}
            </label>
            <div className="relative">
                <input
                    id={id}
                    type={type}
                    value={value}
                    onChange={onChange}
                    placeholder={placeholder}
                    autoComplete={autoComplete}
                    className={`
            w-full px-4 py-3 rounded-xl text-sm text-gray-800
            bg-gray-50 border transition-all duration-200 outline-none
            placeholder-gray-300 pr-${suffix ? "11" : "4"}
            ${error
                            ? "border-rose-300 focus:border-rose-400 focus:ring-2 focus:ring-rose-100"
                            : "border-gray-200 focus:border-indigo-400 focus:ring-2 focus:ring-indigo-100 focus:bg-white"
                        }
          `}
                />
                {suffix && (
                    <div className="absolute right-3 top-1/2 -translate-y-1/2">{suffix}</div>
                )}
            </div>
        </div>
    );
}

// ─────────────────────────────────────────────────────────────────
//  LOGIN PAGE
// ─────────────────────────────────────────────────────────────────

export default function LoginPage() {
    const navigate = useNavigate();
    const { login, user } = useAuth();

    const [username, setUsername] = useState("");
    const [password, setPassword] = useState("");
    const [showPw, setShowPw] = useState(false);
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState("");
    const [fieldErr, setFieldErr] = useState({ username: "", password: "" });

    // Already logged in → redirect
    useEffect(() => {
        if (user) navigate("/dashboard", { replace: true });
    }, [user, navigate]);

    // ── Validation ─────────────────────────────────────────────────
    function validate() {
        const errs = { username: "", password: "" };
        let valid = true;

        if (!username.trim()) {
            errs.username = "Username is required.";
            valid = false;
        } else if (username.length < 3 || username.length > 50) {
            errs.username = "Must be 3–50 characters.";
            valid = false;
        } else if (!/^[a-zA-Z0-9._-]+$/.test(username)) {
            errs.username = "Only letters, numbers, dots, _ and - allowed.";
            valid = false;
        }

        if (!password) {
            errs.password = "Password is required.";
            valid = false;
        } else if (password.length < 8) {
            errs.password = "Minimum 8 characters.";
            valid = false;
        }

        setFieldErr(errs);
        return valid;
    }

    // ── Submit ──────────────────────────────────────────────────────
    async function handleSubmit(e) {
        e?.preventDefault();
        setError("");
        if (!validate()) return;

        setLoading(true);
        try {
            await login(username.trim(), password);
            navigate("/dashboard", { replace: true });
        } catch (err) {
            const msg =
                err?.response?.data?.message ||
                err?.message ||
                "Invalid username or password.";
            setError(msg);
        } finally {
            setLoading(false);
        }
    }

    // ─────────────────────────────────────────────────────────────
    //  RENDER
    // ─────────────────────────────────────────────────────────────

    return (
        <div className="min-h-screen bg-white flex">

            {/* ── Left decorative panel (lg+) ── */}
            <div className="lg:w-[480px] xl:w-[520px] flex-shrink-0">
                <LeftPanel />
            </div>

            {/* ── Right: login form ── */}
            <div className="flex-1 flex items-center justify-center p-6 relative bg-gray-50/50">
                <BackgroundOrbs />

                <div className="relative z-10 w-full max-w-md">

                    {/* Mobile logo */}
                    <motion.div
                        variants={fadeUp} initial="hidden" animate="visible" custom={0}
                        className="flex lg:hidden items-center gap-2.5 mb-8"
                    >
                        <div className="w-9 h-9 rounded-xl bg-indigo-600 flex items-center justify-center shadow-lg shadow-indigo-200">
                            <Radio size={16} className="text-white" />
                        </div>
                        <p className="text-gray-900 font-bold text-lg">StreamCMS</p>
                    </motion.div>

                    {/* Heading */}
                    <motion.div variants={fadeUp} initial="hidden" animate="visible" custom={1} className="mb-8">
                        <h2 className="text-gray-900 font-black text-3xl tracking-tight leading-tight">
                            Sign in
                        </h2>
                        <p className="text-gray-400 text-sm mt-1.5">
                            Enter your admin credentials to continue.
                        </p>
                    </motion.div>

                    {/* Card */}
                    <motion.div
                        variants={fadeUp} initial="hidden" animate="visible" custom={2}
                        className="bg-white rounded-2xl border border-gray-200 shadow-xl shadow-gray-100/80 p-8"
                    >
                        <form onSubmit={handleSubmit} noValidate className="space-y-5">

                            {/* Username */}
                            <InputField
                                label="Username"
                                id="username"
                                value={username}
                                onChange={(e) => {
                                    setUsername(e.target.value);
                                    if (fieldErr.username) setFieldErr((p) => ({ ...p, username: "" }));
                                }}
                                placeholder="e.g. super_admin"
                                autoComplete="username"
                                error={fieldErr.username}
                            />
                            <AnimatePresence>
                                {fieldErr.username && (
                                    <motion.p
                                        initial={{ opacity: 0, height: 0, marginTop: 0 }}
                                        animate={{ opacity: 1, height: "auto", marginTop: 4 }}
                                        exit={{ opacity: 0, height: 0, marginTop: 0 }}
                                        className="text-rose-500 text-xs flex items-center gap-1 -mt-3"
                                    >
                                        <AlertCircle size={11} /> {fieldErr.username}
                                    </motion.p>
                                )}
                            </AnimatePresence>

                            {/* Password */}
                            <InputField
                                label="Password"
                                id="password"
                                type={showPw ? "text" : "password"}
                                value={password}
                                onChange={(e) => {
                                    setPassword(e.target.value);
                                    if (fieldErr.password) setFieldErr((p) => ({ ...p, password: "" }));
                                }}
                                placeholder="Min. 8 characters"
                                autoComplete="current-password"
                                error={fieldErr.password}
                                suffix={
                                    <button
                                        type="button"
                                        onClick={() => setShowPw((p) => !p)}
                                        className="text-gray-400 hover:text-gray-600 transition-colors"
                                        tabIndex={-1}
                                    >
                                        {showPw ? <EyeOff size={16} /> : <Eye size={16} />}
                                    </button>
                                }
                            />
                            <AnimatePresence>
                                {fieldErr.password && (
                                    <motion.p
                                        initial={{ opacity: 0, height: 0, marginTop: 0 }}
                                        animate={{ opacity: 1, height: "auto", marginTop: 4 }}
                                        exit={{ opacity: 0, height: 0, marginTop: 0 }}
                                        className="text-rose-500 text-xs flex items-center gap-1 -mt-3"
                                    >
                                        <AlertCircle size={11} /> {fieldErr.password}
                                    </motion.p>
                                )}
                            </AnimatePresence>

                            {/* Server error */}
                            <AnimatePresence>
                                {error && (
                                    <motion.div
                                        initial={{ opacity: 0, height: 0 }}
                                        animate={{ opacity: 1, height: "auto" }}
                                        exit={{ opacity: 0, height: 0 }}
                                        className="flex items-start gap-2.5 bg-rose-50 border border-rose-200
                      rounded-xl px-4 py-3"
                                    >
                                        <AlertCircle size={15} className="text-rose-500 mt-0.5 flex-shrink-0" />
                                        <p className="text-rose-600 text-sm font-medium">{error}</p>
                                    </motion.div>
                                )}
                            </AnimatePresence>

                            {/* Submit */}
                            <motion.button
                                type="submit"
                                disabled={loading}
                                whileHover={!loading ? { scale: 1.01 } : {}}
                                whileTap={!loading ? { scale: 0.98 } : {}}
                                className="w-full flex items-center justify-center gap-2.5 bg-indigo-600
                  text-white font-semibold rounded-xl py-3.5 text-sm
                  hover:bg-indigo-700 active:bg-indigo-800
                  transition-all shadow-lg shadow-indigo-200
                  disabled:opacity-60 disabled:cursor-not-allowed"
                            >
                                {loading ? (
                                    <>
                                        <motion.span
                                            animate={{ rotate: 360 }}
                                            transition={{ repeat: Infinity, duration: 0.75, ease: "linear" }}
                                            className="w-4 h-4 border-2 border-white/30 border-t-white rounded-full"
                                        />
                                        <span>Signing in…</span>
                                    </>
                                ) : (
                                    <>
                                        <span>Sign In</span>
                                        <ArrowRight size={15} />
                                    </>
                                )}
                            </motion.button>

                        </form>
                    </motion.div>

                    {/* Role hint pills */}
                    <motion.div
                        variants={fadeUp} initial="hidden" animate="visible" custom={3}
                        className="mt-5 flex items-center gap-2.5"
                    >
                        <div className="flex items-center gap-1.5 bg-white border border-indigo-100
              rounded-full px-3 py-1.5 shadow-sm"
                        >
                            <span className="w-1.5 h-1.5 rounded-full bg-indigo-500" />
                            <span className="text-xs text-indigo-600 font-semibold">MODERATOR</span>
                        </div>
                        <div className="flex items-center gap-1.5 bg-white border border-rose-100
              rounded-full px-3 py-1.5 shadow-sm"
                        >
                            <Shield size={10} className="text-rose-500" />
                            <span className="text-xs text-rose-600 font-semibold">SUPER_ADMIN</span>
                        </div>
                        <p className="text-gray-400 text-xs">roles supported</p>
                    </motion.div>

                </div>
            </div>
        </div>
    );
}