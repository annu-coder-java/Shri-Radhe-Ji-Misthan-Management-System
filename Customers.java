import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import java.net.URL;
import java.io.File;
import java.net.MalformedURLException;
class Customers
{

JPanel  customerPanel;
JFrame f;
DashBoard db;
JPanel headerPanel;
JLabel l1;
JPanel customerFilterPanel;
TitledBorder customerFilterPanelBorder;
JPanel customerListPanel;
TitledBorder customerListPanelBorder;
JPanel customerDetailPanel;
TitledBorder customerDetailPanelBorder;
JPanel customerSumarryPanel;
TitledBorder customerSumarryPanelBorder;

JPanel  cardPanel1;
JPanel  cardPanel2;
JPanel  cardPanel3;
JPanel  cardPanel4;
JPanel  cardPanel5;


JTextField searchField;
JButton searchButton;
JLabel customerTypeLabel;
JComboBox<String> customerTypeComboBox;
JButton addCustomerButton;
JButton resetButton;

JLabel customerNameLabel;
JTextField  customerNameField;

JLabel mobileNoLabel;
JTextField  mobileNoField;

JLabel addressLabel;
JTextField   addressArea;
JLabel totalItemsLabel;
JTextField totalItemsField;

JLabel totalPurchaseLabel;
JTextField totalPurchaseField;

JLabel lastOrderLabel;
JTextField lastOrderField;
JLabel customerTypeLabel2;
JTextField customerTypeFiled;
JButton editButton;
JLabel photoLabel;
JButton choosePhotoButton;

JLabel cardPanelIconLabel1;
JLabel cardPanelIconLabel2;
JLabel cardPanelIconLabel3;
JLabel cardPanelIconLabel4;
JLabel cardPanelIconLabel5;

Connection con;

JLabel cardPanelTextLabel1;
JLabel cardPanelTextLabel2;
JLabel cardPanelTextLabel3;
JLabel cardPanelTextLabel4;
JLabel cardPanelTextLabel5;



JTable customerListTable;
JScrollPane customerListScrollPane;
DefaultTableModel customerListModel;

boolean customerPanel_=false;

boolean customerEditMode = false;
String editOriginalMobileNo = "";
String editOriginalLastOrder = "";


JPanel createCustomers(JFrame f,DashBoard db)
{


this.f=f;
this.db=db;
con=db.con;


if(customerPanel!=null)
{

customerPanel.setVisible(true);

if(customerPanel_)
{


ReadUserNamePassword();

totalCustomers();
totalOrders();
totalPurchase();
avgPrchase();
regularCustomer();
searchByCustomerType();



return customerPanel;

}



}




ReadUserNamePassword();

createComponent();
customerPanel_=true;

totalCustomers();
totalOrders();
totalPurchase();
avgPrchase();
regularCustomer();
searchByCustomerType();


setLayout();

setBounds();
addComponent();
setIcon();
setBorder();
addListener();



setFont();
setColor();



return customerPanel;

}

void createComponent()
{



System.out.println("UI create customer");


customerPanel=new JPanel();
headerPanel=new JPanel();
l1=new JLabel("   Customers Management");
customerFilterPanel=new JPanel();
customerListPanel=new JPanel();
customerDetailPanel=new JPanel();
customerSumarryPanel=new JPanel();
cardPanel1=new JPanel();
cardPanel2=new JPanel();
cardPanel3=new JPanel();
cardPanel4=new JPanel();
cardPanel5=new JPanel();
searchField=new JTextField();
searchButton=new JButton("Search");
customerTypeLabel=new JLabel("Customer Type ");
customerTypeComboBox=new JComboBox<>();
customerTypeComboBox.addItem("All");
customerTypeComboBox.addItem("Walk-in Customer");
customerTypeComboBox.addItem("Regular Customer");
customerTypeComboBox.addItem("Existing Customer");

addCustomerButton=new JButton("Add Customer");
resetButton=new JButton("Reset");
customerNameLabel=new JLabel("Customer Name ");
customerNameField=new JTextField();
mobileNoLabel=new JLabel("Mobile No");
mobileNoField=new JTextField();
addressLabel=new JLabel("Address");
addressArea=new JTextField();
totalItemsLabel=new JLabel("Total Items");
totalItemsField=new JTextField();

totalPurchaseLabel=new JLabel("Total Purchase");
totalPurchaseField=new JTextField();

lastOrderLabel=new JLabel("Last Order");
lastOrderField=new JTextField();

 customerTypeLabel2=new JLabel("Customer Type");
 customerTypeFiled=new JTextField();
editButton=new JButton("Edit");
photoLabel=new JLabel();
choosePhotoButton=new JButton("Choose Photo");
cardPanelIconLabel1=new JLabel();
cardPanelIconLabel2=new JLabel();
cardPanelIconLabel3=new JLabel();
cardPanelIconLabel4=new JLabel();
cardPanelIconLabel5=new JLabel();

customerListTable=new JTable();
customerListScrollPane=new JScrollPane(customerListTable);
customerListModel=new DefaultTableModel(){

    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};
customerListModel.addColumn("S.No.");
customerListModel.addColumn("Customer Name");
customerListModel.addColumn("Mobile No");
customerListModel.addColumn("Address");
customerListModel.addColumn("Total items");
customerListModel.addColumn("Total Purchase(₹)");
customerListModel.addColumn("Last Orders");
customerListModel.addColumn("Bill Note ");
customerListTable.setModel(customerListModel);

customerListTable.getColumnModel().getColumn(6).setPreferredWidth(150);
customerListTable.getColumnModel().getColumn(0).setPreferredWidth(50);
customerListTable.getColumnModel().getColumn(4).setPreferredWidth(40);

cardPanelTextLabel1=new JLabel();
cardPanelTextLabel2=new JLabel();
cardPanelTextLabel3=new JLabel();
cardPanelTextLabel4=new JLabel();
cardPanelTextLabel5=new JLabel();



}

void  setBounds()
{
customerPanel.setBounds(285-15,180,1660,772);
headerPanel.setBounds(0,0,customerPanel.getWidth()-5,65);
l1.setBounds(10,10,300,50);
customerFilterPanel.setBounds(16,80,customerPanel.getWidth()-35,100);
customerListPanel.setBounds(16,200,customerPanel.getWidth()/2+100,405+10);
customerListScrollPane.setBounds(20,35,customerPanel.getWidth()/2+55,415-50);

customerDetailPanel.setBounds(customerPanel.getWidth()/2+130,200,customerPanel.getWidth()/2-140,405+10);

customerSumarryPanel.setBounds(16,620,customerPanel.getWidth()-22,153);

cardPanel1.setBounds(30,40,280,80);
cardPanel2.setBounds(340,40,280,80);
cardPanel3.setBounds(650,40,280,80);
cardPanel4.setBounds(960,40,280,80);
cardPanel5.setBounds(1270,40,280,80);
searchField.setBounds(15,35,300,40);
searchButton.setBounds(350,35,150,40);
customerTypeLabel.setBounds(530,40,150,30);
customerTypeComboBox.setBounds(700,40,200,25);

addCustomerButton.setBounds(customerPanel.getWidth()/2+130,40,170,40);
resetButton.setBounds(customerPanel.getWidth()/2+340,40,170,40);
editButton.setBounds(customerPanel.getWidth()/2 + 545, 40, 200, 40);
customerNameLabel.setBounds(10,40,150,30);

customerNameField.setBounds(180,43,200,28);
mobileNoLabel.setBounds(10,85,150,30);
mobileNoField.setBounds(180,85,200,28);
addressLabel.setBounds(10,125,150,30);
addressArea.setBounds(180,125,200,60);
totalItemsLabel.setBounds(10,195,150,28);
totalItemsField.setBounds(180,195,200,28);
totalPurchaseLabel.setBounds(10,237,150,28);
totalPurchaseField.setBounds(180,237,200,28);
lastOrderLabel.setBounds(10,279,150,28);
lastOrderField.setBounds(180,279,200,28);

customerTypeLabel2.setBounds(10,321,150,28);
customerTypeFiled.setBounds(180,321,200,28);

photoLabel.setBounds(410,40,250,200);
choosePhotoButton.setBounds(410,245,250,40);

cardPanelIconLabel1.setBounds(15,10,70,70);
cardPanelIconLabel2.setBounds(15,10,70,70);
cardPanelIconLabel3.setBounds(15,10,70,70);
cardPanelIconLabel4.setBounds(15,10,100,70);
cardPanelIconLabel5.setBounds(15,10,70,70);

//280,80
cardPanelTextLabel1.setBounds(100,10,170,65);
cardPanelTextLabel2.setBounds(100,10,170,65);
cardPanelTextLabel3.setBounds(100,10,170,65);
cardPanelTextLabel4.setBounds(100,10,170,65);
cardPanelTextLabel5.setBounds(100,10,170,65);






}

void addComponent()
{

f.add(customerPanel);
customerPanel.add(headerPanel);
headerPanel.add(l1);
customerPanel.add(customerFilterPanel);
customerPanel.add(customerListPanel);
customerListPanel.add(customerListScrollPane);
customerPanel.add(customerDetailPanel);
customerPanel.add(customerSumarryPanel);
customerSumarryPanel.add(cardPanel1);
customerSumarryPanel.add(cardPanel2);
customerSumarryPanel.add(cardPanel3);
customerSumarryPanel.add(cardPanel4);
customerSumarryPanel.add(cardPanel5);
customerFilterPanel.add(searchField);
customerFilterPanel.add(searchButton);
customerFilterPanel.add(customerTypeLabel);
customerFilterPanel.add(customerTypeComboBox);
customerFilterPanel.add(addCustomerButton);
customerFilterPanel.add(resetButton);
customerDetailPanel.add(customerNameLabel);
customerDetailPanel.add(customerNameField);
customerDetailPanel.add(mobileNoLabel);
customerDetailPanel.add(mobileNoField);
customerDetailPanel.add(addressLabel);
customerDetailPanel.add(addressArea);
customerDetailPanel.add(totalItemsLabel);
customerDetailPanel.add(totalItemsField);
customerDetailPanel.add(totalPurchaseLabel);
customerDetailPanel.add(totalPurchaseField);
customerDetailPanel.add(lastOrderLabel);
customerDetailPanel.add(lastOrderField);
customerDetailPanel.add(customerTypeLabel2);
customerDetailPanel.add(customerTypeFiled);
customerFilterPanel.add(editButton);
customerDetailPanel.add(photoLabel);
customerDetailPanel.add(choosePhotoButton);
cardPanel1.add(cardPanelIconLabel1);
cardPanel2.add(cardPanelIconLabel2);
cardPanel3.add(cardPanelIconLabel3);
cardPanel4.add(cardPanelIconLabel4);
cardPanel5.add(cardPanelIconLabel5);

cardPanel1.add(cardPanelTextLabel1);
cardPanel2.add(cardPanelTextLabel2);
cardPanel3.add(cardPanelTextLabel3);
cardPanel4.add(cardPanelTextLabel4);
cardPanel5.add(cardPanelTextLabel5);









}



void setFont()
{

l1.setFont(new Font("ARIAL",Font.BOLD,20));
customerFilterPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,20));
customerListPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,20));
customerDetailPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,20));
customerSumarryPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,20));
searchButton.setFont(new Font("ARIAL",Font.PLAIN,18));
customerTypeLabel.setFont(new Font("ARIAL",Font.BOLD,18));

