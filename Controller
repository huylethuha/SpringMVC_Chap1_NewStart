//CommonZipCodeController.java
package com.samsung.isbp.com.ui;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.eastern.sec.EtZipcode;
import com.samsung.core.IJSON;
import com.samsung.core.IJSONFactory;
import com.samsung.core.json.JSONSerializer;
import com.samsung.core.util.CommSearchVO;
import com.samsung.core.util.ViewUtils;
import com.samsung.isbp.com.CommonZipCodeService;
import com.samsung.isbp.com.entity.HassAdressVO;
import com.samsung.isbp.com.entity.ZipCodeImportVO;
import com.samsung.isbp.com.entity.ZipCodeVO;
import com.samsung.util.StringUtil;

import net.sf.json.JSONObject;




@Controller
public class CommonZipCodeController {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	 @Inject private CommonZipCodeService zipService;
	 
	 @Inject private IJSONFactory jsonFactory;


	/**
	 * 
	 * @MethodName : openZipCodePopup
	 * @Return : String
	 * @Desc : 우편번호 팝업
	 */
	@RequestMapping(value="/common/zipCodePopup.do")
	public ModelAndView openZipCodePopup(@ModelAttribute("search") CommSearchVO searchVO) throws Exception {

		Map<String,Object> model = new HashMap<String, Object>();
		//return "/common/zipcodePopup";
		
		return new ModelAndView("common/zipcodePopup",model);
	}
	
	@RequestMapping(value="/common/zipCodePopup2.do")
	public ModelAndView openZipCodePopup2(@ModelAttribute("search") CommSearchVO searchVO) throws Exception {

		Map<String,Object> model = new HashMap<String, Object>();
		//return "/common/zipcodePopup";
		
		return new ModelAndView("common/zipcodePopup2",model);

	}

    @RequestMapping(value="/common/getCityList.do" )
    public void getCitylistAjax(@ModelAttribute("search")ZipCodeImportVO zipVo, HttpServletResponse response) throws Exception {
    	
		
		try {
		
			Map<String , Object> pmap = new HashMap<String , Object>();
			
			pmap.put("modeId", zipVo.getModeId());
			
			if("2".equals(zipVo.getModeId())){
				pmap.put("mainCity", zipVo.getMainCity());	
			}else if ("4".equals(zipVo.getModeId())){
				pmap.put("mainCity", zipVo.getBdMainCity());
			}else if ("6".equals(zipVo.getModeId())){
				pmap.put("mainCity", zipVo.getOldMainCity());
			}else {
				
			}
			
			
			List<ZipCodeVO> results  = zipService.getCityList(pmap);

			
			ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			
			for(ZipCodeVO comm : results){
			
				Map<String, Object> data = new HashMap<String, Object>();
				
				if("1".equals(zipVo.getModeId())||"3".equals(zipVo.getModeId())||"5".equals(zipVo.getModeId())){
					
					data.put("city"    	, comm.getCommCd());
					data.put("citynm"    	, comm.getCommCd());
				
				}else {
					
					data.put("city"    	, comm.getCommNm());
					data.put("citynm"    	, comm.getCommNm());
				}
				
				datas.add(data);
				
			}
			
			IJSON json = jsonFactory.create();
			ViewUtils.jsonData(response, json,new JSONSerializer(true).serialize(datas));
			
		} catch (Throwable e) {
			ViewUtils.jsonFailure(response, e);
		}
		
    }
    
    
    
