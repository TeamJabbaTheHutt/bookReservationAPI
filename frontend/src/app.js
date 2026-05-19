// import {initCalender} from "./calender.js";
//
// document.addEventListener("DOMContentLoaded", initApp);
//
// const BASE_URL = "";
//
// const UI = {
//     loginForm: null,
//     messageDiv: null,
//     userInfo: null
// };
//
// /* ---------------- INIT ---------------- */
//
// async function initApp() {
//     setupUi();
//
//     try {
//         const user = await getCurrentUser();
//         // initCalender();
//         hideLoginForm();
//         showUserInfo(user);
//         setMessage("Logged in", true);
//     } catch {
//         showLoginForm();
//         hideUserInfo();
//         setMessage("", true);
//     }
// }
//
// /* ---------------- USER ---------------- */
//
// async function getCurrentUser() {
//     const response = await fetch(`${BASE_URL}/api/employees/employee`, {
//         credentials: "include"
//     });
//
//     if (response.status === 401) {
//         throw new Error("Not logged in");
//     }
//
//     if (!response.ok) {
//         throw new Error("Failed to fetch user");
//     }
//
//     return await response.json();
// }
//
// /* ---------------- LOGIN ---------------- */
//
// async function handleLogin(event) {
//     event.preventDefault();
//
//     const formData = new FormData(event.target);
//     const username = formData.get("username");
//     const password = formData.get("password");
//
//     const response = await fetch(`${BASE_URL}/api/login`, {
//         method: "POST",
//         credentials: "include",
//         headers: {
//             "Content-Type": "application/x-www-form-urlencoded",
//             "X-XSRF-TOKEN": getCsrfToken()
//         },
//         body: new URLSearchParams({ username, password })
//     });
//
//     if (!response.ok) {
//         setMessage("Login failed", false);
//         return;
//     }
//
//     const user = await getCurrentUser();
//
//     hideLoginForm();
//     showUserInfo(user);
//     setMessage("Login successful", true);
// }
//
// /* ---------------- LOGOUT ---------------- */
//
// async function handleLogout() {
//     const response = await fetch(`${BASE_URL}/api/logout`, {
//         method: "POST",
//         credentials: "include",
//         headers: {
//             "X-XSRF-TOKEN": getCsrfToken()
//         }
//     });
//
//     if (!response.ok) {
//         setMessage("Logout failed", false);
//         return;
//     }
//
//     hideUserInfo();
//     showLoginForm();
//     setMessage("Logged out", true);
// }
//
// /* ---------------- UI ---------------- */
//
// function setupUi() {
//     UI.loginForm = document.getElementById("login-form");
//     UI.messageDiv = document.getElementById("message");
//     UI.userInfo = document.getElementById("user-info");
//
//     UI.loginForm.addEventListener("submit", handleLogin);
// }
//
// function showUserInfo(user) {
//     UI.userInfo.innerHTML = `
//         <div>
//             <span>${user.username}</span>
//             <button id="logoutBtn">Logout</button>
//         </div>
//     `;
//
//     document
//         .getElementById("logoutBtn")
//         .addEventListener("click", handleLogout);
//
//     UI.userInfo.classList.remove("hidden");
// }
//
// function hideUserInfo() {
//     UI.userInfo.innerHTML = "";
//     UI.userInfo.classList.add("hidden");
// }
//
// function showLoginForm() {
//     UI.loginForm.classList.remove("hidden");
// }
//
// function hideLoginForm() {
//     UI.loginForm.classList.add("hidden");
// }
//
// /* ---------------- MESSAGE ---------------- */
//
// function setMessage(text, success) {
//     UI.messageDiv.textContent = text;
//     UI.messageDiv.style.color = success ? "green" : "red";
// }
//
// /* ---------------- CSRF ---------------- */
//
// function getCsrfToken() {
//     const match = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
//     return match ? decodeURIComponent(match[1]) : "";
// }

document.addEventListener("DOMContentLoaded", initApp);

const BASE_URL = ""; // nginx proxies requests, so relative URLs are enough

const UI_ELEMENTS = {
    loginForm: null,
    messageDiv: null,
    userInfo: null
};


