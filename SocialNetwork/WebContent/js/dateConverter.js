
function convertDate(data,orario){
	var pezzi = data.split(/ /);
	var mese;
	switch(pezzi[1]){
	case 'Jan': mese='01'; break;
	case 'Feb': mese='02'; break;
	case 'Mar': mese='03'; break;
	case 'Apr': mese='04'; break;
	case 'May': mese='05'; break;
	case 'Jun': mese='06'; break;
	case 'Jul': mese='07'; break;
	case 'Aug': mese='08'; break;
	case 'Sep': mese='09'; break;
	case 'Oct': mese='10'; break;
	case 'Nov': mese='11'; break;
	case 'Dec': mese='12'; break;
	}
	if(orario === false)
		return pezzi[2]+"/"+mese+"/"+pezzi[5];
	
	var orario = pezzi[3].split(/:/);
	return pezzi[2]+"/"+mese+"/"+pezzi[5]+"  "+orario[0]+":"+orario[1];
}