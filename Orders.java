import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import com.toedter.calendar.JDateChooser;
import java.util.Date;
import java.sql.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;

class Orders
{

boolean orderCreated=false;

DashBoard db;

JPanel ordersPanel;
JLabel ordersManagementLabel;
JPanel filterOrdersPanel;
TitledBorder filterOrdersPanelBorder;
JLabel fromDateLabel;
JLabel paymentModeLabel;
JComboBox<String> paymentComboBox;
JLabel toDateLabel;
JLabel customerNameLabel;
JTextField customerNameField;
JLabel orderStatusLabel;
JComboBox<String> orderStatusComboBox;
JButton searchButton;
JButton resetButton;
JButton cancelButton;


JPanel orderListPanel;
TitledBorder orderListPanelBorder;

JPanel orderSummaryPanel;
TitledBorder orderSummaryPanelBorder;
JPanel cardPanel1;
JPanel cardPanel2;
JPanel cardPanel3;
JPanel cardPanel4;
JPanel cardPanel5;
JDateChooser fromDateChooser;
JDateChooser toDateChooser;
JLabel cardIconLabel1;
JLabel cardIconLabel2;
JLabel cardIconLabel3;
JLabel cardIconLabel4;
JLabel cardIconLabel5;

JTable orderTable;
JScrollPane orderTableScrollPane;
DefaultTableModel model;
Connection con;
JLabel cardLabel1;
JLabel cardLabel2;
JLabel cardLabel3;
JLabel cardLabel4;
JLabel cardLabel5;



public JPanel createOrders(DashBoard db)
{

this.db=db;
con=db.con;




 




if(!orderCreated)
{




orderCreated=true;





createComponent();

componentLayout();
countOrder();
countCompleteOrder();
countPendingOrder();
countCompleteOrderMoney();
countCanceledOrders();


setBounds();
addComponent();
setIconOnComponent();
setBorder();
addListener();
setDataOnTable();





setFont();
setColor();







return ordersPanel;

}














model.setRowCount(0);


countOrder();
countCompleteOrder();
countPendingOrder();
countCompleteOrderMoney();
countCanceledOrders();

setDataOnTable();



return ordersPanel;


}








void createComponent()
{

System.out.println("UI create order ");

ordersManagementLabel=new JLabel("   Orders Management");
ordersPanel=new JPanel();
filterOrdersPanel=new JPanel();
fromDateLabel=new JLabel("From Date");
fromDateChooser=new JDateChooser();
paymentModeLabel=new JLabel("Payment Mode");
paymentComboBox=new JComboBox<>();
paymentComboBox.addItem("All");

paymentComboBox.addItem("Cash");
paymentComboBox.addItem("UPI");
paymentComboBox.addItem("Card");
paymentComboBox.addItem("Net Banking");
paymentComboBox.addItem("Other");

toDateLabel=new JLabel("To Date");
toDateChooser=new JDateChooser();
customerNameLabel=new JLabel("Customer Name");
customerNameField=new JTextField();
orderStatusLabel=new JLabel("Order Status");
orderStatusComboBox=new JComboBox<>();
orderStatusComboBox.addItem("All");
orderStatusComboBox.addItem("Completed");
orderStatusComboBox.addItem("pending");
orderStatusComboBox.addItem("Canceled");
searchButton=new JButton("Search");
resetButton=new JButton("Reset");
cancelButton=new JButton("Cancel");
orderListPanel=new JPanel();
orderSummaryPanel=new JPanel();
cardPanel1=new JPanel();
cardPanel2=new JPanel();
cardPanel3=new JPanel();
cardPanel4=new JPanel();
cardPanel5=new JPanel();

cardIconLabel1=new JLabel();
cardIconLabel2=new JLabel();
cardIconLabel3=new JLabel();
cardIconLabel4=new JLabel();
cardIconLabel5=new JLabel();
orderTable=new JTable();
orderTableScrollPane=new JScrollPane(orderTable);
model=new DefaultTableModel();
model.addColumn("Order No");
model.addColumn("Order Date");
model.addColumn("Customer Name");
model.addColumn("Mobile No");
model.addColumn("Total Items");
model.addColumn("Sub Total");
model.addColumn("Discount");
model.addColumn("Tax (GST)");
model.addColumn("Grand Total");
model.addColumn("Payment Mode");
model.addColumn("Status");
cardLabel1=new JLabel("<html><center><font color='BLUE' size='4'>Total Orders<br><font color='BLUE' size='6'>"+order+"</font></font></center></html>");
cardLabel2=new JLabel("<html><center><font color='GREEN' size='4'>Complete Orders<br><font color='GREEN' size='6'>"+countCompleteOrder+"</font></font></center></html>");
cardLabel3=new JLabel("<html><center><font color='ORANGE' size='4'>Pending Orders<br><font color='ORANGE' size='6'>"+countPendingOrder+"</font></font></center></html>");


cardLabel4=new JLabel("<html><center><font color='RED' size='4'>Canceld Orders<br><font color='RED' size='6'>"+countCanceldOrders+"</font></font></center></html>");




cardLabel5=new JLabel("<html><center><font color='PURPLE' size='4'>Total Sales<br><font color='PURPLE' size='6'>"+countCompleteOrderMoney+"</font></font></center></html>");








}


void setBounds()
{

ordersPanel.setBounds(295,180,1610,772);
ordersManagementLabel.setBounds(0,0,ordersPanel.getWidth(),40);
filterOrdersPanel.setBounds(0,55,ordersPanel.getWidth(),120);
fromDateLabel.setBounds(10, 30, 80, 25);
fromDateChooser.setBounds(115,30,200,25);
paymentModeLabel.setBounds(10,75,100,25);
paymentComboBox.setBounds(115,75,200,25);
toDateLabel.setBounds(340, 30, 80, 25);
toDateChooser.setBounds(455,30,200,25);
customerNameLabel.setBounds(340,75,110,25);
customerNameField.setBounds(455,75,200,25);
orderStatusLabel.setBounds(690,30,110,25);
orderStatusComboBox.setBounds(805,30,220,25);
searchButton.setBounds(1180-65-40,50,150,40);
resetButton.setBounds(1360-65-40,50,150,40);
cancelButton.setBounds(1255+170,50,150,40);
orderListPanel.setBounds(0,195+10,ordersPanel.getWidth(),385);
orderSummaryPanel.setBounds(0,620,ordersPanel.getWidth(),150);

cardPanel1.setBounds(30,40,280,80);
cardPanel2.setBounds(340,40,280,80);
cardPanel3.setBounds(650,40,280,80);
cardPanel4.setBounds(960,40,280,80);
cardPanel5.setBounds(1270,40,280,80);

cardIconLabel1.setBounds(220,15,50,50);
cardIconLabel2.setBounds(220,20,40,40);
cardIconLabel3.setBounds(215,12,60,60);
cardIconLabel4.setBounds(218,18,40,40);
cardIconLabel5.setBounds(218,12,50,50);
orderTableScrollPane.setBounds(20,30,ordersPanel.getWidth()-35,355-15);
cardLabel1.setBounds(20,10,185,60);
cardLabel2.setBounds(20,10,185,60);
cardLabel3.setBounds(20,10,185,60);
cardLabel4.setBounds(20,10,185,60);
cardLabel5.setBounds(20,10,185,60);





}



void addComponent()
{

db.f.add(ordersPanel);
ordersPanel.add(ordersManagementLabel);
ordersPanel.add(filterOrdersPanel);
filterOrdersPanel.add(fromDateLabel);
filterOrdersPanel.add(fromDateChooser);
filterOrdersPanel.add(paymentModeLabel);
filterOrdersPanel.add(paymentComboBox);
filterOrdersPanel.add(toDateLabel);
filterOrdersPanel.add(toDateChooser);
filterOrdersPanel.add(customerNameLabel);
filterOrdersPanel.add(customerNameField);
filterOrdersPanel.add(orderStatusLabel);
filterOrdersPanel.add(orderStatusComboBox);
filterOrdersPanel.add(searchButton);
filterOrdersPanel.add(resetButton);
filterOrdersPanel.add(cancelButton);

ordersPanel.add(orderListPanel);
ordersPanel.add(orderSummaryPanel);

orderSummaryPanel.add(cardPanel1);
orderSummaryPanel.add(cardPanel2);
orderSummaryPanel.add(cardPanel3);
orderSummaryPanel.add(cardPanel4);
orderSummaryPanel.add(cardPanel5);


cardPanel1.add(cardIconLabel1);
cardPanel2.add(cardIconLabel2);
cardPanel3.add(cardIconLabel3);
cardPanel4.add(cardIconLabel4);
cardPanel5.add(cardIconLabel5);

orderListPanel.add(orderTableScrollPane);

orderTable.setModel(model);

cardPanel1.add(cardLabel1);
cardPanel2.add(cardLabel2);
cardPanel3.add(cardLabel3);
cardPanel4.add(cardLabel4);
cardPanel5.add(cardLabel5);






}



void setFont()
{

ordersManagementLabel.setFont(new Font("ARIAL",Font.BOLD,20));
filterOrdersPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,18));
fromDateLabel.setFont(new Font("Arial",Font.BOLD,13));
paymentModeLabel.setFont(new Font("Arial",Font.BOLD,13));
toDateLabel.setFont(new Font("Arial",Font.BOLD,13));
customerNameLabel.setFont(new Font("Arial",Font.BOLD,13));
orderStatusLabel.setFont(new Font("Arial",Font.BOLD,13));
searchButton.setFont(new Font("ARIAL",Font.PLAIN,18));
resetButton.setFont(new Font("ARIAL",Font.PLAIN,18));
orderListPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,18));
orderSummaryPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,18));
orderTable.setFont(new Font("Arial",Font.PLAIN,15));


}



