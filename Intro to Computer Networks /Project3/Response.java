public class Response {
    
    public Request request;   // Received Request
    public byte TML;          // Total Message Length (TML)
    public short resID;          // Request ID
    public byte errorC;       // error code if 0 - valid,
                               // 127 - TML not matching, 63 - checksum not matching
    public int result;        // the result of the polynomial calculator
    public byte checksum;     // S = w_1 + w_2 + ... w_n-1 + w_n
                               // if there is a carry then S = S + 1

    public Response(Request request, byte tml, short resID, byte errorC, int result, byte checksum) {
        this.request    = request;
        this.TML        = tml;
        this.resID      = resID;
        this.errorC     = errorC;
        this.result     = result;
        this.checksum   = checksum;
    }

    public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        
        String value = "Total Message Length = " + byteToString(TML) + EOLN +
                       "reqest ID = " + shortToString(resID) + EOLN +
                       "error code = " + byteToString(errorC) + EOLN +
                       "result = " + intToString(result) + EOLN +
                       "checksum = " + byteToString(checksum) + EOLN + EOLN +
                       "Original polynomial P(𝑥) is " + request.a3 + "𝑥^3 + " +
                       request.a2 + "𝑥^2 + " + request.a1 + "𝑥 + " + request.a0 + EOLN +
                       "Original 𝑥 is " + request.x + EOLN + "Therefore, " +
                       "P(𝑥) = " + "a3𝑥^3 + a2𝑥^2 + a1𝑥 + a1 = " + result + EOLN;
        return value;
    }
    
    private StringBuilder byteToString(byte byteVal) {
        StringBuilder hexStr = new StringBuilder();
        hexStr.append("0x");
        hexStr.append(String.format("%02X ", byteVal & 0xFF));

        return hexStr;
    }
    
    private StringBuilder shortToString(short shortVal) {
        byte[] byteArr = new byte[2];
        
        byteArr[0] = (byte)((shortVal >> 8) & 0xFF);
        byteArr[1] = (byte)(shortVal & 0xFF);

        StringBuilder hexStr = new StringBuilder();
        for (byte byteVal : byteArr) {
            hexStr.append("0x");
            hexStr.append(String.format("%02X ", byteVal));
        }
        return hexStr;
    }
    
    private StringBuilder intToString(int intVal) {
        byte[] byteArr = new byte[4];
        
        byteArr[0] = (byte)((intVal >> 24) & 0xFF);
        byteArr[1] = (byte)((intVal >> 16) & 0xFF);
        byteArr[2] = (byte)((intVal >> 8) & 0xFF);
        byteArr[3] = (byte)(intVal & 0xFF);

        StringBuilder hexStr = new StringBuilder();
        for (byte byteVal : byteArr) {
            hexStr.append("0x");
            hexStr.append(String.format("%02X ", byteVal));
        }
        return hexStr;
    }
}
