<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once 'include/db_connect.php';
//$txt = "GET";
// connecting to db
$db = new DB_CONNECT();
 $db->connectforget();

$barId = $_GET["barId"];
// get all products from products table
$result = mysql_query("select u.fName, u.lName, c.commentText from user u, comments c where barId='$barId' and  u.unique_id=c.userId") or die(mysql_error());




// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // images node
    $response["comments"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $comments = array();
        $comments["fName"] = $row["fName"];
		$comments["lName"] = $row["lName"];
		$comments["commentText"] = $row["commentText"];






        // push single product into final response array
        array_push($response["comments"], $comments);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No users found";

    // echo no users JSON
    echo json_encode($response);
}
?>
