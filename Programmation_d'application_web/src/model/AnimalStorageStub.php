<?php
require_once('src/model/AnimalStorage.php');
require_once('src/model/Animal.php');


class AnimalStorageStub implements AnimalStorage{
    private $liste;
    
    public function __construct(){
        $medor = new Animal("Médor", 'chien', '8');
        $felix = new Animal("Félix", 'chat', '3');
        $denver = new Animal("Denver", 'dinosaure', '16000');
        $this->liste = array(
            'medor' => $medor,
            'felix' => $felix,
            'denver' => $denver,
        );
    }

    public function read($id){
        if (array_key_exists($id, $this->liste)){
            return $this->liste[$id];
        }
        else{
            return null;
        }
    }

    public function readAll(){
        return $this->liste;
    }

    public function create(Animal $a){
        throw new ErrorException("Exception lors d'ajout d'un animal");
    }

    public function delete($id){
        throw new ErrorException("Exception lors de la suppresion d'un animal");
    }

    public function update($id, Animal $a){
        throw new ErrorException("Exception lors de la mise à jour d'un animal");
    }

    public function getid(Animal $animal){
		foreach ($this->liste as $id => $storedAnimal) {
			if ($storedAnimal === $animal) {
				return $id;
			}
		}
		return null;
	}
}
?>