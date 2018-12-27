package com.moowei.map.utils;

import com.moowei.map.services.PlaceSearchForBaiduMap;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.io.File;


/**
 * Package com.moowei.map.utils
 * Description: TODO
 *
 * @author moowei
 * date 2018/12/24 0024 15:14
 * @version V1.0
 */
public class JavaSwing {

    JLabel label;

    public static void main(String[] args) throws Exception {
        PlaceSearchForBaiduMap placeSearchForBaiduMap = new PlaceSearchForBaiduMap();
        final JFrame jf = new JFrame("解析成省份和地市");
        jf.setSize(850, 600);
        jf.setLocationRelativeTo(null);
        jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel panel = new JPanel();

//        JScrollPane panel = new JScrollPane();
        // 创建文本区域, 用于显示相关信息
        final JTextArea msgTextArea = new JTextArea(25, 30);
//        panel.setBounds(28, 209, 952, 565);
        msgTextArea.setBounds(28, 209, 790, 265);
//        panel.setViewportView(msgTextArea);
        msgTextArea.setLineWrap(true);
        panel.add(msgTextArea);

//        JButton openBtn = new JButton("打开");
//        openBtn.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                showFileOpenDialog(jf, msgTextArea);
//            }
//        });
//        panel.add(openBtn);

        JButton saveBtn = new JButton("关闭");
        saveBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                showFileSaveDialog(jf, msgTextArea);
                System.exit(0);
            }
        });
        panel.add(saveBtn);

        JButton openAndParseBtn = new JButton("解析");
        System.out.println("-------start--------------");
        openAndParseBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File inputFile = showFileOpenDialog(jf, msgTextArea);
                msgTextArea.append("输入文件: " + inputFile.getAbsolutePath() + "\n");
                File errorOutputFile = new File(inputFile.getAbsolutePath().replaceAll(".txt", "_error.txt"));
                File outputFile = new File(inputFile.getAbsolutePath().replaceAll(".txt", "_out.txt"));
                msgTextArea.append("错误数据保存路径: " + errorOutputFile.getAbsolutePath() + "\n");
                msgTextArea.append("解析结果数据保存路径: " + outputFile.getAbsolutePath() + "\n");
                try {
                    outputFile.deleteOnExit();
                    outputFile.createNewFile();
                    errorOutputFile.deleteOnExit();
                    errorOutputFile.createNewFile();
//                    PlaceSearchForBaiduMap.suggestionAPI(inputFile,outputFile,errorOutputFile,msgTextArea);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        });
        panel.add(openAndParseBtn);
        jf.setContentPane(panel);
        jf.setVisible(true);
    }

    /**
     * 打开文件
     */
    public static File showFileOpenDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();
        File file = null;
        // 设置默认显示的文件夹为当前文件夹
        fileChooser.setCurrentDirectory(new File("."));

        // 设置文件选择的模式（只选文件、只选文件夹、文件和文件均可选）
        fileChooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        // 设置是否允许多选
        fileChooser.setMultiSelectionEnabled(true);

        // 添加可用的文件过滤器（FileNameExtensionFilter 的第一个参数是描述, 后面是需要过滤的文件扩展名 可变参数）
        fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("zip(*.zip, *.rar)", "zip", "rar"));
        // 设置默认使用的文件过滤器
        fileChooser.setFileFilter(new FileNameExtensionFilter("image(*.jpg, *.png, *.gif)", "jpg", "png", "gif"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showOpenDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
//            msgTextArea.append("打开文件: " + file.getAbsolutePath() + "\n\n");
            // 如果点击了"确定", 则获取选择的文件路径
            file = fileChooser.getSelectedFile();
            // 如果允许选择多个文件, 则通过下面方法获取选择的所有文件
            // File[] files = fileChooser.getSelectedFiles();
//
        }
        return file;
    }

    /**
     * 选择文件保存路径
     */
    private static void showFileSaveDialog(Component parent, JTextArea msgTextArea) {
        // 创建一个默认的文件选取器
        JFileChooser fileChooser = new JFileChooser();

        // 设置打开文件选择框后默认输入的文件名
        fileChooser.setSelectedFile(new File("测试文件.zip"));

        // 打开文件选择框（线程将被阻塞, 直到选择框被关闭）
        int result = fileChooser.showSaveDialog(parent);

        if (result == JFileChooser.APPROVE_OPTION) {
            // 如果点击了"保存", 则获取选择的保存路径
            File file = fileChooser.getSelectedFile();
            msgTextArea.append("保存到文件: " + file.getAbsolutePath() + "\n\n");
        }
    }

}