import { Link } from "@remix-run/react";
import { Carousel } from "../components/Carousel";
import { Slide } from "../components/Slide";

import { useLoaderData } from "@remix-run/react";

import type { LoaderFunction } from "@remix-run/node";
import axios from "axios";



interface Product {
  id: string;
  img: string;
  name: string;
  price: number;
  discount: number;
  disPrice?: number;
}

// Định nghĩa kiểu dữ liệu phù hợp với backend trả về
interface Brand {
  ID_BRAND: number;
  NAME_BRAND: string;
  DETAIL: string;
}

interface Category {
  ID_CATEGORY: number;
  NAME_CATEGORY: string;
}

interface ProductFinal {
  ID_SP: number;
  NAME_PRODUCT: string;
  PRICE_SP: number;
  DISCOUNT?: number;
  QUANTITY?: number;
  DES_PRODUCT?: string;
  IMAGE_SP?: string;
  ID_BASE_PRODUCT?: number;
  NAME_PRODUCT_BASE?: string;
}

interface MainDTO {
  brands: Brand[];
  categories: Category[];
  products: ProductFinal[];
  productsAll: ProductFinal[];
}

// Fetch dữ liệu từ API Spring Boot
// Định nghĩa loader function
export const loader: LoaderFunction = async () => {
  try {
    // Gửi yêu cầu đến API backend
    const response = await axios.get<MainDTO>("http://localhost:8080/api/index/homepage");

    // Lấy dữ liệu từ response
    const data: MainDTO = response.data;

    // Trả về JSON
    return new Response(JSON.stringify(data), {
      headers: { "Content-Type": "application/json" },
    });
  } catch (error) {
    // Trả về lỗi nếu xảy ra
    console.error("Error fetching data from backend:", error);

    return new Response(
        JSON.stringify({ error: "Failed to fetch data from backend!" }),
        { status: 500, headers: { "Content-Type": "application/json" } }
    );
  }
};

export default function Homepage() {
  // Lấy dữ liệu từ loader
  const { brands, categories, products, productsAll } = useLoaderData<MainDTO>();

  return (
      <div>
        <h1>Homepage</h1>

        <section>
          <h2>Brands</h2>
          <ul>
            {brands.map((brand) => (
                <li key={brand.ID_BRAND}>
                  {brand.NAME_BRAND} - {brand.DETAIL}
                </li>
            ))}
          </ul>
        </section>

        <section>
          <h2>Categories</h2>
          <ul>
            {categories.map((category) => (
                <li key={category.ID_CATEGORY}>{category.NAME_CATEGORY}</li>
            ))}
          </ul>
        </section>

        <section>
          <h2>Top Discounted Products</h2>
          <ul>
            {products.map((product) => (
                <li key={product.ID_SP}>
                  {product.NAME_PRODUCT} - {product.DISCOUNT ? product.DISCOUNT * 100 : 0}% off
                </li>
            ))}
          </ul>
        </section>

        <section>
          <h2>All Products</h2>
          <ul>
            {productsAll.map((product) => (
                <li key={product.ID_SP}>
                  {product.NAME_PRODUCT} - ${product.PRICE_SP} (Stock: {product.QUANTITY || 0})
                </li>
            ))}
          </ul>
        </section>
      </div>
  );
}

// export default function Index() {
//   const img = [
//     "app/IMG/DKKP_00.png",
//     "app/IMG/DKKP_02.png",
//     "app/IMG/DKKP_04.png",
//   ];
//   const hs: Product[] = [
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 10,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 20,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 40,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 20,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 10,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 5,
//     },
//   ];
//   hs.forEach((item) => {
//     item.disPrice = item.price * (1 - item.discount / 100);
//   });
//   const phone: Product[] = [
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 10,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 20,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 40,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 20,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 10,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 5,
//     },
//   ];
//   phone.forEach((item) => {
//     item.disPrice = item.price * (1 - item.discount / 100);
//   });
//   const laptop: Product[] = [
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 0,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 0,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 0,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 0,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 0,
//     },
//     {
//       id: "dkkp",
//       img: "app/IMG/DKKP_00.png",
//       name: "DKKP",
//       price: 9000000,
//       discount: 0,
//     },
//   ];
//   laptop.forEach((item) => {
//     item.disPrice = item.price * (1 - item.discount / 100);
//   });
//   return (
//     <main className="min-h-screen bg-black">
//       <div className="w-4/5 py-20 m-auto">
//         <Carousel img={img} />
//         <div className="h-[25vw] bg-red-100 border-y-2 border-red-500 rounded mt-16">
//           <div className="h-1/5 text-red-900 text-2xl flex justify-center items-center">
//             <i className="fa-light fa-fire m-2 text-2xl"></i>HOT SALES
//             <i className="fa-light fa-fire m-2 text-2xl"></i>
//           </div>
//           <div className="h-4/5">
//             <Slide products={hs} />
//           </div>
//         </div>
//         <div className="h-[25vw] bg-slate-950 border-y-2 border-purple-500 rounded mt-16">
//           <div className="h-1/5 flex">
//             <div className="w-1/5 h-full text-white text-2xl flex justify-center items-center">
//               <i className="fa-light fa-mobile text-2xl m-2"></i>PHONE
//             </div>
//             <div className="w-4/5 h-full flex justify-end items-center px-20">
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/apple"}
//               >
//                 Apple
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/samsung"}
//               >
//                 Samsung
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/xiaomi"}
//               >
//                 Xiaomi
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/huawei"}
//               >
//                 Huawei
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/oppo"}
//               >
//                 Oppo
//               </Link>
//             </div>
//           </div>
//           <div className="h-4/5">
//             <Slide products={phone} />
//           </div>
//         </div>
//         <div className="h-[25vw] bg-slate-950 border-y-2 border-purple-500 rounded mt-16">
//           <div className="h-1/5 flex">
//             <div className="w-1/5 h-full text-white text-2xl flex justify-center items-center">
//               <i className="fa-light fa-laptop text-2xl m-2"></i>LAPTOP
//             </div>
//             <div className="w-4/5 h-full flex justify-end items-center px-20">
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/acer"}
//               >
//                 Acer
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/asus"}
//               >
//                 Asus
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/dell"}
//               >
//                 Dell
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/hp"}
//               >
//                 HP
//               </Link>
//               <Link
//                 className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
//                 to={"/msi"}
//               >
//                 MSI
//               </Link>
//             </div>
//           </div>
//           <div className="h-4/5">
//             <Slide products={laptop} />
//           </div>
//         </div>
//       </div>
//     </main>
//   );
// }