document.addEventListener("DOMContentLoaded", initApp);

const BASE_URL = "";

const UI = {
    loginForm: null,
    messageDiv: null,
    userInfo: null
};

/* ---------------- INIT ---------------- */

async function initApp() {
    setupUi();

    try {
        const user = await getCurrentUser();

        hideLoginForm();
        showUserInfo(user);
        setMessage("Logged in", true);
    } catch {
        showLoginForm();
        hideUserInfo();
        setMessage("", true);
    }
}

/* ---------------- USER ---------------- */

async function getCurrentUser() {
    const response = await fetch(`${BASE_URL}/api/employees/employee`, {
        credentials: "include"
    });

    if (response.status === 401) {
        throw new Error("Not logged in");
    }

    if (!response.ok) {
        throw new Error("Failed to fetch user");
    }

    return await response.json();
}

/* ---------------- LOGIN ---------------- */

async function handleLogin(event) {
    event.preventDefault();

    const formData = new FormData(event.target);
    const username = formData.get("username");
    const password = formData.get("password");

    const response = await fetch(`${BASE_URL}/api/login`, {
        method: "POST",
        credentials: "include",
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
            "X-XSRF-TOKEN": getCsrfToken()
        },
        body: new URLSearchParams({ username, password })
    });

    if (!response.ok) {
        setMessage("Login failed", false);
        return;
    }

    const user = await getCurrentUser();

    hideLoginForm();
    showUserInfo(user);
    setMessage("Login successful", true);
}

/* ---------------- LOGOUT ---------------- */

async function handleLogout() {
    const response = await fetch(`${BASE_URL}/api/logout`, {
        method: "POST",
        credentials: "include",
        headers: {
            "X-XSRF-TOKEN": getCsrfToken()
        }
    });

    if (!response.ok) {
        setMessage("Logout failed", false);
        return;
    }

    hideUserInfo();
    showLoginForm();
    setMessage("Logged out", true);
}

/* ---------------- UI ---------------- */

function setupUi() {
    UI.loginForm = document.getElementById("login-form");
    UI.messageDiv = document.getElementById("message");
    UI.userInfo = document.getElementById("user-info");

    UI.loginForm.addEventListener("submit", handleLogin);
}

function showUserInfo(user) {
    UI.userInfo.innerHTML = `
        <div>
            <span>${user.username}</span>
            <button id="logoutBtn">Logout</button>
        </div>
    `;

    document
        .getElementById("logoutBtn")
        .addEventListener("click", handleLogout);

    UI.userInfo.classList.remove("hidden");
}

function hideUserInfo() {
    UI.userInfo.innerHTML = "";
    UI.userInfo.classList.add("hidden");
}

function showLoginForm() {
    UI.loginForm.classList.remove("hidden");
}

function hideLoginForm() {
    UI.loginForm.classList.add("hidden");
}

/* ---------------- MESSAGE ---------------- */

function setMessage(text, success) {
    UI.messageDiv.textContent = text;
    UI.messageDiv.style.color = success ? "green" : "red";
}

/* ---------------- CSRF ---------------- */

function getCsrfToken() {
    const match = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
    return match ? decodeURIComponent(match[1]) : "";
}