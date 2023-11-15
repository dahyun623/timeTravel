<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>시간 여행</title>
<script src="https://code.jquery.com/jquery-3.6.0.min.js"
	charset="UTF-8"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.min.js"
	charset="UTF-8"></script>
<script type="text/javascript" src="./js/main.js"></script>
<link href="./css/main.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet"
	href="https://fonts.googleapis.com/css?family=Jua">
</head>
<body>
	<div class="box date">
		<div class="icon">What happened in...</div>
		<form action="./timetravel.do" method="POST">
			<div class="input">
				<input class="month" id="monthPicker" name="month" min="1" max="12"
					type="number" value="" onkeydown="return false;" /> <input
					class="day" id="dayPicker" name="day" min="1" max="31"
					type="number" value="" onkeydown="return false;" />
			</div>
			<div class="submit">
				<input type="submit" value="Time Travel" />
			</div>
		</form>
		<div class="randomDate">
			<input type="button" value="I feel lucky" />
		</div>
	</div>
	<div class='preloading' hidden>
		<div class='body'>
			<span> <span></span> <span></span> <span></span> <span></span>
			</span>
			<div class='base'>
				<span></span>
				<div class='face'></div>
			</div>
		</div>
		<div class='longfazers'>
			<span></span> <span></span> <span></span> <span></span>
		</div>
		<h1>Redirecting</h1>
	</div>
</body>
</html>