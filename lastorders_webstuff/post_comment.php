<?php



require_once 'include/DB_Functions.php';
$db = new DB_Functions();

// json response array
$response = array("error" => FALSE);

if (isset($_POST['barId'])&& isset($_POST['userId']) && isset($_POST['commentText'])) {

    // receiving the post params
    $barId = $_POST['barId'];
	$userId = $_POST['userId'];
    $commentText = $_POST['commentText'];

	
    // check if user is already existed with the same email
    if ($db->isUserCommented($userId, $barId)) {
       $comment = $db->updateCommnet($barId, $userId, $commentText);
        if ($comment) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["comment"]["barId"] = $comment["barId"];
            $response["comment"]["userId"] = $comment["userId"];
			$response["comment"]["commentText"] = $comment["commentText"];
            echo json_encode($response);
		} else {
            // user failed to store
            $response["error"] = TRUE;
            $response["error_msg"] = "Unknown error occurred in registration!";
            echo json_encode($response);
        }
    } else {
        // create a new user
        $comment = $db->storeComment($barId, $userId, $commentText);
        if ($comment) {
            // user stored successfully
            $response["error"] = FALSE;
            $response["comment"]["barId"] = $comment["barId"];
            $response["comment"]["userId"] = $comment["userId"];
			$response["comment"]["commentText"] = $comment["commentText"];
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