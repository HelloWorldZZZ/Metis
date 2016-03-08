$("button#login").click(function() {
  var username = $("input#userId").val();
  var password = $("input#password").val();
  $.post("api/adminLogin.php", {
      username: username,
      password: password
    },
    function(data, status) {
      alert(data);
      if (data == 1) {
        window.location.assign("index.html");
      } else {
        alert("用户名或密码不正确");
      }
    });
});