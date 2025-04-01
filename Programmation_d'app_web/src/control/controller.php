<?php
set_include_path("./src/model");
require_once('Animal.php');
require_once('AnimalBuilder.php');
 class Controller{
    private $view;
    private $animalStorage;

    public function __construct($view, $animalStorage){
        $this->view = $view;
        $this->animalStorage = $animalStorage;
    }

    public function showInformation($id) {
        if ($this->animalStorage->read($id)){
            $this->view->prepareAnimalPage($this->animalStorage->read($id)); 
        }
        else{
            $this->view->prepareUnknownAnimalPage();
        }
    }

    public function createNewAnimal($url){
        $animalBuilder = new AnimalBuilder(array());
        $this->view->prepareAnimalCreationPage($url, $animalBuilder);
    }

    public function saveNewAnimal(array $data){
        $animalBuilder = new AnimalBuilder($data);
        if (!$animalBuilder->isValid()){
            $this->view->prepareAnimalCreationPage($this->view->getRouteur()->getAnimalCreationURL(), $animalBuilder);
        }
        else{
            $newAnimal = $animalBuilder->createAnimal();
            $id = $this->animalStorage->create($newAnimal);
            $this->view->displayAnimalCreationSuccess($id);
        }
    }

    public function showList(){
        $this->view->prepareListPage($this->animalStorage->readAll()); 
    }

    public function showAccueil(){
        $this->view->accueil(); 
    }

    public function showDebugPage($var){
        $this->view->prepareDebugPage($var);
    }
 }

?>