import { Form } from "@remix-run/react";
import { useState } from "react";
import { Input } from "../components/ui/input"
import { Button } from "~/components/ui/button";
import { Textarea } from "~/components/ui/textarea";

export default function Product() {
  const [name, setName] = useState('');
  const [feedback, setFeedback] = useState('');
  return (
    <main className="text-white bg-black pt-20 relative">
      <div className="w-4/5 min-h-screen bg-slate-950 border-2 border-purple-500 rounded-2xl mt-8 mx-auto">
        <div className="text-4xl before:content-[''] before:grow before:border-b-2 before:border-purple-500 after:content-[''] after:grow after:border-b-2 after:border-purple-500 flex justify-center items-center p-4">
          PRODUCT
        </div>
        <div className="h-[25vw] flex">
          <div className="w-1/2 flex justify-center items-center px-4">
            <img src="app/IMG/DKKP_00.png" alt="" className="w-full h-5/6 border-2 border-purple-500 rounded-2xl" />
          </div>
          <div className="w-1/2 flex justify-center items-center px-4">
            <div className="w-full h-5/6 bg-white border-2 border-purple-500 rounded-2xl">
              <div className="h-1/4 flex justify-center items-center">
                <span className="text-2xl text-purple-500 border-b-2 border-purple-500 p-2">Samsung Galaxy S24</span>
              </div>
              <div className="h-1/2 text-purple-500 flex p-4">
                <button className="h-fit border-2 border-purple-500 rounded-xl p-2 m-2">256GB - Gold</button>
                <button className="h-fit border-2 border-purple-500 rounded-xl p-2 m-2">256GB - Black</button>
                <button className="h-fit border-2 border-purple-500 rounded-xl p-2 m-2">512GB - Titanium</button>
              </div>
              <div className="h-1/4 border-t-2 border-dashed border-purple-500 flex justify-center items-center">
                <div>
                  <span className="text-3xl text-red-500 px-2">2.000.000</span>
                  <span className="line-through text-xl text-black px-2">4.000.000</span>
                </div>
              </div>
            </div>
          </div>
        </div>
        <div className="min-h-[25vw] flex">
          <div className="w-3/4 flex justify-center items-center px-4">
            <div className="w-full h-5/6 text-xl text-purple-500 bg-white border-2 border-purple-500 rounded-2xl p-4">Description</div>
          </div>
          <div className="w-1/4 flex justify-center items-center px-4">
            <div className="w-full h-5/6 bg-white border-2 border-purple-500 rounded-2xl flex justify-center">
              <table
                className="w-11/12 text-purple-500 bg-white border-0.5 border-purple-500 border-collapse my-4"
              >
                <tbody>
                  <tr className="flex">
                    <td className="border border-purple-500 w-1/2 px-4">RAM</td>
                    <td className="border border-purple-500 w-1/2 px-4">8GB</td>
                  </tr>
                  <tr className="flex">
                    <td className="border border-purple-500 w-1/2 px-4">Storage</td>
                    <td className="border border-purple-500 w-1/2 px-4">512GB</td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
        <Form className="border-t-2 border-dashed border-purple-500 px-4 mb-4">
          <div>
            <label htmlFor="name" className="text-xl text-purple-500">
              Name
            </label>
            <Input
              type="text"
              id="name"
              className="w-1/4 text-purple-500 bg-white border-2 border-purple-500 rounded my-2"
              value={name}
              onChange={(e) => setName(e.target.value)}
              required
            />
          </div>
          <div>
            <label htmlFor="feedback" className="text-xl text-purple-500">
              Feedback
            </label>
            <Textarea
              id="feedback"
              className="w-1/4 text-purple-500 bg-white border-2 border-purple-500 rounded my-2"
              value={feedback}
              onChange={(e) => setFeedback(e.target.value)}
              required
            />
          </div>

          <div className="text-center">
            <Button
              type="submit"
              className="px-2 text-purple-500 bg-white border-2 border-purple-500"
            >
              Submit
            </Button>
          </div>
        </Form>
      </div>
    </main>
  )
}