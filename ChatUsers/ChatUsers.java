import java.io.*;
import java.net.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.net.NetworkInterface;
import java.net.InetAddress;
import static java.lang.System.out;

public final class  ChatUsers extends JFrame implements ActionListener {
    String username;
    PrintWriter pw;
    BufferedReader br;
    JTextArea  chatmsg;
    JTextField chatip;
    JButton send,exit,showusers,changeuser;
    Socket chatusers;
    
    public ChatUsers(String uname, /*String Password ,*/ String servername) throws Exception {
        super(uname); 
        this.username = uname;
        chatusers  = new Socket(servername,2019);
        br = new BufferedReader( new InputStreamReader( chatusers.getInputStream()) ) ;
        pw = new PrintWriter(chatusers.getOutputStream(),true);
        pw.println(uname);
        final NetworkInterface netInf = NetworkInterface.getNetworkInterfaces().nextElement();
        final byte[] mac = netInf.getHardwareAddress();
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < mac.length; i++) {
                sb.append(String.format("%02X%s", mac[i], (i < mac.length - 1) ? "-" : ""));        
        }
    	pw.println(sb.toString());   			
        buildInterface();
        new MessagesThread().start(); 
    }
    
    public void buildInterface() {
        send = new JButton("Send");
        exit = new JButton("Exit");
        showusers = new JButton("Show Users");
        changeuser = new JButton("Change User Name");
        chatmsg = new JTextArea();
        chatmsg.setRows(30);
        chatmsg.setColumns(50);
        chatmsg.setEditable(false);
        chatip  = new JTextField(50);
        JScrollPane sp = new JScrollPane(chatmsg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel( new FlowLayout());
        JPanel bp2 = new JPanel( new FlowLayout());
        bp.add(chatip);
        bp.add(send);
        bp.add(showusers);
        bp.add(exit);
        bp.setBackground(Color.LIGHT_GRAY);
        bp.setName("Chat-Box");
        add(bp,"North");
        bp2.add(changeuser);
        bp.setBackground(Color.LIGHT_GRAY);
        add(bp2,"South");
        send.addActionListener(this);
        exit.addActionListener(this);
        showusers.addActionListener(this);
        changeuser.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        if (evt.getSource() == showusers ) {
            pw.println("userlist");
        } else if ( evt.getSource() == exit ) {
            pw.println("end"); 
            System.exit(0);
        } else {
            pw.println(chatip.getText());
            chatip.setText(null);
        }
    }
    
    public static void newtry() {
        String SetUserName = JOptionPane.showInputDialog(null,"Login already used, choose another one :", "Chat-Box", JOptionPane.PLAIN_MESSAGE);
        //String SetPassword = JOptionPane.showInputDialog(null,"Please enter your Password :", "Chat-Box", JOptionPane.PLAIN_MESSAGE);
        String servername = "localhost";  
        try {
        	new ChatUsers( SetUserName , /*SetPassword,*/ servername);
        } catch(Exception ex) {
            out.println( "Error: " + ex.getMessage());
        }
        
    } 
    
    public void changeUser(String user) {
    	
    }
    
    public static void main(String ... args) {
        String SetUserName = JOptionPane.showInputDialog(null,"Please enter your name :", "Chat-Box", JOptionPane.PLAIN_MESSAGE);
        //String SetPassword = JOptionPane.showInputDialog(null,"Please enter your Password :", "Chat-Box", JOptionPane.PLAIN_MESSAGE);
        String servername = "localhost";  
        try {
            new ChatUsers( SetUserName , /*SetPassword,*/ servername);
        } catch(Exception ex) {
            out.println( "Error: " + ex.getMessage());
        }    
    } 
    
    class  MessagesThread extends Thread {
        @Override
        public void run() {
            String line;
            try {
                while(true) {
                    line = br.readLine();
                    if (line.equals("Login already used")) {
                    	//setVisible(false);
                    	dispose();
                    	newtry();
                    }
                    chatmsg.append(line + "\n");
                }
            } catch(Exception ex) {}
        }
    }
} 