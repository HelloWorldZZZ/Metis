<?php
	include_once("../config.php");

	$query="select a.retest_en_mark,a.retest_pro_mark,a.retest_peo_mark,b.expert_name,c.subject_id,c.subject_name,e.student_name,f.sub_type_id,f.sub_type_name from d_mark a,s_expert b,s_subject c,d_enroll d,s_student e,s_sub_type f where a.enroll_num=d.enroll_num and d.student_id = e.student_id and c.subject_id=a.subject_id and a.expert_id=b.expert_id and c.sub_type_id=f.sub_type_id group by d.enroll_num order by f.sub_type_id,a.test_temp_id";
	$sql=$c->query($query);
	$subject_id=0;
	while($result=mysqli_fetch_object($sql)){
		if($result->subject_id!=$subject_id){
			if(@$obj!=null)
				$group[]=$obj;
			$obj=null;
			$subject_id=@$result->subject_id;
			@$obj->expert_name=$result->expert_name;
			@$obj->sub_type_id=$result->sub_type_id;
			@$obj->sub_type_name=$result->sub_type_name;
			@$obj->subject_id=$result->subject_id;
			@$obj->subject_name=$result->subject_name;
		}
		@$obj->mark_list[]=array(
							"student_name"=>$result->student_name,
							"retest_en_mark"=>$result->retest_en_mark,
							"retest_pro_mark"=>$result->retest_pro_mark,
							"retest_peo_mark"=>$result->retest_peo_mark,
						);			
	}
	$group[]=$obj;
	echo json_encode($group,JSON_UNESCAPED_UNICODE);