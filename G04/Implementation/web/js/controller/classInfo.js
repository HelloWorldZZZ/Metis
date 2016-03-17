// if (window.localStorage) {
// 	alert("浏览支持localStorage")
// } else {
// 	alert("浏览暂不支持localStorage")
// }
$.get("api/web/getExpertInfo.php", function(data) {
	var jsonArray = eval(data);
	var item;
	for (item in jsonArray) {
		$("tbody").append("<tr><td>" + jsonArray[item].test_class_no + "</td><td>" + jsonArray[item].test_subject_name + "</td><td>" + jsonArray[item].start_time + "</td><td>" + jsonArray[item].expert_name + "</td><td>" + jsonArray[item].expert_mobilephone + "</td></tr>");
	}
	window.localStorage.html = $("#table").html() ;
});

