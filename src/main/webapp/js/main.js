var today = new Date();
var currentMonth = today.getMonth() + 1;
var currentDay = today.getDate();

window.onload = function() {
    displayTodayDate();
    focusOnMouseover();
    setMaxDay();
}

$(function() {
    $("#monthPicker, #dayPicker").on('input', function() {
        $(".month").text($("#monthPicker").val());
        $(".day").text($("#dayPicker").val());
        setMaxDay();
    });

    $("form").on("submit", function(e) {
	    const preload = document.querySelector(".preloading");
	    if (preload != null) {
	        preload.removeAttribute('hidden');  // 프리로딩 화면 보이게
	    }
	});
});

function displayTodayDate() {
    document.getElementById('monthPicker').value = currentMonth;
    document.getElementById('dayPicker').value = currentDay;
}

function focusOnMouseover() {
    var inputs = document.querySelectorAll('input[type="number"]');
    inputs.forEach(function(input) {
        input.addEventListener('mouseover', function() {
            this.focus();
        });
    });
}

function setMaxDay() {
    var month = parseInt(document.getElementById('monthPicker').value);
    var maxDay = new Date(2023, month, 0).getDate();  // 년도를 현재 년도로 고정

    document.getElementById('dayPicker').max = maxDay;
    if(document.getElementById('dayPicker').value > maxDay){
        document.getElementById('dayPicker').value = maxDay;
    }
}
