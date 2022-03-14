import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Component;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

public class CarouselPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private int count = 0;
	private int index = 0;

	private JPanel content = new JPanel(new CardLayout());
	private JButton navLeft, navRight;

	private Timer timer = new Timer(1000, null);

	public CarouselPanel(int scrollseconds) {
		setLayout(new BorderLayout());
		navLeft = new JButton("<");
		navRight = new JButton(">");
		navLeft.addActionListener(l -> {
			CardLayout cl = (CardLayout)(content.getLayout());
			index = index - 1;
			index = index >= 0 ? index : count - 1;
			cl.show(content, Integer.toString(index));
			timer.restart();
		}); 
		navRight.addActionListener(l -> {
			CardLayout cl = (CardLayout)(content.getLayout());
			index = index + 1;
			index = index < count ? index : 0;
			cl.show(content, Integer.toString(index));
			timer.restart();
		}); 
		add(navLeft, BorderLayout.WEST);
		add(navRight, BorderLayout.EAST);
		add(content, BorderLayout.CENTER);
		navLeft.setVisible(count > 1);
		navRight.setVisible(count > 1);
		if(scrollseconds > 0) {
			timer = new Timer(scrollseconds * 1000, null);
			timer.addActionListener(l -> {
				navRight.doClick();
			});
			timer.setRepeats(true);
			timer.start();
		}
	}

	public CarouselPanel() {
		this(-1);
	}

	public void addComponent(Component component) {
		content.add(component, Integer.toString(count));
		count++;
		navLeft.setVisible(count > 1);
		navRight.setVisible(count > 1);
	}

}

//usage
CarouselPanel carousel = new CarouselPanel();
			carousel.addComponent(new JLabel(new ImageIcon(ImageIO.read(new URL("http://i.imgur.com/0yd3HFb.png")))));
			carousel.addComponent(new JLabel(new ImageIcon(ImageIO.read(new URL("http://i.imgur.com/2YDrKSy.png")))));
			carousel.addComponent(new JLabel(new ImageIcon(ImageIO.read(new URL("http://i.imgur.com/e9RmCeC.png")))));
			frame.add(carousel, BorderLayout.CENTER);
