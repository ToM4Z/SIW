
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

		$("#mexEmail").text("E' stata inviata una email di conferma a "+$("#email").val()+"\nInserisci il codice");
		  codice = makeid();

	    $("#sectionform1").hide();
	    $("#sectionform2").show();

	  var json = JSON.stringify({"email" : $("#email").val(),"codice" : codice});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","sendEmail", true);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
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

function confirmCodeEmail(){
	if(codice == $("#code").val()){
		$("#sectionform2").hide();
        $("#sectionform3").show();

      var json = JSON.stringify({"email" : $("#email").val(),"nome" : $("#nome").val(),"cognome" : $("#cognome").val(),
                                  "datadinascita" : $("#datadinascita").val(),"nickname" : $("#nickname").val(),
                                  "password" : $("#password").val()});
  	  var xhr = new XMLHttpRequest();
  	  xhr.open("post","register", true);
  	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
  	  xhr.setRequestHeader("Content-Type", "application/json");
  	  xhr.onreadystatechange = function(){
  		  if(xhr.responseText == "error"){
  			$("#sectionform2").hide();
  	        $("#sectionform4").show();
  	      }
  	  }
  	  xhr.send(json);
	}else{
		$("#codeError").slideDown();
		$("#codeError").css("display:block");
	}
}

function reTryCommitCodeEmail(){
    $("#sectionform3").hide();
	$("#submit1").trigger("click");
}

function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 5; i++)
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    return text;
  }
