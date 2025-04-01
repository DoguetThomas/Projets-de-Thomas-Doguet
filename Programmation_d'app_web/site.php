<?php
define('URL', rtrim(dirname($_SERVER['SCRIPT_NAME']), '/'));

/*
 * On indique que les chemins des fichiers qu'on inclut
 * seront relatifs au répertoire src.
 */
set_include_path("./src");

/* Inclusion des classes utilisées dans ce fichier */
require_once("PathInfoRouter.php");

session_start();
require_once("decisions.php");

/*
 * Cette page est simplement le point d'arrivée de l'internaute
 * sur notre site. On se contente de créer un routeur
 * et de lancer son main.
 */

$router = new PathInfoRouter();
$router->main($animalStorage);

?>
