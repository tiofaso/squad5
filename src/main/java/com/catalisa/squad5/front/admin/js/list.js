const username = 'admin';////
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
            const allIssues = JSON.parse(xhr.responseText); //Getting all JSON content

            //To-do begin
            if(getType() == 0 || getType() == null) {
                issuesContent(allIssues,0,getPage());
            }else if(getType() != 0) {issuesContent(allIssues,0,1)}
           
            pagination(allIssues,0);
            //To-do end
            
            
            //Doing begin
            if(getType() == 1) {
                issuesContent(allIssues,1,getPage());
            }else {issuesContent(allIssues,1,1);}
            pagination(allIssues,1);
            //Doing end
            
            //Done begin
            if(getType() == 2) {
                issuesContent(allIssues,2,getPage());
            }else { issuesContent(allIssues,2,1);}
            pagination(allIssues,2);
            //Done end

            //------
        } else if (xhr.status === 401) {
            document.getElementById('msg').style.display = 'block';
        }
    }
};

let contItens = 0;

function issuesContent(allIssues, type, page) {
   // console.log(allIssues)


    //Filtering data by type
    const filteredInfo = filterAndStoreIssues(allIssues,type);

    //Creating array
    const filteredInfoArray = filteredInfo.split('\n');

    //console.log(filteredInfoArray);

    for (let i = 0; i < filteredInfoArray.length; i++) {
        const line = filteredInfoArray[i];
        // Process each line as needed
       //console.log(`${line}`);
    }

    let inicialTable = 0;
    if(page == null || page == 1) {inicialTable = 0;}
    else { inicialTable = page - 1;}

    
    let tableHtml = createTableFromInfo(filteredInfo,inicialTable);

    const sizeDb = allIssues.length;

    //Var for the HTML data
    let issueData = "";

    //Var for itens list
    contItens = 0;

    // Sort allIssues array by id in ascending order
    allIssues.sort((a, b) => a.id - b.id);

    // Find the first occurrence where issue.task is equal to type
    const firstMatchingIssue = allIssues.find(issue => issue.task === type);

    //Checking for id = 0
    if(firstMatchingIssue.id != 0) { firstMatchingIssue.id = firstMatchingIssue.id - 1;}

   //Initial value based in pagination
    let jInit = firstMatchingIssue.id;


    
    
    //Selecting what type of story to show
    switch(type) {
        case 1:
            
            document.getElementById("issuesDoing").innerHTML = tableHtml;
            break;
        case 2:
            document.getElementById("issuesDone").innerHTML = tableHtml;
            break;
        default:
            document.getElementById("issuesToDo").innerHTML = tableHtml;
    }
    
}

function pagination(jsonData,type) {
   
    //Getting the total number os elements
    let maxElements = 0;
    jsonData.forEach(values => {
        if(values.task === type) {maxElements++;}
    });

    //Calculating how many pages are needed
    let totalPages = 0;
    let contPages = 0;
    jsonData.forEach(values => {
        if(values.task === type) {
            if(contPages == 5) {
                totalPages++;
                contPages = 0;
            }else {contPages++;}
        }
    });

    //Adding an extra page for extra elements
    if((totalPages * 5) < maxElements) {totalPages++;}
    
    //Getting the index elements for each page
    contPages = 0;
    let contIndex = 0;
    const pagesIndex = [];
    jsonData.forEach(values => {
        if (values.task === type) {
            if (contPages % 5 === 0) {
                pagesIndex[contIndex] = values.id;
                contIndex++;
            }
            contPages++;
        }
    });

    //Making pagination
    let pagesLinks ='';

    for(let i = 1; i <= totalPages; i++) {
        pagesLinks += `
            <th scope="row"><a href="story.html?&pg=${i}&type=${type}#${type}" id="${type}">${i}</a></td>`;
        
    }

    //Selecting what type of story to show
    switch(type) {
        case 1:
            document.getElementById("paginationDoing").innerHTML = pagesLinks;
            break;
        case 2:
            document.getElementById("paginationDone").innerHTML = pagesLinks;
            break;
        default:
            document.getElementById("paginationToDo").innerHTML = pagesLinks;
    }

}

function getIndex() {
    const urlParams = new URLSearchParams(window.location.search);
    const index = parseInt(urlParams.get('index')) || 0;
    return index;
}

function getType() {
    const urlParams = new URLSearchParams(window.location.search);
    const index = parseInt(urlParams.get('type')) || null;
    return index;
}

function getPage() {
    const urlParams = new URLSearchParams(window.location.search);
    const index = parseInt(urlParams.get('pg')) || null;
    return index;
}


function filterAndStoreIssues(allIssues, type) {
    let filteredIssuesArray = []; // Initialize an array to store the filtered information

    for (const newIssues of allIssues) {
        
        if (newIssues.task === type) { 
            // Create a string containing the specific fields
            const issueInfo = `ID: ${newIssues.id}, URL: ${newIssues.url}, Name: ${newIssues.nameCompany}, Time: ${newIssues.time}, Date: ${newIssues.date}, Task: ${newIssues.task}`;
           //console.log(issueInfo);
            filteredIssuesArray.push(issueInfo); // Push the string into the array
           
        }
        
    }

    return filteredIssuesArray.join('\n'); // Join the sorted array into a string
}

function createTableFromInfo(filteredInfo, startIndex) {
    const lines = filteredInfo.split('\n');

    let table = '<table>';
  
    table += '<tbody>';

    let newStartIndex = 0;
    let endIndex = newStartIndex + 5;
  
    switch(startIndex) {
        case 0:
            newStartIndex = 0;
            endIndex = 5;
            break;
        case 1:
            newStartIndex = 5;
            endIndex = 10;
            break;
        case 2:
            newStartIndex = 5*2;
            endIndex = 10+5;
            break;
        case 3:
            newStartIndex = 15;
            endIndex = 20;
            break;
        default:
            newStartIndex = 20;
            endIndex = 25;
    }
    
    for (let i = newStartIndex; i < endIndex && i < lines.length; i++) {
        const line = lines[i];
        //console.log(line)
        const matches = line.match(/ID: (\d+), URL: (.+), Name: (.+), Time: (.+), Date: (.+), Task: (\d+)/);
        if (matches) {
            const [_, id, url, name, time, date, task] = matches;
            table += `<tr><td style="width:3%;">${id}</td><td>${url}</td><td style="width:15%;">${name}</td><td style="width:10%;">${time}</td><td style="width:18%;">${date}</td><td style="width:10%;">${task}</td></tr>`;
        }
    }

    table += '</tbody></table>';
    return table;
}




xhr.send();