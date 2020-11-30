import java.io.*;  // for ByteArrayInputStream
import java.net.*; // for DatagramPacket

public class RequestDecoderBin implements RequestDecoder, RequestBinConst {

    private String encoding;  // Character encoding

    public RequestDecoderBin() {
        encoding = DEFAULT_ENCODING; 
    }

    public RequestDecoderBin(String encoding) {
        this.encoding = encoding;
    }
    
    public Request decode(InputStream wire) throws IOException {
        DataInputStream src = new DataInputStream(wire);
        byte TML        = src.readByte(); 
        short requestID  = src.readShort();
        byte x          = src.readByte();
        byte a3         = src.readByte(); 
        byte a2         = src.readByte();
        byte a1         = src.readByte();
        byte a0         = src.readByte();
        byte checksum   = src.readByte();
        
        return new Request(TML, requestID, x, a3, a2, a1, a0, checksum);
    }

    public Request decode(DatagramPacket p) throws IOException {
        ByteArrayInputStream payload =
        new ByteArrayInputStream(p.getData(), p.getOffset(), p.getLength());
        return decode(payload);
    }
}
