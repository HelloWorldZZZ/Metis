$.get("api/web/getRetestMark.php", function(data) {
  var jsonArray = eval(data);
  var item;
  var t = $('table').dataTable();

  for (item in jsonArray) {
    var mark_list = jsonArray[item].mark_list;
    var in_item;
	var canUpload=0;
    for (in_item in mark_list) {
	  if(mark_list[in_item].retest_en_mark)
		canUpload=1;
      t.fnAddData([
        "大连市第二十四中学",
        jsonArray[item].sub_type_name,
        mark_list[in_item].student_name,
        mark_list[in_item].retest_en_mark,
        mark_list[in_item].retest_pro_mark,
        mark_list[in_item].retest_peo_mark,
      ]);
    }
	if(canUpload==0)
	  $("#markUpload").attr("disabled","disabled");
  }
	$(document).ready(function(){
		$("#markUpload").click(function(){
			$("#markUpload").attr("disabled","disabled");
			$.get("api/web/uploadMark.php",function(data){
				$("#markUpload").removeAttr("disabled");
				alert(data);
			});
		});	
	});  
});
