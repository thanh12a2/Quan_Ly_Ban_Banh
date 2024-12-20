/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package PtitstoreGUI;

import Dao.BillDao;
import Dao.BillDetailDao;
import Model.Bill;
import Model.BillDetail;
import com.mysql.cj.util.StringUtils;
import java.awt.Color;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.FileNotFoundException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


public class Payment extends javax.swing.JFrame {
String recieved;
  String totalPayment="";

    /**
     * Creates new form Payment
     */
double bHeight=100;
    public Payment(JLabel totalJLabel, JTable TBLCart) {
        initComponents();
        
        jTextFieldTotal.setEditable(false);
        jTextFieldTotal.setText(totalJLabel.getText());
       
        jTextFieldRefund.setEditable(false);
      
        Payment.this.setBackground(new Color(255,204,102));
         DefaultTableModel model=new DefaultTableModel();
          model=(DefaultTableModel) this.TBLPayment.getModel();
          
          //count table
           int rows = TBLCart.getRowCount();
           for (int row = 0; row < rows; row++) {
                  model.addRow(new Object[]{
                        TBLCart.getValueAt(row, 0),
                         TBLCart.getValueAt(row, 1),
                        TBLCart.getValueAt(row, 2),
                        TBLCart.getValueAt(row, 3),
                    });
                 
            }
    }
     public PageFormat getPageFormat(PrinterJob pj)
{
    
            PageFormat pf = pj.defaultPage();
            Paper paper = pf.getPaper();    

            double bodyHeight = bHeight;  
            double headerHeight = 5.0;                  
            double footerHeight = 5.0;        
            double width = cm_to_pp(20); 
            double height = cm_to_pp(headerHeight+bodyHeight+footerHeight); 
            paper.setSize(width, height);
            paper.setImageableArea(0,10,width,height - cm_to_pp(1));  

            pf.setOrientation(PageFormat.PORTRAIT);  
            pf.setPaper(paper);    

            return pf;
}
      protected static double cm_to_pp(double cm)
    {            
	        return toPPI(cm * 0.393600787);            
    }
 
