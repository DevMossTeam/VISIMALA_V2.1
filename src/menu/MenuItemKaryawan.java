package menu;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import effect.RippleEffect;
import java.awt.Font;
import swing.shadow.ShadowRenderer;

public class MenuItemKaryawan extends JButton {

    public float getAnimate() {
        return animate;
    }

    public void setAnimate(float animate) {
        this.animate = animate;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public boolean isSubMenuAble() {
        return subMenuAble;
    }

    public void setSubMenuAble(boolean subMenuAble) {
        this.subMenuAble = subMenuAble;
    }

    public int getSubMenuIndex() {
        return subMenuIndex;
    }

    public void setSubMenuIndex(int subMenuIndex) {
        this.subMenuIndex = subMenuIndex;
    }

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    private RippleEffect rippleEffect;
    private BufferedImage shadow;
    private int shadowWidth;
    private int shadowSize = 10;
    private int index;
    private boolean subMenuAble;
    private float animate;
    //  Submenu
    private int subMenuIndex;
    private int length;

    public MenuItemKaryawan(String name, int index, boolean subMenuAble) {
        super(name);
        this.index = index;
        this.subMenuAble = subMenuAble;
        setContentAreaFilled(false);
        setForeground(new Color(255, 255, 255)); // menuitem
        setHorizontalAlignment(SwingConstants.LEFT);
        setBorder(new EmptyBorder(9, 20, 9, 10)); //atas bawah kanan kiri 9, 20, 9, 20   ,9, 40, 9, 35
        setIconTextGap(10);//10 jarak text dg icont
        rippleEffect = new RippleEffect(this);
        rippleEffect.setRippleColor(new Color(220, 220, 220));        //220, 220, 220    

        Font font = new Font("Arial", Font.BOLD, 16); 
        setFont(font);
            
        
    }

    private void createShadowImage() {
        int widht = getWidth();
        int height = 5;
        BufferedImage img = new BufferedImage(widht, height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = img.createGraphics();
        g2.setColor(Color.BLACK);
        g2.fill(new Rectangle2D.Double(0, 0, widht, height));
        shadow = new ShadowRenderer(shadowSize, 0.2f, Color.BLACK).createShadow(img);
        g2.dispose();
    }

    public void initSubMenu1(int subMenuIndex, int length) {
        this.subMenuIndex = subMenuIndex; 
        this.length = length;
              setBorder(new EmptyBorder(9, 45, 9, 10)); //9, 10, 9, 10     9, 65, 9, 35
        setBackground(new Color(53, 153, 134));
        setOpaque(true);          
        // setting font submenu
        Font subMenuFont = new Font("Arial", Font.PLAIN, 15); 
        setFont(subMenuFont); 
        
       
    }

    @Override
    protected void paintComponent(Graphics grphcs) {
       super.paintComponent(grphcs);
        Graphics2D g2 = (Graphics2D) grphcs.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        int shiftRight = 10; // Adjust this value as needed

        if (length != 0) {
            g2.setColor(new Color(225, 225, 225));  // line
            if (subMenuIndex == 1) {
                g2.drawImage(shadow, -shadowSize, -20, null);
                g2.drawLine(18 + shiftRight, 0, 18 + shiftRight, getHeight());
                g2.drawLine(18 + shiftRight, getHeight() / 2, 26 + shiftRight, getHeight() / 2);
            } else if (subMenuIndex == length - 1) {
                g2.drawImage(shadow, -shadowSize, getHeight() - 6, null);
                g2.drawLine(18 + shiftRight, 0, 18 + shiftRight, getHeight() / 2);
                g2.drawLine(18 + shiftRight, getHeight() / 2, 26 + shiftRight, getHeight() / 2);
            } else {
                g2.drawLine(18 + shiftRight, 0, 18 + shiftRight, getHeight());
                g2.drawLine(18 + shiftRight, getHeight() / 2, 26 + shiftRight, getHeight() / 2);
            }
        } else if (subMenuAble) {
            g2.setColor(getForeground());
            int arrowWidth = 8;
            int arrowHeight = 4;
            Path2D p = new Path2D.Double();
            p.moveTo(0, arrowHeight * animate);
            p.lineTo(arrowWidth / 2, (1f - animate) * arrowHeight);
            p.lineTo(arrowWidth, arrowHeight * animate);
            g2.translate(getWidth() - arrowWidth - shiftRight , (getHeight() - arrowHeight) / 2); // Adjusted translation
            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
            g2.draw(p);
        }

        g2.dispose();
        rippleEffect.reder(grphcs, new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
    }

    @Override
    public void setBounds(int i, int i1, int i2, int i3) {
        super.setBounds(i, i1, i2, i3);
        createShadowImage();
    }
}
//
//    @Override
//    protected void paintComponent(Graphics grphcs) {
//        super.paintComponent(grphcs);
//        Graphics2D g2 = (Graphics2D) grphcs.create();
//        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        if (length != 0) {
//            //  setting color line submenu
//            g2.setColor(new Color(0,0,0));
//            if (subMenuIndex == 1) {
//                //  First Index
//                g2.drawImage(shadow, -shadowSize, -20, null);
//                g2.drawLine(18, 0, 18, getHeight());
//                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
//            } else if (subMenuIndex == length - 1) {
//                //  Last Index
//                g2.drawImage(shadow, -shadowSize, getHeight() - 6, null);
//                g2.drawLine(18, 0, 18, getHeight() / 2);
//                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
//            } else {
//                g2.drawLine(18, 0, 18, getHeight());
//                g2.drawLine(18, getHeight() / 2, 26, getHeight() / 2);
//            }
//        } else if (subMenuAble) {  
//            g2.setColor(getForeground());
//            int arrowWidth = 8;
//            int arrowHeight = 4;
//            Path2D p = new Path2D.Double();
//            p.moveTo(0, arrowHeight * animate);
//            p.lineTo(arrowWidth / 2, (1f - animate) * arrowHeight);
//            p.lineTo(arrowWidth, arrowHeight * animate);
//            g2.translate(getWidth() - arrowWidth - 15, (getHeight() - arrowHeight) / 2);
//            g2.setRenderingHint(RenderingHints.KEY_STROKE_CONTROL, RenderingHints.VALUE_STROKE_PURE);
//            g2.draw(p);
//        }
//        g2.dispose();
//        rippleEffect.reder(grphcs, new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
//    }