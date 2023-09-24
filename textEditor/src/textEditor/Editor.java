/*
 * Hello! Some things to note while running this program: Files can be saved in two ways: .txt and .rtf
 * While saving the file, save it in this format: fileName.rtf or fileName.txt
 * Writing the extension name is necessary.
 * The SketchPad also works. After you press the rectangle or any shape button, you need to click and drag 
   your mouse across the sketch pad to draw the shape.
   
   I have put some ImageIcons in place. As you know, while using ImageIcons, I need to put the image paths
   for the image to load. The paths that I have specified are working for me...I have uploaded the src folder
   in the hopes that it'll work for you as well. If the images aren't visible in the buttons, 
   please try and change the path so that the code works perfectly. Thanks :) !

* Name: Aviral Chawla
* Roll No: 2110110151
* Lab Batch: P1
* Date: 9/12/22
*  
*/


package textEditor;

import java.awt.event.*;
import java.awt.font.TextAttribute;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.text.NumberFormat.Style;
import java.util.*;
import java.io.*;
import java.awt.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultHighlighter;
import javax.swing.text.Document;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;
import javax.swing.text.rtf.RTFEditorKit;


public class Editor {
	public static void main(String args[]) {
		MainFrame frame = new MainFrame();
	}
}


class MainFrame extends JFrame implements ActionListener, KeyListener, MouseListener{
	
