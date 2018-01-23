
var page = 2;
function shiftLeft(){
	if(page===2){
		$("#homePost").hide();
		$("#barraCanali").show();
		--page;
	}else if(page===3){
		$("#chatGruppo").hide();
		$("#homePost").show();
		--page;
	}
}
function shiftRight(){
	if(page===1){
		$("#barraCanali").hide();
		$("#homePost").show();
		++page;
	}else if(page===2){
		$("#homePost").hide();
		$("#chatGruppo").show();
		++page;
	}
}