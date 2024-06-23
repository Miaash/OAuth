import styled from "styled-components";
import kakaoBtn from "../assets/images/contents/btn-kakao.png";

const KaKaoLoginPage = () => {
  const kakaoConfig = {
    authUrl: import.meta.env.VITE_KAKAO_AUTH_URL,
    redirectUri: import.meta.env.VITE_KAKAO_REDIRECT_URI,
    javascriptKey: import.meta.env.VITE_KAKAO_JAVASCRIPT_KEY,
    restApiKey: import.meta.env.VITE_KAKAO_REST_API_KEY,
  };
  const kakaoAUthUrl = `${kakaoConfig.authUrl}response_type=code&client_id=${kakaoConfig.restApiKey}&redirect_uri=${kakaoConfig.redirectUri}`;

  return (
    <Container>
      <BtnBox>
        <Link href={kakaoAUthUrl}>
          <Img src={kakaoBtn} alt="카카오로그인" />
        </Link>
      </BtnBox>
      <Content>
        {/* 받아온 userData : {} */}
        받아온 userData 영역 :
      </Content>
    </Container>
  );
};

export default KaKaoLoginPage;

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
`;

const BtnBox = styled.div`
  display: flex;
`;

const Link = styled.a``;

const Img = styled.img`
  width: 230px;
  height: 50px;
  cursor: pointer;
`;

const Content = styled.div`
  margin-top: 100px;
`;
