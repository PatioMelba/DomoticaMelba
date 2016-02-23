<?php
header('Content-Type: application/json');
require_once ("DatabaseHandler.php");
/**
 * Created by PhpStorm.
 * User: Boris & Pim
 * Date: 2/15/2016
 * Time: 7:03 PM
 */

if (isset($_GET['do'])) {
    $postData = $_GET['do'];
} else {
    exit("Wrong use of webservice.");
}

//database handler object.
$databaseHandler = new DatabaseHandler();
//echo "<data>";
if (isset($postData)) {
    switch($postData) {
        case "addStreepje":
            if (isset($_GET['user']) && is_numeric($_GET['user']) && $_GET['amount'] && is_numeric($_GET['amount'])) {
                $databaseHandler->addStreepje($_GET['user'],$_GET['amount']);
            } else {
                echo "Wrong use of method.".$_GET['user']. " en " . $_GET['amount'];
            }
            break;

        case "getStreepjes":
            if (isset($_GET['user']) && is_numeric($_GET['user'])) {
                echo "<amount>";
                    echo $databaseHandler->getStreepjes($_GET['user']);
                echo "</amount>";
            } else {
                echo "Wrong use of method.";
            }
            break;

        case "login":
            if (isset($_GET['user']) && isset($_GET['pass'])) {
                $result = $databaseHandler->login($_GET['user'],$_GET['pass']);
                if (is_numeric($result)) {
                    echo "succes:".$result ."\n";
                } else {
                    echo "Invalid login";
                }
            }
            break;

        case "getstats":
            if (isset($_GET['user'])) {
                $result = $databaseHandler->getStats($_GET['user']);
                if (isset($result) && $result !="") {
                    //print_r($result);
                    echo json_encode($result);
                } else {
                    echo "Invalid login";
                }
            }
            break;
        case "syncStreepjes":
            if (isset($_GET['date'])) {
                $date = str_replace("."," ",$_GET['date']);
                $result = $databaseHandler->getStreepjesFrom($date);
                if (isset($result) && $result !="") {
                    //print_r($result);
                    echo json_encode($result);
                } else {
                    echo "Probably Wrong Date Format: ".$_GET['date'];
                }
            }
            break;
    }
}

//echo "</data>";

?>