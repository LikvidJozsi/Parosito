import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ALFrame extends JFrame{

	private static final long serialVersionUID = 6299166972146233430L;
	JTextField inputnametextfield, outputnametextfield,dayinterval;

	public ALFrame(){
		this.setLayout(new GridLayout(8,1));
		JLabel desc1= new JLabel("Adja meg a bemeneti excel fájl elérési útját:");
		this.add(desc1);
		inputnametextfield = new JTextField(40);
		this.add(inputnametextfield);
		JButton browse = new JButton("Tallózás");
		browse.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser browser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter(null,"xlsx","xls");
				browser.setFileFilter(filter);
				int approve = browser.showOpenDialog(ALFrame.this);
				if(approve == JFileChooser.APPROVE_OPTION){
					inputnametextfield.setText(browser.getSelectedFile().getAbsolutePath());
				}
			}
		});
		this.add(browse);
		JLabel desc2 = new JLabel("Adja meg a kimeneti file nevét");
		this.add(desc2);
		outputnametextfield = new JTextField(40);
		outputnametextfield.setText("parositatlan_tetelek.xlsx");
		this.add(outputnametextfield);
		JLabel desc3 = new JLabel("Adja meg a két párosított tétel közti maximális különbséget napban(üres mezőnél korlátlan):");
		this.add(desc3);
		dayinterval = new JTextField(5);
		this.add(dayinterval);
		JButton start = new JButton("start");
		start.addMouseListener(new MouseListener(){

			@Override
			public void mouseClicked(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				Parosito parosito = new Parosito(inputnametextfield.getText(),ALFrame.this);
				String dayIntervalText = dayinterval.getText().trim();
				
				parosito.parositas(outputnametextfield.getText(),dayIntervalText.equals("") ? 2000000 : Integer.parseInt(dayinterval.getText()));
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}
		
		});
		this.add(start);
		this.setSize(640, 480);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