async function initApp() {
    setupUi();

    try {
        // This request serves two purposes:
        // 1. If the user is already logged in, we get their user info,
        // or else we get a 401 unauthorized error, triggering the catch block
        // 2. If no CSRF cookie exists yet, Spring can generate one here
        const user = await fetchUserInfo();

        // If the request was successful, we have a logged in user and can show their info
        hideLoginForm();
        clearDisplayMessage();
        showUserInfo(user);
    } catch (error) {
        // This is the normal path for anonymous users on first page load
        showLoginForm();
        hideUserInfo();
        clearDisplayMessage();
    }
}

function setupUi() {
    UI_ELEMENTS.loginForm = document.getElementById("login-form");
    UI_ELEMENTS.messageDiv = document.getElementById("message");
    UI_ELEMENTS.userInfo = document.getElementById("user-info");

    UI_ELEMENTS.loginForm.addEventListener("submit", handleLogin);
}

function hideLoginForm() {
    UI_ELEMENTS.loginForm.classList.add("hidden");
}

function showLoginForm() {
    UI_ELEMENTS.loginForm.classList.remove("hidden");
}

async function handleLogin(event) {
    event.preventDefault();

    const form = event.target;
    const formData = new FormData(form);
    const username = formData.get("username");
    const password = formData.get("password");

    try {
        const csrfToken = getCsrfToken();
        const response = await fetch(`${BASE_URL}/api/login`, {
            method: 'POST',
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
                "X-XSRF-TOKEN": csrfToken // Include the CSRF token in the header
            },
            body: new URLSearchParams({ username, password })
        });

        if (!response.ok) {
            throw new Error("Login failed. Please check your credentials.");
        }

        form.reset();
        setDisplayMessage("Login successful!", true);
        // TODO #1
        const userInfo = await fetchUserInfo();
        showUserInfo(userInfo);
        hideLoginForm();

    } catch (error) {
        setDisplayMessage(error.message, false);
    }
}
function getCsrfToken() {
    const match = document.cookie.match(/XSRF-TOKEN=([^;]+)/);
    return match ? decodeURIComponent(match[1]) : null;
}
function setDisplayMessage(text, isSuccess) {
    UI_ELEMENTS.messageDiv.textContent = text;
    UI_ELEMENTS.messageDiv.style.color = isSuccess ? "green" : "red";
}
async function fetchUserInfo() {
    const response = await fetch(`${BASE_URL}/api/user`);
    if (!response.ok) {
        throw new Error("Failed to fetch user info");
    }
    return await response.json();
}
function showUserInfo(user) {
    UI_ELEMENTS.userInfo.innerHTML = "";

    const article = document.createElement("article");
    article.style.padding = "1em";

    const usernamePtag = document.createElement("p");
    usernamePtag.textContent = `Logged in as: ${user.username}`;

    const rolesPtag = document.createElement("p");
    rolesPtag.textContent = `Roles: ${user.roles.join(", ")}`;

    const logoutButton = document.createElement("button");
    logoutButton.textContent = "Logout";
    logoutButton.addEventListener("click", handleLogout);

    article.appendChild(usernamePtag);
    article.appendChild(rolesPtag);
    article.appendChild(logoutButton);
    UI_ELEMENTS.userInfo.appendChild(article);

    UI_ELEMENTS.userInfo.classList.remove("hidden");
}

function hideUserInfo() {
    UI_ELEMENTS.userInfo.innerHTML = "";
    UI_ELEMENTS.userInfo.classList.add("hidden");
}
function clearDisplayMessage() {
    UI_ELEMENTS.messageDiv.textContent = "";
}
async function handleLogout() {
    try {
        const csrfToken = getCsrfToken();

        const response = await fetch(`${BASE_URL}/api/logout`, {
            method: "POST",
            headers: {
                "X-XSRF-TOKEN": csrfToken || ""
            }
        });

        if (!response.ok) {
            throw new Error("Logout failed.");
        }

        // Hide user info and show login form again
        hideUserInfo();
        showLoginForm();
        setDisplayMessage("Logged out successfully.", true);

        try {
            await fetchUserInfo();
        } catch {
            // Expected to fail with 401,
            // but this will trigger CSRF token generation
            // for the next login attempt
        }

    } catch (error) {
        setDisplayMessage(error.message, false);
    }
}