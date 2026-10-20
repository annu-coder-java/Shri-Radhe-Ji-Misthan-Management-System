import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.sql.*;
import java.io.File;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class DashBoard{

//		javac -cp ".;C:\jdbc\mysql-connector-j-9.7.0.jar;C:\jdbc\jcalendar-1.4.jar;lib\jfreechart-1.5.6.jar" *.java

//		java -cp ".;C:\jdbc\mysql-connector-j-9.7.0.jar;C:\jdbc\jcalendar-1.4.jar;lib\jfreechart-1.5.6.jar" SSMS





Sales sales=new Sales();

Orders orders=new Orders();
Customers customer=new Customers();
Reports reports = new Reports();
boolean dashboardCreated = false;


AddItem ai;
Stock s;

JPanel miniHeaderPanel2;
JPanel productStockListPanel;
JPanel ordersPanel;



   public DashBoard()
    {
       
        ai = new AddItem();
        s = new Stock();
    }



JFrame f;
JPanel mainPanel;
JPanel footerPanel;


JPanel dashBoardSideBarPanel;
JLabel logoLabel;
JLabel dL1,dL2,dL3;
JPanel dashBoardHeaderPanel;
JLabel photoLabel;
JLabel photoLabel2;

JLabel dateLabelIcon,clockLabelIcon,adminLabelIcon;
JLabel dateLabel;
JLabel timeLabel;
JLabel userLabel;

JDBC jd=new JDBC();

JButton buttons[]=new JButton[9];
JPanel mssgBoxPanel;
JLabel mssg1,mssg2,mssg3,mssg4;
JPanel miniHeaderPanel;
JLabel miniHeaderL1;
JLabel miniHeaderL2;
JLabel miniHeaderLogo;

JPanel cardsPanel;
JPanel panels[]=new JPanel[jd.countRow()];
JScrollPane sp;

int data=jd.countRow();
JLabel sweetsLabel[]=new JLabel[jd.countRow()];


AddItem addItemObj;

JPanel salesMainPanel;
JPanel orderMainPanel;
JPanel addItemPanel;
JPanel customerPanel;
JPanel reportPanel;


public void createDashBoard(JFrame f,JPanel mainPanel,JPanel footerPanel)
{
    this.f = f;
    this.mainPanel = mainPanel;
    this.footerPanel = footerPanel;



    if(dashboardCreated)
  
    {




      if(salesMainPanel != null)
    {
        salesMainPanel.setVisible(false);
    }


 
        showDashboard();
        refreshData();
        return;
    }

    dashboardCreated = true;

    con = jd.con;

    contentVisibilty();
    createComponent();

    dashBoardSideBarPanel.setLayout(null);
    dashBoardHeaderPanel.setLayout(null);
    mssgBoxPanel.setLayout(null);
    miniHeaderPanel.setLayout(null);
    cardsPanel.setLayout(null);

    setBounds();
    addComponent();

    setDateAndTime();

    createSidePanelButton();
    setIconOnButtton();

    createPanels();
    setIconOnPanels();

    Listener();

    setColor();
    setFont();


f.revalidate();
f.repaint();
}

void contentVisibilty()
{

mainPanel.setVisible(false);


}


void createComponent()
{

System.out.println("UI create dashBoard");


dashBoardSideBarPanel=new JPanel();
logoLabel=new JLabel(resizeImage(getClass().getResource("imgs/logo.png"),250,170));
dL1=new JLabel("Shri Radhe ji Misthan");
dL2=new JLabel("---Sweet Shop Management System---");
dL3=new JLabel("premium Quality | Pure Taste | Radhe Radhe");
dashBoardHeaderPanel=new JPanel();
photoLabel=new JLabel(resizeImage(getClass().getResource("imgs/shridha.png"),250,180));
photoLabel2=new JLabel(resizeImage(getClass().getResource("imgs/kiyansh.png"),200,170));

dateLabelIcon=new JLabel( new ImageIcon(getClass().getResource("imgs/date.png.gif")));
clockLabelIcon=new JLabel(new ImageIcon("imgs/clock.gif"));
adminLabelIcon=new JLabel(resizeImage(getClass().getResource("imgs/user.png"),50,50));
dateLabel=new JLabel();
timeLabel=new JLabel();
userLabel=new JLabel("Admin");
mssgBoxPanel=new JPanel();
mssg1=new JLabel("Shri Radhe Ji Misthan");
mssg2=new JLabel("   Premium Quality");
mssg3=new JLabel("   Mob: 9876543210");
mssg4=new JLabel("   DEOBAND,UP");
miniHeaderPanel=new JPanel();
miniHeaderL1=new JLabel("Welcome, Admin!");
miniHeaderL2=new JLabel("Here is toda's summary  of your shop.");
miniHeaderLogo=new JLabel(resizeImage(getClass().getResource("imgs/home2.png"),70,60));
cardsPanel=new JPanel();
sp=new JScrollPane(cardsPanel);



}
void setBounds()
{
dashBoardSideBarPanel.setBounds(0,180,270,955);
logoLabel.setBounds(0,5,250,170);

dL1.setBounds(380,10,900,57);
dL2.setBounds(380,70,800,50);
dL3.setBounds(385+30,120,800,50);
dashBoardHeaderPanel.setBounds(270,0,f.getWidth(),180);
photoLabel.setBounds(1200,10,250,180);
photoLabel2.setBounds(10,5,250,180);

dateLabelIcon.setBounds(1460,10,50,50);
clockLabelIcon.setBounds(1460,70,50,50);
adminLabelIcon.setBounds(1460,125,50,50);
dateLabel.setBounds(1530,10,100,50);
timeLabel.setBounds(1530,70,100,50);
userLabel.setBounds(1530,130,100,50);
mssgBoxPanel.setBounds(10,600,250,177);
mssg1.setBounds(10,20,250,30);
mssg2.setBounds(10,55,200,30);
mssg3.setBounds(10,85,200,30);
mssg4.setBounds(10,115,200,30);
miniHeaderPanel.setBounds(295,200,f.getWidth(),110);
miniHeaderL1.setBounds(150,20,500,30);
miniHeaderL2.setBounds(150,60,500,30);
miniHeaderLogo.setBounds(30,20,70,60);
sp.setBounds(300,320,1610,600);

}

void addComponent()
{

f.add(dashBoardSideBarPanel);
f.add(logoLabel);
dashBoardHeaderPanel.add(dL1);
dashBoardHeaderPanel.add(dL2);
dashBoardHeaderPanel.add(dL3);
f.add(dashBoardHeaderPanel);
dashBoardHeaderPanel.add(photoLabel);
dashBoardHeaderPanel.add(photoLabel2);

dashBoardHeaderPanel.add(dateLabelIcon);
dashBoardHeaderPanel.add(clockLabelIcon);
dashBoardHeaderPanel.add(adminLabelIcon);
dashBoardHeaderPanel.add(dateLabel);
dashBoardHeaderPanel.add(timeLabel);
dashBoardHeaderPanel.add(userLabel);
dashBoardSideBarPanel.add(mssgBoxPanel);
mssgBoxPanel.add(mssg1);
mssgBoxPanel.add(mssg2);
mssgBoxPanel.add(mssg3);
mssgBoxPanel.add(mssg4);
f.add(miniHeaderPanel);
miniHeaderPanel.add(miniHeaderL1);
miniHeaderPanel.add(miniHeaderL2);
miniHeaderPanel.add(miniHeaderLogo);
f.add(sp);



}

void setColor()
{

dashBoardSideBarPanel.setBackground(new Color(105, 12, 30));
dL1.setForeground(new Color(91, 55, 28));
dL3.setForeground(new Color(91, 55, 28));
dashBoardHeaderPanel.setBackground(Color.WHITE);
mssg1.setForeground(new Color(91, 55, 28));
miniHeaderPanel.setBackground(Color.WHITE);
miniHeaderL1.setBackground(new Color(91, 55, 28));
cardsPanel.setBackground(Color.GREEN);
cardsPanel.setBackground(Color.WHITE);



}


ImageIcon resizeImage(URL url,int h,int w)
{
ImageIcon icon=new ImageIcon(url);
Image img=icon.getImage();
Image resizeIcon =img.getScaledInstance(h,w,Image.SCALE_SMOOTH);
return new ImageIcon(resizeIcon);

}

void setFont()
{

dL1.setFont(new Font("ARIAL",Font.BOLD,55));
dL2.setFont(new Font("ARIAL",Font.BOLD,30));
dL3.setFont(new Font("ARIAL",Font.PLAIN,23));
dateLabel.setFont(new Font("ARIAL",Font.BOLD,18));
timeLabel.setFont(new Font("ARIAL",Font.BOLD,18));
userLabel.setFont(new Font("ARIAL",Font.BOLD,18));
mssg1.setFont(new Font("ARIAL",Font.PLAIN,22));
mssg2.setFont(new Font("ARIAL",Font.PLAIN,18));
mssg3.setFont(new Font("ARIAL",Font.BOLD,18));
mssg4.setFont(new Font("ARIAL",Font.PLAIN,22));
miniHeaderL1.setFont(new Font("ARIAL",Font.PLAIN,25));
miniHeaderL2.setFont(new Font("ARIAL",Font.PLAIN,20));


for(int i=0;i<buttons.length;i++)
{
buttons[i].setFont(new Font("ARIAL",Font.PLAIN,22));
}



}

void setDateAndTime()
{

Date d=new Date();

SimpleDateFormat date=new SimpleDateFormat("dd/MM/yyyy");
dateLabel.setText(date.format(d));

SimpleDateFormat time=new SimpleDateFormat("hh:mm:ss");
timeLabel.setText(time.format(d));
 
Timer t=new Timer(1000,e ->
{

Date currentDate=new Date();


dateLabel.setText(date.format(currentDate));
timeLabel.setText(time.format(currentDate));




});
 
t.start();

}

String buttonsName[]={

"Dashboard",
"Add Item",
"Stock",
"Sales/Billing",
"Orders",
"Customers",
"Reports",
"Settings",
"Logout"

};
void createSidePanelButton()
{

int y=10;
for(int i=0;i<buttons.length;i++)
{

buttons[i]=new JButton(buttonsName[i]);
buttons[i].setBounds(10,y,230,50);
dashBoardSideBarPanel.add(buttons[i]);
y+=65;

}




}

void setIconOnButtton()
{

buttons[0].setIcon(resizeImage(getClass().getResource("imgs/home.png"),60,40));
buttons[0].setHorizontalAlignment(SwingConstants.LEFT);
buttons[0].setIconTextGap(15);
buttons[1].setIcon(resizeImage(getClass().getResource("imgs/add.png"),60,50));
buttons[1].setHorizontalAlignment(SwingConstants.LEFT);
buttons[1].setIconTextGap(15);
buttons[2].setIcon(resizeImage(getClass().getResource("imgs/stock.png"),50,50));
buttons[2].setHorizontalAlignment(SwingConstants.LEFT);
buttons[2].setIconTextGap(20);
buttons[3].setIcon(resizeImage(getClass().getResource("imgs/sales.png"),60,40));
buttons[3].setHorizontalAlignment(SwingConstants.LEFT);
buttons[3].setIconTextGap(15);
buttons[4].setIcon(resizeImage(getClass().getResource("imgs/orders.png"),70,40));
buttons[4].setHorizontalAlignment(SwingConstants.LEFT);
buttons[4].setIconTextGap(15);
buttons[5].setIcon(resizeImage(getClass().getResource("imgs/customer.png"),70,40));
buttons[5].setHorizontalAlignment(SwingConstants.LEFT);
buttons[5].setIconTextGap(15);
buttons[6].setIcon(resizeImage(getClass().getResource("imgs/reports.png"),70,40));
buttons[6].setHorizontalAlignment(SwingConstants.LEFT);
buttons[6].setIconTextGap(15);
buttons[7].setIcon(resizeImage(getClass().getResource("imgs/setting.png"),70,40));
buttons[7].setHorizontalAlignment(SwingConstants.LEFT);
buttons[8].setIcon(resizeImage(getClass().getResource("imgs/logout.png"),60,40));
buttons[8].setHorizontalAlignment(SwingConstants.LEFT);
buttons[8].setIconTextGap(15);










}

Connection con;

void createPanels()
{










Color panelColors[] = {
        new Color(255, 242, 242),
        new Color(238, 248, 253),
        new Color(243, 250, 235),
        new Color(255, 249, 230),
        new Color(248, 240, 252),
        new Color(255, 242, 242),
        new Color(238, 248, 253),
        new Color(243, 250, 235),
        new Color(255, 249, 230),
        new Color(248, 240, 252)
    };

int x=0;


for(int i=0;i<panels.length;i++)
{




panels[i]=new JPanel();
panels[i].setLayout(null);
panels[i].setBounds(x,0,270,530);
cardsPanel.add(panels[i]);
x+=290;

panels[i].setBackground(panelColors[i%panelColors.length]);
}






cardsPanel.setPreferredSize(new Dimension(panels.length * 290, 530));

}









































JLabel l[]=new JLabel[jd.countRow()];
String name[]=new String[jd.countRow()];
String unit[]=new String[jd.countRow()];
String stock[]=new String[jd.countRow()];
String price[]=new String[jd.countRow()];


void setIconOnPanels()
{



 











try
{

PreparedStatement ps=con.prepareStatement("SELECT * FROM sweets");
ResultSet rs=ps.executeQuery();
int i=0;
while(rs.next())
{


name[i]=rs.getString(1);
stock[i]=rs.getString(3);
unit[i]=rs.getString(4);
price[i]=rs.getString(5);
String path=rs.getString(7);


URL url=new File(path).toURI().toURL();


sweetsLabel[i]=new JLabel(resizeImage(url,190,190));




sweetsLabel[i].setBounds(5,50,260,190);


panels[i].add(sweetsLabel[i]);





i++;




}

rs.close();
ps.close();

}


catch(Exception ex)
{


JOptionPane.showMessageDialog(null,ex.getMessage());


}






for(int i=0;i<panels.length;i++)
{

l[i]=new JLabel("<html><center><font size='5'><<b>"+name[i]+"</b><br><br>Stock - "+unit[i]+"<br><b>"+stock[i]+"</b><br><br>price (₹)<br><b>"+price[i]+"</b></font></center></html>");

l[i].setBounds(95,210,190,300);

panels[i].add(l[i]);





}








}

void Listener()
{


for (int i = 0; i < buttons.length; i++)
    {
        final int index = i;

        buttons[i].addMouseListener(new MouseAdapter()
        {
            @Override
            public void mousePressed(MouseEvent e)
            {
                setDafaultButtonColor();

                buttons[index].setBackground(new Color(212, 160, 23));
            }
        });
    }





buttons[7].addActionListener(e->
{

        JOptionPane.showMessageDialog(null, "This option is comming soon");




});



buttons[8].addActionListener( e->
{


int confirm=JOptionPane.showConfirmDialog(null,"Are you sure from Logout :","Alert",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

if(confirm!=JOptionPane.YES_OPTION)
{

return;


}


System.exit(0);





});



buttons[6].addActionListener(e->
{

hideAllPanel();

reportPanel=reports.createReports(f,this);

System.out.println("Report button click");





});





buttons[0].addActionListener(e -> {

refreshDataOnTextCardPanel();


hideAllPanel();


showDashboard();
cardsPanel.revalidate();
        cardsPanel.repaint();




});


buttons[1].addActionListener(e ->
{

hideAllPanel();





    



     addItemPanel =
        ai.createAddItem(buttons, sp, f, miniHeaderPanel, cardsPanel, this);

    

});




buttons[2].addActionListener(e ->  {


hideAllPanel();



     

 s.createStock(miniHeaderPanel,cardsPanel,sp,f, this,ai);



});







buttons[3].addActionListener(e ->  {

hideAllPanel();




 salesMainPanel=sales.createSales(this);
 



});

//zz

buttons[4].addActionListener(e -> {

  hideAllPanel();


    // Orders create
    ordersPanel = orders.createOrders(this);

    // Orders show
    if(ordersPanel != null)
    {
        ordersPanel.setVisible(true);
    }

});



buttons[5].addActionListener(e ->
{

hideAllPanel();


customerPanel=customer.createCustomers( f,this);



});



}








void refreshDataOnTextCardPanel()
{
    try
    {
        PreparedStatement ps = con.prepareStatement(
            "SELECT sweets_name, stock, unit, price FROM sweets"
        );

        ResultSet rs = ps.executeQuery();

        int i = 0;

        while(rs.next() && i < l.length)
        {
            String name = rs.getString("sweets_name");
            String stock = rs.getString("stock");
            String unit = rs.getString("unit");
            String price = rs.getString("price");

            l[i].setText(
                "<html><center><font size='5'><b>"
                + name +
                "</b><br><br>Stock - "
                + unit +
                "<br><b>"
                + stock +
                "</b><br><br>price (₹)<br><b>"
                + price +
                "</b></font></center></html>"
            );

            i++;
        }

        rs.close();
        ps.close();

        cardsPanel.revalidate();
        cardsPanel.repaint();
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}












 void refreshData()
{


 int count=jd.countRow();

 if(data!=count)
 {

 data = count;
 panels=new JPanel[count];
 l=new JLabel[count];
 name=new String[count];
sweetsLabel = new JLabel[count];
 unit=new String[count];
 stock=new String[count];
 price=new String[count];
  cardsPanel.removeAll();
  createPanels();
  setIconOnPanels();

}

}




void showDashboard()
{



    dashBoardSideBarPanel.setVisible(true);

    dashBoardHeaderPanel.setVisible(true);
    miniHeaderPanel.setVisible(true);
    sp.setVisible(true);



   f.revalidate();
    f.repaint();
}



void hideDashboard()
{


    
if(dashBoardSideBarPanel!=null)
 {

f.revalidate();
    f.repaint();
   dashBoardSideBarPanel.setVisible(true);
}  

if(dashBoardHeaderPanel!=null)
{
f.revalidate();
    f.repaint();
  dashBoardHeaderPanel.setVisible(true);
}


if(miniHeaderPanel!=null)
{

f.revalidate();
    f.repaint();
    miniHeaderPanel.setVisible(false);
}

if(sp!=null)

{
f.revalidate();
    f.repaint();
    sp.setVisible(false);

}

   
}




void hideAllPanel()
{

//``










if(reportPanel!=null)
{


System.out.println("ffffffffffffffffffffffffffffffffff");

reportPanel.setVisible(false);



}






if(dashBoardSideBarPanel!=null)
 {

f.revalidate();
    f.repaint();
   dashBoardSideBarPanel.setVisible(true);
}  

if(dashBoardHeaderPanel!=null)
{
f.revalidate();
    f.repaint();
  dashBoardHeaderPanel.setVisible(true);
}


if(miniHeaderPanel!=null)
{

f.revalidate();
    f.repaint();
    miniHeaderPanel.setVisible(false);
}

if(sp!=null)

{
f.revalidate();
    f.repaint();
    sp.setVisible(false);

}













if(addItemPanel != null)
    {
        addItemPanel.setVisible(true);
    }














if(salesMainPanel != null)
    {
        salesMainPanel.setVisible(false);
    }





if(ordersPanel!=null)
{

ordersPanel.setVisible(false);

}


 



ai.hideAddItem();


s.hideStock();



if(customerPanel!=null)
{

customerPanel.setVisible(false);



}




    















}


void setDafaultButtonColor()
{
    for (int i = 0; i < buttons.length; i++)
    {
        buttons[i].setBackground(new Color(230, 240, 250));
    }
}






}






