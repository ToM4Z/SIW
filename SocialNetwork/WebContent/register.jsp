<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<html>
<head lang="it">
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">

<title>LoosyNet</title>

<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>

<link href="css/login.css" rel="stylesheet" />
<link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<script>
function checkFB(){
	if("${param.emailFB}" != ""){
		$("#email").val("${param.emailFB}");
		console.log("${param.emailFB}");
		$("#nome").val("${param.name}");
		console.log("${param.name}");
		$("#cognome").val("${param.surname}");
		console.log("${param.surname}");
		$("#datadinascita").val("${param.birthday}");
		console.log("${param.birthday}");
	}
}


function samePassword(){
  if($("#password").val() != $("#password1").val()){
    $("#passwordError").slideDown();
    $("#passwordError").css("display:block");
  }else{
    $("#passwordError").slideUp();
    $("#passwordError").css("display:none");
  }
}

function checkEmailExist(){
  $.get("register?email="+$("#email").val(), function(responseText) {
      if(responseText == "true"){
        $("#emailError").slideDown();
        $("#emailError").css("display:block");
      }else{
        $("#emailError").slideUp();
        $("#emailError").css("display:none");
      }
  });
}

var codice;

function commitRegistration(){
	if($("#emailError").css("display") == "none" && $("#passwordError").css("display") == "none"){
		if("${param.emailFB}" != ""){
			registra();
		}else{
		$("#mexEmail").text("E' stata inviata una email di conferma a "+$("#email").val()+"\nInserisci il codice");
		  codice = makeid();

	    $("#sectionform1").hide();
	    $("#sectionform2").show();

	  var json = JSON.stringify({"email" : $("#email").val(),"codice" : codice});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","sendEmail", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "error"){
			$("#sectionform2").hide();
	        $("#sectionform4").show();
	      }
	  }
	  xhr.send(json);
	}
  }
}

function registra(){
	$("#sectionform1").hide();
    $("#sectionform3").show();
	
	 var json = JSON.stringify({"email" : $("#email").val(),"nome" : $("#nome").val(),"cognome" : $("#cognome").val(),
         "datadinascita" : $("#datadinascita").val(),"nickname" : $("#nickname").val(),
         "password" : $("#password").val()});
	var xhr = new XMLHttpRequest();
	xhr.open("post","register", true);
	xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	xhr.setRequestHeader("Content-Type", "application/json");
	xhr.onreadystatechange = function(){
		if(xhr.responseText === "error"){
			$("#sectionform1").hide();
			$("#sectionform2").hide();
	        $("#sectionform3").hide();
			$("#sectionform4").show();
		}
	}
	xhr.send(json);
}

function confirmCodeEmail(){
	if(codice == $("#code").val()){
		$("#sectionform2").hide();
        $("#sectionform3").show();

        registra();
	}else{
		$("#codeError").slideDown();
		$("#codeError").css("display:block");
	}
}

function reTryCommitCodeEmail(){
    $("#sectionform3").hide();
    $("#sectionform4").hide();
	$("#submit1").trigger("click");
}

function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 5; i++)
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    return text;
  }


</script>
<body onload="javascript:checkFB()">
	<div class="container">
		<div class="row row-centerd pos" id="pwd-container">
			<div class="col-md-3"></div>
			<div class="col-md-6">
				<section id="sectionform1" class="login-form">
					<form id="form1" method="post" action="javascript:commitRegistration()" role="login">
						<img src="images/logo.png" style="width: 180px; margin-bottom: 0px;" class="img-responsive"	alt="LoosyNet" />
						<div id="emailError" style="margin-top: 10px; margin-bottom: -0.5px; display: none" class="alert alert-danger" role="alert">
							Email già  registrata!
						</div>
						<div id="passwordError"	style="margin-top: 10px; margin-bottom: -0.5px; display: none" class="alert alert-danger" role="alert">
							Le password non	combaciano!
						</div>
						<table style="margin-bottom: -15px">
							<tr>
								<td><input type="text" id="nome" placeholder="Nome"
									required class="form-control" /></td>
								<td><input type="text" id="cognome" placeholder="Cognome"
									required class="form-control" /></td>
							</tr>
						</table>
						<table style="margin-bottom: -15px">
							<tr>
								<td><input type="text" id="nickname"
									placeholder="Nickname" required class="form-control" /></td>
								<td><input type="text" required
									onfocus="(this.type='date')" onblur="(this.type='text')"
									id="datadinascita" class="form-control"
									placeholder="Data di nascita"></td>
							</tr>
						</table>
						<input type="email" id="email" placeholder="Email"
							required class="form-control input-lg"
							onchange="javascript:checkEmailExist()" /> 
						<input type="password" class="form-control input-lg" id="password"
							placeholder="Password" required onkeyup="javascript:samePassword()"/> 
						<input type="password" class="form-control input-lg" id="password1"
							placeholder="Conferma Password" required onkeyup="javascript:samePassword()" />
						<button id="submit1" type="submit" class="btn btn-lg btn-primary btn-block">Registrati</button>
					</form>
				</section>
				<section id="sectionform2" class="login-form" style="display: none">
					<form id="form2" method="post" action="javascript:confirmCodeEmail()" role="login">
						<img src="images/logo.png"
							style="width: 180px; margin-bottom: 0px;" class="img-responsive"
							alt="LoosyNet" />
						<div id="mexEmail"
							style="margin-top: 10px; margin-bottom: -0.5px;"
							class="alert alert-info" role="alert">
						</div>
						<div id="codeError"
							style="margin-top: 10px; margin-bottom: -0.5px; display: none"
							class="alert alert-danger" role="alert">
							Il codice inserito non è valido!
						</div>
						<input type="text" class="form-control input-lg" id="code"
							placeholder="Codice" required />
						<button type="submit" class="btn btn-lg btn-primary btn-block">Invia</button>
						<div>
							<a href="javascript:reTryCommitCodeEmail()">Reinvia l'email</a>
						</div>
					</form>
				</section>
				<section id="sectionform3" class="login-form" style="display: none">
					<form id="form3" method="post" action="login.html" role="login">
						<img src="images/logo.png"
							style="width: 180px; margin-bottom: 0px;" class="img-responsive"
							alt="LoosyNet" />
						<div id="success"
							style="margin-top: 10px; margin-bottom: -0.5px;"
							class="alert alert-success" role="alert">
							Registrazione completata con successo!
						</div>
						<button type="submit" class="btn btn-lg btn-primary btn-block">Torna al Login</button>
					</form>
				</section>
				<section id="sectionform4" class="login-form" style="display: none">
					<form id="form4" method="post" action="javascript:reTryCommitCodeEmail()" role="login">
						<img src="images/logo.png"
							style="width: 180px; margin-bottom: 0px;" class="img-responsive"
							alt="LoosyNet" />
						<div id="sendEmailError"
							style="margin-top: 10px; margin-bottom: -0.5px;"
							class="alert alert-danger" role="alert">
							Ops...<br>
							Ci deve essere un problema con i server..
							Riprova più tardi
						</div>
						<button type="submit" class="btn btn-lg btn-primary btn-block">Riprova</button>
					</form>
				</section>
			</div>
			<div class="col-md-6"></div>
		</div>
	</div>
</body>
</html>
