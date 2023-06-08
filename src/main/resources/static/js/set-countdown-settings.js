var operationType;

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
			backgroundMode: this.backgroundMode,
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

		document.getElementById("button-countdown").setAttribute("class", "selected-button");
		document.getElementById("button-message").setAttribute("class", "deselected-button");

	}else if(buttonType === "MESSAGE") {

		operationType = "Message";

		document.getElementById("ot-message-input-message").readOnly = false;
		document.getElementById("ot-countdown-input-date").readOnly = true;
		document.getElementById("ot-countdown-input-time").readOnly = true;

		document.getElementById("button-message").setAttribute("class", "selected-button");
		document.getElementById("button-countdown").setAttribute("class", "deselected-button");
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
			appendStatus("Server returned error with code " + error.message, false);
		});
}

function injectAvailableImages(responseObj) {

	let response = responseObj.responseBody;
	for(i=0;i<response.length;i++) {
		document.getElementById("ot-both-input-images").options.add(new Option(response[i], response[i]));
	}

}

function validateFormData() {

	clearStatus();

	const setting = operationType === "Countdown" ? new CountdownSetting() : new MessageSetting();

	Array.from(document.querySelectorAll(".interesting-value:not([readonly])")).map((element) => (
		{[element.name]: element.value}
	)).forEach(element => (Object.assign(setting, element)));

	sendRequest(appendStatus, window.location.origin + "/rest/countdownData", "POST", JSON.stringify(setting), new Headers(
		{'content-type': 'application/json'}));

}

function appendStatus(response, successful = true) {

	console.dir(response);

	let statusHTML = document.getElementById("status").innerHTML;
	statusHTML = `
		${statusHTML} 
		<strong class="${successful ? 'success' : 'error'}">
			${response.responseBody ? 'Successfully changed countdown settings' :  response}	
		</strong>
	`;
	document.getElementById("status").innerHTML = statusHTML;
}

function clearStatus() {
	document.getElementById("status").innerHTML = "";
}