	//JFrame helpWindow;
	JPanel font, mainText, charCount, find, combo,replace, findReplace,findReplaceButtons, shapeButtons, sketchPad, sk, wordCountPanel, selectedWordPanel;
	JMenuBar menuBar;
	JMenu fileMenu, editMenu, reviewMenu, helpMenu;
	JMenuItem newFile, saveFile, openFile, exitFile, cut, copy, paste, helpWindow, reviewWindow;
	JTextArea text;
	JLabel charC, wordC, selectedCharC, selectedWordC;
	JButton bold, italics, underline, strike, findAll, findNext, replaceIt, replaceAll, rectangle, oval, line, triangle, pentagon, clear;
	JComboBox<String> fontType;
	JToggleButton leftAlign, centerAlign, rightAlign, middleAlign;
	JComboBox<Integer> fontSize;
	JTextPane text2;
	boolean boldCheck = false, italicCheck = false, underlineCheck = false, strikeCheck = false;
	String fontName, textToFind, para, textToReplace;
	Integer sizeFont;
	int startPos =0, endPos, startPos2 =0, endPos2;
	JLabel findText, replaceText ;
	JTextField findField, replaceField;
	RTFEditorKit outFile;
	Object prev;
	ButtonGroup align;
	BufferedImage image1, image2, image3, image4, image5, image6, image7;
	//JOptionPane helpPane;
	ImageIcon left_align, center_align, right_align, justify_align,t_logo, notepad_logo, under_construction;
	KeyStroke shortcutLoad, shortcutSave, shortcutNew, shortcutExit, shortcutCopy, shortcutCut, shortcutPaste;
	public MainFrame() {
		
		geoShape geo = new geoShape();
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(700,700);
		this.setTitle("Text Editor");
		//this.setLayout(null);
		this.setLayout(new BorderLayout());

		outFile = new RTFEditorKit();
		align = new ButtonGroup();
		//helpWindow = new JFrame("Help");
		//helpWindow.setSize(100,100);
		//helpWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		String[] fontList = {"Arial", "Calibri", "Times New Roman", "Agency FB", "Centaur", "Bradley Hand ITC", "Freestyle Script","Cambrian", "Century Gothic", "Comic Sans MS", "Courier New", "Forte", "Garamond", "Monospaced", "Segoe UI", "Trebuchet MS", "Serif"};
		Integer[] fontSizeList = {8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30};
		fontType = new JComboBox<String>(fontList);
		fontSize = new JComboBox<Integer>(fontSizeList);
		try {
			image1 = ImageIO.read(getClass().getResource("/resources/images/left_align.png"));
			image2 = ImageIO.read(getClass().getResource("/resources/images/center_align.png"));
			image3 = ImageIO.read(getClass().getResource("/resources/images/right_align.png"));
			image4 = ImageIO.read(getClass().getResource("/resources/images/justify_align.png"));
			image5 = ImageIO.read(getClass().getResource("/resources/images/notepad_logo.png"));
			image6 = ImageIO.read(getClass().getResource("/resources/images/under_construction.png"));
			image7 = ImageIO.read(getClass().getResource("/resources/images/t_logo.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		left_align = new ImageIcon(image1);
		//left_align = new ImageIcon(getClass().getResource("/images/left_align"));
		center_align = new ImageIcon(image2);
		right_align = new ImageIcon(image3);
		justify_align = new ImageIcon(image4);
		t_logo = new ImageIcon(image7);
		notepad_logo = new ImageIcon(image5);
		under_construction = new ImageIcon(image6);
		
		this.setIconImage(t_logo.getImage());
		
		fontType.addActionListener(this);
		fontSize.addActionListener(this);
		
		fontName = "Arial";
		sizeFont = 8;
		
		findText = new JLabel("Find");
		replaceText = new JLabel("Replace");
		
		
		findField = new JTextField();
		replaceField = new JTextField();
		
		font = new JPanel(new FlowLayout(FlowLayout.LEADING));
		wordCountPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		selectedWordPanel = new JPanel(new FlowLayout(FlowLayout.LEADING));
		mainText = new JPanel(new BorderLayout());
		
		charCount = new JPanel(new BorderLayout());
		find = new JPanel(new FlowLayout(FlowLayout.LEADING));
		replace = new JPanel(new FlowLayout(FlowLayout.LEADING));
		//combo = new JPanel(new BoxLayout(combo, BoxLayout.Y_AXIS));
		findReplace = new JPanel(new BorderLayout());
		combo = new JPanel(new BorderLayout());
		findReplaceButtons = new JPanel();
		
		
		bold = new JButton("B");
		italics = new JButton("I");
		underline = new JButton("U");
		strike = new JButton("S");
		leftAlign = new JToggleButton(left_align);
		centerAlign = new JToggleButton(center_align);
		rightAlign = new JToggleButton(right_align);
		middleAlign = new JToggleButton(justify_align);
		//leftAlign = new JButton("Left");
		//centerAlign = new JButton("Center");
		//rightAlign = new JButton("Right");
		//middleAlign = new JButton("Middle");
		//selectedWordCount = new JButton("Count Words");
		findNext = new JButton("Find Next");
		findAll = new JButton("Find All");
		replaceIt = new JButton("Replace");
		replaceAll = new JButton("Replace All");
		
	
		
		bold.setFocusable(false);
		italics.setFocusable(false);
		underline.setFocusable(false);
		strike.setFocusable(false);
		leftAlign.setFocusable(false);
		centerAlign.setFocusable(false);
		rightAlign.setFocusable(false);
		middleAlign.setFocusable(false);
		findNext.setFocusable(false);
		findAll.setFocusable(false);
		replaceIt.setFocusable(false);
		replaceAll.setFocusable(false);
		//selectedWordCount.setFocusable(false);
		
		//rightAlign.setSize(new Dimension(20,20));
		
		bold.addActionListener(this);
		italics.addActionListener(this);
		underline.addActionListener(this);
		strike.addActionListener(this);
		leftAlign.addActionListener(this);
		centerAlign.addActionListener(this);
		rightAlign.addActionListener(this);
		middleAlign.addActionListener(this);
		findNext.addActionListener(this);
		findAll.addActionListener(this);
		replaceIt.addActionListener(this);
		replaceAll.addActionListener(this);
		//selectedWordCount.addActionListener(this);
		
		Map<TextAttribute, Object> under = new Hashtable<TextAttribute, Object>();
		under.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
		
		Map<TextAttribute, Object> st = new Hashtable<TextAttribute, Object>();
		st.put(TextAttribute.STRIKETHROUGH, TextAttribute.STRIKETHROUGH_ON);
		
		bold.setFont(new Font("Arial", Font.BOLD, 25));
		underline.setFont(new Font("Arial", Font.PLAIN, 25).deriveFont(under));
		italics.setFont(new Font("Arial", Font.ITALIC, 25));
		strike.setFont(new Font("Arial", Font.PLAIN, 25).deriveFont(st));
		
		leftAlign.setFont(new Font("Arial",Font.PLAIN, 25));
		centerAlign.setFont(new Font("Arial",Font.PLAIN, 25));
		rightAlign.setFont(new Font("Times New Roman",Font.PLAIN, 25));
		middleAlign.setFont(new Font("Centaur",Font.PLAIN, 25));
		
		findNext.setFont(new Font("Arial", Font.PLAIN, 25));
		findAll.setFont(new Font("Arial", Font.PLAIN, 25));
		replaceIt.setFont(new Font("Arial", Font.PLAIN, 25));
		replaceAll.setFont(new Font("Arial", Font.PLAIN, 25));
		
		fontType.setFont(new Font ("Arial", Font.PLAIN,20));
		fontSize.setFont(new Font ("Arial", Font.PLAIN,20));
		
		findText.setFont(new Font("Arial", Font.PLAIN, 20));
		replaceText.setFont(new Font("Arial", Font.PLAIN, 20));
		
		//selectedWordCount.setFont(new Font("Arial",Font.PLAIN,25));
		
		
		charC = new JLabel("Character Count: 0");
		wordC = new JLabel("Word Count: 0");
		selectedCharC = new JLabel("Selected Character Count: 0");
		selectedWordC = new JLabel("Selected Word Count: 0");
		
		//font.setBackground(Color.red);
		combo.setPreferredSize(new Dimension(1000,200));
		font.setPreferredSize(new Dimension(1000,65));
		find.setPreferredSize(new Dimension(50,50));
		replace.setPreferredSize(new Dimension(50,50));
		findField.setPreferredSize(new Dimension(600,30));
		replaceField.setPreferredSize(new Dimension(600,30));
		
		//charCount.setBackground(new Color(0xADD8E6));
		charCount.setBackground(new Color(0xc1ccd4));
		charCount.setPreferredSize(new Dimension(1000,25));
		wordCountPanel.setBackground(new Color(0xc1ccd4));
		selectedWordPanel.setBackground(new Color(0xc1ccd4));
		
		align.add(leftAlign);
		align.add(rightAlign);
		align.add(centerAlign);
		align.add(middleAlign);
		
		//find.setBackground(Color.red);
		
		mainText.setBorder(BorderFactory.createLineBorder(Color.black));
		
		text2 = new JTextPane();
		//text2.setEditable(true);
		text2.addKeyListener(this);
		text2.addMouseListener(this);
		//StyledDocument doc = text2.getStyledDocument();
		//addStylesToDocument(doc);
		
		
		
		
		menuBar = new JMenuBar();
		fileMenu = new JMenu("File");
		editMenu = new JMenu("Edit");
		reviewMenu = new JMenu("Reiview");
		helpMenu = new JMenu("Help");
		
		newFile = new JMenuItem("New File");
		saveFile = new JMenuItem("Save As");
		openFile = new JMenuItem("Open");
		exitFile = new JMenuItem("Exit");
		
		cut = new JMenuItem("Cut");
		copy = new JMenuItem("Copy");
		paste = new JMenuItem("Paste");
		
		helpWindow = new JMenuItem("About");
		reviewWindow = new JMenuItem("Review");
		
		newFile.addActionListener(this);
		saveFile.addActionListener(this);
		openFile.addActionListener(this);
		exitFile.addActionListener(this);
		
		cut.addActionListener(this);
		copy.addActionListener(this);
		paste.addActionListener(this);
		helpWindow.addActionListener(this);
		reviewWindow.addActionListener(this);
		
		fileMenu.setMnemonic(KeyEvent.VK_F);
		editMenu.setMnemonic(KeyEvent.VK_E);
		reviewMenu.setMnemonic(KeyEvent.VK_R);
		helpMenu.setMnemonic(KeyEvent.VK_H);
		
		shortcutLoad = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
		shortcutSave = KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_DOWN_MASK);
		shortcutNew = KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_DOWN_MASK);
		shortcutExit = KeyStroke.getKeyStroke(KeyEvent.VK_E, KeyEvent.CTRL_DOWN_MASK);
		shortcutCut = KeyStroke.getKeyStroke(KeyEvent.VK_X, KeyEvent.CTRL_DOWN_MASK);
		shortcutCopy = KeyStroke.getKeyStroke(KeyEvent.VK_C, KeyEvent.CTRL_DOWN_MASK);
		shortcutPaste = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
		
		newFile.setAccelerator(shortcutNew);
		saveFile.setAccelerator(shortcutSave);
		openFile.setAccelerator(shortcutLoad);
		exitFile.setAccelerator(shortcutExit);
		cut.setAccelerator(shortcutCut);
		copy.setAccelerator(shortcutCopy);
		paste.setAccelerator(shortcutPaste);
		
		
		
		fileMenu.add(newFile);
		fileMenu.add(saveFile);
		fileMenu.add(openFile);
		fileMenu.add(exitFile);
		
		editMenu.add(cut);
		editMenu.add(copy);
		editMenu.add(paste);
		
		reviewMenu.add(reviewWindow);
		
		helpMenu.add(helpWindow);
		
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(reviewMenu);
		menuBar.add(helpMenu);
		
		font.add(bold);
		font.add(italics);
		font.add(underline);
		font.add(strike);
		font.add(leftAlign);
		font.add(centerAlign);
		font.add(rightAlign);
		font.add(middleAlign);
		font.add(fontType);
		font.add(fontSize);
		
		find.add(findText);
		find.add(findField);
		//find.add(selectedWordCount);
		
		
		
		replace.add(replaceText);
		replace.add(replaceField);
		
		findReplaceButtons.add(findNext);
		findReplaceButtons.add(findAll);
		findReplaceButtons.add(replaceIt);
		findReplaceButtons.add(replaceAll);
		
		combo.add(font,BorderLayout.PAGE_START);
		combo.add(findReplace,BorderLayout.CENTER);
		findReplace.add(find,BorderLayout.NORTH);
		findReplace.add(replace,BorderLayout.CENTER);
		findReplace.add(findReplaceButtons, BorderLayout.SOUTH);
		
		mainText.add(text2);
		mainText.add(combo,BorderLayout.PAGE_START);
		
		wordCountPanel.add(wordC);
		wordCountPanel.add(charC);
		selectedWordPanel.add(selectedWordC);
		selectedWordPanel.add(selectedCharC);
		
		
		charCount.add(wordCountPanel,BorderLayout.WEST);
		charCount.add(selectedWordPanel,BorderLayout.EAST);
		
		this.setJMenuBar(menuBar);
		//this.add(font, BorderLayout.PAGE_START);
		this.add(mainText, BorderLayout.CENTER);
		this.add(geo, BorderLayout.EAST);
		this.add(charCount, BorderLayout.PAGE_END);
		//this.add(sl);
		
		
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		StyleConstants.setFontFamily(attributeSet, fontName);
		StyleConstants.setFontSize(attributeSet, sizeFont);
		text2.setCharacterAttributes(attributeSet,true);
		
		this.pack();
		this.setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		SimpleAttributeSet attributeSet = new SimpleAttributeSet();
		DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
		StyleConstants.setFontFamily(attributeSet, fontName);
		StyleConstants.setFontSize(attributeSet, sizeFont);
		text2.getHighlighter().removeAllHighlights();
		if (boldCheck == false) {
			StyleConstants.setBold(attributeSet, false);
		}
		else {
			StyleConstants.setBold(attributeSet, true);
		}
		
		if (italicCheck == false) {
			StyleConstants.setItalic(attributeSet, false);
		}
		else {
			StyleConstants.setItalic(attributeSet, true);
		}
		
		if (underlineCheck == false) {
			StyleConstants.setUnderline(attributeSet, false);
		}
		else {
			StyleConstants.setUnderline(attributeSet, true);
		}
		
		if (strikeCheck == false) {
			StyleConstants.setStrikeThrough(attributeSet, false);
		}
		else {
			StyleConstants.setStrikeThrough(attributeSet, true);
		}
		
		
		if (e.getSource() == newFile) {
			text2.setText("");
			StyleConstants.setBold(attributeSet, false);
			StyleConstants.setItalic(attributeSet, false);
			StyleConstants.setUnderline(attributeSet, false);
			StyleConstants.setStrikeThrough(attributeSet, false);
			boldCheck = false;
			italicCheck = false;
			underlineCheck = false;
			strikeCheck = false;
			
		}
		
		if (e.getSource() == saveFile) {
			JFileChooser save = new JFileChooser(new File("c:\\"));
			save.setDialogTitle("Save File");
			save.setFileFilter(new FileType(".txt", "Text File"));
			save.setFileFilter(new FileType(".rtf", "RTF File"));
			//save.setFileFilter(new FileType(".doc", "Doc File"));
			//save.setFileFilter(new FileType(".docx", "Docx File"));
			//save.setFileFilter(new FileType(".pdf", "PDF File"));
			//save.showSaveDialog(null);
			
			int result = save.showSaveDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				String content = text2.getText();
				File f = save.getSelectedFile();
				try {
					FileOutputStream fw = null;
					fw = new FileOutputStream(f);
					outFile.write(fw, text2.getStyledDocument(), 0, text2.getStyledDocument().getLength());
					/*
					FileWriter fw = new FileWriter(f.getPath());
					fw.write(content);
					fw.flush();
					fw.close();
					*/
				}
				catch(Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
			}
			
		}
		
		if (e.getSource() == openFile) {
			JFileChooser open = new JFileChooser(new File("c:\\"));
			open.setDialogTitle("Open a File");
			open.setFileFilter(new FileType(".txt", "Text File"));
			open.setFileFilter(new FileType(".rtf", "RTF File"));
			//open.setFileFilter(new FileType(".doc", "Doc File"));
			//open.setFileFilter(new FileType(".docx", "Docx File"));
			//open.setFileFilter(new FileType(".pdf", "PDF File"));
			
			int result = open.showOpenDialog(this);
			if (result == JFileChooser.APPROVE_OPTION) {
				try {
					File f = open.getSelectedFile();
					FileInputStream stream = new FileInputStream(f);
					//RTFEditorKit kit = new RTFEditorKit();
					StyledDocument doc = (StyledDocument) outFile.createDefaultDocument();
					outFile.read(stream, doc, 0);
					text2.setStyledDocument(doc);
					/*FileInputStream fr = null;
					fr = new FileInputStream(f);
					outFile.read(fr, text2.setStyledDocument(), 0, text2.setStyledDocument().getLength());*/
					/*BufferedReader br = new BufferedReader(new FileReader(f.getPath()));
					String line = "";
					String s = "";
					while ((line=br.readLine()) != null) {
						s = s + line;
					}
					text2.setText(s);
					if (br != null) {
						br.close();
					}
					*/
				}
				catch (Exception e2) {
					JOptionPane.showMessageDialog(null, e2.getMessage());
				}
				
			}
		}
		
		if (e.getSource() == exitFile) {
			System.exit(0);
		}
		
		if (e.getSource() == cut) {
			//text2.requestFocus();
			text2.cut();
		}
		
		if (e.getSource() == copy) {
			text2.copy();
		}
		
		if (e.getSource() == paste) {
			text2.paste();
		}
		
		if (e.getSource() == bold) {
			if (boldCheck == false) {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				StyleConstants.setBold(attributeSet, true);
				text2.setCharacterAttributes(attributeSet,true);
				boldCheck=true;
			}
			else {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				StyleConstants.setBold(attributeSet, false);
				text2.setCharacterAttributes(attributeSet,true);
				boldCheck=false;
				
			}
		}
		
		if (e.getSource() == italics) {
			if (italicCheck == false) {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				StyleConstants.setItalic(attributeSet, true);
				text2.setCharacterAttributes(attributeSet,true);
				italicCheck=true;
			}
			else {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				StyleConstants.setItalic(attributeSet, false);
				text2.setCharacterAttributes(attributeSet,true);
				italicCheck=false;
				
			}
		}
		
		if (e.getSource() == underline) {
			if (underlineCheck == false) {
				
				StyleConstants.setUnderline(attributeSet, true);
				text2.setCharacterAttributes(attributeSet,true);
				underlineCheck=true;
			}
			else {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				StyleConstants.setUnderline(attributeSet, false);
				text2.setCharacterAttributes(attributeSet,true);
				underlineCheck=false;
				
			}
		}
		if (e.getSource() == strike) {
			if (strikeCheck == false) {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				
				StyleConstants.setStrikeThrough(attributeSet, true);
				text2.setCharacterAttributes(attributeSet,true);
				strikeCheck=true;
			}
			else {
				//SimpleAttributeSet attributeSet = new SimpleAttributeSet();
				StyleConstants.setStrikeThrough(attributeSet, false);
				text2.setCharacterAttributes(attributeSet,true);
				strikeCheck=false;
				
			}
		}
		
		if (e.getSource() == fontType) {
			fontName = (String) fontType.getSelectedItem();
			StyleConstants.setFontFamily(attributeSet, fontName);
			text2.setCharacterAttributes(attributeSet,true);
		}
		
		if (e.getSource() == fontSize) {
			sizeFont = (Integer) fontSize.getSelectedItem();
			StyleConstants.setFontSize(attributeSet, sizeFont);
			text2.setCharacterAttributes(attributeSet,true);
		}
		
		if (e.getSource() == leftAlign) {
			StyledDocument styledDocument = text2.getStyledDocument();
			StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_LEFT);
			styledDocument.setParagraphAttributes(0, styledDocument.getLength(), attributeSet, false);
		}
		
		if (e.getSource() == centerAlign) {
			StyledDocument styledDocument = text2.getStyledDocument();
			StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_CENTER);
			styledDocument.setParagraphAttributes(0, styledDocument.getLength(), attributeSet, false);
		}
		
