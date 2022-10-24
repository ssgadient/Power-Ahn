class Main {

    public static void main(String[] args) {
        MainFrame frame = new MainFrame();
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() { frame.createAndShowGUI(); }
        });


    }

}