//CommonZipCodeServiceImpl.java

package com.samsung.isbp.com.impl;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.google.gson.JsonObject;
import com.samsung.isbp.com.CommonZipCodeService;
import com.samsung.isbp.com.entity.ZipCodeVO;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
/**
 *
 * @클래스명 : CommonZipCodeServiceImpl.java
 * @작성자 : luckyjong
 * @작성일 : 2018. 4. 24.
 * @설명 :
 * @수정이력
 * =====================================================================================
 * 	변경일		|	작성자		| 변경내용
 * =====================================================================================
 * 	2018. 4. 24.		luckyjong			최초 생성
 */
@Service
public class CommonZipCodeServiceImpl implements CommonZipCodeService {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Inject private CommonMainDao dao;

	@Value("#{restProperties['juso.rest.address.preFix']}")
	private String jusoHost;
	
	@Value("#{restProperties['juso.rest.service']}")
	private String jusoEndpoint;
	
	@Value("#{restProperties['juso.rest.confmKey']}")
	private String jusoConfmKey;


	@Override
	public List<ZipCodeVO> getCityList(Map<String, Object> prms) {

		String queryName = "selectCityNameList";

		if("5".equals(prms.get("modeId"))||"6".equals(prms.get("modeId"))) {

			queryName = "selectOldCityNameList";
		}

		List<ZipCodeVO> list  = (List<ZipCodeVO>) dao.selectList(queryName,  prms);

		return list;


	}




	@Override
	public List<ZipCodeVO> getZipCodeList(Map<String, Object> prms) {
		// TODO Auto-generated method stub


	String queryName = "selectZipCodeList";

		if("9".equals(prms.get("modeId"))) {
			queryName = "selectOldZipCodeList";
		}

		List<ZipCodeVO> list  = (List<ZipCodeVO>) dao.selectList(queryName,  prms);

		return list;
	}




	@Override
	public Map<String, Object> getAddrApi(Map<String, Object> prms) {
		BufferedReader br = null;
		HttpURLConnection urlConnection = null;
		
		Map<String, Object> result = new HashMap<>();
		
		try {
		String keyword = (String) prms.get("keyword");
		String currentPage = (String) prms.get("currentPage");
		String countPerPage = (String) prms.get("countPerPage");
		String resultType = (String) prms.get("resultType");
		
		String apiUrl = jusoHost + jusoEndpoint + "?currentPage="+currentPage
				+"&countPerPage="+countPerPage+"&keyword="+URLEncoder.encode(keyword,"UTF-8")
				+"&confmKey="+jusoConfmKey+"&resultType="+resultType;
		
		urlConnection = (HttpURLConnection) new URL(apiUrl).openConnection();
		urlConnection.setRequestMethod("GET");
		urlConnection.setConnectTimeout(10000);
		urlConnection.setReadTimeout(10000);
		
		logger.info("## Part1 : Juso Request Start");
		
		br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));
		StringBuffer sb = new StringBuffer();
		String tempStr = null;
		while(true){ 
			tempStr = br.readLine();
			if(tempStr == null) break;
			sb.append(tempStr);	
		}
		
		logger.info("## Part2 : Juso Request End");
		
		JSONObject jo = JSONObject.fromObject(sb.toString());
		if(jo != null && jo.get("results") != null) {
			
			JSONObject joResult = JSONObject.fromObject(jo.get("results").toString());
			
			JSONObject msg = JSONObject.fromObject(joResult.get("common"));
			
			JSONArray arrData = JSONArray.fromObject(joResult.get("juso"));
			
			if(msg != null) {
				
				result.put("errorCode", msg.getString("errorCode"));
				result.put("errorMessage", msg.getString("errorMessage"));
				result.put("totalCount", msg.getString("totalCount"));
				result.put("countPerPage", msg.getString("countPerPage"));
				result.put("currentPage", msg.getString("currentPage"));
				
					if ("0".equals(msg.getString("errorCode"))) {
						// call success
						List<Map<String, Object>> rsDatas = new ArrayList<Map<String, Object>>();
						if (arrData != null) {
							for (int i = 0; i < arrData.size(); i++) {

								Map<String, Object> data = new HashMap<String, Object>();
								JSONObject obj = arrData.getJSONObject(i);

								data.put("zipCd", obj.getString("zipNo"));
								data.put("mnAdrs", obj.getString("roadAddr")); // full address
								data.put("wideNm", obj.getString("siNm")); // city
								data.put("midNm", obj.getString("sggNm")); // district
								data.put("emdNm", obj.getString("emdNm")); // eup/myeon/dong/gil
								data.put("strtNm", obj.getString("rn")); // road
								data.put("sbAdrs", !"0".equals(obj.getString("buldSlno"))
												? obj.getString("buldMnnm") + "-" + obj.getString("buldSlno")
												: obj.getString("buldMnnm")); // number building
								data.put("oldAdrs", "");
								data.put("reFineYn", "Y");
								data.put("reTransYn", "Y");

								rsDatas.add(data);
							}
						}
						result.put("data", rsDatas);
					} else {
						result.put("data", Collections.EMPTY_LIST);
					}
			}
			

			
		}
		
		}catch(Exception e) {
			logger.error(e.toString());
			result.put("errorCode", "505");
			result.put("errorMessage", e.getMessage());
			result.put("totalCount", "0");
			result.put("countPerPage", "0");
			result.put("currentPage", "0");
			result.put("data", Collections.EMPTY_LIST);
			return result;
		}finally {
			try {
				if (br != null) br.close();
				if(urlConnection != null) urlConnection.disconnect();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

}
