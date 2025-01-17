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

$products = [
    "dkkp" => [
        "id" => "dkkp",
        "img" => "image1.jpg",
        "name" => "Product 1",
        "description" => "DKKP",
        "optionValue" => "256GB - Black",
        "attributeName" => "RAM",
        "attributeValue" => "8GB",
        "price" => 10.99,
        "discount" => 5
    ]
];

$id = $_GET['id'] ?? null;

if ($id && isset($products[$id])) {
    echo json_encode($products[$id]);
} else {
    echo json_encode(['error' => 'Product Not Found!']);
}