addCustomerButton.setFont(new Font("ARIAL",Font.BOLD,13));

resetButton.setFont(new Font("ARIAL",Font.BOLD,15));
customerNameLabel.setFont(new Font("ARIAL",Font.BOLD,17));
mobileNoLabel.setFont(new Font("ARIAL",Font.BOLD,17));
addressLabel.setFont(new Font("ARIAL",Font.BOLD,17));
totalItemsLabel.setFont(new Font("ARIAL",Font.BOLD,17));
totalPurchaseLabel.setFont(new Font("ARIAL",Font.BOLD,17));
lastOrderLabel.setFont(new Font("ARIAL",Font.BOLD,17));
customerTypeLabel2.setFont(new Font("ARIAL",Font.BOLD,17));
searchField.setFont(new Font("ARIAL",Font.BOLD,17));
customerListTable.setFont(new Font("ARIAL",Font.PLAIN,13));

}


void setColor()
{
customerPanel.setBackground(Color.WHITE);
headerPanel.setBackground(new Color(110, 5, 33));
l1.setForeground(Color.WHITE);
customerFilterPanel.setBackground(Color.WHITE);
customerFilterPanelBorder.setTitleColor(new Color(110,5,33));
customerListPanelBorder.setTitleColor(new Color(110,5,33));

customerListPanel.setBackground(Color.WHITE);
customerDetailPanel.setBackground(Color.WHITE);
customerDetailPanelBorder.setTitleColor(new Color(110,5,33));
customerSumarryPanel.setBackground(Color.WHITE);
customerSumarryPanelBorder.setTitleColor(new Color(110,5,33));
cardPanel1.setBackground(Color.WHITE);
cardPanel2.setBackground(Color.WHITE);
cardPanel3.setBackground(Color.WHITE);
cardPanel4.setBackground(Color.WHITE);
cardPanel5.setBackground(Color.WHITE);

searchButton.setBackground(new Color(116, 10, 33));
searchButton.setForeground(Color.WHITE);
customerTypeComboBox.setBackground(Color.WHITE);

addCustomerButton.setBackground(new Color(0, 120, 60));
addCustomerButton.setForeground(Color.WHITE);

resetButton.setBackground(new Color(1, 89, 212));
resetButton.setForeground(Color.WHITE);
photoLabel.setOpaque(true);
photoLabel.setBackground(Color.WHITE);




editButton.setBackground(new Color(180, 100, 0));



}


