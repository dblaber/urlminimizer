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
		$.getJSON("RequestApiKey", {
			url : url
		}).done(function(json) {
			if(json == null || json.minifiedUrl == null || json.minifiedUrl == "")
			{
				return;
			}
			
			console.log("JSON Data: " + json.minifiedUrl);
			$('#minifiedUrlArea').html(json.minifiedUrl);

		}).fail(function(jqxhr, textStatus, error) {
			var err = textStatus + ", " + error;
			console.log("Request Failed: " + err);
		});
		return true;
	}
	$( document ).ready(function() {
		$('#urlbox').on('keypress', function (e) {
			 //e.preventDefault();
	         if(e.which === 13){
	        	 sendBigUrl($('#urlbox').val())
	         }
	});
	 
         
     $("#minimize_form").on("submit", function(e){
        	// e.preventDefault();
        	alert(e)
        	})
   });
</script>
<style type="text/css">
body {
	background-color: #f7f7f7;
}

#main {
	height: 480px;
}
#footer{
	height: 480px;
	width: 500px;
}
</style>

<title>URL Minimizer</title>

</head>
<body class="c-text">

	<div id="main" class="u-center-block">
	<div class="u-center-block__content">
		<h1 class='c-heading'>ne8.org URL Minimizer</h1>
		<p>Request API Key. We reserve the right to throttle and add additional terms to the API, and also terminate it for any reason. <b>The API is currently BETA.</b></p>
		<p>API Key and documentation will be emailed to email address provided. Please use this api only for lawful purposes, do not 
		use it for spamming. We reserve right to terminate api at any point</p>
			<div class="o-form-element">
				<div class="c-input-group c-input-group--stacked">
					<div class="o-field">
						<input class="c-field" id="urlbox" placeholder="Email Address">
					</div>
				</div>
				<div class="o-form-element">
					<button class="c-button c-button--brand c-button--block"
						onclick="sendBigUrl($('#urlbox').val())"> Request API Key </button>
				</div>
			</div>

		<h2 class="c-heading" id="minifiedUrlArea"></h2>
	</div>
	<div></div>
	</div>
	<div id="footer" class="u-center-block__content u-center-block__content--horizontal">
	<div  class="u-center-block__content">
	<p>Copyright 2016 Darren Blaber.</p><p> Abuse Contact: abuse@ne8.org</p>
	</div>
	</div>

</body>
</html>