<?php
	include_once("../config.php");
	$test_list=json_decode(@$_POST["test_list"]);
	$sql=$c->query("update d_save_status set is_complete=0 where save_type_name='mark'");
	$status=1;
	foreach($test_list as $test){
		$subject_id=$test->subject_id;
		$create_time=$test->create_time;
		foreach($test->markList as $student){
			$impression_mark=$student->impression_mark;
			$mark=$student->mark;
			$enroll_num=$student->enroll_num;
			$sql=$c->query("update d_mark set impression_mark=$impression_mark,mark=$mark,createTime='$create_time' where enroll_num='$enroll_num' and subject_id='$subject_id'");
			if(!$sql)
				$status=0;
		}
	}
	$sql=$c->query("update d_save_status set is_complete='$status' where save_type_name='mark'");
	$obj["status"]=$status;
	ob_end_clean();
	echo json_encode($obj);
