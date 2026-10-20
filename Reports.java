import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
import com.toedter.calendar.JDateChooser;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.text.SimpleDateFormat;
//============================================================chart lib

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;


import org.jfree.chart.labels.StandardPieSectionLabelGenerator;
import org.jfree.chart.plot.RingPlot;
import java.text.DecimalFormat;


class Reports
{


JFrame f;
DashBoard db;
JPanel reportPanel;
JLabel reportLabel;

JPanel cardPanel1;
JPanel cardPanel2;
JPanel cardPanel3;
JPanel cardPanel4;
JPanel cardPanel5;
JPanel reportFilterPanel;
TitledBorder reportFilterPanelBorder;
JPanel salesReportPanel;
TitledBorder salesReportPanelBorder;
JPanel paymentModePanel;
TitledBorder paymentModePanelBorder;

JPanel reportDetailPanel;
TitledBorder reportDetailPanelBorder;
JPanel summaryPanel;
TitledBorder summaryPanelBorder;
JButton viewButton1;
JButton viewButton2;
JButton viewButton3;
JButton viewButton4;
JButton viewButton5;
JComboBox<String> reportTypeComboBox; 
JLabel reportTypeLabel;
JLabel customerTypeLabel;
JDateChooser fromDate;
JLabel fromDateLabel;
JDateChooser toDate;
JLabel toDateLabel;
JComboBox<String> customerTypeComboBox;
JComboBox<String> paymentComboBox;
JLabel paymentLabel;
JComboBox<String> orderStatusComboBox;
JLabel orderStatusLabel;
JButton searchButton;
JButton resetButton;
JLabel cardPanelIconLabel1;
JLabel cardPanelIconLabel2;
JLabel cardPanelIconLabel3;
JLabel cardPanelIconLabel4;
JLabel cardPanelIconLabel5;

JLabel cardPanelTextLabel1;
JLabel cardPanelTextLabel2;
JLabel cardPanelTextLabel3;
JLabel cardPanelTextLabel4;
JLabel cardPanelTextLabel5;

Connection con;

JTable reportTable;
DefaultTableModel reportTableModel;
JScrollPane reportScrollPane;

// Prevent automatic refresh while dates are being set programmatically.
boolean suppressAutoRefresh = false;


ChartPanel salesChartPanel;
ChartPanel customerTypeChartPanel;
ChartPanel paymentChartPanel;
JLabel totalSalesLabel;
JLabel cashPaymentLabel;
JLabel upiPaymentLabel;
JLabel cardPaymentLabel;
JLabel otherPaymentLabel;


JPanel createReports(JFrame f, DashBoard db)
{
    this.f = f;
    this.db = db;
    this.con = db.con;

    // ================= ALREADY CREATED =================

    if(reportPanel != null)
    {
        reportPanel.setVisible(true);

        // Har baar Reports page khulne par aaj ki date select karo
        java.util.Date today = new java.util.Date();
        suppressAutoRefresh = true;
        fromDate.setDate(today);
        toDate.setDate(today);
        suppressAutoRefresh = false;

        // Summary aur aaj ke orders refresh karo
        totalCustomers();
        totalPurchase();
        avgPrchase();
        regularCustomers();
        totalOrders();
        calculatePaymentSummary();
        generateReport(false);
        createCharts();

        return reportPanel;
    }

    // ================= FIRST TIME CREATION =================

    createComponent();

    // Default From Date aur To Date = aaj ki date
    java.util.Date today = new java.util.Date();
    fromDate.setDate(today);
    toDate.setDate(today);

    totalCustomers();
    totalPurchase();
    avgPrchase();
    regularCustomers();
    totalOrders();
    calculatePaymentSummary();

    setLayout();
    setBounds();
    addComponent();

    setBorder();
    setIcon();
    setAlignmentComponent();
    addListener();

    setFont();
    setColor();

    f.revalidate();
    f.repaint();

    // Page khulte hi aaj ke orders table mein load karo
    generateReport(false);
    createCharts();

    return reportPanel;
}







void createComponent()
{



System.out.println("xxxxxxxxxxxxxxxxxxxxxxxx");


reportPanel=new JPanel();
reportLabel=new JLabel("    Reports");
reportLabel.setIcon(db.resizeImage(getClass().getResource("imgs/bar.png"),40,40));
cardPanel1=new JPanel();
cardPanel2=new JPanel();
cardPanel3=new JPanel();
cardPanel4=new JPanel();
cardPanel5=new JPanel();
reportFilterPanel=new JPanel();
salesReportPanel=new JPanel();
paymentModePanel=new JPanel();
reportDetailPanel=new JPanel();
summaryPanel=new JPanel();

viewButton1=new JButton("View Details ➜");
viewButton2=new JButton("View Details ➜");
viewButton3=new JButton("View Details ➜");
viewButton4=new JButton("View Details ➜");
viewButton5=new JButton("View Details ➜");

reportTypeComboBox=new JComboBox<>();
reportTypeComboBox.addItem("Sales Report");
reportTypeComboBox.addItem("Purchase Report");
reportTypeComboBox.addItem("Order Report");
reportTypeComboBox.addItem("Customer Report");
reportTypeComboBox.addItem("Stock Report");
reportTypeComboBox.addItem("Payment Report");
customerTypeLabel=new JLabel("Report Type");
fromDate=new JDateChooser();
fromDateLabel=new JLabel("From Date");

toDate=new JDateChooser();
toDateLabel=new JLabel("To Date");
customerTypeComboBox = new JComboBox<>();

customerTypeComboBox.addItem("All");
customerTypeComboBox.addItem("Walk-in Customer");
customerTypeComboBox.addItem("Regular Customer");
customerTypeComboBox.addItem("Existing Customer");

customerTypeLabel  = new JLabel("Customer Type");
reportTypeLabel = new JLabel("Report Type");
paymentComboBox=new JComboBox<>(new String[]{


"All","Cash", "UPI", "Card", "Net Banking", "Other"


});

paymentLabel=new JLabel("Payment Mode");
orderStatusComboBox=new JComboBox<>(new String[]
{

"All",
"Completed",
"Pending",
"Canceled"



});

orderStatusLabel=new JLabel("Order Status");
searchButton=new JButton("Generate Report");
resetButton=new JButton("Reset");
cardPanelIconLabel1=new JLabel();
cardPanelIconLabel2=new JLabel();
cardPanelIconLabel3=new JLabel();
cardPanelIconLabel4=new JLabel();
cardPanelIconLabel5=new JLabel();

cardPanelTextLabel1=new JLabel();
cardPanelTextLabel2=new JLabel();
cardPanelTextLabel3=new JLabel();
cardPanelTextLabel4=new JLabel();
cardPanelTextLabel5=new JLabel();


// REPORT TABLE
reportTableModel = new DefaultTableModel(
    new Object[]{
        "S.No.",
        "Order No",
        "Invoice No",
        "Date & Time",
        "Customer Name",
        "Items",
        "Amount (₹)",
        "Payment Mode",
        "Status"
    }, 0
){
    @Override
    public boolean isCellEditable(int row, int column) {
        return false;
    }
};

reportTable = new JTable(reportTableModel);

reportTable.setRowHeight(25);
reportTable.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);

reportScrollPane = new JScrollPane(reportTable);
reportTable.getColumnModel().getColumn(3).setPreferredWidth(160); 




totalSalesLabel = new JLabel("Total Sales");
cashPaymentLabel = new JLabel("Cash Payment");
upiPaymentLabel = new JLabel("UPI Payment");
cardPaymentLabel = new JLabel("Card Payment");
otherPaymentLabel = new JLabel("Other Payment");








}




