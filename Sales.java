import javax.swing.*;
import java.awt.*;
import javax.swing.border.TitledBorder;
import java.awt.event.*;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.math.BigDecimal;
import java.math.RoundingMode;
public class Sales
{

JPanel salesMainPanel;
JFrame f;
DashBoard db;

JLabel salesLabel;

JPanel customerDetailPanel;
JPanel billDetailPanel;
JPanel searchItemPanel;
TitledBorder customerDetailPanelBorder;
TitledBorder billDetailPanelBorder;
TitledBorder searchItemPanelBorder;


JLabel  customerTypeLabel;
JLabel customerNameLabel;
JLabel  customerMobileNoLabel;
JLabel addressLabel;

JComboBox<String>  customerTypeComboBox;


JTextField   customerNameField;
JTextField   customerMobileNoField;
JTextArea    addressField;


JLabel billNoLabel;
JLabel  billDateLabel;
JLabel paymentModeLabel;


JTextField billNoField;
JTextField billDateField;
JComboBox<String> paymentModeComboBox;


JTextField searchItemField;
JButton searchButton;
JButton addButton;
JButton refreshButton;
JButton clearButton;

JScrollPane sp;

TitledBorder spBorder;

JPanel quickButtonsPanel;
TitledBorder quickButtonsPanelBorder;

JButton cartButton;
JButton saveButton;
JButton printButton;

JPanel billNotePanel;
TitledBorder billNotePanelBorder;

JTextArea billNoteArea;

JPanel recentBillPanel;
TitledBorder recentBillPanelBorder;

JPanel billSumarryPanel;
TitledBorder billSumarryPanelBorder;

JTable t1;

JLabel totalItemValueLabel;
JLabel subTotalValueLabel;
JLabel discountValueLabel;
JLabel taxValueLabel;
JLabel grandTotalValueLabel;
JTextField receivedAmountField;
JLabel returnBalanceValueLabel;



JLabel totalItemLabel;
JLabel subTotalLabel;
JLabel discountLabel;
JLabel taxLabel;
JLabel grandTotalLabel;
JLabel receivedAmountLabel;
JLabel returnBalanceLabel;
JTextField discountField;
JLabel  percentLabel;

JTextField gstField;

boolean addItem=false;
boolean updatingSummary = false;


boolean salesCreated=true;





JTable recentTable;
JScrollPane recentTableScrollPane;

DefaultTableModel  recentTableModel;







public JPanel createSales(DashBoard db)
{

this.db=db;
this.f=db.f;

if(!salesCreated)
{


salesMainPanel.setVisible(true);
db.hideDashboard();                                                     
db.ai.hideAddItem();
db.s.hideStock();
recentOrders();


return salesMainPanel;
}


salesCreated=false;


db.hideDashboard();                                                     
db.ai.hideAddItem();
db.s.hideStock();

createComponent();
recentOrders();

salesMainPanel.setLayout(null);
customerDetailPanel.setLayout(null);
billDetailPanel.setLayout(null);
searchItemPanel.setLayout(null);
quickButtonsPanel.setLayout(null);
billNotePanel.setLayout(null);
recentBillPanel.setLayout(null);
billSumarryPanel.setLayout(null);
setBounds();
addcomponent();
componentBorder();
setEditTable();
createPlaceHolder();
setIconOnComponent();
addListener();
setBillNo_Date();








setColors();
setFont();

return salesMainPanel;

}

void createComponent()
{

System.out.println("UI create Sales");

salesMainPanel=new JPanel();
salesLabel=new JLabel("   Sales / Billing");
salesLabel.setIcon(db.resizeImage(getClass().getResource("imgs/sales2.png"),30,30));
customerDetailPanel=new JPanel();
billDetailPanel=new JPanel();
searchItemPanel=new JPanel();
customerTypeLabel=new JLabel("Customer Type");
customerNameLabel=new JLabel("Customer Name");
customerMobileNoLabel=new JLabel("Mobile No");
addressLabel=new JLabel("Address");
customerTypeComboBox=new JComboBox<>(new String[]{
"Walk-in Customer",
    "Regular Customer",
    "New Customer",
    "Existing Customer"




});

customerNameField=new JTextField("Enter Customer Name");
customerMobileNoField=new JTextField("Enter Mobile No");
addressField=new JTextArea("Optional   Enter Address");

billNoLabel=new JLabel("Bill No");
billDateLabel=new JLabel("Bill Date");
paymentModeLabel=new JLabel("Payment Mode");

billNoField=new JTextField();

billDateField=new JTextField();
paymentModeComboBox=new JComboBox<>(new String[]{


"Cash", "UPI", "Card", "Net Banking", "Other"


});


searchItemField=new JTextField("Enter Item Name");

searchButton=new JButton();
addButton=new JButton();
refreshButton=new JButton("Refresh");
clearButton=new JButton("<html><font color=black>Clear</font></html>");


sp=new JScrollPane();
quickButtonsPanel=new JPanel();
cartButton=new JButton();
saveButton=new JButton();
printButton=new JButton();

billNotePanel=new JPanel();

billNoteArea=new JTextArea("Enter any note  Here");

recentBillPanel=new JPanel();
billSumarryPanel=new JPanel();

t1=new JTable();
sp=new JScrollPane(t1);

totalItemLabel=new JLabel("Total items");
subTotalLabel=new JLabel("Sub Total");
discountLabel=new JLabel("Discount");
gstField=new JTextField("0");

taxLabel=new JLabel("Tax (Gst 5%)");
taxLabel.setText("Tax(GST %"+gstField.getText()+")");
grandTotalLabel=new JLabel("Grand Total");
receivedAmountLabel=new JLabel("Received Amount");
returnBalanceLabel=new JLabel("Return Balance ");




totalItemValueLabel = new JLabel("0");
subTotalValueLabel = new JLabel("₹ 0.00");
discountValueLabel = new JLabel("₹ 0.00");
taxValueLabel = new JLabel("₹ 0.00");
grandTotalValueLabel = new JLabel("₹ 0.00");
receivedAmountField = new JTextField("plz Enter received Amount");
returnBalanceValueLabel = new JLabel("₹ 0.00");


discountField=new JTextField("0");
percentLabel=new JLabel("%");
recentTable=new JTable();
recentTableScrollPane=new JScrollPane(recentTable);
recentTableModel=new DefaultTableModel();
recentTableModel.addColumn("Order");
recentTableModel.addColumn("Date & Time");
recentTableModel.addColumn("Grand Total");
recentTableModel.addColumn("Payment ");
recentTableModel.addColumn("Status");


}

void setBounds()
{
salesMainPanel.setBounds(295-25,180,1610+40,772);
salesLabel.setBounds(0,0,salesMainPanel.getWidth(),40);
customerDetailPanel.setBounds(10,60,550,220);
billDetailPanel.setBounds(580,60,460,220);
searchItemPanel.setBounds(1060,60,550+35,220);
customerTypeComboBox.setBounds(180,30,300,40);

customerTypeLabel.setBounds(10,30,150,30);
customerNameLabel.setBounds(10,95,150,30);
customerMobileNoLabel.setBounds(10,133,150,30);
addressLabel.setBounds(10,175,150,30);
customerNameField.setBounds(180,95,300,30);
customerMobileNoField.setBounds(180,133,300,30);
addressField.setBounds(180,175,300,30);

billNoLabel.setBounds(10,30,150,30);
billDateLabel.setBounds(10,95,150,30);
paymentModeLabel.setBounds(10,153,150,30);

billNoField.setBounds(170,30,250,40);
billDateField.setBounds(170,95,250,30);
paymentModeComboBox.setBounds(170,153,250,30);

searchItemField.setBounds(10,30,300,50);

searchButton.setBounds(330,30,100,50);
addButton.setBounds(10,110,150,50);
refreshButton.setBounds(190,110,150,50);
clearButton.setBounds(370,110,150,50);
sp.setBounds(10,300,1600+35,160);
quickButtonsPanel.setBounds(10, 547,550-25,100);
cartButton.setBounds(10,40,150,35);
saveButton.setBounds(190-10,40,150,35);
printButton.setBounds(370-15,40,150,35);

billNotePanel.setBounds(10,664,550-10-15,107);
billNoteArea.setBounds(15,30,520-10-15,65);
recentBillPanel.setBounds(580-35,547,460+35+10+35,222);
billSumarryPanel.setBounds(1060+35,547-30-40-5,550,222+30+40+5);




totalItemLabel.setBounds(20, 25, 180, 28);

subTotalLabel.setBounds(20, 62, 180, 28);

discountLabel.setBounds(20, 99, 180, 28);

taxLabel.setBounds(20, 136, 100, 28);

grandTotalLabel.setBounds(20, 184-6, 200, 32);

receivedAmountLabel.setBounds(20, 230-6, 150, 30);

returnBalanceLabel.setBounds(20, 267-6, 220, 30);





totalItemValueLabel.setBounds(465, 28, 60, 25);

subTotalValueLabel.setBounds(365, 68, 160, 25);

discountValueLabel.setBounds(400, 108, 125, 25);

taxValueLabel.setBounds(400, 148, 125, 25);

grandTotalValueLabel.setBounds(350, 190, 175, 30);

receivedAmountField.setBounds(280-100, 238-20, 245, 32);

returnBalanceValueLabel.setBounds(390, 267-6, 135, 24);


totalItemValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
subTotalValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
discountValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
taxValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
grandTotalValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);
receivedAmountField.setHorizontalAlignment(SwingConstants.CENTER);
returnBalanceValueLabel.setHorizontalAlignment(SwingConstants.RIGHT);

discountField.setBounds(180,99,50,20);
percentLabel.setBounds(235,99,20,20);
gstField.setBounds(180,148,50,20);


recentTableScrollPane.setBounds(20-10,35,425+35+10+10+10+28,222-50);



}
void addcomponent()
{
f.add(salesMainPanel);
salesMainPanel.add(salesLabel);
salesMainPanel.add(customerDetailPanel);
salesMainPanel.add(billDetailPanel);
salesMainPanel.add(searchItemPanel);

customerDetailPanel.add(customerTypeLabel);
customerDetailPanel.add(customerTypeComboBox);
customerDetailPanel.add(customerMobileNoLabel);
customerDetailPanel.add(addressLabel);
customerDetailPanel.add(customerNameLabel);
customerDetailPanel.add(customerNameField);
customerDetailPanel.add(customerMobileNoField);
customerDetailPanel.add(addressField);

billDetailPanel.add(billNoLabel);
billDetailPanel.add(billDateLabel);
billDetailPanel.add(paymentModeLabel);

billDetailPanel.add(billNoField);
billDetailPanel.add(billDateField);
billDetailPanel.add(paymentModeComboBox);

searchItemPanel.add(searchItemField);

searchItemPanel.add(searchButton);
searchItemPanel.add(addButton);
searchItemPanel.add(refreshButton);
searchItemPanel.add(clearButton);

salesMainPanel.add(sp);

salesMainPanel.add(quickButtonsPanel);
quickButtonsPanel.add(cartButton);
quickButtonsPanel.add(saveButton);
quickButtonsPanel.add(printButton);

salesMainPanel.add(billNotePanel);
billNotePanel.add(billNoteArea);
salesMainPanel.add(recentBillPanel);
salesMainPanel.add(billSumarryPanel);

billSumarryPanel.add(totalItemLabel);
billSumarryPanel.add(subTotalLabel);
billSumarryPanel.add(discountLabel);
billSumarryPanel.add(taxLabel);
billSumarryPanel.add(grandTotalLabel);
billSumarryPanel.add(receivedAmountLabel);
billSumarryPanel.add(returnBalanceLabel);




billSumarryPanel.add(totalItemValueLabel);
billSumarryPanel.add(subTotalValueLabel);
billSumarryPanel.add(discountValueLabel);
billSumarryPanel.add(taxValueLabel);
billSumarryPanel.add(grandTotalValueLabel);
billSumarryPanel.add(receivedAmountField);
billSumarryPanel.add(returnBalanceValueLabel);

billSumarryPanel.add(discountField);
billSumarryPanel.add(percentLabel);
billSumarryPanel.add(gstField);

recentBillPanel.add(recentTableScrollPane);

recentTable.setModel(recentTableModel);
recentTable.getColumnModel().getColumn(1).setPreferredWidth(150);

}



