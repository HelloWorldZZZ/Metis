<?php
	include_once("../config.php");
	$c=new mysqli();
	
	$username=@$_POST["username"];
	$password=@$_POST["password"];
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME);
	$c->query("set names UTF8");
	$query="select * from metis_admin where username='$username' and password='$password'";
	$result=mysqli_fetch_object($c->query($query));
	if($result)
		echo 1;
	else
		echo 0;	
	
	