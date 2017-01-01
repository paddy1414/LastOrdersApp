<?php


require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);


if (isset($_POST['barId'])) {
	
	   // receiving the post params
    $barId = $_POST['barId'];

    $response = $db->get_bar_comments($barId);

    if ($response != false) {
		  $response["error"] = FALSE;
   // success
    $response["success"] = 1;

    // echoing JSON response
 echo json_encode($response);
 
    } else {
        // user is not found with the credentials
        $response["error"] = TRUE;
        $response["error_msg"] = "getting bar credentials are wrong. Please try again!";
        echo json_encode($response);
    }
}  else {
    // required post params is missing
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters barIdis missing!";
    echo json_encode($response);
}
	
	

?>

