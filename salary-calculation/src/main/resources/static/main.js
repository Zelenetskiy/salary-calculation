getAllPersons();
getPersonsPossibleBoss()

/**
 * метод передает запрос на получение всех сотрудников
 */
function getAllPersons() {
    fetch("/rest/main")
        .then(res => res.json())
        .then(persons => {
            let personsListToHTML = '';
            let personToHTML = '';
            persons.forEach(person => {
                personToHTML += `
                        <tr>
                        <td>${person.id}</td>
                        <td>${person.name}</td>
                        <td>${person.dateEmployment}</td>
                        <td>${person.group}</td>
                        <td>${person.rateSalary}</td>                                                
                        </tr>       
                    `;
                personsListToHTML += `<option value=${person.id} >${person.name}</option>`;
            });
            document.getElementById("allPersonTable").innerHTML = personToHTML;
            document.getElementById("selectEmployee").innerHTML = personsListToHTML;
        });
}

/**
 * метод передает запрос на добавление сотрудника
 */
function addPerson() {
    let person = {
        name: document.getElementById('newName').value,
        dateEmployment: document.getElementById('newDateEmployment').value,
        group: document.getElementById('newGroup').value,
        rateSalary: document.getElementById('newRateSalary').value,
    }
    fetch("/rest/addPerson", {
        method: 'POST',
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json; charset=UTF-8'
        },
        body: JSON.stringify(person)
    })
        .then(() => {
            getAllPersons();
            document.getElementById("nav-add-employee-tab").click();
            document.getElementById("addPersonForm").reset();
        })
}

/**
 * метод передает запрос на получение зарплаты выбранного сотрудника на выбранную дату
 */
function getSalarySelectPerson() {
    let id = document.getElementById('selectEmployee').value;
    let selectDate = document.getElementById('selectDate').value;
    fetch("/rest/getSalary/" + id + "/" + selectDate, {
        method: "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        dataType: 'json'
    })
        .then(res => {
            res.json()
                .then(salary => {
                    document.getElementById("salaryEmployee").value = salary;
                })
        })
}

/**
 * метод передает запрос на получение зарплаты всех сотрудниов на выбранную дату
 */
function getSalaryAllPersons() {
    let selectDate = document.getElementById('selectDate').value;
    fetch("/rest/getSalaryAllPersons/" + selectDate, {
        method: "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        dataType: 'json'
    })
        .then(res => {
            res.json()
                .then(salaryAllPersons => {
                    document.getElementById("salaryAllEmployee").value = salaryAllPersons;
                })
        })
}

/**
 * метод передает запрос на получение списка подчиненных
 */
function getSubordinateList() {
    let id = document.getElementById('selectEmployeeGetSubordinate').value;
    fetch("/rest/getSubordinates/" + id, {
        method: "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        dataType: 'json'
    })
        .then(res => res.json())
        .then(subordinates => {
            let subordinatesListToHTML = '';
            subordinates.forEach(subordinate => {
                subordinatesListToHTML += `<li class="list-group-item" value=${subordinate.id}>${subordinate.name}</li>`;
            });
            document.getElementById("subordinateList").innerHTML = subordinatesListToHTML;
        });
}

/**
 * метод передает запрос на получение сотрудников которые могут иметь подчиненных
 */
function getPersonsPossibleBoss() {
    fetch("/rest/possibleboss")
        .then(res => res.json())
        .then(possibleBoss => {
            let possibleBossListToHTML = '';
            possibleBoss.forEach(person => {
                possibleBossListToHTML += `<option value=${person.id} >${person.name}</option>`;
            });
            document.getElementById("selectEmployeeGetSubordinate").innerHTML = possibleBossListToHTML;
        });
}

/**
 * метод передает запрос на получение списка сотрудников без начальника
 */
function getNewSubordinateList() {
    let id = document.getElementById('selectEmployeeGetSubordinate').value;
    fetch("/rest/possibleboss/" + id, {
        method: "GET",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        dataType: 'json'
    })
        .then(res => res.json())
        .then(listNewSubordinate => {
            let freedomPersonListToHTML = '';
            listNewSubordinate.forEach(person => {
                freedomPersonListToHTML += `<option value=${person.id} >${person.name}</option>`;
            });
            document.getElementById("selectSubordinate").innerHTML = freedomPersonListToHTML;
        })
}

/**
 * метод передает запрос на добавление нового подчиненного
 */
function addSubordinate() {
    let id = document.getElementById('selectSubordinate').value;
    let idboss = document.getElementById('selectEmployeeGetSubordinate').value;
    fetch("/rest/addsubordinate/" + id + "/" + idboss, {
        method: "PUT",
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json;charset=UTF-8'
        },
        dataType: 'json'
    })
        .then(() => {
            document.getElementById("showSubordinateButton").click();
        })
}