void setColors()
{

salesMainPanel.setBackground(Color.WHITE);
salesLabel.setOpaque(true);
salesLabel.setBackground(new Color(110, 5, 33));
salesLabel.setForeground(Color.WHITE);
customerDetailPanel.setBackground(Color.WHITE);
billDetailPanel.setBackground(Color.WHITE);
searchItemPanel.setBackground(Color.WHITE);
customerDetailPanelBorder.setTitleColor(new Color(102, 1, 17));
billDetailPanelBorder.setTitleColor(new Color(102,1,17));
searchItemPanelBorder.setTitleColor(new Color(102,1,17));
customerTypeComboBox.setBackground(Color.WHITE);



addressField.setBackground(Color.WHITE);
paymentModeComboBox.setBackground(Color.WHITE);
searchButton.setBackground(new Color(110, 5, 33));


sp.setBackground(Color.WHITE);
sp.getViewport().setBackground(Color.WHITE);
t1.setBackground(Color.WHITE);

spBorder.setTitleColor(new Color(102, 1, 17));
quickButtonsPanelBorder.setTitleColor(new Color(102,1,17));

quickButtonsPanel.setBackground(Color.WHITE);

addButton.setBackground(new Color(14, 80, 23));      // Green
refreshButton.setBackground(new Color(3, 38, 144));  // Blue
clearButton.setBackground(new Color(140, 4, 4));     // Red


billNotePanelBorder.setTitleColor(new Color(102,1,17));
billNotePanel.setBackground(Color.WHITE);
recentBillPanelBorder.setTitleColor(new Color(102,1,17));
recentBillPanel.setBackground(Color.WHITE);
billSumarryPanel.setBackground(Color.WHITE);
billSumarryPanelBorder.setTitleColor(new Color(102,1,17));

Color normalColor = new Color(30, 30, 30);
Color grandTotalColor = new Color(130, 30, 30);
Color balanceColor = new Color(30, 140, 50);

totalItemLabel.setForeground(normalColor);
subTotalLabel.setForeground(normalColor);
discountLabel.setForeground(normalColor);
taxLabel.setForeground(normalColor);
receivedAmountLabel.setForeground(normalColor);

grandTotalLabel.setForeground(grandTotalColor);
returnBalanceLabel.setForeground(balanceColor);
recentTableScrollPane.setBackground(Color.WHITE);
recentTableScrollPane.getViewport().setBackground(Color.WHITE);
recentTable.setBackground(Color.WHITE);





}


