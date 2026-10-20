import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.sql.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.util.Arrays;
import java.awt.event.*;
import java.math.BigDecimal;

public class Stock 
{



boolean stockCreated = false;



DashBoard db;


JPanel mainPanel;
JPanel subPanel1;
JPanel subPanel2;
JPanel miniHeaderPanel;
JPanel cardsPanel;
JScrollPane sp;
JFrame f;





JPanel miniHeaderPanel2;
JLabel miniHeaderL1;
JLabel miniHeaderL2;
JLabel miniHeaderLogo;

JTextField tf1;
JButton searchButton;
JButton addButton;
JButton refreshButton;
 


JPanel sumarryPanel1;
JPanel sumarryPanel2;
JPanel sumarryPanel3;
JPanel sumarryPanel4;


JLabel s1IconLabel;
JLabel s2IconLabel;
JLabel s3IconLabel;
JLabel s4IconLabel;



Connection con;

JDBC jd=new JDBC();



JLabel sL1;
JLabel sL2;
JLabel sL3;
JLabel sL4;


JPanel productStockListPanel;
JLabel productStockListLabel;


JTable t1;
JScrollPane sp1;
String[] columns = {
    "S.No.", "Name", "Category", "Stock", "Unit", "Price", "Status"
};


Object[][]  data=new Object[jd.countRow()][7];



JLabel noteLabel;

JLabel noteLabelIcon;



AddItem ai;

DefaultTableModel model;

public void createStock(
    JPanel miniHeaderPanel,
    JPanel cardsPanel,
    JScrollPane sp,
    JFrame f,
    DashBoard db,
    AddItem ai)
{
    this.ai = ai;
    this.db = db;
    this.con = jd.con;

    this.miniHeaderPanel = miniHeaderPanel;
    this.cardsPanel = cardsPanel;
    this.sp = sp;
    this.f = f;

    if(stockCreated)
    {
        showStock();
        loadStock();

        totalProducts();
        totalStockQuantity();
        lowStockItem();
        outOfStockItem();
        return;
    }

    stockCreated = true;

    createComponent();
    hideComponent();

    miniHeaderPanel2.setLayout(null);
    productStockListPanel.setLayout(null);
    sumarryPanel1.setLayout(null);
    sumarryPanel2.setLayout(null);
    sumarryPanel3.setLayout(null);
    sumarryPanel4.setLayout(null);

    setBounds();
    addComponent();

    loadStock();

    setIconOnMiniHeaderPanel();

    totalProducts();
    totalStockQuantity();
    lowStockItem();
    outOfStockItem();

    addListener();

    setColor();
    setFont();
}

void hideComponent()
{

miniHeaderPanel.setVisible(false);
cardsPanel.setVisible(false);
sp.setVisible(false);



}


void hideStock()
{


  if(miniHeaderPanel2!=null)
  {
  miniHeaderPanel2.setVisible(false);
   }
 if(productStockListPanel!=null)
{
 

productStockListPanel.setVisible(false);

}
}



