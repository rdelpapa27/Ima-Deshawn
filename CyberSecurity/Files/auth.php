<?php
session_start();
$username = $_POST['username'];
$password = $_POST['password'];
$captcha = $_POST['captcha'];

// Verify CAPTCHA
if ($captcha !== $_SESSION['captcha']) {
    die("Invalid CAPTCHA");
}

// Connect to MySQL
$conn = new mysqli("localhost", "root", "COSC4343", "cybersecurity");
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}

// Hash the entered password
$hashed_password = sha1($password);

// Query the database
$stmt = $conn->prepare("SELECT clearance FROM UserAccounts WHERE username = ? AND password = ?");
$stmt->bind_param("ss", $username, $hashed_password);
$stmt->execute();
$result = $stmt->get_result();

if ($result->num_rows > 0) {
    $row = $result->fetch_assoc();
    $_SESSION['username'] = $username;
    $_SESSION['clearance'] = $row['clearance'];
    header("Location: dashboard.php");
} else {
    echo "Invalid username or password";
}

$stmt->close();
$conn->close();
?>
