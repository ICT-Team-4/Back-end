package com.security.config.oauth;


import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;
import com.security.config.auth.PrincipalDetails;
import com.security.config.oauth.provider.FacebookUserInfo;
import com.security.config.oauth.provider.GoogleUserInfo;
import com.security.config.oauth.provider.KakaoUserInfo;
import com.security.config.oauth.provider.NaverUserInfo;
import com.security.config.oauth.provider.OAuth2UserInfo;
import com.security.model.UserDto;
import com.security.model.UserMapper;
import com.security.service.UserService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class PrincipalOauth2UserService extends DefaultOAuth2UserService {


  private PasswordEncoder passwordEncoder;
  private UserMapper usermapper;
  private UserService userservice;

  public PrincipalOauth2UserService(UserMapper usermapper, PasswordEncoder passwordEncoder, UserService userservice) {
    this.usermapper = usermapper;
    this.passwordEncoder = passwordEncoder;
    this.userservice = userservice;
  }

  // 소셜부터 받은 userRequest 데이터에 대한 후처리되는 함수
  @Override
  public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
    System.out.println("getClientRegistration:" + userRequest.getClientRegistration());// registrarionId로
                                                                                       // 어떤 OAuth로
                                                                                       // 로그인을 했는지
                                                                                       // 확인
    System.out.println("getAccessToken:" + userRequest.getAccessToken().getTokenValue());
    
    System.out.println("userRequest : "+userRequest);
    
    OAuth2User oauth2User = super.loadUser(userRequest);
    // 구글 로그인 버튼 클릭 -> 구글로그인 창 -> 로그인 완료 -> code를 리턴 (OAuth-Client라이브러리)->AccessToken요청
    // userRequest 정보 -> loadUser함수 호출-> 구글로부터 회원프로필 받아줌
    System.out.println("getAttributes:" + oauth2User.getAttributes());


    // 회원가입을 강제로 진행
    OAuth2UserInfo oAuth2UserInfo = null;
    if (userRequest.getClientRegistration().getRegistrationId().equals("google")) {
    	log.info("구글 로그인 요청");
      oAuth2UserInfo = new GoogleUserInfo(oauth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("facebook")) {
    	log.info("페이스북 로그인 요청");
      oAuth2UserInfo = new FacebookUserInfo(oauth2User.getAttributes());
    } else if (userRequest.getClientRegistration().getRegistrationId().equals("naver")) {
    	log.info("네이버 로그인 요청");
      oAuth2UserInfo = new NaverUserInfo((Map) oauth2User.getAttributes().get("response"));
    }else if (userRequest.getClientRegistration().getRegistrationId().equals("kakao")) {
        log.info("카카오 로그인 요청");
        Long longid= (Long)oauth2User.getAttributes().get("id");
        String id = longid.toString();
        oAuth2UserInfo = new KakaoUserInfo(oauth2User.getAttributes());
      } 
    else {
      System.out.println("우리는 구글, 페이스북, 네이버, 카카오만 지원합니다");
    }
    System.out.println("2");
    String provider = oAuth2UserInfo.getProvider();
    String providerId = oAuth2UserInfo.getProviderId();
    String username = provider + "_" + providerId;
    String password = passwordEncoder.encode(providerId);
    System.out.println("3");
    log.info("provider : " + provider);
    log.info("providerId : " + providerId);
    log.info("username : " + username);
    log.info("password : " + password);
    
    UserDto userEntity = usermapper.findAccountByProviderId(providerId);
    log.info("userEntity : " + userEntity);
    log.info("OAuth2UserInfo : " + oAuth2UserInfo.getProviderId());
    
    if (userEntity == null) {
        System.out.println("OAuth 로그인이 최초");
        userEntity = UserDto.builder().username(username).password(password)
            .provider(provider).providerId(providerId).build();
    
      userservice.insertSocial(userEntity);
      log.info("userEntity = " + userEntity);
    } else {
      System.out.println("로그인을 이미 한 적이있습니다. 당신은 자동 회원가입이 되어있습니다");
    }
    return new PrincipalDetails(userEntity, oauth2User.getAttributes());
  }
}
