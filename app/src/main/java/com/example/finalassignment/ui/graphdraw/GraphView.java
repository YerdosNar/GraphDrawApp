package com.example.finalassignment.ui.graphdraw;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import java.util.ArrayList;
import java.util.List;

public class GraphView extends View {
    private Paint axisPaint;
    private Paint gridPaint;
    private Paint textPaint;
    private Paint functionPaint;

    private float xMin = -10;
    private float xMax = 10;
    private float yMin = -10;
    private float yMax = 10;

    private float zoom = 1.0f;

    // List to store multiple functions
    private List<FunctionInfo> functions = new ArrayList<>();
    private int[] functionColors = {
            Color.BLUE,
            Color.RED,
            Color.GREEN,
            Color.MAGENTA,
            Color.CYAN
    };

    // Inner class to hold function information
    private static class FunctionInfo {
        String expression;
        int color;

        FunctionInfo(String expression, int color) {
            this.expression = expression;
            this.color = color;
        }
    }

    public GraphView(Context context) {
        super(context);
        init();
    }

    public GraphView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Initialize paints for axes
        axisPaint = new Paint();
        axisPaint.setColor(Color.BLACK);
        axisPaint.setStrokeWidth(2f);
        axisPaint.setStyle(Paint.Style.STROKE);

        // Initialize paint for grid lines
        gridPaint = new Paint();
        gridPaint.setColor(Color.LTGRAY);
        gridPaint.setStrokeWidth(1f);
        gridPaint.setStyle(Paint.Style.STROKE);

        // Initialize paint for text (numbers on axes)
        textPaint = new Paint();
        textPaint.setColor(Color.BLACK);
        textPaint.setTextSize(24f);

        // Initialize paint for functions
        functionPaint = new Paint();
        functionPaint.setStrokeWidth(3f);
        functionPaint.setStyle(Paint.Style.STROKE);
        functionPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int width = getWidth();
        int height = getHeight();

        // Draw grid
        drawGrid(canvas, width, height);

        // Draw axes
        drawAxes(canvas, width, height);

        // Draw numbers on axes
        drawAxisNumbers(canvas, width, height);

        // Draw all functions
        for (FunctionInfo function : functions) {
            functionPaint.setColor(function.color);
            float prevX = -1;
            float prevY = -1;
            float step = width / 200f;

            for (float px = 0; px < width; px += step) {
                float x = xMin + (px / width) * (xMax - xMin);
                double y = FunctionParser.evaluate(function.expression, x);

                float py = height - ((float)((y - yMin) / (yMax - yMin)) * height);

                if (prevX >= 0 && py >= 0 && py <= height) {
                    canvas.drawLine(prevX, prevY, px, py, functionPaint);
                }

                prevX = px;
                prevY = py;
            }
        }
    }

    private void drawGrid(Canvas canvas, int width, int height) {
        float xStep = width / 20f;  // 20 divisions
        float yStep = height / 20f;

        // Vertical grid lines
        for (int i = 0; i <= 20; i++) {
            float x = i * xStep;
            canvas.drawLine(x, 0, x, height, gridPaint);
        }

        // Horizontal grid lines
        for (int i = 0; i <= 20; i++) {
            float y = i * yStep;
            canvas.drawLine(0, y, width, y, gridPaint);
        }
    }

    private void drawAxes(Canvas canvas, int width, int height) {
        // Draw X axis
        float yCenter = height / 2f;
        canvas.drawLine(0, yCenter, width, yCenter, axisPaint);

        // Draw Y axis
        float xCenter = width / 2f;
        canvas.drawLine(xCenter, 0, xCenter, height, axisPaint);
    }

    private void drawAxisNumbers(Canvas canvas, int width, int height) {
        float xCenter = width / 2f;
        float yCenter = height / 2f;

        // Draw numbers on X axis
        for (int i = -10; i <= 10; i += 2) {
            if (i != 0) {  // Skip 0 to avoid cluttering center
                float x = xCenter + (i * width / 20f);
                canvas.drawText(String.valueOf(i), x, yCenter + 24, textPaint);
            }
        }

        // Draw numbers on Y axis
        for (int i = -10; i <= 10; i += 2) {
            if (i != 0) {  // Skip 0 to avoid cluttering center
                float y = yCenter - (i * height / 20f);
                canvas.drawText(String.valueOf(i), xCenter + 5, y + 8, textPaint);
            }
        }
    }

    public void setRange(float xMin, float xMax) {
        this.xMin = xMin;
        this.xMax = xMax;
        this.yMin = xMin;  // For now, keeping y range same as x
        this.yMax = xMax;
        invalidate();  // Redraw the view
    }

    public void setZoom(float zoom) {
        this.zoom = zoom;
        invalidate();
    }

    public void addFunction(String function, int index) {
        if (index >= 0 && index < functionColors.length) {
            // Remove existing function at this index if it exists
            functions.removeIf(f -> f.color == functionColors[index]);
            functions.add(new FunctionInfo(function, functionColors[index]));
            invalidate();
        }
    }

    public void clearFunctions() {
        functions.clear();
        invalidate();
    }
}