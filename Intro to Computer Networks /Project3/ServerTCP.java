import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket and ServerSocket

public class ServerTCP {
  public static void main(String args[]) throws Exception {
      
    if (args.length != 1)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Port>");

    int port = Integer.parseInt(args[0]);   // Receiving Port
	
    ServerSocket servSock = new ServerSocket(port);

    for(;;) {
      Socket clntSock = servSock.accept();

      // Receive binary-encoded request
      RequestDecoder decoder = new RequestDecoderBin();
      Request receivedRequest = decoder.decode(clntSock.getInputStream());

      System.out.println("Received Binary-Encoded Request:\n");
      System.out.println(receivedRequest);

      // Response elements
      byte tmlRecv = 0;
      if (!Short.toString(receivedRequest.reqID).isEmpty() && 
          !Byte.toString(receivedRequest.x).isEmpty() && 
          !Byte.toString(receivedRequest.a3).isEmpty() && 
          !Byte.toString(receivedRequest.a2).isEmpty() && 
          !Byte.toString(receivedRequest.a1).isEmpty() &&
          !Byte.toString(receivedRequest.a0).isEmpty())
          tmlRecv = 9;
      byte tml = 9;
      byte checksum = calcChecksum(tmlRecv, receivedRequest.reqID, receivedRequest.x, receivedRequest.a3, receivedRequest.a2, receivedRequest.a1, receivedRequest.a0);
      byte errorC = checkError(tmlRecv, receivedRequest.checksum, tml, checksum);

      int polynomialResult = calcPolynomial(receivedRequest);
      Response response = new Response(receivedRequest, tml, receivedRequest.reqID, errorC, polynomialResult, checksum);
      
      System.out.println("Client will print this message:\n");
      System.out.println(response);
        
      // Send the response message
      byte[] polyResponseMsg = encodeResponse(response);
      OutputStream out = clntSock.getOutputStream();
      out.write(polyResponseMsg);

      clntSock.close();
      //servSock.close();
    }
  }
 
  public static byte calcChecksum(byte tlm, short req, byte x, byte a3, byte a2, byte a1, byte a0) {
    byte[] byteArr = new byte[2];
    String s = "0";
    String[] wArr = new String[8];

    wArr[0] = Integer.toBinaryString(tlm & 0xFF);
    byte byteID1 = (byte)((req >> 8) & 0xFF);
    byte byteID2 = (byte)(req & 0xFF);
    wArr[1] = Integer.toBinaryString(byteID1 & 0xFF);
    wArr[2] = Integer.toBinaryString(byteID2 & 0xFF);
    wArr[3] = Integer.toBinaryString(x & 0xFF);
    wArr[4] = Integer.toBinaryString(a3 & 0xFF);
    wArr[5] = Integer.toBinaryString(a2 & 0xFF);
    wArr[6] = Integer.toBinaryString(a1 & 0xFF);
    wArr[7] = Integer.toBinaryString(a0 & 0xFF);
      
    s = calcNBitWord(wArr[0], wArr[1]).toString();
    for (int i = 2; i < wArr.length; i++) {
        if (s.equals(""))
            s = "0";
        
        s = calcNBitWord(s, wArr[i]).toString();
    }
    if (s.equals(""))
        s = "0";
      
    StringBuilder sb = new StringBuilder();

    for (int i = 0; i < s.length() ; i++) {
        char c = s.charAt(i)=='1' ? '0' : '1';
        sb.append(c);
    }
    s = sb.toString();
      
    return Byte.parseByte(s, 2);
  }

  private static StringBuilder calcNBitWord(String binary1, String binary2) {
        int b1 = Integer.parseInt(binary1);
        int b2 = Integer.parseInt(binary2);

        int[] total = new int[4];
        int i = 0, carry = 0;
        while (i < 4 && (b1 != 0 || b2 != 0)) {
            total[i++] = (int)((b1 % 10 + b2 % 10 + carry) % 2);
            carry = (int)((b1 % 10 + b2 % 10 + carry) / 2);
            b1 = b1 / 10;
            b2 = b2 / 10;
        }

        for (int j = 0; j < total.length - 1; j++) {
            if (carry != 0) {
            if (i < total.length) {
                j = i++;
            }
            int tempTotal = total[j];
            total[j] = (int)((tempTotal + carry) % 2);
            carry = (int)(tempTotal + carry / 2);
            }
        }
        
        --i;
        StringBuilder sb = new StringBuilder();
        while (i >= 0) {
            String s = Integer.toString(total[i--]);
            sb.append(s);
        }
        return sb;
  }


  public static byte checkError(short receivedTML, short receivedChecksumm, short responseTML, short responseChecksum) {
    byte errorC = 0;

    if (receivedTML != responseTML)
        errorC = 127;
    else if (receivedChecksumm != responseChecksum)
        errorC = 63;
      
    return errorC;
  }

  public static int calcPolynomial(Request received) {
    byte x = received.x;
    byte a3 = received.a3;
    byte a2 = received.a2;
    byte a1 = received.a1;
    byte a0 = received.a0;
      
    int px = (int)(a3 * Math.pow(x, 3) + a2 * Math.pow(x, 2) + a1 * Math.pow(x, 1) + a0);
    return px;
  }
    
  private static byte[] encodeResponse(Response res) throws Exception {
      ByteArrayOutputStream buf = new ByteArrayOutputStream();
      DataOutputStream out = new DataOutputStream(buf);
      
      out.writeByte(res.TML);
      out.writeShort(res.resID);
      out.writeByte(res.errorC);
      out.writeInt(res.result);
      out.writeByte(res.checksum);

      out.flush();
      return buf.toByteArray();
  }
}
