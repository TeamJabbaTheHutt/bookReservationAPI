

export function initCalender() {

    const calender = document.getElementById("calender");
    renderCalender(calender);
}

export function renderCalender(calender) {

    const now = new Date();

    const year = now.getFullYear();
    const month = now.getMonth();

    const firstDay = new Date(year, month, 1).getDay();
    const daysInMonth = new Date(year, month + 1, 0).getDate();

    const monthNames = [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December"
    ];

    const weekDays = [
        "Sun",
        "Mon",
        "Tue",
        "Wed",
        "Thu",
        "Fri",
        "Sat"
    ];

    let daysHtml = "";

    for (let i = 0; i < firstDay; i++) {
        daysHtml += `<div class="empty"></div>`;
    }

    for (let day = 1; day <= daysInMonth; day++) {
        daysHtml += `
            <div class="day">
                ${day}
            </div>
        `;
    }

    calender.innerHTML = `
        <div class="calendar">
        
            <h2>
                ${monthNames[month]} ${year}
            </h2>

            <div class="weekdays">
                ${weekDays.map(day => `<div>${day}</div>`).join("")}
            </div>

            <div class="days">
                ${daysHtml}
            </div>

        </div>
    `;
}