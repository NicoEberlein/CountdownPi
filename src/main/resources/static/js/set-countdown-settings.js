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
	fetch(url, request)
		.then(response => {

			if(response.ok) {
				return response.json();
			}else{
				return Promise.reject(response);
			}
		})
		.then(data => {
			if(functionToCall != null) {
				functionToCall(data);
			}
		})
		.catch((response) => {
			response.json().then(body => {
				console.error(body.code + " - " + body.message);

				let error_div = document.createElement("div");
				error_div.setAttribute("class", "alert alert-danger mt-1");
				error_div.innerHTML = body.message;

				document.getElementById("errors").appendChild(error_div);

			})
		});
}

function injectAvailableImages(images) {
	
	for(i=0;i<images.length;i++) {
		document.getElementById("ot-both-input-images").options.add(new Option(images[i], images[i]));
	}
	
}

function validateFormData() {

	var body = new FormData(document.getElementById("countdown-settings-form"));
	
	if(body.get("backgroundMode") === "BLURREDIMAGE") {
		body.append("color", null);
	}
	
	if(body.get("image") == null) {
		//showError();
		return;
	}
	if(body.get("heading") == null) {
		//showError();
		return;
	}
	if(body.get("operationType") === "COUNTDOWN") {
		if(body.get("date").length == 0 && body.get("time").length == 0) {
			//showError();
			return;
		}
	}else if(body.get("operationType") === "MESSAGE") {
		if(body.get("message").length == null) {
			//showError();
			return;
		}
	}
	
	sendFormData(body);
}

function sendFormData(body) {

	var datetime = body.get("date") + "-" + body.get("time");

	body.delete("date");
	body.delete("time");
	body.append("datetime", datetime);

	sendRequest(null, window.location.origin + "/rest/countdownData", "POST", body, new Headers());
}





