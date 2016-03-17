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
	for ($i = 1; $i <= $data->sheets[0]['numRows']; $i++) {
		for ($j = 1; $j <= $data->sheets[0]['numCols']; $j++) {
			echo $param[$j]=$data->sheets[0]['cells'][$i][$j];
		}
	}