<?php
	include_once("../config.php");
	header("Content-type: text/html; charset=utf-8"); 
	$c->query("update d_save_status set is_complete=0 where save_type_name='dataSync'");
	$sql=$c->query("select * from d_mark where mark is not null");
	if($result=mysqli_fetch_array($sql)){
		echo "考试已经开始，请不要再同步数据";
		exit();
	}
	$c->query("delete from s_student,s_class,s_test,s_test_school_subject_class,e_enroll,s_test_school_retest");
	$c->query("delete from s_class");
	$c->query("delete from s_test");
	$c->query("delete from s_test_school_subject_class");
	$c->query("delete from e_enroll");
	$c->query("delete from s_test_school_retest");
	$c->query("delete from d_mark");
	$status=1;
	$cu = curl_init();
	$root_url="http://192.168.1.112:8080/Clemson";
	$student_url="/school/20/ready/student";
	$class_url="/school/20/ready/classroom";
	$test_url="/school/20/ready/test";
	$tssc_url="/school/20/ready/testSchoolSubjectClass";
	$enroll_url="/school/20/ready/enroll";
	$retest_url="/school/20/ready/testSchoolRetest";
	// $data="name=zlx&password=123456";
	$URL=$root_url.$student_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	curl_setopt($cu, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($cu, CURLOPT_SSL_VERIFYPEER, false);    //SSL 报错时使用
    curl_setopt($cu, CURLOPT_SSL_VERIFYHOST, false);    //SSL 报错时使用
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	//student
	$response=json_decode($response_json,true);
	foreach($response as $item){
		$str="('".implode("','",$item)."')";  
		$sql=$c->query("insert into s_student values$str");
	}
	//class
	$URL=$root_url.$class_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	$response=json_decode($response_json,true);
	foreach($response as $item){
		array_pop($item);
		$str="('".implode("','",$item)."')";  
		$sql=$c->query("insert into s_class values$str");
	}
	//test
	$URL=$root_url.$test_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	$response=json_decode($response_json,true);
	foreach($response as $item){
		array_pop($item);
		array_pop($item);
		array_pop($item);
		$str="('".implode("','",$item)."')";  
		$sql=$c->query("insert into s_test values$str");
	}
	//tssc
	$URL=$root_url.$tssc_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	$response=json_decode($response_json,true);
	foreach($response as $item){
		array_splice($item,1,1);
		array_splice($item,2,1);
		array_splice($item,3,1);
		array_splice($item,4,1);
		$str="('".implode("','",$item)."')";  
		$sql=$c->query("insert into s_test_school_subject_class values$str");
	}	
	//enroll
	$URL=$root_url.$enroll_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	$response=json_decode($response_json,true);
	foreach($response as $item){
		array_splice($item,7,26);
		$str="('".implode("','",$item)."')";
		$sql=$c->query("insert into d_enroll values$str");
		$sql=$c->query("select b.enroll_num,d.subject_id,c.expert_id from s_test a,d_enroll b,s_expert c,s_subject d where b.enroll_num=$item[enrollNum] and a.test_id=b.test_id and c.subject_id=d.subject_id and d.sub_type_id=a.sub_type_id");
		while($result=mysqli_fetch_array($sql)){
			$c->query("insert into d_mark values($result[enroll_num],$result[subject_id],$result[expert_id],null,null,null,null,null,null,null)");
		}
		$sub_type_id=$result["sub_type_id"];
		
	}
	//retest
	$URL=$root_url.$retest_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	$response=json_decode($response_json,true);
	foreach($response as $item){
		array_splice($item,7,26);
		$str="('".implode("','",$item)."')";  
		$sql=$c->query("insert into s_test_school_retest values$str");
	}
	$c->query("update d_save_status set is_complete='$status' where save_type_name='dataSync'");
	curl_close($cu);
	echo "数据同步成功！";
