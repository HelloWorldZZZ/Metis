<?php
	include_once("../config.php");
	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME);
	$c->query("set names UTF8");
	$query="select a.impression_mark,a.final_mark,c.test_class_no,d.test_subject_name,e.expert_name,f.real_name from d_specific_mark_info a,d_test_arrangement b,s_test_class c,s_test_subject d,d_expert e,d_enroll_basic_info f where a.test_id=b.test_id and b.test_class_id=c.test_class_id and b.expert_id=e.expert_id and d.test_subject_id=b.test_subject_id and f.enroll_num=b.enroll_num";
	$sql=$c->query($query);
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	echo json_encode($group,JSON_UNESCAPED_UNICODE);