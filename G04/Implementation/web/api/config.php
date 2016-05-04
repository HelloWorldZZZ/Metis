<?php
	define('DB_HOST','56d6b10631055.sh.cdb.myqcloud.com');
	define('DB_USER','cdb_outerroot');
	define('DB_PSW','metis123456');
	define('DB_NAME','test2');
	define('DB_PORT','4003');
	// define('DB_HOST','zlx.web.com');
	// define('DB_USER','root');
	// define('DB_PSW','0000');
	// define('DB_NAME','test');
	// define('DB_PORT','3306');
	$root_url="http://192.168.1.100:8080/Clemson";
	
	$student_url="/school/20/ready/student";
	$class_url="/school/20/ready/classroom";
	$test_url="/school/20/ready/test";
	$tssc_url="/school/20/ready/testSchoolSubjectClass";
	$enroll_url="/school/20/ready/enroll";
	$retest_url="/school/20/ready/testSchoolRetest";
	
	$question_url="/school/20/ready/question";
	$questionType_url="/school/20/ready/questionType";
	
	$url_mark="/school/20/mark";
	
	$enroll_upload_url="/school/20/enroll";
	header("Content-type: text/html; charset=utf-8"); 
	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME,DB_PORT);
	$c->query("set names UTF8");
?>