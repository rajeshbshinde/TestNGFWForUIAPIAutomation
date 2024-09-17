package utils;

import org.apache.commons.compress.archivers.tar.TarArchiveEntry;
import org.apache.commons.compress.archivers.tar.TarArchiveInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.lf5.LogLevel;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class FileUtil {
	private static String ZIP_SOURCE_FOLDER = "";
	private static List<String> listOfFileToZip;

	/**
	 * Find files with extension
	 *
	 * @param dirName
	 *            - directory name
	 * @param extension
	 *            - file extension
	 * @return - list of files.
	 */
	public static File[] findFilesWithExtension(String dirName, final String extension) {
		File dir = new File(dirName);
		return dir.listFiles(new FilenameFilter() {
			public boolean accept(File dir, String filename) {
				return filename.endsWith("." + extension);
			}
		});
	}

	/**
	 * Delete files
	 *
	 * @param files
	 *            - list of files
	 */
	public static void deleteFiles(File[] files) {
		for (File file : files) {
			try {
				Log.Message("Deleting file: " + file.getAbsolutePath(), LogLevel.INFO);
				FileUtils.forceDelete(file);
			} catch (IOException exception) {
				Log.Message("Failed to delete file: " + exception.getMessage(), LogLevel.ERROR);
			}
		}
	}

	/**
	 * Replace file content in give file with new content
	 *
	 * @param filePath
	 *            - file to replace content in.
	 * @param sourceString
	 *            - content to replace
	 * @param targetString
	 *            - new content at source string
	 * @return
	 */
	public static String replaceFileContent(String filePath, String sourceString, String targetString) {
		String result = "false";
		try {
			Log.Message("Replacing file content " + sourceString + " with " + targetString + " in file " + filePath, LogLevel.INFO);
			File file = new File(filePath);
			// We are using the canWrite() method to check whether we can
			// modified file content.
			if (file.canWrite()) {
				Log.Message("File is writable!", LogLevel.INFO);
			} else {
				Log.Message("File is in read only mode!", LogLevel.INFO);
			}
			// Now make our file writable
			file.setWritable(true);
			if (file.canWrite()) {
				Log.Message("Changed file to writable!", LogLevel.INFO);
			}
			String fileContents = FileUtils.readFileToString(file);
			String newFileContencts = fileContents.replace(sourceString, targetString);
			FileUtils.writeStringToFile(file, newFileContencts);
			result = "true";
		} catch (Exception exception) {
			result = exception.getMessage();

			Log.Message(result, LogLevel.ERROR);
			Log.Message(exception.getStackTrace().toString(), LogLevel.ERROR);

		}
		return result;
	}

	/**
	 * Append given content to file
	 *
	 * @param filePath
	 *            - file to append content to.
	 * @param data
	 *            - content to append
	 * @throws IOException
	 */
	public static void appendFileContent(String filePath, String data) {
		Log.Message("Appending file content " + data + " to file " + filePath, LogLevel.INFO);
		File file = new File(filePath);
		// We are using the canWrite() method to check whether we can
		// modified file content.
		if (file.canWrite()) {
			Log.Message("File is writable!", LogLevel.INFO);
		} else {
			Log.Message("File is in read only mode!", LogLevel.INFO);
		}
		// Now make our file writable
		file.setWritable(true);
		if (file.canWrite()) {
			Log.Message("Changed file to writable!", LogLevel.INFO);
		}
		try {
			FileUtils.writeStringToFile(file, data, true);
		} catch (IOException e) {
			Log.Message("Error occured while writing data to file", LogLevel.ERROR);
			e.printStackTrace();
		}

	}

	/**
	 *
	 * @param filePath
	 *            : Path of the File whose data is to be appended
	 * @param data
	 *            : Data to be appended on the new line of the file
	 * @throws IOException
	 *             : throws I/O exceptions
	 */

	public static void appendFileContentOnNLIfNotExists(String filePath, String data) throws IOException {
		Log.Message("Appending file content " + data + " to file " + filePath, LogLevel.INFO);
		File file = new File(filePath);
		// We are using the canWrite() method to check whether we can
		// modified file content.
		if (file.canWrite()) {
			Log.Message("File is writable!", LogLevel.INFO);
		} else {
			Log.Message("File is in read only mode!", LogLevel.INFO);
		}
		// Now make our file writable
		file.setWritable(true);
		if (file.canWrite()) {
			Log.Message("Changed file to writable!", LogLevel.INFO);
		}
		String fileContents = FileUtils.readFileToString(file);
		Boolean contentExists = fileContents.contains(data);
		if (!contentExists) {
			FileUtils.writeStringToFile(file, "\n", true);
			FileUtils.writeStringToFile(file, data, true);

			Log.Message("File is updated!", LogLevel.INFO);
		}

	}

	/**
	 * get file content in give file with new content
	 *
	 * @param filePath
	 *            - file to replace content in.
	 * @return
	 */
	public static String getFileContent(String filePath) {
		String fileContents = "";
		try {
			Log.Message("getting file content from file " + filePath, LogLevel.INFO);
			File file = new File(filePath);
			// We are using the canWrite() method to check whether we can
			// modified file content.
			if (file.canRead()) {
				Log.Message("File is Readable!", LogLevel.INFO);
			} else {
				Log.Message("File is NOT in read only mode!", LogLevel.INFO);
				file.setReadable(true);
				Log.Message("Changed file to Readable!", LogLevel.INFO);
			}
			fileContents = FileUtils.readFileToString(file);
		} catch (Exception exception) {
			fileContents = exception.getMessage();
			Log.Message(fileContents, LogLevel.ERROR);
			Log.Message(exception.getStackTrace().toString(), LogLevel.ERROR);
		}
		return fileContents;
	}

	/**
	 * get last modified file.
	 *
	 * @param dirPath
	 *            - directory path.
	 * @return last modified file from the directory
	 */
	public static File getLastModifiedFile(String dirPath) {
		File fileName = new File(dirPath);
		File[] files = fileName.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return file.isFile();
			}
		});
		long lastModified = Long.MIN_VALUE;
		File lastModifiedFile = null;
		for (File file : files) {
			if (file.lastModified() > lastModified) {
				lastModifiedFile = file;
				lastModified = file.lastModified();
			}
		}
		return lastModifiedFile;
	}

	/**
	 * Unzip files
	 *
	 * @param zipFile
	 * @param extractTo
	 * @throws IOException
	 */
	public static void unzipFile(String zipFile, String extractTo) throws IOException {

		byte[] buffer = new byte[1024 * 5];

		File folder = new File(extractTo);
		if (!folder.exists()) {
			folder.mkdir();
		}

		ZipInputStream ziStream = new ZipInputStream(new FileInputStream(zipFile));
		ZipEntry zEntity = ziStream.getNextEntry();

		while (zEntity != null) {
			if (zEntity.isDirectory()) {
				zEntity = ziStream.getNextEntry();
				continue;
			}

			String fileName = zEntity.getName();
			File newFile = new File(extractTo + File.separator + fileName);
			new File(newFile.getParent()).mkdirs();
			FileOutputStream foStream = new FileOutputStream(newFile);
			int len;
			while ((len = ziStream.read(buffer)) > 0) {
				foStream.write(buffer, 0, len);
			}

			foStream.close();
			zEntity = ziStream.getNextEntry();
		}
		ziStream.closeEntry();
		ziStream.close();
	}

	/**
	 * Gets files from the sourceFolder and makes a zip in the destination
	 * folder.
	 *
	 * @param sourceFolder
	 * @param outputZipFolder
	 *
	 */
	public static void zipFile(String sourceFolder, String outputZipFolder, String outputZipFileName) {
		boolean isFoldersValidated = false;
		File zipDestinationFolder = null;
		if (new File(sourceFolder).isDirectory()) {
			zipDestinationFolder = new File(outputZipFolder);
			if (!zipDestinationFolder.isDirectory()) {
				if (zipDestinationFolder.mkdirs()) {
					Log.Message("Destination path created successfully.", LogLevel.INFO);
				}
			}
			isFoldersValidated = true;
		} else {
			isFoldersValidated = false;
		}

		if (isFoldersValidated) {
			Log.Message("Provided paths have been located.", LogLevel.INFO);
			setZipSourceFolder(sourceFolder);
			listOfFileToZip = new ArrayList<String>();
			generateFileStructure(new File(sourceFolder));
			zipFiles(zipDestinationFolder.getAbsolutePath() + File.separator + outputZipFileName);
			Log.Message("Creating zipfile.", LogLevel.INFO);
		} else {
			Log.Message("Provided paths could not be found.", LogLevel.ERROR);
		}
	}

	/**
	 * Get the source folder path for current file in use.
	 */
	private static String getZipSourceFolder() {
		return ZIP_SOURCE_FOLDER;
	}

	/**
	 * Set the source folder path for current file in use.
	 */
	private static void setZipSourceFolder(String sourceFolder) {
		ZIP_SOURCE_FOLDER = new File(sourceFolder).getAbsolutePath();
	}

	/**
	 * Traverse a directory and get all files, and add the file into fileList
	 *
	 * @param node
	 *            file or directory
	 */
	private static void generateFileStructure(File node) {

		// add file only
		if (node.isFile()) {
			listOfFileToZip.add(getRelativeFilepath(node.getAbsoluteFile().toString()));
		}
		if (node.isDirectory()) {
			String[] subNote = node.list();
			for (String filename : subNote) {
				generateFileStructure(new File(node, filename));
			}
		}
	}

	/**
	 * Format the file path for zip
	 *
	 * @param file
	 *            file path
	 * @return Formatted file path
	 */
	private static String getRelativeFilepath(String file) {
		return file.substring(getZipSourceFolder().length() + 1, file.length());
	}

	/**
	 * Zip the list of files from the <code>sourceFolder</code>
	 *
	 * @param zipFileFullPath
	 *            output ZIP file location
	 */
	private static void zipFiles(String zipFileFullPath) {

		byte[] buffer = new byte[1024];

		try {

			FileOutputStream fos = new FileOutputStream(zipFileFullPath);
			ZipOutputStream zos = new ZipOutputStream(fos);

			System.out.println("Output to Zip : " + zipFileFullPath);

			for (String file : FileUtil.listOfFileToZip) {

				System.out.println("File Added : " + file);
				ZipEntry ze = new ZipEntry(file);
				zos.putNextEntry(ze);

				FileInputStream in = new FileInputStream(ZIP_SOURCE_FOLDER + File.separator + file);

				int len;
				while ((len = in.read(buffer)) > 0) {
					zos.write(buffer, 0, len);
				}

				in.close();
			}

			zos.closeEntry();
			zos.close();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Copy files locally.
	 *
	 * @param sourcePath
	 * @param destinationPath
	 * @throws IOException
	 */
	public static String copyFile(String sourcePath, String destinationPath, String fileName) throws IOException {

		File source = new File(sourcePath);
		File destination = new File(destinationPath);

		if (source.isDirectory()) {
			if (!destination.isDirectory()) {
				if (destination.mkdirs()) {
					Log.Message("Destination path created successfully.", LogLevel.INFO);
				}
			}
			Log.Message("Provided paths have been located.", LogLevel.INFO);
		} else {
			Log.Message("Provided paths could not be found.", LogLevel.ERROR);
			return "Copy failed";
		}

		source = new File(sourcePath + fileName).getAbsoluteFile();
		destination = new File(destinationPath + fileName).getAbsoluteFile();

		Log.Message("Copying file '" + fileName + "' from source path: " + sourcePath + " to destination path: " + destinationPath, LogLevel.INFO);

		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(destination);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			is.close();
			os.flush();
			os.close();
		}

		Log.Message("File copied successfully", LogLevel.INFO);
		return "File copied successfully";
	}

	/**
	 * Gets the absolute path of the <code>filePath</code> provided.
	 *
	 * @param filePath
	 *            - the relative path of the file. It should directly begin with
	 *            the file/folder name and not the file separator.
	 *
	 * @return
	 */
	public String getAbsoluteFilePath(String filePath) {
		filePath = System.getProperty("user.dir") + System.getProperty("file.separator") + filePath;
		return filePath;
	}

	/**
	 * delete old folder with its content and create new one
	 * 
	 * @param folderPath
	 */
	public void deleteOldFolderAndCreateNew(String folderPath) {
		try {
			File path = new File(folderPath);
			if (!path.exists()) {
				Log.Message("Creating fresg folder...." + folderPath, LogLevel.INFO);
				path.mkdir();
			} else {
				String[] entries = path.list();
				for (String s : entries) {
					File currentFile = new File(path.getPath(), s);
					Log.Message("deleting file ...." + currentFile, LogLevel.INFO);
					currentFile.delete();
				}
				path.delete();
				if (!path.exists()) {
					Log.Message("Creating fresh folder...." + folderPath, LogLevel.INFO);
					path.mkdir();
				}
			}
		} catch (Exception e) {
			Log.Message("Error occured while deleting downloaded zip files", LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * method to unzip a zip file
	 * 
	 * @param zipFilePath
	 * @param unZipFolderPath
	 */
	public void unZipFile(String zipFilePath, String unZipFolderPath) {
		try {
			FileUtil.unzipFile(zipFilePath, unZipFolderPath);
		} catch (Exception E) {
			E.printStackTrace();
		}
	}

	/**
	 * method to delete old file and create a new file of same name
	 * 
	 * @param filePath
	 */
	public static void deleteOldFileAndCreateNew(String filePath) {
		try {
			File path = new File(filePath);
			if (!path.exists()) {
				Log.Message("The file dose not exist in specified path: " + filePath, LogLevel.ERROR);
			} else {
				Log.Message("deleting old file ...." + path, LogLevel.INFO);
				path.delete();
			}

			if (!path.exists()) {
				Log.Message("Creating fresh File...." + path, LogLevel.INFO);
				path = new File(filePath);
			}

		} catch (Exception e) {
			Log.Message("Error occured while deleting downloaded zip files", LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	/**
	 * method to create a new file at specified file path
	 * 
	 * @param filePath
	 */
	public static void createNewFile(String filePath) {
		File file = new File(filePath);
		try {
			file.createNewFile();
		} catch (IOException e) {
			Log.Message("Error occured while creating new file", LogLevel.ERROR);
			e.printStackTrace();
		}
	}

	public static void changeFileEncoding(String filePath, String sourceEncoding, String targetEncoding) {
		try {
			File file = new File(filePath);
			String content = FileUtils.readFileToString(file, sourceEncoding);
			FileUtils.write(file, content, targetEncoding);
		} catch (IOException e) {
			Log.Message("Failed to change file encoding " + filePath, LogLevel.ERROR);
		}
	}

	public static void deleteAllFilesInDirectory(String folderPath) {
		File directory = new File(folderPath);

		// Get all files in directory

		File[] files = directory.listFiles();
		for (File file : files) {
			// Delete each file

			if (!file.delete()) {
				// Failed to delete file

				Log.Message("Failed to delete " + file, LogLevel.ERROR);
			}
		}
	}

	public static void deleteFolder(String folderPath) {
		try {
			File srcDir = new File(folderPath);
			FileUtils.deleteDirectory(srcDir);
		} catch (IOException e) {
			Log.Message("Failed to delete directory.", LogLevel.ERROR);
		}
	}

	public static void copyFolder(String sourcePath, String destinationPath) {
		try {
			File srcDir = new File(sourcePath);
			File destDir = new File(destinationPath);
			FileUtils.copyDirectory(srcDir, destDir, true);
		} catch (IOException e) {
			Log.Message("Failed to Copy directory data.", LogLevel.ERROR);
		}
	}

	/**
	 * Untar an input file into an output file.
	 * 
	 * @param tarFile
	 *            the input .tar file
	 * @param extractTo
	 *            the output directory file.
	 * 
	 */
	public static void unTar(String tarFile, String extractTo) {

		int BUFFER = 2048;
		try {
			FileInputStream fin = new FileInputStream(tarFile);
			BufferedInputStream in = new BufferedInputStream(fin);
			GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
			TarArchiveInputStream tarIn = new TarArchiveInputStream(gzIn);

			TarArchiveEntry entry = null;

			/** Read the tar entries using the getNextEntry method **/

			while ((entry = (TarArchiveEntry) tarIn.getNextEntry()) != null) {

				System.out.println("Extracting: " + entry.getName());

				/** If the entry is a directory, create the directory. **/

				if (entry.isDirectory()) {

					File f = new File(extractTo + entry.getName());
					f.mkdirs();
				}
				/**
				 * If the entry is a file,write the decompressed file to the
				 * disk and close destination stream.
				 **/
				else {
					int count;
					byte data[] = new byte[BUFFER];

					FileOutputStream fos = new FileOutputStream(extractTo + entry.getName());
					BufferedOutputStream dest = new BufferedOutputStream(fos, BUFFER);
					while ((count = tarIn.read(data, 0, BUFFER)) != -1) {
						dest.write(data, 0, count);
					}
					dest.close();
				}
			}

			/** Close the input stream **/

			tarIn.close();
			System.out.println("untar completed successfully!!");
		} catch (IOException e) {
			Log.Message("Failed to untar file.", LogLevel.ERROR);
		}
	}

}
