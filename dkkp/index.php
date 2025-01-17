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

session_start();
$hotsales = [
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 10
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 20
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 40
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 20
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 10
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 5
    ]
];

$phone = [
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 10
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 20
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 40
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 20
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 10
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 5
    ]
];

$laptop = [
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 10
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 20
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 40
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 20
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 10
    ],
    [
        'id' => 'dkkp',
        'img' => 'app/IMG/DKKP_00.png',
        'name' => 'DKKP',
        'price' => 9000000,
        'discount' => 5
    ]
];

$data = [
    'hotsales' => $hotsales,
    'phone' => $phone,
    'laptop' => $laptop
];

echo json_encode($data);