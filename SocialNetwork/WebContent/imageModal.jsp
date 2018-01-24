<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="bootstrap-3.3.7-dist/css/bootstrap.min.css">
<script src="js/jquery-3.2.1.min.js"></script>
<script src="bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
<script src="js/dateConverter.js"></script>
<link rel="stylesheet" href="css/buttonFile.css">
<style>

.imagemodal {
    display: none;
    position: fixed;
    z-index: 1; 
    padding-top: 100px;
    left: 0;
    top: 0;
    width: 100%; 
    height: 100%; 
    overflow: auto;
    background-color: rgb(0,0,0);
    background-color: rgba(0,0,0,0.9);
}

.imagemodal-content {
    margin: auto;
    display: block;
    width: 80%;
    max-width: 600px;
}

.imagemodal-content {    
    -webkit-animation-name: zoom;
    -webkit-animation-duration: 0.6s;
    animation-name: zoom;
    animation-duration: 0.6s;
}

@-webkit-keyframes zoom {
    from {-webkit-transform:scale(0)} 
    to {-webkit-transform:scale(1)}
}

@keyframes zoom {
    from {transform:scale(0)} 
    to {transform:scale(1)}
}

.imageClose {
    position: absolute;
    top: 15px;
    right: 35px;
    color: #f1f1f1;
    font-size: 40px;
    font-weight: bold;
    transition: 0.3s;
}

.imageClose:hover,
.imageClose:focus {
    color: #bbb;
    text-decoration: none;
    cursor: pointer;
}

/* 100% Image Width on Smaller Screens */
@media only screen and (max-width: 700px){
    .imagemodal-content {
        width: 100%;
    }
}
</style>
<script>

function showImageModal(url){
	$("#imageModal #img").attr("src",url);
	$("#imageModal").modal("show");
}

</script>
</head>
<body>
<div id="imageModal" class="modal imagemodal">
  <span class="imageClose" onclick='$("#imageModal").modal("hide");'>&times;</span>
  <img class="imagemodal-content" id="img">
  <div id="caption"></div>
</div>
</body>
</html>