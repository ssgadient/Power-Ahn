public class CommandTester {

    public static void main(String[] args) {
       try {
 
          // print a message
          System.out.println("Executing Brave.exe");
 
          // create a process and execute Brave.exe
          Process process = Runtime.getRuntime().exec(new String[]{"cmd", "/c", "explorer shell:Appsfolder\\Brave"});
 
       } catch (Exception ex) {
          ex.printStackTrace();
       }
    }
 }