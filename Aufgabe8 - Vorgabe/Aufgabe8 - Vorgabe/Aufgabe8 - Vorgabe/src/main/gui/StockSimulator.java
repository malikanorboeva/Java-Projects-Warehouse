package main.gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import main.goods.Pallet;
import main.manager.StockManager;
import main.manager.IncomingPalletManager;
import main.stock.Stack;

public class StockSimulator extends JFrame {

	Font normalFont = new Font(Font.SERIF, Font.PLAIN, 26);
	Font palletFont = new Font(Font.SERIF, Font.PLAIN, 26);

	Color palletColor = Color.decode("#a6391e");
	Color wichelFace = Color.decode("#f5e831");
	Color wichtelHat = Color.decode("#3139a8");
	Color elfHat = Color.decode("#26802b");
	Color eyes = Color.decode("#070807");
	Color outgoingZone = Color.decode("#b7e8b9");
	
	IncomingPalletManager incomingPalletManager;
	Stack[] stacks;

	JScrollPane incomingPanel;
	JPanel mainPanel;
	JPanel stockPanel;
	JLabel mapImage;
	JPanel rightPanel;
	JLabel lblScore;
	
	private static final long serialVersionUID = 7795453049253322895L;
	
	public StockSimulator(IncomingPalletManager incomingPalletManager, Stack[] stacks) {
		this.incomingPalletManager = incomingPalletManager;
		this.stacks = stacks;
		this.setSize(800, 600);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		this.setTitle("Stock simulation");
		
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				StockManager.stop();
				System.exit(0);
			}
		});
		
		addMainGuiComponents();		
		this.setVisible(true);
	}
		
	private void addMainGuiComponents() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		mainPanel = new JPanel(new GridBagLayout());
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 1.0;
		gbc.weighty = 1.0;
		this.add(mainPanel, gbc);

		incomingPanel = new JScrollPane(getPalletsPanel(), JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.5;
		gbc.weighty = 1.0;
		mainPanel.add(incomingPanel, gbc);

		stockPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.fill = GridBagConstraints.BOTH;
		gbc.gridx = 1;
		gbc.weightx = 0.1;
		gbc.weighty = 1.0;
		mainPanel.add(stockPanel, gbc);

		rightPanel = new JPanel();
		gbc = new GridBagConstraints();
		gbc.gridx = 2;
		gbc.fill = GridBagConstraints.BOTH;
		gbc.weightx = 0.1;
		gbc.weighty = 1.0;
		mainPanel.add(rightPanel, gbc);
	}
	
	public JPanel getPalletsPanel() {
		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints gbc;
		for (int i = 0; i < incomingPalletManager.getPallets().length; i++) {
			Pallet p = incomingPalletManager.getPallets()[i];
			gbc = new GridBagConstraints();
			gbc.gridy = i;
			gbc.weightx = 1;
			gbc.fill = GridBagConstraints.BOTH;
			panel.add(getPalletPanel(p, i), gbc);
		}

		gbc = new GridBagConstraints();
		gbc.gridy = incomingPalletManager.getPallets().length;
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;
		panel.add(new JPanel(), gbc);
		panel.setMinimumSize(new Dimension(panel.getWidth(), 100));
		return panel;		
	}
	
	public JPanel getPalletPanel(Pallet p, int idx) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));		

		StringBuffer text = new StringBuffer();
		text.append(idx);
		if (p != null) {
			text.append(": " + p.getDescription());
			text.append(", " + p.getWidth());
			text.append(", " + ((p.getWeight() / 1000) + 1) + "kg");
			text.append(", " + p.getValue() + "€");
		} else {
			text.append(": null");
		}

		JLabel lblPallet = new JLabel(text.toString());
		lblPallet.setFont(palletFont);

		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.weighty = 1;
		gbc.fill = GridBagConstraints.BOTH;

		panel.add(lblPallet, gbc);
		return panel;
	}
	
	public void UpdateGUI() {
		updateImage();
		updatePalletsPanel();
		updateRequestedPallet();
	}

	public void updateRequestedPallet() {
		rightPanel.removeAll();
		lblScore = new JLabel("Score: " + ElfController.getScore());
		lblScore.setFont(normalFont);

		rightPanel.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();

		rightPanel.add(lblScore, gbc);
		gbc.gridy = 1;
		Pallet p = ElfController.getRequestedPallet();
		if (p != null) {
			rightPanel.add(getRequestedPalletPanel(p), gbc);
		}
	}

	public JPanel getRequestedPalletPanel(Pallet p) {
		JPanel panel = new JPanel(new GridBagLayout());
		panel.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY, 2));

		JLabel lblDesciption = new JLabel(p.getDescription());
		lblDesciption.setFont(palletFont);
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.weightx = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(lblDesciption, gbc);

		JLabel lblWidth = new JLabel("" + p.getWidth() + "cm");
		lblWidth.setFont(palletFont);
		gbc.gridy = 1;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(lblWidth, gbc);

		JLabel lblWeight = new JLabel("" + ((p.getWeight() / 1000) + 1) + "kg");
		lblWeight.setFont(palletFont);
		gbc.gridy = 2;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(lblWeight, gbc);

		JLabel lblValue = new JLabel("" + p.getValue() + "€");
		lblValue.setFont(palletFont);
		gbc.gridy = 3;
		gbc.fill = GridBagConstraints.NONE;
		panel.add(lblValue, gbc);

		return panel;
	}
	
	private void updateImage() {
		Image generated = getCurrentImage();
		int w = mainPanel.getWidth() - incomingPanel.getWidth() - rightPanel.getWidth();
		if (mainPanel.getHeight() < w) {
			w = mainPanel.getHeight();
		}
		if (w < 10) {
			w = 10;
		}
		Image scaled = generated.getScaledInstance(w, w, Image.SCALE_FAST);
		if (mapImage == null) {
			mapImage = new JLabel(new ImageIcon(scaled));
			stockPanel.add(mapImage);
		} else {
			mapImage.setIcon(new ImageIcon(scaled));
		}
		mapImage.updateUI();
	}
	
	private void updatePalletsPanel() {
		incomingPanel.setViewportView(getPalletsPanel());
		incomingPanel.updateUI();
	}

	private Image getCurrentImage() {
		int scale = 50;
		int w = StockManager.WIDTH * scale * 2 + scale;
		int h = StockManager.NUMBEROFSTACKS * scale;
		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();		
		//init white image
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, w, h);

		//draw conveyor
		Pallet[] pallets = incomingPalletManager.getPallets();
		g.setStroke(new BasicStroke(2));
		g.setFont(normalFont);
		for (int i = 0 ; i < pallets.length; i++) {
			g.setColor(Color.BLACK);
			g.drawRect(0, i * scale, scale, scale );
			if (pallets[i] != null) {
				Pallet p = pallets[i];
				int size = p.getWidth() / 10;
				g.setColor(palletColor);
				switch (size) {
					case 3:
						g.fillRect(scale / 3, i * scale + scale / 3, scale/3 , scale/3 );
						break;
					case 6:
						g.fillRect(scale/4, i * scale + scale / 4, scale/2 , scale /2);
						break;
					case 9:
						g.fillRect(0, i * scale, scale , scale);
						break;
				}
			}
		}

		//draw stacks
		g.setStroke(new BasicStroke(3));
		g.setFont(normalFont);
		for (int i = 0; i < stacks.length; i++) {
			g.setColor(Color.BLACK);
			g.drawRect(StockManager.WIDTH*scale, i * scale, scale, scale );
			String text = "";
			int height = scale - 10;
			for (int j = 0; j < StockManager.STACKHEIGHT; j++) {
				if (stacks[i].get(j) != null) {
					Pallet p = stacks[i].get(j);
					int factor = StockManager.WIDTH * scale;
					g.setColor(palletColor);
					switch (p.getWidth()) {
						case
							30:
							g.drawLine(factor + 20, i * scale + height, factor + 30, i * scale + height);
							break;
						case
							60:
							g.drawLine(factor + 15, i * scale + height, factor + 35, i * scale + height);
							break;
						case 90:
							g.drawLine(factor + 10, i * scale + height, factor + 40, i * scale + height);
							break;
					}
					height -= 10;
				}
			}
		}

		//draw outgoing zone
		g.setColor(outgoingZone);
		g.fillRect(StockManager.WIDTH * scale * 2, 0, scale, scale);

		//draw Wichtel and Elf
		g.setColor(Color.BLACK);
		g.setStroke(new BasicStroke(3));
		g.setFont(normalFont);
		for (WichtelController sc : WichtelController.getWichtelList()) {
			drawHelper(g, sc, scale, wichtelHat);
		}

		for (ElfController ec : ElfController.getElfList()) {
			drawHelper(g, ec, scale, elfHat);
		}

		g.dispose();
		return image;
	}

	private void drawHelper(Graphics2D g, Helper sc, int scale, Color hatColor) {
		g.setColor(wichelFace);
		int x = sc.getX() * scale;
		int y = sc.getY() * scale;
		g.fillOval(x+10, y+10, scale-20, scale-20);
		g.setColor(hatColor);
		g.fillArc(x-5, y-40, 60, 60, 250, 40);
				
		g.setColor(eyes);
		g.drawLine(x+18, y+25, x+20, y+25);
		g.drawLine(x + 27, y+25, x+29, y+25);
		g.drawLine(x+18, y+30, x+25, y+32);
		g.drawLine(x+26, y+32, x+29, y+30);
		
		g.setColor(Color.black);
		if (sc.getInventory() != null) {
			Pallet p = sc.getInventory();
			String text = p.getWidth() + "cm";
			g.drawChars(text.toCharArray(), 0, text.length(), x+50, y+25);
			g.setColor(palletColor);
			g.fillRect(x+35, y+35, 10 ,10 );
		}
	}
}