void setBounds()
{





reportPanel.setBounds(285-15,180,1660,772); 
reportLabel.setBounds(0,0,reportPanel.getWidth()-5,50);
cardPanel1.setBounds(15,   70, 300, 130);
cardPanel2.setBounds(340,  70, 300, 130);
cardPanel3.setBounds(665,  70, 300, 130);
cardPanel4.setBounds(990,  70, 300, 130);
cardPanel5.setBounds(1315, 70, 310, 130);
reportFilterPanel.setBounds(15,220,reportPanel.getWidth()-50,120);
reportTypeLabel.setBounds(15,44,160,20);
salesReportPanel.setBounds(15,350,(reportPanel.getWidth()-50)/3+10+((reportPanel.getWidth()-50)/3),210);
paymentModePanel.setBounds(((reportPanel.getWidth()-50)/3)*2+35,350,(reportPanel.getWidth()-50)/3-12,210);
reportDetailPanel.setBounds(15,210+350+10,
        (reportPanel.getWidth()-50)/3+35+(reportPanel.getWidth()-50)/3-23,200);

summaryPanel.setBounds(((reportPanel.getWidth()-50)/3)*2+35,
        210+350+10,(reportPanel.getWidth()-50)/3-12,200);

viewButton1.setBounds(15,97,300-15-15,25);
viewButton2.setBounds(15,97,300-15-15,25);
viewButton3.setBounds(15,97,300-15-15,25);
viewButton4.setBounds(15,97,300-15-15,25);
viewButton5.setBounds(15,97,300-15-15,25);

reportTypeComboBox.setBounds(15,70,160,25);
customerTypeLabel.setBounds(15,44,160,20);

fromDate.setBounds(175+30,70,160,25);
fromDateLabel.setBounds(175+30,44,160,20);
toDate.setBounds(175+30+160+30,70,160,25);
toDateLabel.setBounds(175+30+160+30,44,160,22);
customerTypeComboBox.setBounds(175+30+160+30+160+30,70,160,25);
customerTypeLabel .setBounds(175+30+160+30+160+30,44,160,22);
paymentComboBox.setBounds(175+30+160+30+160+30+160+30,70,160,25);
paymentLabel.setBounds(175+30+160+30+160+30+160+30,44,160,22);
orderStatusComboBox.setBounds(175+30+160+30+160+30+160+30+160+30,70,160,25);
orderStatusLabel.setBounds(175+30+160+30+160+30+160+30+160+30,44,160,25);
searchButton.setBounds(175+30+160+30+160+30+160+30+160+30+160+30,70-10,180,25+10);
resetButton.setBounds(175+30+160+30+160+30+160+30+160+30+160+30+180+30,70-10,180,25+10);

cardPanelIconLabel1.setBounds(10,20,70,70);
cardPanelIconLabel2.setBounds(10,20,70,70);
cardPanelIconLabel3.setBounds(10,20,70,70);
cardPanelIconLabel4.setBounds(10,20,100,70);
cardPanelIconLabel5.setBounds(10,20,70,70);

cardPanelTextLabel1.setBounds(100,10,170,65);
cardPanelTextLabel2.setBounds(100,10,170,65);
cardPanelTextLabel3.setBounds(100,10,170,65);
cardPanelTextLabel4.setBounds(100,10,170,65);
cardPanelTextLabel5.setBounds(100,10,170,65);



// ================= SUMMARY LABEL BOUNDS =================

totalSalesLabel.setBounds(
    10, 35,
    summaryPanel.getWidth() - 20, 22
);

cashPaymentLabel.setBounds(
    10, 57+10,
    summaryPanel.getWidth() - 20, 22
);

upiPaymentLabel.setBounds(
    10, 94,
    summaryPanel.getWidth() - 20, 22
);

cardPaymentLabel.setBounds(
    10, 124,
    summaryPanel.getWidth() - 20, 22
);

otherPaymentLabel.setBounds(
    10, 154,
    summaryPanel.getWidth() - 20, 22
);




}



