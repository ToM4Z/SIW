
function samePassword(){
  if($("#password").val() != $("#password1").val()){
    $("#passwordError").slideDown();
    $("#passwordError").css("display:block");
  }else{
    $("#passwordError").slideUp();
    $("#passwordError").css("display:none");
  }
}

function checkEmailNotExist(){
  $.get("register?email="+$("#email").val(), function(responseText) {
      if(responseText == "false"){
        $("#emailNotExistsError").slideDown();
        $("#emailNotExistsError").css("display:block");
      }else{
        $("#emailNotExistsError").slideUp();
        $("#emailNotExistsError").css("display:none");
      }
  });
}

var codice;

function sendCodeEmail(){
	if($("#emailNotExistsError").css("display") == "none"){
		$("#mexEmail").text("E' stata inviata una email di conferma a "+$("#email").val()+"\nInserisci il codice");
		codice = makeid();

	    $("#sectionform1").hide();
	    $("#sectionform2").show();

	  var json = JSON.stringify({"email" : $("#email").val(),"codice" : codice});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","sendEmail?reset=true", true);
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

function reTryCommitCodeEmail(){
    $("#sectionError").hide();
	$("#submit1").trigger("click");
}

function confirmCodeEmail(){
	if(codice == $("#code").val()){
		$("#sectionform2").hide();
        $("#sectionform3").show();
	}else{
		$("#codeError").slideDown();
		$("#codeError").css("display:block");
	}
}

function sendNewPassword(){
	if($("#passwordError").css("display") == "none"){

		$("#sectionform3").hide();
		$("#sectionSuccess").show();
		
		  var json = JSON.stringify({"email" : $("#email").val(),"password" : $("#password").val()});
		  var xhr = new XMLHttpRequest();
		  xhr.open("post","resetPassword", true);
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

function resetPage(){
	$("#sectionError").hide();
	$("#sectionForm2").hide();
	$("#sectionForm3").hide();
	$("#sectionSuccess").hide();
	$("#sectionform1").show();
}

function makeid() {
    var text = "";
    var possible = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    for (var i = 0; i < 5; i++)
      text += possible.charAt(Math.floor(Math.random() * possible.length));
    return text;
  }