document.onkeydown = function(){
   if (event.keyCode == 13)
     $("button#login").click();
 }

 $("button#login").click(function() {
   var username = $("input#userId").val();
   var password = $("input#password").val();
   $.post("api/web/adminLogin.php", {
       username: username,
       password: password
     },
     function(data, status) {
       if (data == 1) {
         $.cookie('username',username);
         $.cookie('login_status', true);
         window.location.assign("index.html");
       } else {
         $(".alert").fadeIn();
         setTimeout(function() {
           $(".alert").fadeOut()
         }, 1000);
       }
     });
 });