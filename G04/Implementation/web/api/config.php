<?php
	define('DB_HOST','56d6b10631055.sh.cdb.myqcloud.com');
	define('DB_USER','cdb_outerroot');
	define('DB_PSW','metis123456');
	define('DB_NAME','test');
	define('DB_PORT','4003');
	$c=new mysqli();
	$c->connect(DB_HOST,DB_USER,DB_PSW,DB_NAME,DB_PORT);
	$c->query("set names UTF8");
?>