void setFont()
{

salesLabel.setFont(new Font("ARIAL",Font.BOLD,20));
customerDetailPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,18));
billDetailPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));
searchItemPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));
customerTypeLabel.setFont(new Font("Arial",Font.BOLD,17));
customerMobileNoLabel.setFont(new Font("Arial",Font.BOLD,17));
addressLabel.setFont(new Font("ARIAL",Font.BOLD,17));
customerNameLabel.setFont(new  Font("Arial",Font.BOLD,17));
customerNameField.setFont(new  Font("Arial",Font.PLAIN,17));
customerMobileNoField.setFont(new  Font("Arial",Font.PLAIN,17));
addressField.setFont(new  Font("Arial",Font.PLAIN,17));
billNoLabel.setFont(new Font("Arial",Font.BOLD,17));
billDateLabel.setFont(new Font("Arial",Font.BOLD,17));
paymentModeLabel.setFont(new Font("Arial",Font.BOLD,17));
billNoField.setFont(new Font("ARIAL",Font.BOLD,17));
billDateField.setFont(new Font("ARIAL",Font.BOLD,17));
searchItemField.setFont(new Font("ARIAL",Font.PLAIN,17));
spBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));
quickButtonsPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));

billNotePanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));
billNoteArea.setFont(new Font("Arial",Font.PLAIN,17));
recentBillPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));
billSumarryPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,18));
percentLabel.setFont(new Font("ARIAL",Font.BOLD,17));







