<?php
/**
 * Created by PhpStorm.
 * User: Boris & Pim
 * Date: 2/15/2016
 * Time: 7:03 PM
 */

$postData = $_GET['do'];

//database handler object.
$databaseHandler = new DatabaseHandler();

if (isset($postData)) {
    switch($postData) {
        case "addStreepje":
            if (isset($_GET['user']) && is_int($_GET['user']) && $_GET['amount'] && is_int($_GET['amount'])) {
                $databaseHandler->addStreepje($_GET['user'],$_GET['amount']);
            }
            break;

    }


}

?>