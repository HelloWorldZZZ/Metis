<?php
	include_once("../config.php");
	$enroll_num=@$_POST["enroll_num"];
	if(!$enroll_num)
		$enroll_num=1517;
	$status=0;
	$sql=$c->query("select * from d_answer where enroll_num=$enroll_num");
	$obj=mysqli_fetch_object($sql);
	if(!$obj){
		@$obj->status=0;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		exit();
	}
	$sql=$c->query("select * from s_question where question_id=$obj->en_question_id");
	$que1=mysqli_fetch_array($sql);	
	$sql=$c->query("select * from s_question where question_id=$obj->pro_question_id");
	$que2=mysqli_fetch_array($sql);	
	$sql=$c->query("select * from s_question where question_id=$obj->peo_question_id");
	$que3=mysqli_fetch_array($sql);
	$obj->en_question=$que1["question_content"];
	$obj->pro_question=$que2["question_content"];
	$obj->peo_question=$que3["question_content"];
	$obj->status=1;
	echo json_encode($obj,JSON_UNESCAPED_UNICODE);
