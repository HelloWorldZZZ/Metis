<?php
	include_once("./Excel/reader.php");
	$filename=$_FILES["file"]["name"];
	$dir=$filename;
	if(!move_uploaded_file($_FILES["file"]["tmp_name"],$dir)){
		$logger->warn("upload failed.");
		$logger->warn("excel save failed: $dir");
		$logger->warn("upload tmp_name is: " . $_FILES["photo"]["tmp_name"]);
		$logger->warn("upload name is: " . $_FILES["photo"]["name"]);
		echo "upload error";
		return;
	}

	//循环读取上传的表格数据并插入数据库
	$data = new Spreadsheet_Excel_Reader();
	$data->setOutputEncoding('utf-8');
	$data->read($dir);
	for ($j = 1; $j <= $data->sheets[0]['numCols']; $j++) {
		@$key[]=$data->sheets[0]['cells'][1][$j];
	}
	for ($i = 2; $i <= $data->sheets[0]['numRows']; $i++) {
		for ($j = 1; $j <= $data->sheets[0]['numCols']; $j++) {
			@$obj[$key[$j-1]]=$data->sheets[0]['cells'][$i][$j];
		}
		$group[]=$obj;
	}
	echo $data=json_encode($group,JSON_UNESCAPED_UNICODE);
	// $URL="https://api.weixin.qq.com/cgi-bin/menu/create?access_token=";
	// $cu = curl_init();
	// curl_setopt($cu, CURLOPT_URL, $URL);
	// curl_setopt($cu, CURLOPT_RETURNTRANSFER, 1);
	// curl_setopt($cu, CURLOPT_SSL_VERIFYPEER, false);    //SSL 报错时使用
    // curl_setopt($cu, CURLOPT_SSL_VERIFYHOST, false);    //SSL 报错时使用
	// if (!empty($data)){
        // curl_setopt($cu, CURLOPT_POST, 1);
        // curl_setopt($cu, CURLOPT_POSTFIELDS, $data);
    // }
	// $response_json = curl_exec($cu);
	// if ($response_json === FALSE) {
		// echo "cURL Error: " . curl_error($cu);
	// }
	// else
		// echo $response_json;
	// curl_close($cu);

