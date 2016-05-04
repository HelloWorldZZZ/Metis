<?php
	include_once("../config.php");
	$sql=$c->query("select * from d_mark where mark is null");
	if($result=mysqli_fetch_array($sql)){
		echo "初试未结束，不能下载复试试题";
		exit();
	}
	$c->query("delete from s_question");
	$c->query("update d_save_status set is_complete=0 where save_type_name='downloadQuestion'");
	$status=1;
	$cu = curl_init();
	// $data="name=zlx&password=123456";
	$URL=$root_url.$question_url;
	curl_setopt($cu, CURLOPT_URL, $URL);
	curl_setopt($cu, CURLOPT_RETURNTRANSFER, 1);
	curl_setopt($cu, CURLOPT_SSL_VERIFYPEER, false);    //SSL 报错时使用
    curl_setopt($cu, CURLOPT_SSL_VERIFYHOST, false);    //SSL 报错时使用
	$response_json = curl_exec($cu);
	if ($response_json === FALSE) {
		echo "cURL Error: " . curl_error($cu);
		exit();
	}
	$response=json_decode($response_json,true);
	foreach($response as $item){
		array_pop($item);
		array_pop($item);
		$str="('".implode("','",$item)."')";
		$sql=$c->query("insert into s_question values$str");
	}
	$c->query("update d_save_status set is_complete='$status' where save_type_name='downloadQuestion'");
	curl_close($cu);
	echo "下载成功";
