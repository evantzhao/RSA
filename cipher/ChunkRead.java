package cipher;

import java.io.IOException;
import java.io.InputStream;

public class ChunkRead implements ChunkReader {
	private int chunkSize;
	InputStream in;
	protected int bytesRead;
	
	public ChunkRead (InputStream in, int chunkSize){
		this.chunkSize = chunkSize;
		this.in = in;
	}
	
	public int chunkSize() {
		return chunkSize;
	}

	public boolean hasNext() {
		if (bytesRead == -1){
			return false;
		}
		return true;
	}

	public int nextChunk(byte[] rawData) {
		try {
			int readBytes = in.read(rawData);
			if (readBytes == -1) {
				bytesRead = -1;
			}
			bytesRead = readBytes;
			return readBytes;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return -1;
	}
}