void setLayout()
{

cardPanel1.setLayout(null);
cardPanel2.setLayout(null);
cardPanel3.setLayout(null);
cardPanel4.setLayout(null);
cardPanel5.setLayout(null);

customerPanel.setLayout(null);
headerPanel.setLayout(null);
customerFilterPanel.setLayout(null);
customerListPanel.setLayout(null);
customerDetailPanel.setLayout(null);
customerSumarryPanel.setLayout(null);



}


void setIcon()
{


l1.setIcon(db.resizeImage(getClass().getResource("imgs/customers.png"),40,40));
l1.setHorizontalAlignment(SwingConstants.LEFT);


searchButton.setIcon(db.resizeImage(getClass().getResource("imgs/search.png"),30,30));
 searchButton.setHorizontalAlignment(SwingConstants.LEFT);
 searchButton.setIconTextGap(15);

addCustomerButton.setIcon(db.resizeImage(getClass().getResource("imgs/plus.png"),40,30));
 addCustomerButton.setHorizontalAlignment(SwingConstants.LEFT);
 addCustomerButton.setIconTextGap(5);

resetButton.setIcon(db.resizeImage(getClass().getResource("imgs/refresh.png"),30,30));
 resetButton.setHorizontalAlignment(SwingConstants.LEFT);
 resetButton.setIconTextGap(10);



editButton.setIcon(db.resizeImage(getClass().getResource("imgs/edit.png"),25,25));
editButton.setHorizontalAlignment(SwingConstants.CENTER);
editButton.setIconTextGap(25);


choosePhotoButton.setIcon(db.resizeImage(getClass().getResource("imgs/gallery.png"),30,30));
choosePhotoButton.setHorizontalAlignment(SwingConstants.CENTER);
choosePhotoButton.setIconTextGap(25);


photoLabel.setIcon(db.resizeImage(getClass().getResource("imgs/femaleLogo.png"),250,200));

cardPanelIconLabel1.setIcon(db.resizeImage(getClass().getResource("imgs/group.png"),70,70));
cardPanelIconLabel2.setIcon(db.resizeImage(getClass().getResource("imgs/customerCart.png"),60,60));
cardPanelIconLabel3.setIcon(db.resizeImage(getClass().getResource("imgs/dollar2.png"),60,60));
cardPanelIconLabel4.setIcon(db.resizeImage(getClass().getResource("imgs/avg.png"),100,70));
cardPanelIconLabel5.setIcon(db.resizeImage(getClass().getResource("imgs/star.png"),60,60));










}

