import java.io.*;
import java.util.*;
import java.net.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.*;
import static java.lang.System.out;

public class ChatServer {

    public static void main(String... args) throws Exception {
        new ChatServer().createserver();
    } 
    
    Vector<String> users = new Vector<String>();
    Vector<String> Mac = new Vector<String>();
    //Vector<String> password = new Vector<String>();
    Vector<Manageuser> clients = new Vector<Manageuser>();

    public void createserver() throws Exception {
        ServerSocket server = new ServerSocket(2019, 10);
        out.println("Now Server Is Running");
        while (true) {
            Socket client = server.accept();
            Manageuser c = new Manageuser(client);
            clients.add(c);
        }
    }

    public void sendtoall(String user, String message) {
        for (Manageuser c : clients) {
            if (!c.getchatusers().equals(user)) {
                c.sendMessage(user, message);
            }
        }
    }
    
    public void sendtoone(String user, String message, String dest) {
        for (Manageuser c : clients) {
            if (c.getchatusers().equals(dest)) {
                c.sendWhisp(user, message);
            }
        }
    }
    
    public void myreturn(String user, String message) {
        for (Manageuser c : clients) {
            if (c.getchatusers().equals(user)) {
                c.sendReturn(user, message);
            }
        }
    }
    
    public static String getTime() {
    	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    	Date date = new Date();
    	return(formatter.format(date));
    }
    
    class Manageuser extends Thread {

        String gotuser = "", gotMac = "";
        BufferedReader input;
        PrintWriter output;

        public Manageuser(Socket client) throws Exception {

            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);
            gotuser = input.readLine();
            int check = 0;
            for (String u : users) {
                if (u.equals(gotuser)) {
                	this.sendKill();
                	clients.remove(this);
                	check = 1;
                }
            }
            if (check == 0) {
            	gotMac = input.readLine();
            	for (String g : Mac) {
            		if (g.equals(gotMac)) {
            			users.set(Mac.indexOf(gotMac), gotuser);
            			check = 2;
            		}
            	}
            	if(check != 2) {
            		Mac.add(gotMac);
            		users.add(gotuser); 
            	}
            	start();
            }
        }
        
        public void sendMessage(String chatuser, String chatmsg) {
            output.println(getTime() + "  " + chatuser + " says : " + chatmsg);
        }
        
        public void sendWhisp(String chatuser, String chatmsg) {
            output.println(getTime() + "  " + chatuser + " says to you : " + chatmsg);
        }
        
        public void sendReturn(String chatuser, String chatmsg) {
            output.println(getTime() + "  You : " + chatmsg);
        }
        
        public void sendKill() {
            output.println("Login already used");
        }
        
        public void sendList(String chatmsg) {
            output.println(getTime() + "  " + chatmsg);
        }
        
        public void sendhist(String chatuser, String dest) {
        	Path pathuser = Paths.get(Mac.get(users.indexOf(chatuser)) + "_" + Mac.get(users.indexOf(dest)) + ".txt");
        	try {BufferedReader br = new BufferedReader(new FileReader(pathuser.toString()));
	        	StringBuilder sb = new StringBuilder();
	    	    String line = br.readLine();
	
	    	    while (line != null) {
	    	        sb.append(line);
	    	        sb.append(System.lineSeparator());
	    	        line = br.readLine();
	    	        output.println(line);
	    	    }
	    	    br.close();
        	}
        	catch(IOException e){}
        }
        
        public void savehist(String chatuser, String dest, String msg) {
        	Path pathuser = Paths.get(Mac.get(users.indexOf(chatuser)) + "_" + Mac.get(users.indexOf(dest)) + ".txt");
        	Path pathdest = Paths.get(Mac.get(users.indexOf(dest)) + "_" + Mac.get(users.indexOf(chatuser)) + ".txt");
        	try(FileWriter fw = new FileWriter(pathuser.toString(), true);
        		    BufferedWriter bw = new BufferedWriter(fw);
        		    PrintWriter hist = new PrintWriter(bw))
        		{
        			hist.println(getTime() + "  You : " + msg);
        		} catch (IOException e) {}
        	try(FileWriter fw = new FileWriter(pathdest.toString(), true);
        		    BufferedWriter bw = new BufferedWriter(fw);
        		    PrintWriter hist = new PrintWriter(bw))
        		{
        			hist.println(getTime() + "  " + chatuser + " says to you : " + msg);
        		} catch (IOException e) {}
        }
        
        public String getchatusers() {
            return gotuser;
        }

        @Override
        public void run() {
            String line;
            try {
                while (true) {
                    line = input.readLine();
                    if (line.equals("end")) {
                        clients.remove(this);
                        users.set(users.indexOf(gotuser), "");
                        //users.remove(gotuser);
                        sendtoall(gotuser, line); 
                        break;
                    }else if(line.equals("userlist")) {
                    	for (Manageuser c : clients) {
                            if (c.getchatusers().equals(gotuser)) {
                            	String list = "List of Users : ";
                            	for (Manageuser d : clients) {
                                    if (!d.getchatusers().equals(gotuser)) {
                                    	list = list + d.getchatusers() + " / ";
                                    }
                                }
                            	list = list.substring(0, list.length()-3);
                            	c.sendList(list);
                            	
                            	String listMac = "List of Mac : ";
                            	for (String d : Mac) {
                                    listMac = listMac + d + " / ";
                                }
                            	listMac = listMac.substring(0, listMac.length()-3);
                            	c.sendList(listMac);
                            }
                        }
                    } else {
                    	String[] result = line.split("\"");
                    	if (result[0].isEmpty()) {
                    		//if (result[2].isEmpty())
                    			//sendhist(gotuser,result[1]);
                    		//else {
                    			sendtoone(gotuser, result[2],result[1]);
                    			//savehist(gotuser,result[1], result[2]);
                    			myreturn(gotuser,result[2]);
                    		//}
                    	} else {
                    		sendtoall(gotuser, line);        
                    		myreturn(gotuser,line);
                    	}
                    }
                }
            } 
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        } 
    } 
} 