/*{
    "@type": "Countdown",
    "operationType": "COUNTDOWN",
    "backgroundMode": "ONECOLOR",
    "image": "a.jpg",
    "heading": "Hallo",
    "color": "#c8d421",
    "date": "2022-05-10T09:18:00.000Z"
}*/

var eventDate;

window.onload = function() {
    receiveCountdownData();
    window.setInterval(receiveCountdownData, 5000);
    window.setInterval(function() {
        var timeDiff = calculateTimeDiff(eventDate);
        document.getElementById("ot-countdown-hours").innerHTML = timeDiff.hours;
        document.getElementById("ot-countdown-minutes").innerHTML = timeDiff.minutes;
    }, 1000);
}

function receiveCountdownData() {
    sendRequest(processCountdownData, window.location.origin + "/rest/countdownData", "GET", null, new Headers());
}

function processCountdownData(data) {

    if(data.operationType === "COUNTDOWN") {
        document.getElementById("ot-countdown-countdown").hidden = false;
        document.getElementById("ot-message-text").hidden = true;

        eventDate = data.date;

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

    document.getElementById("ot-both-logo").setAttribute("src", window.location.origin + "/rest/getImage/" + data.image);
    document.getElementById("ot-both-heading").innerHTML = data.heading;

}

function calculateTimeDiff(dateInFuture) {

    var currentDate = new Date();
    var dateInFuture = new Date(dateInFuture);

    var milliDiff = dateInFuture.getTime() - currentDate.getTime();

    var timeDiff={
        hours: 0,
        minutes: 0
    };

    if(milliDiff > 0) {

        var minutes = Math.floor(milliDiff / 1000 / 60);
        if(minutes > 60) {
            timeDiff.hours = Math.floor(minutes / 60);
            timeDiff.minutes = minutes - timeDiff.hours * 60;
        }

    }else{
        throw "The specified date is in the past.";
    }

    return timeDiff;

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