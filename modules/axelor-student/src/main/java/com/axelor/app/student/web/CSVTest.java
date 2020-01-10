package com.axelor.app.student.web;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.IOUtils;

import com.axelor.data.ImportTask;
import com.axelor.data.Importer;
import com.axelor.data.Listener;
import com.axelor.data.csv.CSVImporter;
import com.axelor.db.Model;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class CSVTest {
	
	private Logger log = LoggerFactory.getLogger(getClass());

	public void setSports(ActionRequest request , ActionResponse response) throws IOException {
		File configFile = getConfigFile();
		
		final List<Model> records = new ArrayList<>();
		
		Importer importer = new CSVImporter(configFile.getAbsolutePath(),getDataCsvFile());
		importer.run();
		importer.addListener(new Listener() {
			
			@Override
		      public void imported(Model bean) {
		        log.info("Bean saved : {}(id={})",
		            bean.getClass().getSimpleName(),
		            bean.getId());
		        
		        System.err.println("Bean saved : {}(id={})"+
		            bean.getClass().getSimpleName()+
		            bean.getId());
		        
		        records.add(bean);
		      }

		      @Override
		      public void imported(Integer total, Integer success) {
		        log.info("Record (total): " + total);
		        log.info("Record (success): " + success);
		        System.err.println("TOTAL: "+total+"  SUCCESS: "+success);
		      }

		      @Override
		      public void handle(Model bean, Exception e) {
		    	  System.err.println("HANDLE");
		      }
		});
		
		importer.run(new ImportTask() {
			
			@Override
			public void configure() throws IOException {
				// TODO Auto-generated method stub
				System.err.println("CONFIGURE");
			}
		});
		response.setReload(true);
	}

	public File getConfigFile() throws IOException {
		File configFile = File.createTempFile("csv-config", ".xml");
		InputStream inputStream = this.getClass().getResourceAsStream("/data-demo/input-config2.xml");
		FileOutputStream fileOutputStream = new FileOutputStream(configFile);
		IOUtils.copy(inputStream, fileOutputStream);
		return configFile;
	}
	
	public String getDataCsvFile() {
		URL url = this.getClass().getResource("/data-demo/input/");
		return url.getPath();
	}
	
	
  
}
