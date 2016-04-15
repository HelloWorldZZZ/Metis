<?php
	include_once("../config.php");
	session_start();
	$username=@$_POST["username"];//$_SESSION["username"]="admin";
	$password=@$_POST["password"];//$password="123456";
	$login_status=@$_POST["login_status"];//$login_status=1;
	$c->query("set names UTF8");
	if(!$login_status)
		$query="select * from s_admin where admin_name='$username' and admin_password='$password'";
	else
		$query="select * from s_admin where admin_name='$_SESSION[username]' and admin_password='$password'";
	$result=@mysqli_fetch_object($c->query($query));
	if($result){
		if($username)
			$_SESSION["username"]=$username;
		echo 1;
	}
	else
		echo 0;