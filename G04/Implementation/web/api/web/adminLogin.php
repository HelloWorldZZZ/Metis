<?php
	include_once("../config.php");

	
	$username=@$_POST["username"];
	$password=@$_POST["password"];

	$c->query("set names UTF8");
	$query="select * from s_admin where admin_name='$username' and admin_password='$password'";
	$result=@mysqli_fetch_object($c->query($query));
	if($result)
		echo 1;
	else
		echo 0;	
	
	