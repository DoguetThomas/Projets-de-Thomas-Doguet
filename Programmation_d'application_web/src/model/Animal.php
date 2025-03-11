<?php
    class Animal{
        private $nom;
        private $espece;
        private $age;
        private $source;

        public function __construct($nom, $espece, $age, $source){
            $this->nom = $nom;
            $this->espece = $espece;
            $this->age = $age;
            $this->source = $source;
        }

        public function getNom(){
            return $this->nom;
        }

        public function getEspece(){
            return $this->espece;
        }

        public function getAge(){
            return $this->age;
        }

        public function getSource(){
            return $this->source;
        }

        public function setSource($source){
            $this->source = $source;
        }
    }
?>