
document.onreadystatechange = doSomething;

function doSomething() {
	if (document.readyState == "complete")//DOM树已经载入
		window.print();

}

$(document).ready(function() {
	$("section").append(window.localStorage.html);
	$("table").removeClass('table-invoice');
});


//onreadystatechange在jquery ready之后运行
//$(".print-body").load("classInfo.html  .table");
//setTimeout("window.print()",100);