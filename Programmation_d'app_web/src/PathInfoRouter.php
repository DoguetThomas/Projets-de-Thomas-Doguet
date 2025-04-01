<?php
    set_include_path("./src/control");
    require_once("controller.php");
    set_include_path("./src/view");
    require_once("View.php");
    set_include_path("./src/model");
    require_once("AnimalStorageSession.php");

    class PathInfoRouter{
        public function main($animalStorgage){
			$_SESSION["feedback"] = null;
	        $view = new View($this, $_SESSION["feedback"],$animalStorgage);
            $control = new Controller($view, $animalStorgage);
            if(isset($_SERVER['PATH_INFO'])){
                $id = substr($_SERVER['PATH_INFO'],1);
                $control->showInformation($id);
            }
            else if(isset($_GET['action']) && $_GET['action'] == "liste"){
                $control->showList();
            }
            else if(isset($_GET['action']) && $_GET['action'] == "nouveau"){
                if (isset($_POST[AnimalBuilder::NAME_REF])){
                    $control->saveNewAnimal($_POST);
                }
                else{
                    $control->createNewAnimal($this->getAnimalCreationURL());
                }
                
            }
            else if(isset($_GET['action']) && $_GET['action'] == "sauverNouveau"){
                $control->saveNewAnimal($_POST);

            }
            else{
                $control->showAccueil();
            }
            
        }

        public function getAnimalURL($id){
            return URL . "/site.php/".$id;
        }

        public function getAnimalCreationURL(){
            return URL . "/site.php?action=nouveau";
        }

        public function getAnimalSaveURL(){
            return URL . "/site.php?action=sauverNouveau";
        }

        public function POSTredirect($url, $feedback){
            $_SESSION["feedback"] = $feedback;
            header('Location: ' . $url, true, 303);
            exit;
        }
    }
?>
