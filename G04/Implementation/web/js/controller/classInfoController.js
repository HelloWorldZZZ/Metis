// if (window.localStorage) {
// 	alert("浏览支持localStorage")
// } else {
// 	alert("浏览暂不支持localStorage")
// }

$.get("api/web/getClassInfo.php", function(data) {
	var jsonArray = eval(data);
	var item;
	for (item in jsonArray) {
		//将科目加入下拉列表中
		$(".dropdown-menu").append("<li><a href='#'>" + jsonArray[item].subject_name + "</a></li>");
		$("tbody").append("<tr><td>" + jsonArray[item].class_no + "</td><td>" + jsonArray[item].subject_name + "</td><td>" + jsonArray[item].Date + "</td><td>" + jsonArray[item].expert_name + "</td><td>" + jsonArray[item].expert_mobilephone + "</td></tr>");
	}
	$(".dropdown-menu").append("<li class='divider'></li>");
	$(".dropdown-menu").append("<li><a href='#'>" + "所有" + "</a></li>");

	window.localStorage.html = $("#table").html();

	$(document).ready(function() {
		$(".btn-group .dropdown-menu li a").click(function() {
			$("tbody").empty();
			for (item in jsonArray) {
				if($(this).html() == jsonArray[item].subject_name || $(this).html() == "所有")
				$("tbody").append("<tr><td>" + jsonArray[item].class_no + "</td><td>" + jsonArray[item].subject_name + "</td><td>" + jsonArray[item].Date + "</td><td>" + jsonArray[item].expert_name + "</td><td>" + jsonArray[item].expert_mobilephone + "</td></tr>");
			}
			window.localStorage.html = $("#table").html();
		});


	});

});