		if (e.getSource() == rightAlign) {
			StyledDocument styledDocument = text2.getStyledDocument();
			
			StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_RIGHT);
			//text2.setCharacterAttributes(attributeSet,true);
			styledDocument.setParagraphAttributes(0, styledDocument.getLength(), attributeSet, false);
			//System.out.println("Called Right");
		}
		
		if (e.getSource() == middleAlign) {
			StyledDocument styledDocument = text2.getStyledDocument();
			StyleConstants.setAlignment(attributeSet, StyleConstants.ALIGN_JUSTIFIED);
			styledDocument.setParagraphAttributes(0, styledDocument.getLength(), attributeSet, false);
			
		}
		
		if (e.getSource() == findNext) {
			textToFind = findField.getText();
			Document document = text2.getDocument();
			try {
				para = document.getText(0, document.getLength());
			} catch (BadLocationException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			startPos = para.indexOf(textToFind,startPos);
			
			if (startPos==-1) {
				//System.out.println("hihi");
				startPos = para.indexOf(textToFind,startPos);
			}
			endPos = ((textToFind.length())) + startPos;
			
			//System.out.println()
			
			//DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
			try {
					if (prev != null){
			            text2.getHighlighter().removeHighlight(prev);
			        }
			        prev = text2.getHighlighter().addHighlight(startPos, endPos, highlightPainter);
			        startPos++;

				//text2.getHighlighter().addHighlight(startPos, endPos, highlightPainter);
			}
			catch (Exception e4) {
				//JOptionPane.showMessageDialog(null, e4.getMessage());
			}

		}
		
		if (e.getSource() == findAll) {
			textToFind = findField.getText();
			//System.out.println(textToFind);
			//System.out.println(textToFind.length());
			if (textToFind.length() != 0) {
				Document document = text2.getDocument();
				try {
					para = document.getText(0, document.getLength());
				} catch (BadLocationException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				while (true) {
					startPos2 = para.indexOf(textToFind,startPos2);
					if (startPos2==-1) {
						break;
					}
					endPos2 = ((textToFind.length())) + startPos2;
					//DefaultHighlighter.DefaultHighlightPainter highlightPainter = new DefaultHighlighter.DefaultHighlightPainter(Color.YELLOW);
					try {
							
							
							text2.getHighlighter().addHighlight(startPos2, endPos2, highlightPainter);
							//System.out.println(startPos2+","+endPos2);
							//System.out.println(para.substring(startPos2,endPos2));
							startPos2++;
						
					}
					catch (Exception e5) {
						
					}
				}
			}
		}
		
		if (e.getSource() ==replaceIt) {
			textToFind = findField.getText();
			textToReplace = replaceField.getText();
			para = text2.getText();
			try {
				if (prev!=null) {
					para = para.substring(0,startPos-1) + (textToReplace) + para.substring(endPos);
					
				}
				else {
				//System.out.println(para);
					startPos = para.indexOf(textToFind,startPos);
					//System.out.println(startPos);
					endPos = ((textToFind.length())) + startPos;
					para = para.replaceFirst(textToFind, textToReplace);
				}
				text2.setText(para);
			}
			catch (IndexOutOfBoundsException ee) {
				
			}
			//text2.setCharacterAttributes(attributeSet,true);
		}
		
		if (e.getSource() == replaceAll) {
			textToFind = findField.getText();
			textToReplace = replaceField.getText();
			para = text2.getText();
			
			//System.out.println(para);
			startPos = para.indexOf(textToFind,startPos);
			//System.out.println(startPos);
			endPos = ((textToFind.length())) + startPos;
			para = para.replace(textToFind, textToReplace);
			text2.setText(para);
			//text2.setCharacterAttributes(attributeSet,true);
		}
		
		if (e.getSource() == helpWindow) {
			String[] response = {"Exit"};
			String message = "Hello! This program has been made by yours truly:- Aviral Chawla \nAfter my testing, this Text Editor should function normally. If you happen to face any errors, please contact me through mail. \nEmail: ac724@snu.edu.in";
			JOptionPane.showOptionDialog(null, message,"Help",JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,notepad_logo,response,0);
		}
		
		if (e.getSource() == reviewWindow) {
			String[] response = {"Exit"};
			String message = "Hello! This program is still under construction. Any reviews would be appreciated! \nEmail: ac724@snu.edu.in";
			JOptionPane.showOptionDialog(null, message,"Review",JOptionPane.CANCEL_OPTION, JOptionPane.INFORMATION_MESSAGE,under_construction,response,0);
		}
		
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		Document document = text2.getDocument();
		try {
			String w = document.getText(0, document.getLength());
			String words[]=w.split("\\s");  
		    wordC.setText("Word Count: "+words.length);  
		    charC.setText("Character Count: "+w.length()); 
		} catch (BadLocationException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		//String w=text2.getText();  
	    
		
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	
		String selectedText = text2.getSelectedText();
		if (selectedText!=null) {
			String selectedwords[]=selectedText.split("\\s");  
		    selectedWordC.setText("Selected Word Count: "+selectedwords.length);  
		    selectedCharC.setText("Selected Character Count: "+selectedText.length()); 
		}
		else {
			selectedWordC.setText("Selected Word Count: 0");  
		    selectedCharC.setText("Selected Character Count: 0");
		}
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}


class FileType extends FileFilter {

	private final String extension;
	private final String description;
	
	public FileType(String extension, String description) {
		this.extension = extension;
		this.description = description;
	}
	
	@Override
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f.isDirectory()) {
			return true;
		}
		return f.getName().endsWith(extension);
	}

	@Override
	public String getDescription() {
		// TODO Auto-generated method stub
		return description + String.format(" (*%s)", extension);
	}
	
}

