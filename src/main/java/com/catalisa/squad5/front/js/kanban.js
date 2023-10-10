const username = 'admin';
const password = '12345';
const credentials = `${username}:${password}`;
const base64Credentials = btoa(credentials);

const xhr = new XMLHttpRequest();
xhr.open('GET', 'http://localhost:8080/issues', true);
xhr.setRequestHeader('Content-Type', 'application/json');
xhr.setRequestHeader('Authorization', `Basic ${base64Credentials}`);

xhr.onreadystatechange = function() {
    if (xhr.readyState === XMLHttpRequest.DONE) {
        if (xhr.status === 200) {
            const allIssues = JSON.parse(xhr.responseText);

            populateKanban(allIssues);
        } else if (xhr.status === 401) {
            document.getElementById('msg').style.display = 'block';
        }
    }
};

xhr.send();

function populateKanban(issues) {
    const issuesToDo = document.getElementById("issuesToDo");
    const issuesDoing = document.getElementById("issuesDoing");
    const issuesDone = document.getElementById("issuesDone");

    // Limpa o conteúdo antes de adicionar os cards
    issuesToDo.innerHTML = "";
    issuesDoing.innerHTML = "";
    issuesDone.innerHTML = "";

    let todoCount = 0;
    let doingCount = 0;
    let doneCount = 0;

    issues.forEach(values => {
        const issueElement = `
<li class="list-group-item">
    <div class="card">
        <div class="card-body">
            <h5 class="card-title">ID: ${values.id}</h5>
            <p class="card-text"><strong>URL:</strong> ${values.url}</p>
            <p class="card-text"><strong>Empresa:</strong> ${values.nameCompany}</p>
            <p class="card-text"><strong>Descrição:</strong> ${values.description}</p>
        </div>
    </div>
</li>
`;

        if (values.task === 0 && todoCount < 3) {
            issuesToDo.innerHTML += issueElement;
            todoCount++;
        } else if (values.task === 1 && doingCount < 3) {
            issuesDoing.innerHTML += issueElement;
            doingCount++;
        } else if (values.task === 2 && doneCount < 3) {
            issuesDone.innerHTML += issueElement;
            doneCount++;
        }
    });
}