 void  showStock()

{


    miniHeaderPanel2.setVisible(true);
    productStockListPanel.setVisible(true);
}


void createComponent()
{

System.out.println("UI create Add Stock");


miniHeaderPanel2=new JPanel();
miniHeaderL1=new JLabel("Stock Management");
miniHeaderL2=new JLabel("View and manage all sweet/product stock");
miniHeaderLogo=new JLabel(resizeImage(getClass().getResource("imgs/stock.png"),70,60));
tf1=new JTextField();
searchButton=new JButton("Search");
addButton=new JButton("Add Stock");
refreshButton=new JButton("Refresh");


sumarryPanel1=new JPanel();
sumarryPanel2=new JPanel();
sumarryPanel3=new JPanel();
sumarryPanel4=new JPanel();

s1IconLabel=new JLabel(resizeImage(getClass().getResource("imgs/s_bag.png"),70,70));
s2IconLabel=new JLabel(resizeImage(getClass().getResource("imgs/s_stock.png"),70,70));
s3IconLabel=new JLabel(resizeImage(getClass().getResource("imgs/s_alert.png"),70,70));
s4IconLabel=new JLabel(resizeImage(getClass().getResource("imgs/s_remove.png"),70,70));


sL1=new JLabel();
sL2=new JLabel();
sL3=new JLabel();
sL4=new JLabel();
productStockListPanel=new JPanel();
productStockListLabel=new JLabel("Product Stock List ");

t1=new JTable(data,columns);
sp1=new JScrollPane(t1);

t2=new JTable(data,columns);
sp2=new JScrollPane(t2);

sp2.setVisible(false);


sp1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

noteLabel=new JLabel("<html> <font size='5'><font color=blue>Note:</font> Status is calculated automatically based on stock quantity.</font></html> ");
noteLabelIcon=new JLabel(resizeImage(getClass().getResource("imgs/note.png"),715,180));


}


void setBounds()
{

miniHeaderPanel2.setBounds(295,190,f.getWidth(),110);
miniHeaderL1.setBounds(150,20,500,30);
miniHeaderL2.setBounds(150,60,500,30);
miniHeaderLogo.setBounds(30,20,70,60);
tf1.setBounds(600,40,300,40);
searchButton.setBounds(930,40,150,40);
addButton.setBounds(1100,40,170,40);
refreshButton.setBounds(1290,40,150,40);

productStockListPanel.setBounds(300,320,1610,633);


sumarryPanel1.setBounds(5,5,380,120);
sumarryPanel2.setBounds(410,5,380,120);
sumarryPanel3.setBounds(820,5,380,120);
sumarryPanel4.setBounds(1225,5,380,120);


s1IconLabel.setBounds(40,30,70,70);
s2IconLabel.setBounds(40,30,70,70);
s3IconLabel.setBounds(40,30,70,70);
s4IconLabel.setBounds(40,30,70,70);




sL1.setBounds(150,0,200,130);
sL2.setBounds(150,0,200,130);
sL3.setBounds(150,0,200,130);
sL4.setBounds(150,0,200,130);


productStockListLabel.setBounds(5,155,400,30);

sp1.setBounds(0,190,productStockListPanel.getWidth(),productStockListPanel.getHeight()-260);

sp2.setBounds(0,190,productStockListPanel.getWidth(),productStockListPanel.getHeight()-260);

noteLabel.setBounds(100,575,580,50);

noteLabelIcon.setBounds(690,510,715,180);






}



void addComponent()
{

f.add(miniHeaderPanel2);
miniHeaderPanel2.add(miniHeaderL1);
miniHeaderPanel2.add(miniHeaderL2);
miniHeaderPanel2.add(miniHeaderLogo);
miniHeaderPanel2.add(tf1);
miniHeaderPanel2.add(searchButton);
miniHeaderPanel2.add(addButton);
miniHeaderPanel2.add(refreshButton);

productStockListPanel.add(sumarryPanel1);
productStockListPanel.add(sumarryPanel2);
productStockListPanel.add(sumarryPanel3);
productStockListPanel.add(sumarryPanel4);

sumarryPanel1.add(s1IconLabel);
sumarryPanel2.add(s2IconLabel);
sumarryPanel3.add(s3IconLabel);
sumarryPanel4.add(s4IconLabel);

f.add(productStockListPanel);


sumarryPanel1.add(sL1);
sumarryPanel2.add(sL2);
sumarryPanel3.add(sL3);
sumarryPanel4.add(sL4);


productStockListPanel.add(productStockListLabel);
productStockListPanel.add(sp1);

productStockListPanel.add(sp2);

productStockListPanel.add(noteLabel);
productStockListPanel.add(noteLabelIcon);


}

void setColor()
{

miniHeaderPanel.setBackground(Color.WHITE);
miniHeaderL1.setForeground(new Color(91, 55, 28));
miniHeaderPanel2.setBackground(Color.WHITE);
searchButton.setBackground(new Color(116, 10, 33));
addButton.setBackground(new Color(3, 148, 72));
refreshButton.setBackground(new Color(1, 89, 212));

searchButton.setForeground(Color.WHITE);
addButton.setForeground(Color.WHITE);
refreshButton.setForeground(Color.WHITE);

sumarryPanel1.setBackground(new Color(225, 237, 250)); // Blue
sumarryPanel2.setBackground(new Color(225, 242, 225)); // Green
sumarryPanel3.setBackground(new Color(253, 237, 215)); // Orange
sumarryPanel4.setBackground(new Color(250, 225, 225)); // Red


productStockListPanel.setBackground(Color.WHITE);


}
void setFont()
{

miniHeaderL1.setFont(new Font("ARIAL",Font.PLAIN,25));
miniHeaderL2.setFont(new Font("ARIAL",Font.PLAIN,20));
tf1.setFont(new Font("ARIAL",Font.PLAIN,18));
searchButton.setFont(new Font("ARIAL",Font.PLAIN,18));
addButton.setFont(new Font("ARIAL",Font.PLAIN,18));
refreshButton.setFont(new Font("ARIAL",Font.PLAIN,18));
productStockListLabel.setFont(new Font("ARIAL",Font.BOLD,20));

t1.setFont(new Font("ARIAL",Font.PLAIN,17));


}


ImageIcon resizeImage(URL url,int w,int h)
{

ImageIcon icon=new ImageIcon(url);
Image img=icon.getImage();

Image resizeImage =img.getScaledInstance(w,h,Image.SCALE_SMOOTH);
return new ImageIcon(resizeImage);

}

void setIconOnMiniHeaderPanel()
{

searchButton.setIcon(resizeImage(getClass().getResource("imgs/search.png"),30,30));
 searchButton.setHorizontalAlignment(SwingConstants.LEFT);
 searchButton.setIconTextGap(15);
addButton.setIcon(resizeImage(getClass().getResource("imgs/plus.png"),40,30));
 addButton.setHorizontalAlignment(SwingConstants.LEFT);
 addButton.setIconTextGap(15);

refreshButton.setIcon(resizeImage(getClass().getResource("imgs/refresh.png"),30,30));
 refreshButton.setHorizontalAlignment(SwingConstants.LEFT);
 refreshButton.setIconTextGap(15);





}

int totalProducts=0;
void totalProducts()
{

try
{

PreparedStatement ps=con.prepareStatement("SELECT COUNT(*) FROM sweets");
ResultSet rs=ps.executeQuery();

if(rs.next())
{

totalProducts=rs.getInt(1);
sL1.setText("<html><center><font color=blue><font size=5>Total Products</font></font><br><h2>"+totalProducts+"</h2></center></html>");

}

else
{


JOptionPane.showMessageDialog(null,"No products available");
return;
}

rs.close();
ps.close();

}


catch(Exception ex)

{


JOptionPane.showMessageDialog(null,ex.getMessage());


}










}





BigDecimal totalStockQuantity = BigDecimal.ZERO;
void totalStockQuantity()
{
    try(PreparedStatement ps = con.prepareStatement("SELECT SUM(stock) FROM sweets");
        ResultSet rs = ps.executeQuery())
    {
        BigDecimal total = BigDecimal.ZERO;

        if(rs.next())
        {
            total = rs.getBigDecimal(1);
            if(total == null)
            {
                total = BigDecimal.ZERO;
            }
        }

        totalStockQuantity = total;
        String totalText = totalStockQuantity.stripTrailingZeros().toPlainString();

        sL2.setText(
            "<html><center><font color=green><font size=5>"
            + "Total Stock Quantity</font></font><br><h2>"
            + totalText
            + "</h2></center></html>"
        );
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}

int lowStockItem=0;
void lowStockItem()
{

    try
    {

        PreparedStatement ps = con.prepareStatement(
            "SELECT COUNT(*) FROM sweets WHERE stock BETWEEN 1 AND 10"
        );

        ResultSet rs = ps.executeQuery();

        if(rs.next())
        {

            lowStockItem = rs.getInt(1);

            sL3.setText(
                "<html><center><font color=orange><font size=5>"
                + "Low Stock Item</font></font><br><h2>"
                + lowStockItem
                + "</h2></center></html>"
            );

        }

        rs.close();
        ps.close();

    }
    catch(Exception ex)
    {

        JOptionPane.showMessageDialog(null, ex.getMessage());

    }

}





int outOfStockItem=0;
void outOfStockItem()
{

try
{



PreparedStatement ps=con.prepareStatement("SELECT COUNT(*) FROM  sweets WHERE stock=0");

  
ResultSet rs=ps.executeQuery();

if(rs.next())
{

outOfStockItem=rs.getInt(1);
sL4.setText("<html><center><font color=red><font size=5>Out Of Stock Item</font></font><br><h2>"+outOfStockItem+"</h2></center></html>");


}

else
{


JOptionPane.showMessageDialog(null,"No Stock available");
return;
}


ps.close();

}


catch(Exception ex)

{


JOptionPane.showMessageDialog(null,ex.getMessage());


}










}






void addListener()
{




tf1.addActionListener(e -> searchButton.doClick());

refreshButton.addActionListener(e ->
{

    t1.setVisible(true);
    sp1.setVisible(true);

    // Hide Search Table
    t2.setVisible(false);
    sp2.setVisible(false);

    tf1.setText("");

    ai.hideAddItem();

    totalProducts();
    totalStockQuantity();
    lowStockItem();
    outOfStockItem();

    tf1.setForeground(Color.GRAY);
    tf1.setText("Enter item Name");

    loadStock();

    JOptionPane.showMessageDialog(null, "Refresh Successfully");

});


db.buttons[0].addActionListener(e ->
{

    hideStock();

    ai.hideAddItem();

});

searchButton.addActionListener(e ->
{
    
// arrays empty aar2

for(int i=0;i<searchData.length;i++)
{

Arrays.fill(searchData[i],null);


}

searchStock();



});


addButton.addActionListener(e ->
{


JOptionPane.showMessageDialog(null, "Please update Stock directly from the table.");


});


tf1.setForeground(Color.GRAY);
tf1.setText("Enter item Name");

tf1.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{


if(tf1.getText().equals("Enter item Name"))
{

tf1.setText("");
tf1.setForeground(Color.BLACK);




}




}




@Override
public void focusLost(FocusEvent e)
{

if(tf1.getText().isEmpty())
{

tf1.setForeground(Color.GRAY);

tf1.setText("Enter item Name");


}




}






});






}



