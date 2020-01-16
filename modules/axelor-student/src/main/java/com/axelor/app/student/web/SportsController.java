package com.axelor.app.student.web;

import com.axelor.app.AppSettings;
import com.axelor.app.student.db.Sports;
import com.axelor.app.student.db.Student;
import com.axelor.db.JPA;
import com.axelor.db.JpaSupport;
import com.axelor.meta.db.MetaFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.Enumeration;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import javax.persistence.EntityManager;

import org.apache.commons.io.IOUtils;

public class SportsController extends JpaSupport {

	public Object checkForStudentBinding(Object bean, Map<String, Object> context) throws IOException {
		assert bean instanceof Sports;
		// Sports sp = (Sports) bean;

		// Student s = JPA.all(Student.class).filter("self.name =
		// ?",sp.getStudent()).fetchOne();
		readUsingZipFile();

		long count = JPA.all(Sports.class).count();
		System.out.println("COUNT-SPORTS: " + count);
		assert count > 1;

		return bean;
	}

	private void readUsingZipFile() throws IOException {
		URL url = this.getClass().getResource("/data-demo/input/");
		String FILE_NAME = url.getPath() + "studentPhoto.zip";
		String OUTPUT_DIR = AppSettings.get().getPath("file.upload.dir", "") + "/";
		final ZipFile file = new ZipFile(FILE_NAME);
		System.out.println("Iterating over zip file : " + FILE_NAME);

		long counter = 1;

		try {
			final Enumeration<? extends ZipEntry> entries = file.entries();
			while (entries.hasMoreElements()) {
				final ZipEntry entry = entries.nextElement();

				int n = (int) JPA.all(MetaFile.class).filter("self.fileName = ?1", entry.getName()).count();
				if (n <= 0) {
					MetaFile metaFile = new MetaFile();
					metaFile.setFileName(entry.getName());
					metaFile.setDescription("djfhgkjshdfk");
					metaFile.setFilePath(entry.getName());
					metaFile.setFileSize(entry.getSize());
					EntityManager em = JPA.em();
					em.persist(metaFile);

					Student st = em.find(Student.class, counter++);
					st.setStudentImage(metaFile);
					System.out.printf("File: %s Size %d  Modified on %TD %n", entry.getName(), entry.getSize(),
							new Date(entry.getTime()));
					extractEntry(entry, file.getInputStream(entry));
				}

				
			}
			System.out.printf("Zip file %s extracted successfully in %s", FILE_NAME, OUTPUT_DIR);
		} finally {
			file.close();
		}

	}

	private static void extractEntry(final ZipEntry entry, InputStream is) throws IOException {
		String OUTPUT_DIR = AppSettings.get().getPath("file.upload.dir", "") + "/";
		int BUFFER_SIZE = 8192;
		String exractedFile = OUTPUT_DIR + entry.getName();
		FileOutputStream fos = null;

		try {
			fos = new FileOutputStream(exractedFile);
			final byte[] buf = new byte[BUFFER_SIZE];
			int read = 0;
			int length;

			while ((length = is.read(buf, 0, buf.length)) >= 0) {
				fos.write(buf, 0, length);
			}

		} catch (IOException ioex) {
			fos.close();
		}

	}

	public File getConfigFile() throws IOException {
		File configFile = File.createTempFile("csv-config", ".xml");
		InputStream inputStream = this.getClass().getResourceAsStream("/data-demo/input-config2.xml");
		FileOutputStream fileOutputStream = new FileOutputStream(configFile);
		IOUtils.copy(inputStream, fileOutputStream);
		return configFile;
	}

	public String getZipFile() {
		URL url = this.getClass().getResource("/data-demo/input/");
		return url.getPath();
	}
}
