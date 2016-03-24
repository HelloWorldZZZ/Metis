$.get("api/web/getStudentList.php", function(data) {
  processData(data);
});



$(".btn-success").click(function() {
  $.post("api/web/getStudentList.php", {
      task: "distribute"
    },
    function(data) {
      processData(data);
    });
});


function processData(data) {
  var jsonArray = eval(data);
  var item;
  var t = $('table').dataTable();

  for (item in jsonArray) {
    var student_list = jsonArray[item].student_list;
    var in_item;
    $(".dropdown-menu").append("<li><a href='#'>" + jsonArray[item].class_no + "</a></li>");

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

  $(".dropdown-menu").append("<li class='divider'></li>");
  $(".dropdown-menu").append("<li><a href='#'>" + "所有" + "</a></li>");
  window.localStorage.html = $(".table").html();


  $(document).ready(function() {

    $(".dropdown-menu li a").click(function() {
      t.fnClearTable();
      for (item in jsonArray) {
        if ($(this).html() == jsonArray[item].class_no || $(this).html() == "所有") {
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
              " "
            ]);
          }
        }
      }
        window.localStorage.html = $(".table").html();

    });
  });


}