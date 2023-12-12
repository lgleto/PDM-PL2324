package ipca.utility.customcontrol

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Point
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class JogoGaloView : View {

    var points = arrayListOf<Point>()

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)


    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val paint = Paint()


        paint.color = Color.BLACK
        paint.strokeWidth = 20f
        val w3 = width / 3f
        val h3 = height / 3f
        canvas.drawLine(w3,0f,w3, height.toFloat(), paint)
        canvas.drawLine(2*w3,0f,2*w3, height.toFloat(), paint)
        canvas.drawLine(0f,h3, width.toFloat(), h3, paint)
        canvas.drawLine(0f,2*h3, width.toFloat(), 2*h3, paint)

        paint.color = Color.RED

        for (p in points){
            canvas.drawCircle(
                (w3) * p.x.toFloat() - w3/2,
                (h3) * p.y.toFloat() - h3/2,
                w3/2.2f, paint)
        }

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        super.onTouchEvent(event)
        val x = event?.x?:0
        val y = event?.y?:0

        val w3 = width / 3f
        val h3 = height / 3f

        var qx = 0
        var qy = 0

        if (x.toFloat() > w3*2) {
            qx = 3
        }else if (x.toFloat() > w3) {
            qx = 2
        }else {
            qx = 1
        }

        if (y.toFloat() > h3*2) {
            qy = 3
        }else if (y.toFloat() > h3) {
            qy = 2
        }else {
            qy = 1
        }


        when(event?.action){
            MotionEvent.ACTION_DOWN -> {
                points.add(Point(qx,qy))
                invalidate()
                return true
            }
        }
        return false
    }
}