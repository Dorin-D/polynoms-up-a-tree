import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import javax.swing.text.html.HTML;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

//import java.util.ArrayList;
//import java.util.Map;
//import java.util.TreeMap;


class Main  {

	
	public static void main(String args[]){
	
		JFrame frame;
		JPanel panel;
		JTextArea inputRoot;
		JButton add,getPolynom,sum, multiply,diff,divideByNumber,generatePolynom;
	
			
			ArrayList<Polynom> polynomList = new ArrayList<Polynom>();
			JLabel label1 = new JLabel("Introduce the polynom to the main list:");
			
			frame = new JFrame();
			frame.setTitle("Polynom tool");
			
			frame.setSize(1600,800);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			panel = new JPanel();
			panel.add(label1);
			inputRoot = new JTextArea(9,140);
			inputRoot.setFont(new Font("Serif", Font.ITALIC, 16));
			inputRoot.setLineWrap(true);
			inputRoot.setWrapStyleWord(true);
			add = new JButton("add");
			getPolynom = new JButton("Get Polynom");
			sum = new JButton("Sum");
			multiply = new JButton("Multiply");
			diff = new JButton("Diff");
			divideByNumber = new JButton("DivideByNumber");
			generatePolynom = new JButton("GeneratePolynom");
			
			
			panel.add(inputRoot);
			panel.add(add);
			panel.add(getPolynom);
			panel.add(sum);
			panel.add(multiply);
			panel.add(diff);
			panel.add(divideByNumber);
			panel.add(generatePolynom);
			
			
			frame.add(panel);
			frame.setVisible(true);
			add.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					String input = inputRoot.getText();
					try {
						polynomList.add(new Polynom(input));
					} catch (BadParsingException | UnstableStateException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					inputRoot.setText("");
					System.out.println("Polynom added!");
				}
			});
			getPolynom.addActionListener( new ActionListener()
			{
			 public void actionPerformed(ActionEvent e){
				 JFrame frame2;
				 
				 JPanel p = new JPanel();
				  frame2 = new JFrame();
				  String lista[]=new String[2000];
				  for (int i=0;i<polynomList.size();i++)
				  {
					  lista[i]=polynomList.get(i).toString();
				  }
				  JList b=new JList(lista);
				 // int it=b.locationToIndex(mouseEvent.getPoint());
				  p.setBackground(Color.blue);
				  b.addMouseListener(new MouseAdapter() {
					  public void mousePressed(MouseEvent e)
					  {
						  if ( SwingUtilities.isRightMouseButton(e) )
					        {
							  
							  	
					            JList list = (JList)e.getSource();
					            list.setBackground(Color.GREEN);
					            int row = list.locationToIndex(e.getPoint());	
					            if (row<polynomList.size()) {
					            	String newText="";
					            	int i=1;
					            	boolean ok=false;
					            	if (Operations.isLetter(lista[row].charAt(0)))
				            		{
				            			newText += "<html><font color=#ff0000>"+lista[row].charAt(0)+"</font>";
				         		
				            		}
					            	else
					            	{
					            		newText += "<html><font color=#0000ff>"+lista[row].charAt(0)+"</font>";
					            	}
					            	for (;i<lista[row].length();i++)
					            	{
					            		if (Operations.isLetter(lista[row].charAt(i)))
					            		{
					            			newText += "<html><font color=#ff0000>"+lista[row].charAt(i)+"</font>";
					            			ok=false;
					            		
					            		}
					            		else
					            		if (lista[row].charAt(i)=='+' || lista[row].charAt(i)=='-' || lista[row].charAt(i)=='^' || lista[row].charAt(i)=='*' || lista[row].charAt(i)=='.')
					            		{
					            			newText+=lista[row].charAt(i);
					            			ok=false;
					            		}
					            		else
					            			if (lista[row].charAt(i-1)=='^' || ok==true)
					            		{
					            				newText+="<html><font color=#40ff00>"+lista[row].charAt(i)+"</font>";
					            				ok=true;
					            		}
					            			else
					            			if (ok==false)
					            			{
					            				newText+="<html><font color=#0000ff>"+lista[row].charAt(i)+"</font>";
					            			}
					            	
					            	}
					            	
					            	JOptionPane.showMessageDialog(frame, newText);
					            	System.out.println(polynomList.get(row));
					            	
					            	   
					            }
					        }
					  }
				  });
				  
				  p.add(b);
				  frame2.add(p);
				  frame2.setSize(1600,800);
				  frame2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
				  frame2.setVisible(true);
				
				 
			 }
			});
			sum.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					JFrame frame3=new JFrame();
					frame3.setLayout(new FlowLayout());
					JButton addbutton = new JButton("ADD");
					
					String lista[]=new String[2000];
					  for (int i=0;i<polynomList.size();i++)
					  {
						  lista[i]=polynomList.get(i).toString();
					  }  
					JList leftList = new JList(lista);
					JList rightList = new JList();
					leftList.setVisibleRowCount(5);
			        leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					frame3.add(new JScrollPane(leftList));
					addbutton.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent event) {
			                //make sure you preserve the previously selected list items
			                int size = rightList.getModel().getSize();
			                Set objects = new LinkedHashSet();
			                for (int i = 0; i < size; i++) {
			                    objects.add(rightList.getModel().getElementAt(i));
			                }
			                objects.addAll(Arrays.asList(leftList.getSelectedValues()));

			                rightList.setListData(objects.toArray());
			            }
			        });
					JButton removeBttn= new JButton("Reset ALL");
					removeBttn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event)
						{
							DefaultListModel listModel = new DefaultListModel();
							rightList.setModel(listModel);
						}
					});
					frame3.add(addbutton);
					frame3.add(removeBttn);
					  rightList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					 rightList.setVisibleRowCount(5);
				        rightList.setFixedCellWidth(55);
				        rightList.setFixedCellHeight(20);
				       frame3.add(new JScrollPane(rightList));
				       JButton suma= new JButton("SUM");
				       suma.addActionListener(new ActionListener() {
				    	   public void actionPerformed(ActionEvent event)
				    	   {
				    		   int size = rightList.getModel().getSize();
				    		   if (size == 2)
				    		   {
				    			   try {
									Polynom poly1 = new Polynom(rightList.getModel().getElementAt(0).toString());
									Polynom poly2 = new Polynom(rightList.getModel().getElementAt(1).toString());
									String text =Operations.sum(poly1,poly2).toString();
									String newText="";
					               	int i=1;
					            	boolean ok=false;
					            	if (Operations.isLetter(text.charAt(0)))
				            		{
				            			newText += "<html><font color=#ff0000>"+text.charAt(0)+"</font>";
				         		
				            		}
					            	else
					            	{
					            		newText += "<html><font color=#0000ff>"+text.charAt(0)+"</font>";
					            	}
					            	for (;i<text.length();i++)
					            	{
					            		if (Operations.isLetter(text.charAt(i)))
					            		{
					            			newText += "<html><font color=#ff0000>"+text.charAt(i)+"</font>";
					            			ok=false;
					            		
					            		}
					            		else
					            		if (text.charAt(i)=='+' || text.charAt(i)=='-' || text.charAt(i)=='^' || text.charAt(i)=='*' || text.charAt(i)=='.')
					            		{
					            			newText+=text.charAt(i);
					            			ok=false;
					            		}
					            		else
					            			if (text.charAt(i-1)=='^' || ok==true)
					            		{
					            				newText+="<html><font color=#40ff00>"+text.charAt(i)+"</font>";
					            				ok=true;
					            		}
					            			else
					            			if (ok==false)
					            			{
					            				newText+="<html><font color=#0000ff>"+text.charAt(i)+"</font>";
					            			}
					            	
					            	}
					            	
					            	JOptionPane.showMessageDialog(frame, newText);
					            	System.out.println(text);
								} catch (BadParsingException | UnstableStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		   }
				    	   }
				       });
				       
				       frame3.add(suma);
				       frame3.setSize(1600,800);
						  frame3.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						  frame3.setVisible(true);
				       
					
				}
			});
			multiply.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e)
				{
					JFrame frame4=new JFrame();
					frame4.setLayout(new FlowLayout());
					JButton addbutton = new JButton("ADD");
					String lista[]=new String[2000];
					  for (int i=0;i<polynomList.size();i++)
					  {
						  lista[i]=polynomList.get(i).toString();
					  }  
					JList leftList = new JList(lista);
					JList rightList = new JList();
					leftList.setVisibleRowCount(5);
			        leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					frame4.add(new JScrollPane(leftList));
					addbutton.addActionListener(new ActionListener() {
			            public void actionPerformed(ActionEvent event) {
			                //make sure you preserve the previously selected list items
			                int size = rightList.getModel().getSize();
			                Set objects = new LinkedHashSet();
			                for (int i = 0; i < size; i++) {
			                    objects.add(rightList.getModel().getElementAt(i));
			                }
			                objects.addAll(Arrays.asList(leftList.getSelectedValues()));

			                rightList.setListData(objects.toArray());
			            }
			        });
					rightList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
					JButton removeBttn= new JButton("Reset ALL");
					removeBttn.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent event)
						{
							DefaultListModel listModel = new DefaultListModel();
							rightList.setModel(listModel);
						}
					});
				
					frame4.add(addbutton);
						frame4.add(removeBttn);
					 rightList.setVisibleRowCount(5);
				        rightList.setFixedCellWidth(55);
				        rightList.setFixedCellHeight(20);
				       frame4.add(new JScrollPane(rightList));
				       JButton multiply= new JButton("MULTIPLY");
				       
				       multiply.addActionListener(new ActionListener() {
				    	   public void actionPerformed(ActionEvent event)
				    	   {
				    		   int size = rightList.getModel().getSize();
				    		   if (size == 2)
				    		   {
				    			   try {
									Polynom poly1 = new Polynom(rightList.getModel().getElementAt(0).toString());
									Polynom poly2 = new Polynom(rightList.getModel().getElementAt(1).toString());
									String text =Operations.multiply(poly1,poly2).toString();
									String newText="";
					               	int i=1;
					            	boolean ok=false;
					            	if (Operations.isLetter(text.charAt(0)))
				            		{
				            			newText += "<html><font color=#ff0000>"+text.charAt(0)+"</font>";
				         		
				            		}
					            	else
					            	{
					            		newText += "<html><font color=#0000ff>"+text.charAt(0)+"</font>";
					            	}
					            	for (;i<text.length();i++)
					            	{
					            		if (Operations.isLetter(text.charAt(i)))
					            		{
					            			newText += "<html><font color=#ff0000>"+text.charAt(i)+"</font>";
					            			ok=false;
					            		
					            		}
					            		else
					            		if (text.charAt(i)=='+' || text.charAt(i)=='-' || text.charAt(i)=='^' || text.charAt(i)=='*' || text.charAt(i)=='.')
					            		{
					            			newText+=text.charAt(i);
					            			ok=false;
					            		}
					            		else
					            			if (text.charAt(i-1)=='^' || ok==true)
					            		{
					            				newText+="<html><font color=#40ff00>"+text.charAt(i)+"</font>";
					            				ok=true;
					            		}
					            			else
					            			if (ok==false)
					            			{
					            				newText+="<html><font color=#0000ff>"+text.charAt(i)+"</font>";
					            			}
					            	
					            	}
					            	
					            	JOptionPane.showMessageDialog(frame, newText);
					            	System.out.println(text);
								} catch (BadParsingException | UnstableStateException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
				    		   }
				    	   }
				       });
				     
				       frame4.add(multiply);
				       frame4.setSize(1600,800);
					   frame4.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
					   frame4.setVisible(true);
				       
					
				}
			});
			  generatePolynom.addActionListener(new ActionListener() {
					 public void actionPerformed(ActionEvent event)
			    	   {
						 JFrame frame5 = new JFrame();
						 JPanel p = new JPanel();
						 JLabel label2= new JLabel("Enter the polynom");
						 JTextArea inputPolynom = new JTextArea(9,140);
						 inputPolynom.setFont(new Font("Serif", Font.ITALIC, 16));
						 JTextField inputPower = new JTextField(30);
				
						 JButton generate = new JButton("Generate");
						 
						 p.add(label2);
						 p.add(inputRoot);
						 p.add(inputPower);
						 p.add(generate);
						 
						 generate.addActionListener(new ActionListener() {
					    	   public void actionPerformed(ActionEvent event)
					    	   {
					    		   String text = inputPower.getText();
					    		   int power = Integer.parseInt(text);
					    		   String root = inputRoot.getText();
					    		   
					    		   try {
					    		   Polynom r = new Polynom(root);
					    	
					    			String text1 =Operations.generatePolynom(r,power).toString();
									String newText="";
					               	int i=1;
					            	boolean ok=false;
					            	if (Operations.isLetter(text1.charAt(0)))
				            		{
				            			newText += "<html><font color=#ff0000>"+text1.charAt(0)+"</font>";
				         		
				            		}
					            	else
					            	{
					            		newText += "<html><font color=#0000ff>"+text1.charAt(0)+"</font>";
					            	}
					            	for (;i<text1.length();i++)
					            	{
					            		if (Operations.isLetter(text1.charAt(i)))
					            		{
					            			newText += "<html><font color=#ff0000>"+text1.charAt(i)+"</font>";
					            			ok=false;
					            		
					            		}
					            		else
					            		if (text1.charAt(i)=='+' || text1.charAt(i)=='-' || text1.charAt(i)=='^' || text1.charAt(i)=='*' || text1.charAt(i)=='.')
					            		{
					            			newText+=text1.charAt(i);
					            			ok=false;
					            		}
					            		else
					            			if (text1.charAt(i-1)=='^' || ok==true)
					            		{
					            				newText+="<html><font color=#40ff00>"+text1.charAt(i)+"</font>";
					            				ok=true;
					            		}
					            			else
					            			if (ok==false)
					            			{
					            				newText+="<html><font color=#0000ff>"+text1.charAt(i)+"</font>";
					            			}
					            	
					            	}
					            	inputPolynom.setText("");
					            	JOptionPane.showMessageDialog(frame, newText);
					            	System.out.println(text1);
					    		   } catch (BadParsingException | UnstableStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					    	   }  
					       });
						 
						 frame5.add(p);
						 frame5.setSize(1600,800);
						 frame5.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						 frame5.setVisible(true);
			    	   }
				});
		       diff.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e)
					{
						JFrame frame6=new JFrame();
						frame6.setLayout(new FlowLayout());
						JButton addbutton = new JButton("ADD");
						String lista[]=new String[2000];
						  for (int i=0;i<polynomList.size();i++)
						  {
							  lista[i]=polynomList.get(i).toString();
						  }  
						JList leftList = new JList(lista);
						JList rightList = new JList();
						leftList.setVisibleRowCount(5);
				        leftList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
	
						addbutton.addActionListener(new ActionListener() {
				            public void actionPerformed(ActionEvent event) {
				                //make sure you preserve the previously selected list items
				                int size = rightList.getModel().getSize();
				                Set objects = new LinkedHashSet();
				                for (int i = 0; i < size; i++) {
				                    objects.add(rightList.getModel().getElementAt(i));
				                }
				                objects.addAll(Arrays.asList(leftList.getSelectedValues()));

				                rightList.setListData(objects.toArray());
				            }
				        });
						
						frame6.add(new JScrollPane(leftList));
						frame6.add(addbutton);
						JButton removeBttn= new JButton("Reset ALL");
						removeBttn.addActionListener(new ActionListener() {
							public void actionPerformed(ActionEvent event)
							{
								DefaultListModel listModel = new DefaultListModel();
								rightList.setModel(listModel);
							}
						});
						frame6.add(removeBttn);
						 rightList.setVisibleRowCount(5);
					        rightList.setFixedCellWidth(55);
					        rightList.setFixedCellHeight(20);
					       frame6.add(new JScrollPane(rightList));
					       JButton diff= new JButton("DIFF");
					       
					       diff.addActionListener(new ActionListener() {
					    	   public void actionPerformed(ActionEvent event)
					    	   {
					    		   int size = rightList.getModel().getSize();
					    		   if (size == 2)
					    		   {
					    			   try {
										Polynom poly1 = new Polynom(rightList.getModel().getElementAt(0).toString());
										Polynom poly2 = new Polynom(rightList.getModel().getElementAt(1).toString());
										String text1 = Operations.diff(poly1,poly2).toString();
										String newText="";
						               	int i=1;
						            	boolean ok=false;
						            	if (Operations.isLetter(text1.charAt(0)))
					            		{
					            			newText += "<html><font color=#ff0000>"+text1.charAt(0)+"</font>";
					         		
					            		}
						            	else
						            	{
						            		newText += "<html><font color=#0000ff>"+text1.charAt(0)+"</font>";
						            	}
						            	for (;i<text1.length();i++)
						            	{
						            		if (Operations.isLetter(text1.charAt(i)))
						            		{
						            			newText += "<html><font color=#ff0000>"+text1.charAt(i)+"</font>";
						            			ok=false;
						            		
						            		}
						            		else
						            		if (text1.charAt(i)=='+' || text1.charAt(i)=='-' || text1.charAt(i)=='^' || text1.charAt(i)=='*' || text1.charAt(i)=='.')
						            		{
						            			newText+=text1.charAt(i);
						            			ok=false;
						            		}
						            		else
						            			if (text1.charAt(i-1)=='^' || ok==true)
						            		{
						            				newText+="<html><font color=#40ff00>"+text1.charAt(i)+"</font>";
						            				ok=true;
						            		}
						            			else
						            			if (ok==false)
						            			{
						            				newText+="<html><font color=#0000ff>"+text1.charAt(i)+"</font>";
						            			}
						            	
						            	}
						            	
						            	JOptionPane.showMessageDialog(frame, newText);
						            	System.out.println(text1);
									} catch (BadParsingException | UnstableStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					    		   }
					    	   }
					       });
					       frame6.add(diff);
					       frame6.setSize(1600,800);
						   frame6.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						   frame6.setVisible(true);
					       
						
					}
				});
		       divideByNumber.addActionListener(new ActionListener() {
					 public void actionPerformed(ActionEvent event)
			    	   {
						 JFrame frame7 = new JFrame();
						 JPanel p = new JPanel();
						 JLabel label3=new JLabel("Enter the polynom");
						 JTextArea inputPolynom = new JTextArea(9,140);
						 inputPolynom.setFont(new Font("Serif", Font.ITALIC, 16));
						 JTextField inputNumber = new JTextField(30);
						 JLabel label = new JLabel("Enter the number:");
						 JButton divide = new JButton("Divide");
						 p.add(label3);
						 p.add(inputPolynom);
						 p.add(label);
						 p.add(inputNumber);
						 p.add(divide);
						
						 
						 divide.addActionListener(new ActionListener() {
					    	   public void actionPerformed(ActionEvent event)
					    	   {
					    		   String text = inputNumber.getText();
					    		   double d = Double.parseDouble(text);
					    		   String polynom = inputPolynom.getText();
					    		   
					    		   try {
					    		   Polynom poly = new Polynom(polynom);
					    			String text1 = Operations.divideByNumber(poly,d).toString();
									String newText="";
					               	int i=1;
					            	boolean ok=false;
					            	if (Operations.isLetter(text1.charAt(0)))
				            		{
				            			newText += "<html><font color=#ff0000>"+text1.charAt(0)+"</font>";
				         		
				            		}
					            	else
					            	{
					            		newText += "<html><font color=#0000ff>"+text1.charAt(0)+"</font>";
					            	}
					            	for (;i<text1.length();i++)
					            	{
					            		if (Operations.isLetter(text1.charAt(i)))
					            		{
					            			newText += "<html><font color=#ff0000>"+text1.charAt(i)+"</font>";
					            			ok=false;
					            		
					            		}
					            		else
					            		if (text1.charAt(i)=='+' || text1.charAt(i)=='-' || text1.charAt(i)=='^' || text1.charAt(i)=='*' || text1.charAt(i)=='.')
					            		{
					            			newText+=text1.charAt(i);
					            			ok=false;
					            		}
					            		else
					            			if (text1.charAt(i-1)=='^' || ok==true)
					            		{
					            				newText+="<html><font color=#40ff00>"+text1.charAt(i)+"</font>";
					            				ok=true;
					            		}
					            			else
					            			if (ok==false)
					            			{
					            				newText+="<html><font color=#0000ff>"+text1.charAt(i)+"</font>";
					            			}
					            	
					            	}
					            	
					            	JOptionPane.showMessageDialog(frame, newText);
					            	System.out.println(text1);
					            	inputPolynom.setText("");
					    		   } catch (BadParsingException | UnstableStateException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
					    	   }  
					       });
						 
						 frame7.add(p);
						 frame7.setSize(1600,800);
						 frame7.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						 frame7.setVisible(true);
			    	   }
				});
				
				
				
				
				
				
				
							
		
//				TreeMap<String, Integer> test = new TreeMap<String, Integer>();
//				test.put("x", 5);
//				test.put("a", 2);
//				TreeMap<Map.Entry<String, Integer>, Double> test2 = new TreeMap<Map.Entry<String, Integer>, Double>();
//				test2.put(null, 5.0);
//				test2.put(test.firstEntry(), 2.0);
//				System.out.println(test);
//				System.out.println(test2);
//				if(test.get("x") == null || test.get("a") == null){
//					System.out.println("null detected");
//				}
//				else if(test.get("x") < test.get("a")){
//					System.out.println("test");
//				}
		
	
//old testing		
//		int pos = 100;
//		if((pos = "+-".indexOf("*"))!= -1){
//			System.out.println(pos);
//		} else System.out.println("bugged pos " + pos);
//		int myInt = 4;
//		if(Arrays.asList(1, 2, 3).contains(myInt)){
//			System.out.println("If Big true");
//		}
//		else {
//			System.out.println("Else Small false");
//		}
//		States myState = States.COEFFICIENT;
//		if((pos = "0123456789".indexOf("9")) != -1 && myState == States.START){
//			System.out.println(pos);
//			System.out.println(States.COEFFICIENT);
//		}
//		double currentCoefficient = 50;
//		int posi = 2;
//		int decimalTenMultiplier = 100;
//		currentCoefficient = currentCoefficient + posi/(double)decimalTenMultiplier;
//		System.out.println(currentCoefficient);
//		ArrayList<String> varList = new ArrayList<String>();
//		varList.add("E");
//		varList.add("B");
//		String doIGetContained = "E";
//		if(varList.contains(doIGetContained)){
//			System.out.println("YES!");
//		}
	}
}
