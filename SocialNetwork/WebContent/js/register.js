
function samePassword(){
  if($("#password1").val() != $("#password2").val()){
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

function commitRegistration(){
	return ($("#emailError").css("display") == "none" && $("#passwordError").css("display") == "none");
}