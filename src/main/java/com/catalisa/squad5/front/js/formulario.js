function reportIssue(event){
  event.preventDefault();

  //This is a temporary code to lock the session active
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
            //------

            //Everything is fine and logged. Now do the magic!!!
            const url = document.getElementById('endereco-site').value;
            const nameCompany = document.getElementById('nome-empresa').value;
            const email_reporter = document.getElementById('email').value;
            const description = document.getElementById('descricao-problema').value;

            //Building the payload
            const payload = JSON.stringify({
                url,
                nameCompany,
                email_reporter,
                description
            });

            //Configuring fetch
            const requestOptions = {
              method: 'POST',
              headers: {
                'Content-Type': 'application/json'
              },
              body: payload
            };
            
            //Send payload through endpoint
            fetch('http://localhost:8080/issues', requestOptions)
            .then(response => response.json())
            .then(data => {
              console.log('Issue reported successfully:', data);
              // Add any further actions you want to take after reporting the issue
            })
            .catch(error => console.error('Error reporting the issue:', error));
            
            //------
        } else if (xhr.status === 401) {
            document.getElementById('msg').style.display = 'block';
        }
    }
};

xhr.send();
}