void setColor()
{

ordersPanel.setBackground(Color.WHITE);

ordersManagementLabel.setOpaque(true);
ordersManagementLabel.setBackground(new Color(110, 5, 33));
ordersManagementLabel.setForeground(Color.WHITE);
filterOrdersPanel.setBackground(Color.WHITE);
filterOrdersPanelBorder.setTitleColor(new Color(110, 5, 33));
filterOrdersPanel.setBackground(Color.WHITE);
paymentComboBox.setBackground(Color.WHITE);
orderStatusComboBox.setBackground(Color.WHITE);
searchButton.setBackground(new Color(116, 10, 33));
searchButton.setForeground(Color.WHITE);
orderListPanel.setBackground(Color.WHITE);
orderListPanelBorder.setTitleColor(new Color(110, 5, 33));
orderSummaryPanel.setBackground(Color.WHITE);
orderSummaryPanelBorder.setTitleColor(new Color(110, 5, 33));
cardPanel1.setBackground(new Color(243, 249, 255));

cardPanel2.setBackground(new Color(244, 251, 245));
cardPanel3.setBackground(new Color(255, 248, 239));
cardPanel4.setBackground(new Color(255, 245, 245));
cardPanel5.setBackground(new Color(250, 246, 255));


}



void componentLayout()
{

ordersPanel.setLayout(null);
filterOrdersPanel.setLayout(null);
orderListPanel.setLayout(null);
orderSummaryPanel.setLayout(null);
cardPanel1.setLayout(null);
cardPanel2.setLayout(null);
cardPanel3.setLayout(null);
cardPanel4.setLayout(null);
cardPanel5.setLayout(null);


}


