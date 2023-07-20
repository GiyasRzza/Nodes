package com.example.nodess.SwingUI;

import lombok.Getter;
import lombok.Setter;
import javax.swing.*;
import java.util.Arrays;

public class AnagramChecker extends JFrame {
    private JPanel myPanel;
    private JButton boshButton;
    private JTextField getUser;
    private JTextField getPass;
    private JTextField isAnagram;
    private JLabel field1;
    private JLabel field2;
    @Getter
    @Setter
    private  String userName;
    @Getter
    @Setter
    private   String password;




    public AnagramChecker() {

        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedLookAndFeelException e) {
            throw new RuntimeException(e);
        }
        add(myPanel);
        setTitle("Spaghetti");
        setSize(500,500);
        setResizable(false);
        setVisible(true);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
       field1.setSize(150,150);
        boshButton.addActionListener(e -> {
            setUserName(Checker.userName);
            setPassword(Checker.password);
            StringBuffer bufferUserName = new StringBuffer();
            StringBuffer bufferPassword = new StringBuffer();
            bufferUserName.append(getUserName().toLowerCase());
            bufferPassword.append(getPassword().toLowerCase());
            if(bufferPassword.isEmpty() && bufferUserName.isEmpty()){
                JOptionPane.showMessageDialog(null,"Qeydiyyatdan kecin!");
            }
            String toPassBuf = bufferPassword.toString();
            String toUserBuf =bufferUserName.toString();
            char[] myUserArray = toUserBuf.toCharArray();
            char[] myPassArray = toPassBuf.toCharArray();
            Arrays.sort(myUserArray);
            Arrays.sort(myPassArray);
            getUser.setText(toUserBuf);
            getPass.setText(Checker.encrptPassword);

            if (Arrays.equals(myPassArray,myUserArray) && myPassArray.length==myUserArray.length){
                isAnagram.setText( "Password: "+toPassBuf+" and"+" Username: "+toUserBuf+" are anagram");

            }
            else{
                isAnagram.setText( "Password: "+toPassBuf+" and"+" Username: "+toUserBuf+" are not anagram");
            }
        });
    }







}
