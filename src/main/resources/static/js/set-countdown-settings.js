window.onload = function() {

	sendRequest(injectAvailableImages, window.location.origin + "/rest/getAvailableLogos", "GET", null, new Headers());
	
	buttonClick("COUNTDOWN");
	
	document.getElementById("ot-both-background-mode").addEventListener("change", function() {
		
		if(document.getElementById("ot-both-background-mode").value === "BLURREDIMAGE") {
			
			document.getElementById("bm-onecolor-color").disabled = true;

		}else if(document.getElementById("ot-both-background-mode").value === "ONECOLOR") {
			
			document.getElementById("bm-onecolor-color").disabled = false;
			
		}
		
	});

}

function buttonClick(buttonType) {

	if(buttonType === "COUNTDOWN") {

		document.getElementById("ot-selection-hidden").value = "COUNTDOWN";
		document.getElementById("ot-message-input-message").readOnly = true;
		document.getElementById("ot-countdown-input-date").readOnly = false;
		document.getElementById("ot-countdown-input-time").readOnly = false;
		document.getElementById("button-countdown").setAttribute("class", "btn btn-dark");
		document.getElementById("button-message").setAttribute("class", "btn btn-light");

	}else if(buttonType === "MESSAGE") {

		document.getElementById("ot-selection-hidden").value = "MESSAGE";
		document.getElementById("ot-message-input-message").readOnly = false;
		document.getElementById("ot-countdown-input-date").readOnly = true;
		document.getElementById("ot-countdown-input-time").readOnly = true;
		document.getElementById("button-countdown").setAttribute("class", "btn btn-light");
		document.getElementById("button-message").setAttribute("class", "btn btn-dark");

	}
}

function sendRequest(functionToCall, url, type, body, headers){
	let request = {
		method: type,
		headers: headers,
		body: body
	};
	let responseObj = {
		responseBody: null,
		responseStatus: null
	}

	fetch(url, request)
		.then(function(response) {
			if(!response.ok) {
				throw Error(response.status);
			}
			responseObj.responseStatus = response.status;
			return response.text();
		})
		.then(function(text) {
			if(text.length > 0) {
				return JSON.parse(text);
			}else{
				return null;
			}
		})
		.then(function(response){
			responseObj.responseBody = response;
			functionToCall(responseObj);
		})
		.catch(function(error) {
			console.log(error.message);
			appendError("Server returned error with code " + error.message);
		});
}

function injectAvailableImages(responseObj) {

	let response = responseObj.responseBody;
	for(i=0;i<response.length;i++) {
		document.getElementById("ot-both-input-images").options.add(new Option(response[i], response[i]));
	}
	
}

function validateFormData() {

	clearErrors();

	var body = new FormData(document.getElementById("countdown-settings-form"));
	
	if(body.get("backgroundMode") === "BLURREDIMAGE") {
		body.append("color", null);
	}

	let errorPresent = false;

	if(body.get("image") == null) {
		appendError("You must select a logo");
		errorPresent = true;
	}

	if(body.get("heading").length == 0) {
		appendError("You must specify a heading");
		errorPresent = true;
	}
	if(body.get("operationType") === "COUNTDOWN") {
		if(body.get("date").length == 0 || body.get("time").length == 0) {
			appendError("Neither date nor time can be null");
			errorPresent = true;
		}
	}else if(body.get("operationType") === "MESSAGE") {
		if(body.get("message").length == 0) {
			appendError("You must specify a message");
			errorPresent = true;
		}
	}

	if(!errorPresent) {
		sendFormData(body);
	}

}

function appendError(message) {
	console.log("Appending error with message " + message);
	let error_div = document.createElement("div");
	error_div.setAttribute("class", "alert alert-danger mt-1");
	error_div.innerHTML = message;

	document.getElementById("status").appendChild(error_div);
}

function clearErrors() {
	let errors = document.getElementsByClassName("alert");
	for(var i=0;i<errors.length;i++) {
		document.getElementById("status").removeChild(errors[i]);
	}
}

function showSuccessMessage(responseObj) {
	if(responseObj.responseStatus == 200) {
		let success_div = document.createElement("div");
		success_div.setAttribute("class", "alert alert-success mt-1");
		success_div.innerHTML = "The data was sucessfully transmitted to the server";

		document.getElementById("status").appendChild(success_div);
	}
}

function sendFormData(body) {

	var datetime = body.get("date") + "-" + body.get("time");

	body.delete("date");
	body.delete("time");
	body.append("datetime", datetime);

	sendRequest(showSuccessMessage, window.location.origin + "/rest/countdownData", "POST", body, new Headers());
}





