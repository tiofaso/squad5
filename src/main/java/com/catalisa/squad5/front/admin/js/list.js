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
            const allIssues = JSON.parse(xhr.responseText); //Getting all JSON content

            //To-do begin
            issuesContent(allIssues,0,getPage());
            pagination(allIssues,0);
            //To-do end
            
            
            //Doing begin
            issuesContent(allIssues,1,getPage());
            pagination(allIssues,1);
            //Doing end
            
            //Done begin
            issuesContent(allIssues,2,getPage());
            pagination(allIssues,2);
            //Done end

            //------
        } else if (xhr.status === 401) {
            document.getElementById('msg').style.display = 'block';
        }
    }
};

function issuesContent(allIssues, type, page) {
    for (let j = 0; j < allIssues.length; j++) {
        const record = allIssues[j];
    
        if (record.task === type) {
            firstRecord = j;
            break;
        }
    }

    let startingIndex = getIndex() + firstRecord;
    
    //Lock pagination 
    if(type != getType()) {
        startingIndex = firstRecord;
    }else if(getPage() == 0){startingIndex = getIndex()-1;}
    else{startingIndex = getIndex();}

    //For the first item only
    if (startingIndex < 0) {
        startingIndex = 0;
        firstRecord = 0;
    } 

    let issueData = "";
    
    // Iterate from the starting index for 5 iterations or until the end of the array
    for (let i = startingIndex; i <  startingIndex + 4; i++) {
       
        const values = allIssues[i];

        if (values && values.task !== undefined && values.task === type) {
        
                issueData += `
                <tr>
                    <th scope="row">${values.id}</th>
                    <td>${values.url}</td>
                    <td>${values.nameCompany}</td>
                    <td>${values.time}</td>
                    <td>${values.date}</td>
                    <td>${values.task}</td>
                </tr>
                `;   
                cont = 0;    
        }
    }
    
    //Selecting what type of story to show
    switch(type) {
        case 1:
            document.getElementById("issuesDoing").innerHTML = issueData;
            break;
        case 2:
            document.getElementById("issuesDone").innerHTML = issueData;
            break;
        default:
            document.getElementById("issuesToDo").innerHTML = issueData;
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
            if(contPages == 4) {
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
    for(let i = 0; i < pagesIndex.length; i++) {
        pagesLinks += `
            <th scope="row"><a href="story.html?&pg=${i}&index=${pagesIndex[i]}&type=${type}">${i}</a></td>`;
        
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
    const index = parseInt(urlParams.get('type')) || 0;
    return index;
}

function getPage() {
    const urlParams = new URLSearchParams(window.location.search);
    const index = parseInt(urlParams.get('pg')) || 0;
    return index;
}


xhr.send();
