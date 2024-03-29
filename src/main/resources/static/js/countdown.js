var eventDate;
var countdownIntervalId = null;

window.onload = function() {
    receiveCountdownData();
    window.setInterval(receiveCountdownData, 5000);
}

function receiveCountdownData() {
    sendRequest(processCountdownData, window.location.origin + "/rest/countdownData", "GET", null, new Headers());
}

function processCountdownData(data) {

    if(data["@type"] === "Countdown") {
        document.getElementById("ot-countdown-countdown").hidden = false;
        document.getElementById("ot-message-text").hidden = true;

        eventDate = data.date;
        if(countdownIntervalId == null) {
	
				startCountdown();	
            
        }

    }else if(data["@type"] === "Message") {
        document.getElementById("ot-message-text").hidden = false;
        document.getElementById("ot-countdown-countdown").hidden = true;
        document.getElementById("ot-message-text").innerHTML = data.message;

        clearCountdown();
    }

    if(data.backgroundMode === "BLURREDIMAGE") {
        document.body.style.backgroundImage = "url('" + window.location.origin + "/rest/getImage/5.jpg')";
        document.body.style.backgroundSize = "cover";
    }else if(data.backgroundMode === "ONECOLOR") {
        document.body.style.background = data.color;
    }

    document.getElementById("ot-both-logo").setAttribute("src", window.location.origin + "/rest/getImage/" + data.image);
    document.getElementById("ot-both-heading").innerHTML = data.heading;

}

function startCountdown() {

    countdownIntervalId = window.setInterval(function () {

        var timeDiff={
            hours: 0,
            minutes: 0
        };

        try {
            timeDiff = calculateTimeDiff(eventDate);
        } catch (err) {
            console.error(err.message);
        }

        if (timeDiff.hours > 0) {
			//If event is more than one hour in future: show hours-div and inner hour count
			
			if(timeDiff.hours == 1) {
				//Use singular in ot-countdown-hours-label
				document.getElementById("ot-countdown-hours-label").innerHTML = " Stunde ";
			}else{
				//Use plural in ot-countdown-hours-label
				document.getElementById("ot-countdown-hours-label").innerHTML = " Stunden ";
			}
			
            document.getElementById("ot-countdown-hours").hidden = false;
            document.getElementById("ot-countdown-hours-value").innerHTML = timeDiff.hours;
        } else {
			//If event less than one hour in future: hide hours-div
            document.getElementById("ot-countdown-hours").hidden = true;
        }
        if (timeDiff.minutes > 0) {
            document.getElementById("ot-countdown-minutes").hidden = false;
            document.getElementById("ot-countdown-minutes-value").innerHTML = timeDiff.minutes;
        } else {
            document.getElementById("ot-countdown-minutes").hidden = true;
        }
    }, 1000);
}

function clearCountdown() {
    window.clearInterval(countdownIntervalId);
    countdownIntervalId = null;
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
        timeDiff.minutes = minutes;
        if(minutes > 60) {
            timeDiff.hours = Math.floor(minutes / 60);
            timeDiff.minutes = minutes - timeDiff.hours * 60;
        }

    }else{
        throw new Error("The specified date is in the past.");
    }

    return timeDiff;

}


function sendRequest(functionToCall, url, type, body, headers) {
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
                throw new Error("Error " + response.status + " occured");
            }
        })
        .then(data => {
            if (functionToCall != null) {
                functionToCall(data);
            }
        })
        .catch(function(err) {
            console.error(err);
        });
}