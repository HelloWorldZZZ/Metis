<?php
	include_once("../config.php");
	$answer=json_decode(@$_POST["answer"],true);
	$status=0;
	
	$sql=$c->query("insert into d_answer values(null,$answer[enroll_num],$answer[en_answer],$answer[pro_answer],$answer[peo_answer])");
	if($sql)
		$status=1;
	$obj["status"]=$status;
	echo json_encode($obj);
