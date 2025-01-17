<?php
if ($_SERVER["REQUEST_METHOD"] === "OPTIONS") {
    header("Access-Control-Allow-Origin: http://localhost:5173");
    header("Access-Control-Allow-Methods: POST");
    header("Access-Control-Allow-Headers: Content-Type, Authorization");
    header("Access-Control-Allow-Credentials: true");
    header("Access-Control-Max-Age: 3600");
    header("Content-Length: 0");
    header("Content-Type: text/plain");
    exit();
}

header("Access-Control-Allow-Origin: http://localhost:5173");
header("Access-Control-Allow-Methods: POST");
header("Access-Control-Allow-Headers: Content-Type, Authorization");
header("Access-Control-Allow-Credentials: true");
header("Access-Control-Max-Age: 3600");
header("Content-Type: application/json");
header("X-XSS-Protection: 1; mode=block");
header("X-Content-Type-Options: nosniff");
header(
    "Strict-Transport-Security: max-age=31536000; includeSubDomains; preload"
);

$userInfo = [
    'username' => 'John Doe',
    'email' => 'john.doe@example.com',
    'phone' => '1234567890',
    'address' => '1234 Elm Street, Springfield, IL'
];

$productInfo = [
    [
        'name' => 'DKKP',
        'price' => '5000000',
        'quantity' => '3',
        'total' => '15000000'
    ],
    [
        'name' => 'DKKP',
        'price' => '5000000',
        'quantity' => '3',
        'total' => '15000000'
    ]
];

$data = [
    'userInfo' => $userInfo,
    'productInfo' => $productInfo
];

echo json_encode($data);