export const BASE_URL = "/api";


// working days
export async function fetchEntityList(entity) {
    let res = "";
    if (entity === "workingDays") {
        res = await fetch(`${BASE_URL}/workingDays`, {});
        return await res.json();
    } else {
        return null;
    }
}


//treatment
export async function fetchTreatments() {
    const res = await fetch(`${BASE_URL}/treatment`);
    return await res.json();
}


export async function fetchTreatmentById(id) {
    const res = await fetch(`${BASE_URL}/treatment/` + id);
    return await res.json();
}

export async function createTreatment(treatment) {
    const body = {
        title: treatment.title,
        duration: treatment.duration,
        price: treatment.price,
        employees: treatment.employees,

    }

    const res = await fetch(`${BASE_URl}/treatment`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body),
        credentials: "include"
    });

    return await res.json();
}

export async function updateTreatment(id, treatment) {
    const body = {
        title: treatment.title,
        duration: treatment.duration,
        price: treatment.price,
        employees: treatment.employees,

    }

    const res = await fetch(`${BASE_URL}/treatment/`+id, {
       method: "PUT",
       headers: {
           "Content-Type": "application/json"
       },
        body: JSON.stringify(body),
        credentials: "include"
    });
    return await res.json();
}

export async function deleteAllTreatments() {
    const res = await fetch(`${BASE_URL}/treatment`, {
        method: "DELETE",
        credentials: "include"
    });
    return await res.json();
}

export async function deleteTreatmentById(id) {
    const res = await fetch(`${BASE_URL}/treatment/`+ id, {
        method: "DELETE",
        credentials: "include"
    });
}


// employee

export async function fetchAllEmployees() {
    const res = await fetch(`${BASE_URL}/employees`);
    return await res.json();
}

export async function fetchEmployeeById(id) {
    const res = await fetch(`${BASE_URL}/employees`+ id);
    return await res.json();
}

export async function createEmployee(employee) {
    const body = {
        username: employee.username,
        password: employee.password,
        employeeName: employee.employeeName,
        treatments: employee.treatments,
        role: employee.role,
    }
    const res = await fetch(`${BASE_URL}/employees`, {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body),
        credentials: "include"
    });
    return await res.json();
}

export async function updateEmployeeById(id, employee) {
    const body = {
        username: employee.username,
        password: employee.password,
        employeeName: employee.employeeName,
        treatments: employee.treatments,
        role: employee.role,
    }
    const res = await fetch(`${BASE_URL}/employees`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(body),
        credentials: "include"
    })
    return await res.json();
}

export async function deleteAllEmployees() {
    const res = await fetch(`${BASE_URL}/employees`, {
        method: "DELETE",
        credentials: "include"
    })
    return await res.json();
}
export async function deleteEmployeeById(id) {
    const res = await fetch(`${BASE_URL}/employees/` + id, {
        method: "DELETE",
        credentials: "include"
    });
}


// reservation

export async function fetchAllReservationsByUser() {
    const res = await fetch(`${BASE_URL}/reservations/all`);
    return await res.json();
}

export async function fetchReservationsToAndFromByUser(startDate, endDate, employee) {

    let url = `${BASE_URL}/api/reservations`;

    const params = new URLSearchParams();

    if (startDate) params.append("startDate", startDate);
    if (endDate) params.append("endDate", endDate);

    if (params.toString()) {
        url += `?${params.toString()}`;
    }

    const res = await fetch(url, {
        method: "GET",
        credentials: "include",
        headers: {
            "Authorization": "Basic " + btoa(employee.username + ":" + employee.password)
        }
    });

    return await res.json();
}

export async function getMyReservations(employee) {

    const res = await fetch(`${BASE_URL}/api/reservations/all`, {
        method: "GET",
        headers: {
            "Authorization": "Basic " + btoa(employee.username + ":" + employee.password)
        }
    });

    return await res.json();
}

export async function getAllReservations() {
    const res = await fetch(`${BASE_URL}/api/reservations/all-resesrvations`);
    return await res.json();
}

export async function getReservationByEmployeeId(id) {
    const res = await fetch(`${BASE_URL}/api/reservations/employee/` + id);
    return await res.json();
}


