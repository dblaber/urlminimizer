<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="Description" content="Simple, free fast open source free url shortener. Make a large url smaller without the hastle. ">
<meta name="Keywords" content="url minimizer,ne8.org,url shortener, open source,tinyurl,url shortening service">
<link rel="stylesheet" href="https://unpkg.com/blaze">
<script src="https://code.jquery.com/jquery-3.1.1.min.js"
	integrity="sha256-hVVnYaiADRTO2PzUGmuLJr8BLUSjGIZsDYGmIJLv2b8="
	crossorigin="anonymous" type="text/javascript"></script>
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
<script type="text/javascript">
	function sendBigUrl(url) {
		$.getJSON("./AjaxMinimize", {
			url : url
		}).done(function(json) {
			if(json.minifiedUrl == null || json.minifiedUrl == "")
			{
				return;
			}
			
			console.log("JSON Data: " + json.minifiedUrl);
			$('#minifiedUrlArea').html(json.minifiedUrl);

		}).fail(function(jqxhr, textStatus, error) {
			var err = textStatus + ", " + error;
			console.log("Request Failed: " + err);
		});
	}
	
	 $('#urlbox').on('keypress', function (e) {
         if(e.which === 13){
        	 sendBigUrl($('#urlbox').val())
         }
   });
</script>
<style type="text/css">
body {
	background-color: #f7f7f7;
}

main {
	height: 480px;
}
</style>

<title>URL Minimizer</title>

</head>
<body class="c-text">

	<main class="u-center-block">
	<div class="u-center-block__content">
		<h1 class='c-heading'>ne8.org URL Minimizer</h1>
		<p1>Short, simple, <a href="https://github.com/dblaber/urlminimizer"> Open
			Source! </a></p1>
		<form action="#">
			<div class="o-form-element">
				<div class="c-input-group c-input-group--stacked">
					<div class="o-field">
						<input class="c-field" id="urlbox" placeholder="URL to minimize" onpress>
					</div>
				</div>
				<div class="o-form-element">
					<button class="c-button c-button--brand c-button--block"
						onclick="sendBigUrl($('#urlbox').val())"> Minimize </button>
				</div>
			</div>
		</form>
		<h2 class="c-heading" id="minifiedUrlArea"></h2>
	</div>
	<div></div>
	</main>
	<main class="u-center-block">
	<div class="u-center-block__content">
	<p>Copyright 2016 Darren Blaber.</p><p> Abuse Contact: abuse@ne8.org</p>
	</div>
	</main>
</body>
</html>