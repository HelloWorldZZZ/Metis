<?php
	include_once("../config.php");
	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME);
	$c->query("set names UTF8");
	$query="select a.expert_name,b.test_class_no,c.start_time,a.expert_mobilephone,d.test_subject_name from d_expert a,s_test_class b,d_test_arrangement c,s_test_subject d where c.expert_id=a.expert_id and b.test_class_id=c.test_class_id and d.test_subject_id=c.test_subject_id";
	$sql=$c->query($query);
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	echo json_encode($group,JSON_UNESCAPED_UNICODE);