void loadStock()
{
    data = new Object[jd.countRow()][7];

    try(PreparedStatement ps = con.prepareStatement("SELECT * FROM sweets");
        ResultSet rs = ps.executeQuery())
    {
        int i = 0;

        while(rs.next() && i < data.length)
        {
            BigDecimal stock = rs.getBigDecimal("stock");
            if(stock == null)
            {
                stock = BigDecimal.ZERO;
            }

            data[i][0] = i + 1;
            data[i][1] = rs.getString("sweets_name");
            data[i][2] = rs.getString("category");
            // Keep the decimal part visible (e.g. 10.5, not 10).
            data[i][3] = stock.stripTrailingZeros().toPlainString();
            data[i][4] = rs.getString("unit");
            data[i][5] = rs.getInt("price");

            if(stock.compareTo(BigDecimal.TEN) > 0)
            {
                data[i][6] = "In Stock";
            }
            else if(stock.compareTo(BigDecimal.ZERO) > 0)
            {
                data[i][6] = "Low Stock";
            }
            else
            {
                data[i][6] = "Out Stock";
            }

            i++;
        }

        Object[][] tableData = data;
        model = new DefaultTableModel(tableData, columns)
        {
            @Override
            public boolean isCellEditable(int row, int column)
            {
                return column == 3 || column == 5;
            }
        };

        t1.setModel(model);
        setStatusColor(t1); // Status column index 6: In Stock / Low Stock / Out Stock

        model.addTableModelListener(e -> {
            try
            {
                updateStockUnitPrice();
            }
            catch(Exception ex)
            {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        });
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}

JTable t2;
JScrollPane sp2;


Object[][]  searchData=new Object[jd.countRow()][7];


    


void searchStock()
{
    String sweet = tf1.getText().trim();

    if(sweet.isEmpty() || sweet.equals("Enter item Name"))
    {
        JOptionPane.showMessageDialog(null, "Please Enter sweet name");
        return;
    }

    try(PreparedStatement ps = con.prepareStatement(
        "SELECT * FROM sweets WHERE sweets_name LIKE ?"))
    {
        ps.setString(1, "%" + sweet + "%");

        try(ResultSet rs = ps.executeQuery())
        {
            DefaultTableModel searchModel = new DefaultTableModel(columns, 0)
            {
                @Override
                public boolean isCellEditable(int row, int column)
                {
                    return false;
                }
            };

            int i = 0;

            while(rs.next())
            {
                BigDecimal stock = rs.getBigDecimal("stock");
                if(stock == null)
                {
                    stock = BigDecimal.ZERO;
                }

                Object[] row = new Object[7];
                row[0] = i + 1;
                row[1] = rs.getString("sweets_name");
                row[2] = rs.getString("category");
                // Keep the decimal part visible in search results too.
                row[3] = stock.stripTrailingZeros().toPlainString();
                row[4] = rs.getString("unit");
                row[5] = rs.getInt("price");

                if(stock.compareTo(BigDecimal.TEN) > 0)
                {
                    row[6] = "In Stock";
                }
                else if(stock.compareTo(BigDecimal.ZERO) > 0)
                {
                    row[6] = "Low Stock";
                }
                else
                {
                    row[6] = "Out Stock";
                }

                searchModel.addRow(row);
                i++;
            }

            t2.setModel(searchModel);
            setStatusColor(t2); // Apply same status colours to search results
            t1.setVisible(false);
            sp1.setVisible(false);
            t2.setVisible(true);
            sp2.setVisible(true);
            sp2.revalidate();
            sp2.repaint();

            if(i == 0)
            {
                JOptionPane.showMessageDialog(null, "Item not found");
            }
        }
    }
    catch(Exception ex)
    {
        JOptionPane.showMessageDialog(null, ex.getMessage());
    }
}

// Colour the Stock Status column in both the main and search tables.
// Stock.java has 7 columns, so Status is column index 6 (not index 8).
void setStatusColor(JTable table)
{
    if (table == null || table.getColumnModel().getColumnCount() <= 6)
    {
        return;
    }

    table.getColumnModel().getColumn(6).setCellRenderer(
        new DefaultTableCellRenderer()
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
                    table, value, isSelected, hasFocus, row, column
                );

                setOpaque(true);
                setHorizontalAlignment(SwingConstants.CENTER);
                setFont(new Font("Arial", Font.BOLD, 14));

                String status = value == null
                    ? ""
                    : value.toString().trim().replaceAll("\\s+", " ");

                // Preserve normal Swing selection colours when the row is selected.
                if (isSelected)
                {
                    return this;
                }

                if (status.equalsIgnoreCase("In Stock"))
                {
                    setBackground(new Color(210, 245, 210));
                    setForeground(new Color(0, 120, 50));
                }
                else if (status.equalsIgnoreCase("Low Stock"))
                {
                    setBackground(new Color(255, 235, 180));
                    setForeground(new Color(190, 105, 0));
                }
                else if (status.equalsIgnoreCase("Out Stock")
                        || status.equalsIgnoreCase("Out Of Stock")
                        || status.equalsIgnoreCase("Out of Stock"))
                {
                    setBackground(new Color(255, 210, 210));
                    setForeground(new Color(190, 0, 0));
                }
                else
                {
                    setBackground(Color.WHITE);
                    setForeground(Color.BLACK);
                }

                return this;
            }
        }
    );
}

