$.get("api/web/getRetestStudentList.php", function(data) {

	processData(data, true);
	window.localStorage.html = $(".table").html();
	//动态生成下拉框
});



$(".btn-success").click(function() {
	$.post("api/web/getStudentList.php", {
			task: "distribute"
		},
		function(data) {
			$.get("api/web/getRetestStudentList.php", function(data) {

	processData(data, true);
	//动态生成下拉框
});
		});
});


function processData(data, flag) {
	var jsonArray = eval(data);
	var item;
	var t = $('table').dataTable();
	t.fnClearTable();

	for (item in jsonArray) {
		var student_list = jsonArray[item].student_list;
		var in_item;
			t.fnAddData([
				"大连市第二十四中学",
				jsonArray[item].class_no,
				jsonArray[item].sub_type_name,
				jsonArray[item].student_name,
				jsonArray[item].test_num,
				jsonArray[item].test_temp_id
			]);
	}
}
