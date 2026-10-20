import javax.swing.*;
import java.sql.*;

public class JDBC
{

Connection con;

JDBC()
{

//connection stablished

try{

 con=DriverManager.getConnection("jdbc:mysql://localhost:3306/ssms",
    "root",
    "password");

}


catch(Exception e)
{

JOptionPane.showMessageDialog(null,e.getMessage());


}



}


public  int countRow()
{

int count=0;
try
{

PreparedStatement ps=con.prepareStatement("SELECT Count(*) FROM sweets");
ResultSet rs=ps.executeQuery();


if(rs.next())
{

 count= rs.getInt(1);

}



}


catch(Exception ex)
{

JOptionPane.showMessageDialog(null,ex.getMessage());

}

return count;

}




}