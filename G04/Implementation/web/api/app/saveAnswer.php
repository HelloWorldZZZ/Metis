<?php
	include_once("../config.php");
	//echo $_POST["answer"];
	$answer=json_decode(@$_POST["answer"],true);
	$status=0;
	if(!$answer){
		$answer["enroll_num"]=1517;
		$answer["en_answer"]="a";
		$answer["pro_answer"]="b";
		$answer["peo_answer"]="c";
	}
	$sql=$c->query("update d_answer set en_answer='$answer[en_answer]',pro_answer='$answer[pro_answer]',peo_answer='$answer[peo_answer]' where enroll_num='$answer[enroll_num]'");
	if($sql)
		$status=1;
	$obj["status"]=$status;
	echo json_encode($obj);