void setIconOnComponent()
{

ordersManagementLabel.setIcon(db.resizeImage(getClass().getResource("imgs/orders2.png"),30,30));
searchButton.setIcon(db.resizeImage(getClass().getResource("imgs/search.png"),30,30));
 searchButton.setHorizontalAlignment(SwingConstants.LEFT);
 searchButton.setIconTextGap(15);


resetButton.setIcon(db.resizeImage(getClass().getResource("imgs/ref.png"),30,30));
 resetButton.setHorizontalAlignment(SwingConstants.LEFT);
 resetButton.setIconTextGap(15);
  

cardIconLabel1.setIcon(db.resizeImage(getClass().getResource("imgs/ord.png"),50,50));
cardIconLabel2.setIcon(db.resizeImage(getClass().getResource("imgs/greenTick.png"),40,40));
cardIconLabel3.setIcon(db.resizeImage(getClass().getResource("imgs/clock.png"),60,60));
cardIconLabel4.setIcon(db.resizeImage(getClass().getResource("imgs/s_remove.png"),40,40));
cardIconLabel5.setIcon(db.resizeImage(getClass().getResource("imgs/dollar.png"),40,40));

cancelButton.setIcon(db.resizeImage(getClass().getResource("imgs/remove.png"),30,30));

cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
cancelButton.setIconTextGap(15);

}