void setBorder()
{

customerFilterPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Search / Filter Customers");
customerFilterPanel.setBorder(customerFilterPanelBorder);
customerListPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Customer List");
customerListPanel.setBorder(customerListPanelBorder);
customerDetailPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Customer Details");
customerDetailPanel.setBorder(customerDetailPanelBorder);
customerSumarryPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Customer Summary");
customerSumarryPanel.setBorder(customerSumarryPanelBorder);
cardPanel1.setBorder(BorderFactory.createLineBorder(new Color(199, 222, 247),1));
cardPanel2.setBorder(BorderFactory.createLineBorder(new Color(205, 232, 210),1));
cardPanel3.setBorder(BorderFactory.createLineBorder(new Color(248, 216, 181),1));
cardPanel4.setBorder(BorderFactory.createLineBorder(new Color(235, 210, 215),1));
cardPanel5.setBorder(BorderFactory.createLineBorder(new Color(220, 203, 247),1));
//addressArea.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
photoLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));
choosePhotoButton.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));


}



void addListener()
{



addCustomerButton.addActionListener(e->
{

JOptionPane.showMessageDialog(null,"This features is Comming soon","UPCOMING",JOptionPane.WARNING_MESSAGE);



});

choosePhotoButton.addActionListener( e->
{


setPhoto();



});



resetButton.addActionListener( e->
{


resetAllState();



});





editButton.addActionListener( e->
{


editCustomerInformation();





});







searchField.setText("Enter Name or mobile No");
searchField.setForeground(Color.GRAY);
searchField.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(searchField.getText().trim().equals("Enter Name or mobile No"))
{

searchField.setFont(new Font("ARIAL",Font.PLAIN,17));

searchField.setForeground(Color.BLACK);

searchField.setText("");


}





}


