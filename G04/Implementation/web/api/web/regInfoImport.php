<?php
	include_once("./Excel/reader.php");
	include_once("../config.php");
	//循环读取上传的表格数据并插入数据库
	$data = new Spreadsheet_Excel_Reader();
	$data->setOutputEncoding('utf-8');
	$data->read($_FILES["file"]["tmp_name"]);
	for ($j = 1; $j <= $data->sheets[0]['numCols']; $j++) {
		@$key[]=$data->sheets[0]['cells'][1][$j];
	}
	for ($i = 2; $i <= $data->sheets[0]['numRows']; $i++) {
		for ($j = 1; $j <= $data->sheets[0]['numCols']; $j++) {
			@$obj[$key[$j-1]]=$data->sheets[0]['cells'][$i][$j];
		}
		$group[]=$obj;
	}
	$data="name=zlx&password=123456&enrolls=".json_encode($group,JSON_UNESCAPED_UNICODE);
	$cu = curl_init();
	curl_setopt($cu, CURLOPT_URL, $root_url.$enroll_upload_url);
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
		exit();
	}
	if($response_json==1)
		echo "报名表上传成功！";
	curl_close($cu);

