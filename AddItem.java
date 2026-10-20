import javax.swing.*;
import java.awt.*;
import java.net.URL;
import java.io.File;
import java.sql.*;
import java.awt.event.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Locale;
import java.util.UUID;
import java.math.BigDecimal;


public class AddItem
{



boolean addItemCreated = false;



JButton buttons[];
JPanel miniHeaderPanel;
JPanel cardsPanel;
JPanel mainPanel;
JFrame f;
JScrollPane sp;

JPanel subPanel1;
JPanel subPanel2;

JLabel homeIconLabel;
JLabel mL1;
JLabel mL2;

JLabel s7Label;
JLabel s1Label;
JLabel s2Label;
JLabel s3Label;
JLabel s4Label;
JLabel s5Label;
JLabel s6Label;


JTextField tf1;
JTextField tf2;
JTextField tf3;
JTextField tf4;

JTextArea ta;
JComboBox<String> categoryCb;
JComboBox<String> unitCb;

JButton addButton;
JButton clearButton;
JButton cancelButton;
JButton searchButton;


JLabel sweetImgLabel;
JLabel sweetIconLabel;
JLabel noImageSelectedLabel;
JButton chooseButton;
JLabel infoLabel;

JPanel footerPanel;

DashBoard x;





// Product images are stored outside the JAR, beside the running application,
// and only a relative path is saved in MySQL. This makes paths portable when
// the application folder and product-images folder are copied together.
private static final String PRODUCT_IMAGE_FOLDER = "product-images";

private File getApplicationDirectory() throws Exception
{
    URL codeLocation = AddItem.class.getProtectionDomain()
            .getCodeSource().getLocation();
    File location = new File(codeLocation.toURI());

    // When running from a JAR, use the folder containing the JAR.
    // When running compiled classes, use the classes output folder.
    return location.isFile() ? location.getParentFile() : location;
}

private File getProductImagesDirectory() throws Exception
{
    File imageDirectory = new File(
            getApplicationDirectory(), PRODUCT_IMAGE_FOLDER);

    if (!imageDirectory.exists() && !imageDirectory.mkdirs())
    {
        throw new java.io.IOException(
                "Could not create product image folder: "
                + imageDirectory.getAbsolutePath());
    }

    if (!imageDirectory.isDirectory() || !imageDirectory.canWrite())
    {
        throw new java.io.IOException(
                "Product image folder is not writable: "
                + imageDirectory.getAbsolutePath());
    }

    return imageDirectory;
}

private String copyImageToPortableStorage(File sourceFile) throws Exception
{
    String originalName = sourceFile.getName();
    int dot = originalName.lastIndexOf('.');

    if (dot < 0)
    {
        throw new java.io.IOException("Image file has no extension.");
    }

    String extension = originalName.substring(dot).toLowerCase(Locale.ROOT);
    String newName = UUID.randomUUID().toString() + extension;
    File destination = new File(getProductImagesDirectory(), newName);

    Files.copy(sourceFile.toPath(), destination.toPath(),
            StandardCopyOption.REPLACE_EXISTING);

    // IMPORTANT: store a relative path in the database, never the user's
    // machine-specific absolute path such as C:\\Users\\...\\image.png.
    return PRODUCT_IMAGE_FOLDER + "/" + newName;
}

private File resolveStoredImagePath(String storedPath) throws Exception
{
    if (storedPath == null || storedPath.trim().isEmpty())
    {
        return null;
    }

    File savedPath = new File(storedPath);

    // Backward compatibility: old database rows may contain absolute paths.
    if (savedPath.isAbsolute())
    {
        return savedPath;
    }

    String normalizedPath = storedPath
            .replace('/', File.separatorChar)
            .replace('\\', File.separatorChar);

    File relativeToApplication = new File(
            getApplicationDirectory(), normalizedPath);

    if (relativeToApplication.exists())
    {
        return relativeToApplication;
    }

    // Also support older relative paths saved from the working directory.
    File relativeToWorkingDirectory = new File(normalizedPath);
    if (relativeToWorkingDirectory.exists())
    {
        return relativeToWorkingDirectory;
    }

    return relativeToApplication;
}

private void showDefaultImage(String message)
{
    URL defaultUrl = getClass().getResource("imgs/def.png");

    sweetIconLabel.setIcon(
            defaultUrl == null ? new ImageIcon()
                    : resizeImage(defaultUrl, 440, 360));

    noImageSelectedLabel.setText(message);
    sweetIconLabel.revalidate();
    sweetIconLabel.repaint();
}

private boolean isPositiveInteger(String value)
{
    if (value == null || !value.matches("\\d+"))
    {
        return false;
    }

    try
    {
        return Integer.parseInt(value) > 0;
    }
    catch (NumberFormatException ex)
    {
        return false;
    }
}

// Allows fractional stock for weight/volume units (e.g. 0.5 kg).
// Piece-based units must remain whole numbers.
private boolean isValidStock(String value, String unit)
{
    if (value == null || !value.matches("\\d+(\\.\\d{1,3})?"))
    {
        return false;
    }

    try
    {
        BigDecimal stock = new BigDecimal(value).stripTrailingZeros();

        if (stock.compareTo(BigDecimal.ZERO) <= 0)
        {
            return false;
        }

        String normalizedUnit = unit == null
                ? "" : unit.trim().toLowerCase(Locale.ROOT);

        if (normalizedUnit.equals("pcs")
                || normalizedUnit.equals("box")
                || normalizedUnit.equals("packet"))
        {
            return stock.scale() <= 0;
        }

        return stock.scale() <= 3;
    }
    catch (NumberFormatException ex)
    {
        return false;
    }
}

public JPanel createAddItem(
    JButton buttons[],
    JScrollPane sp,
    JFrame f,
    JPanel miniHeaderPanel,
    JPanel cardsPanel,
    DashBoard x)
{
    this.x = x;

    this.miniHeaderPanel = miniHeaderPanel;
    this.cardsPanel = cardsPanel;
    this.f = f;
    this.sp = sp;
    this.buttons = buttons;

    if(addItemCreated)
    {
        mainPanel.setVisible(true);

        miniHeaderPanel.setVisible(false);
        cardsPanel.setVisible(false);
        sp.setVisible(false);

        return mainPanel;
    }

    addItemCreated = true;

    setVisible();
    createComponent();

    mainPanel.setLayout(null);
    subPanel1.setLayout(null);
    subPanel2.setLayout(null);

    setBounds();
    addComponent();

    setIconOnButtons();
    addListener();

    setColors();
    setFont();
return mainPanel;
}
void setVisible()
{

miniHeaderPanel.setVisible(false);
cardsPanel.setVisible(false);
sp.setVisible(false);





}


void createComponent()
{

System.out.println("UI create Add item");

mainPanel=new JPanel();
subPanel1=new JPanel();
subPanel2=new JPanel();
homeIconLabel=new JLabel(resizeImage(getClass().getResource("imgs/home2.png"),70,60));
mL1=new JLabel("Add New Sweet / Product");
mL2=new JLabel("Fill the detail below to add a new sweet/product in your shop");

s1Label=new JLabel("Sweet Name*");
s2Label=new JLabel("Category*");
s3Label=new JLabel("Stock*");
s4Label=new JLabel("Price*");
s5Label=new JLabel("Description");
s6Label=new JLabel("Unit*");
s7Label=new JLabel("Search Sweet");


tf1=new JTextField();
tf2=new JTextField();
tf3=new JTextField();
tf4=new JTextField();

ta=new JTextArea();
categoryCb=new JComboBox<>(new String[]{
    "Enter Category",
    "Sweet",
    "Namkeen",
    "Bakery",
    "Other"
});



unitCb=new JComboBox<>(new String[]{
 "Enter Unit",
    "kg",
    "gram",
    "pcs",
    "litre",
    "ml",
    "box",
    "packet"


});


addButton=new JButton("ADD ITEM");
clearButton=new JButton("CLEAR");
cancelButton=new JButton("CANCEL");
searchButton=new JButton("Search");


sweetImgLabel=new JLabel("Sweet Image");
sweetIconLabel=new JLabel(resizeImage(getClass().getResource("imgs/def.png"),440,360));

noImageSelectedLabel=new JLabel("NO Image Selected");
chooseButton=new JButton("Choose Image");
infoLabel=new JLabel("Recommended size: 400x400px (JPG, PNG)");

}

void setBounds()
{

mainPanel.setBounds(295,200,1600,740);
subPanel1.setBounds(20,110,1000,530);
subPanel2.setBounds(1040,110,530,530);
homeIconLabel.setBounds(30,20,70,60);
mL1.setBounds(150,20,500,30);
mL2.setBounds(150,60,700,30);

s1Label.setBounds(30,90,150,30);
s2Label.setBounds(30,170,150,30);
s3Label.setBounds(30,250,150,30);
s4Label.setBounds(30,330,150,30);
s5Label.setBounds(30,410,150,30);
s6Label.setBounds(620,250,90,30);
s7Label.setBounds(30,30,150,30);



tf1.setBounds(200,90,700,40);
tf2.setBounds(200,250,330,40);
tf3.setBounds(200,330,700,40);
tf4.setBounds(200,20,520,40);


ta.setBounds(200,410,700,80);
categoryCb.setBounds(200,170,700,40);
unitCb.setBounds(730,250,175,40);

addButton.setBounds(300,670,200,60);
clearButton.setBounds(550,670,200,60);
cancelButton.setBounds(800,670,200,60);
searchButton.setBounds(740,20,165,40);



sweetImgLabel.setBounds(30,40,150,30);
sweetIconLabel.setBounds(30,90,440,360);

noImageSelectedLabel.setBounds(145,265,300,30);

chooseButton.setBounds(125,300,200,42);
URL chooseIconUrl = getClass().getResource("imgs/upArrow.gif");
chooseButton.setIcon(chooseIconUrl == null ? new ImageIcon() : new ImageIcon(chooseIconUrl));
chooseButton.setHorizontalAlignment(SwingConstants.LEFT);
chooseButton.setIconTextGap(25);
infoLabel.setBounds(50,465,400,30);

}

void addComponent()
{

f.add(mainPanel);
mainPanel.add(subPanel1);
mainPanel.add(subPanel2);
mainPanel.add(homeIconLabel);

mainPanel.add(mL1);
mainPanel.add(mL2);
subPanel1.add(s1Label);
subPanel1.add(s2Label);
subPanel1.add(s3Label);
subPanel1.add(s4Label);
subPanel1.add(s5Label);
subPanel1.add(s6Label);
subPanel1.add(s7Label);


subPanel1.add(tf1);
subPanel1.add(tf2);
subPanel1.add(tf3);
subPanel1.add(tf4);

subPanel1.add(ta);
subPanel1.add(categoryCb);
subPanel1.add(unitCb);

mainPanel.add(addButton);
mainPanel.add(clearButton);
mainPanel.add(cancelButton);
subPanel1.add(searchButton);


subPanel2.add(sweetImgLabel);
subPanel2.add(sweetIconLabel);
sweetIconLabel.add(noImageSelectedLabel);
sweetIconLabel.add(chooseButton);
subPanel2.add(infoLabel);










}


void setColors()
{

mainPanel.setBackground(Color.WHITE);
mL1.setForeground(new Color(91, 55, 28));
s1Label.setForeground(new Color(91, 55, 28));
s2Label.setForeground(new Color(91, 55, 28));
s3Label.setForeground(new Color(91, 55, 28));
s4Label.setForeground(new Color(91, 55, 28));
s5Label.setForeground(new Color(91, 55, 28));
s6Label.setForeground(new Color(91, 55, 28));
s7Label.setForeground(new Color(91, 55, 28));

sweetImgLabel.setForeground(new Color(91, 55, 28));
chooseButton.setBackground(Color.WHITE);
chooseButton.setForeground(Color.BLACK);
subPanel1.setBackground(Color.WHITE);
subPanel2.setBackground(Color.WHITE);

// Light Warm Beige Border
Color borderColor = new Color(222, 202, 178);

subPanel1.setBorder(
    BorderFactory.createLineBorder(borderColor, 1)
);

subPanel2.setBorder(
    BorderFactory.createLineBorder(borderColor, 1)
);



}





ImageIcon resizeImage(URL url, int w, int h)
{
    if (url == null)
    {
        return new ImageIcon();
    }

    ImageIcon icon = new ImageIcon(url);
    Image img = icon.getImage();
    Image resized = img.getScaledInstance(w, h, Image.SCALE_SMOOTH);
    return new ImageIcon(resized);
}

void setFont()
{

mL1.setFont(new Font("Arial",Font.BOLD,25));
mL2.setFont(new Font("Arial",Font.PLAIN,20));
s1Label.setFont(new Font("Arial",Font.PLAIN,20));
s2Label.setFont(new Font("Arial",Font.PLAIN,20));
s3Label.setFont(new Font("Arial",Font.PLAIN,20));
s4Label.setFont(new Font("Arial",Font.PLAIN,20));
s5Label.setFont(new Font("Arial",Font.PLAIN,20));
s6Label.setFont(new Font("Arial",Font.PLAIN,20));
s7Label.setFont(new Font("Arial",Font.PLAIN,20));



tf1.setFont(new Font("Arial",Font.PLAIN,20));
tf2.setFont(new Font("Arial",Font.PLAIN,20));
tf3.setFont(new Font("Arial",Font.PLAIN,20));
tf4.setFont(new Font("Arial",Font.PLAIN,20));

ta.setFont(new Font("Arial",Font.PLAIN,20));
sweetImgLabel.setFont(new Font("Arial",Font.BOLD,25));
noImageSelectedLabel.setFont(new Font("Arial",Font.PLAIN,17));
infoLabel.setFont(new Font("Arial",Font.PLAIN,20));

searchButton.setFont(new Font("Arial",Font.PLAIN,18));





}


void setIconOnButtons()
{

addButton.setIcon(resizeImage(getClass().getResource("imgs/additem.png"),40,40));
addButton.setHorizontalAlignment(SwingConstants.LEFT);
addButton.setIconTextGap(25);


cancelButton.setIcon(resizeImage(getClass().getResource("imgs/cancel.png"),35,35));
clearButton.setHorizontalAlignment(SwingConstants.LEFT);
clearButton.setIconTextGap(25);


clearButton.setIcon(resizeImage(getClass().getResource("imgs/clear.png"),35,35));
cancelButton.setHorizontalAlignment(SwingConstants.LEFT);
cancelButton.setIconTextGap(25);







}


String path;
URL url;


void addListener()
{



chooseButton.addActionListener(e ->  getImageAndSet());
clearButton.addActionListener(e ->  clearInformation());
cancelButton.addActionListener(e ->  cancel());
buttons[0].addActionListener(e ->  cancel());




addButton.addActionListener(e->{

try{
addDataOnMySql();
}

 


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());
return;

}



});




