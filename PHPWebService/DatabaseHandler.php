<?php

/**
 * Created by PhpStorm.
 * User: Boris
 * Date: 2/15/2016
 * Time: 7:17 PM
 */
class DatabaseHandler
{
    private $servername = "localhost";
    private $username = "username";
    private $password = "password";
    private $DBH = null;

    public function __construct () {
        try {
            $this->DBH = new PDO("mysql:host=$this->servername;dbname:patio_melba;", $this->username, $this->password);
        } catch (PDOException $exception) {
            echo "Connection failed: " . $exception->getMessage();
        }
    }

    public function addStreepje($userID, $amount) {
        if ($this->DBH != null) {
            try {
                $statement = $this->DBH->prepare("INSERT INTO streeplijst (id, user_id,added_by, amount,date)
                                              VALUES (NULL, :userid, :userid, :amount, CURRENT_TIMESTAMP)");
                $statement->bindParam(':userid', $userID);
                $statement->bindParam(':amount', $amount);

                $statement->execute();
            } catch (PDOException $exception) {
                echo "Failed to add streepjes: ".$exception->getMessage();
            }
        }

    }



}