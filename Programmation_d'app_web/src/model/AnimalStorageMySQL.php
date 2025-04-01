<?php

require_once("AnimalStorage.php");
require_once("src/model/Animal.php");
require_once("src/model/AnimalStorage.php");
require_once("src/model/AnimalStorageStub.php");

class AnimalStorageMySQL implements AnimalStorage {

    private $pdo;

    public function __construct($pdo){
        $this->pdo = $pdo;
    }
    public function read($id){
        try {
            $stmt = $this->pdo->prepare("SELECT * FROM animals WHERE id = :id;");
            $stmt->bindParam(':id', $id, PDO::PARAM_INT);
            $stmt->execute();
            $animal = $stmt->fetch(); 
            
            if (!$animal) {
                throw new Exception("Animal avec l'ID $id non trouvé.");
            }

            return new Animal($animal['name'], $animal['species'], $animal['age'], $animal['source']);
        } catch (PDOException $e) {
            throw new Exception("Erreur lors de l'exécution de la requête SQL dans la méthode read : " . $e->getMessage());
        }
    }
    public function readAll(){
        try {
            $stmt = $this->pdo->query("SELECT * from animals;");
            $all = $stmt->fetchAll();
            $res = array();
            foreach ($all as $animal) {
                array_push($res, new Animal($animal['name'], $animal['species'], $animal['age'], $animal['id'], $animal['source']));
            }
            return $res;
        } catch (PDOException $e) {
            throw new Exception("Erreur lors de l'exécution de la requête SQL dans la méthode readAll : " . $e->getMessage());
        }
    }

    public function create(Animal $a){
        try {
            $stmt = $this->pdo->prepare("INSERT INTO animals(name, species, age) VALUES (:name, :species, :age);");

            $name = $a->getNom();
            $species = $a->getEspece();
            $age = $a->getAge();
            $source = $a->getSource();

            $stmt->bindParam(':name', $name, PDO::PARAM_STR);
            $stmt->bindParam(':species', $species, PDO::PARAM_STR);
            $stmt->bindParam(':age', $age, PDO::PARAM_INT); 
            
            $stmt->execute();
            return $this->getId($a);
        } catch (PDOException $e) {
            throw new Exception("Erreur lors de l'exécution de la requête SQL dans la méthode create : " . $e->getMessage());
        }
    }

    public function delete($id){
        try {
            $stmt = $this->pdo->prepare("DELETE FROM animals WHERE id = :id;");
            $stmt->bindParam(':id', $id, PDO::PARAM_INT);
            $stmt->execute();
            return true;
        } catch (PDOException $e) {
            throw new Exception("Erreur lors de l'exécution de la requête SQL dans la méthode delete : " . $e->getMessage());
            return false;
        }
    }

    public function update($id, Animal $a){
        try {
            $stmt = $this->pdo->prepare("UPDATE animals SET name = :name, species = :species, age = :age, source = :source WHERE id = :id;");
            $name = $a->getNom();
            $stmt->bindParam(':name', $name, PDO::PARAM_STR);
            $espece = $a->getEspece();
            $stmt->bindParam(':species', $espece, PDO::PARAM_STR);
            $age = $a->getAge();
            $stmt->bindParam(':age', $age, PDO::PARAM_INT);
            $stmt->bindParam(':id', $id, PDO::PARAM_INT);
            $source = $a->getSource();
            $stmt->bindParam(':source', $source, PDO::PARAM_STR);
            $stmt->execute();
            return true;
        } catch (PDOException $e) {
            throw new Exception("Erreur lors de l'exécution de la requête SQL dans la méthode update : " . $e->getMessage());
            return false;
        }
    }

    public function getId(Animal $a){
        try {
            $stmt = $this->pdo->prepare('SELECT id FROM animals WHERE name = :nom AND species = :espece AND age = :age;');
            $nom = $a->getNom();
            $stmt->bindParam(':nom', $nom, PDO::PARAM_STR);
            $espece = $a->getEspece();
            $stmt->bindParam(':espece', $espece, PDO::PARAM_STR);
            $age = $a->getAge();
            $stmt->bindParam(':age', $age, PDO::PARAM_INT);
            $stmt->execute();
            $row = $stmt->fetch();
            return $row['id'];
        }
        catch (PDOException $e) {
            throw new Exception("Erreur lors de l'exécution de la requête SQL dans la méthode getId : " . $e->getMessage());
            return false;
        }
    }
}

?>
