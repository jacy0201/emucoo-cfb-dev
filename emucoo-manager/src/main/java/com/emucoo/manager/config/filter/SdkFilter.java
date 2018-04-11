package com.emucoo.manager.config.filter;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.apache.catalina.util.ParameterMap;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.emucoo.common.util.GetRequestJsonUtils;
import com.xiaoleilu.hutool.util.StrUtil;

public class SdkFilter implements Filter {

	@Override
	public void destroy() {
		
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		System.out.println("[" + this.getClass() + "]Filter run...");
		try
		{
			String json = GetRequestJsonUtils.getRequestJsonString((HttpServletRequest)arg0);
			
			if(StrUtil.isNotEmpty(json))
			{
				ObjectMapper mapper = new ObjectMapper(); //转换器
				Map m = mapper.readValue(json, Map.class);
				
				m.put("data", mapper.writeValueAsString(m.get("Data")));
				m.remove("Data");
				
				ParameterRequestWrapper newRequest = new SdkFilter.ParameterRequestWrapper((HttpServletRequest)arg0,m);
				
				String cmd = m.get("Cmd")!=null?m.get("Cmd").toString():"";
				
				if(StrUtil.isNotEmpty(cmd))
				{
					String url = "/" + (cmd.contains(".")?cmd.replaceAll("\\.", "/"):cmd);
					arg0.getRequestDispatcher(url).forward(newRequest, arg1);
				}		
			}		
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		arg2.doFilter(arg0, arg1);
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		
	}
	
	public class SdkParams
	{
		private String Version;
		private String Cmd;
		private String PageType;
		private String RespCode;
		private String RespMsg;
		private String Sign;
		private Object Data;
		
		public String getVersion() {
			return Version;
		}
		public void setVersion(String version) {
			Version = version;
		}
		public String getCmd() {
			return Cmd;
		}
		public void setCmd(String cmd) {
			Cmd = cmd;
		}
		public String getPageType() {
			return PageType;
		}
		public void setPageType(String pageType) {
			PageType = pageType;
		}
		public String getRespCode() {
			return RespCode;
		}
		public void setRespCode(String respCode) {
			RespCode = respCode;
		}
		public String getRespMsg() {
			return RespMsg;
		}
		public void setRespMsg(String respMsg) {
			RespMsg = respMsg;
		}
		public String getSign() {
			return Sign;
		}
		public void setSign(String sign) {
			Sign = sign;
		}
		public Object getData() {
			return Data;
		}
		public void setData(Object data) {
			Data = data;
		}
		
	}
	
	class ParameterRequestWrapper extends HttpServletRequestWrapper  {
		
		private ParameterMap<String, String[]> params;
		
		@SuppressWarnings("all")
		public ParameterRequestWrapper(HttpServletRequest request, Map m) {
			super(request);
			params=(ParameterMap)request.getParameterMap();
			addParameterAll(m);
		}

		@Override
		public String getParameter(String name) {
			String[] values = params.get(name);
	        if(values == null || values.length == 0) {
	            return null;
	        }
	        return values[0];
		}
		
		@Override
		public Map<String, String[]> getParameterMap() {
			return params;
		}
		
		public void addParameter(String name , Object value) {
			//增加参数
			if(value != null) {
	        	params.setLocked(false);
	            if(value instanceof String[]) {
	                params.put(name , (String[])value);
	            }else if(value instanceof String) {
	                params.put(name , new String[] {(String)value});
	            }else {
	                params.put(name , new String[] {String.valueOf(value)});
	            }
	            params.setLocked(true);
	        }
	    }
		
		@SuppressWarnings("rawtypes")
		public void addParameterAll(Map m)
		{
			if(m!=null&&m.size()>0)
			{
				for(Object key:m.keySet())
				{
					this.addParameter(key.toString(), m.get(key));
				}
			}
		}
	}

}
