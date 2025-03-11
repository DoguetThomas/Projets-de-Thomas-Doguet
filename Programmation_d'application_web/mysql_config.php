<?php
define('DB_HOST', 'mysql:host=mysql.info.unicaen.fr;port=3306;');
define('DB_NAME', 'dbname=moalic222_bd');
define('DB_USER', 'moalic222');
define('DB_PASS', 'ce2eL9niojiengu6');
try {
    $dsn = DB_HOST . DB_NAME;
    $options = [
        PDO::MYSQL_ATTR_INIT_COMMAND => "SET NAMES utf8",
        PDO::ATTR_ERRMODE => PDO::ERRMODE_EXCEPTION
    ];

    $pdo = new PDO($dsn, DB_USER, DB_PASS, $options);
    $pdo->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
} catch (PDOException $e) {
    throw new Exception("Database connection failed: " . $e->getMessage());
}
?>
