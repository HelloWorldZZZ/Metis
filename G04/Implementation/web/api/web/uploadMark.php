<?php
	include_once("../config.php");
	$sql=$c->query("select enroll_num,subject_id,expert_id,mark,createTime from d_mark");
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	$data=json_encode($group,JSON_UNESCAPED_UNICODE);
	$data="marks=".$data;
	//echo $data;
	$URL="";
	$cu = curl_init();
	curl_setopt($cu, CURLOPT_URL, $URL);
	curl_setopt($cu, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($cu, CURLOPT_SSL_VERIFYPEER, false);    //SSL 报错时使用
    curl_setopt($cu, CURLOPT_SSL_VERIFYHOST, false);    //SSL 报错时使用
	if (!empty($data)){
        curl_setopt($cu, CURLOPT_POST, 1);
        curl_setopt($cu, CURLOPT_POSTFIELDS, $data);
    }
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
	}
	else
		echo $response_json;
	curl_close($cu);
