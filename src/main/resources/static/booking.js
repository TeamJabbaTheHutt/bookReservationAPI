import {
    fetchAllEmployees,
    fetchTreatmentsForEmployee,
    fetchAvailableSlots,
    createReservation
} from "./fetch.js";

const bookingState = {
    employee: null,
    treatment: null,
    startDateTime: null
};

export function initBooking() {
    document.getElementById("show-booking-btn").addEventListener("click", showBookingSection);

    document.querySelectorAll(".back-btn").forEach(btn => {
        btn.addEventListener("click", () => goToStep(btn.dataset.target));
    });

    document.getElementById("booking-date").addEventListener("change", loadAvailableSlots);
    document.getElementById("confirm-booking-btn").addEventListener("click", confirmBooking);
    document.getElementById("new-booking-btn").addEventListener("click", resetBooking);
}

function showBookingSection() {
    document.getElementById("booking-section").classList.remove("hidden");
    loadEmployees();
}

function goToStep(stepId) {
    document.querySelectorAll(".booking-step").forEach(step => step.classList.add("hidden"));
    document.getElementById(stepId).classList.remove("hidden");
}

// Trin 1: Vælg behandler
async function loadEmployees() {
    const employees = await fetchAllEmployees();
    const list = document.getElementById("employee-list");
    list.innerHTML = "";

    employees.forEach(emp => {
        const div = document.createElement("div");
        div.className = "list-item";
        div.textContent = `${emp.employeeName} (${emp.role})`;
        div.addEventListener("click", () => selectEmployee(emp));
        list.appendChild(div);
    });

    goToStep("step-employee");
}

function selectEmployee(employee) {
    bookingState.employee = employee;
    document.getElementById("selected-employee-name").textContent = employee.employeeName;
    loadTreatments(employee.id);
    goToStep("step-treatment");
}

// Trin 2: Vælg behandling
async function loadTreatments(employeeId) {
    const treatments = await fetchTreatmentsForEmployee(employeeId);
    const list = document.getElementById("treatment-list");
    list.innerHTML = "";

    if (treatments.length === 0) {
        list.innerHTML = "<p>Denne behandler har ingen behandlinger.</p>";
        return;
    }

    treatments.forEach(t => {
        const div = document.createElement("div");
        div.className = "list-item";
        div.textContent = `${t.title} (${t.durationMinutes} min)`;
        div.addEventListener("click", () => selectTreatment(t));
        list.appendChild(div);
    });
}

function selectTreatment(treatment) {
    bookingState.treatment = treatment;
    document.getElementById("selected-treatment-title").textContent = treatment.title;
    document.getElementById("booking-date").min = new Date().toISOString().split("T")[0];
    document.getElementById("booking-date").value = "";
    document.getElementById("slot-list").innerHTML = "";
    goToStep("step-datetime");
}

// Trin 3: Vælg dato og tid
async function loadAvailableSlots() {
    const date = document.getElementById("booking-date").value;
    if (!date) return;

    const slots = await fetchAvailableSlots(
        bookingState.employee.id,
        bookingState.treatment.treatmentId,
        date
    );

    const slotList = document.getElementById("slot-list");
    slotList.innerHTML = "";

    if (slots.length === 0) {
        slotList.innerHTML = "<p>Ingen ledige tider på denne dag.</p>";
        return;
    }

    const grid = document.createElement("div");
    grid.className = "slot-grid";

    slots.forEach(slot => {
        const time = slot.substring(11, 16);  // "09:00" fra "2026-05-12T09:00:00"
        const btn = document.createElement("button");
        btn.className = "slot-btn";
        btn.textContent = time;
        btn.addEventListener("click", () => selectSlot(slot));
        grid.appendChild(btn);
    });

    slotList.appendChild(grid);
}

function selectSlot(slot) {
    bookingState.startDateTime = slot;
    showSummary();
    goToStep("step-confirm");
}

// Trin 4: Bekræft
function showSummary() {
    const summary = document.getElementById("booking-summary");
    const slot = bookingState.startDateTime;
    const date = slot.substring(0, 10);
    const time = slot.substring(11, 16);

    summary.innerHTML = `
        <p><strong>Behandler:</strong> ${bookingState.employee.employeeName}</p>
        <p><strong>Behandling:</strong> ${bookingState.treatment.title}</p>
        <p><strong>Varighed:</strong> ${bookingState.treatment.durationMinutes} min</p>
        <p><strong>Dato:</strong> ${date}</p>
        <p><strong>Tidspunkt:</strong> ${time}</p>
    `;
}

async function confirmBooking() {
    const name = document.getElementById("customer-name").value.trim();
    const email = document.getElementById("customer-email").value.trim();

    if (!name || !email) {
        alert("Udfyld venligst både navn og email");
        return;
    }

    try {
        await createReservation({
            employeeId: bookingState.employee.id,
            treatmentId: bookingState.treatment.treatmentId,
            startDateTime: bookingState.startDateTime,
            customerName: name,
            customerEmail: email
        });

        showConfirmation(name, email);
        goToStep("step-success");
    } catch (err) {
        alert("Noget gik galt: " + err.message);
    }
}

function showConfirmation(name, email) {
    const slot = bookingState.startDateTime;
    const date = slot.substring(0, 10);
    const time = slot.substring(11, 16);

    const details = document.getElementById("confirmation-details");
    details.innerHTML = `
        <p><strong>Navn:</strong> ${name}</p>
        <p><strong>Email:</strong> ${email}</p>
        <p><strong>Behandler:</strong> ${bookingState.employee.employeeName}</p>
        <p><strong>Behandling:</strong> ${bookingState.treatment.title}</p>
        <p><strong>Varighed:</strong> ${bookingState.treatment.durationMinutes} min</p>
        <p><strong>Dato:</strong> ${date}</p>
        <p><strong>Tidspunkt:</strong> ${time}</p>
    `;
}

function resetBooking() {
    bookingState.employee = null;
    bookingState.treatment = null;
    bookingState.startDateTime = null;
    document.getElementById("customer-name").value = "";
    document.getElementById("customer-email").value = "";
    loadEmployees();
}