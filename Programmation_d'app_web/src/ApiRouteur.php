<?php
set_include_path("./src/view");
require_once("ApiView.php");
set_include_path("./src/control");
require_once("controller.php");

    class ApiRouter {

        public function main($animalStorage) {

            $view = new ApiView($this, $animalStorage);
            $control = new Controller($view, $animalStorage);

            if (isset($_GET['collection']) && $_GET['collection'] == 'animaux') {
                if (isset($_GET['id'])) {
                    $id = $_GET['id'];
                    $control->showInformation($id);
                }
                else {
                    $animals = $animalStorage->readAll();
                    $control->showList();
                }
            }
            else {
                http_response_code(400);
                $view->display(["error" => "Page inconnue"]);
            }
        }
    }

?>