void addComponent()
{

f.add(reportPanel);
reportPanel.add(reportLabel);
reportPanel.add(cardPanel1);
reportPanel.add(cardPanel2);
reportPanel.add(cardPanel3);
reportPanel.add(cardPanel4);
reportPanel.add(cardPanel5);
reportPanel.add(reportFilterPanel);
reportPanel.add(salesReportPanel);
reportPanel.add(paymentModePanel);
reportPanel.add(reportDetailPanel);
reportPanel.add(summaryPanel);
cardPanel1.add(viewButton1);
cardPanel2.add(viewButton2);
cardPanel3.add(viewButton3);
cardPanel4.add(viewButton4);
cardPanel5.add(viewButton5);
reportFilterPanel.add(reportTypeComboBox);
reportFilterPanel.add(reportTypeComboBox);
reportFilterPanel.add(reportTypeLabel);

reportFilterPanel.add(fromDate);
reportFilterPanel.add(fromDateLabel);
reportFilterPanel.add(toDate);
reportFilterPanel.add(toDateLabel);
reportFilterPanel.add(customerTypeComboBox);reportFilterPanel.add(customerTypeLabel);
reportFilterPanel.add(paymentComboBox);
reportFilterPanel.add(paymentLabel);
reportFilterPanel.add(orderStatusComboBox);

reportFilterPanel.add(orderStatusLabel);
reportFilterPanel.add(searchButton);
reportFilterPanel.add(resetButton);
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

reportDetailPanel.add(reportScrollPane);

// ================= SUMMARY LABELS =================

summaryPanel.add(totalSalesLabel);
summaryPanel.add(cashPaymentLabel);
summaryPanel.add(upiPaymentLabel);
summaryPanel.add(cardPaymentLabel);
summaryPanel.add(otherPaymentLabel);





}




void setFont()
{

reportLabel.setFont(new Font("ARIAL",Font.BOLD,23));
reportFilterPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,20));
salesReportPanelBorder.setTitleFont(new Font("Arial",Font.BOLD,20));
paymentModePanelBorder.setTitleFont(new Font("Arial",Font.BOLD,20));
reportDetailPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,20));
summaryPanelBorder.setTitleFont(new Font("ARIAL",Font.BOLD,20));
customerTypeLabel.setFont(new Font("ARIAL",Font.BOLD,15));
fromDateLabel.setFont(new Font("ARIAL",Font.BOLD,15));
toDateLabel.setFont(new Font("ARIAL",Font.BOLD,15));
customerTypeLabel .setFont(new Font("ARIAL",Font.BOLD,15));
paymentLabel.setFont(new Font("ARIAL",Font.BOLD,15));
orderStatusLabel.setFont(new Font("Ariasl",Font.BOLD,15));
reportTypeLabel.setFont(
    new Font("ARIAL", Font.BOLD, 15)
);

reportScrollPane.setBounds(
    10,
    30,
    reportDetailPanel.getWidth() - 20,
    reportDetailPanel.getHeight() - 40
);




// ================= SUMMARY LABEL FONT =================

Font summaryFont = new Font("Arial", Font.BOLD, 15);

totalSalesLabel.setFont(summaryFont);
cashPaymentLabel.setFont(summaryFont);
upiPaymentLabel.setFont(summaryFont);
cardPaymentLabel.setFont(summaryFont);
otherPaymentLabel.setFont(summaryFont);


}



void setColor()
{

reportPanel.setBackground(Color.WHITE);
reportLabel.setOpaque(true);
reportLabel.setBackground(new Color(110, 5, 33));
reportLabel.setForeground(Color.WHITE);
reportFilterPanelBorder.setTitleColor(new Color(110,5,33));

cardPanel1.setBackground(Color.WHITE);
cardPanel2.setBackground(Color.WHITE);
cardPanel3.setBackground(Color.WHITE);
cardPanel4.setBackground(Color.WHITE);
cardPanel5.setBackground(Color.WHITE);
reportFilterPanel.setBackground(Color.WHITE);
salesReportPanel.setBackground(Color.WHITE);
salesReportPanelBorder.setTitleColor(new Color(110,5,33));
paymentModePanelBorder.setTitleColor(new Color(110,5,33));
paymentModePanel.setBackground(Color.WHITE);
reportDetailPanel.setBackground(Color.WHITE);
reportDetailPanelBorder.setTitleColor(new Color(110,5,33));
summaryPanel.setBackground(Color.WHITE);
summaryPanelBorder.setTitleColor(new Color(110,5,33));
viewButton1.setBackground(new Color(199, 222, 247));
viewButton1.setForeground(new Color(70, 110, 160));

viewButton2.setBackground(new Color(205, 232, 210));
viewButton2.setForeground(new Color(70, 125, 80));

viewButton3.setBackground(new Color(248, 216, 181));
viewButton3.setForeground(new Color(170, 105, 45));

viewButton4.setBackground(new Color(235, 210, 215));
viewButton4.setForeground(new Color(155, 85, 105));

viewButton5.setBackground(new Color(220, 203, 247));
viewButton5.setForeground(new Color(110, 75, 165));

searchButton.setBackground(new Color(116, 10, 33));
searchButton.setForeground(Color.WHITE);
resetButton.setBackground(new Color(1, 89, 212));
resetButton.setForeground(Color.WHITE);
cardPanelIconLabel1.setOpaque(true);
cardPanelIconLabel1.setBackground(Color.WHITE);




}