void setBorder()
{

ordersManagementLabel.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));

filterOrdersPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225, 213, 210),2),"Search / Filter Orders");

filterOrdersPanel.setBorder(filterOrdersPanelBorder);

orderListPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Order List");

orderListPanel.setBorder(orderListPanelBorder);


orderSummaryPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Order Summary");
orderSummaryPanel.setBorder(orderSummaryPanelBorder);

cardPanel1.setBorder(BorderFactory.createLineBorder(new Color(199, 222, 247),1));
cardPanel2.setBorder(BorderFactory.createLineBorder(new Color(205, 232, 210),1));
cardPanel3.setBorder(BorderFactory.createLineBorder(new Color(248, 216, 181),1));
cardPanel4.setBorder(BorderFactory.createLineBorder(new Color(245, 204, 204),1));
cardPanel5.setBorder(BorderFactory.createLineBorder(new Color(220, 203, 247),1));


}


void addListener()
{

customerNameField.setText("Enter Customer Name");
customerNameField.setForeground(Color.GRAY);
customerNameField.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(customerNameField.getText().equals("Enter Customer Name"))
{

customerNameField.setText("");
customerNameField.setForeground(Color.BLACK);


}



}






@Override
public void focusLost(FocusEvent e)
{

if(customerNameField.getText().isEmpty())
{


customerNameField.setForeground(Color.GRAY);

customerNameField.setText("Enter Customer Name");



}




}








});






