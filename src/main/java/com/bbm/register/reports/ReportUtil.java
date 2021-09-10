package com.bbm.register.reports;

import java.io.Serializable;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

@Component
public class ReportUtil implements Serializable{

	
	private static final long serialVersionUID = 1L;

	//retorna o pdf em bytes para download no navegador
	public byte[] geraRelatorio(List listaDados, String relatorio, ServletContext context) throws Exception{
		
		
		return null;
	}
}
