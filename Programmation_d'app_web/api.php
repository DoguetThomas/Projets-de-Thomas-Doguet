<?php
define('URL', rtrim(dirname($_SERVER['SCRIPT_NAME']), '/'));

set_include_path("./src");
require_once("ApiRouteur.php");

session_start();

require_once("decisions.php");

$router = new ApiRouter();

$router->main($animalStorage);

?>