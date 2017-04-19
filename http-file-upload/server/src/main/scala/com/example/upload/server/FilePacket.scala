package com.example.upload.server




case class FileTransferPacket(name:String,
                             chunkNumber: Int,
                             base64:String,
                             isFinal: Boolean)

