package com.bbm.register.reports;

import java.io.File;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Component;

import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Component
public class ReportUtil implements Serializable{

	
	private static final long serialVersionUID = 1L;

	//retorna o pdf em bytes para download no navegador
	public byte[] geraRelatorio(List listaDados, String relatorio, ServletContext context) throws Exception{
		
		//Cria a lista de dados para a impressao
		JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(listaDados);
		 
		//Carrega o caminho do arquivo jasper
		String url = context.getRealPath("reports") + File.separator + relatorio + ".jasper";
		
		//Carrega o arquivo jasper passando os dados
		JasperPrint printer = JasperFillManager.fillReport(url, new HashMap(), dataSource);
		
		return JasperExportManager.exportReportToPdf(printer);
	}
}
