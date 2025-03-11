<?php
class ApiView {
    public function __construct($routeur, $animalStorage){
        $this->routeur = $routeur;
        $this->animalStorage = $animalStorage;
    }

    public function display($element){
        header('Content-Type: application/json');
        echo json_encode($element);
    }

    public function prepareAnimalPage($animal){
        $res = [
            "nom" => $animal->getNom(),
            "espece" => $animal->getEspece(),
            "age" => $animal->getAge()
        ];
        $this->display($res);
    }

    public function prepareUnknownAnimalPage(){
        $this->display([
            "Erreur" => "Animal Inconu"
        ]);
    }

    public function prepareListPage($liste){
        $res = [];
        foreach ($liste as $animal) {
            $res[] = [
                "id" => $this->animalStorage->getId($animal),
                "nom" => $animal->getNom()
            ];
        }
        $this->display($res);
    }
}
?>