package dummy;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class eRandomAccess extends RandomAccessFile {

	public eRandomAccess(File file, String mode) throws FileNotFoundException {
		super(file, mode);
		// TODO Auto-generated constructor stub
	}
	public void updateSeek(int inc) throws IOException{
		this.seek(this.getFilePointer()+inc);
	}

}
