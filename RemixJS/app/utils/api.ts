const BACKEND_URL = "http://localhost:8080";

// Fetch danh sách sản phẩm
export async function fetchProducts() {
    const response = await fetch(`${BACKEND_URL}/api/products`);
    if (!response.ok) {
        throw new Error("Failed to fetch products");
    }
    return response.json();
}

// Fetch thông tin người dùng
export async function fetchUser(userId: number) {
    const response = await fetch(`${BACKEND_URL}/api/users/${userId}`);
    if (!response.ok) {
        throw new Error("Failed to fetch user data");
    }
    return response.json();
}
