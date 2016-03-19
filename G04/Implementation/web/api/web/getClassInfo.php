<?php
	include_once("../config.php");

	$query="select expert_name,expert_mobilephone,b.subject_name,c.sub_type_name,class_no,Date from s_expert a,s_subject b,s_sub_type c,s_test_school_subject_class d,s_class e where a.subject_id=b.subject_id and b.sub_type_id=c.sub_type_id and d.school_id=20 and d.subject_id=a.subject_id and e.class_id=d.class_id";
	$sql=$c->query($query);
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	echo json_encode($group,JSON_UNESCAPED_UNICODE);