searchButton.addActionListener(e -> {
















    Date fromDate = fromDateChooser.getDate();
    Date toDate = toDateChooser.getDate();


if (fromDate == null && toDate == null) {
  
  searchDataFilter();


  // Date select nahi hai


return;

}



    if (fromDate == null || toDate == null) {

        JOptionPane.showMessageDialog(
            null,
            "Please select both dates"
        );

        return;
    }

    if (fromDate.after(toDate)) {

        JOptionPane.showMessageDialog(
            null,
            "From Date cannot be greater than To Date"
        );

        return;
    }

    // Yahan tumhara records search/filter wala code aayega




    Date from_date=fromDateChooser.getDate();
    Date to_date=toDateChooser.getDate();

SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd");
SimpleDateFormat sdf2=new SimpleDateFormat("yyyy-MM-dd");
String  fromDate_=sdf1.format(from_date);
String  toDate_=sdf2.format(to_date);
String orderStatus=orderStatusComboBox.getSelectedItem().toString();





//``
    try
{


if(orderStatusComboBox.getSelectedItem().equals("All"))
{

   model.setRowCount(0);


  String paymentType=paymentComboBox.getSelectedItem().toString();




   if(paymentType.equals("All"))
{

  PreparedStatement ps=con.prepareStatement("SELECT * FROM orders WHERE DATE(order_date) BETWEEN ? AND ? ORDER BY order_date DESC");
  ps.setString(1,fromDate_);
   ps.setString(2,toDate_);

  ResultSet rs=ps.executeQuery();


  while(rs.next())
  {


   model.addRow(new Object[]{

    rs.getString(1),
    rs.getString(3),
    rs.getString(4),
    rs.getString(5),
    rs.getString(6),
    rs.getString(7),
    rs.getString(8),
    rs.getString(9),
    rs.getString(10),
    rs.getString(11),
    rs.getString(12)
    






});



   }




orderTable.setModel(model);
     
ps.close();









}




}













if(!orderStatusComboBox.getSelectedItem().equals("All"))
{
    model.setRowCount(0);

    String paymentType = paymentComboBox.getSelectedItem().toString();

    if(paymentType.equals("All"))
    {
        String orderStatus_ = orderStatusComboBox.getSelectedItem().toString();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders WHERE DATE(order_date) BETWEEN ? AND ? AND status=? ORDER BY order_date DESC"
        );

        ps.setString(1, fromDate_);
        ps.setString(2, toDate_);
        ps.setString(3, orderStatus_);

     
        ResultSet rs = ps.executeQuery();



        while(rs.next())
        {

            model.addRow(new Object[]{
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }


        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}






if(!paymentComboBox.getSelectedItem().equals("All"))
{
    if(orderStatusComboBox.getSelectedItem().equals("All"))
    {
        model.setRowCount(0);

        String paymentType = paymentComboBox.getSelectedItem().toString();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders " +
            "WHERE DATE(order_date) BETWEEN ? AND ? AND payment_Mode=? ORDER BY order_date DESC"
        );

        ps.setString(1, fromDate_);
        ps.setString(2, toDate_);
        ps.setString(3, paymentType);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {

            model.addRow(new Object[]{
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }


        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}
























if(!paymentComboBox.getSelectedItem().equals("All"))
{
    if(!orderStatusComboBox.getSelectedItem().equals("All"))
    {
        model.setRowCount(0);

        String paymentType = paymentComboBox.getSelectedItem().toString();
         orderStatus = orderStatusComboBox.getSelectedItem().toString();


        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders " +
            "WHERE DATE(order_date) BETWEEN ? AND ? AND payment_Mode=? AND status=? ORDER BY order_date DESC" 
        );

        ps.setString(1, fromDate_);
        ps.setString(2, toDate_);
        ps.setString(3, paymentType);
        ps.setString(4, orderStatus);


        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {

            model.addRow(new Object[]{
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }


        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}











if(!paymentComboBox.getSelectedItem().equals("All"))
{
    if(!orderStatusComboBox.getSelectedItem().equals("All"))
    {
        model.setRowCount(0);

        String paymentType = paymentComboBox.getSelectedItem().toString();
         orderStatus = orderStatusComboBox.getSelectedItem().toString();


        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders " +
            "WHERE DATE(order_date) BETWEEN ? AND ? AND payment_Mode=? AND status=? ORDER BY order_date DESC "
        );

        ps.setString(1, fromDate_);
        ps.setString(2, toDate_);
        ps.setString(3, paymentType);
        ps.setString(4, orderStatus );


        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {

            model.addRow(new Object[]{
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }


        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}





}

catch(Exception ex)
{


JOptionPane.showMessageDialog(null,ex.getMessage());

}
  








   




});







customerNameField.addActionListener(e-> {


try
{
filterByName();
}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());

}


});



resetButton.addActionListener(e-> {



model.setRowCount(0);

setDataOnTable();


});








cancelButton.addActionListener(e -> {
    cancelOrder();
});







}

int order=0;

void setDataOnTable()
{

try
{

PreparedStatement ps=con.prepareStatement("SELECT * FROM orders ORDER BY order_date DESC");
ResultSet rs=ps.executeQuery();




while(rs.next())
{

Timestamp ts=rs.getTimestamp(3);
SimpleDateFormat sdf=new SimpleDateFormat("dd/MM/yy HH:mm:ss");
String dateTime=sdf.format(ts);

model.addRow(new  Object[]{



rs.getString(1),
dateTime,
rs.getString(4),
rs.getString(5),
rs.getString(6),
rs.getString(7),
rs.getString(8),
rs.getString(9),
rs.getString(10),
rs.getString(11),
rs.getString(12),




});




}

ps.close();
rs.close();

}



catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());



}



}


void countOrder()

{






try
{
PreparedStatement ps1=con.prepareStatement("SELECT Count(*) FROM orders");

ResultSet rs1=ps1.executeQuery();

if(rs1.next())
{

this.order=rs1.getInt(1);


}

ps1.close();
rs1.close();

}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());



}

cardLabel1.setText("<html><center><font color='BLUE' size='4'>Total Orders<br><font color='BLUE' size='6'>"+order+"</font></font></center></html>");



}








int countPendingOrder=0;

void countPendingOrder()
{





try
{
PreparedStatement ps1=con.prepareStatement("SELECT COUNT(*) FROM  orders WHERE status=?");
ps1.setString(1,"pending");

ResultSet rs1=ps1.executeQuery();

if(rs1.next())
{

this.countPendingOrder=rs1.getInt(1);


}
ps1.close();
rs1.close();

}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());



}



