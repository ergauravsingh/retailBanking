<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Required meta tags -->
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="/css/style.css">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-BmbxuPwQa2lc/FVzBcNJ7UAyJxM6wuqIj61tLrc4wSX0szH/Ev+nYRRuWlolflfl"
	crossorigin="anonymous">

<title>Retail Bank</title>
</head>

<body>

	<div class="container">

		<div class="row justify-content-center">

			<div class="col-md-6">

				<div class="box">


					<div class="row my-2" style="text-align: center;">
						<div class="alert alert-success" role="alert">
							<h3>Welcome to India's largest Retail Bank</h3>
						</div>
					</div>
					<div class="row">
						<div class="col-sm-6" style="text-align: center;">
							<div class="alert alert-info" role="alert">
								<h4>Customer</h4>
							</div>
							<div class="text-center">
								<img src="/images/u1.png" class="rounded mx-auto d-block"
									alt="usericon.jpg" style="height: 200px; width: 200px;">
							</div>
							<div class="row justify-content-center my-4">
								<form action="/customerlogin" method="get">
									<input type="submit" value="Customer Login">
								</form>
							</div>
						</div>

						<div class="col-sm-6">

							<div class="alert alert-warning d-flex justify-content-center"
								role="alert">
								<h4>Bank Staff</h4>
							</div>

							<div class="text-center">
								<img src="/images/e1.png" class="rounded mx-auto d-block"
									alt="employeeicon.jpg" style="height: 200px; width: 200px;">
							</div>
							<div class="row justify-content-center my-4">
								<!-- <form action="/employeelogin" method="get">
											<input type="submit" value="Employee Login">
										</form> -->
								<form action="/employeelogin" method="get">
									<input type="submit" value="Employee Login">
								</form>
							</div>
						</div>
					</div>
				</div>

			</div>
		</div>

	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-b5kHyXgcpbZJO/tY9Ul7kGkf1S0CWuKcCD38l8YkeH8z8QjE0GmW1gYU5S9FOnJ0"
		crossorigin="anonymous"></script>

</body>
</html>





