Font normalFont = new Font("Arial", Font.PLAIN, 14);
Font boldFont = new Font("Arial", Font.BOLD, 19);

totalItemLabel.setFont(normalFont);
subTotalLabel.setFont(normalFont);
discountLabel.setFont(normalFont);
taxLabel.setFont(normalFont);
receivedAmountLabel.setFont(normalFont);

grandTotalLabel.setFont(boldFont);
returnBalanceLabel.setFont(boldFont);




Font valueFont = new Font("Arial", Font.BOLD, 16);
Font totalValueFont = new Font("Arial", Font.BOLD, 21);

totalItemValueLabel.setFont(valueFont);
subTotalValueLabel.setFont(valueFont);
discountValueLabel.setFont(valueFont);
taxValueLabel.setFont(valueFont);

grandTotalValueLabel.setFont(totalValueFont);

receivedAmountField.setFont(valueFont);

returnBalanceValueLabel.setFont(totalValueFont);







}




void componentBorder()
{

salesLabel.setBorder(BorderFactory.createEmptyBorder(0,30,0,0));


 customerDetailPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225, 213, 210),2),"Customer Details");

customerDetailPanel.setBorder(customerDetailPanelBorder);

billDetailPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),2),"Bill Details");

billDetailPanel.setBorder(billDetailPanelBorder);


searchItemPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,210,210),2),"Search Item");
searchItemPanel.setBorder(searchItemPanelBorder);



addressField.setOpaque(true);

addressField.setBorder(BorderFactory.createLineBorder(Color.BLACK,1));

spBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,210,210),2),"Item List");
sp.setBorder(spBorder);


quickButtonsPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,210,210),2),"Quick Buttons");

quickButtonsPanel.setBorder(quickButtonsPanelBorder);

billNotePanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,210,210),2),"Bill Note");

billNotePanel.setBorder(billNotePanelBorder);

billNoteArea.setBorder(BorderFactory.createLineBorder(new Color(235, 235, 235), 2));


recentBillPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,210,210),2),"Recent Bills");

recentBillPanel.setBorder(recentBillPanelBorder);


billSumarryPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,210,210),2),"Bill Sumarry");

billSumarryPanel.setBorder(billSumarryPanelBorder);


//receivedAmountField.setBorder(BorderFactory.createLineBorder(Color.BLACK,2));


}


void setEditTable()
{



billNoField.setEditable(false);
billDateField.setEditable(false);




}



void createPlaceHolder()
{
   customerMobileNoField.setForeground(Color.GRAY);
    



customerMobileNoField.addFocusListener(new FocusAdapter()
{



public void focusGained(FocusEvent e)
{
 if(customerMobileNoField.getText().trim().equals("Enter Mobile No"))
{

customerMobileNoField.setText("");
  customerMobileNoField.setForeground(Color.BLACK);


}

 

}


public void  focusLost(FocusEvent e)
{

 if(customerMobileNoField.getText().trim().isEmpty())
  {
  
   customerMobileNoField.setForeground(Color.GRAY);
   customerMobileNoField.setText("Enter Mobile No");


   }

else
{

  customerMobileNoField.setForeground(Color.BLACK);
}


}



});





addressField.setForeground(Color.GRAY);

addressField.addFocusListener(new FocusAdapter(){


public void focusGained(FocusEvent e)
{

if(addressField.getText().trim().equals("Optional   Enter Address"))
{

addressField.setText("");

addressField.setForeground(Color.BLACK);

}




}

public void focusLost(FocusEvent e)
{

if(addressField.getText().trim().isEmpty())
{

addressField.setForeground(Color.GRAY);

addressField.setText("Optional   Enter Address");




}


else
{

addressField.setForeground(Color.BLACK);


}





}






});




customerNameField.setForeground(Color.GRAY);

customerNameField.addFocusListener(new FocusAdapter()
{


@Override
public void focusGained(FocusEvent e)
{



if(customerNameField.getText().trim().equals("Enter Customer Name"))
{
customerNameField.setText("");
customerNameField.setForeground(Color.BLACK);


}



}




@Override
public  void focusLost(FocusEvent e)
{


if(customerNameField.getText().trim().isEmpty())
{

customerNameField.setForeground(Color.GRAY);
customerNameField.setText("Enter Customer Name");



}

else
{




}



}







});




searchItemField.setForeground(Color.GRAY);

searchItemField.addFocusListener(new FocusAdapter()
{


public void focusGained(FocusEvent e)
{

if(searchItemField.getText().trim().equals("Enter Item Name"))
{


searchItemField.setText("");
searchItemField.setForeground(Color.BLACK);



}


}

public void focusLost(FocusEvent e)
{

if(searchItemField.getText().trim().isEmpty())
{

searchItemField.setForeground(Color.GRAY);
searchItemField.setText("Enter Item Name");

}



}






});



billNoteArea.setForeground(Color.GRAY);

billNoteArea.addFocusListener(new FocusAdapter()
{


@Override
public void focusGained(FocusEvent e)
{



if(billNoteArea.getText().trim().equals("Enter any note  Here"))
{

billNoteArea.setText("");
billNoteArea.setForeground(Color.BLACK);




}



}





@Override
public void focusLost(FocusEvent e)
{

if(billNoteArea.getText().trim().isEmpty())
{

billNoteArea.setForeground(Color.GRAY);

billNoteArea.setText("Enter any note  Here");



}




}








});

receivedAmountField.setForeground(Color.GRAY);

receivedAmountField.addFocusListener(new FocusAdapter()
{


@Override
public void  focusGained(FocusEvent e)
{




if(receivedAmountField.getText().equals("plz Enter received Amount"))
{

receivedAmountField.setForeground(Color.BLACK);

receivedAmountField.setText("");


}




}


@Override
public void  focusLost(FocusEvent e)
{



if(receivedAmountField.getText().isEmpty())
{

receivedAmountField.setForeground(Color.GRAY);

receivedAmountField.setText("plz Enter received Amount");

}


}






});






receivedAmountField.addKeyListener(new KeyAdapter()
{

@Override
public void  keyReleased(KeyEvent e)
{

  
try
{
    receivedAmount = Double.parseDouble(receivedAmountField.getText());
    System.out.println(receivedAmount);
}
catch(NumberFormatException ex)
{
    receivedAmount = 0.0;
}


String text = receivedAmountField.getText().trim();

        if(text.isEmpty()|| text.equals("plz Enter received Amount"))
        {
            returnBalanceLabel.setText("Return Amount");
            returnBalanceLabel.setForeground(Color.GRAY);
            returnBalanceValueLabel.setText("₹ "+"0");
            return;
        }


if(!addItem)
   {

   return;

   }
try
{


double receivedAmount=Double.parseDouble(receivedAmountField.getText());



if(receivedAmount>grandTotal)
{

 returnBalance = receivedAmount - grandTotal;
returnBalanceLabel.setForeground(Color.GREEN);

returnBalanceLabel.setText("Return Amount");
returnBalanceValueLabel.setText("₹ "+String.valueOf(returnBalance));
}

else if(receivedAmount<grandTotal)
{
returnBalance = grandTotal - receivedAmount;
returnBalanceLabel.setForeground(Color.RED);
returnBalanceLabel.setText("Due Amount");
returnBalanceValueLabel.setText("₹ "+String.valueOf(returnBalance));


}

else
{

returnBalanceLabel.setForeground(Color.GRAY);

returnBalanceLabel.setText("Return  Amount");

returnBalanceValueLabel.setText("₹ "+"0");


}

}

catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());

}


}



});






