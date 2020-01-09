package com.axelor.app.student.web;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import org.apache.commons.io.IOUtils;

import com.axelor.data.Importer;
import com.axelor.data.csv.CSVImporter;
import com.axelor.rpc.ActionRequest;
import com.axelor.rpc.ActionResponse;

public class CSVTest {

	public void setSports(ActionRequest request , ActionResponse response) throws IOException {
		File configFile = getConfigFile();
		Importer importer = new CSVImporter(configFile.getAbsolutePath(),getDataCsvFile());
		importer.run();
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
