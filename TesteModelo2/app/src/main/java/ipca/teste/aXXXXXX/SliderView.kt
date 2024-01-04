package ipca.teste.aXXXXXX

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class SliderView : View {

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    var xPos : Float? =  null

    var value : Int
        get() { return (((xPos?:(width/2f)) / width.toFloat())*100f).toInt() }
        set(value) {
            val position = (value/100f)*width
            xPos = position
            onPercentageChanged?.invoke(value)
            invalidate()
        }

    private var onPercentageChanged : ((Int)->Unit)? = null

    fun setPercentageChanged (callback: (Int)->Unit) {
        onPercentageChanged = callback
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()

        paint.color = Color.BLACK
        paint.strokeWidth = 20f

        canvas.drawLine(0f,height/2f,
            width.toFloat(), height/2f, paint)
        paint.color = Color.RED


        canvas.drawCircle(xPos?:width/2f, height/2f, 60f, paint)
        paint.color = Color.GRAY
        paint.textSize = 80f

        var percentage = ((xPos?:(width/2f)) / width.toFloat())*100f

        canvas.drawText("${percentage.toInt()}%",xPos?:width/2f - 50f, height/2f-90f,paint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        val x = event?.x ?: 0

        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                xPos = x.toFloat().coerceIn(0f..width.toFloat())
                var percentage = ((xPos?:(width/2f)) / width.toFloat())*100f
                onPercentageChanged?.invoke(percentage.toInt())
                invalidate()
                return true
            }
            MotionEvent.ACTION_MOVE -> {
                xPos = x.toFloat().coerceIn(0f..width.toFloat())
                var percentage = ((xPos?:(width/2f)) / width.toFloat())*100f
                onPercentageChanged?.invoke(percentage.toInt())
                invalidate()
                return true
            }
        }

        return false
    }


}