void setLayout()
{


f.setLayout(null);

reportPanel.setLayout(null);
cardPanel1.setLayout(null);
cardPanel2.setLayout(null);
cardPanel3.setLayout(null);
cardPanel4.setLayout(null);
cardPanel5.setLayout(null);
reportFilterPanel.setLayout(null);
salesReportPanel.setLayout(null);
paymentModePanel.setLayout(null);
reportDetailPanel.setLayout(null);
summaryPanel.setLayout(null);




}




void setBorder()
{


reportLabel.setBorder(BorderFactory.createEmptyBorder(0,25,0,0));

cardPanel1.setBorder(BorderFactory.createLineBorder(new Color(199, 222, 247),2));
cardPanel2.setBorder(BorderFactory.createLineBorder(new Color(205, 232, 210),2));
cardPanel3.setBorder(BorderFactory.createLineBorder(new Color(248, 216, 181),2));
cardPanel4.setBorder(BorderFactory.createLineBorder(new Color(235, 210, 215),2));
cardPanel5.setBorder(BorderFactory.createLineBorder(new Color(220, 203, 247),2));
reportFilterPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),1),"Report Filter");   
reportFilterPanel.setBorder(reportFilterPanelBorder);
salesReportPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),1),"Sales Reports");
salesReportPanel.setBorder(salesReportPanelBorder);
paymentModePanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),1),"Payment Mode Wise Orders");
paymentModePanel.setBorder(paymentModePanelBorder);




reportDetailPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),1),"Reports Details");
reportDetailPanel.setBorder(reportDetailPanelBorder);
summaryPanelBorder=BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(225,213,210),1),"Summary");
summaryPanel.setBorder(summaryPanelBorder);


//  card view button border false

viewButton1.setBorderPainted(false);
viewButton2.setBorderPainted(false);
viewButton3.setBorderPainted(false);
viewButton4.setBorderPainted(false);
viewButton5.setBorderPainted(false);






}



void setIcon()
{


searchButton.setIcon(db.resizeImage(getClass().getResource("imgs/search.png"),25,25));
resetButton.setIcon(db.resizeImage(getClass().getResource("imgs/refresh.png"),25,25));
cardPanelIconLabel1.setIcon(db.resizeImage(getClass().getResource("imgs/group.png"),70,70));
cardPanelIconLabel2.setIcon(db.resizeImage(getClass().getResource("imgs/customerCart.png"),70,70));
cardPanelIconLabel3.setIcon(db.resizeImage(getClass().getResource("imgs/dollar2.png"),70,70));
cardPanelIconLabel4.setIcon(db.resizeImage(getClass().getResource("imgs/avg.png"),100,70));
cardPanelIconLabel5.setIcon(db.resizeImage(getClass().getResource("imgs/star.png"),70,70));



}





