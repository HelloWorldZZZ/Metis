<?php
	define('DB_HOST','56d6b10631055.sh.cdb.myqcloud.com');
	define('DB_USER','cdb_outerroot');
	define('DB_PSW','metis123456');
	define('DB_NAME','test');
	define('DB_PORT','4003');	
	// define('DB_HOST','zlx.web.com');
	// define('DB_USER','root');
	// define('DB_PSW','0000');
	// define('DB_NAME','test');
	// define('DB_PORT','3306');
	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME,DB_PORT);
	$c->query("set names UTF8");
?>