discountField.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{



if(discountField.getText().equals("0"))
{

discountField.setText("");


}


}

@Override
public void focusLost(FocusEvent e)
{

if(discountField.getText().isEmpty())
{

discountField.setText("0");



}



}






});















gstField.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{



if(gstField.getText().equals("0"))
{

gstField.setText("");


}


}

@Override
public void focusLost(FocusEvent e)
{

if(gstField.getText().isEmpty())
{

gstField.setText("0");



}



}






});




discountField.addKeyListener(new KeyAdapter()
{
    @Override
    public void keyReleased(KeyEvent e)
    {

     
    if(!discountField.getText().matches("\\d+"))
     {


      discountField.setText("");

     }

 


        if(!addItem)
        {
            return;
        }


        if(discountField.getText().isEmpty())
        {
            discount = 0;
            discountPercent = 0;
           

            discountValueLabel.setText("₹ "+"0");
              billSumarryLogic();

            return;
        }

        if(!discountField.getText().matches("\\d+"))
        {
            return;
        }

        discount = Double.parseDouble(discountField.getText());

        discountPercent = subTotal * discount / 100;

        discountValueLabel.setText(String.valueOf("₹ "+discountPercent));

        billSumarryLogic();
    }
});




















gstField.addKeyListener(new KeyAdapter()
{
    @Override
    public void keyReleased(KeyEvent e)
    {



if(!gstField.getText().matches("\\d+"))
     {


      gstField.setText("");

     }


        if(!addItem)
        {
            return;
        }

        if(gstField.getText().isEmpty())
        {
            gst = 0;
            taxAmount = 0;
            

            taxLabel.setText("Tax(GST %0)");
            taxValueLabel.setText("₹ "+"0");
billSumarryLogic();

            return;
        }

        if(!gstField.getText().matches("\\d+"))
        {
            return;
        }

        taxLabel.setText("Tax(GST %"+gstField.getText()+")");

        gst = Double.parseDouble(gstField.getText());

        taxAmount = (subTotal - discountPercent) * gst / 100;

        taxValueLabel.setText(String.valueOf("₹ "+taxAmount));

        billSumarryLogic();
    }
});





}

void setIconOnComponent()
{

searchButton.setIcon(db.resizeImage(getClass().getResource("imgs/search.png"),40,40));
addButton.setIcon(db.resizeImage(getClass().getResource("imgs/addItemS.png"),150,65));
refreshButton.setIcon(db.resizeImage(getClass().getResource("imgs/refreshS.png"),165,65));
clearButton.setIcon(db.resizeImage(getClass().getResource("imgs/removeS.png"),159,80));



//=============================position================



cartButton.setIcon(db.resizeImage(getClass().getResource("imgs/cart.png"),153,82));
saveButton.setIcon(db.resizeImage(getClass().getResource("imgs/save.png"),153,70));
printButton.setIcon(db.resizeImage(getClass().getResource("imgs/print.png"),153,55));








}




void addListener()
{


searchItemField.addActionListener( e-> {


try
{
searchItem();

}


catch(Exception ex )
{




JOptionPane.showMessageDialog(null,ex.getMessage());



}




});


searchButton.addActionListener(e->
{

try
{
searchItem();
}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());


}


});






addButton.addActionListener(e->
{

try
{
addItem();
}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());


}


});




clearButton.addActionListener(e->
{


clear();



});


