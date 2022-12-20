package com.korea.phj;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import dao.BlogBoardDAO;
import dao.BlogBoardReplyDAO;
import dao.KaKaoDAO;
import vo.BlogBoardVO;
import vo.KakaoVO;



@Controller
public class KakaoController {
	
	
	public static final String VIEW_PATH = "/WEB-INF/views/";
	
	KaKaoDAO kakaodao;

	public void setkakaodao(KaKaoDAO kakaodao) {
		this.kakaodao = kakaodao;
	}
	
	

@RequestMapping("/getKakaoAuthUrl")
@ResponseBody String getKakaoAuthUrl(HttpServletRequest request) throws Exception {
		String reqUrl = 
				"https://kauth.kakao.com/oauth/authorize"
				+ "?client_id=37e286b9c406deaa2f6a4f035acc8f35"
				+ "&redirect_uri=http://localhost:9090/phj/oauth_kakao"
				+ "&response_type=code";
		
		System.out.println("getKakaoAuthUrl");
		
		return reqUrl;
		
		
	}
	
	// 카카오 연동정보 조회
	@RequestMapping("/oauth_kakao")
	public String oauthKakao(
			@RequestParam(value = "code", required = false) String code
			, Model model ,HttpServletRequest request) throws Exception {

		System.out.println("#########" + code);
        String access_Token = getAccessToken(code);
        System.out.println("###access_Token#### : " + access_Token);
        
        
        KakaoVO userInfo = getUserInfo(access_Token);
        System.out.println("###access_Token#### : " + access_Token);
        System.out.println("###nickname#### : " + userInfo.getK_name());
      
       
        HttpSession session = request.getSession();
        session.setAttribute("kakaoInfo", userInfo);
       

        return "redirect:/loginOk";//본인 원하는 경로 설정
	}
	
    //토큰발급
	public String getAccessToken (String authorize_code) throws ParseException {
        String access_Token = "";
        String refresh_Token = "";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try {
        	 // 졀대 경로를 이용하여 url 객체 생성
            URL url = new URL(reqURL);
           
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
     

            // URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //	POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=37e286b9c406deaa2f6a4f035acc8f35");  //본인이 발급받은 key
            sb.append("&redirect_uri=http://localhost:9090/phj/oauth_kakao");     // 본인이 설정해 놓은 경로
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            bw.flush();

            //    결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            //    요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);
			

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        return access_Token;
    }
	
    //유저정보조회
    public KakaoVO getUserInfo (String access_Token) throws ParseException {

        //    요청하는 클라이언트마다 가진 정보가 다를 수 있기에 HashMap타입으로 선언
        HashMap<String, Object> userInfo = new HashMap<String, Object>();
        String reqURL = "https://kapi.kakao.com/v2/user/me";
        try {
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            //    요청에 필요한 Header에 포함될 내용
            conn.setRequestProperty("Authorization", "Bearer " + access_Token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            JsonParser parser = new JsonParser();
            JsonElement element =  parser.parse(result);
            JsonObject properties = element.getAsJsonObject().get("properties").getAsJsonObject();
            JsonObject kakao_account = element.getAsJsonObject().get("kakao_account").getAsJsonObject();

            String nickname = properties.getAsJsonObject().get("nickname").getAsString();
        
            userInfo.put("accessToken", access_Token);
            userInfo.put("nickname", nickname);
           

        } catch ( IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        
        KakaoVO result = kakaodao.findkakao(userInfo);
        System.out.println("잘저장되어있는가? : " + result);
        
        if(result == null) {
        	kakaodao.kakaoinsert(userInfo);
        	return kakaodao.findkakao(userInfo); 
        }else {
        	return result;
        }
      
    }
    
    
 }
