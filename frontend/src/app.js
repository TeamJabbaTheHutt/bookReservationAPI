document.addEventListener("DOMContentLoaded", initApp);

const BASE_URL = ""; // nginx proxies requests, so relative URLs are enough

const UI_ELEMENTS = {
    loginForm: null,
    messageDiv: null,
    userInfo: null
};


function initApp() {
    setupUi();
    showLoginForm();
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
        const response = await fetch(`${BASE_URL}/api/login`, {
            method: "POST",
            headers: {
                "Content-Type": "application/x-www-form-urlencoded",
            },
            body: new URLSearchParams({ username, password })
        });

        if (!response.ok) {
            throw new Error("Login failed. Please check your credentials.");
        }

        form.reset();
        setDisplayMessage("Login successful!", true);
        // PLACEHOLDER #1: After successful login

    } catch (error) {
        setDisplayMessage(error.message, false);
    }
}

function setDisplayMessage(text, isSuccess) {
    UI_ELEMENTS.messageDiv.textContent = text;
    UI_ELEMENTS.messageDiv.style.color = isSuccess ? "green" : "red";
}

function clearDisplayMessage() {
    UI_ELEMENTS.messageDiv.textContent = "";
}