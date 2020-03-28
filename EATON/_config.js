// All of our UDP Keys, stored as 32 character hex strings.  These will be converted to Buffers before use in our application
var UDPKeys = {
    "broadcast": "3UJT2HJaAqDB+jQX2Alob+OXzIFI7/UyjOQ2ZEhJoiU=",
    "unicast": {
        "30000c2a690c7652" : "AcQ6ON9WafPUEGAkN+wu89rrEq7Tx+s/oZLVgdKrnyA=",
        "30000c2a69112b6f" : "nz1BBgJDfsLv3UJT2HEq7Tx+s/rjQX2Alob+OXzIFI4="
    }
}

const keyEncoding = "base64"
// Convert from Hex strings to buffers
UDPKeys.broadcast = Buffer.from(UDPKeys.broadcast, keyEncoding)

for(var k in UDPKeys.unicast){
    UDPKeys.unicast[k] = Buffer.from(UDPKeys.unicast[k], keyEncoding)
}

module.exports = UDPKeys