cardLabel3.setText("<html><center><font color='ORANGE' size='4'>Pending Orders<br><font color='ORANGE' size='6'>"+countPendingOrder+"</font></font></center></html>");




}










int countCompleteOrder=0;

void countCompleteOrder()
{









try
{
PreparedStatement ps1=con.prepareStatement("SELECT COUNT(*) FROM  orders WHERE status=?");
ps1.setString(1,"completed");

ResultSet rs1=ps1.executeQuery();

if(rs1.next())
{

this.countCompleteOrder=rs1.getInt(1);


}
ps1.close();
rs1.close();

}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());



}




cardLabel2=new JLabel("<html><center><font color='GREEN' size='4'>Complete Orders<br><font color='GREEN' size='6'>"+countCompleteOrder+"</font></font></center></html>");




}
















String countCompleteOrderMoney="0.00";

void countCompleteOrderMoney()
{








try
{
PreparedStatement ps1=con.prepareStatement("SELECT SUM(grand_total) FROM  orders WHERE status=?");
ps1.setString(1,"completed");

ResultSet rs1=ps1.executeQuery();

if(rs1.next())
{

double countCompleteOrderMoney=rs1.getDouble(1);
this.countCompleteOrderMoney=String.format("%,.2f",countCompleteOrderMoney);


}
ps1.close();
rs1.close();


}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());



}


cardLabel5.setText("<html><center><font color='PURPLE' size='4'>Total Sales<br><font color='PURPLE' size='5'>₹ "+countCompleteOrderMoney+"</font></font></center></html>");




}


int countCanceldOrders=0;

void countCanceledOrders()
{



try
{

PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM orders WHERE status='Canceled'"
        );
ResultSet rs=ps.executeQuery();


if(rs.next())
{

countCanceldOrders=rs.getInt(1);


}

ps.close();
rs.close();



}



catch(Exception ex)
{


JOptionPane.showMessageDialog(null,ex.getMessage());

}


cardLabel4.setText("<html><center><font color='RED' size='4'>Canceld Orders<br><font color='RED' size='6'>"+countCanceldOrders+"</font></font></center></html>");




}



void filterByName() throws Exception
{

model.setRowCount(0);

String customerName=customerNameField.getText();

PreparedStatement ps=con.prepareStatement("SELECT * FROM orders WHERE customer_name LIKE ? ORDER BY order_date DESC");
ps.setString(1,"%"+customerName+"%");

ResultSet rs=ps.executeQuery();


while(rs.next())
{

model.addRow(new Object[]
{

rs.getString(1),
rs.getString(3),
rs.getString(4),
rs.getString(5),
rs.getString(6),
rs.getString(7),
rs.getString(8),
rs.getString(9),
rs.getString(10),
rs.getString(11),
rs.getString(12)





});




}

orderTable.setModel(model);


ps.close();
rs.close();



}




