class Setting {
	constructor(backgroundMode, image, heading, color) {
		Object.assign(this, {backgroundMode, image, heading, color});
	}
}

class CountdownSetting extends Setting {

	#date;
	#time;

	constructor(backgroundMode, image, heading, color, date, time) {
		super(backgroundMode, image, heading, color);
		this["@type"] = "Countdown";
		this.#date = date;
		this.#time = time;
	}

	set date(date) {
		this.#date = date;
	}

	set time(time) {
		this.#time = time;
	}

	get datetime() {
		return `${this.#date}-${this.#time}`;
	}

	toJSON() {
		return {
			backgorundMode: this.backgroundMode,
			image: this.image,
			heading: this.heading,
			color: this.color,
			datetime: this.datetime,
			'@type': this["@type"]
		}
	}
}

class MessageSetting extends Setting {

	constructor(backgroundMode, image, heading, color, message) {
		super(backgroundMode, image, heading, color);
		this.message = message;
		this["@type"] = "Message";
	}
}

var operationType;

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

		operationType = "Countdown";

		document.getElementById("ot-message-input-message").readOnly = true;
		document.getElementById("ot-countdown-input-date").readOnly = false;
		document.getElementById("ot-countdown-input-time").readOnly = false;
		// Change button color

	}else if(buttonType === "MESSAGE") {

		operationType = "Message";

		document.getElementById("ot-message-input-message").readOnly = false;
		document.getElementById("ot-countdown-input-date").readOnly = true;
		document.getElementById("ot-countdown-input-time").readOnly = true;
		//Change button color
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

	const setting = operationType === "Countdown" ? new CountdownSetting() : new MessageSetting();

	Array.from(document.querySelectorAll(".interesting-value:not([readonly])")).map((element) => (
		{[element.name]: element.value}
	)).forEach(element => (Object.assign(setting, element)));

	sendFormData(setting);

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

	console.log(JSON.stringify(body));

	sendRequest(showSuccessMessage, window.location.origin + "/rest/countdownData", "POST", JSON.stringify(body), new Headers(
		{'content-type': 'application/json'}
	));

}





