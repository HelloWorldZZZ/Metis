<?php
	include_once("../config.php");
	$sql=$c->query("select enroll_num,subject_id,expert_id,impression_mark,mark,createTime from d_mark");
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	@$json->first_mark=$group;
	$group=array();
	$sql=$c->query("select enroll_num,subject_id,expert_id,retest_en_mark,retest_pro_mark,retest_peo_mark,createTime from d_mark group by enroll_num");
	while($result=mysqli_fetch_object($sql))
		$group[]=$result;
	$json->retest_mark=$group;
	$data=json_encode($json,JSON_UNESCAPED_UNICODE);
	$data="name=zlx&password=123456&marks=".$data;
	$cu = curl_init();
	curl_setopt($cu, CURLOPT_URL, $root_url.$url_mark);
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
