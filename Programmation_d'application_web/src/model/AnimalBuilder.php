<?php

class AnimalBuilder{
    private $data;
    private $error;
    
    const NAME_REF = 'NAME';
    const SPECIES_REF = 'ESPECE';
    const AGE_REF = 'AGE';
    const SOURCE_REF = 'SOURCE';

    public function __construct($data){
        $this->data = array_merge([
            self::NAME_REF => '',
            self::SPECIES_REF => '',
            self::AGE_REF => '',
            self::SOURCE_REF => ''
        ], $data);
        $this->error = null;
    }

    public function createAnimal(){
        $newAnimal = new Animal($this->data[self::NAME_REF], $this->data[self::SPECIES_REF], $this->data[self::AGE_REF], $this->data[self::SOURCE_REF]);
        return $newAnimal;
    }

    public function isValid(){
        if (empty($this->data[self::NAME_REF])|| empty($this->data[self::SPECIES_REF])||empty($this->data[self::AGE_REF]) || !is_numeric($this->data[self::AGE_REF])){
            $this->error = "verifiez la saisie";
            return False;
        }
        $this->error = null;
        return True;
    }

    public function getData(){
        return $this->data;
    }

    public function getError(){
        return $this->error;
    }
}
?>