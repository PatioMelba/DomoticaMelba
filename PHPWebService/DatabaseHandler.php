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
    private $username = "root";
    private $password = "";
    private $DBH = null;

    public function __construct () {
        try {
            $this->DBH = new PDO("mysql:host=$this->servername;dbname:'patio_melba';", $this->username, $this->password);
        } catch (PDOException $exception) {
            echo "Connection failed: " . $exception->getMessage();
        }
    }

    public function addStreepje($userID, $amount) {
        if ($this->DBH != null) {
            try {
                $this->DBH->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $statement = $this->DBH->prepare("INSERT INTO patio_melba.streeplijst (id, user_id,added_by, amount,date)
                                              VALUES (NULL, :userid, :userid, :amount, CURRENT_TIMESTAMP)");
                $statement->bindParam(':userid', $userID);
                $statement->bindParam(':amount', $amount);

                $statement->execute();

                echo "Succesfully Added.";
            } catch (PDOException $exception) {
                echo "Failed to add streepjes: ".$exception->getMessage();
            }
        }

    }

    public function getStreepjes($userID) {
        if ($this->DBH != null) {
            try {
                $this->DBH->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $statement = $this->DBH->prepare("SELECT SUM(amount) FROM patio_melba.streeplijst WHERE `user_id` = :userid");
                $statement->bindParam(':userid', $userID);
                $statement->execute();

                $result = $statement->setFetchMode(PDO::FETCH_ASSOC);
                $result = $statement->fetchColumn(0);

                //typecheck?
                return $result;

                echo "Succesfully Added.";
            } catch (PDOException $exception) {
                echo "Failed to add streepjes: ".$exception->getMessage();
            }
        }
    }
    public function login($user, $password) {
        if ($this->DBH != null) {
            try {
                $this->DBH->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $statement = $this->DBH->prepare("SELECT * FROM patio_melba.users WHERE name = :userid AND password = :password");
                $statement->bindParam(':userid', $user);
                $statement->bindParam(':password', $password);
                $statement->execute();

                if ($statement->rowCount() == 1) {
                    return $statement->fetchColumn(0);
                } else {
                    return false;
                }
            } catch (PDOException $exception) {
                echo $exception->getMessage();
            }
        }
    }

    public function getStats($userId) {
        $data = array();
        //echo print_r($this->getPersonalBeerCount($userId));
        //echo "<BR>";


        $data = array_merge($data, $this->getPersonalBeerCount($userId));
        $data = array_merge($data, $this->getBeerCount());
        $data = array_merge($data, $this->getBeerPrice());
        return $data;
    }

    public function getPersonalBeerCount($userId) {
        if ($this->DBH != null) {
            try {
                $this->DBH->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $statement = $this->DBH->prepare("SELECT SUM(amount) AS userbeeramount,users.name FROM patio_melba.streeplijst INNER JOIN patio_melba.users ON streeplijst.user_id = users.id WHERE streeplijst.user_id = :userid ");
                $statement->bindParam(':userid', $userId);
                $statement->setFetchMode(PDO::FETCH_ASSOC);
                $statement->execute();
                if ($statement->rowCount() == 1) {
                    return $statement->fetch();
                } else {
                    return false;
                }

            } catch (PDOException $exception) {
                echo $exception->getMessage();
            }
        }
    }

    public function getBeerCount() {
        if ($this->DBH != null) {
            try {
                $this->DBH->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $statement = $this->DBH->prepare("SELECT SUM(amount) AS totalbeercount FROM patio_melba.streeplijst");
                $statement->setFetchMode(PDO::FETCH_ASSOC);
                $statement->execute();
                if ($statement->rowCount() == 1) {
                    return $statement->fetch();
                } else {
                    return false;
                }
            } catch (PDOException $exception) {
                echo $exception->getMessage();
            }
        }
    }

    public function getBeerPrice() {
        if ($this->DBH != null) {
            try {
                $this->DBH->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);
                $statement = $this->DBH->prepare("SELECT SUM(price)/SUM(amount) AS beerprice FROM patio_melba.beer_stock WHERE is_payed = 0");
                $statement->setFetchMode(PDO::FETCH_ASSOC);
                $statement->execute();
                if ($statement->rowCount() == 1) {
                    return $statement->fetch();
                } else {
                    return false;
                }
            } catch (PDOException $exception) {
                echo $exception->getMessage();
            }
        }
    }



}