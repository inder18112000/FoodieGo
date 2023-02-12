function go()
{
    var xhttp = new XMLHttpRequest();
    var form = new FormData();
    form.append("email",document.getElementById("email").value);
    form.append("password",document.getElementById("pwd").value);
    xhttp.onreadystatechange = function() {
        if(this.readyState == 4 && this.status == 200) 
        {
            var ans = xhttp.responseText;
            if(ans=="success")
            {          
                localStorage.setItem("admin_email",document.getElementById("email").value);
                alert("Login successfull");
                location.href = "home/Dashboard.html";
            }
        }
    };
    xhttp.open("POST", "/one", true);
    xhttp.send(form);
}

function go2()
{
    const type = document.getElementById("pwd").getAttribute('type') === 'password' ? 'text' : 'password';
    document.getElementById("pwd").setAttribute('type', type);
}