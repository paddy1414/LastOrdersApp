<?php
require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['barId'])&& isset($_POST['userId']) && isset($_POST['ratingNo'])) {

    // receiving the post params
    $barId = $_POST['barId'];
	$userId = $_POST['userId'];
    $ratingNo = $_POST['ratingNo'];

    // check if user is already existed with the same email
    if ($db->isUserRating($userId, $barId)) {
       $rating = $db->updateRating($barId, $userId, $ratingNo);
        if ($rating) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["rating"]["barid"] = $rating["barid"];
            $response["rating"]["userid"] = $rating["userid"];
			$response["rating"]["ratingNo"] = $rating["ratingNo"];
            echo json_encode($response);
		} else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    } else {
        // create a new user
        $rating = $db->storeRating($barId, $userId, $ratingNo);
        if ($rating) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["rating"]["barid"] = $rating["barid"];
            $response["rating"]["userid"] = $rating["userid"];
			$response["rating"]["ratingNo"] = $rating["ratingNo"];
            echo json_encode($response);
        } else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    }
} else {
    $response["error"] = TRUE;
    $response["error_msg"] = "Required parameters (name, email or password) is missing!";
    echo json_encode($response);
}

?>