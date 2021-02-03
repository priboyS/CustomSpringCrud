var form = document.getElementById("registrationForm");
var passwordError = document.getElementById("errorPassword");

form.addEventListener("submit", function(event){
    var password = document.getElementById("password");
    var confirm = document.getElementById("confirm");

    if(password.value !== confirm.value){
        passwordError.innerText = "Пароли не совпадают";
        event.preventDefault();
    }

}, false);