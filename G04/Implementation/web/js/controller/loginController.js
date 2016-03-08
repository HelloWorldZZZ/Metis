
$(document).ready(function(){
	$("button").click(function(){
		alert();
	   var name = $("input#userId").val();
	   var password = $("input#password").val();
	   $.ajax({
		   type:'POST',
		   url:'api/adminLogin.php',
		   data:{username:name,password:password},
		   success:function(data){
			   alert(data);
		   },
		   error:function(status){
			   alert(JSON.stringify(status))
		   }
	   })
	});
});
