function validate(){
	
	var ok = true;
	var name = document.getElementById("name").value;
	var credit = document.getElementById("credit_taken").value;
	
	if (name == "") {
		
		alert("Invalid Name. Length of Name should be atleast 1");
		document.getElementById("name-error").style.display = "inline";
		document.getElementById("name-error").style.color = "red";
		
		ok = false;
	} else {
		document.getElementById("name-error").style.display = "none";
	}
	
	if (isNaN(credit) || credit < 0) {
		
		alert("Credit Taken invalid! Cannot be Negative or empty.");
		document.getElementById("credit_taken-error").style.display = "inline";
		document.getElementById("credit_taken-error").style.color = "red";
		
		ok = false;
	} else {
		
		document.getElementById("credit_taken-error").style.display = "none";
	}
		
	return ok;
}

function reportAjax(address){
	
	valid = validate();
	if(valid == false)
		return false;
		
	var request = new XMLHttpRequest();
	var data='';
	
	data += "namePrefix=" + document.getElementById("name").value + "&";
	data += "credit_taken=" + document.getElementById("credit_taken").value;
	
	request.open("GET", (address + "&" + data), true);
	request.onreadystatechange = function() {
		handler(request);
	};
	request.send(null);
}

function handler(request){
	
	 if ((request.readyState == 4) && (request.status == 200)){
	 	
	 	var target = document.getElementById("result");
		var tableData = '<table><tr><th>Student Name</th><th>Credits taken</th><th>Credits toGraduate</th></tr>';
		
		try{
			var data=JSON.parse(request.responseText);
			data.students.forEach(student => {
				tableData += '<tr><td>'+student.name+'</td><td>'+student.credit_taken+'</td><td>'+student.credit_graduate+'</td></tr>';
			});
		}
		catch(err){
			console.log(err);
		}
		
		tableData += '</table>';
		target.innerHTML = tableData;
	}
}