cartButton.addActionListener(e->
{


clearCart();



});



db.buttons[0].addActionListener(e->
{

 if(salesMainPanel != null)
    {
        salesMainPanel.setVisible(false);
    }
  db.showDashboard();




});



model.addTableModelListener(e->
{

billSumarryLogic();



});


//zz

saveButton.addActionListener(e -> ordersSave());



}



JDBC jdbc=new JDBC();

Connection con=jdbc.con;


String name;
String category;
String unit;
int price;


void searchItem() throws Exception
{



if(searchItemField.getText().trim().equals("Enter Item Name"))
{

JOptionPane.showMessageDialog(null,"plz Enter sweets Name");
return;

}


String sweet_name=searchItemField.getText();

PreparedStatement ps=con.prepareStatement("SELECT * FROM sweets WHERE sweets_name=?");
ps.setString(1,sweet_name);
ResultSet rs=ps.executeQuery();

if(rs.next())
{

name=rs.getString(1);
category=rs.getString(2);
unit=rs.getString(4);
price=rs.getInt(5);



}

else
{


JOptionPane.showMessageDialog(null,"This item does not exists");
return;

}

JOptionPane.showMessageDialog(null,"item found succesfully plz  click GREEN -->  addItem Button ");

ps.close();
rs.close();



}
//aa
DefaultTableModel model=new DefaultTableModel(new Object[]{
        "S.No.",
        "Item Name",
        "Category",
        "Qty",
        "Unit",
        "Price (₹)",
        "Discount (%)",
        "Total (₹)",
        "Action"
    }, 
    0);



int totalItems=0;
double subTotal = 0.0;
double discountPercent = 0.0;
double discountAmount = 0.0;
double taxAmount = 0.0;
double grandTotal = 0.0;
double receivedAmount = 0.0;
double returnBalance = 0.0;





void addItem()
{
addItem=true;

for(int i=0;i<model.getRowCount();i++)
{


if(model.getValueAt(i,1).equals(name))
{


JOptionPane.showMessageDialog(null,"This item alredy add in table");
return;
}




}




if(searchItemField.getText().trim().equals("Enter Item Name"))
{

JOptionPane.showMessageDialog(null,"please Enter sweets Name");
return;

}






model.addRow(new Object[]{

model.getRowCount()+1,

name,
category,
1,
unit,
price,
0,
price,
"remove"

});

t1.setModel(model);
t1.getColumn("Action").setCellRenderer(new ButtonRenderer());
t1.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

billSumarryLogic();





//zz

}
double discount;
double gst;
void billSumarryLogic()
{
    if(updatingSummary)
    {
        return;
    }

    updatingSummary = true;

    try
    {
        totalItems = model.getRowCount();
        totalItemValueLabel.setText(String.valueOf(totalItems));

        BigDecimal calculatedSubtotal = BigDecimal.ZERO;

        for(int i = 0; i < model.getRowCount(); i++)
        {
            BigDecimal itemPrice = new BigDecimal(
                model.getValueAt(i, 5).toString().trim()
            );

            BigDecimal quantity = readQuantity(i);

            BigDecimal lineTotal = itemPrice.multiply(quantity)
                .setScale(2, RoundingMode.HALF_UP);

            calculatedSubtotal = calculatedSubtotal.add(lineTotal);

            // Keep the row Total updated when quantity changes (e.g. 0.5 kg).
            Object oldTotal = model.getValueAt(i, 7);
            boolean sameTotal = false;

            if(oldTotal != null)
            {
                try
                {
                    sameTotal = new BigDecimal(oldTotal.toString())
                        .compareTo(lineTotal) == 0;
                }
                catch(NumberFormatException ignored)
                {
                    sameTotal = false;
                }
            }

            if(!sameTotal)
            {
                model.setValueAt(lineTotal.toPlainString(), i, 7);
            }
        }

        subTotal = calculatedSubtotal
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();

        String discountText = discountField.getText().trim();
        discount = discountText.isEmpty()
            ? 0.0 : Double.parseDouble(discountText);

        discountPercent = BigDecimal.valueOf(subTotal)
            .multiply(BigDecimal.valueOf(discount))
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            .doubleValue();

        discountValueLabel.setText(String.format("₹ %.2f", discountPercent));
        subTotalValueLabel.setText(String.format("₹ %.2f", subTotal));

        String gstText = gstField.getText().trim();
        gst = gstText.isEmpty() ? 0.0 : Double.parseDouble(gstText);

        taxAmount = BigDecimal.valueOf(subTotal - discountPercent)
            .multiply(BigDecimal.valueOf(gst))
            .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP)
            .doubleValue();

        taxValueLabel.setText(String.format("₹ %.2f", taxAmount));

        grandTotal = BigDecimal.valueOf(subTotal - discountPercent + taxAmount)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();

        grandTotalValueLabel.setText(String.format("₹ %.2f", grandTotal));

        String receivedText = receivedAmountField.getText().trim();

        if(receivedText.isEmpty()
                || receivedText.equals("plz Enter received Amount"))
        {
            returnBalanceLabel.setText("Return Amount");
            returnBalanceLabel.setForeground(Color.GRAY);
            returnBalanceValueLabel.setText("₹ 0.00");
            return;
        }

        if(!receivedText.matches("\\d+(\\.\\d{1,2})?"))
        {
            return;
        }

        double received = Double.parseDouble(receivedText);

        if(received > grandTotal)
        {
            returnBalance = received - grandTotal;
            returnBalanceLabel.setForeground(Color.GREEN);
            returnBalanceLabel.setText("Return Amount");
            returnBalanceValueLabel.setText(String.format("₹ %.2f", returnBalance));
        }
        else if(received < grandTotal)
        {
            returnBalance = grandTotal - received;
            returnBalanceLabel.setForeground(Color.RED);
            returnBalanceLabel.setText("Due Amount");
            returnBalanceValueLabel.setText(String.format("₹ %.2f", returnBalance));
        }
        else
        {
            returnBalanceLabel.setForeground(Color.GRAY);
            returnBalanceLabel.setText("Return Amount");
            returnBalanceValueLabel.setText("₹ 0.00");
        }
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(
            null,
            "Please enter a valid quantity (e.g. 0.5 for half kg) and numeric prices.\n"
            + ex.getMessage(),
            "Invalid Billing Value",
            JOptionPane.WARNING_MESSAGE
        );
    }
    finally
    {
        updatingSummary = false;
    }
}

