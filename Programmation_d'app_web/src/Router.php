<?php

//inutile (à l'abandon) depuis la création du path info routeur dans la partie complement du TP8.
    set_include_path("./src/control");
    require_once("controller.php");
    set_include_path("./src/view");
    require_once("View.php");

    class Router{
        public function main($animalStorgage){

	        $view = new View($this);
            $control = new Controller($view, $animalStorgage);
            if(isset($_GET['id'])){
                $id = $_GET['id'];
                $control->showInformation($id);
            }
            else if(isset($_GET['action']) && $_GET['action'] == "liste"){
                $control->showList();
            }
            else{
                $control->showAccueil();
            }
            
        }

        public function getAnimalURL($id){
            return "site.php?id=".$id;
        }
    }
?>