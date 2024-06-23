import axios from "axios";
import { useEffect } from "react";

/**
 * [카카오로그인 인증 후, redirect로 오게 될 컴포넌트]
 *
 * query string으로 받은 authorization code를 백으로 전송.
 *
 * @returns
 */
const LoginHandler = () => {
  const authCode = new URL(window.location.href).searchParams.get("code");
  useEffect(() => {
    // 백으로 보내는 api 호출.
    // axios
    //   .post("/user/oauth/kakao", {
    //     code: authCode,
    //   })
    //   .then((res) => {
    //     console.log(res);
    //   })
    //   .catch((err) => {
    //     console.error(err);
    //   });
  }, []);

  return (
    <div>
      <p>로그인 중입니다.</p>
      <p>잠시만 기다려주세요.</p>
      <p>로딩바</p>
    </div>
  );
};

export default LoginHandler;
