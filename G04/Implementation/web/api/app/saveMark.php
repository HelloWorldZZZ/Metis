<?php
	include_once("../config.php");
	$test_list=json_decode(@$_POST["test_list"]);
	$sql=$c->query("update d_save_status set is_complete=0 where save_type_name='mark'");
	$status=1;
	foreach($test_list as $test){
		$subject_id=$test->subject_id;
		$create_time=$test->create_time;
		foreach($test->markList as $student){
			$mark=$student->mark;
			$enroll_num=$student->enroll_num;
			$sql=$c->query("update d_mark set mark=$mark,createTime='$create_time' where enroll_num='$enroll_num' and subject_id='$subject_id'");
			if(!$sql)
				$status=0;
		}
	}
	$sql=$c->query("update d_save_status set is_complete='$status' where save_type_name='mark'");
	$obj["status"]=$status;
	echo json_encode($obj);
