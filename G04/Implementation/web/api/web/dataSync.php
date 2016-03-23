<?php
	$URL="http://192.168.0.100:8080/Clemson/school/20/test";
	$cu = curl_init();
	$data="name=zlx&password=123456";
	
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
