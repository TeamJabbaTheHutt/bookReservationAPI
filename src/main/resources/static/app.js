import { initCalender } from "./calender.js";
import { initBooking } from "./booking.js";

document.addEventListener("DOMContentLoaded", initApp);

async function initApp() {
    console.log("DOM loaded");
    renderNav();

    const loginBtn = document.getElementById("logIn");
    loginBtn.addEventListener("click", () => redirectLogin());

    initCalender();
    initBooking();  // ← tilføj denne
}

function renderNav() {
    const nav = document.getElementById("nav");
    nav.innerHTML = `
        <button id="logIn">Log ind</button>
    `;
}

async function redirectLogin() {
    window.location.href = "/login";
}