void cancelOrder()
{

int row=orderTable.getSelectedRow();

if(row==-1)
{

JOptionPane.showMessageDialog(null,"Plz select an item row before Cancel order","Alert",JOptionPane.WARNING_MESSAGE);

return;
}


String status=orderTable.getValueAt(row,10).toString();


if(!status.equalsIgnoreCase("Pending"))
{

JOptionPane.showMessageDialog(null,"Only pending order can be canceled","ALERT",JOptionPane.WARNING_MESSAGE);

return;

}



try
{



int confirm=JOptionPane.showConfirmDialog(null,"Are you sure you want to canceld order","Confirm",JOptionPane.YES_NO_OPTION,JOptionPane.WARNING_MESSAGE);

if((confirm!=JOptionPane.YES_OPTION))
{


return;

}

PreparedStatement ps=con.prepareStatement("UPDATE orders SET status=? WHERE order_no=?");

String orderNo=orderTable.getValueAt(row,0).toString();

ps.setString(1,"Canceled");
ps.setString(2,orderNo);
int i=ps.executeUpdate();

if(i>0)
{
countPendingOrder();
countCanceledOrders();

JOptionPane.showMessageDialog(null,"this  Order No "+orderNo+" sucessfully canceled");
}

model.setRowCount(0);

setDataOnTable();


ps.close();





}



catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());


}







}


void searchDataFilter()
{




    try
{


if(orderStatusComboBox.getSelectedItem().equals("All"))
{
    model.setRowCount(0);

    String paymentType = paymentComboBox.getSelectedItem().toString();

    if(paymentType.equals("All"))
    {
        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders ORDER BY order_date DESC"
        );

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            model.addRow(new Object[]
            {
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }

        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}











if(!orderStatusComboBox.getSelectedItem().equals("All"))
{
    model.setRowCount(0);

    String paymentType = paymentComboBox.getSelectedItem().toString();

    if(paymentType.equals("All"))
    {
        String orderStatus_ =
            orderStatusComboBox.getSelectedItem().toString();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders WHERE status=? ORDER BY order_date DESC"
        );

        ps.setString(1, orderStatus_);


        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            model.addRow(new Object[]
            {
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }

        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}



if(!paymentComboBox.getSelectedItem().equals("All"))
{
    if(orderStatusComboBox.getSelectedItem().equals("All"))
    {
        model.setRowCount(0);

        String paymentType =
            paymentComboBox.getSelectedItem().toString();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders WHERE payment_Mode=? ORDER BY order_date DESC"
        );

        ps.setString(1, paymentType);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {

            model.addRow(new Object[]
            {
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }

        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}





















if(!paymentComboBox.getSelectedItem().equals("All"))
{
    if(!orderStatusComboBox.getSelectedItem().equals("All"))
    {
        model.setRowCount(0);

        String paymentType =
            paymentComboBox.getSelectedItem().toString();

        String orderStatus =
            orderStatusComboBox.getSelectedItem().toString();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders " +
            "WHERE payment_Mode=? AND status=? ORDER BY order_date DESC"
        );

        ps.setString(1, paymentType);
        ps.setString(2, orderStatus);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {

            model.addRow(new Object[]
            {
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }

        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}







if(!paymentComboBox.getSelectedItem().equals("All"))
{
    if(!orderStatusComboBox.getSelectedItem().equals("All"))
    {
        model.setRowCount(0);

        String paymentType =
            paymentComboBox.getSelectedItem().toString();

        String orderStatus =
            orderStatusComboBox.getSelectedItem().toString();

        PreparedStatement ps = con.prepareStatement(
            "SELECT * FROM orders " +
            "WHERE payment_Mode=? AND status=? ORDER BY order_date DESC"
        );

        ps.setString(1, paymentType);
        ps.setString(2, orderStatus);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {

            model.addRow(new Object[]
            {
                rs.getString(1),
                rs.getString(3),
                rs.getString(4),
                rs.getString(5),
                rs.getString(6),
                rs.getString(7),
                rs.getString(8),
                rs.getString(9),
                rs.getString(10),
                rs.getString(11),
                rs.getString(12)
            });
        }

        orderTable.setModel(model);

        rs.close();
        ps.close();
    }
}



}

catch(Exception ex)
{


JOptionPane.showMessageDialog(null,ex.getMessage());

}
  







}



}