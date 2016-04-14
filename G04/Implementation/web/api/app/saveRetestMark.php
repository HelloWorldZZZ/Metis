<?php
	include_once("../config.php");
	$obj["status"]=0;
	$retestMark=json_decode(@$_POST["retestMark"],true);
	if(!$retestMark){
		$retestMark["retest_en_mark"]=60;
		$retestMark["retest_pro_mark"]=70;
		$retestMark["retest_peo_mark"]=80;
		$retestMark["enroll_num"]=1517;
	}
	$sql=$c->query("update d_mark set retest_en_mark=$retestMark[retest_en_mark],retest_pro_mark=$retestMark[retest_pro_mark],retest_peo_mark=$retestMark[retest_peo_mark] where enroll_num=$retestMark[enroll_num]");
	if($sql)
		$obj["status"]=1;
	echo json_encode($obj);
