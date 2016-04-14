<?php
	include_once("../config.php");

	$query="select a.test_num,b.student_name,f.sub_type_name,c.class_no,e.test_temp_id from d_enroll a,s_student b,s_class c,t_test_school_retest d,d_mark e,s_sub_type f,s_test h where a.student_id=b.student_id and c.class_id=d.retest_classroom_id and f.sub_type_id=h.sub_type_id and a.test_id=h.test_id and e.enroll_num=a.enroll_num group by e.enroll_num order by f.sub_type_id,test_temp_id";
	$sql=$c->query($query);
	while($result=mysqli_fetch_object($sql)){
		$group[]=$result;
	}

	echo json_encode($group,JSON_UNESCAPED_UNICODE);