const username = "admin";
const password = "12345";
const credentials = `${username}:${password}`;
const base64Credentials = btoa(credentials);
let issues = []; // Array para armazenar os cards

// Configuração da requisição AJAX
const xhr = new XMLHttpRequest();
xhr.open("GET", "http://localhost:8080/issues", true);
xhr.setRequestHeader("Content-Type", "application/json");
xhr.setRequestHeader("Authorization", `Basic ${base64Credentials}`);

xhr.onreadystatechange = function () {
  // Função que é chamada sempre que o estado da requisição muda
  if (xhr.readyState === XMLHttpRequest.DONE) {
    if (xhr.status === 200) {
      // Se a requisição foi bem-sucedida, atualiza os cards
      issues = JSON.parse(xhr.responseText);
      populateKanban(issues);
    } else if (xhr.status === 401) {
      // Se houve erro de autorização, exibe uma mensagem
      document.getElementById("msg").style.display = "block";
    }
  }
};

xhr.send(); // Envio da requisição GET

// Função para popular o kanban com os cards
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

  // Itera sobre os cards e os adiciona ao kanban conforme a coluna
  issues.forEach((values) => {
    // Gera a estrutura HTML para cada card
    const issueElement = `
        <li class="list-group-item">
        <div class="card">
        <div class="card-body">
        <h5 class="card-title">ID: ${values.id}</h5>
        <p class="card-text"><strong>URL:</strong> ${values.url}</p>
        <p class="card-text"><strong>Empresa:</strong> ${values.nameCompany}</p>
        <p class="card-text"><strong>Descrição:</strong> ${values.description}</p>
        <div class="btn-group" role="group">
        <button class="btn btn-secondary" onclick="moveToInProgress(${values.id})">In Progress</button>
        <button class="btn btn-secondary" onclick="moveToDone(${values.id})">Done</button>
        </div>
        </div>
        </div>
        </li>
        `;

    // Adiciona o card à coluna correspondente
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

function changeTask(newTask, issueId) {
  const xhrChangeTask = new XMLHttpRequest();
  xhrChangeTask.open(
    "PUT",
    `http://localhost:8080/issues/task/${issueId}`,
    true
  ); // Alterada a rota para `/task/{id}`
  xhrChangeTask.setRequestHeader("Content-Type", "application/json");
  xhrChangeTask.setRequestHeader("Authorization", `Basic ${base64Credentials}`);

  const body = JSON.stringify({ task: newTask }); // Corpo da requisição modificado

  xhrChangeTask.onreadystatechange = function () {
    if (xhrChangeTask.readyState === XMLHttpRequest.DONE) {
      if (xhrChangeTask.status === 200) {
        console.log("Tarefa atualizada com sucesso.");
      } else {
        console.error("Erro ao atualizar tarefa:", xhrChangeTask.status);
      }
    }
  };

  xhrChangeTask.send(body);
}

function moveToInProgress(issueId) {
  changeTask(1, issueId);
}

function moveToDone(issueId) {
  changeTask(2, issueId);
}
