# Base64 library
Base64 library converts bytes to the base64 representation and vice versa.

Base64 encoding is described in RFC 1421 and RFC 2045.

`Base64Helper` class contains methods to convert the byte array to the base64 string, to convert the base64 string to the byte array and to check, if the string is a base64 representation of bytes.

An example, how to convert the byte array to the base64 string:
```
String str1 = Base64Helper.toBase64(new byte[]{1, 5, (byte) 140, (byte) 250, -14, 25}); // str1 = "AQWM+vIZ"
String str2 = Base64Helper.toBase64(new byte[]{(byte) 255, (byte) 250, (byte) 180, 17, 94}); // str2 = "//q0EV4="
String str3 = Base64Helper.toBase64(new byte[]{17, 28, (byte) 179, -14}); // str3 = "ERyz8g=="
```

An example, how to convert the base64 string to the byte array:
```
byte[] bytes1 = Base64Helper.toBytes("a+Z/fF12"); // bytes1 = new byte[]{(107, (byte) 230, 127, 124, 93, 118}
byte[] bytes2 = Base64Helper.toBytes("12gE3JQ="); // bytes2 = new byte[]{(byte) 215, 104, 4, (byte) 220, (byte) 148}
byte[] bytes3 = Base64Helper.toBytes("0FFTyQ=="); // bytes3 = new byte[]{(byte) 208, 81, 83, (byte) 201}
```

`Base64Helper` class contains all data in memory.
For the large data (for example, big files) this is not efficient.
In this case `Base64InputStream` and `Base64OutputStream` classes can be used.
`Base64InputStream` reads the stream of the base64 characters and translates them to the bytes.
`Base64OutputStream` translates the bytes to the base64 characters and writes them to the stream.

An example, how to write the base64 characters to the file:
```
try (FileInputStream inputStream = new FileInputStream("input file");
     Base64OutputStream outputStream = new Base64OutputStream(new FileOutputStream("base64 output file"))) {
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

Each 3 original bytes are represented with 4 base64 characters.
The base64 representation of bytes increases the original size by 33 percent.

# The latest release
Base64 library:
* **&lt;groupId&gt;**: ru.d-shap
* **&lt;artifactId&gt;**: base64
* **&lt;version&gt;**: 1.1

# Donation
If you find my code useful, you can [bye me a coffee](https://www.paypal.me/dshapovalov)
