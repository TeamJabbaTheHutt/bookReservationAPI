import {initCalender} from "./calender.js";



document.addEventListener("DOMContentLoaded", initApp);
async function initApp() {
    console.log("DOM loaded");
    renderNav();
    const loginBtn = document.getElementById("logIn");


    loginBtn.addEventListener("click", () => redirectLogin());
    initCalender();

}

function renderNav() {
    const nav = document.getElementById("nav");
    nav.innerHTML = `
                <button id="logIn">Log in</button>
    
    `;

}
async function redirectLogin() {
    window.location.href = "/login";
}