$.get("api/web/getStudentList.php", function(data) {
  processData(data, true);
  //动态生成下拉框
  $(".dropdown-menu").append("<li class='divider'></li>");
  $(".dropdown-menu").append("<li><a href='#'>" + "所有" + "</a></li>");
});



$(".btn-success").click(function() {
  $.post("api/web/getStudentList.php", {
      task: "distribute"
    },
    function(data) {
      processData(data, false);
    });
});


function processData(data, flag) {
  var jsonArray = eval(data);
  var item;
  var t = $('table').dataTable();
  t.fnClearTable();

  for (item in jsonArray) {

    if ($("#selector").html() == (jsonArray[item].class_no + "考场") || $("#selector").html() == "选择考场") {
      var student_list = jsonArray[item].student_list;
      var in_item;
      if (flag)
        $(".dropdown-menu").append("<li><a href='#'>" + jsonArray[item].class_no + "考场" + "</a></li>");

      for (in_item in student_list) {
        t.fnAddData([
          "大连市第二十四中学",
          jsonArray[item].class_no,
          jsonArray[item].sub_type_name,
          jsonArray[item].subject_name,
          student_list[in_item].student_name,
          student_list[in_item].test_num,
          student_list[in_item].test_temp_id
        ]);
      }
    }
  }

  window.localStorage.html = $(".table").html();


  $(document).ready(function() {
    $(".dropdown-menu li a").click(function() {
      $("#selector").html($(this).html());
      t.fnClearTable();
      for (item in jsonArray) {
        if ($(this).html() == (jsonArray[item].class_no + "考场") || $(this).html() == "所有") {
          var student_list = jsonArray[item].student_list;
          var in_item;
          for (in_item in student_list) {
            t.fnAddData([
              "大连市第二十四中学",
              jsonArray[item].class_no,
              jsonArray[item].sub_type_name,
              jsonArray[item].subject_name,
              student_list[in_item].student_name,
              student_list[in_item].test_num,
              student_list[in_item].test_temp_id
            ]);
          }
        }
      }
      window.localStorage.html = $(".table").html();

    });
  });


}