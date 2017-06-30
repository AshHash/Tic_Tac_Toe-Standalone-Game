import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TicTacToe implements ActionListener{

	private JFrame frame;
	private JPanel panelC, panelL;
	private JButton btn[];
	private String sym[];
	private JButton reset, swap;
	private JLabel ply1, ply2, ply1Symbol, ply2Symbol;
	private JLabel status;
	private Font font, fontBtn, fontEx;
	
	private int winner, full;
	private boolean won;
	
	private TicTacToe(){
		
		winner = full = 0;
		won = false;
		
		frame = new JFrame("Tic Tac Toe");
		panelC = new JPanel();
		panelL = new JPanel();
		
		font = new Font("Constantia",Font.PLAIN,100);
		fontBtn = new Font("Broadway",Font.PLAIN,30);
		fontEx = new Font("Elephant", Font.PLAIN, 30);
		
		reset = new JButton("<html><u>R</u>eset"); reset.setMnemonic(KeyEvent.VK_R);
		swap = new JButton("<html><u>S</u>wap");   swap.setMnemonic(KeyEvent.VK_S);
		ply1 = new JLabel("Player 1");			   //ply1.setForeground(Color.lightGray);
		ply2 = new JLabel("Player 2");			   //ply2.setForeground(Color.pink);
		ply1Symbol = new JLabel("X"); 			   ply1Symbol.setForeground(Color.darkGray);
		ply2Symbol = new JLabel("O");			   ply2Symbol.setForeground(Color.magenta);
		
		swap.addActionListener(this);
		reset.addActionListener(this);
		
		reset.setForeground(Color.blue);
		swap.setForeground(Color.red);
		
		swap.setBackground(Color.orange);;
		reset.setBackground(Color.orange);;
		
		swap.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		reset.setBorder(BorderFactory.createRaisedSoftBevelBorder());
		
		swap.setFont(fontBtn);
		reset.setFont(fontBtn);
		
		ply1.setFont(fontEx);
		ply2.setFont(fontEx);
		ply1Symbol.setFont(new Font("Bradley Hand ITC",Font.BOLD, 40));//.setFont(fontEx);
		ply2Symbol.setFont(new Font("Bradley Hand ITC",Font.BOLD, 40));
		
		status = new JLabel("Player 1 : Your turn");
		status.setForeground(Color.red);
		status.setBackground(Color.white);
		status.setFont(new Font("Goudy Stout", Font.PLAIN, 20));
		
		GridBagLayout gbag = new GridBagLayout();
		GridBagConstraints gbc = new GridBagConstraints(); 
		
		gbc.insets = new Insets(20,20,0,10);
		gbc.weighty=1;gbc.weightx=1;
		
		gbc.fill = GridBagConstraints.BOTH;
		
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(reset, gbc);
	
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(ply1, gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(ply1Symbol, gbc);
		
		gbc.gridwidth=GridBagConstraints.RELATIVE;
		gbag.setConstraints(ply2, gbc);
		gbc.gridwidth=GridBagConstraints.REMAINDER;
		gbag.setConstraints(ply2Symbol, gbc);
		
		gbag.setConstraints(swap, gbc);
		
		panelL.setLayout(gbag);
		panelL.add(reset);
		panelL.add(ply1);
		panelL.add(ply1Symbol);
		panelL.add(ply2);
		panelL.add(ply2Symbol);
		panelL.add(swap);
		
		panelC.setLayout(new GridLayout(3,3));
		
		btn = new JButton[9];
		sym = new String[9];
		for(int i=0; i<9; ++i){ 
			btn[i] = new JButton(""); 
			sym[i]="";
			btn[i].setFont(font);
			btn[i].addActionListener(this);
			btn[i].setActionCommand(""+i);
			btn[i].setBackground(Color.white);
			panelC.add(btn[i]);
		}
		
		frame.add(panelC);
		frame.add(panelL,BorderLayout.EAST);
		frame.add(status,BorderLayout.SOUTH);
		frame.setVisible(true);
		frame.setResizable(false);
		frame.setLocation(269,66);
		frame.setSize(779,569);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void actionPerformed(ActionEvent ae){
		JButton bt = (JButton) ae.getSource();
		if(bt == reset){
			winner=0;
			full=0;
			won=false;
			for(int i=0; i<9; ++i){ 
				sym[i]="";
				btn[i].setText("");
				btn[i].setEnabled(true);
				btn[i].setBackground(Color.white);
			}
		}
		else if(bt == swap){
			String temp = ply1Symbol.getText();
			ply1Symbol.setText(ply2Symbol.getText());
			ply2Symbol.setText(temp);
			reset.doClick();
		}
		else{ //on center panel btn click
			if(bt.isEnabled()){
				if(won){
					int ans = JOptionPane.showConfirmDialog(panelC,"Reset ?","Reset",0);
					if(ans == JOptionPane.YES_OPTION) reset.doClick();
					return;
				}
				full++; 
				String s ;
				String ply = status.getText();
				if(ply.contains("Player 1")){
					s = ply1Symbol.getText();
					bt.setBackground(Color.lightGray);
					bt.setText(s);
					status.setText("Player 2 : Your turn");
				}
				else{
					s = ply2Symbol.getText();
					bt.setBackground(Color.pink);
					bt.setText(s);
					status.setText("Player 1 : Your turn");
				}
				bt.setEnabled(false);
				sym[Integer.parseInt(bt.getActionCommand())]=s;
			}
			String msg="";
			result();
			switch(winner){
				case 1 : msg="Player 1 wins!"; break;
				case 2 : msg="Player 2 wins!"; break;
			}
			
			if(full==9){ //not used else-if --> because what if the game has a winner at the last step
				if(msg.equals(""))msg="Draw !!!";
				int ans = JOptionPane.showConfirmDialog(panelC,msg+" \nNew Game ?","Reset",0);
				if(ans == JOptionPane.YES_OPTION) reset.doClick();
			}
			//first written before checking full, but changed since giving to JOpDlgs two times for reason stated above
			else //now after change using else-if
			if(winner!=0){ JOptionPane.showMessageDialog(panelC, msg,"Winner",1); 	 won=true; }
		}
	}
	
	private void result(){
		String py1s = ply1Symbol.getText();
		for(int i=1; i<5 ; ++i){
			int k, max, it;
			switch(i){
				default: 
				case 1 : k=0; max=9; it=3; break; 
				case 3 : k=0; max=3; it=1; break;
				case 2 : k=2; max=3; it=1; break;
				case 4 : k=0; max=1; it=1; break;
			}
			for(; k<max; k+=it){
				if(!sym[k].equals(""))
				if(sym[k].equals(sym[k+i]) && sym[k+i].equals(sym[k+2*i])){
					if(sym[k].equals(py1s)) winner=1;
					else winner=2;
					return;
				}
			}
		}
	}
	
	public static void main(String args[]){
		new TicTacToe();
	}
	
}
	/*
	private void result(){
		String py1s = ply1Symbol.getText();
		int i = 0; //horizontal check
		for(int j=0; j<9; j+=3){
			if(!sym[j].equals(""))
			if(sym[i++].equals(sym[i]) && sym[i++].equals(sym[i++])){
				if(sym[j].equals(py1s)) winner=1;
				else winner=2;
				return;
			}
		}
		
		i=0; //vertical
		for(int j=0; j<3; j++, i++){
		if(!sym[j].equals(""))
			if(sym[i].equals(sym[i+3]) && sym[i+3].equals(sym[i+6])){
				if(sym[j].equals(py1s)) winner=1;
				else winner=2;
				return;
			}
		}
		
		i=0; //diagonal
		if(!sym[i].equals(""))
		if(sym[i].equals(sym[i+4]) && sym[i+4].equals(sym[i+8])){
			if(sym[i].equals(py1s)) winner=1;
			else winner=2;
			return;
		}
		
		i=2; //diagonal
		if(!sym[i].equals(""))
		if(sym[i].equals(sym[i+2]) && sym[i+2].equals(sym[i+4])){
			if(sym[i].equals(py1s)) winner=1;
			else winner=2;
			return;
		}
	}
	*/