// Accepts decimal quantities such as 0.5 kg or 1.25 kg.
BigDecimal readQuantity(int row) throws SQLException
{
    String quantityText = model.getValueAt(row, 3).toString().trim();

    try
    {
        BigDecimal quantity = new BigDecimal(quantityText).stripTrailingZeros();

        if(quantity.compareTo(BigDecimal.ZERO) <= 0)
        {
            throw new SQLException(
                "Quantity must be greater than zero for "
                + model.getValueAt(row, 1).toString()
            );
        }

        if(quantity.scale() > 3)
        {
            throw new SQLException(
                "Use up to 3 decimal places for quantity, e.g. 0.5 or 0.250."
            );
        }

        return quantity;
    }
    catch(NumberFormatException ex)
    {
        throw new SQLException(
            "Invalid quantity: " + quantityText
            + ". Enter 0.5 for half kg, not 1/2.",
            ex
        );
    }
}

class ButtonRenderer extends JButton implements TableCellRenderer
{

public Component getTableCellRendererComponent(JTable table ,Object value ,boolean isSelected,boolean hasFocus,int row ,int column )
{

setText("Remove");
return this;



}




}


class ButtonEditor extends DefaultCellEditor
{

JButton button;

public ButtonEditor(JCheckBox checkBox)
{

super(checkBox);

button=new JButton("Delete");

button.addActionListener(e ->
{
    int row = t1.getSelectedRow();

    fireEditingStopped();

    if(row != -1)
    {
        model.removeRow(row);
     


for(int i=0; i<model.getRowCount();i++)
{

model.setValueAt(i+1,i,0);




}


    }

billSumarryLogic();

});
}



 public Component getTableCellEditorComponent(
        JTable table,
        Object value,
        boolean isSelected,
        int row,
        int column)
    {
        return button;
    }




}


void clear()
{


discountField.setText("0");
gstField.setText("0");
addItem = false;

searchItemField.setText("Enter Item Name");
searchItemField.setForeground(Color.GRAY);

customerNameField.setText("Enter Customer Name");
customerNameField.setForeground(Color.GRAY);

customerMobileNoField.setText("Enter Mobile No");
customerMobileNoField.setForeground(Color.GRAY);

addressField.setText("Optional   Enter Address");
addressField.setForeground(Color.GRAY);

paymentModeComboBox.setSelectedIndex(0);
customerTypeComboBox.setSelectedIndex(0);
receivedAmountField.setForeground(Color.GRAY);
receivedAmountField.setText("plz Enter received Amount");
billNoteArea.setText("");

model.setRowCount(0);

totalItems = 0;
subTotal = 0.0;
discountPercent = 0.0;
discountAmount = 0.0;
taxAmount = 0.0;
grandTotal = 0.0;
receivedAmount = 0.0;
returnBalance = 0.0;

 

}


void clearCart()
{
gstField.setText("0");
 discountField.setText("0");

 totalItemValueLabel.setText("0");
subTotalValueLabel.setText("₹ 0.00");
discountValueLabel.setText("₹ 0.00");
taxValueLabel.setText("₹ 0.00");
grandTotalValueLabel.setText("₹ 0.00");
receivedAmountField.setText("plz Enter received Amount");
receivedAmountField.setForeground(Color.GRAY);

returnBalanceValueLabel.setText("₹ 0.00");
returnBalanceLabel.setForeground(new Color(30, 140, 50));
model.setRowCount(0);






}

int billNo;
String billNumber;
String currentDate;

void setBillNo_Date()
{
    billDateField.setText(LocalDate.now().toString());

    Date date = new Date();

    SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yy");
    this.currentDate = sdf1.format(date);

    try
    {
        PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM orders " +
            "WHERE DATE(order_date) = CURDATE()"
        );

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            billNo = rs.getInt(1) + 1;
        }
        else
        {
            billNo = 1;
        }

        billNumber = String.format("INVC-%04d", billNo);

        billNoField.setText(billNumber);

    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}






int orderNumber;
String orderNo;
String status;
void ordersSave()
{



try
{

PreparedStatement ps = con.prepareStatement(
    "SELECT COALESCE(MAX(CAST(SUBSTRING(order_no,5) AS UNSIGNED)),0) + 1 FROM orders"
);

ResultSet rs = ps.executeQuery();

if(rs.next())
{
    orderNumber = rs.getInt(1);
    orderNo = String.format("ORD-%04d", orderNumber);
}

rs.close();
ps.close();

}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());


}



