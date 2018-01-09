
function checkEmailNotExist(){
  $.get("register?email="+$("#email").val(), function(responseText) {
      if(responseText == "false"){
        $("#emailError").slideDown();
        $("#emailError").css("display:block");
      }else{
        $("#emailError").slideUp();
        $("#emailError").css("display:none");
      }
  });
}

function checkLogin(){
  if($("#emailError").css("display") == "none"){
	  var json = JSON.stringify({"email" : $("#email").val(),"password" : $("#password").val()});
	  var xhr = new XMLHttpRequest();
	  xhr.open("post","login", false);
	  xhr.setRequestHeader("content-type", "x-www-form-urlencoded");
	  xhr.setRequestHeader("connection","close");
	  xhr.setRequestHeader("Content-Type", "application/json");
	  xhr.onreadystatechange = function(){
		  if(xhr.responseText == "true")
			  window.location.replace("home");
	    	  //$("#changePage").trigger("click");
	      else{
	        $("#passwordError").slideDown();
	        $("#passwordError").css("display:block");
	      }
	  }
	  xhr.send(json);
  }
}
