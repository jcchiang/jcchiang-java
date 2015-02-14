package gui;

import gui.io.ImageLoader;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.swing.*;

import system.DialogDetail;
import system.actor.BaseActor;
import system.actor.MainHero;

/**
 * Used to show user of a dialog that contains who said what.
 * Last Update: Create. at 2013/06/17 18:49
 * @author B98505005
 *
 */
public class DialogPanel extends JPanel{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField titleField, nameField;
	private JTextArea textArea;
	private BufferedImage bg, currentFace;
	private String[] realContent;
	private int showBegin = 0;
	
	public final static int DONE = -1, THIS_ONE_DONE = -2;
	private final int PHOTOWIDTH = 96, PHOTOHEIGHT = 96;
	private final int FACE_AND_NAME_OFFSET_Y = 10, FACE_AND_TEXT_OFFSET_X = 15;
	private final int NAME_WIDTH = PHOTOWIDTH, NAME_HEIGHT1 = 18, NAME_HEIGHT2 = 30;
	private final int TEXT_WIDTH, TEXT_HEIGHT;
	private final int BORDER_OFFSET = 15;
	
	private MainHero hero;
	private int conversationCounter;
	public boolean isPause = false;
	
	public DialogPanel(int width, int height){
		setLayout(null);
		setOpaque(false);
		setVisible(false);
		
		TEXT_WIDTH = width - (2 * BORDER_OFFSET + PHOTOWIDTH + FACE_AND_TEXT_OFFSET_X);
		TEXT_HEIGHT = height - 3 * BORDER_OFFSET;

		titleField = new JTextField();
		titleField.setOpaque(false);
		titleField.setBorder(null);
		titleField.setForeground(Color.WHITE);
		titleField.setHorizontalAlignment(JTextField.CENTER);
		titleField.setSize(width/2, 30);
		titleField.setBounds(BORDER_OFFSET, BORDER_OFFSET + PHOTOHEIGHT + FACE_AND_NAME_OFFSET_Y, 
							NAME_WIDTH, NAME_HEIGHT1);
		titleField.setEditable(false);
		titleField.setFont(new Font("�L�n������", Font.BOLD, 13));
		titleField.setText("�n���R����H");
		add(titleField);
		
		nameField = new JTextField();
		nameField.setOpaque(false);
		nameField.setBorder(null);
		nameField.setForeground(Color.WHITE);
		nameField.setHorizontalAlignment(JTextField.CENTER);
		nameField.setSize(width/2, 30);
		nameField.setBounds(BORDER_OFFSET, BORDER_OFFSET + PHOTOHEIGHT + FACE_AND_NAME_OFFSET_Y + NAME_HEIGHT1, 
							NAME_WIDTH, NAME_HEIGHT2);
		nameField.setEditable(false);
		nameField.setFont(new Font("�L�n������", Font.BOLD, 20));
		nameField.setText("��Ѳ�");
		add(nameField);
		
		textArea = new JTextArea();
		textArea.setForeground(Color.WHITE);
		textArea.setOpaque(false);
		textArea.setSize(width, height);
		textArea.setBounds(BORDER_OFFSET + PHOTOWIDTH + FACE_AND_TEXT_OFFSET_X, BORDER_OFFSET,
						   TEXT_WIDTH, TEXT_HEIGHT);
		textArea.setEditable(false);
		textArea.setFont(new Font("�L�n������", Font.BOLD, 20));
		
		add(textArea);
		
		try {
			currentFace = ImageLoader.getImage("face0");
			bg = ImageLoader.getImage("dialogBG");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}
	/*
	public static void main(String[] args){
		ImageLoader.initial();
		JFrame A = new JFrame();
		DialogPanel B = new DialogPanel(800,200);
		A.setSize(800,230);
		A.setResizable(false);
		A.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		A.setVisible(true);
		A.add(B);
	}
	*/
	
	public void beginConversation(DialogDetail toShow){
		//System.out.println("BEGIN!!");
		showBegin = 0;
		conversationCounter = 0;		
		keepConversation(toShow);
	}
	
	public void keepConversation(DialogDetail toShow){
		if(toShow.getSpeaker(conversationCounter).getID() == new MainHero().getID()){
			showDialog(hero, toShow.getContent(conversationCounter));
			try {
				currentFace = ImageLoader.getImage("face0");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else{
			showDialog(toShow.getSpeaker(conversationCounter), toShow.getContent(conversationCounter));
			try {
				currentFace = ImageLoader.getImage("face"+toShow.getSpeaker(conversationCounter).getID());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		/*
		if(toShow.getSpeaker(conversationCounter).getID() == new MainHero().getID()){
			showDialog(hero, toShow.getHeroContent(hero, conversationCounter));
			try {
				currentFace = ImageLoader.getImage("face0");
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		else{
			showDialog(toShow.getSpeaker(conversationCounter), toShow.getContent(conversationCounter));
			try {
				currentFace = ImageLoader.getImage("face"+toShow.getSpeaker(conversationCounter).getID());
			} catch (IOException e) {
				e.printStackTrace();
			};
		}
		*/
		conversationCounter++;
		if(conversationCounter == toShow.getDetailSize()){
			showBegin = DONE;
		}
	}
	
	public void showDialog(BaseActor talker, String said){
		titleField.setText(null);
		nameField.setText(talker.getName());
		realContent = said.split("<br>");
		showBegin = 0;
		keepShowingDialog();
		setVisible(true);
	}

	public void keepShowingDialog(){
		StringBuffer thisDialog = new StringBuffer();
		boolean isEnd = false;
		textArea.setText(null);
		
		for(int i=0; i<5; i++){
			if(realContent.length == 0){
				isEnd = true;
				break;
			}
			thisDialog.append(realContent[showBegin+i]+"\n");
			if(showBegin + i == realContent.length - 1){
				isEnd = true;
				break;
			}
		}
		textArea.setText(thisDialog.toString());
		if(!isEnd){
			showBegin += 5;
		}
		else{
			showBegin = THIS_ONE_DONE;
			isPause = true;
		}
	}
	
	public void hideDialog(){
		showBegin = DONE;
		setVisible(false);
	}
	
	public int getBeginLines(){
		return showBegin;
	}
	
	public void setMainHero(MainHero hero){
		this.hero = hero;
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bg, 0, 0, null);
        g.drawImage(currentFace, BORDER_OFFSET, BORDER_OFFSET, null); 
    }
	
	/*
	public void showDialog(int ID){
		if(ID == 0){
			content = new String(
					"�Ѧa������A���M��y�ΡC�U�h���e���A�W�h����P�C\n" +
					"��H��E�M�A�K�G��a�ߡC�Ӹ���M�i�A�t�M�R���x�C\n" +
					"�ɽa�`�D���A�@�@�����C�C�b���ӥv²�A�b�ʸ������C\n" +
					"�b���i�}�աA�b�~Ĭ�Z�`�C���Y�N�x�Y�A���R�ͤ���C\n" +
					"���i�F�����A���C�`�s�ޡC�ά���F�U�A�M�޼F�B���C\n" +
					"�ά��X�v��A�����_���P�C�ά��禿���A�B�n�]�J�~�C\n" +
					"�ά������C�A�f���Y�}���C�O��ҽS�A���P�U�j�s�C\n" +
					"���e���A�ͦ��w���סC�a����H�ߡA�ѬW��H�L�C\n" +
					"�T����ô�R�A�D�q�����ڡC�ؤ������E�A���]�ꤣ�O�C\n" +
					"���}�ը�a�A�Ǩ��e�a�_�C���i�̦p�~�A�D�����i�o�C\n" +
					"�����񰭤��A�K�|�ͤѶ¡C���k�P�@�m�A���ϻ�ĭ��C\n" +
					"�@�»X���S�A���@�����C�C�p���A�H���A���B�۹@���C\n" +
					"�s�v�q�v���A���ڦw�ְ�C�Z���L�[���A���������C\n" +
					"�U���կզb�A�����B���աC�y�y�ڤߴd�A�a�ѬT�����I\n" +
					"���H��w���A�嫬�b�g���C��ò�i��Ū�A�j�D���C��C");
			realContent = content.split("\n");
		}
		showBegin = 0;
		keepShowingDialog();
		setVisible(true);
	}
	*/
}