@Override
public void focusLost(FocusEvent e)
{

if(searchField.getText().isEmpty())
{

searchField.setFont(new Font("ARIAL",Font.BOLD,17));

searchField.setForeground(Color.GRAY);

searchField.setText("Enter Name or mobile No");



}




}






});



searchButton.addActionListener(e-> searchCustomerNameORMobileNumber());
searchField.addActionListener(e->  searchCustomerNameORMobileNumber());

customerListTable.addMouseListener( new MouseAdapter()
{

@Override
public void mouseClicked(MouseEvent e)
{

customerDetailFetch();




}





});





customerTypeComboBox.addActionListener(e -> {



searchByCustomerType();


if(!customerTypeComboBox.getSelectedItem().toString().equals("All"))
{
regularCustomer();

}

});



}


void searchCustomerNameORMobileNumber()
{

if(searchField.getText().trim().equals("Enter Name or mobile No"))
{

JOptionPane.showMessageDialog(null,"please Enter before Name OR mobile No");
return;

}




try
{

customerListModel.setRowCount(0);

PreparedStatement ps=con.prepareStatement("SELECT * FROM orders WHERE customer_name Like ? OR mobile_no Like ? ORDER BY order_date DESC");
ps.setString(1,"%"+searchField.getText()+"%");
ps.setString(2, "%"+searchField.getText()+"%");

ResultSet rs=ps.executeQuery();
int i=1;

while(rs.next())
{

customerListModel.addRow(new Object[]{

i++,
rs.getString(4),
rs.getString(5),
rs.getString(13),
rs.getString(6),
rs.getString(10),
rs.getString(3),
rs.getString(14)





}





);


customerListTable.setModel(customerListModel);



}





}


catch(Exception ex)
{

ex.printStackTrace();
JOptionPane.showMessageDialog(null,ex.getMessage());




}







}


//```

void searchByCustomerType()
{


if(customerTypeComboBox.getSelectedItem().toString().equals("All"))
{


try
{


customerListModel.setRowCount(0);
PreparedStatement ps=con.prepareStatement("SELECT * FROM orders ORDER BY order_date DESC");
ResultSet rs=ps.executeQuery();

int i=1;
while(rs.next())
{

customerListModel.addRow(new Object[]{

i++,
rs.getString(4),
rs.getString(5),
rs.getString(13),
rs.getString(6),
rs.getString(10),
rs.getString(3),
rs.getString(14),









});



customerListTable.setModel(customerListModel);


}




}



catch(Exception ex)
{

ex.printStackTrace();
JOptionPane.showMessageDialog(null,ex.getMessage());



}


}



else
{

String customerType=customerTypeComboBox.getSelectedItem().toString();
customerListModel.setRowCount(0);

try
{

int i=1;
PreparedStatement ps=con.prepareStatement("SELECT * FROM orders WHERE customer_type=? ORDER BY order_date DESC");
ps.setString(1,customerType);
ResultSet rs=ps.executeQuery();

while(rs.next())
{

customerListModel.addRow(new Object[]{

i++,
rs.getString(4),
rs.getString(5),
rs.getString(13),
rs.getString(6),
rs.getString(10),
rs.getString(3),
rs.getString(14),





});




}

customerListTable.setModel(customerListModel);


}



catch(Exception ex)
{





JOptionPane.showMessageDialog(null,ex.getMessage());



}









}




}


