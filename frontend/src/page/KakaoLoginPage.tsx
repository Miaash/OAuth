import styled from "styled-components";
import kakaoBtn from "../assets/images/contents/btn-kakao.png";

const KaKaoLoginPage = () => {
  const kakaoConfig = {
    auth_url: import.meta.env.VITE_KAKAO_AUTH_URL,
    redirect_uri: import.meta.env.VITE_KAKAO_REDIRECT_URI,
    client_id: import.meta.env.VITE_KAKAO_REST_API_KEY,
  };
  const kakaoAUthUrl = `${kakaoConfig.auth_url}response_type=code&client_id=${kakaoConfig.client_id}&redirect_uri=${kakaoConfig.redirect_uri}`;

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
