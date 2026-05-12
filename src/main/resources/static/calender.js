// import {fetchEntityList} from "./fetch.js";

export function initCalender() {

    const calender = document.getElementById("calender");
    renderCalender(calender);
}
// frontend dummy data lige nu skal komme fra initAPP
const entityDaySpan = [
    {
        entityName: "Mette",
        startDateTime: "2026-05-11T09:00:00",
        endDateTime: "2026-05-11T17:00:00"
    },
    {
        entityName: "Laura",
        startDateTime: "2026-05-11T09:00:00",
        endDateTime: "2026-05-11T11:00:00"
    },
    {
        entityName: "Niklas",
        startDateTime: "2026-05-12T10:00:00",
        endDateTime: "2026-05-12T18:00:00"
    },
    {
        entityName: "Laura",
        startDateTime: "2026-05-13T08:00:00",
        endDateTime: "2026-05-13T16:00:00"
    },
    {
        entityName: "Mette",
        startDateTime: "2026-05-14T12:00:00",
        endDateTime: "2026-05-14T20:00:00"
    },
    {
        entityName: "Niklas",
        startDateTime: "2026-05-15T09:00:00",
        endDateTime: "2026-05-15T17:00:00"
    }
];

let currentWeekOffset = 0;

export function renderCalender(calendar) {

    // lige nu er det kun på working days, ud fra en knap skal kan vi render specificerede lister
    // let entityDaySpan = fetchEntityList("workingDays");
    const today = new Date();

    const currentDay = today.getDay();

    const monday = new Date(today);

    let diffToMonday;

    if (currentDay === 0) {
        diffToMonday = -6;
    } else {
        diffToMonday = 1 - currentDay;
    }

    monday.setDate(today.getDate() + diffToMonday + (currentWeekOffset * 7));

    const weekDays = [];

    for (let i = 0; i < 7; i++) {
        const date = new Date(monday);
        date.setDate(monday.getDate() + i);
        weekDays.push(date);
    }

    const firstDate = weekDays[0];
    const lastDate = weekDays[6];

    const formatDate = (date) =>
        String(date.getDate()).padStart(2, "0") + "-" +
        String(date.getMonth() + 1).padStart(2, "0") + "-" +
        date.getFullYear();

    const formatDayKey = (date) =>
        date.getFullYear() + "-" +
        String(date.getMonth() + 1).padStart(2, "0") + "-" +
        String(date.getDate()).padStart(2, "0");

    const dayNames = [
        "Monday",
        "Tuesday",
        "Wednesday",
        "Thursday",
        "Friday",
        "Saturday",
        "Sunday"
    ];

    const hourHeight = 60;

    let daysHtml = "";

    weekDays.forEach((date, index) => {

        const formattedDate = formatDayKey(date);

        const employeesWorking = [];

        let i = 0;

        while (i < entityDaySpan.length) {

            const employee = entityDaySpan[i];

            const employeeDate = employee.startDateTime.split("T")[0];

            if (employeeDate === formattedDate) {
                employeesWorking.push(employee);
            }

            i++;
        }

        let timeLabels = "";
        let hour = 0;

        while (hour < 24) {

            let hourText = hour < 10 ? "0" + hour : "" + hour;

            timeLabels +=
                "<div class='time-slot' style='top:" +
                (hour * hourHeight) +
                "px;'>" +
                hourText +
                ":00</div>";

            hour++;
        }

        let employeeHtml = "";
        let j = 0;

        while (j < employeesWorking.length) {

            let employee = employeesWorking[j];

            let start = new Date(employee.startDateTime);
            let end = new Date(employee.endDateTime);

            let startTotal = start.getHours() * 60 + start.getMinutes();
            let endTotal = end.getHours() * 60 + end.getMinutes();

            let top = (startTotal / 60) * hourHeight;
            let height = ((endTotal - startTotal) / 60) * hourHeight;

            let width = 100 / employeesWorking.length;
            let left = j * width;

            let name = employee.entityName;

            let startText = employee.startDateTime.split("T")[1].substring(0, 5);
            let endText = employee.endDateTime.split("T")[1].substring(0, 5);

            employeeHtml +=
                "<div class='calendar-event' style='" +
                "top:" + top + "px;" +
                "height:" + height + "px;" +
                "width:calc(" + width + "% - 4px);" +
                "left:" + left + "%;'>" +
                "<div class='employee-name'>" + name + "</div>" +
                "<div class='employee-time'>" +
                startText + " - " + endText +
                "</div></div>";

            j++;
        }

        daysHtml += `
            <div class="calendar-day">

                <div class="calendar-day-header">
                    <h3>${dayNames[index]}</h3>
                    <p>${formattedDate}</p>
                </div>

                <div class="calendar-timeline">

                    ${timeLabels}
                    ${employeeHtml}

                </div>

            </div>
        `;
    });

    calendar.innerHTML = `
        <div class="calendar-wrapper">

            <div class="calendar-navigation">

                <button id="previous-week">Previous Week</button>

                <div class="calendar-title">
                    <h2>Week View</h2>
                    <p>${formatDate(firstDate)} → ${formatDate(lastDate)}</p>
                </div>

                <button id="next-week">Next Week</button>

            </div>

            <div class="calendar-grid">
                ${daysHtml}
            </div>

        </div>
    `;

    document.getElementById("previous-week").onclick = () => {
        currentWeekOffset--;
        renderCalender(calendar);
    };

    document.getElementById("next-week").onclick = () => {
        currentWeekOffset++;
        renderCalender(calendar);
    };
}