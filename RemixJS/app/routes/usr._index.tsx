import { useNavigate } from "@remix-run/react";
import { useState } from "react";

export default function Account() {
  const navigate = useNavigate();
  const [userInfo, setUserInfo] = useState<{
    username: string;
    email: string;
    phone: number;
    address: string;
  } | null>(null);
  const [productInfo, setProductInfo] = useState<
    Array<{
      name: string | null;
      price: number | null;
      quantity: number | null;
      total: number | null;
    }>
  >([]);
  const handleLogout = () => {
    navigate('/login');
  };
  return (
    <main className="text-white bg-black pt-20 relative">
      <div className="w-4/5 min-h-screen bg-slate-950 border-2 border-purple-500 rounded-2xl mt-8 mx-auto">
        <div
          className="text-center text-4xl before:content-[''] before:grow before:m-auto before:border-b-2 before:border-purple-500 after:content-[''] after:grow after:m-auto after:border-b-2 after:border-purple-500 flex justify-center items-center"
        >
          <i className="fa-light fa-circle-user text-8xl p-4"></i>
        </div>
        <div className="w-1/2 m-auto">
          <h2 className="text-center text-2xl border-2 border-purple-500 mt-10">
            My Info
          </h2>
          {userInfo && (
            <div className="h-fit border-2 border-purple-500 p-4">
              <div className="w-full float-left">
                Username:<span className="float-right">{userInfo.username}</span>
              </div>
              <div className="w-full float-left">
                Email:<span className="float-right">{userInfo.email}</span>
              </div>
              <div className="w-full float-left">
                Phone Number:<span className="float-right">{userInfo.phone}</span>
              </div>
              <div className="w-full float-left">
                Address:<span className="float-right">{userInfo.address}</span>
              </div>
            </div>
          )}
          <div className="w-full flex justify-center items-center">
            <button
              onClick={handleLogout}
              className="border-2 border-purple-500 my-2 px-2 hover:bg-red-200"
            >
              Logout
            </button>
          </div>
        </div>
        <div
          className={
            "text-center text-4xl before:content-[''] before:grow before:m-auto before:border-b-2 before:border-purple-500 after:content-[''] after:grow after:m-auto after:border-b-2 after:border-purple-500 flex justify-center items-center my-10"
          }
        >
          <i className="fa-solid fa-cart-shopping text-8xl p-4"></i>
        </div>
        {productInfo[0]?.name !== null ? (
          <table
            className={
              "w-full mb-4 border-[1px] border-solid border-slate-900 border-collapse"
            }
          >
            <thead>
              <tr className={"flex bg-green-200"}>
                <td
                  className={
                    "border-[1px] border-solid border-slate-900 max-w-[60%] flex-[0_0_60%] flex justify-center items-center"
                  }
                >
                  Purchased Products
                </td>
                <td
                  className={
                    "border-[1px] border-solid border-slate-900 max-w-[10%] flex-[0_0_10%] flex justify-center items-center"
                  }
                >
                  Quantity
                </td>
                <td
                  className={
                    "border-[1px] border-solid border-slate-900 max-w-[15%] flex-[0_0_15%] flex justify-center items-center"
                  }
                >
                  Price
                </td>
                <td
                  className={
                    "border-[1px] border-solid border-slate-900 max-w-[15%] flex-[0_0_15%] flex justify-center items-center"
                  }
                >
                  Total
                </td>
              </tr>
            </thead>
            {productInfo.map((pddt) => (
              <tbody key={pddt.name}>
                <tr className={"flex"}>
                  <td
                    className={
                      "border-[1px] border-solid border-slate-900 max-w-[60%] flex-[0_0_60%] px-4"
                    }
                  >
                    {pddt.name}
                  </td>
                  <td
                    className={
                      "border-[1px] border-solid border-slate-900 max-w-[10%] flex-[0_0_10%] flex justify-center items-center"
                    }
                  >
                    {pddt.quantity}
                  </td>
                  <td
                    className={
                      "border-[1px] border-solid border-slate-900 max-w-[15%] flex-[0_0_15%] flex justify-center items-center"
                    }
                  >
                    {pddt.price}$
                  </td>
                  <td
                    className={
                      "border-[1px] border-solid border-slate-900 max-w-[15%] flex-[0_0_15%] flex justify-center items-center"
                    }
                  >
                    {pddt.total}$
                  </td>
                </tr>
              </tbody>
            ))}
          </table>
        ) : (
          <div className="flex justify-center items-center">
            <i className="fa-light fa-face-sad-sweat text-2xl mx-2"></i>
            <p className="text-2xl">You Have Not Purchased Anything Yet!</p>
            <i className="fa-light fa-face-sad-sweat text-2xl mx-2"></i>
          </div>
        )}
      </div>
    </main>
  );
}