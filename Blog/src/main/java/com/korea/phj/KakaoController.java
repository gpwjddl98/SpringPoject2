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
				+ "?client_id=발급받은 키값 입력하기"
				+ "&redirect_uri=본인이 지정해둔 경로 입력하기"
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
        	 // 1.졀대 경로를 이용하여 url 객체 생성
            URL url = new URL(reqURL);
            // 2. 요청하고자 하는 URL과 통신하기 위한 Connection객체 생성.
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            // URL연결은 입출력에 사용 될 수 있고, POST 혹은 PUT 요청을 하려면 setDoOutput을 true로 설정해야함.
            //3.통신을 위한 메소드 전송방식 post로설정 
            conn.setRequestMethod("POST");
            /// OutputStream으로 POST 데이터를 넘겨주겠다는 옵션.
            conn.setDoOutput(true);
            //conn.setRequestProperty("Content-type", "application/json"); 
            //타입설정(application/json) 형식으로 전송 (Request Body 전달시 application/json로 서버에 전달.)
            //등 여러 옵션들이 있다 위에 글은 예시

            //전달받은 데이터를 BufferedWriter에 저장
            //:System.out.println();과 유사함
            //OutputStrean ot = conn.OutputStrean();으로 작성하지 않고 바로  new OutputStreamWriter(conn.getOutputStream()) 으로 작성함
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            //4.저장된 데이터를 라인별로 읽어 StringBuilder객체로 한개씩 append해준다.
            //5.append 된 sb를 출력할때는 toString을 해줘야 한다.
            //6.sb.toString 을 
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=본인이 발급받은 키값을 입력하기");
            sb.append("&redirect_uri=본인이 지정해둔 경로를 입력하기");
            sb.append("&code=" + authorize_code);
            bw.write(sb.toString());
            //BufferedWriter를 사용하고 반드시 flush 와 아래 close를 해줘야 한다.
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

        
        if(result == null) {
        	kakaodao.kakaoinsert(userInfo);
        	return kakaodao.findkakao(userInfo); 
        }else {
        	return result;
        }
      
    }
    
    
 }