searchButton.addActionListener(e ->{  



try{
searchSweet();

}

 


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());
return;

}


});



tf1.setForeground(Color.GRAY);
tf1.setText("Enter sweets name :");
tf1.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(tf1.getText().equals("Enter sweets name :"))
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
tf1.setText("Enter sweets name :");


}




}






});







tf2.setForeground(Color.GRAY);
tf2.setText("Enter Stock Amount :");
tf2.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(tf2.getText().equals("Enter Stock Amount :"))
{

tf2.setText("");
tf2.setForeground(Color.BLACK);

}


}


@Override
public void focusLost(FocusEvent e)
{

if(tf2.getText().isEmpty())
{

tf2.setForeground(Color.GRAY);
tf2.setText("Enter Stock Amount :");


}




}






});








tf3.setForeground(Color.GRAY);
tf3.setText("Enter sweets price :");
tf3.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(tf3.getText().equals("Enter sweets price :"))
{

tf3.setText("");
tf3.setForeground(Color.BLACK);

}


}


@Override
public void focusLost(FocusEvent e)
{

if(tf3.getText().isEmpty())
{

tf3.setForeground(Color.GRAY);
tf3.setText("Enter sweets price :");


}




}






});







tf4.setForeground(Color.GRAY);
tf4.setText("Enter sweets name for search Item:");
tf4.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(tf4.getText().equals("Enter sweets name for search Item:"))
{

tf4.setText("");
tf4.setForeground(Color.BLACK);

}


}


