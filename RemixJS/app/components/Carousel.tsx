import React from "react";
import { Swiper, SwiperSlide } from "swiper/react";
import { Autoplay, Pagination, Navigation } from "swiper/modules";
import "swiper/css";
import "swiper/css/pagination";
import "swiper/css/navigation";

export const Carousel: React.FC<{ img: string[] }> = ({ img }) => {
  return (
    <Swiper
      centeredSlides={true}
      autoplay={{
        delay: 2500,
        disableOnInteraction: false,
      }}
      pagination={{
        clickable: true,
      }}
      navigation={true}
      modules={[Autoplay, Pagination, Navigation]}
      className="Carousel"
    >
      {img.map((image, index) => (
        <SwiperSlide key={index} className="border-b-2 border-purple-500">
          <img src={image} alt="" />
        </SwiperSlide>
      ))}
    </Swiper>
  );
};