int totalCustomers=0;

void totalCustomers()
{



try
{

PreparedStatement ps=con.prepareStatement("SELECT COUNT(DISTINCT mobile_no) FROM orders");
ResultSet rs=ps.executeQuery();

if(rs.next())
{

totalCustomers=rs.getInt(1);



}



}



catch(SQLException ex)
{

ex.printStackTrace();
JOptionPane.showMessageDialog(null,ex.getMessage());


}



cardPanelTextLabel1.setText(
    "<html><center>" +
    "<font size='4' color='#1565C0'>Total Customers</font><br>" +
    "<font size='6' color='#1565C0'>" + totalCustomers + "</font>" +
    "</center></html>"
);


}








int totalOrders=0;


void totalOrders()
{



try
{

PreparedStatement ps=con.prepareStatement("SELECT COUNT(*) FROM orders");
ResultSet rs=ps.executeQuery();

if(rs.next())
{

totalOrders=rs.getInt(1);




}

cardPanelTextLabel2.setText(
    "<html><center>" + 
    "<font size='4' color='#1B5E20'>Total Orders</font><br>" +
    "<font size='6' color='#1B5E20'>" + totalOrders + "</font>" +
    "</center></html>"
);


}


catch(SQLException ex)
{

ex.printStackTrace();

JOptionPane.showMessageDialog(null,ex.getMessage());





}






}




String totalPurchase="0.00";

void totalPurchase()
{


try
{

PreparedStatement ps=con.prepareStatement("SELECT SUM(grand_Total) FROM orders  WHERE status=?");
ps.setString(1,"Completed");
ResultSet rs=ps.executeQuery();

if(rs.next())
{

double totalPurchase=rs.getDouble(1);
this.totalPurchase=String.format("%,.2f",totalPurchase);





}


cardPanelTextLabel3.setText(
    "<html><center>" + 
    "<font size='4' color='#E65100'>Total Purchase</font><br>" +
    "<font size='5' color='#E65100'>" + "₹ "+ totalPurchase + "</font>" +
    "</center></html>"
);



}



catch(SQLException ex)
{



JOptionPane.showMessageDialog(null,ex.getMessage());



}






}




String avgPurchase="0.00";

void avgPrchase()
{


try{

PreparedStatement ps=con.prepareStatement("SELECT AVG(grand_total) FROM orders WHERE status=?");
ps.setString(1,"Completed");
ResultSet rs=ps.executeQuery();

if(rs.next())
{

double avgPurchase=rs.getDouble(1);
this.avgPurchase=String.format("%,.2f",avgPurchase);



}

cardPanelTextLabel4.setText(
    "<html><center>" + 
    "<font size='4' color='#AD3C55'>purchase Average</font><br>" +
    "<font size='5' color='#AD3C55'>₹ " +avgPurchase + "</font>" +
    "</center></html>"
);



}


catch(Exception ex)
{


JOptionPane.showMessageDialog(null,ex.getMessage());




}




}





int regularCustomer=0;

void regularCustomer()
{
    String selectedType = String.valueOf(customerTypeComboBox.getSelectedItem());
    String typeToCount = selectedType.equals("All") ? "Regular Customer" : selectedType;

    try (PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(DISTINCT TRIM(mobile_no)) FROM orders " +
            "WHERE customer_type = ? AND mobile_no IS NOT NULL AND TRIM(mobile_no) <> ''")) {
        ps.setString(1, typeToCount);
        try (ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                regularCustomer = rs.getInt(1);
            }
        }

        String heading = selectedType.equals("All") ? "Regular Customers" : selectedType;
        cardPanelTextLabel5.setText(
            "<html><center>" +
            "<font size='4' color='#7B4FAA'>" + heading + "</font><br>" +
            "<font size='6' color='#7B4FAA'>" + regularCustomer + "</font>" +
            "</center></html>"
        );
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}

