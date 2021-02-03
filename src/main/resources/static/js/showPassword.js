var checkBoxPassword = document.getElementById("showPassword");
var checkBoxConfirm = document.getElementById("showConfirm");
var password = document.getElementById("password");
var confirm = document.getElementById("confirm");

checkBoxPassword.addEventListener("click", function(event){
    if(password.type === "password"){
        password.type = "text";
    }else{
        password.type = "password";
    }
}, false);

checkBoxConfirm.addEventListener("click", function(event){
    if(confirm.type === "password"){
        confirm.type = "text";
    }else{
        confirm.type = "password";
    }
}, false);