@Override
public void focusLost(FocusEvent e)
{

if(tf4.getText().isEmpty())
{

tf4.setForeground(Color.GRAY);
tf4.setText("Enter sweets name for search Item:");


}




}






});









ta.setForeground(Color.GRAY);
ta.setText("Optional  description :");
ta.addFocusListener(new FocusAdapter()
{

@Override
public void focusGained(FocusEvent e)
{

if(ta.getText().equals("Optional  description :"))
{

ta.setText("");
ta.setForeground(Color.BLACK);

}


}


@Override
public void focusLost(FocusEvent e)
{

if(ta.getText().isEmpty())
{

ta.setForeground(Color.GRAY);
ta.setText("Optional  description :");


}




}






});




}
void getImageAndSet()
{
    JFileChooser fc = new JFileChooser();
    fc.setDialogTitle("Choose Sweet Image");
    fc.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Image Files (*.png, *.jpg, *.jpeg)", "png", "jpg", "jpeg"));

    int result = fc.showOpenDialog(f);
    if (result != JFileChooser.APPROVE_OPTION)
    {
        return; // User cancelled the chooser; keep the current selection.
    }

    File selectedFile = fc.getSelectedFile();

    if (selectedFile == null || !selectedFile.exists() || !selectedFile.isFile())
    {
        JOptionPane.showMessageDialog(f, "Selected file does not exist.",
                "Invalid File", JOptionPane.ERROR_MESSAGE);
        return;
    }

    if (!selectedFile.canRead())
    {
        JOptionPane.showMessageDialog(f, "Cannot read the selected image.",
                "File Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    String fileName = selectedFile.getName().toLowerCase(Locale.ROOT);
    if (!(fileName.endsWith(".png") || fileName.endsWith(".jpg")
            || fileName.endsWith(".jpeg")))
    {
        JOptionPane.showMessageDialog(f,
                "Please select a PNG, JPG or JPEG image.",
                "Invalid Image Format", JOptionPane.ERROR_MESSAGE);
        return;
    }

    try
    {
        // Extension alone is not enough; verify that Java can decode the file.
        java.awt.image.BufferedImage decodedImage =
                javax.imageio.ImageIO.read(selectedFile);

        if (decodedImage == null)
        {
            JOptionPane.showMessageDialog(f,
                    "The selected file is not a valid image.",
                    "Invalid Image", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Copy image next to the application, then store a relative path in DB.
        String portablePath = copyImageToPortableStorage(selectedFile);
        File storedFile = resolveStoredImagePath(portablePath);
        URL storedUrl = storedFile.toURI().toURL();

        path = portablePath;
        url = storedUrl;
        sweetIconLabel.setIcon(resizeImage(url, 440, 180));
        noImageSelectedLabel.setText("Image selected successfully.");
        sweetIconLabel.revalidate();
        sweetIconLabel.repaint();
    }
    catch (Exception ex)
    {
        JOptionPane.showMessageDialog(f,
                "Unable to load or copy image:\n" + ex.getMessage(),
                "Image Error", JOptionPane.ERROR_MESSAGE);
    }
}
void clearInformation()
{
    path = null;
    url = null;

    tf1.setText("Enter sweets name :");
    tf1.setForeground(Color.GRAY);
    tf2.setText("Enter Stock Amount :");
    tf2.setForeground(Color.GRAY);
    tf3.setText("Enter sweets price :");
    tf3.setForeground(Color.GRAY);
    tf4.setText("Enter sweets name for search Item:");
    tf4.setForeground(Color.GRAY);
    ta.setText("Optional  description :");
    ta.setForeground(Color.GRAY);

    categoryCb.setSelectedIndex(0);
    unitCb.setSelectedIndex(0);
    showDefaultImage("NO Image Selected");
}


void cancel()
{

x.refreshData();
mainPanel.setVisible(false);

miniHeaderPanel.setVisible(true);
cardsPanel.setVisible(true);
sp.setVisible(true);

}


Connection con;
void addDataOnMySql() throws Exception
{
    String sweetName = tf1.getText() == null ? "" : tf1.getText().trim();
    String stockText = tf2.getText() == null ? "" : tf2.getText().trim();
    String priceText = tf3.getText() == null ? "" : tf3.getText().trim();
    String description = ta.getText() == null ? "" : ta.getText().trim();
    String category = (String) categoryCb.getSelectedItem();
    String unit = (String) unitCb.getSelectedItem();

    if (sweetName.isEmpty() || sweetName.equalsIgnoreCase("Enter sweets name :"))
    {
        JOptionPane.showMessageDialog(f, "Please enter sweet name.");
        tf1.requestFocusInWindow();
        return;
    }

    if (sweetName.length() > 100)
    {
        JOptionPane.showMessageDialog(f, "Sweet name cannot exceed 100 characters.");
        tf1.requestFocusInWindow();
        return;
    }

    if (categoryCb.getSelectedIndex() <= 0 || category == null)
    {
        JOptionPane.showMessageDialog(f, "Please select category.");
        categoryCb.requestFocusInWindow();
        return;
    }

    if (stockText.isEmpty() || stockText.equalsIgnoreCase("Enter Stock Amount :"))
    {
        JOptionPane.showMessageDialog(f, "Please enter stock amount.");
        tf2.requestFocusInWindow();
        return;
    }

    if (unitCb.getSelectedIndex() <= 0 || unit == null)
    {
        JOptionPane.showMessageDialog(f, "Please select unit.");
        unitCb.requestFocusInWindow();
        return;
    }

    if (!isValidStock(stockText, unit))
    {
        String message = ("pcs".equalsIgnoreCase(unit)
                || "box".equalsIgnoreCase(unit)
                || "packet".equalsIgnoreCase(unit))
                ? "Stock must be a whole number greater than zero for " + unit + "."
                : "Enter a stock amount greater than zero, e.g. 0.5 or 1.250 (up to 3 decimal places).";

        JOptionPane.showMessageDialog(f, message);
        tf2.requestFocusInWindow();
        return;
    }

    if (priceText.isEmpty() || priceText.equalsIgnoreCase("Enter sweets price :"))
    {
        JOptionPane.showMessageDialog(f, "Please enter price.");
        tf3.requestFocusInWindow();
        return;
    }

    if (!isPositiveInteger(priceText))
    {
        JOptionPane.showMessageDialog(f,
                "Price must be a whole number greater than zero.");
        tf3.requestFocusInWindow();
        return;
    }

    BigDecimal stock;
    int price;
    try
    {
        stock = new BigDecimal(stockText).stripTrailingZeros();
        price = Integer.parseInt(priceText);
    }
    catch (NumberFormatException ex)
    {
        JOptionPane.showMessageDialog(f,
                "Stock or price is invalid or too large. Please check the values.");
        return;
    }

    if (path == null || path.trim().isEmpty())
    {
        JOptionPane.showMessageDialog(f, "Please select a sweet image.");
        return;
    }

    File imageFile = resolveStoredImagePath(path);
    if (imageFile == null || !imageFile.isFile() || !imageFile.canRead())
    {
        JOptionPane.showMessageDialog(f,
                "Selected image is unavailable. Please select it again.");
        path = null;
        url = null;
        return;
    }

    JDBC jd = new JDBC();
    con = jd.con;
    if (con == null || con.isClosed())
    {
        JOptionPane.showMessageDialog(f,
                "Database connection is not available.",
                "Database Error", JOptionPane.ERROR_MESSAGE);
        return;
    }

    // Prevent ordinary duplicate-name inserts (database UNIQUE constraint is stronger).
    String checkQuery =
            "SELECT 1 FROM sweets WHERE LOWER(TRIM(sweets_name)) = LOWER(?) LIMIT 1";
    try (PreparedStatement checkPs = con.prepareStatement(checkQuery))
    {
        checkPs.setString(1, sweetName);
        try (ResultSet rs = checkPs.executeQuery())
        {
            if (rs.next())
            {
                JOptionPane.showMessageDialog(f, "This sweet already exists.");
                tf1.requestFocusInWindow();
                return;
            }
        }
    }

    if (description.equalsIgnoreCase("Optional  description :"))
    {
        description = "";
    }

    String query = "INSERT INTO sweets "
            + "(sweets_name, category, stock, unit, price, description, image_path) "
            + "VALUES (?, ?, ?, ?, ?, ?, ?)";

    try (PreparedStatement ps = con.prepareStatement(query))
    {
        ps.setString(1, sweetName);
        ps.setString(2, category);
        ps.setBigDecimal(3, stock);
        ps.setString(4, unit);
        ps.setInt(5, price);
        ps.setString(6, description);
        ps.setString(7, path); // relative path such as product-images/uuid.png

        if (ps.executeUpdate() > 0)
        {
            JOptionPane.showMessageDialog(f, "Sweet added successfully!");
            clearInformation();
            if (x != null)
            {
                x.refreshData();
            }
        }
    }
    catch (SQLException ex)
    {
        JOptionPane.showMessageDialog(f,
                "Unable to save sweet:\n" + ex.getMessage(),
                "Database Error", JOptionPane.ERROR_MESSAGE);
    }
}
void searchSweet() throws Exception
{
    String item = tf4.getText() == null ? "" : tf4.getText().trim();

    if (item.isEmpty() || item.equalsIgnoreCase("Enter sweets name for search Item:"))
    {
        JOptionPane.showMessageDialog(f, "Please enter sweet name to search.");
        tf4.requestFocusInWindow();
        return;
    }

    if (item.length() > 100)
    {
        JOptionPane.showMessageDialog(f, "Sweet name cannot exceed 100 characters.");
        tf4.requestFocusInWindow();
        return;
    }

    JDBC jd = new JDBC();
    this.con = jd.con;
    if (con == null || con.isClosed())
    {
        JOptionPane.showMessageDialog(f, "Database connection is not available.");
        return;
    }

    String query = "SELECT * FROM sweets "
            + "WHERE LOWER(TRIM(sweets_name)) = LOWER(?)";

    try (PreparedStatement ps = con.prepareStatement(query))
    {
        ps.setString(1, item);
        try (ResultSet rs = ps.executeQuery())
        {
            if (!rs.next())
            {
                JOptionPane.showMessageDialog(f,
                        "Sweet not found. Please check the name.");
                tf4.requestFocusInWindow();
                return;
            }

            String sweetName = rs.getString(1);
            String category = rs.getString(2);
            String stock = rs.getString(3);
            String unit = rs.getString(4);
            String price = rs.getString(5);
            String description = rs.getString(6);
            String imagePath = rs.getString(7);

            tf1.setText(sweetName);
            tf1.setForeground(Color.BLACK);
            categoryCb.setSelectedItem(category);
            tf2.setText(stock);
            tf2.setForeground(Color.BLACK);
            unitCb.setSelectedItem(unit);
            tf3.setText(price);
            tf3.setForeground(Color.BLACK);
            ta.setText(description == null ? "" : description);
            ta.setForeground(Color.BLACK);

            path = null;
            url = null;
            boolean imageLoaded = false;

            try
            {
                File imageFile = resolveStoredImagePath(imagePath);
                if (imageFile != null && imageFile.isFile() && imageFile.canRead())
                {
                    java.awt.image.BufferedImage decodedImage =
                            javax.imageio.ImageIO.read(imageFile);
                    if (decodedImage != null)
                    {
                        url = imageFile.toURI().toURL();
                        path = imagePath; // preserve relative DB path for portability
                        sweetIconLabel.setIcon(resizeImage(url, 440, 180));
                        noImageSelectedLabel.setText("Image loaded successfully.");
                        imageLoaded = true;
                    }
                }
            }
            catch (Exception imageEx)
            {
                System.out.println("Image loading error: " + imageEx.getMessage());
            }

            if (!imageLoaded)
            {
                showDefaultImage("Image unavailable. Please choose an image.");
            }
            else
            {
                sweetIconLabel.revalidate();
                sweetIconLabel.repaint();
            }
        }
    }
}
public void hideAddItem()
{
    if (mainPanel != null)
    {
        mainPanel.setVisible(false);
    }

    if (f != null)
    {
        f.revalidate();
        f.repaint();
    }
}




}



