package NewFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Timer;

public class BallBounceGame extends JFrame {
    private CirclePanel circlePanel;

    private int circleX, circleY, circle2X, circle2Y;
    private int circleDiameter, circle2Width, circle2Height;
    private int circleXMovement, circle2XMovement;
    private int circleYMovement, circle2YMovement;
    private Color circleColor, circle2Color;

    private int rectangleX, rectangleY;
    private int rectangleWidth, rectangleHeight;
    private int rectangleXMovement;
    private Color rectangleColor;

    public BallBounceGame() {
        setTitle("Moving Shapes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        circlePanel = new CirclePanel();
        setLayout(new BorderLayout());
        add(circlePanel, BorderLayout.CENTER);

        circleX = 100;
        circleY = 100;
        circleDiameter = 50;
        circleXMovement = 3;
        circleYMovement = 2;
        circleColor = Color.RED;

        circle2X = 100;
        circle2Y = 0;
        circle2Width = 100;
        circle2Height = 50;
        circle2XMovement = 3;
        circle2YMovement = 0;
        circle2Color = Color.RED;

        int delay = 10;
        Timer timer = new Timer(delay, new TimerListener());
        timer.start();

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        new BallBounceGame();
    }

    private class CirclePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            g.setColor(circleColor);
            g.fillOval(circleX, circleY, circleDiameter, circleDiameter);
            g.setColor(circle2Color);
            g.fillRect(circle2X, circle2Y, circle2Width, circle2Height);
        }

        @Override
        public Dimension getPreferredSize() {
            return new Dimension(400, 400);
        }

        public boolean contains(int x, int y) {
            int centerX = circleX + circleDiameter / 2;
            int centerY = circleY + circleDiameter / 2;
            int dx = x - centerX;
            int dy = y - centerY;
            double distance = Math.sqrt(dx * dx + dy * dy);
            return distance < circleDiameter / 2;
        }
    }

    private class TimerListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            if (isColliding(circleX, circleY, circleDiameter, circle2X, circle2Y, circle2Width, circle2Height)) {
                int tempXMovement = circleXMovement;
                int tempYMovement = circleYMovement;
                circleXMovement = circle2XMovement;
                circleYMovement = circle2YMovement;
                circle2XMovement = tempXMovement;
                circle2YMovement = tempYMovement;


                circleColor = Color.BLUE;
                circle2Color = Color.GREEN;
            }


            circleX += circleXMovement;
            circleY += circleYMovement;
            circle2X += circle2XMovement;


            if (circleX <= 0 || circleX >= getWidth() - circleDiameter) {
                circleXMovement *= -1;
                circleColor = Color.BLUE;
            }
            if (circleY <= 0 || circleY >= getHeight() - circleDiameter) {
                circleYMovement *= -1;
                circleColor = Color.RED;
            }


            if (circle2X <= 0 || circle2X >= getWidth() - circle2Width) {
                circle2XMovement *= -1;
                circle2Color = Color.GRAY;
            }

            circlePanel.repaint();
        }


        private boolean isColliding(int x1, int y1, int diameter1, int x2, int y2, int width2, int height2) {
            int centerX1 = x1 + diameter1 / 2;
            int centerY1 = y1 + diameter1 / 2;
            int centerX2 = x2 + width2 / 2;
            int centerY2 = y2 + height2 / 2;
            int dx = centerX2 - centerX1;
            int dy = centerY2 - centerY1;
            int distanceX = Math.abs(dx) - width2 / 2 - diameter1 / 2;
            int distanceY = Math.abs(dy) - height2 / 2 - diameter1 / 2;
            return distanceX < 0 && distanceY < 0;
        }
    }
}

