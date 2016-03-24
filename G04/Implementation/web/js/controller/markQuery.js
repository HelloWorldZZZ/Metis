$.get("api/web/getMark.php", function(data) {
  var jsonArray = eval(data);
  var item;
  var t = $('table').dataTable();

  for (item in jsonArray) {
    //$("tbody").append("<tr><td>" + "大连市第二十四中学" + "</td><td>" +  "音乐学" + "</td><td>" + jsonArray[item].test_subject_name + "</td><td>" + jsonArray[item].real_name + "</td><td>" + jsonArray[item].final_mark + "</td></tr>");
    var mark_list = jsonArray[item].mark_list;
    var in_item;
    for (in_item in mark_list) {

      t.fnAddData([
        "大连市第二十四中学",
        jsonArray[item].sub_type_name,
        jsonArray[item].subject_name,
        mark_list[in_item].student_name,
        mark_list[in_item].mark,
      ]);
    }
  }
});
$(document).ready(function(){
	$("#markUpload").click(function(){
		$("#markUpload").attr("disabled","disabled");
		$.get("api/web/uploadMark.php",function(data){
			$("#markUpload").removeAttr("disabled");
			alert(data);
		});
	});	
});