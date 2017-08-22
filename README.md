Base64 library
==============
Package contains classes to convert bytes to base64 representation and vice versa.

Base64 encoding is described in RFC 1421 and RFC 2045.

Base64Helper class contains methods to convert byte array to base64 string, to convert base64 string to byte array and to check, if the string is a base64 representation of bytes.

An example, how to convert bytes to base64 symbols:
```
String str1 = Base64Helper.toBase64(new byte[]{1, 5, (byte) 140, (byte) 250, -14, 25}); // str1 = "AQWM+vIZ"
String str2 = Base64Helper.toBase64(new byte[]{(byte) 255, (byte) 250, (byte) 180, 17, 94}); // str2 = "//q0EV4="
String str3 = Base64Helper.toBase64(new byte[]{17, 28, (byte) 179, -14}); // str3 = "ERyz8g=="
```

An example, how to convert base64 symbols to bytes:
```
byte[] bytes1 = Base64Helper.toBytes("a+Z/fF12"); // bytes1 = new byte[]{(107, (byte) 230, 127, 124, 93, 118}
byte[] bytes2 = Base64Helper.toBytes("12gE3JQ="); // bytes2 = new byte[]{(byte) 215, 104, 4, (byte) 220, (byte) 148}
byte[] bytes3 = Base64Helper.toBytes("0FFTyQ=="); // bytes3 = new byte[]{(byte) 208, 81, 83, (byte) 201}
```

Base64Helper class contains all data in memory.
For a large data (for example, big files) this is not efficient.
In such cases Base64InputStream and Base64OutputStream classes could be used.
Base64InputStream read stream of base64 symbols and translates them to bytes.
Base64OutputStream writes base64 symbols to the stream.

An example, how to write base64 symbols to the file:
```
try (FileInputStream inputStream = new FileInputStream("some input file");
     Base64OutputStream outputStream = new Base64OutputStream(new FileOutputStream("some output file"));) {
    int read;
    while (true) {
        read = inputStream.read();
        if (read < 0) {
            break;
        }
        outputStream.write(read);
    }
}
```

Each 3 original bytes are represented with 4 base64 symbols.
Convertion to base64 increases the original size by 33 percent.
