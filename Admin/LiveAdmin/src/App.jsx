import { useState } from 'react'
import './App.css'

// src/App.jsx
import { AuthProvider } from "./context/AuthContext";
import AppRouter from './routes/AppRouter';

/**
 * App root
 * AuthProvider wraps everything so useAuth() works in all child components.
 */
export default function App() {
  return (
    <AuthProvider>
      <AppRouter />
    </AuthProvider>
  );
}


// ─────────────────────────────────────────────────────────────────
// src/main.jsx
// ─────────────────────────────────────────────────────────────────

// import { StrictMode } from "react";
// import { createRoot }  from "react-dom/client";
// import "./index.css";
// import App from "./App";
//
// createRoot(document.getElementById("root")).render(
//   <StrictMode>
//     <App />
//   </StrictMode>
// );


// ─────────────────────────────────────────────────────────────────
// src/index.css
// ─────────────────────────────────────────────────────────────────

// @tailwind base;
// @tailwind components;
// @tailwind utilities;


// ─────────────────────────────────────────────────────────────────
// src/constants/roles.js
// ─────────────────────────────────────────────────────────────────

// Mirrors: com.example.Live.stream.domain.enums.AdminRole
// export const AdminRole = {
//   SUPER_ADMIN : "SUPER_ADMIN",
//   MODERATOR   : "MODERATOR",
// };


// ─────────────────────────────────────────────────────────────────
// src/components/sidebar/index.js  (barrel export)
// ─────────────────────────────────────────────────────────────────

// export { SuperAdminSidebar } from "./SuperAdminSidebar";
// export { ModeratorSidebar  } from "./ModeratorSidebar";
