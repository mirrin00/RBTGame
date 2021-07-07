package summer.practice.infty.drawable

import javafx.scene.shape.Line

class DrawableLink(var startX: Double, var startY: Double, var endX: Double, var endY: Double){
    var line = Line()

    init{
        line = Line(startX, startY, endX, endY)
        line.strokeWidth = 3.0
    }

}
