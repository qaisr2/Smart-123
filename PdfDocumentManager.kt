package com.example.data.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.text.PDFTextStripper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class PdfDocumentManager(private val context: Context) {

    private var parcelFileDescriptor: ParcelFileDescriptor? = null
    private var pdfRenderer: PdfRenderer? = null
    private var pdDocument: PDDocument? = null
    var pageCount: Int = 0
        private set
    var currentTitle: String = "Sample English Lesson.pdf"
        private set

    init {
        try {
            PDFBoxResourceLoader.init(context.applicationContext)
        } catch (_: Exception) {}
    }

    suspend fun loadFromUri(uri: Uri, displayName: String = "Document.pdf"): Result<Int> = withContext(Dispatchers.IO) {
        return@withContext try {
            close()
            currentTitle = displayName

            // Copy to local app cache for safe seeking and descriptor access
            val tempFile = File(context.cacheDir, "current_reading_doc.pdf")
            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    input.copyTo(output)
                }
            } ?: return@withContext Result.failure(Exception("Could not open file stream"))

            initRenderer(tempFile)
            Result.success(pageCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun loadBundledSamplePdf(): Result<Int> = withContext(Dispatchers.IO) {
        return@withContext try {
            close()
            currentTitle = "Smart English Tutor Guide.pdf"
            val sampleFile = File(context.filesDir, "sample_english_guide.pdf")
            if (!sampleFile.exists()) {
                createSamplePdfFile(sampleFile)
            }
            initRenderer(sampleFile)
            Result.success(pageCount)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private fun initRenderer(file: File) {
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        parcelFileDescriptor?.let { pfd ->
            pdfRenderer = PdfRenderer(pfd)
            pageCount = pdfRenderer?.pageCount ?: 0
        }

        try {
            pdDocument = PDDocument.load(file)
        } catch (_: Exception) {
            pdDocument = null
        }
    }

    suspend fun renderPageBitmap(pageIndex: Int, targetWidth: Int = 1080): Bitmap? = withContext(Dispatchers.Default) {
        val renderer = pdfRenderer ?: return@withContext null
        if (pageIndex < 0 || pageIndex >= pageCount) return@withContext null

        return@withContext try {
            val page = renderer.openPage(pageIndex)
            val aspectRatio = page.height.toFloat() / page.width.toFloat()
            val targetHeight = (targetWidth * aspectRatio).toInt().coerceAtLeast(100)

            val bitmap = Bitmap.createBitmap(targetWidth, targetHeight, Bitmap.Config.ARGB_8888)
            val canvas = android.graphics.Canvas(bitmap)
            canvas.drawColor(Color.WHITE)

            page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
            page.close()
            bitmap
        } catch (e: Exception) {
            null
        }
    }

    suspend fun extractPageText(pageIndex: Int): String = withContext(Dispatchers.IO) {
        val doc = pdDocument
        if (doc != null) {
            try {
                val stripper = PDFTextStripper()
                stripper.startPage = pageIndex + 1
                stripper.endPage = pageIndex + 1
                val text = stripper.getText(doc).trim()
                if (text.isNotBlank()) {
                    return@withContext cleanExtractedText(text)
                }
            } catch (_: Exception) {}
        }

        // Fallback sample content if direct text stream parsing is empty (e.g. initial demo)
        return@withContext getSamplePageText(pageIndex)
    }

    private fun cleanExtractedText(text: String): String {
        return text.replace(Regex("\r\n|\r"), "\n")
            .replace(Regex("[ \t]+"), " ")
            .trim()
    }

    private fun createSamplePdfFile(file: File) {
        val document = PdfDocument()

        val samplePages = listOf(
            SamplePageContent(
                title = "Chapter 1: The Power of Diligent Practice",
                paragraphs = listOf(
                    "Learning a foreign language is an extraordinary journey that requires patience, courage, and daily effort.",
                    "When students cultivate the habit of reading English every morning, they acquire new vocabulary and gain immense confidence.",
                    "An obstacle on this path is merely an opportunity to persevere and grow wiser.",
                    "He sat along the river bank, feeling anxious before the exam, but the sincere advice of his teacher inspired him to attempt the challenge once more."
                )
            ),
            SamplePageContent(
                title = "Chapter 2: Essential Habits for Mastery",
                paragraphs = listOf(
                    "A diligent reader does not hesitate when encountering difficult or unfamiliar words.",
                    "Instead of feeling discouraged, they pause to analyze the sentence and highlight the crucial terms.",
                    "It is essential to appreciate the progress you make every single day, no matter how small.",
                    "Knowledge is a precious treasure that will transform your thinking and reveal the potential within your mind."
                )
            ),
            SamplePageContent(
                title = "Chapter 3: Turning Knowledge into Wisdom",
                paragraphs = listOf(
                    "Generous mentors encourage young learners to build solid foundations in comprehension.",
                    "Evaluating your own mistakes without fear provides a significant advantage in academic life.",
                    "Temporary difficulties are natural, but a flexible and mature approach ensures thorough mastery.",
                    "Remember that wisdom is not merely memorizing words, but understanding how they connect hearts and ideas."
                )
            )
        )

        val titlePaint = Paint().apply {
            color = Color.rgb(26, 54, 93) // #1A365D
            textSize = 22f
            isFakeBoldText = true
            isAntiAlias = true
        }

        val bodyPaint = Paint().apply {
            color = Color.rgb(33, 37, 41)
            textSize = 14f
            isAntiAlias = true
        }

        val hintPaint = Paint().apply {
            color = Color.rgb(13, 148, 136) // #0D9488
            textSize = 12f
            isAntiAlias = true
        }

        samplePages.forEachIndexed { index, pageData ->
            val pageInfo = PdfDocument.PageInfo.Builder(595, 842, index + 1).create() // A4 points
            val page = document.startPage(pageInfo)
            val canvas = page.canvas

            // Background
            canvas.drawColor(Color.WHITE)

            // Header Banner
            val headerPaint = Paint().apply { color = Color.rgb(241, 245, 249) }
            canvas.drawRect(0f, 0f, 595f, 60f, headerPaint)

            val headerTextPaint = Paint().apply {
                color = Color.rgb(71, 85, 105)
                textSize = 10f
                isAntiAlias = true
            }
            canvas.drawText("Smart PDF English Tutor - Sample Reader", 40f, 35f, headerTextPaint)
            canvas.drawText("Page ${index + 1} of ${samplePages.size}", 500f, 35f, headerTextPaint)

            var y = 110f
            canvas.drawText(pageData.title, 40f, y, titlePaint)
            y += 35f

            canvas.drawText("💡 Tip: Tap any word in the reader pane to see Urdu meaning, Roman Urdu, and synonyms!", 40f, y, hintPaint)
            y += 40f

            for (paragraph in pageData.paragraphs) {
                val words = paragraph.split(" ")
                var line = ""
                for (word in words) {
                    val testLine = if (line.isEmpty()) word else "$line $word"
                    if (bodyPaint.measureText(testLine) < 515f) {
                        line = testLine
                    } else {
                        canvas.drawText(line, 40f, y, bodyPaint)
                        y += 24f
                        line = word
                    }
                }
                if (line.isNotEmpty()) {
                    canvas.drawText(line, 40f, y, bodyPaint)
                    y += 36f
                }
            }

            document.finishPage(page)
        }

        file.outputStream().use { out ->
            document.writeTo(out)
        }
        document.close()
    }

    private fun getSamplePageText(pageIndex: Int): String {
        return when (pageIndex) {
            0 -> """
                Chapter 1: The Power of Diligent Practice
                
                Learning a foreign language is an extraordinary journey that requires patience, courage, and daily effort.
                
                When students cultivate the habit of reading English every morning, they acquire new vocabulary and gain immense confidence.
                
                An obstacle on this path is merely an opportunity to persevere and grow wiser.
                
                He sat along the river bank, feeling anxious before the exam, but the sincere advice of his teacher inspired him to attempt the challenge once more.
            """.trimIndent()
            1 -> """
                Chapter 2: Essential Habits for Mastery
                
                A diligent reader does not hesitate when encountering difficult or unfamiliar words.
                
                Instead of feeling discouraged, they pause to analyze the sentence and highlight the crucial terms.
                
                It is essential to appreciate the progress you make every single day, no matter how small.
                
                Knowledge is a precious treasure that will transform your thinking and reveal the potential within your mind.
            """.trimIndent()
            else -> """
                Chapter 3: Turning Knowledge into Wisdom
                
                Generous mentors encourage young learners to build solid foundations in comprehension.
                
                Evaluating your own mistakes without fear provides a significant advantage in academic life.
                
                Temporary difficulties are natural, but a flexible and mature approach ensures thorough mastery.
                
                Remember that wisdom is not merely memorizing words, but understanding how they connect hearts and ideas.
            """.trimIndent()
        }
    }

    fun close() {
        try {
            pdDocument?.close()
        } catch (_: Exception) {}
        pdDocument = null

        try {
            pdfRenderer?.close()
        } catch (_: Exception) {}
        pdfRenderer = null

        try {
            parcelFileDescriptor?.close()
        } catch (_: Exception) {}
        parcelFileDescriptor = null
    }

    private data class SamplePageContent(
        val title: String,
        val paragraphs: List<String>
    )
}
