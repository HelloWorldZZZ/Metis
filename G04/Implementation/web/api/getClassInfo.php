<?php
	include_once("config.php");
	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME);
	$c->query("set names UTF8");
	$query="select a.real_name,a.test_subject_name,b.expert_name,d.test_class_no from d_enroll_basic_info as a,d_expert as b,d_test_arrangement as c,s_test_class as d where a.enroll_num=c.enroll_num and c.expert_id=b.expert_id and d.text_class_id=c.text_class_id";
	$query="select a.real_name,a.test_subject_name,b.expert_name,d.test_class_no from d_enroll_basic_info a,d_expert b,d_test_arrangement c,s_test_class d where a.enroll_num=c.enroll_num and c.expert_id=b.expert_id and d.test_class_id=c.test_class_id";
	$sql=$c->query($query);
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	echo json_encode($group,JSON_UNESCAPED_UNICODE);