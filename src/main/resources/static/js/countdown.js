/*{
    "@type": "Countdown",
    "operationType": "COUNTDOWN",
    "backgroundMode": "ONECOLOR",
    "image": "a.jpg",
    "heading": "Hallo",
    "color": "#c8d421",
    "date": "2022-05-10T09:18:00.000Z"
}*/

window.onload = function() {
    receiveCountdownData();
    window.setInterval(receiveCountdownData, 5000);
}

function receiveCountdownData() {
    sendRequest(processCountdownData, window.location.origin + "/rest/countdownData", "GET", null, new Headers());
}

function processCountdownData(data) {
    data.date = new Date(data.date);

    if(data.operationType === "COUNTDOWN") {

        document.getElementById("ot-countdown-countdown").hidden = false;
        document.getElementById("ot-message-text").hidden = true;

    }else if(data.operationType === "MESSAGE") {

        document.getElementById("ot-message-text").hidden = false;
        document.getElementById("ot-countdown-countdown").hidden = true;

    }

    if(data.backgroundMode === "BLURREDIMAGE") {
        document.body.style.backgroundImage = "url('http://localhost:8080/rest/getImage/5.jpg')";
        document.body.style.backgroundSize = "cover";
    }else if(data.backgroundMode === "ONECOLOR") {
        document.body.style.background = data.color;
    }

    document.getElementById("ot-both-image").setAttribute("src", window.location.origin + "/rest/getImage/" + data.image);
    document.getElementById("ot-both-heading").innerHTML = data.heading;

}

function sendRequest(functionToCall, url, type, body, headers){
    let request = {
        method: type,
        headers: headers,
        body: body
    };
    fetch(url, request)
        .then(response => { return response.json() })
        .then(data => {
            if(functionToCall != null) {
                functionToCall(data);
            }
        });
}