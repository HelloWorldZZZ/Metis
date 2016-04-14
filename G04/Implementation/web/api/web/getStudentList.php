<?php
	include_once("../config.php");
	$task=@$_POST["task"];
	$sql1=$c->query("select * from s_test_school_subject_class where school_id=20");
	if($task=="distribute"){
		while($result=mysqli_fetch_object($sql1)){
			$result_array[]=$result;
			$subject_id=$result->subject_id;
			$query="select * from d_mark where subject_id=$subject_id";
			$sql=$c->query($query);
			$count=mysqli_num_rows($sql);
			$num=range(1,$count);
			shuffle($num);
			foreach($num as $i){
				$stu=mysqli_fetch_array($sql);
				$c->query("update d_mark set test_temp_id=$i where enroll_num=$stu[enroll_num] and subject_id=$subject_id");
			}
		}
	}else{
		while($result=mysqli_fetch_object($sql1))
			$result_array[]=$result;
	}
	$flag=0;
	$retest=1;
	foreach($result_array as $result){
		$class_id=$result->class_id;
		$query="select b.test_id,b.class_id,e.class_no,c.subject_id,c.subject_name,d.sub_type_name,b.Date from s_test_school_subject_class b,s_subject c,s_sub_type d,s_class e where e.class_id=$class_id and c.subject_id=b.subject_id and c.sub_type_id=d.sub_type_id and e.class_id=b.class_id";
		$sql2=$c->query($query);
		$result=@mysqli_fetch_object($sql2);
		@$test_id=$result->test_id;
		@$subject_id=$result->subject_id;
		$obj=$result;
		//echo json_encode($obj,JSON_UNESCAPED_UNICODE);
		$query="select a.enroll_num,a.test_num,c.test_temp_id,b.student_name,b.student_sex,b.student_nation,b.student_birthday,b.student_school,c.test_temp_id,c.mark from d_enroll a,s_student b,d_mark c where a.test_id=$test_id and a.student_id=b.student_id and c.enroll_num=a.enroll_num and c.subject_id=$subject_id order by c.test_temp_id";
		$sql3=$c->query($query);
		while($result=mysqli_fetch_object($sql3)){
			$obj->student_list[]=$result;
		}
		$group[]=$obj;
	}
	echo json_encode($group,JSON_UNESCAPED_UNICODE);