package com.client;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.Socket;

//Socket
public class MyClientWindow extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTextArea txt;
    private JTextField txtip;
    private JTextField txtSend;

    private Socket socket;
    private BufferedReader bReader;
    private PrintWriter pWriter;

    public MyClientWindow() throws IOException {
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);

        txt = new JTextArea();
        txt.setText("准备...");

        txtip = new JTextField();
        txtip.setText("127.0.0.1");
        txtip.setColumns(10);

        JButton btnConnect = new JButton("connect");
        btnConnect.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                String IP = txtip.getText();
                try{
                    socket = new Socket(IP, 23456);
                    // 输出流
                    pWriter = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
                    bReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    // 输入流
                    /*String line = null;
                    // 如果读取数据为空
                    while ((line = bReader.readLine()) != null) {
                        appendText("收到： " + line);
                    }
                    // 读完数据之后要关闭
                    pWriter.close();
                    bReader.close();
                    pWriter = null;
                    bReader = null;*/
                }catch (Exception e1){
                    e1.printStackTrace();
                }
            }
        });

        txtSend = new JTextField();
        txtSend.setText("hello");
        txtSend.setColumns(10);

        JButton btnSend = new JButton("send");
        btnSend.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                try{
                    String sendMsg = txtSend.getText();
                    if (pWriter != null) {
                        pWriter.write(sendMsg + "\n");
                        pWriter.flush();
                    } else {
                        appendText("当前链接已经中断...");
                    }
                    appendText("我说: " + sendMsg);
                    txtSend.setText("");

                    String line = null;
                    while ((line = bReader.readLine())!=null){
                        appendText(line);
                    }
                }catch (Exception e2){
                    e2.printStackTrace();
                }
            }
        });
        GroupLayout gl_contentPane = new GroupLayout(contentPane);

        gl_contentPane.setHorizontalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING).addGroup(
                GroupLayout.Alignment.TRAILING,
                gl_contentPane.createSequentialGroup().addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING)
                        .addGroup(gl_contentPane.createSequentialGroup()
                                .addComponent(txtSend, GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(btnSend, GroupLayout.PREFERRED_SIZE, 109, GroupLayout.PREFERRED_SIZE))
                        .addGroup(GroupLayout.Alignment.LEADING,
                                gl_contentPane.createSequentialGroup()
                                        .addComponent(txtip, GroupLayout.PREFERRED_SIZE, 294,
                                                GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnConnect, GroupLayout.DEFAULT_SIZE, 140, Short.MAX_VALUE))
                        .addComponent(txt, GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)).addContainerGap()));

        gl_contentPane.setVerticalGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.LEADING)
                .addGroup(gl_contentPane.createSequentialGroup()
                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(txtip, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE)
                                .addComponent(btnConnect))
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(gl_contentPane.createParallelGroup(GroupLayout.Alignment.TRAILING).addComponent(btnSend)
                                .addComponent(txtSend, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
                                        GroupLayout.PREFERRED_SIZE))));

        contentPane.setLayout(gl_contentPane);
    }

    /* 客户端发送的内容添加到中间的txt控件中 */
    public void appendText(String in) {
        txt.append("\n" + in);
    }

    public void recieve() throws IOException {
        String line = null;
        if ((line = bReader.readLine()) != null) {
            appendText(line);
        }
    }
}
