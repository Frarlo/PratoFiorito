package pratofiorito.renderer.swing.state;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public abstract class BaseIntegerDisplay extends JPanel {
    private static final BufferedImage[] DIGITS_IMAGES;
    
    static {
        BufferedImage[] temp;
        try {
            final BufferedImage numsImg = ImageIO.read(ClassLoader.getSystemResource("assets/numeretti.png"));
            
            final List<BufferedImage> tempList = new LinkedList();
            
            int w = numsImg.getWidth();
            while(w > 0) {
                tempList.add(numsImg.getSubimage(
                        numsImg.getWidth() - w, 
                        0, 13, numsImg.getHeight()));
                w -= 14;
            }
            
            // Move the zero cause it's latest but should be first :C
            tempList.add(0, tempList.get(tempList.size() - 1));
            tempList.remove(tempList.size() - 1);
            
            temp = tempList.toArray(new BufferedImage[tempList.size()]);
        } catch (IOException ignored) {
            temp = null;
        }
        DIGITS_IMAGES = temp;
    }
    
    public void drawInteger(Graphics g, int num,
                            int digitsNum,
                            int x, int y, 
                            int width, int height) {
        if(DIGITS_IMAGES == null)
            return;
        
        final int digitWidth = width / digitsNum;
        int digitX = x + digitWidth * (digitsNum - 1);
        
        while(digitsNum > 0) {
            final int digit = num % 10;
            
            g.drawImage(DIGITS_IMAGES[digit], digitX, y, digitWidth, height, null);
            digitX -= digitWidth;
            
            digitsNum--;
            num /= 10;
        }
    }
    
    public void drawInteger(Graphics g, int num,
                            int x, int y, 
                            int width, int height) {
        int digits = 0;
        int tempNum = num;
        while(tempNum > 0) {
            tempNum /= 10;
            digits++;
        }
        
        drawInteger(g, num, digits, x, y, width, height);
    }
    
    public int getDigitWidth() {
        return DIGITS_IMAGES[0].getWidth();
    }
    
    public int getDigitHeight() {
        return DIGITS_IMAGES[0].getHeight();
    }
}
