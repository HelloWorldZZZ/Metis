<?php
	include_once("../config.php");
	$enroll_num=@$_POST["enroll_num"];
	if(!$enroll_num)
		$enroll_num=1517;
	$status=0;
	$sql=$c->query("select * from d_answer where enroll_num=$enroll_num");
	$obj=mysqli_fetch_object($sql);
	echo json_encode($obj,JSON_UNESCAPED_UNICODE);