class HelpWindow extends JFrame {
	JLabel t;
	public HelpWindow() {
		//this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Help");
		this.setSize(200,200);
		
		t = new JLabel("HELLO I AM Aviral");
		this.add(t);
		this.pack();
		this.setVisible(true);
		
		
	}
}

class Oval {
	int x;
	int y;
	int breadth;
	int length;
	
	public Oval(int x, int y, int breadth, int length) {
		this.x = x;
		this.y = y;
		this.breadth = breadth;
		this.length = length;
	}
	
	public void setBounds(int x, int y, int breadth, int length) {
		this.x = x;
		this.y = y;
		this.breadth = breadth;
		this.length = length;
	}
}


class Line {
	int x1;
	int y1;
	int x2;
	int y2;
	
	public Line(int x1, int y1, int x2,int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
	
	public void setBounds(int x1, int y1, int x2,int y2) {
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
	}
}

class Triangle {
	int[] x;
	int[] y;
	int n;
	
	public Triangle(int[] x, int[] y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
	
	public void setBounds(int[] x, int[] y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
}

class Pentagon {
	int[] x;
	int[] y;
	int n;
	
	public Pentagon(int[] x, int[] y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
	
	public void setBounds(int[] x, int[] y, int n) {
		this.x = x;
		this.y = y;
		this.n = n;
	}
}

class geoShape extends JPanel implements ActionListener, MouseListener{
	
	JToggleButton rectangle, oval, line, triangle, pentagon, clear;
	//JButton clear;
	ButtonGroup group;
	JPanel sk, shapeButtons,sketchPad;
	JLabel sketch;
	MouseAdapter mouseAdapter;
	Rectangle newRectangle;
	Oval newOval;
	Line newLine;
	Triangle newTriangle;
	Pentagon newPentagon;
	Polygon h,m;
	//Oval newOval;
	boolean draw = false, ovalCheck = false;
	ArrayList<Rectangle> savedRectangles;
	ArrayList<Oval> savedOvals;
	ArrayList<Line> savedLines;
	ArrayList<Triangle> savedTriangles;
	ArrayList<Pentagon> savedPentagons;
	
	public geoShape() {
		this.setLayout(new BorderLayout());
		//this.setBackground(Color.gray);
		this.setBackground(new Color(0xc1ccd4));
		this.setPreferredSize(new Dimension(650,400));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		
		rectangle = new JToggleButton("Rectangle");
		oval = new JToggleButton("Oval");
		line = new JToggleButton("Line");
		triangle = new JToggleButton("Triangle");
		pentagon = new JToggleButton("Pentagon");
		clear = new JToggleButton("CLEAR");
		
		sketch = new JLabel("SketchPad");
		
		rectangle.setFocusable(false);
		oval.setFocusable(false);
		triangle.setFocusable(false);
		pentagon.setFocusable(false);
		clear.setFocusable(false);
		line.setFocusable(false);
		
		group = new ButtonGroup();
		group.add(rectangle);
		group.add(oval);
		group.add(line);
		group.add(triangle);
		group.add(pentagon);
		group.add(clear);
		
		sketchPad = new JPanel(new BorderLayout());
		shapeButtons = new JPanel(new FlowLayout(FlowLayout.LEADING));
		sk = new JPanel();
		
		rectangle.setFont(new Font("Arial", Font.PLAIN, 20));
		oval.setFont(new Font("Arial", Font.PLAIN, 20));
		line.setFont(new Font("Arial", Font.PLAIN, 20));
		triangle.setFont(new Font("Arial", Font.PLAIN, 20));
		pentagon.setFont(new Font("Arial", Font.PLAIN, 20));
		clear.setFont(new Font("Arial", Font.PLAIN, 20));
		sketch.setFont(new Font("Arial", Font.BOLD, 20));
		//System.out.println("DSBHDBS");
		
		rectangle.addActionListener(this);
		oval.addActionListener(this);
		line.addActionListener(this);
		triangle.addActionListener(this);
		pentagon.addActionListener(this);
		clear.addActionListener(this);
		savedRectangles = new ArrayList<Rectangle>();
		savedOvals = new ArrayList<Oval>();
		savedLines = new ArrayList<Line>();
		savedTriangles = new ArrayList<Triangle>();
		savedPentagons = new ArrayList<Pentagon>();
		//sk.add(sketch);
		shapeButtons.add(rectangle);
		shapeButtons.add(oval);
		shapeButtons.add(line);
		shapeButtons.add(triangle);
		shapeButtons.add(pentagon);
		shapeButtons.add(clear);
		
		sk.add(sketch);
		
		sketchPad.add(sk,BorderLayout.PAGE_START);
		sketchPad.add(shapeButtons,BorderLayout.CENTER);
		
		this.add(sketchPad,BorderLayout.PAGE_START);
		
	
	}
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g ;
		for (Rectangle a: savedRectangles) {
			if (a!=null) {
				g2.draw(a);
			}
		}
		
		for (Oval a: savedOvals) {
			if (a!=null) {
				g2.drawOval(a.x,a.y,a.breadth,a.length);
			}
		}
		
		for (Line a: savedLines) {
			if (a!=null) {
				g2.drawLine(a.x1,a.y1,a.x2,a.y2);
			}
		}
		
		for (Triangle a: savedTriangles) {
			if (a!=null) {
				h = new Polygon(a.x,a.y,a.n);
				g2.drawPolygon(h);
			}
		}
		
		for (Pentagon a: savedPentagons) {
			if(a!=null) {
				m = new Polygon(a.x,a.y,a.n);
				g2.drawPolygon(m);
			}
		}
		
	}
		 
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
			if (e.getSource() == rectangle) {
				this.removeMouseListener(mouseAdapter);
				this.removeMouseMotionListener(mouseAdapter);
				repaint();
				
				mouseAdapter = new MouseAdapter() {
					Point initialPoint;
					
					@Override
					public void mousePressed(MouseEvent e) {
						//System.out.println("Pressed");
						initialPoint=e.getPoint();
						newRectangle = null;
					}
					
					@Override 
					public void mouseDragged(MouseEvent e) {
						//System.out.println("Mouse dragged");
						Point finalPoint = e.getPoint();
						int x = Math.min(initialPoint.x, finalPoint.x);
						int y = Math.min(initialPoint.y, finalPoint.y);
						
						int breadth = Math.max(initialPoint.x, finalPoint.x) - x;
						int length = Math.max(initialPoint.y, finalPoint.y) - y;
						
						if (newRectangle == null) {
							newRectangle = new Rectangle(x,y,breadth,length);
							
						}
						else {
							newRectangle.setBounds(x,y,breadth,length);
						}
						savedRectangles.add(newRectangle);
						repaint();
					}
					
					@Override 
					public void mouseReleased(MouseEvent e) {
						newRectangle =null;
						
					}
					
				};
				this.addMouseListener(mouseAdapter);
				this.addMouseMotionListener(mouseAdapter);
			}
		
			if (e.getSource() == oval) {
				this.removeMouseListener(mouseAdapter);
				this.removeMouseMotionListener(mouseAdapter);
				//draw=true;
				repaint();
				
				mouseAdapter = new MouseAdapter() {
					Point initialPoint;
					
					@Override
					public void mousePressed(MouseEvent e) {
						//System.out.println("Pressed");
						initialPoint=e.getPoint();
						newOval = null;
					}
					
					@Override 
					public void mouseDragged(MouseEvent e) {
						//System.out.println("Mouse dragged");
						Point finalPoint = e.getPoint();
						int x = Math.min(initialPoint.x, finalPoint.x);
						int y = Math.min(initialPoint.y, finalPoint.y);
						
						int breadth = Math.max(initialPoint.x, finalPoint.x) - x;
						int length = Math.max(initialPoint.y, finalPoint.y) - y;
						
						if (newOval == null) {
							newOval = new Oval(x,y,breadth,length);
							//newRectangle = new Rectangle(x,y,breadth,length);
							
						}
						else {
							newOval.setBounds(x,y,breadth,length);
						}
						savedOvals.add(newOval);
						repaint();
					}
					
					@Override 
					public void mouseReleased(MouseEvent e) {
						newOval =null;
						
					}
					
				};
				this.addMouseListener(mouseAdapter);
				this.addMouseMotionListener(mouseAdapter);
			}
			
			
			if (e.getSource() == line) {
				this.removeMouseListener(mouseAdapter);
				this.removeMouseMotionListener(mouseAdapter);
				repaint();
				mouseAdapter = new MouseAdapter() {
					Point initialPoint;
					
					@Override
					public void mousePressed(MouseEvent e) {
						//System.out.println("Pressed");
						initialPoint=e.getPoint();
						newLine = null;
					}
					
					@Override 
					public void mouseDragged(MouseEvent e) {
						//System.out.println("Mouse dragged");
						Point finalPoint = e.getPoint();
						
						if (newLine == null) {
							newLine = new Line(initialPoint.x,initialPoint.y,finalPoint.x,finalPoint.y);
							
						}
						else {
							newLine.setBounds(initialPoint.x,initialPoint.y,finalPoint.x,finalPoint.y);
						}
						savedLines.add(newLine);
						repaint();
					}
					
					@Override 
					public void mouseReleased(MouseEvent e) {
						newLine =null;
						
					}
					
				};
				this.addMouseListener(mouseAdapter);
				this.addMouseMotionListener(mouseAdapter);
			}
			
			if (e.getSource() == triangle) {
				this.removeMouseListener(mouseAdapter);
				this.removeMouseMotionListener(mouseAdapter);
				//draw=true;
				repaint();
				//newTriangle = new Triangle();
				mouseAdapter = new MouseAdapter() {
					Point initialPoint;
					
					@Override
					public void mousePressed(MouseEvent e) {
						//System.out.println("Pressed");
						initialPoint=e.getPoint();
						newTriangle = null;
					}
					
					@Override 
					public void mouseDragged(MouseEvent e) {
						//System.out.println("Mouse dragged");
						Point finalPoint = e.getPoint();
						int xp = Math.min(initialPoint.x, finalPoint.x);
						int yp = Math.min(initialPoint.y, finalPoint.y);
						
						int breadth = Math.max(initialPoint.x, finalPoint.x) - xp;
						int length = Math.max(initialPoint.y, finalPoint.y) - yp;
						
						int[] x = {initialPoint.x, finalPoint.x, ((initialPoint.x+finalPoint.x)/2)};
						int[] y = {finalPoint.y, finalPoint.y, initialPoint.y};
						
						if (newTriangle == null) {
							newTriangle = new Triangle(x,y,3);
							
						}
						else {
							newTriangle.setBounds(x,y,3);
							//newTriangle = new Polygon(x,y,3);
						}
						savedTriangles.add(newTriangle);
						repaint();
					}
					
					@Override 
					public void mouseReleased(MouseEvent e) {
						newTriangle =null;
						
					}
					
				};
				this.addMouseListener(mouseAdapter);
				this.addMouseMotionListener(mouseAdapter);
				
			}
			
			
			if (e.getSource() == pentagon) {
				this.removeMouseListener(mouseAdapter);
				this.removeMouseMotionListener(mouseAdapter);
				//draw=true;
				repaint();
				//newTriangle = new Triangle();
				mouseAdapter = new MouseAdapter() {
					Point initialPoint;
					
					@Override
					public void mousePressed(MouseEvent e) {
						//System.out.println("Pressed");
						initialPoint=e.getPoint();
						newPentagon = null;
					}
					
					@Override 
					public void mouseDragged(MouseEvent e) {
						//System.out.println("Mouse dragged");
						Point finalPoint = e.getPoint();
						int xp = Math.min(initialPoint.x, finalPoint.x);
						int yp = Math.min(initialPoint.y, finalPoint.y);
						
						int breadth = Math.max(initialPoint.x, finalPoint.x) - xp;
						int length = Math.max(initialPoint.y, finalPoint.y) - yp;
						
						int[] x = {initialPoint.x, finalPoint.x, finalPoint.x, ((initialPoint.x+finalPoint.x)/2), initialPoint.x};
						int[] y = {finalPoint.y, finalPoint.y, (initialPoint.y+finalPoint.y)/2, initialPoint.y, (initialPoint.y+finalPoint.y)/2};
						
						if (newPentagon == null) {
							newPentagon = new Pentagon(x,y,5);
							
						}
						else {
							newPentagon.setBounds(x,y,5);
							//newTriangle = new Polygon(x,y,3);
						}
						savedPentagons.add(newPentagon);
						repaint();
					}
					
					@Override 
					public void mouseReleased(MouseEvent e) {
						newPentagon =null;
						
					}
					
				};
				this.addMouseListener(mouseAdapter);
				this.addMouseMotionListener(mouseAdapter);
			}
			
			
			if (e.getSource() == clear) {
				savedRectangles.removeAll(savedRectangles);
				savedOvals.removeAll(savedOvals);
				savedLines.removeAll(savedLines);
				savedTriangles.removeAll(savedTriangles);
				savedPentagons.removeAll(savedPentagons);
				repaint();
				this.removeMouseListener(mouseAdapter);
				this.removeMouseMotionListener(mouseAdapter);
			}
		}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
	}
	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
}



