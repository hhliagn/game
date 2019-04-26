/*
package com.game;


import javax.swing.*;
import java.awt.event.ActionListener;

public class SocketConnect extends JFrame implements ActionListener {

    private JLabel lblServerIp;
    private JLabel lblServerPort;
    private JTextField tfServerIp;
    private JTextField tfServerPort;
    private JButton btnOpenConnect;
    private JButton btnCloseConnect;
    private JTextArea textArea;

    private SocketAcceptor acceptor;

    public SocketConnect() throws HeadlessException {
        acceptor = new NioSocketAcceptor();
    }

    public void myContainer(Container container){

        JPanel jPanel = new JPanel();
        jPanel.setLayout(new GridBagLayout());

        GridBagConstraints gridBagConstraints =new GridBagConstraints();
        gridBagConstraints.fill=GridBagConstraints.CENTER;

        lblServerIp = new JLabel("服务器IP:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets=new Insets(10, 0, 10, 0);
        jPanel.add(lblServerIp, gridBagConstraints);

        tfServerIp = new JTextField(15);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.anchor=GridBagConstraints.LINE_START;
        gridBagConstraints.insets=new Insets(10, 10, 10, 0);
        tfServerIp.setText("192.168.1.218");
        tfServerIp.setEditable(false);
        jPanel.add(tfServerIp, gridBagConstraints);

        lblServerPort = new JLabel("端口:");
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets=new Insets(10, 0, 10, 0);
        jPanel.add(lblServerPort, gridBagConstraints);

        tfServerPort = new JTextField(15);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets=new Insets(10, 10, 10, 0);
        tfServerPort.setText("4999");
        jPanel.add(tfServerPort, gridBagConstraints);

        btnOpenConnect = new JButton("开启Socket");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 0;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets=new Insets(10, 20, 10, 0);
        btnOpenConnect.addActionListener(this);
        jPanel.add(btnOpenConnect, gridBagConstraints);

        btnCloseConnect = new JButton("关闭Socket");
        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets=new Insets(10, 20, 10, 0);
        btnCloseConnect.addActionListener(this);
        jPanel.add(btnCloseConnect, gridBagConstraints);

        textArea = new JTextArea(60,25);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.weightx = 0;
        gridBagConstraints.weighty = 0;
        gridBagConstraints.insets=new Insets(10, 20, 10, 0);
        textArea.setEditable(false);
        textArea.setAutoscrolls(true);
        Dimension d = new Dimension(20, 20);
        textArea.setMaximumSize(d);
        container.add(new JScrollPane(textArea),"Center");
        //添加jPanel
        container.add(jPanel,BorderLayout.PAGE_START);

    }

    public  void ActionEventFrame(){
        JFrame frame =new JFrame();
        frame.setTitle("socket连接窗口");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //调用方法
        this.myContainer(frame.getContentPane());
        frame.setLocation(400, 50);
        frame.setSize(800, 650);
//		frame.setBounds(500, 230, 200, 210);
        frame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton button= (JButton)e.getSource();
        if(button == btnOpenConnect)
        {
            //开启socket连接
            Connect();
        }
        else if(button == btnCloseConnect)
        {
            //关闭socket
            acceptor.dispose();
        }
    }

    //连接socket的代码
    public void Connect(){
        textArea.append("正在等待连接........\n");

        acceptor = new NioSocketAcceptor();
        DefaultIoFilterChainBuilder chain = acceptor.getFilterChain();
        //获取端口控件的值
        int bindPort = Integer.parseInt(tfServerPort.getText());
        chain.addLast("myChin", new ProtocolCodecFilter(new ObjectSerializationCodecFactory()));
        //创建serverhandler
        acceptor.setHandler(ServerHandler.getInstances(textArea));
        try {
            acceptor.bind(new InetSocketAddress(bindPort));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String args[]) {
        SocketConnect s = new SocketConnect();
        s.ActionEventFrame();
    }
}
*/