String lastOrder;
void customerDetailFetch()
{
    if (customerEditMode) {
        return;
    }

    lastOrderField.setEditable(false);
    totalPurchaseField.setEditable(false);
    totalItemsField.setEditable(false);
    customerNameField.setEditable(false);
    addressArea.setEditable(false);
    customerTypeFiled.setEditable(false);
    mobileNoField.setEditable(false);

    int row = customerListTable.getSelectedRow();
    if (row == -1) {
        return;
    }

    String customerName = String.valueOf(customerListModel.getValueAt(row, 1));
    String mobileNo = String.valueOf(customerListModel.getValueAt(row, 2));
    String address = String.valueOf(customerListModel.getValueAt(row, 3));
    String totalItems = String.valueOf(customerListModel.getValueAt(row, 4));
    String totalPurchase = String.valueOf(customerListModel.getValueAt(row, 5));
    lastOrder = String.valueOf(customerListModel.getValueAt(row, 6));

    customerNameField.setText(customerName);
    mobileNoField.setText(mobileNo.equals("null") ? "" : mobileNo);
    addressArea.setText(address.equals("null") ? "" : address);
    totalItemsField.setText(totalItems);
    totalPurchaseField.setText("₹ " + totalPurchase);
    lastOrderField.setText(lastOrder);

    try {
        Timestamp orderTime = Timestamp.valueOf(lastOrder);
        String sql = "SELECT customer_type, customer_photo FROM orders " +
                     "WHERE order_date = ? AND " +
                     "((mobile_no = ?) OR (mobile_no IS NULL AND ? = 'null')) " +
                     "ORDER BY order_no LIMIT 1";
        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setTimestamp(1, orderTime);
            ps.setString(2, mobileNo);
            ps.setString(3, mobileNo);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    customerTypeFiled.setText(rs.getString("customer_type"));
                    path = rs.getString("customer_photo");
                } else {
                    customerTypeFiled.setText("");
                    path = null;
                }
            }
        }

        if (path == null || path.trim().isEmpty()) {
            URL defaultUrl = getClass().getResource("imgs/femaleLogo.png");
            if (defaultUrl != null) {
                photoLabel.setIcon(db.resizeImage(defaultUrl, 250, 200));
            }
        } else {
            try {
                url = new File(path).toURI().toURL();
                photoLabel.setIcon(db.resizeImage(url, 250, 200));
            } catch (MalformedURLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Photo Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(null, "Selected order date is invalid.", "Date Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}




void editCustomerInformation()
{
    int row = customerListTable.getSelectedRow();
    if (row == -1) {
        JOptionPane.showMessageDialog(null, "Please select a customer row first.", "Select Customer", JOptionPane.WARNING_MESSAGE);
        return;
    }

    if (!customerEditMode) {
        if (lastOrderField.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Please select a customer row first.", "Select Customer", JOptionPane.WARNING_MESSAGE);
            return;
        }

        JPasswordField passwordInput = new JPasswordField();
        int passwordChoice = JOptionPane.showConfirmDialog(
            null, passwordInput, "Please Enter Password", JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE
        );
        if (passwordChoice != JOptionPane.OK_OPTION) {
            return;
        }

        String enteredPassword = new String(passwordInput.getPassword());
        if (password == null || !enteredPassword.equals(password)) {
            JOptionPane.showMessageDialog(null, "Password Invalid!", "Wrong Password", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Object mobileCell = customerListModel.getValueAt(row, 2);
        editOriginalMobileNo = (mobileCell == null || "null".equals(mobileCell.toString()))
                ? "" : mobileCell.toString().trim();
        editOriginalLastOrder = String.valueOf(customerListModel.getValueAt(row, 6));

        customerNameField.setEditable(true);
        addressArea.setEditable(true);
        mobileNoField.setEditable(true);
        customerListTable.setEnabled(false);
        choosePhotoButton.setEnabled(true);
        editButton.setText("Save Changes");
        customerEditMode = true;
        return;
    }

    String customerName = customerNameField.getText().trim();
    String mobileNo = mobileNoField.getText().trim();
    String address = addressArea.getText().trim();

    if (customerName.isEmpty()) {
        JOptionPane.showMessageDialog(null, "Customer name cannot be empty.", "Validation", JOptionPane.WARNING_MESSAGE);
        customerNameField.requestFocus();
        return;
    }
    if (!mobileNo.isEmpty() && !mobileNo.matches("\\d{10,15}")) {
        JOptionPane.showMessageDialog(null, "Mobile number must contain 10 to 15 digits.", "Validation", JOptionPane.WARNING_MESSAGE);
        mobileNoField.requestFocus();
        return;
    }

    int confirm = JOptionPane.showConfirmDialog(
        null, "Save changes to this customer?", "Confirm Changes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE
    );
    if (confirm != JOptionPane.YES_OPTION) {
        return;
    }

    if (path == null || path.trim().isEmpty()) {
        path = "C:\\Users\\user\\Desktop\\SSMS\\imgs\\femaleLogo.png";
    }

    try {
        int updated;
        if (!editOriginalMobileNo.isEmpty()) {
            String sql = "UPDATE orders SET customer_name = ?, mobile_no = ?, address = ?, customer_photo = ? WHERE mobile_no = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, customerName);
                ps.setString(2, mobileNo.isEmpty() ? null : mobileNo);
                ps.setString(3, address);
                ps.setString(4, path);
                ps.setString(5, editOriginalMobileNo);
                updated = ps.executeUpdate();
            }
        } else {
            String sql = "UPDATE orders SET customer_name = ?, mobile_no = ?, address = ?, customer_photo = ? " +
                         "WHERE order_date = ? AND (mobile_no IS NULL OR TRIM(mobile_no) = '')";
            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, customerName);
                ps.setString(2, mobileNo.isEmpty() ? null : mobileNo);
                ps.setString(3, address);
                ps.setString(4, path);
                ps.setTimestamp(5, Timestamp.valueOf(editOriginalLastOrder));
                updated = ps.executeUpdate();
            }
        }

        if (updated > 0) {
            JOptionPane.showMessageDialog(null, "Customer information updated successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
            customerEditMode = false;
            customerNameField.setEditable(false);
            mobileNoField.setEditable(false);
            addressArea.setEditable(false);
            customerListTable.setEnabled(true);
            editButton.setText("Edit");
            searchByCustomerType();
            totalCustomers();
            regularCustomer();
            resetAllState();
        } else {
            JOptionPane.showMessageDialog(null, "No customer record was updated.", "Not Updated", JOptionPane.WARNING_MESSAGE);
        }
    } catch (IllegalArgumentException ex) {
        JOptionPane.showMessageDialog(null, "Selected order date is invalid.", "Date Error", JOptionPane.ERROR_MESSAGE);
    } catch (SQLException ex) {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(null, ex.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}


String path;
URL url;
void setPhoto()
{

JFileChooser fc=new JFileChooser();

if(fc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION)
{

 path=fc.getSelectedFile().getAbsolutePath();

try
{
 url=new File(path).toURI().toURL();
}

catch(MalformedURLException e)
{

e.printStackTrace();
JOptionPane.showMessageDialog(null,e.getMessage());


}

photoLabel.setIcon(db.resizeImage(url,250,200));


}


else
{


JOptionPane.showMessageDialog(null,"File not choose");



}



}




void resetAllState()
{

customerNameField.setText("");
mobileNoField.setText("");
addressArea.setText("");
totalItemsField.setText("");
totalPurchaseField.setText("");
lastOrderField.setText("");
customerTypeFiled.setText("");

customerTypeComboBox.setSelectedItem("All");
searchField.setFont(new Font("ARIAL",Font.BOLD,17));
searchField.setForeground(Color.GRAY);
searchField.setText("Enter Name or mobile No");

photoLabel.setIcon(db.resizeImage(getClass().getResource("imgs/femaleLogo.png"),250,200));





}







String userName;
String password;

void ReadUserNamePassword()
{


try
{








PreparedStatement ps=con.prepareStatement("SELECT * FROM LoginUserNamePassword");
ResultSet rs=ps.executeQuery();

while(rs.next())
{



userName=rs.getString(1);
password=rs.getString(2);







}








}



catch(Exception ex)
{




JOptionPane.showMessageDialog(null,ex.getMessage(),"Database Error",JOptionPane.ERROR_MESSAGE);




}







}








}