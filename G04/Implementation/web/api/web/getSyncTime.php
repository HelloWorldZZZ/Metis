<?php
	include_once("../config.php");
	$sql=$c->query("select time from d_save_status where save_type_name='dataSync'");
	$result=mysqli_fetch_object($sql);
	echo json_encode($result);