// Returns true for units that must be counted as whole pieces.
boolean isPieceUnit(String unit)
{
    if(unit == null)
    {
        return false;
    }

    String normalized = unit.trim();

    return normalized.equalsIgnoreCase("pcs")
        || normalized.equalsIgnoreCase("pc")
        || normalized.equalsIgnoreCase("piece")
        || normalized.equalsIgnoreCase("pieces");
}

void updateStockUnitPrice() throws Exception
{
    int selectedColumn = t1.getSelectedColumn();
    int row = t1.getSelectedRow();

    if(row < 0)
    {
        return;
    }

    if(selectedColumn != 3 && selectedColumn != 5)
    {
        return;
    }

    String sweetName = t1.getValueAt(row, 1).toString();

    // ==============================
    // STOCK UPDATE - DECIMAL SUPPORT
    // PCS / PIECE UNITS MUST BE WHOLE NUMBERS
    // ==============================
    if(selectedColumn == 3)
    {
        BigDecimal stock;
        String unit = t1.getValueAt(row, 4).toString().trim();

        try
        {
            stock = new BigDecimal(
                model.getValueAt(row, 3).toString().trim()
            ).stripTrailingZeros();

            if(stock.scale() > 3)
            {
                throw new NumberFormatException();
            }
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(
                null,
                "Enter a valid stock quantity (e.g. 10 or 10.5)."
            );

            loadStock();
            return;
        }

        // Items sold as pieces cannot have fractional stock.
        if(isPieceUnit(unit) && stock.stripTrailingZeros().scale() > 0)
        {
            JOptionPane.showMessageDialog(
                null,
                "Unit is PCS. Please enter a whole number only (e.g. 10, not 10.5).",
                "Invalid Stock Quantity",
                JOptionPane.WARNING_MESSAGE
            );

            loadStock();
            return;
        }

        if(stock.compareTo(BigDecimal.ZERO) < 0)
        {
            JOptionPane.showMessageDialog(
                null,
                "Stock cannot be negative."
            );

            loadStock();
            return;
        }

        try(PreparedStatement ps = con.prepareStatement(
            "UPDATE sweets SET stock=? WHERE sweets_name=?"))
        {
            ps.setBigDecimal(1, stock);
            ps.setString(2, sweetName);

            int updated = ps.executeUpdate();

            if(updated > 0)
            {
                loadStock();

                totalProducts();
                totalStockQuantity();
                lowStockItem();
                outOfStockItem();

                JOptionPane.showMessageDialog(
                    null,
                    "Stock updated successfully."
                );
            }
            else
            {
                loadStock();

                JOptionPane.showMessageDialog(
                    null,
                    "Stock update failed."
                );
            }
        }
    }

    // ==============================
    // PRICE UPDATE
    // ==============================
    if(selectedColumn == 5)
    {
        int price;

        try
        {
            price = Integer.parseInt(
                model.getValueAt(row, 5).toString().trim()
            );
        }
        catch(NumberFormatException ex)
        {
            JOptionPane.showMessageDialog(
                null,
                "Please enter a valid Price."
            );

            loadStock();
            return;
        }

        if(price < 0)
        {
            JOptionPane.showMessageDialog(
                null,
                "Price cannot be negative."
            );

            loadStock();
            return;
        }

        try(PreparedStatement ps = con.prepareStatement(
            "UPDATE sweets SET price=? WHERE sweets_name=?"))
        {
            ps.setInt(1, price);
            ps.setString(2, sweetName);

            int updated = ps.executeUpdate();

            if(updated > 0)
            {
                loadStock();

                totalProducts();
                totalStockQuantity();
                lowStockItem();
                outOfStockItem();

                JOptionPane.showMessageDialog(
                    null,
                    "Price updated successfully."
                );
            }
            else
            {
                loadStock();

                JOptionPane.showMessageDialog(
                    null,
                    "Price update failed."
                );
            }
        }
    }
}
}