<?php
	include_once("../config.php");

	$username=@$_POST["username"];$username="shy";
	$password=@$_POST["password"];$password="123456";
	$role=@$_POST["role"];
	if($role==1){
		$query="select admin_name from s_admin where admin_name='$username' and admin_password='$password'";
		$result=@mysqli_fetch_object($c->query($query));
		@$result->role=1;	
		if(@$result->admin_account_name)
			@$result->isLogin=1;
		else
			@$result->isLogin=0;
			echo json_encode($result,JSON_UNESCAPED_UNICODE);
		exit();
	}
	$query="select * from s_expert where expert_username='$username' and expert_password='$password'";
	$result=mysqli_fetch_object($c->query($query));
	if($result){
		$expert_id=$result->expert_id;
		$query="select b.test_id,b.class_id,e.class_no,c.subject_id,c.subject_name,d.sub_type_name,a.expert_id,a.expert_name,b.Date from s_expert a,s_test_school_subject_class b,s_subject c,s_sub_type d,s_class e where a.expert_id=$expert_id and a.subject_id=c.subject_id and c.subject_id=b.subject_id and c.sub_type_id=d.sub_type_id and e.class_id=b.class_id";
		$result=@mysqli_fetch_object($c->query($query));
		$test_id=$result->test_id;
		$obj=$result;
		@$obj->role=2;
		@$obj->isLogin=1;
		//echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		$query="select a.enroll_num,a.test_num,c.test_temp_id,b.student_name,b.student_sex,b.student_nation,b.student_birthday,b.student_school,c.test_temp_id from d_enroll a,s_student b,d_mark c where a.test_id=$test_id and a.student_id=b.student_id and c.expert_id=$expert_id and c.enroll_num=a.enroll_num order by c.test_temp_id";
		$sql=$c->query($query);
		while($result=mysqli_fetch_object($sql))
			$obj->student_list[]=$result;
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
	}else{
		@$result->role=2;
		@$result->isLogin=0;
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
	}