    @RequestMapping(value="/common/getZipCodeList.do" )
    public void getZipCodelistAjax(@ModelAttribute("search")ZipCodeImportVO zipVo, HttpServletResponse response) throws Exception {
		try {
			
			Map<String , Object> pmap = new HashMap<String , Object>();
			
			pmap.put("modeId", zipVo.getModeId());
			
			if("7".equals(zipVo.getModeId())){
				pmap.put("mainCity", zipVo.getMainCity());
				pmap.put("subCity", zipVo.getSubCity());
				pmap.put("roadNm", zipVo.getRoadNm());
				
				String[] bdNum = zipVo.getBuildNum().split("-");
				pmap.put("mBuildNum", bdNum[0]);
				
				if(bdNum.length>2) {
					pmap.put("sBuildNum", bdNum[1]);	
				}
				
			}else if ("8".equals(zipVo.getModeId())){
				
				pmap.put("mainCity", zipVo.getBdMainCity());
				pmap.put("subCity", zipVo.getBdSubCity());
				pmap.put("roadNm", zipVo.getBdRoadNm());
				pmap.put("buildNm", zipVo.getBdBuildNm());
				
			}else if ("9".equals(zipVo.getModeId())){
				
				pmap.put("mainCity", zipVo.getOldMainCity());
				pmap.put("subCity", zipVo.getOldSubCity());
				pmap.put("umdrNm", zipVo.getOldTown());
			}else {
				
			}
			
			
			List<ZipCodeVO> results  = zipService.getZipCodeList(pmap);
			
			
			ArrayList<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
			
			for(ZipCodeVO comm : results){
			
				
				Map<String, Object> jo = new HashMap<String, Object>();
									
					jo.put("zipcode"    	, comm.getCommCd());
					jo.put("address"    	, comm.getCommNm());
					
					jo.put("widenm"    	, comm.getWideNm());
					jo.put("citygu"    	, comm.getCityGuNm());
					jo.put("roadnm"    	, comm.getRoadNm());
				
					datas.add(jo);
				
			}
			
			
			IJSON json = jsonFactory.create();
			ViewUtils.jsonData(response, json,new JSONSerializer(true).serialize(datas));
			
		} catch (Throwable e) {
			ViewUtils.jsonFailure(response, e);
		}
		
    }

    
    @RequestMapping(value="/common/getRefineAddress.do" )
    public void getRefineAddressAjax(@ModelAttribute("search")ZipCodeImportVO zipVo, HttpServletResponse response) throws Exception {
		try {
			
			
				EtZipcode ecam4j = new EtZipcode();
			
				
				ecam4j.putAddress(zipVo.getInAddrs()+" "+zipVo.getInAddrsDtl(),"","","","","","","");
				
				
				Map<String, Object> jo = new HashMap<String, Object>();
				
					
					jo.put("zipCd"   	, ecam4j.getZipcode());
					jo.put("wideNm"    	, ecam4j.getAddrWideNm());
					jo.put("midNm"    	, ecam4j.getAddrMidNm());
					jo.put("strtNm"    	, StringUtil.null2str(ecam4j.getAddrEMD(), "")+" "+StringUtil.null2str(ecam4j.getAddrStreetNm(), ""));
					jo.put("mnAdrs"  	, ecam4j.getAddress1());
					jo.put("sbAdrs"   	, ecam4j.getAddress2());
					jo.put("oldAdrs"   	, ecam4j.getTransAddr1()+" "+ecam4j.getTransAddr2());
					jo.put("reFineYn"  	, ecam4j.getCL_SUCCESS());
					jo.put("reTransYn"  	, ecam4j.getTR_SUCCESS());

					
					
					IJSON json = jsonFactory.create();
					ViewUtils.jsonData(response, json,new JSONSerializer(true).serialize(jo));
			
		} catch (Throwable e) {
			ViewUtils.jsonFailure(response, e);
		}
		

    }    
    
	@RequestMapping(value="/common/zipCodeList2.do")
	public @ResponseBody void getZipCodeList(@RequestBody Map<String, Object> searchParam,
			HttpServletResponse response, HttpServletRequest request) throws Exception {
		Map<String, Object> rs = new HashMap<>();
		JSONObject json = new JSONObject();
		try {
			rs = zipService.getAddrApi(searchParam);
			
			json.put("errorCode", rs.get("errorCode"));
			json.put("errorMessage", rs.get("errorMessage"));
			json.put("totalCount", rs.get("totalCount"));
			json.put("countPerPage", rs.get("countPerPage"));
			json.put("currentPage", rs.get("currentPage"));
			json.put("data", rs.get("data"));
			
		}catch(Exception er) {
			ViewUtils.jsonFailure(response, er.getMessage());
		}finally {
			response.setContentType("application/json; charset=UTF-8");
			PrintWriter outModelPol01 = response.getWriter();
			outModelPol01.print(json);
			outModelPol01.flush();
			outModelPol01.close(); 
		}
		ViewUtils.jsonSuccess(response, "text/plain", null, "Success");

	}
    
}