//=============================VALIDATION===============================
if(customerNameField.getText().equals("Enter Customer Name"))
{
JOptionPane.showMessageDialog(null,"Plz Enter Customer Name ");
return;
}


if(customerMobileNoField.getText().equals("Enter Mobile No"))
{

JOptionPane.showMessageDialog(null,"Plz Enter Mobile No");
return;
}


if(model.getRowCount()==0)
{

JOptionPane.showMessageDialog(null,"Plz add sweets in table before order save");
return;
}








Timestamp timeStamp=new Timestamp(System.currentTimeMillis());

String customerName=customerNameField.getText().trim();
String customerMobileNo=customerMobileNoField.getText().trim();
String  paymentMode=(String)paymentModeComboBox.getSelectedItem();

String address=addressField.getText();

if(address.trim().equals("Optional   Enter Address"))
{

address="";


}

String billNote=billNoteArea.getText();

if(billNote.trim().equals("Enter any note  Here"))
{

billNote="";



}


String customerType=customerTypeComboBox.getSelectedItem().toString();


try
{
    // 1. JTable ki editing complete karo
    if(t1.isEditing())
    {
        if(!t1.getCellEditor().stopCellEditing())
        {
            JOptionPane.showMessageDialog(
                null,
                "Please enter a valid quantity."
            );
            return;
        }
    }

    // 2. Received Amount
    String receivedText = receivedAmountField.getText().trim();

    if(receivedText.isEmpty()
            || receivedText.equals("plz Enter received Amount"))
    {
        receivedAmount = 0.0;
    }
    else
    {
        receivedAmount = Double.parseDouble(receivedText);
    }

    if(receivedAmount < 0)
    {
        JOptionPane.showMessageDialog(
            null,
            "Received amount cannot be negative."
        );
        return;
    }

    // 3. Credit Confirmation
    if(receivedAmount < grandTotal)
    {
        int confirm = JOptionPane.showConfirmDialog(
            null,
            "Received amount is less than Grand Total.\n"
            + "Do you want to save this order on credit?",
            "Confirm Credit",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE
        );

        if(confirm != JOptionPane.YES_OPTION)
        {
            return;
        }
    }

    // 4. Save Order and Update Stock
    boolean oldAutoCommit = con.getAutoCommit();

    try
    {
        con.setAutoCommit(false);

        // Stock minus karo
        try(PreparedStatement stockPS = con.prepareStatement(
            "UPDATE sweets "
            + "SET stock = stock - ? "
            + "WHERE sweets_name = ? AND stock >= ?"))
        {
            for(int row = 0; row < model.getRowCount(); row++)
            {
                String sweetName =
                    model.getValueAt(row, 1).toString();

                BigDecimal quantity = readQuantity(row);

                stockPS.setBigDecimal(1, quantity);
                stockPS.setString(2, sweetName);
                stockPS.setBigDecimal(3, quantity);

                int updated = stockPS.executeUpdate();

                if(updated != 1)
                {
                    throw new SQLException(
                        "Stock is insufficient for "
                        + sweetName
                        + ". Please check available stock."
                    );
                }
            }
        }

        // Discount amount
        discountAmount = discountPercent;

        // Order save karo
        try(PreparedStatement ps = con.prepareStatement(
            "INSERT INTO orders("
            + "order_no, billNo, order_date, customer_name, "
            + "mobile_no, total_items, subtotal, discount, tax, "
            + "grand_total, payment_Mode, status, address, "
            + "bill_note, customer_type"
            + ") VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)"))
        {
            ps.setString(1, orderNo);
            ps.setString(2, billNumber);
            ps.setTimestamp(3, timeStamp);
            ps.setString(4, customerName);
            ps.setString(5, customerMobileNo);
            ps.setInt(6, totalItems);
            ps.setDouble(7, subTotal);
            ps.setDouble(8, discountAmount);
            ps.setDouble(9, taxAmount);
            ps.setDouble(10, grandTotal);
            ps.setString(11, paymentMode);

            if(receivedAmount >= grandTotal
                    && receivedAmount > 0)
            {
                status = "Completed";
            }
            else
            {
                status = "Pending";
            }

            ps.setString(12, status);
            ps.setString(13, address);
            ps.setString(14, billNote);
            ps.setString(15, customerType);

            ps.executeUpdate();
        }

        // Dono operations successful
        con.commit();
    }
    catch(Exception ex)
    {
        con.rollback();
        throw ex;
    }
    finally
    {
        con.setAutoCommit(oldAutoCommit);
    }

    // 5. Success
    JOptionPane.showMessageDialog(
        null,
        "Order saved successfully!\nStock updated successfully."
    );

    clear();

    recentTableModel.setRowCount(0);
    recentOrders();
}
catch(Exception ex)
{
    JOptionPane.showMessageDialog(
        null,
        ex.getMessage(),
        "Order Save Error",
        JOptionPane.ERROR_MESSAGE
    );
}



setBillNo_Date();










}




void recentOrders()
{

try
{

PreparedStatement ps=con.prepareStatement("SELECT * FROM orders  ORDER BY order_date DESC  LIMIT 9");
ResultSet rs=ps.executeQuery();
recentTableModel.setRowCount(0);

while(rs.next())
{

recentTableModel.addRow(new Object[]
{

rs.getString(1),
rs.getString(3),
rs.getString(10)+"  ₹",
rs.getString(11),
rs.getString(12)






});

recentTable.setModel(recentTableModel);

}


}

catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());


}







}











}