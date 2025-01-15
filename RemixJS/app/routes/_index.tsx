import { Link } from "@remix-run/react";
import { Carousel } from "../components/Carousel";
import { Slide } from "../components/Slide";

interface Product {
  id: string;
  img: string;
  name: string;
  price: number;
  discount: number;
  disPrice?: number;
}

export default function Index() {
  const img = [
    "app/IMG/DKKP_00.png",
    "app/IMG/DKKP_02.png",
    "app/IMG/DKKP_04.png",
  ];
  const hs: Product[] = [
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
  ];
  hs.forEach((item) => {
    item.disPrice = item.price * (1 - item.discount / 100);
  });
  const phone: Product[] = [
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
  ];
  phone.forEach((item) => {
    item.disPrice = item.price * (1 - item.discount / 100);
  });
  const laptop: Product[] = [
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 0,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 0,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 0,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 0,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 0,
    },
    {
      id: "dkkp",
      img: "app/IMG/DKKP_00.png",
      name: "DKKP",
      price: 9000000,
      discount: 0,
    },
  ];
  laptop.forEach((item) => {
    item.disPrice = item.price * (1 - item.discount / 100);
  });
  return (
    <main className="min-h-screen bg-black">
      <div className="w-4/5 py-20 m-auto">
        <Carousel img={img} />
        <div className="h-[25vw] bg-red-100 border-y-2 border-red-500 rounded mt-16">
          <div className="h-1/5 text-red-900 text-2xl flex justify-center items-center">
            <i className="fa-light fa-fire m-2 text-2xl"></i>HOT SALES
            <i className="fa-light fa-fire m-2 text-2xl"></i>
          </div>
          <div className="h-4/5">
            <Slide products={hs} />
          </div>
        </div>
        <div className="h-[25vw] bg-slate-950 border-y-2 border-purple-500 rounded mt-16">
          <div className="h-1/5 flex">
            <div className="w-1/5 h-full text-white text-2xl flex justify-center items-center">
              <i className="fa-light fa-mobile text-2xl m-2"></i>PHONE
            </div>
            <div className="w-4/5 h-full flex justify-end items-center px-20">
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/apple"}
              >
                Apple
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/samsung"}
              >
                Samsung
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/xiaomi"}
              >
                Xiaomi
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/huawei"}
              >
                Huawei
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/oppo"}
              >
                Oppo
              </Link>
            </div>
          </div>
          <div className="h-4/5">
            <Slide products={phone} />
          </div>
        </div>
        <div className="h-[25vw] bg-slate-950 border-y-2 border-purple-500 rounded mt-16">
          <div className="h-1/5 flex">
            <div className="w-1/5 h-full text-white text-2xl flex justify-center items-center">
              <i className="fa-light fa-laptop text-2xl m-2"></i>LAPTOP
            </div>
            <div className="w-4/5 h-full flex justify-end items-center px-20">
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/acer"}
              >
                Acer
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/asus"}
              >
                Asus
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/dell"}
              >
                Dell
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/hp"}
              >
                HP
              </Link>
              <Link
                className="text-purple-900 bg-white border-2 border-purple-500 rounded px-2 mx-2"
                to={"/msi"}
              >
                MSI
              </Link>
            </div>
          </div>
          <div className="h-4/5">
            <Slide products={laptop} />
          </div>
        </div>
      </div>
    </main>
  );
}