        protected static double toPPI(double inch)
    {            
	        return inch * 72d;            
    }



public class BillPrintable implements Printable {
    
   
    
    
  public int print(Graphics graphics, PageFormat pageFormat,int pageIndex) 
  throws PrinterException 
  {    
      
     // int r= itemName.size();
      ImageIcon icon=new ImageIcon("F:\\project swing\\FoodTSK\\src\\img\\tsk.PNG"); 
      int result = NO_SUCH_PAGE;    
        if (pageIndex == 0) {                    
        
            Graphics2D g2d = (Graphics2D) graphics;                    
            double width = pageFormat.getImageableWidth();                               
            g2d.translate((int) pageFormat.getImageableX(),(int) pageFormat.getImageableY()); 



          //  FontMetrics metrics=g2d.getFontMetrics(new Font("Arial",Font.BOLD,7));
        
        try{
            int y=30;
            int yShift = 10;
            int headerRectHeight=15;
           // int headerRectHeighta=40;
          DecimalFormat formatter = new DecimalFormat("###,###,###");
          String RecievedString="0 VNĐ";
            if (!jTextFieldRecieved.getText().equals("")) {
                 RecievedString= formatter.format(Float.parseFloat(jTextFieldRecieved.getText()))+" VNĐ";
            }
         
             
                
            g2d.setFont(new Font("Monospaced",Font.PLAIN,9));
            g2d.drawImage(icon.getImage(), 50, 20, 100, 50, rootPane);y+=yShift+30;
            g2d.drawString("-------------------------------------",12,y);y+=yShift;
            g2d.drawString("         Ptit Store        ",12,y);y+=yShift;
            g2d.drawString("   No 00000 Address Line One ",12,y);y+=yShift;
            g2d.drawString("   Address Line 02 SRI LANKA ",12,y);y+=yShift;
            g2d.drawString("   www.facebook.com/PtitStore      ",12,y);y+=yShift;
            g2d.drawString("-------------------------------------",12,y);y+=headerRectHeight;

            g2d.drawString(" Item Name                  Price   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=headerRectHeight;
            int rows = TBLPayment.getRowCount();
            for(int row=0; row<rows; row++)
            {
            g2d.drawString(" "+TBLPayment.getValueAt(row, 1)+"                            ",10,y);y+=yShift;
            g2d.drawString("      "+TBLPayment.getValueAt(row, 2)+" * "+Float.parseFloat(String.valueOf(TBLPayment.getValueAt(row, 3)))/ Float.parseFloat(String.valueOf(TBLPayment.getValueAt(row, 2))) ,10,y); g2d.drawString(String.valueOf(TBLPayment.getValueAt(row, 3)),160,y);y+=yShift;

            }
          
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Total amount:               "+jTextFieldTotal.getText()+"   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Recieved      :             "+RecievedString+"   ",10,y);y+=yShift;
            g2d.drawString("-------------------------------------",10,y);y+=yShift;
            g2d.drawString(" Refund   :                 "+jTextFieldRefund.getText()+"   ",10,y);y+=yShift;
  
            g2d.drawString("*************************************",10,y);y+=yShift;
            g2d.drawString("       THANK YOU COME AGAIN            ",10,y);y+=yShift;
            g2d.drawString("*************************************",10,y);y+=yShift;
         
            
           

    }
    catch(Exception e){
    e.printStackTrace();
    }

              result = PAGE_EXISTS;    
          }    
          return result;    
      }
   }



    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        TBLPayment = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jTextFieldRefund = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jTextFieldTotal = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextFieldRecieved = new javax.swing.JTextField();
        btn_updatePayment1 = new keeptoo.KButton();
        btn_updatePayment2 = new keeptoo.KButton();
        btn_updatePayment3 = new keeptoo.KButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 204, 153));
        setUndecorated(true);

        TBLPayment.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                //        {null, null, null, null},
                //        {null, null, null, null},
                //        {null, null, null, null},
                //        {null, null, null, null}
            },
            new String [] {
                "id", "Name", "Quantity", "Price"
            }
        ));
        jScrollPane1.setViewportView(TBLPayment);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel1.setText("Total Price :");

        jTextFieldRefund.setText("0 VND");
        jTextFieldRefund.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRefundActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Received : ");

        jTextFieldTotal.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldTotalActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Refund : ");

        jTextFieldRecieved.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextFieldRecievedActionPerformed(evt);
            }
        });
        jTextFieldRecieved.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextFieldRecievedKeyPressed(evt);
            }
        });

        btn_updatePayment1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_updatePayment1.setText("Caculator");
        btn_updatePayment1.setkBackGroundColor(new java.awt.Color(255, 255, 255));
        btn_updatePayment1.setkEndColor(new java.awt.Color(255, 153, 153));
        btn_updatePayment1.setkForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment1.setkHoverColor(new java.awt.Color(0, 0, 0));
        btn_updatePayment1.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment1.setkHoverStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment1.setkStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatePayment1ActionPerformed(evt);
            }
        });

        btn_updatePayment2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_updatePayment2.setText("Print");
        btn_updatePayment2.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_updatePayment2.setkBackGroundColor(new java.awt.Color(255, 255, 255));
        btn_updatePayment2.setkEndColor(new java.awt.Color(255, 153, 153));
        btn_updatePayment2.setkForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment2.setkHoverColor(new java.awt.Color(0, 0, 0));
        btn_updatePayment2.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment2.setkHoverStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment2.setkStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatePayment2ActionPerformed(evt);
            }
        });

        btn_updatePayment3.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));
        btn_updatePayment3.setText("Back");
        btn_updatePayment3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        btn_updatePayment3.setkBackGroundColor(new java.awt.Color(255, 255, 255));
        btn_updatePayment3.setkEndColor(new java.awt.Color(255, 153, 153));
        btn_updatePayment3.setkForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment3.setkHoverColor(new java.awt.Color(0, 0, 0));
        btn_updatePayment3.setkHoverForeGround(new java.awt.Color(0, 0, 0));
        btn_updatePayment3.setkHoverStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment3.setkStartColor(new java.awt.Color(255, 255, 204));
        btn_updatePayment3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_updatePayment3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 429, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel1)
                                    .addComponent(jLabel3))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldTotal)
                                    .addComponent(jTextFieldRefund)
                                    .addComponent(jTextFieldRecieved)))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 19, Short.MAX_VALUE)
                                .addComponent(btn_updatePayment3, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(btn_updatePayment2, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_updatePayment1, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(70, 70, 70))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addGap(43, 43, 43)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2)
                    .addComponent(jTextFieldRecieved, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_updatePayment1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTextFieldRefund, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_updatePayment2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_updatePayment3, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jTextFieldRefundActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRefundActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRefundActionPerformed

    private void jTextFieldTotalActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldTotalActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldTotalActionPerformed

    private void jTextFieldRecievedActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextFieldRecievedActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextFieldRecievedActionPerformed

    private void btn_updatePayment1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePayment1ActionPerformed
       
         String[] totalCurrent=jTextFieldTotal.getText().split("\\,");
        String totalCurrent1="";
        for (int i = 0; i < totalCurrent.length; i++) {
            totalCurrent1+=totalCurrent[i];
        }
        String totalCurrent2=totalCurrent1;
        String[] totalCurrent3=totalCurrent2.split("\\ VNĐ");
      
        for (int i = 0; i < totalCurrent3.length; i++) {
            totalPayment+=totalCurrent3[i];
        }
        if (Float.compare(Float.parseFloat(jTextFieldRecieved.getText()),Float.parseFloat(totalPayment))>0) {
             DecimalFormat formatter = new DecimalFormat("###,###,###");
             jTextFieldRefund.setText(formatter.format(Float.parseFloat(jTextFieldRecieved.getText())-Float.parseFloat(totalPayment))+" VNĐ");

        }
    }//GEN-LAST:event_btn_updatePayment1ActionPerformed

    private void btn_updatePayment2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePayment2ActionPerformed
        // TODO add your handling code here:
      //  bHeight = Double.valueOf(itemName.size());
        //JOptionPane.showMessageDialog(rootPane, bHeight);
        
        PrinterJob pj = PrinterJob.getPrinterJob();        
        pj.setPrintable(new BillPrintable(),getPageFormat(pj));
        try {
            BillDao bd=new BillDao();
          String[] time=String.valueOf(java.time.LocalTime.now()).split("\\.");
          String [] time1=time[0].split("\\:");
          String timeNow=time1[0]+":"+time1[1];
            Bill b=new Bill(null,timeNow,String.valueOf(java.time.LocalDate.now()),Float.parseFloat(totalPayment));
            try {
                if (bd.insertBill(b)==true) {
                    try {
                        int idMax=bd.getBillMax();
                       int rows = TBLPayment.getRowCount();
                        BillDetailDao bdd=new BillDetailDao();
                        for (int row = 0; row<rows; row++) {
                            Integer product=Integer.parseInt(String.valueOf(TBLPayment.getValueAt(row, 0)) ) ;
                            Integer quantity=Integer.parseInt(String.valueOf(TBLPayment.getValueAt(row, 2)) ) ;
                            Float price=Float.parseFloat(String.valueOf(TBLPayment.getValueAt(row, 3))) ;
                            BillDetail bd1=new BillDetail(null,product,idMax,quantity,price);
                           bdd.insertBillD(bd1);
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
                    }
                    pj.print();
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Payment.class.getName()).log(Level.SEVERE, null, ex);
            }
            
          
        }
         catch (PrinterException ex) {
                 ex.printStackTrace();
        }
    }//GEN-LAST:event_btn_updatePayment2ActionPerformed

    private void jTextFieldRecievedKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldRecievedKeyPressed
        char c=evt.getKeyChar();
        if (Character.isLetter(c)) {
            jTextFieldRecieved.setEditable(false);
        }else{
         jTextFieldRecieved.setEditable(true);
        }
       
    }//GEN-LAST:event_jTextFieldRecievedKeyPressed

    private void btn_updatePayment3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_updatePayment3ActionPerformed
        // TODO add your handling code here:
        this.dispose();
    }//GEN-LAST:event_btn_updatePayment3ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Payment.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new Payment().setVisible(true);
//            }
//        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable TBLPayment;
    private keeptoo.KButton btn_updatePayment1;
    private keeptoo.KButton btn_updatePayment2;
    private keeptoo.KButton btn_updatePayment3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextFieldRecieved;
    private javax.swing.JTextField jTextFieldRefund;
    private javax.swing.JTextField jTextFieldTotal;
    // End of variables declaration//GEN-END:variables
}
