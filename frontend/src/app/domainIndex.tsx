"use client";

import Image from "next/image";
import "../styles/vars.css";
import "../styles/style.css";

export default function HomePage() {
    return (
        <div
            className="main"
            style={{
                background: 'url("/main.png") center',
                backgroundSize: "cover",
                backgroundRepeat: "no-repeat",
            }}
        >
            {/* 이미지들 */}
            <Image className="rectangle-1" src="/rectangle-10.png" alt="Rectangle 1" width={200} height={100} />
            <div className="line-1"></div>
            <div className="line-2"></div>
            <Image className="rectangle-2" src="/rectangle-20.png" alt="Rectangle 2" width={200} height={100} />

            {/* 텍스트 요소들 */}
            <div className="log-in">LOG IN</div>
            <div className="log-in">LOG IN</div>
            <div className="join">JOIN</div>
            <h1 className="welcome-to-life-in-seoul">Welcome to Life in Seoul</h1>
            <h2 className="let-s-share-it-with-us">Let's share it with us.</h2>
            <p className="description">
                The site introduces the culture, food, and entertainment of many people
                living in Seoul, the capital of Korea.
            </p>

            {/* 추가 이미지 */}
            <Image className="rectangle-4" src="/rectangle-40.png" alt="Rectangle 4" width={200} height={100} />
            <Image className="rectangle-5" src="/rectangle-50.png" alt="Rectangle 5" width={200} height={100} />
            <Image className="rectangle-6" src="/rectangle-60.png" alt="Rectangle 6" width={200} height={100} />
        </div>
    );
}