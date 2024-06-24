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
  const code = new URL(window.location.href).searchParams.get("code");
  const grant_type = "authorization_code";
  const client_id = import.meta.env.VITE_KAKAO_REST_API_KEY;
  const redirect_uri = import.meta.env.VITE_KAKAO_REDIRECT_URI;

  useEffect(() => {
    // 백으로 보내는 api 호출.
    // axios
    //   .post("/user/oauth/kakao", {
    //     code: code,
    //   })
    //   .then((res) => {
    //     console.log(res);
    //   })
    //   .catch((err) => {
    //     console.error(err);
    //   });
    // 프론트에서 테스트해본 코드
    const oauthUrl = `https://kauth.kakao.com/oauth/token?grant_type=${grant_type}&client_id=${client_id}&redirect_uri=${redirect_uri}&code=${code}`;
    axios
      .post(
        oauthUrl,
        {},
        {
          headers: {
            "Content-Type": "application/X-WWW-form-urlencoded",
          },
        }
      )
      .then((res) => {
        console.log(res);
        const { data } = res;
        const { access_token } = data;
        console.log("access_token", access_token);
        if (access_token) {
          axios
            .post(
              "https:/kapi/kakao.com/v2/user/me",
              {},
              {
                headers: {
                  Authorization: `Bearer ${access_token}`,
                  "Content-Type": "application/X-WWW-form-urlencoded",
                },
              }
            )
            .then((res) => {
              console.log(res);
            });
        } else {
          console.log("access_token이 없습니다.");
        }
      });
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
