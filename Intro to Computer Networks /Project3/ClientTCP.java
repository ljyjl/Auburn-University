import java.io.*;   // for Input/OutputStream
import java.net.*;  // for Socket
import java.util.*; // for Scanner

public class ClientTCP {
  
  public static short id = 1;
    
  public static void main(String args[]) throws Exception {
    if (args.length != 2)  // Test for correct # of args
      throw new IllegalArgumentException("Parameter(s): <Destination> <Port>");

    InetAddress destAddr = InetAddress.getByName(args[0]);  // Destination address
    int destPort = Integer.parseInt(args[1]);               // Destination port

    String quit = "";
    Socket sock;
    do {
      // Prompt a user to get value x that the size is one byte
      Scanner scan = new Scanner(System.in);
      System.out.print("\nUser Prompt:\n");
      System.out.print("\nEnter x for P(x) = 𝑎3𝑥^3 + 𝑎2𝑥^2+ + 𝑎1𝑥 + 𝑎0: ");
      byte x = scan.nextByte();
     
      if (x < -128 || x > 127)  // Test for correct value of x
        throw new IllegalArgumentException("The value of x must be bigger than -128 and smaller than 127.");
    
      // Prompt a user to get value ai for all i 0 <= i <= 3 and 0 <= ai <= 64
      byte[] aArr = new byte[4];
      for (int i = 3; i >= 0; i--) {
        System.out.print("Enter the coefficient a" + i + ": ");
        byte a = scan.nextByte();

        if (a < 0 || a > 64)  // Test for correct value of ai
          throw new IllegalArgumentException("The value of a" + i + " must be less than or equal to 64.");
        aArr[i] = a;
      }
      byte a3 =  aArr[3];
      byte a2 =  aArr[2];
      byte a1 =  aArr[1];
      byte a0 =  aArr[0];
   
      byte tlm = 9;
      byte checksum = calcChecksum(tlm, id, x, a3, a2, a1, a0);
      Request request = new Request(tlm, id, x, a3, a2, a1, a0, checksum);

      RequestEncoder encoder = new RequestEncoderBin();

      sock = new Socket(destAddr, destPort);
      OutputStream out = sock.getOutputStream(); // Get a handle onto Output Stream
      long begin = System.currentTimeMillis();  // Return beginning time in millisec
      out.write(encoder.encode(request)); // Encode and send

      InputStream in = sock.getInputStream();
      Response responseMsg = decodeResponse(in, request); // Decode the received response message
                                                          // with the original request mesage
        
      long end = System.currentTimeMillis();  // Return ending time in millisec
      double time = end - begin;  // Return total time taken in millisec

      System.out.println("\nReceived Response message: \n\n" + responseMsg);
      System.out.println("Time expressed (Round Trip): " + time + " ms");
    
      System.out.print("\nEnter q if you want to quit this program. \nOtherwhise, type any other character(s): ");
      quit = scan.next();
      id++;
    } while (!quit.equals("q"));
    
    sock.close();
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

    for(int i = 0; i < s.length() ; i++) {
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
    
   private static Response decodeResponse(InputStream wire, Request req) throws IOException  {
       DataInputStream src = new DataInputStream(wire);
                             
       byte resTML = src.readByte();
       short resID = src.readShort();
       byte resErrorC = src.readByte();
       int resResult = src.readInt();
       byte resChecksum = src.readByte();
                                                             
       return new Response(req, resTML, resID, resErrorC, resResult, resChecksum);
   }
}
                                                                        
