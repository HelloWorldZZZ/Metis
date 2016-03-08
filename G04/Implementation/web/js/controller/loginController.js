

$("button#login").click(function(){

   var name = $("input#userId").val();
   var password = $("input#password").val();
   $.post("demo_test_post.asp",
   {
    name:name,
    password:password
   },
   function(data,status){
    alert("Data: " + data + "\nStatus: " + status);
    window.href();
  });
});

