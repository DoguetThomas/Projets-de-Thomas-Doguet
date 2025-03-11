<?php 
    include ('src/model/AnimalStorageMySQL.php');
    require_once ('../../../../../private/mysql_config.php');
    $animalStorage = new AnimalStorageMySQL($pdo);
?>
