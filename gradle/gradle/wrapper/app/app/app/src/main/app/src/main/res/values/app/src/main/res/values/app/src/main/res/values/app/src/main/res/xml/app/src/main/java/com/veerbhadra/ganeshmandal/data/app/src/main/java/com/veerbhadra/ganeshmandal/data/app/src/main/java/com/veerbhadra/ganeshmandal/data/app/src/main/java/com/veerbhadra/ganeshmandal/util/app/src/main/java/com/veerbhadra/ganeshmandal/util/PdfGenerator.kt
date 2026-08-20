package com.veerbhadra.ganeshmandal.util

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.veerbhadra.ganeshmandal.data.ReceiptEntity
import java.io.File
import java.io.FileOutputStream

object PdfGenerator {

fun generateReceiptPdf(context: Context, receipt: ReceiptEntity): File {  
    val document = PdfDocument()  
    val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()  
    val page = document.startPage(pageInfo)  
    val canvas = page.canvas  

    val maroon = Color.rgb(114, 28, 36)  
    val gold = Color.rgb(197, 155, 39)  
    val ivory = Color.rgb(253, 251, 247)  
    val darkText = Color.rgb(38, 17, 20)  

    val bgPaint = Paint().apply { color = ivory }  
    canvas.drawRect(0f, 0f, 595f, 842f, bgPaint)  

    val borderPaint = Paint().apply {  
        color = gold  
        style = Paint.Style.STROKE  
        strokeWidth = 3f  
    }  
    canvas.drawRect(20f, 20f, 575f, 822f, borderPaint)  
    borderPaint.strokeWidth = 1f  
    canvas.drawRect(26f, 26f, 569f, 816f, borderPaint)  

    val titlePaint = Paint().apply {  
        color = maroon  
        textSize = 22f  
        isFakeBoldText = true  
        textAlign = Paint.Align.CENTER  
    }  
    val subPaint = Paint().apply {  
        color = darkText  
        textSize = 12f  
        textAlign = Paint.Align.CENTER  
    }  

    canvas.drawText("॥ श्री गणेशाय नमः ॥", 297.5f, 60f, subPaint)  
    canvas.drawText("वीरभद्र गणेश मंडळ", 297.5f, 95f, titlePaint)  
    canvas.drawText("मंगळवार पेठ, वसमतनगर", 297.5f, 118f, subPaint)  
    canvas.drawText("स्थापना – २०२२", 297.5f, 136f, subPaint)  

    val linePaint = Paint().apply { color = gold; strokeWidth = 1.5f }  
    canvas.drawLine(50f, 150f, 545f, 150f, linePaint)  

    val badgePaint = Paint().apply { color = maroon }  
    canvas.drawRoundRect(220f, 160f, 375f, 190f, 8f, 8f, badgePaint)  
    val badgeText = Paint().apply {  
        color = Color.WHITE  
        textSize = 14f  
        isFakeBoldText = true  
        textAlign = Paint.Align.CENTER  
    }  
    canvas.drawText("देणगी पावती", 297.5f, 181f, badgeText)  

    val labelPaint = Paint().apply { color = maroon; textSize = 13f; isFakeBoldText = true }  
    val valuePaint = Paint().apply { color = darkText; textSize = 13f }  

    var y = 230f  
    val step = 35f  

    fun drawRow(label: String, value: String) {  
        canvas.drawText(label, 50f, y, labelPaint)  
        canvas.drawText(":", 180f, y, labelPaint)  
        canvas.drawText(value, 195f, y, valuePaint)  
        canvas.drawLine(50f, y + 10f, 545f, y + 10f, Paint().apply { color = Color.LTGRAY; strokeWidth = 0.5f })  
        y += step  
    }  

    drawRow("पावती क्र.", receipt.receiptNo)  
    drawRow("दिनांक", receipt.date)  
    drawRow("देणगीदाराचे नाव", receipt.donorName)  
    drawRow("मोबाईल क्र.", if (receipt.mobileNumber.isEmpty()) "-" else receipt.mobileNumber)  
    drawRow("पत्ता", if (receipt.address.isEmpty()) "-" else receipt.address)  
    drawRow("प्रकार", receipt.donationType)  
    drawRow("रक्कम", "₹ ${receipt.amount}")  
    drawRow("अक्षरी", NumberToWords.convertToMarathi(receipt.amount.toLong()))  
    drawRow("भरणा पद्धत", receipt.paymentMode)  
    if (receipt.transactionId.isNotEmpty()) {  
        drawRow("संदर्भ क्रमांक (Ref ID)", receipt.transactionId)  
    }  

    y = 680f  
    canvas.drawLine(70f, y, 200f, y, Paint().apply { color = darkText; strokeWidth = 1f })  
    canvas.drawText("देणगीदार स्वाक्षरी", 85f, y + 20f, subPaint)  

    canvas.drawLine(395f, y, 525f, y, Paint().apply { color = darkText; strokeWidth = 1f })  
    canvas.drawText("अधिकृत प्रतिनिधी स्वाक्षरी", 400f, y + 20f, subPaint)  

    canvas.drawText("आपल्या सहकार्याबद्दल मनःपूर्वक धन्यवाद!", 297.5f, 780f, Paint().apply {  
        color = maroon  
        textSize = 14f  
        isFakeBoldText = true  
        textAlign = Paint.Align.CENTER  
    })  

    document.finishPage(page)  
    val file = File(context.cacheDir, "${receipt.receiptNo}.pdf")  
    val outputStream = FileOutputStream(file)  
    document.writeTo(outputStream)  
    outputStream.flush()  
    outputStream.close()  
    document.close()  
    return file  
}

}
