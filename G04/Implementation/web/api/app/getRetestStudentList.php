<?php
	include_once("../config.php");
	$expert_username=@$_POST["expert_username"];
	if(!$expert_username)
		$expert_username="jnk";
	
	$query="select * from s_expert where expert_username='$expert_username'";
	$result=mysqli_fetch_object($c->query($query));
	if($result){
		$expert_id=$result->expert_id;
		$sql=$c->query("select a.sub_type_id from s_subject a,s_expert b where b.expert_id='$expert_id' and a.subject_id=b.subject_id");
		$sub=mysqli_fetch_array($sql);
		$sub_type_id=$sub["sub_type_id"];
		$sql=$c->query("select * from s_subject where sub_type_id=$sub_type_id");
		while($subject=mysqli_fetch_array($sql)){
			$sql2=$c->query("select * from d_mark where subject_id='$subject[subject_id]' and mark is null");
			if(mysqli_fetch_row($sql2)){
				@$error->status=0;
				echo json_encode($error,JSON_UNESCAPED_UNICODE);
				exit();
			}
		}
		$query="select b.test_id,b.class_id,e.class_no,d.sub_type_id,d.sub_type_name,a.expert_id,a.expert_name,b.start_time,b.end_time from s_expert a,s_test b,s_subject c,s_sub_type d,s_class e where a.expert_id=$expert_id and a.subject_id=c.subject_id and c.sub_type_id=d.sub_type_id and e.class_id=b.class_id";
		$result=@mysqli_fetch_object($c->query($query));
		$test_id=$result->test_id;
		$obj=$result;
		$obj->status=1;
		$query="select a.enroll_num,a.test_num,c.test_temp_id,b.student_name from d_enroll a,s_student b,d_mark c where a.test_id=$test_id and a.student_id=b.student_id and c.expert_id=$expert_id and c.enroll_num=a.enroll_num order by c.test_temp_id";
		$sql=$c->query($query);
		$q=$c->query("select * from s_question where sub_type_id=$sub_type_id or sub_type_id is null order by question_type_id");
		while($question=mysqli_fetch_object($q)){
			if($question->question_type_id==1)
				$question_array[1][]=$question;
			if($question->question_type_id==2)
				$question_array[2][]=$question;
			if($question->question_type_id==3)
				$question_array[3][]=$question;
		}
		while($result=mysqli_fetch_object($sql)){
			$a1=array_rand($question_array[1],1);
			$result->question[]=$question_array[1][$a1];
			$a2=array_rand($question_array[2],1);
			$result->question[]=$question_array[2][$a2];
			$a3=array_rand($question_array[3],1);
			$result->question[]=$question_array[3][$a3];
			$obj->student_list[]=$result;
			$q_id1= $question_array[1][$a1]->question_id;
			$q_id2= $question_array[2][$a2]->question_id;
			$q_id3= $question_array[3][$a3]->question_id;
			$c->query("delete from d_answer where enroll_num=$result->enroll_num");
			$c->query("insert into d_answer values(null,$result->enroll_num,$q_id1,null,$q_id2,null,$q_id3,null)");
		}
		echo json_encode($obj,JSON_UNESCAPED_UNICODE);
	}
	
