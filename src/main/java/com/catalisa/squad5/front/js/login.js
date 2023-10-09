// Login
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault(); // Prevent the default form submission behavior

    const username = document.getElementById('username').value;
    const password = document.getElementById('password').value;

console.log(username);
console.log(password);

    // Base64 encode the credentials
    const credentials = `${username}:${password}`;
    const base64Credentials = btoa(credentials);

    // Create a request object
    const xhr = new XMLHttpRequest();

    // Define the POST request
    xhr.open('GET', 'http://localhost:8080/issues', true);

    // Set the request headers for JSON
    xhr.setRequestHeader('Content-Type', 'application/json');
    xhr.setRequestHeader('Authorization', `Basic ${base64Credentials}`);

    // Define a callback function to handle the response
    xhr.onreadystatechange = function() {
        
        if (xhr.readyState === XMLHttpRequest.DONE) {
            
            if (xhr.status === 200) {
                // Handle successful login, for example, redirect to a new page
                console.log('Login successful!');
            } else if (xhr.status === 401) {
                // Handle login failure
                document.getElementById('msg').style.display = 'block';
                //alert('Login failed. Please try again.');
            }
        }
    };

    // Send the request with the form data as JSON
    const data = JSON.stringify({
        username: username,
        password: password
    });

    xhr.send(data);
});
