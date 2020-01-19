
public class FileHelper {
	
	static String appendHiddenToFileName(String filename) {
		String[] documentFilePathAndExtension = filename.split("\\.");
		documentFilePathAndExtension[0] += "_hidden";
		String filenameWithHiddenAppended = "".join(".", documentFilePathAndExtension);
		return filenameWithHiddenAppended;
	}
}
