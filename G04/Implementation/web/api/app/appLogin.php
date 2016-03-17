<?php
	include_once("config.php");

	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME,DB_PORT);
	$c->query("set names UTF8");

	$username=@$_POST["username"];
	$password=@$_POST["password"];
	$class=@$_POST["class_no"];
	$role=@$_POST["role"];
	if($role==1){
		$query="select admin_account_name from s_admin_account where admin_account_name='$username' and admin_account_password='$password'";
		$result=@mysqli_fetch_object($c->query($query));
		@$result->role=1;	
		if(@$result->admin_account_name)
			@$result->isLogin=1;
		else
			@$result->isLogin=0;
			echo json_encode($result,JSON_UNESCAPED_UNICODE);
		exit();
	}
	$query="select * from d_expert where expert_username='$username' and expert_password='$password'";
	$result=mysqli_fetch_object($c->query($query));
	if($result){
		$query="select b.test_class_no,c.test_subject_name,c.type_name,d.expert_name,a.start_time,a.end_time from d_test_arrangement a,s_test_class b,s_test_subject c,d_expert d where b.test_class_id=(select test_class_id from s_test_class where test_class_no=$class) and c.test_subject_id=a.test_subject_id and d.expert_username='$username'";
		$result=@mysqli_fetch_object($c->query($query));
		@$result->role=2;
		@$result->isLogin=1;
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
	}else{
		@$result->role=2;
		@$result->isLogin=0;
		echo json_encode($result,JSON_UNESCAPED_UNICODE);
	}