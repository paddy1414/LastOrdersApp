<?php



class DB_Functions {

    private $conn;

    // constructor
    function __construct() {
        require_once 'DB_Connect.php';
        // connecting to database
        $db = new Db_Connect();
        $this->conn = $db->connect();
    }

    // destructor
    function __destruct() {
        
    }

    /**
     * Storing new user
     * returns user details
     */
        public function storeUser($fName, $lName, $email, $password) {
        $uuid = uniqid('', true);
        $hash = $this->hashSSHA($password);
        $encrypted_password = $hash["encrypted"]; // encrypted password
        $salt = $hash["salt"]; // salt

     $stmt = $this->conn->prepare("INSERT INTO user(unique_id, fName, lName, email, uPassword, salt) VALUES(?, ?, ?, ?, ?, ?)");
        $stmt->bind_param("ssssss", $uuid, $fName, $lName, $email, $encrypted_password, $salt);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM user WHERE email = ?");
            $stmt->bind_param("s", $email);
            $stmt->execute();
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $user;
        } else {
            return false;
        }
    }
	
	 public function storeComment($barId, $userId, $comment) {
     $stmt = $this->conn->prepare("INSERT INTO comments(barId, userId, commentText) VALUES(?, ?, ?)");
        $stmt->bind_param("iss", $barId, $userId, $comment);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM comments WHERE barId = ? and userId= ? ");
            $stmt->bind_param("is", $barId, $userId);
            $stmt->execute();
            $comment = $stmt->get_result()->fetch_assoc();
            $stmt->close();;

            return $comment;
        } else {
            return false;
        }
    }
	
	public function updateCommnet($barId, $userId, $comment) {
     $stmt = $this->conn->prepare("UPDATE comments SET commentText = ? where userid = ? and barId = ?");
        $stmt->bind_param("ssi",  $comment, $userId, $barId);
        $result = $stmt->execute();
        $stmt->close();

        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM comments WHERE barId = ? and userId= ? ");
            $stmt->bind_param("ss", $barId, $userId);
            $stmt->execute();
            $comment = $stmt->get_result()->fetch_assoc();
            $stmt->close();

            return $comment;
        } else {
            return false;
        }
    }

    /**
     * Get user by email and password
     */
    public function getUserByEmailAndPassword($email, $password) {
 
        $stmt = $this->conn->prepare("SELECT * FROM user WHERE email = ?");
 
        $stmt->bind_param("s", $email);
 
        if ($stmt->execute()) {
            $user = $stmt->get_result()->fetch_assoc();
            $stmt->close();
 
            // verifying user password
            $salt = $user['salt'];
            $encrypted_password = $user['uPassword'];
            $hash = $this->checkhashSSHA($salt, $password);
            // check for password equality
            if ($encrypted_password == $hash) {
                // user authentication details are correct
                return $user;
            }
        } else {
            return NULL;
        }
    }

    /**
     * Check user is existed or not
     */
    public function isUserExisted($email) {
        $stmt = $this->conn->prepare("SELECT email from user WHERE email = ?");

        $stmt->bind_param("s", $email);

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // user existed 
            $stmt->close();
            return true;
        } else {
            // user not existed
            $stmt->close();
            return false;
        }
    }
	
	 public function isUserCommented($userId, $barId) {
        $stmt = $this->conn->prepare("SELECT userId from comments WHERE userId = ? and barId = ?");

        $stmt->bind_param("si", $userId, $barId);
		

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // comment existed 
            $stmt->close();
            return true;
        } else {
            // comment not existed
            $stmt->close();
            return false;
        }
    }
	
	public function isUserRating($userId, $barId) {
        $stmt = $this->conn->prepare("SELECT * from ratings WHERE userId = ? and barId = ?");

        $stmt->bind_param("si", $userId, $barId);
		

        $stmt->execute();

        $stmt->store_result();

        if ($stmt->num_rows > 0) {
            // comment existed 
            $stmt->close();
            return true;
        } else {
            // comment not existed
            $stmt->close();
            return false;
        }
    }
	
	public function updateRating($barId, $userId, $ratingNo) {
			
		$stmt = $this->conn->prepare("select ratingNo from ratings where userId=? and barId =?");
        $stmt->bind_param("si", $userId, $barId );
		$row[] = $stmt->execute();
		$previous = $row['ratingNo'];
        $stmt->close();
			
		$stmt = $this->conn->prepare("update bars set numRatings = numRatings -1, totalRate = totalRate - ?, average=totalRate/numRatings where barId =?");
        $stmt->bind_param("si", $previous, $barId);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("update bars set numRatings = numRatings +1, totalRate = totalRate + ?, average=totalRate/numRatings where barId =?");
        $stmt->bind_param("si", $ratingNo, $barId);
        $result = $stmt->execute();
        $stmt->close();
			
		$stmt = $this->conn->prepare("update ratings set ratingNo = ? where userId=? and barId =?");
        $stmt->bind_param("ssi", $ratingNo, $userId, $barId );
        $result = $stmt->execute();
        $stmt->close();
		
			
        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM ratings WHERE barId = ? and userId= ? ");
            $stmt->bind_param("is", $barId, $userId);
            $stmt->execute();
            $ratingNo = $stmt->get_result()->fetch_assoc();
            $stmt->close();
            return $ratingNo;
        } else {
            return false;
        }
    }
	public function storeRating($barId, $userId, $ratingNo) {	
		$stmt = $this->conn->prepare("update bars set numRatings = numRatings +1, totalRate = totalRate + ?, average=totalRate/numRatings where barId =?");
        $stmt->bind_param("si", $ratingNo, $barId);
        $result = $stmt->execute();
        $stmt->close();
		
		$stmt = $this->conn->prepare("INSERT INTO ratings(barId, userId, ratingNo) VALUES(?, ?, ?)");
        $stmt->bind_param("iss", $barId, $userId, $ratingNo);
        $result = $stmt->execute();
        $stmt->close();


        // check for successful store
        if ($result) {
            $stmt = $this->conn->prepare("SELECT * FROM ratings WHERE barId = ? and userId= ? ");
            $stmt->bind_param("is", $barId, $userId);
            $stmt->execute();
            $comment = $stmt->get_result()->fetch_assoc();
            $stmt->close();;

            return $comment;
        } else {
            return false;
		}
	}

    /**
     * Encrypting password
     * @param password
     * returns salt and encrypted password
     */
    public function hashSSHA($password) {

        $salt = sha1(rand());
        $salt = substr($salt, 0, 10);
        $encrypted = base64_encode(sha1($password . $salt, true) . $salt);
        $hash = array("salt" => $salt, "encrypted" => $encrypted);
        return $hash;
    }

    /**
     * Decrypting password
     * @param salt, password
     * returns hash string
     */
    public function checkhashSSHA($salt, $password) {

        $hash = base64_encode(sha1($password . $salt, true) . $salt);

        return $hash;
    }
	
	
	

}

?>
