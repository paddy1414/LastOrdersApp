<?php

/*
 * Following code will list all the products
 */

// array for JSON response
$response = array();


// include db connect class
require_once __DIR__ . '/db_connect.php';

// connecting to db
$db = new DB_CONNECT();

// get all products from products table
$result = mysql_query("SELECT *FROM bars") or die(mysql_error());

// check for empty result
if (mysql_num_rows($result) > 0) {
    // looping through all results
    // images node
    $response["bars"] = array();
    
    while ($row = mysql_fetch_array($result)) {
        // temp user array
        $bars = array();
        $bars["barId"] = $row["barId"];
		$bars["barName"] = $row["barName"];
		$bars["wheelchar"] = $row["wheelChar"];
		$bars["barLocation"] = $row["barLocation"];
		$bars["pictureUrl"] = $row["pictureUrl"];
		$bars["barFBPage"] = $row["barFBPage"];




        // push single product into final response array
        array_push($response["bars"], $bars);
    }
    // success
    $response["success"] = 1;

    // echoing JSON response
    echo json_encode($response);
} else {
    // no products found
    $response["success"] = 0;
    $response["message"] = "No bars found";

    // echo no users JSON
    echo json_encode($response);
}
?>
