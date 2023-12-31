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

// Ordena os cards por ID antes de chamar populateKanban
xhr.onreadystatechange = function () {
  if (xhr.readyState === XMLHttpRequest.DONE) {
    if (xhr.status === 200) {
      issues = JSON.parse(xhr.responseText);
      issues.sort((a, b) => a.id - b.id);
      populateKanban(issues);
    } else if (xhr.status === 401) {
      document.getElementById("msg").style.display = "block";
    }
  }
};

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
        <p class="card-text"><strong>URL:</strong> ${values.url} <br>
        <strong>Empresa:</strong> ${values.nameCompany} <br>
        <strong>Descrição:</strong> ${values.description} 
        <div class="btn-group" role="group">
        ${getButtons(values.task, values.id)}
        </div>
        </div>
        </div>
        </li>
        `;

    // Adiciona o card à coluna correspondente
    if (values.task === 0 && todoCount < 5) {
      issuesToDo.innerHTML += issueElement;
      todoCount++;
    } else if (values.task === 1 && doingCount < 5) {
      issuesDoing.innerHTML += issueElement;
      doingCount++;
    } else if (values.task === 2 && doneCount < 5) {
      issuesDone.innerHTML += issueElement;
      doneCount++;
    }
  });
}

function getButtons(task, issueId) {
  if (task === 0) {
    buttonHtml = `<button type="button" class="btn btn-outline-secondary btn-sm" onclick="moveToInProgress(${issueId})">Mover para In Progress</button>`;
  } else if (task === 1) {
    buttonHtml = `<button type="button" class="btn btn-outline-warning btn-sm" onclick="moveToDone(${issueId})">Mover para Done</button>`;
  } else if (task === 2  || task === 3) {
    buttonHtml = `<button type="button" class="btn btn-outline-danger btn-sm" onclick="deleteIssue(${issueId})">Deletar</button>`;
  }
  return buttonHtml;
}

function changeTask(newTask, issueId) {
  const xhrChangeTask = new XMLHttpRequest();
  xhrChangeTask.open(
      "PUT",
      `http://localhost:8080/issues/task/${issueId}`,
      true
  );
  xhrChangeTask.setRequestHeader("Content-Type", "application/json");
  xhrChangeTask.setRequestHeader("Authorization", `Basic ${base64Credentials}`);

  const body = JSON.stringify({ task: newTask });

  xhrChangeTask.onreadystatechange = function () {
      if (xhrChangeTask.readyState === XMLHttpRequest.DONE) {
          if (xhrChangeTask.status === 200) {
              console.log("Tarefa atualizada com sucesso no servidor.");
              // Não precisamos fazer nada aqui, pois já atualizamos localmente
          } else {
              console.error("Erro ao atualizar tarefa no servidor:", xhrChangeTask.status);
          }
      }
  };

  xhrChangeTask.send(body);
}

function moveToInProgress(issueId) {
  // Encontre a tarefa no array 'issues' pelo ID
  const issue = issues.find(issue => issue.id === issueId);
  // Atualize a task para 'In Progress' (task 1)
  issue.task = 1;
  // Atualize o kanban após mover para 'In Progress'
  populateKanban(issues);
  // Agora, envie a requisição para o servidor para atualizar a task
  changeTask(1, issueId);
}

function moveToDone(issueId) {
  const issue = issues.find(issue => issue.id === issueId);
  issue.task = 2;
 populateKanban(issues);
changeTask(2, issueId);
}

function deleteIssue(issueId) {
  changeTask(3, issueId); // Mover para o estado "Done" (task 2)
  const deletedIssue = issues.find(issue => issue.id === issueId);

  const index = issues.indexOf(deletedIssue);
  if (index !== -1) {
    issues.splice(index, 1); // Remover do array de issues
  }
  populateKanban(issues); // Atualizar o kanban
}