void setAlignmentComponent()
{




searchButton.setHorizontalAlignment(SwingConstants.LEFT);
searchButton.setIconTextGap(15);
resetButton.setHorizontalAlignment(SwingConstants.LEFT);
resetButton.setIconTextGap(15);




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






int regularCustomers = 0;

void regularCustomers()
{
    try
    {
        // Count unique mobile numbers explicitly marked as Regular Customer.
        // Do not count every repeat buyer, canceled order, or test phone value.
        String sql =
            "SELECT COUNT(DISTINCT TRIM(mobile_no)) " +
            "FROM orders " +
            "WHERE customer_type = ? " +
            "AND mobile_no IS NOT NULL " +
            "AND TRIM(mobile_no) <> ''";

        try (PreparedStatement ps = con.prepareStatement(sql))
        {
            ps.setString(1, "Regular Customer");

            try (ResultSet rs = ps.executeQuery())
            {
                if(rs.next())
                {
                    regularCustomers = rs.getInt(1);
                }
            }
        }

        cardPanelTextLabel5.setText(
            "<html><center>" +
            "<font size='4' color='#6A1B9A'>Regular Customers</font><br>" +
            "<font size='6' color='#6A1B9A'>" +
            regularCustomers +
            "</font>" +
            "</center></html>"
        );
    }
    catch(SQLException ex)
    {
        ex.printStackTrace();
        JOptionPane.showMessageDialog(
            null,
            ex.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}

void generateReport()
{
    generateReport(true);
}

void generateReport(boolean showNoRecordsMessage)
{
    try
    {
        // =========================
        // DATE VALIDATION
        // =========================

        if(fromDate.getDate() == null || toDate.getDate() == null)
        {
            JOptionPane.showMessageDialog(
                null,
                "Please select From Date and To Date"
            );

            return;
        }

        if(fromDate.getDate().after(toDate.getDate()))
        {
            if(showNoRecordsMessage)
            {
                JOptionPane.showMessageDialog(
                    null,
                    "From Date cannot be after To Date.",
                    "Invalid Date Range",
                    JOptionPane.WARNING_MESSAGE
                );
            }
            return;
        }

        // =========================
        // DATE FORMAT
        // =========================

        SimpleDateFormat sdf =
            new SimpleDateFormat("yyyy-MM-dd");

        String from =
            sdf.format(fromDate.getDate());

        String to =
            sdf.format(toDate.getDate());


        // =========================
        // PAYMENT & STATUS
        // =========================

        String customerType =
            customerTypeComboBox.getSelectedItem().toString();

        String payment =
            paymentComboBox.getSelectedItem().toString();

        String status =
            orderStatusComboBox.getSelectedItem().toString();


        // =========================
        // MAIN QUERY
        // =========================

        // Date ko SQL DATE ke roop mein compare karo, taaki JDBC/time-zone
        // conversion ki wajah se aaj ke orders miss na hon.
        String query =
            "SELECT order_no, billNo, order_date, customer_name, " +
            "total_items, grand_total, payment_Mode, status " +
            "FROM orders " +
            "WHERE DATE(order_date) BETWEEN ? AND ? ";


        // =========================
        // CUSTOMER TYPE FILTER
        // =========================

        if(!customerType.equals("All"))
        {
            query += "AND customer_type = ? ";
        }

        // =========================
        // PAYMENT FILTER
        // =========================

        if(!payment.equals("All"))
        {
            query += "AND payment_Mode = ? ";
        }


        // =========================
        // STATUS FILTER
        // =========================

        if(!status.equals("All"))
        {
            query += "AND status = ? ";
        }


        // =========================
        // LATEST ORDER FIRST
        // =========================

        query += "ORDER BY order_date DESC, order_no DESC";


        // =========================
        // PREPARED STATEMENT
        // =========================

        PreparedStatement ps =
            con.prepareStatement(query);

        int parameter = 1;


        // Bind the same yyyy-MM-dd values that are displayed by the date fields.
        // DATE(order_date) BETWEEN ? AND ? includes every time during both dates.
        ps.setString(parameter++, from);
        ps.setString(parameter++, to);

        // CUSTOMER TYPE PARAMETER
        if(!customerType.equals("All"))
        {
            ps.setString(parameter++, customerType);
        }

        // PAYMENT PARAMETER

        if(!payment.equals("All"))
        {
            ps.setString(parameter++, payment);
        }


        // STATUS PARAMETER

        if(!status.equals("All"))
        {
            ps.setString(parameter++, status);
        }


        // =========================
        // EXECUTE QUERY
        // =========================

        System.out.println("Reports SQL: " + query);
        System.out.println("Reports date range: " + from + " to " + to
            + ", Customer Type: " + customerType
            + ", Payment: " + payment
            + ", Status: " + status);

        ResultSet rs =
            ps.executeQuery();


        // =========================
        // CLEAR OLD TABLE DATA
        // =========================

        reportTableModel.setRowCount(0);


        int serialNo = 1;


        // =========================
        // DATE & TIME FORMAT
        // =========================

        SimpleDateFormat dateTimeFormat =
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


        // =========================
        // LOAD ALL ORDERS
        // =========================

        while(rs.next())
        {
            Timestamp orderDate =
                rs.getTimestamp("order_date");


            String formattedDateTime =
                dateTimeFormat.format(orderDate);


            reportTableModel.addRow(
                new Object[]
                {
                    serialNo++,

                    rs.getString("order_no"),

                    rs.getString("billNo"),

                    formattedDateTime,

                    rs.getString("customer_name"),

                    rs.getInt("total_items"),

                    String.format(
                        "₹ %.2f",
                        rs.getDouble("grand_total")
                    ),

                    rs.getString("payment_Mode"),

                    rs.getString("status")
                }
            );
        }







        // =========================
        // TOTAL ROWS
        // =========================

        System.out.println(
            "TOTAL ROWS LOADED = " +
            reportTableModel.getRowCount()
        );


        // =========================
        // CLOSE
        // =========================

        rs.close();
        ps.close();









// =========================
// APPLY STATUS COLORS
// =========================

setStatusColor(reportTable);





        // =========================
        // REFRESH TABLE
        // =========================

        reportTable.revalidate();
        reportTable.repaint();


        // =========================
        // NO RECORD
        // =========================

        if(showNoRecordsMessage && reportTableModel.getRowCount() == 0)
        {
            JOptionPane.showMessageDialog(
                null,
                "No orders found for the selected dates/filters. Check whether orders exist for this date in the database.",
                "No Records",
                JOptionPane.INFORMATION_MESSAGE
            );
        }

    }
    catch(SQLException ex)
    {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
            null,
            ex.getMessage(),
            "Database Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}void addListener()
{







searchButton.addActionListener(e ->
{
    generateReport(true);
    createCharts();
});

resetButton.addActionListener(e -> resetReport());

customerTypeComboBox.addActionListener(e -> updateReportAndChartsIfDatesSelected());
paymentComboBox.addActionListener(e -> updateReportAndChartsIfDatesSelected());
orderStatusComboBox.addActionListener(e -> updateReportAndChartsIfDatesSelected());

fromDate.addPropertyChangeListener("date",
    e -> updateReportAndChartsIfDatesSelected());
toDate.addPropertyChangeListener("date",
    e -> updateReportAndChartsIfDatesSelected());







}




// Date/filter change hone par table aur charts dono refresh karo.
void updateReportAndChartsIfDatesSelected()
{
    if(suppressAutoRefresh || fromDate == null || toDate == null ||
       fromDate.getDate() == null || toDate.getDate() == null)
    {
        return;
    }

    // Jab tak date range valid na ho, purane results ko galat range se replace na karo.
    if(fromDate.getDate().after(toDate.getDate()))
    {
        return;
    }

    generateReport(false);
    createCharts();
}




void resetReport()
{
    // =========================
    // RESET FILTERS
    // =========================

    reportTypeComboBox.setSelectedIndex(0);
    customerTypeComboBox.setSelectedIndex(0);
    paymentComboBox.setSelectedIndex(0);
    orderStatusComboBox.setSelectedIndex(0);

    java.util.Date today = new java.util.Date();
    suppressAutoRefresh = true;
    fromDate.setDate(today);
    toDate.setDate(today);
    suppressAutoRefresh = false;

    // =========================
    // RELOAD TODAY'S REPORT
    // =========================

    generateReport(false);


    // =========================
    // REMOVE SALES CHART
    // =========================

    if(salesChartPanel != null)
    {
        salesReportPanel.remove(salesChartPanel);
        salesChartPanel = null;
    }


    // =========================
    // REMOVE CUSTOMER TYPE CHART
    // =========================

    if(customerTypeChartPanel != null)
    {
        salesReportPanel.remove(customerTypeChartPanel);
        customerTypeChartPanel = null;
    }


    // =========================
    // REMOVE PAYMENT CHART
    // =========================

    if(paymentChartPanel != null)
    {
        paymentModePanel.remove(paymentChartPanel);
        paymentChartPanel = null;
    }


    // =========================
    // REFRESH UI
    // =========================

    salesReportPanel.revalidate();
    salesReportPanel.repaint();

    paymentModePanel.revalidate();
    paymentModePanel.repaint();

    reportTable.revalidate();
    reportTable.repaint();

    // Reset ke baad today's charts bhi dobara banao.
    createCharts();












}


void createCharts()
{
    createSalesChart();
    createCustomerTypeChart();
    createPaymentChart();
}





void createSalesChart()
{
    DefaultCategoryDataset dataset = new DefaultCategoryDataset();

    if(fromDate.getDate() == null || toDate.getDate() == null)
        return;

    try
    {
        String customerType =
            customerTypeComboBox.getSelectedItem().toString();

        String payment =
            paymentComboBox.getSelectedItem().toString();

        String status =
            orderStatusComboBox.getSelectedItem().toString();

        String query =
            "SELECT DATE(order_date) AS sale_date, " +
            "SUM(grand_total) AS total_sales " +
            "FROM orders " +
            "WHERE order_date >= ? " +
            "AND order_date < DATE_ADD(?, INTERVAL 1 DAY) ";

        if(!customerType.equals("All"))
            query += "AND customer_type = ? ";

        if(!payment.equals("All"))
            query += "AND payment_Mode = ? ";

        if(!status.equals("All"))
            query += "AND status = ? ";

        query +=
            "GROUP BY DATE(order_date) " +
            "ORDER BY sale_date";

        PreparedStatement ps = con.prepareStatement(query);

        int parameter = 1;

        ps.setDate(
            parameter++,
            new java.sql.Date(fromDate.getDate().getTime())
        );

        ps.setDate(
            parameter++,
            new java.sql.Date(toDate.getDate().getTime())
        );

        if(!customerType.equals("All"))
            ps.setString(parameter++, customerType);

        if(!payment.equals("All"))
            ps.setString(parameter++, payment);

        if(!status.equals("All"))
            ps.setString(parameter++, status);

        ResultSet rs = ps.executeQuery();

        SimpleDateFormat sdf =
            new SimpleDateFormat("dd MMM");

        while(rs.next())
        {
            String date =
                sdf.format(rs.getDate("sale_date"));

            double amount =
                rs.getDouble("total_sales");

            dataset.addValue(
                amount,
                "Sales",
                date
            );
        }

        rs.close();
        ps.close();

        JFreeChart chart =
            ChartFactory.createBarChart(
                "",
                "Date",
                "Sales Amount (₹)",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
            );

        if(salesChartPanel != null)
            salesReportPanel.remove(salesChartPanel);

        salesChartPanel =
            new ChartPanel(chart);

        salesChartPanel.setBounds(
            5,
            20,
            salesReportPanel.getWidth() / 2 - 8,
            salesReportPanel.getHeight() - 25
        );

        salesReportPanel.add(salesChartPanel);

        salesReportPanel.revalidate();
        salesReportPanel.repaint();
    }
    catch(SQLException ex)
    {
        ex.printStackTrace();
    }
}






void createCustomerTypeChart()
{
    DefaultPieDataset<String> dataset =
        new DefaultPieDataset<>();

    if(fromDate.getDate() == null || toDate.getDate() == null)
        return;

    try
    {
        String customerType =
            customerTypeComboBox.getSelectedItem().toString();

        String payment =
            paymentComboBox.getSelectedItem().toString();

        String status =
            orderStatusComboBox.getSelectedItem().toString();


        String query =
            "SELECT customer_type, " +
            "SUM(grand_total) AS total_sales " +
            "FROM orders " +
            "WHERE order_date >= ? " +
            "AND order_date < DATE_ADD(?, INTERVAL 1 DAY) " +
            "AND customer_type IS NOT NULL " +
            "AND TRIM(customer_type) <> '' ";


        if(!customerType.equals("All"))
            query += "AND customer_type = ? ";

        if(!payment.equals("All"))
            query += "AND payment_Mode = ? ";

        if(!status.equals("All"))
            query += "AND status = ? ";


       query +=
    "GROUP BY customer_type " +
    "HAVING SUM(grand_total) > 0 " +
    "ORDER BY total_sales DESC";


        PreparedStatement ps =
            con.prepareStatement(query);

        int parameter = 1;


        ps.setDate(
            parameter++,
            new java.sql.Date(
                fromDate.getDate().getTime()
            )
        );


        ps.setDate(
            parameter++,
            new java.sql.Date(
                toDate.getDate().getTime()
            )
        );


        if(!customerType.equals("All"))
            ps.setString(parameter++, customerType);

        if(!payment.equals("All"))
            ps.setString(parameter++, payment);

        if(!status.equals("All"))
            ps.setString(parameter++, status);


        ResultSet rs = ps.executeQuery();


        while(rs.next())
        {
            dataset.setValue(
                rs.getString("customer_type"),
                rs.getDouble("total_sales")
            );
        }


        rs.close();
        ps.close();


        // ==========================================
        // CREATE DONUT CHART
        // ==========================================

        JFreeChart chart =
            ChartFactory.createRingChart(
                "",
                dataset,
                true,
                true,
                false
            );


        // ==========================================
        // DONUT SETTINGS
        // ==========================================

        RingPlot plot =
            (RingPlot) chart.getPlot();

        plot.setSectionDepth(0.35);


        plot.setLabelGenerator(
            new StandardPieSectionLabelGenerator(
                "{0} ({2})",
                new DecimalFormat("#,##0.00"),
new DecimalFormat("0.00%")            )
        );


        plot.setLabelFont(
            new Font("Arial", Font.BOLD, 12)
        );

        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.LIGHT_GRAY);
        plot.setLabelShadowPaint(null);

        plot.setBackgroundPaint(Color.WHITE);

        chart.setBackgroundPaint(Color.WHITE);


        // ==========================================
        // REMOVE OLD CHART
        // ==========================================

        if(customerTypeChartPanel != null)
        {
            salesReportPanel.remove(
                customerTypeChartPanel
            );
        }


        // ==========================================
        // ADD NEW CHART
        // ==========================================

        customerTypeChartPanel =
            new ChartPanel(chart);


        customerTypeChartPanel.setBounds(
            salesReportPanel.getWidth() / 2,
            20,
            salesReportPanel.getWidth() / 2 - 5,
            salesReportPanel.getHeight() - 25
        );


        salesReportPanel.add(
            customerTypeChartPanel
        );


        salesReportPanel.revalidate();
        salesReportPanel.repaint();
    }
    catch(SQLException ex)
    {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
            null,
            ex.getMessage(),
            "Customer Type Chart Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}


void createPaymentChart()
{
    DefaultPieDataset<String> dataset =
        new DefaultPieDataset<>();

    if(fromDate.getDate() == null || toDate.getDate() == null)
        return;

    try
    {
        String customerType =
            customerTypeComboBox.getSelectedItem().toString();

        String payment =
            paymentComboBox.getSelectedItem().toString();

        String status =
            orderStatusComboBox.getSelectedItem().toString();

        String query =
            "SELECT payment_Mode, " +
            "COUNT(*) AS total_orders " +
            "FROM orders " +
            "WHERE order_date >= ? " +
            "AND order_date < DATE_ADD(?, INTERVAL 1 DAY) " +
            "AND payment_Mode IS NOT NULL " +
            "AND TRIM(payment_Mode) <> '' ";

        if(!customerType.equals("All"))
            query += "AND customer_type = ? ";

        if(!payment.equals("All"))
            query += "AND payment_Mode = ? ";

        if(!status.equals("All"))
            query += "AND status = ? ";

        query +=
            "GROUP BY payment_Mode " +
            "ORDER BY total_orders DESC";

        PreparedStatement ps =
            con.prepareStatement(query);

        int parameter = 1;

        ps.setDate(
            parameter++,
            new java.sql.Date(fromDate.getDate().getTime())
        );

        ps.setDate(
            parameter++,
            new java.sql.Date(toDate.getDate().getTime())
        );

        if(!customerType.equals("All"))
            ps.setString(parameter++, customerType);

        if(!payment.equals("All"))
            ps.setString(parameter++, payment);

        if(!status.equals("All"))
            ps.setString(parameter++, status);

        ResultSet rs = ps.executeQuery();

        while(rs.next())
        {
            String paymentMode =
                rs.getString("payment_Mode");

            int totalOrders =
                rs.getInt("total_orders");

            dataset.setValue(
                paymentMode,
                totalOrders
            );
        }

        rs.close();
        ps.close();


        // ==========================================
        // CREATE DONUT CHART
        // ==========================================

        JFreeChart chart =
            ChartFactory.createRingChart(
                "",
                dataset,
                true,
                true,
                false
            );


        // ==========================================
        // DONUT SETTINGS
        // ==========================================

        RingPlot plot =
            (RingPlot) chart.getPlot();

        plot.setSectionDepth(0.35);

        plot.setLabelGenerator(
            new StandardPieSectionLabelGenerator(
                "{0} ({2})",
                new DecimalFormat("#,##0"),
                new DecimalFormat("0%")
            )
        );

        plot.setLabelFont(
            new Font("Arial", Font.BOLD, 12)
        );

        plot.setLabelBackgroundPaint(Color.WHITE);
        plot.setLabelOutlinePaint(Color.LIGHT_GRAY);
        plot.setLabelShadowPaint(null);

        plot.setBackgroundPaint(Color.WHITE);

        chart.setBackgroundPaint(Color.WHITE);


        // ==========================================
        // REMOVE OLD CHART
        // ==========================================

        if(paymentChartPanel != null)
        {
            paymentModePanel.remove(paymentChartPanel);
        }


        // ==========================================
        // ADD NEW CHART
        // ==========================================

        paymentChartPanel =
            new ChartPanel(chart);

        paymentChartPanel.setBounds(
            5,
            20,
            paymentModePanel.getWidth() - 10,
            paymentModePanel.getHeight() - 25
        );

        paymentModePanel.add(paymentChartPanel);




        paymentModePanel.revalidate();
        paymentModePanel.repaint();
    }
    catch(SQLException ex)
    {
        ex.printStackTrace();

        JOptionPane.showMessageDialog(
            null,
            ex.getMessage(),
            "Payment Chart Error",
            JOptionPane.ERROR_MESSAGE
        );
    }
}





void calculatePaymentSummary()
{
    try
    {
        String sql =
    "SELECT " +
    "COALESCE(SUM(grand_total),0), " +

    "COALESCE(SUM(CASE WHEN payment_Mode='Cash' " +
    "THEN grand_total ELSE 0 END),0), " +

    "COALESCE(SUM(CASE WHEN payment_Mode='UPI' " +
    "THEN grand_total ELSE 0 END),0), " +

    "COALESCE(SUM(CASE WHEN payment_Mode='Card' " +
    "THEN grand_total ELSE 0 END),0), " +

    "COALESCE(SUM(CASE WHEN payment_Mode NOT IN ('Cash','UPI','Card') " +
    "OR payment_Mode IS NULL " +
    "THEN grand_total ELSE 0 END),0) " +

    "FROM orders " +
    "WHERE status='Completed'";

        PreparedStatement ps = con.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {
            double totalSales = rs.getDouble(1);
            double cash = rs.getDouble(2);
            double upi = rs.getDouble(3);
            double card = rs.getDouble(4);
            double other = rs.getDouble(5);

// ================= SUMMARY LABEL TEXT =================

totalSalesLabel.setText(
    "<html><table><tr>"
    + "<td width='150'>Total Sales</td>"
    + "<td width='200'></td>"
    + "<td><font color='#1976D2'>₹ "
    + String.format("%,.2f", totalSales)
    + "</font></td>"
    + "</tr></table>"
    + "<hr></html>"
);

cashPaymentLabel.setText(
    "<html><table><tr>"
    + "<td width='150'>Cash Payment</td>"
    + "<td width='200'></td>"
    + "<td><font color='#8E24AA'>₹ "
    + String.format("%,.2f", cash)
    + "</font></td>"
    + "</tr></table>"
    + "<hr></html>"
);

upiPaymentLabel.setText(
    "<html><table><tr>"
    + "<td width='150'>UPI Payment</td>"
    + "<td width='200'></td>"
    + "<td><font color='#00A65A'>₹ "
    + String.format("%,.2f", upi)
    + "</font></td>"
    + "</tr></table>"
    + "<hr></html>"
);

cardPaymentLabel.setText(
    "<html><table><tr>"
    + "<td width='150'>Card Payment</td>"
    + "<td width='200'></td>"
    + "<td><font color='#F57C00'>₹ "
    + String.format("%,.2f", card)
    + "</font></td>"
    + "</tr></table>"
    + "<hr></html>"
);

otherPaymentLabel.setText(
    "<html><table><tr>"
    + "<td width='150'>Other Payment</td>"
    + "<td width='200'></td>"
    + "<td><font color='#E53935'>₹ "
    + String.format("%,.2f", other)
    + "</font></td>"
    + "</tr></table>"
    + "<hr></html>"
);



Color lineColor = new Color(210, 210, 210);

totalSalesLabel.setBorder(
    BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor)
);

cashPaymentLabel.setBorder(
    BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor)
);

upiPaymentLabel.setBorder(
    BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor)
);

cardPaymentLabel.setBorder(
    BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor)
);

otherPaymentLabel.setBorder(
    BorderFactory.createMatteBorder(0, 0, 1, 0, lineColor)
);



        }

        rs.close();
        ps.close();
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
}






void setStatusColor(JTable table)
{
    table.getColumnModel()
         .getColumn(8)
         .setCellRenderer(new DefaultTableCellRenderer()
    {
        @Override
        public Component getTableCellRendererComponent(
                JTable table,
                Object value,
                boolean isSelected,
                boolean hasFocus,
                int row,
                int column)
        {
            super.getTableCellRendererComponent(
                    table,
                    value,
                    false,
                    hasFocus,
                    row,
                    column
            );

            setOpaque(true);
            setHorizontalAlignment(SwingConstants.CENTER);
            setFont(new Font("Arial", Font.BOLD, 14));

            String status = value == null ? "" :
                    value.toString()
                         .trim()
                         .replaceAll("\\s+", " ");

            if(status.equalsIgnoreCase("Completed"))
            {
                setBackground(new Color(210, 245, 210));
                setForeground(new Color(0, 120, 50));
            }
            else if(status.equalsIgnoreCase("Pending"))
            {
                setBackground(new Color(255, 235, 180));
                setForeground(new Color(210, 110, 0));
            }
            else if(status.equalsIgnoreCase("Canceled") ||
                    status.equalsIgnoreCase("Cancelled"))
            {
                setBackground(new Color(255, 210, 210));
                setForeground(new Color(200, 0, 0));
            }
            else
            {
                setBackground(Color.WHITE);
                setForeground(Color.BLACK);
            }

            return this;
        }
    });
}









}