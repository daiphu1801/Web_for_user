import { LoaderFunction } from "@remix-run/node";
import { Link, useLoaderData } from "@remix-run/react";
import { useMemo } from "react";

interface Product {
  id: string;
  img: string;
  name: string;
  price: number;
  discount: number;
  disPrice?: number;
}

interface URL {
  menu: string;
}

const Menu = [
  "apple",
  "samsung",
  "xiaomi",
  "huawei",
  "oppo",
  "acer",
  "asus",
  "dell",
  "hp",
  "msi",
  "phone",
  "laptop"
];

export const loader: LoaderFunction = async ({ params }) => {
  const { menu } = params;

  if (!menu || !Menu.includes(menu)) {
    throw new Response("Not Found", { status: 404 });
  }

  return new Response(JSON.stringify({ menu }), {
    headers: { "Content-Type": "application/json" },
  });
};

export default function Brand() {
  const { menu }: URL = useLoaderData();
  const total = 100;
  const limit = 10;
  let page = 5;
  const pd: Product[] = [
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 10,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 20,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 40,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 20,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 10,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 5,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 10,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 20,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 40,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 20,
    },
  ];
  pd.forEach((item) => {
    item.disPrice = item.price * (1 - item.discount / 100);
  });
  const sumPages = Math.ceil(total / limit);
  const pagination = useMemo(() => {
    const pages = [];
    if (sumPages > 1) {
      if (page > 1) pages.push(page - 1);
      pages.push(page);
      if (page < sumPages) pages.push(page + 1);
    }
    return [...new Set(pages)];
  }, [sumPages, page]);
  return (
    <main className="text-white bg-black pt-20 relative">
      <div className="w-4/5 min-h-screen bg-slate-950 border-2 border-purple-500 rounded-2xl mt-8 mx-auto">
        <div className="w-11/12 border-b-2 border-purple-500 py-10 m-auto">
          <div className="text-4xl before:content-[''] before:grow before:border-b-2 before:border-purple-500 after:content-[''] after:grow after:border-b-2 after:border-purple-500 flex justify-center items-center">
            {menu.toUpperCase()}
          </div>
          <div className="grid grid-cols-5 gap-4 pt-4 overflow-auto">
            {pd.map((product, index) => (
              <div
                key={index}
                className="h-[15vw] text-black border-2 border-purple-500 rounded-xl overflow-hidden"
              >
                <Link to={`/${product.id}`}>
                  <div className="h-2/3 border-b-2 border-purple-500 overflow-hidden">
                    <img
                      src={product.img}
                      alt={product.name}
                      className="w-full h-full"
                    />
                  </div>
                  <div className="h-1/3 bg-purple-200">
                    <div className="text-purple-500 text-xl px-2">
                      {product.name}
                    </div>
                    {product.price == product.disPrice ? (
                      <span className="text-red-500 px-2">
                        {product.price.toLocaleString("vi-VN")}
                      </span>
                    ) : (
                      <div>
                        <span className="text-red-500 px-2">
                          {product.disPrice?.toLocaleString("vi-VN")}
                        </span>
                        <span className="line-through px-2">
                          {product.price.toLocaleString("vi-VN")}
                        </span>
                      </div>
                    )}
                  </div>
                </Link>
              </div>
            ))}
          </div>
          <div aria-live="polite" className="flex justify-center my-4">
            {page > 1 && (
              <div className="mx-2">
                <Link to={`/brnd/page=1`}>
                  <i className="fa-light fa-chevrons-left mr-4"></i>
                </Link>
                <Link to={`/brnd/page=${page - 1}`}>
                  <i className="fa-light fa-arrow-left"></i>
                </Link>
              </div>
            )}
            {pagination.map((num, index) => (
              <div key={index} className="mx-2">
                <Link
                  to={`/brnd/page=${num}`}
                  className={`p-2 rounded ${page === num ? "bg-purple-500" : ""
                    }`}
                >
                  {num}
                </Link>
              </div>
            ))}
            {page < sumPages && (
              <div className="mx-2">
                <Link to={`/brnd/page=${page + 1}`}>
                  <i className="fa-light fa-arrow-right"></i>
                </Link>
                <Link to={`/brnd/page=${sumPages}`}>
                  <i className="fa-light fa-chevrons-right ml-4"></i>
                </Link>
              </div>
            )}
          </div>
        </div>
      </div>
    </main>
  );
}