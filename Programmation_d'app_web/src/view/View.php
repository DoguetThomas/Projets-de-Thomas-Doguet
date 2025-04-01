<?php
require_once('src/model/AnimalBuilder.php');
Class View{
    private $title;
    private $content;
    private $routeur;
    private $menu;
    private $feedback;
    

    public function __construct($routeur, $feedback, $animalStorage){
        $this->routeur = $routeur;
        $this->menu = array(
            "accueil" => URL . "/site.php",
            "liste" => URL . "/site.php?action=liste",
            "nouveau" => $this->routeur->getAnimalCreationURL()
        );
        $this->feedback = $feedback;
        $this->animalStorage = $animalStorage;
    }

    public function prepareTestPage(){
        $this->title = "titre de test";
        $this->content = "ceci est un contenu de test";
    }

    public function prepareAnimalPage($animal) {
        $id = $this->animalStorage->getId($animal);
        $this->render();
        $this->title = "Page sur " . $animal->getNom();
        $this->content = htmlspecialchars($animal->getNom(), ENT_QUOTES) . " est un animal de l'espèce " . htmlspecialchars($animal->getEspece(), ENT_QUOTES) . " qui est agé de " . $animal->getAge() . " ans.";
        
        echo "<h1>" . $this->title . "</h1>";
        echo "<p>" . $this->content . "</p>";
        if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_FILES['image'])) {
            $targetDir = "images/";
            $extension = strtolower(pathinfo($_FILES["image"]["name"], PATHINFO_EXTENSION));
            $filename = $id . "." . $extension;
            $targetFile = $targetDir . $filename;

            $imageFileType = strtolower(pathinfo($targetFile, PATHINFO_EXTENSION));
            $check = getimagesize($_FILES["image"]["tmp_name"]);

            if ($check !== false && !file_exists($targetFile)) {
                if (move_uploaded_file($_FILES["image"]["tmp_name"], $targetFile)) {
                    $animal->setSource($filename);
                    $this->animalStorage->update($id, $animal);
                } else {
                    echo "Erreur lors du téléchargement.";
                }
            }
        }
        if ($_SERVER['REQUEST_METHOD'] === 'POST' && isset($_POST['delete'])) {
            $currentFile = "images/" . $animal->getSource();
            if (file_exists($currentFile)) {
                unlink($currentFile);
                $animal->setSource(NULL);
                $this->animalStorage->update($this->animalStorage->getId($animal), $animal);
            }
        }
        if ($animal->getSource() != NULL) {
            echo '<img src="../images/' . $animal->getSource() . '" alt="' . $animal->getNom() . '" class="animal-image" style="width: 500px; height: 500px; object-fit: cover;"/>';
            echo '<br>
                <form action="#" method="post">
                    <button type="submit" name="delete">Supprimer l\'image</button>
                </form>';
                
        } else {
            echo '<h2>Ajout d\'une image</h2>
                <form action="#" method="post" enctype="multipart/form-data">
                    <input type="file" name="image" accept="image/png, image/jpeg, image/jpg" required>
                    <input type="submit" value="Ajouter">
                </form>';
    
            
        }
    
        $this->end();
    }
    

    public function prepareListPage($liste){
        $this->render();
        echo "<h1>Liste d'animaux</h1>";
        foreach($liste as $instance){
            echo "<a href = ".$this->routeur->getAnimalURL($this->animalStorage->getId($instance)).">".$instance->getNom()."</a>";
            echo "<br>";
        }
        $this->end();
    }

    public function prepareAnimalCreationPage($url, $animalBuilder){
            $data = $animalBuilder->getData();
            $error = $animalBuilder->getError();
            $this->render();
            echo "<form action=".$url." method='post'>
            <label for='".AnimalBuilder::NAME_REF."'>Nom</label>
            <input type='text' id='".AnimalBuilder::NAME_REF."' name='".AnimalBuilder::NAME_REF."' value='".htmlspecialchars($data[AnimalBuilder::NAME_REF], ENT_QUOTES)."'/>
            
            <label for='".AnimalBuilder::SPECIES_REF."'>Espece</label>
            <input type='text' id='".AnimalBuilder::SPECIES_REF."' name='".AnimalBuilder::SPECIES_REF."' value='".htmlspecialchars($data[AnimalBuilder::SPECIES_REF], ENT_QUOTES)."'/>
            
            <label for='".AnimalBuilder::AGE_REF."'>Age</label>
            <input type='text' id='".AnimalBuilder::AGE_REF."' name='".AnimalBuilder::AGE_REF."' value='".$data[AnimalBuilder::AGE_REF]."'/>
            
            <button type='submit'>Envoyer le formulaire</button>
        </form>";
        if ($error){
            echo "<p>".$error."</p>";
        }
        $this->end();
    }

    public function displayAnimalCreationSuccess($id){
        $animalPageUrl = $this->routeur->getAnimalURL($id);
        $this->routeur->POSTredirect($animalPageUrl, "succes de l'ajout de l'animal");
    }

    public function accueil(){
        $this->render();
        echo "<h1>Accueil</h1>";
        echo "<p>vous etes a l'acceuil, si vous souhaitez afficher une liste des annimaux, rendez vous sur le lien ci-dessous</p>";
        echo "<a href = '?action=liste'>Lien vers la liste des animaux</a>";
        $this->end();
    }

    private function end(){
        echo "</body>
        </html>";
    }

    public function menu(){
        foreach ($this->menu as $nom => $url){ 
            echo "<li><a href = $url> $nom </a></li>";
        }
    }

    public function render(){
        echo "<!DOCTYPE html>
	<html lang='fr'>
		<head>
			<meta charset='UTF-8'>
			<title>TP Animaux</title>
			<meta name='autor' content='doguet221' >
		</head>
		<body>
            <p>".$this->feedback."
            <nav>
                <ul>";
                $this->menu();
        echo " </ul>
            </nav>";
    }

    public function prepareUnknownAnimalPage(){
        $this->render();
        echo "<h1>Animal inconnu</h1>";
        $this->end();
    }

    public function prepareErrorMessage(){
        echo "<h1>Erreur</h1>";
        echo "<p>Veuillez recommencer en verifiant votre saisie</p>";
    }

    public function prepareDebugPage($variable) {
        $this->title = 'Debug';
        $this->content = '<pre>'.htmlspecialchars(var_export($variable, true)).'</pre>';
        echo "<h1>".$this->title."</h1>";
        echo "<p>".$this->content."</p>";
    }

    public function getRouteur(){
        return $this->routeur;
    }
}
?>