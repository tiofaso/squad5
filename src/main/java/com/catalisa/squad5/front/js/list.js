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
            const allIssues = JSON.parse(xhr.responseText);

            //------ To Do - begin
            let dataToDo = "";

            allIssues.forEach((values) => {
                if (values.task === 0) {
                    dataToDo += `
                        <tr>
                            <th scope="row">${values.id}</th>
                            <td>${values.url}</td>
                            <td>${values.nameCompany}</td>
                            <td>${values.time}</td>
                            <td>${values.date}</td>
                            <td>${values.task}</td>
                        </tr>
                    `;
                }
            });

            document.getElementById("issuesToDo").innerHTML = dataToDo;
            //------ To Do - end

             //------ Doing - begin
             let dataDoing = "";
 
             allIssues.forEach((values) => {
                 if (values.task === 1) {
                    dataDoing += `
                         <tr>
                             <th scope="row">${values.id}</th>
                             <td>${values.url}</td>
                             <td>${values.nameCompany}</td>
                             <td>${values.time}</td>
                             <td>${values.date}</td>
                             <td>${values.task}</td>
                         </tr>
                     `;
                 }
             });
 
             document.getElementById("issuesDoing").innerHTML = dataDoing;
             //------ Doing - end

             //------ Done - begin
            let dataDone = "";

            allIssues.forEach((values) => {
                if (values.task === 2) {
                    dataDone += `
                        <tr>
                            <th scope="row">${values.id}</th>
                            <td>${values.url}</td>
                            <td>${values.nameCompany}</td>
                            <td>${values.time}</td>
                            <td>${values.date}</td>
                            <td>${values.task}</td>
                        </tr>
                    `;
                }
            });

            document.getElementById("issuesDone").innerHTML = dataDone;
            //------ To Do - end
            //------
        } else if (xhr.status === 401) {
            document.getElementById('msg').style.display = 'block';
        }
    }
};

xhr.send();
