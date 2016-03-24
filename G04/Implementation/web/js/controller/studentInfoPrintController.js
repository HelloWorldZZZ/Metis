document.onreadystatechange = doSomething;

function doSomething() {
	if (document.readyState == "complete") //DOM树已经载入
		window.print();

}

$(document).ready(function() {
	$("section").append("<table  class='table table-bordered table-striped'></table>"); 
	$("table").append(window.localStorage.html);
	//$("table").attr("id", "");
});


//onreadystatechange在jquery ready之后运行
//$(".print-body").load("classInfo.html  .table");
//setTimeout("window.print()",100);