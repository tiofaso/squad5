const username = 'admin';
const password = '12345';
const base64Credentials = btoa(`${username}:${password}`);

const formData = new FormData(document.getElementById('issue-form')); // Obtém os dados do formulário

fetch('http://localhost:8080/issues', {
    method: 'GET',
    body: formData,
    headers: {
        'Authorization': `Basic ${base64Credentials}`
    }
})
.then(response => {
    if (response.status === 201) {

        //Cadastro de alunos
function cadastraAluno(event) {
    event.preventDefault();

    const endereco = document.getElementById('endereco-site').value;
    const empresa = document.getElementById('nome-empresa').value;
    const email = document.getElementById('email').value;
    const descricao = document.getElementById('descricao-problema').value;

    // Montando json
    const payload = JSON.stringify({
      endereco,
      empresa,
      email,
      descricao
    });

    fetch('http://localhost:8080/issues', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json'
      },
      body: payload
    })
      .then(response => response.json())
      .then(data => {
        console.log('Success:', data);
        // Display the success message
        document.getElementById('msgsucesso').style.display = 'block';
        document.getElementById('cadastrofalha').reset();  // Reset the form
      })
      .catch(error => {
        console.error('Error:', error);
      });
}
    } else {
        // Tratamento para erro
        alert('Erro ao registrar. Por favor, verifique os campos e tente novamente.');
    }
})
.catch(error => {
    console.error('Erro ao enviar os dados:', error);
});