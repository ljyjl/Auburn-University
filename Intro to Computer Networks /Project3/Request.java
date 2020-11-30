public class Request {
     
    public byte TML;          // Total Message Length (TML)
    public short reqID;        // Request ID
    public byte x;            // number for which we want to compute P(x)
    public byte a3;           // 3th polynomial coefficient
    public byte a2;           // 2th polynomial coefficient
    public byte a1;           // 1th polynomial coefficient
    public byte a0;           // 0th polynomial coefficient
    public byte checksum;     // S = w_1 + w_2 + ... w_n-1 + w_n
                               // if there is a carry then S = S + 1

    public Request(byte tml, short reqID, byte x, byte a3,
                   byte a2, byte a1, byte a0, byte checksum) {
        this.TML        = tml;
        this.reqID      = reqID;
        this.x          = x;
        this.a3         = a3;
        this.a2         = a2;
        this.a1         = a1;
        this.a0         = a0;
        this.checksum   = checksum;
    }

    public String toString() {
        final String EOLN = java.lang.System.getProperty("line.separator");
        
        String value = "Total Message Length = " + byteToString(TML) + EOLN +
                       "reqID = " + shortToString(reqID) + EOLN +
                       "x  = " + byteToString(x) + EOLN +
                       "a3 = " + byteToString(a3) + EOLN +
                       "a2 = " + byteToString(a2) + EOLN +
                       "a1 = " + byteToString(a1) + EOLN +
                       "a0 = " + byteToString(a0) + EOLN +
                       "Checksum = " + byteToString(checksum) + EOLN;
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
}
