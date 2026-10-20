import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import java.awt.event.*;


public class Login
{

JFrame f;
JPanel mainPanel;
JLabel imgLabel;
JLabel l1,l2,l3;
JPanel loginPanel;
JLabel a1,a2,a3;
JTextField tf1;
JPasswordField pf1;
JCheckBox cb1;
JButton loginButton;
JPanel footerPanel;
JLabel b1,b2,b3;
JLabel plateLabel;
JLabel bowlLabel;
JLabel photoLabel;
JLabel photoLabel2;

Connection con;


DashBoard db;
void createLogin( )
{

 db=new DashBoard();


createFrame();
createComponent();
setBounds();
addCompoenet();
ReadUserNamePassword();

mainPanel.setLayout(null);
loginPanel.setLayout(null);
footerPanel.setLayout(null);

addListener();




setFont();

setColors();




 //db.createDashBoard(f,mainPanel,footerPanel);

}


void createFrame()
{

f=new JFrame("Shri Radhe Ji Misthan - Management System");
f.setLayout(null);
f.setExtendedState(JFrame.MAXIMIZED_BOTH);


f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

f.setVisible(true);


}


void createComponent()
{

mainPanel=new JPanel();
//ImageIcon imgIcon1= resizeImage(getClass().getResource("imgs/logo.png"),300,200);
ImageIcon imgIcon1 = new ImageIcon(  "C:\\Users\\user\\Desktop\\SSMS\\imgs\\login_logo2.gif");
imgLabel=new JLabel(imgIcon1);
l1=new JLabel("Shri Radhe ji Misthan");
l2=new JLabel("---Sweet Shop Management System---");
l3=new JLabel("premium Quality | Pure Taste | Radhe Radhe");
loginPanel=new JPanel();
a1=new JLabel("Login To Your Account");
a2=new JLabel("Username:");
a3=new JLabel("password:");
cb1=new JCheckBox("Remember me:");

tf1=new JTextField();
pf1=new JPasswordField();
loginButton=new JButton("LOGIN");
footerPanel=new JPanel();
b1=new JLabel("Premium Quality | Pure Taste");
b2=new JLabel("*|| Radhe Radhe ||*");
b3=new JLabel("version:1.0.0");
bowlLabel=new JLabel(resizeImage(getClass().getResource("imgs/bowl.png"),350,350));
plateLabel=new JLabel(resizeImage(getClass().getResource("imgs/plate.png"),350,350));
photoLabel=new JLabel(resizeImage(getClass().getResource("imgs/shridha.png"),250,200));
photoLabel2=new JLabel(resizeImage(getClass().getResource("imgs/kiyansh.png"),210,180));


}


void addCompoenet()
{

f.add(mainPanel);
mainPanel.add(imgLabel);
mainPanel.add(l1);
mainPanel.add(l2);
mainPanel.add(l3);
mainPanel.add(loginPanel);
loginPanel.add(a1);
loginPanel.add(a2);
loginPanel.add(a3);
loginPanel.add(tf1);
loginPanel.add(pf1);
loginPanel.add(cb1);
loginPanel.add(loginButton);
f.add(footerPanel);
footerPanel.add(b1);
footerPanel.add(b2);
footerPanel.add(b3);
mainPanel.add(plateLabel);
mainPanel.add(bowlLabel);
mainPanel.add(photoLabel);
mainPanel.add(photoLabel2);




}


void setBounds()
{
mainPanel.setBounds(0,0,f.getWidth(),f.getHeight());
imgLabel.setBounds(820,0,300,200);
l1.setBounds(620,100,800,300);
l2.setBounds(720,165,800,300);
l3.setBounds(770,225,800,300);
loginPanel.setBounds(670,450,630,500);
a1.setBounds(210,20,400,40);
a2.setBounds(50,110,140,30);
tf1.setBounds(180,110,400,40);
a3.setBounds(50,180,140,30);
pf1.setBounds(180,180,400,40);
cb1.setBounds(50,240,150,30);
loginButton.setBounds(130,330,400,40);
footerPanel.setBounds(0,955,mainPanel.getWidth(),55);
b1.setBounds(10,10,300,30);
b2.setBounds(900,10,300,30);
b3.setBounds(1800,10,300,30);
bowlLabel.setBounds(1500,600,350,350);
plateLabel.setBounds(100,600,350,350);
photoLabel.setBounds(1700,20,250,200);
photoLabel2.setBounds(1,5,250,200);



}










void setColors()
{

mainPanel.setBackground(new Color(254, 248, 239));
l1.setForeground(new Color(91, 55, 28));
l3.setForeground(new Color(91, 55, 28));
loginPanel.setBackground(new Color(254, 248, 239));
footerPanel.setBackground(new Color(105, 12, 30));
b1.setForeground(Color.WHITE);
b2.setForeground(Color.WHITE);
b3.setForeground(Color.WHITE);
cb1.setBackground(Color.WHITE);
loginButton.setBackground(new Color(105, 12, 30));
loginButton.setForeground(Color.WHITE);



}

void setFont()
{

l1.setFont(new Font("ARIAL",Font.BOLD,70));
l2.setFont(new Font("ARIAL",Font.BOLD,30));
l3.setFont(new Font("ARIAL",Font.PLAIN,23));
a1.setFont(new Font("ARIAL",Font.BOLD,23));
a2.setFont(new Font("ARIAL",Font.BOLD,20));
a3.setFont(new Font("ARIAL",Font.BOLD,20));
loginButton.setFont(new Font("ARIAL",Font.BOLD,18));
tf1.setFont(new Font("ARIAL",Font.PLAIN,20));
pf1.setFont(new Font("ARIAL",Font.PLAIN,20));
b1.setFont(new Font("ARIAL",Font.BOLD,15));
b2.setFont(new Font("ARIAL",Font.BOLD,15));
b3.setFont(new Font("ARIAL",Font.BOLD,15));
cb1.setFont(new Font("ARIAL",Font.BOLD,15));





}

ImageIcon resizeImage(URL url,int width,int height)
{

ImageIcon imgIcon=new ImageIcon(url);
Image img=imgIcon.getImage();
Image resizeIcon =img.getScaledInstance(width,height,Image.SCALE_SMOOTH);
return new ImageIcon(resizeIcon);
 
}

int count=0;

void addListener()
{






tf1.setText("Enter Username");
tf1.setForeground(Color.GRAY);

tf1.addFocusListener(new FocusAdapter()
{
    @Override
    public void focusGained(FocusEvent e)
    {
        if (tf1.getText().equals("Enter Username"))
        {
            tf1.setText("");
            tf1.setForeground(Color.BLACK);
        }
    }

    @Override
    public void focusLost(FocusEvent e)
    {
        if (tf1.getText().trim().isEmpty())
        {
            tf1.setText("Enter Username");
            tf1.setForeground(Color.GRAY);
        }
    }
});


pf1.setEchoChar((char) 0);
pf1.setText("Enter Password");
pf1.setForeground(Color.GRAY);

pf1.addFocusListener(new FocusAdapter()
{
    public void focusGained(FocusEvent e)
    {
        if (pf1.getText().equals("Enter Password"))
        {
            pf1.setText("");
            pf1.setEchoChar('*');
            pf1.setForeground(Color.BLACK);
        }
    }

    public void focusLost(FocusEvent e)
    {
        if (pf1.getPassword().length == 0)
        {
            pf1.setEchoChar((char) 0);
            pf1.setText("Enter Password");
            pf1.setForeground(Color.GRAY);
        }
    }
});

pf1.addActionListener(e->
{


chackingPassword();



});



loginButton.addActionListener(e ->{


chackingPassword();





});






}



String userName;
String password;

void ReadUserNamePassword()
{


try
{


con = DriverManager.getConnection(
        "jdbc:mysql://localhost:3306/ssms",
        "root",
        "password"
    );


  System.out.println("Database Connected!");





PreparedStatement ps=con.prepareStatement("SELECT * FROM LoginUserNamePassword");
ResultSet rs=ps.executeQuery();

while(rs.next())
{



userName=rs.getString(1);
password=rs.getString(2);







}



  System.out.println(userName);
  System.out.println(password);


ps.close();
rs.close();


}



catch(Exception ex)
{




JOptionPane.showMessageDialog(null,ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);




}







}






void chackingPassword()
{


String userName=tf1.getText();
 char ch[]=pf1.getPassword();
String password=String.valueOf(ch);



if(tf1.getText().trim().isEmpty()
        || tf1.getText().equals("Enter Username")
        || password.trim().isEmpty()
        || password.equals("Enter Password"))
{
    JOptionPane.showMessageDialog(null,
            "Please enter username and password:");
    return;
}


if(userName.equals(this.userName)&& password.equals(this.password))
{



  db.createDashBoard(f,mainPanel,footerPanel);
JOptionPane.showMessageDialog(null,"Login Successfully:");



}


else
{

count++;

JOptionPane.showMessageDialog(null,"Attempt"+count+"  Invalid username or password: ");
tf1.setText("");
pf1.setText("");

if(count==3)
{

JOptionPane.showMessageDialog(null,"Maximum try you are logout");
System.exit(0);

}


}






}




}